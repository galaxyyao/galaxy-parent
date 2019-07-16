package com.galaxy.cypher.util;

import com.galaxy.common.exception.BusinessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class DESCypherUtilTest {

	@Parameterized.Parameter(value = 0)
	public String unEncryptedText;

	@Parameterized.Parameter(value = 1)
	public String encryptedText;

	@Parameterized.Parameter(value = 2)
	public String key;

	@Parameterized.Parameter(value = 3)
	public String iv;

	@Parameterized.Parameter(value = 4)
	public String charset;

	@Parameterized.Parameter(value = 5)
	public String encryptedCharsetText;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"明文", "edOjg7Xjd8s=", "12345678", "12345678", "GB18030", "bhRfghHMSfY="}
		});
	}

	@Test
	public void encryptToBase64Text_shouldSucceed() throws BusinessException {
		DesCypherUtil desCypherUtil = DesCypherUtil.getInstance("DES/CBC/PKCS5Padding", iv);
		assertEquals(encryptedText, desCypherUtil.encryptToBase64Text(key, unEncryptedText));
	}

	@Test
	public void encryptToBase64Text_charsetParam_shouldSucceed() throws BusinessException {
		DesCypherUtil desCypherUtil = DesCypherUtil.getInstance("DES/CBC/PKCS5Padding", iv);
		assertEquals(encryptedCharsetText, desCypherUtil.encryptToBase64Text(key, unEncryptedText, charset));
	}

	@Test
	public void decryptToBase64Text_shouldSucceed() throws BusinessException {
		DesCypherUtil desCypherUtil = DesCypherUtil.getInstance("DES/CBC/PKCS5Padding", iv);
		assertEquals(unEncryptedText, desCypherUtil.decryptToBase64Text(key, encryptedText));
	}

	@Test
	public void decryptToBase64Text_charsetParam_shouldSucceed() throws BusinessException {
		DesCypherUtil desCypherUtil = DesCypherUtil.getInstance("DES/CBC/PKCS5Padding", iv);
		assertEquals(unEncryptedText, desCypherUtil.decryptToBase64Text(key, encryptedCharsetText, charset));
	}
}