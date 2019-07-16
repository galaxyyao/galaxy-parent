package com.galaxy.authentication.service.sys;

import com.galaxy.authentication.constant.AuthConstant;
import com.galaxy.common.constant.RequestConstant;
import com.galaxy.authentication.config.AuthProperties;
import com.galaxy.authentication.domain.UserRepository;
import com.galaxy.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.galaxy.authentication.domain.entity.User;
import com.galaxy.authentication.event.ApplicationStartupListener;
import com.galaxy.authentication.service.jwt.JwtTokenService;
import com.galaxy.common.constant.CommonConstant;
import com.galaxy.common.rest.domain.JsonResult;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthProperties authProps;

    @Autowired
    private JwtTokenService jwtTokenService;

    public User getCurrentUser() throws BusinessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return getValidUserByUserCode(currentPrincipalName);
    }

    public String getCurrentUserCode() throws BusinessException {
        return getCurrentUser().getUserCode();
    }

    @Cacheable("user")
    public User getUnfilteredUser(String userCode) throws BusinessException {
        User user = userRepository.findByUserCode(userCode).orElseThrow(() -> new BusinessException("ATH1001"));
        return user;
    }

    public User getValidUserByUserCode(String userCode) throws BusinessException {
        if (AuthConstant.TOKEN_MODE.equals(authProps.getMode())) {
            String authServerUrl = authProps.getToken().getAuthCenterUrl();
            RestTemplate restTemplate = new RestTemplate();
            ParameterizedTypeReference<JsonResult<User>> typeRef = new ParameterizedTypeReference<JsonResult<User>>() {
            };
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(headers);
            String url = authServerUrl + "/user/userByUserCode/" + userCode;
            ResponseEntity<JsonResult<User>> userResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
                    typeRef);
            if (userResponseEntity != null && userResponseEntity.getBody() != null) {
                throw new BusinessException("ATH1001");
            }
            if (!CommonConstant.JSON_RESULT_SUCCESS.equals(userResponseEntity.getBody().getCode())) {
                throw new BusinessException("ATH1001");
            }
            User user = userResponseEntity.getBody().getObject();
            return user;
        }
        User user = userRepository.findByUserCodeIgnoreCaseAndIsLockedAndIsDeleted(userCode, CommonConstant.NO,
                CommonConstant.NO).orElseThrow(() -> new BusinessException("ATH1001"));
        return user;
    }

    public User getValidUserByAuthToken(String authToken) throws BusinessException {
        if (authToken.startsWith(RequestConstant.AUTHORIZATION_TOKEN_PREFIX)) {
            authToken = authToken.substring(RequestConstant.AUTHORIZATION_TOKEN_PREFIX.length());
        }
        String userCode = jwtTokenService.getUsernameFromToken(authToken);
        User user = userRepository.findByUserCodeIgnoreCaseAndIsLockedAndIsDeleted(userCode, CommonConstant.NO,
                CommonConstant.NO).orElseThrow(() -> new BusinessException("ATH1001"));
        return user;
    }
}
