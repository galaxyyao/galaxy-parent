package com.galaxy.cypher.util;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsaCypherUtil {
	private static Logger logger = LoggerFactory.getLogger(RsaCypherUtil.class);

	public static void main(String[] args) throws Exception {
		// generate public and private keys
		KeyPair keyPair = buildKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));

		// encrypt the message
		byte[] encrypted = encrypt(publicKey, "This is a secret message");
		logger.info(new String(encrypted)); // <<encrypted message>>

		// decrypt the message
		byte[] secret = decrypt(privateKey, encrypted);
		logger.info(new String(secret)); // This is a secret message
	}

	public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
		final int keySize = 2048;
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keySize);
		return keyPairGenerator.genKeyPair();
	}

	public static byte[] encrypt(PrivateKey privateKey, String message) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(message.getBytes());
	}

	public static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(encrypted);
	}

	public static byte[] encrypt(PublicKey publicKey, String message) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(message.getBytes());
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(encrypted);
	}
}
