package com.galaxy.authentication.controller;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import com.galaxy.common.constant.RequestConstant;
import com.galaxy.authentication.config.AuthProperties;
import com.galaxy.authentication.domain.custom.JwtAuthenticationRequest;
import com.galaxy.authentication.domain.custom.JwtAuthenticationResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galaxy.authentication.constant.AuthConstant;
import com.galaxy.authentication.exception.AuthenticationException;
import com.galaxy.authentication.security.JwtUser;
import com.galaxy.authentication.service.jwt.JwtTokenService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.exception.BusinessException;
import com.galaxy.common.rest.domain.JsonResult;
import com.galaxy.cypher.util.RsaCypherUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@RestController
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthProperties authProps;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	private Cache<String, KeyPair> userKeyPair;

	@RequestMapping(value = "${auth.jwt.login-path}", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws BusinessException {
		String userCode = authenticationRequest.getUsername().toLowerCase();
		authenticate(userCode, authenticationRequest.getPassword());
		return generateJwtAuthenticationResponse(userCode);
	}

	@RequestMapping(value = "${auth.jwt.sso-path}", method = RequestMethod.POST)
	public ResponseEntity<?> sso(@RequestParam String userCode, @RequestParam String ssoToken) throws BusinessException {
		userCode = userCode.toLowerCase();
		ssoAuthenticate(userCode, ssoToken);
		return generateJwtAuthenticationResponse(userCode);
	}

	private ResponseEntity<?> generateJwtAuthenticationResponse(String username) {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final String token = jwtTokenService.generateToken(userDetails);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, RequestConstant.REQUEST_HEADER_KEY_AUTHORIZATION);
		headers.add(HttpHeaders.AUTHORIZATION, RequestConstant.AUTHORIZATION_TOKEN_PREFIX + token);

		// Return the token
		return ResponseEntity.ok().headers(headers).body(new JsonResult<>(
				CommonConstant.JSON_RESULT_SUCCESS, "",
				new JwtAuthenticationResponse(RequestConstant.AUTHORIZATION_TOKEN_PREFIX + token)));
	}

	@RequestMapping(value = "${auth.jwt.refresh-path}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(authProps.getJwt().getHeader());
		final String token = authToken.substring(7);
		String username = jwtTokenService.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		logger.info("User to refresh:" + user.getUsername());

		if (jwtTokenService.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenService.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<JsonResult<Void>> handleAuthenticationException(AuthenticationException e) {
		logger.error(e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JsonResult<>("ATH1001", e.getMessage()));
	}

	/**
	 * Authenticates the user. If something is wrong, an
	 * {@link AuthenticationException} will be thrown
	 *
	 * @throws AuthenticationException
	 */
	private void authenticate(String username, String password) throws BusinessException {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			if (authProps.getIsEncryptPassword()) {
				KeyPair keyPair = getUserKeyPair().getIfPresent(username);
				PrivateKey privateKey = keyPair.getPrivate();
				byte[] encrypted = Base64.getDecoder().decode(password);
				byte[] secret = RsaCypherUtil.decrypt(privateKey, encrypted);
				String decryptedPassword = new String(secret);
				authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(username, decryptedPassword));
			} else {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			}
		} catch (DisabledException e) {
			throw new BusinessException("ATH1003").setPlaceHolder(username);
		} catch (BadCredentialsException | IllegalArgumentException e) {
			throw new BusinessException("ATH1004").setPlaceHolder(username);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new BusinessException("ATH1002").setPlaceHolder(username);
		}
	}

	private void ssoAuthenticate(String username, String token) throws BusinessException {
		//TODO: authenticate SSO user token
	}

	@RequestMapping(method = RequestMethod.POST, value = "/security/encryptPassword")
	public JsonResult<String> encryptPassword(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws BusinessException {
		String userName = authenticationRequest.getUsername().toLowerCase();
		authenticationRequest.setUsername(userName);
		try {
			KeyPair keyPair = getUserKeyPair().getIfPresent(userName);
			if (keyPair == null) {
				throw new BusinessException("ATH1006").setPlaceHolder(userName);
			}
			PublicKey publicKey = keyPair.getPublic();
			byte[] secret = RsaCypherUtil.encrypt(publicKey, authenticationRequest.getPassword());
			String encryptedPassword = Base64.getEncoder().encodeToString(secret);
			return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", encryptedPassword);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			throw new BusinessException("ATH1002").setPlaceHolder(userName);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/security/publicKey/{userCode:.+}")
	public JsonResult<String> getPublicKey(@PathVariable String userCode) throws BusinessException {
		userCode = userCode.toLowerCase();
		// generate public and private keys
		KeyPair keyPair;
		try {
			keyPair = RsaCypherUtil.buildKeyPair();
			getUserKeyPair().put(userCode, keyPair);
			logger.info("public key generated for user:" + userCode);
			PublicKey publicKey = keyPair.getPublic();
			String publicKeyText = Base64.getEncoder().encodeToString(publicKey.getEncoded());
			return new JsonResult<>(CommonConstant.JSON_RESULT_SUCCESS, "", publicKeyText);
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException("ATH1002").setPlaceHolder(userCode);
		}
	}

	private Cache<String, KeyPair> getUserKeyPair() {
		if (userKeyPair == null) {
			userKeyPair = CacheBuilder.newBuilder().maximumSize(AuthConstant.KEY_PAIR_CACHE_MAX_NUMBER)
					.expireAfterWrite(AuthConstant.KEY_PAIR_CACHE_EXPIRATION_MINUTE, TimeUnit.MINUTES).build();
		}
		return userKeyPair;
	}
}