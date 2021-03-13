/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.pay.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class WxPayUtil {

	public static String descrypt(String reqInfo, String mchId) throws NoSuchPaddingException,
			NoSuchAlgorithmException, NoSuchProviderException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
		byte[] reqInfoB = Base64.decodeBase64(reqInfo);
		String key = DigestUtils.md5Hex(mchId).toLowerCase();
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		return new String(cipher.doFinal(reqInfoB));
	}
}
