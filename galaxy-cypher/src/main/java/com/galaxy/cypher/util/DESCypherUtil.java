package com.galaxy.cypher.util;

import com.galaxy.common.exception.BusinessException;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESCypherUtil {
	private static final Logger logger = LoggerFactory.getLogger(DESCypherUtil.class);

	/**
	 * 算法名称/加密模式/填充方式
	 * DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	 */
	private String cypherAlgorithm;

	/**
	 * 向量
	 */
	private String iv;

	private DESCypherUtil(String cypherAlgorithm, String iv) {
		this.cypherAlgorithm = cypherAlgorithm;
		this.iv = iv;
	}

	private volatile static DESCypherUtil instance;

	public static DESCypherUtil getInstance(String cypherAlgorithm, String iv) {
		if (instance == null) {
			synchronized (DESCypherUtil.class) {
				if (instance == null) {
					instance = new DESCypherUtil(cypherAlgorithm, iv);
				}
			}
		}
		return instance;
	}

	public String encryptToBase64Text(String key, String textToEncrypt) throws BusinessException {
		String defaultCharset = "UTF-8";
		return encryptToBase64Text(key, textToEncrypt, defaultCharset);
	}

	public String encryptToBase64Text(String key, String textToEncrypt, String charset) throws BusinessException {
		byte[] encryptedData;
		try {
			encryptedData = encrypt(key, textToEncrypt.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("CIPHER1002");
		}
		return BaseEncoding.base64().encode(encryptedData);
	}

	public byte[] encrypt(String key, byte[] dataToEncrypt) throws BusinessException {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(cypherAlgorithm);
			IvParameterSpec iv = new IvParameterSpec(this.iv.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

			byte[] encryptedData = cipher.doFinal(dataToEncrypt);
			return encryptedData;
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("CIPHER1001");
		}
	}

	public String decryptToBase64Text(String key, String textToDecrypt) throws BusinessException {
		String defaultCharset = "UTF-8";
		return decryptToBase64Text(key, textToDecrypt, defaultCharset);
	}

	public String decryptToBase64Text(String key, String textToDecrypt, String charset) throws BusinessException {
		byte[] dataToDecrypt = BaseEncoding.base64().decode(textToDecrypt);
		byte[] decryptedData = decrypt(key, dataToDecrypt);
		try {
			return new String(decryptedData, charset);
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("CIPHER1002");
		}
	}

	public byte[] decrypt(String key, byte[] dataToDecrypt) throws BusinessException {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(cypherAlgorithm);
			IvParameterSpec iv = new IvParameterSpec(this.iv.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			byte[] decryptedData = cipher.doFinal(dataToDecrypt);
			return decryptedData;
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("CIPHER1001");
		}
	}
}
