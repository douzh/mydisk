package com.iteedu.crypto.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 解密工具
 * 
 */
public class DeUtils extends CryptoUtils{



	/**
	 * RSA解密
	 * 
	 * @param content
	 *            待解密密�?
	 * @return 明文
	 */
	public static String rsaDec(String content, String prikeyStr) {
		try {
			byte[] newPlainText = rsaDecByte(content, prikeyStr);
			if (newPlainText == null) {
				throw new RuntimeException("RSA解密异常");
			}
			return (new String(newPlainText));
		} catch (Exception e) {
			throw new RuntimeException("RSA解密异常",e);
		}
	}

	/**
	 * RSA解密
	 * @param content
	 * @param prikeyStr
	 * @return
	 */
	public static byte[] rsaDecByte(String content, String prikeyStr) {
		try {
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey privkey = null;
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			InputStream key = new ByteArrayInputStream(
					prikeyStr.getBytes("utf-8"));
			byte[] pribytes = new byte[new Long(prikeyStr.length()).intValue()];
			if (key.read(pribytes) > 0) {
				// 生成私钥
				PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
						base64Decode(new String(pribytes)));
				privkey = keyf.generatePrivate(priPKCS8);
				cipher.init(Cipher.DECRYPT_MODE, privkey);
				byte[] newPlainText = cipher.doFinal(base64Decode(content));
				return newPlainText;
			}else{
				throw new RuntimeException("RSA解密异常, 私钥为空");
			}
		} catch (Exception e) {
			throw new RuntimeException("RSA解密异常", e);
		}
	}

	/**
	 * AES解密
	 * 
	 * @param strMi
	 *            加密后转换为base64格式的字符串
	 * @param strKey
	 *            加密用的Key
	 * @return 解密的字符串
	 * 
	 *         首先base64解密，�?�后在用key解密
	 */
	public static String aesDec(String strMi, String strKey) {
		try {
			byte[] result = aesDecByte(strMi, strKey);
			return new String(result, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException("AES解密异常", e);
		}
	}

	/**
	 * AES解密
	 * @param strMi
	 * @param strKey
	 * @return
	 */
	public static byte[] aesDecByte(String strMi, String strKey) {
		try {
			SecretKey secretKey = getKey(strKey);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码�?
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始�?
			byte[] bytes = base64Decode(strMi);
			byte[] result = cipher.doFinal(bytes);
			return result; // 解密
		} catch (Exception e) {
			throw new RuntimeException("AES解密异常", e);
		}
	}
	
}
