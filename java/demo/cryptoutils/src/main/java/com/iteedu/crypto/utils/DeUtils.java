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
public class DeUtils{

    private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * 私有构造，限制创建
	 */
	private DeUtils() {

	}

	/**
	 * RSA解密
	 * 
	 * @param content
	 *            待解密密
	 * @return 明文
	 * @throws CryptoException
	 */
	public static String rsaDec(String content, String prikeyStr) throws CryptoException {
		try {
			byte[] newPlainText = rsaDecByte(content, prikeyStr);
			if (newPlainText == null) {
				throw new CryptoException("RSA解密异常,解密内容为空");
			}
            return new String(newPlainText, DEFAULT_ENCODING);
		} catch (Exception e) {
			throw new CryptoException("RSA解密异常", e);
		}
	}

	/**
	 * RSA解密
	 * 
	 * @param content
	 * @param prikeyStr
	 * @return
	 * @throws CryptoException
	 */
	public static byte[] rsaDecByte(String content, String prikeyStr) throws CryptoException {
		try {
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			InputStream key = new ByteArrayInputStream(prikeyStr.getBytes("utf-8"));
			byte[] pribytes = new byte[prikeyStr.length()];
			if (key.read(pribytes) > 0) {
				// 生成私钥
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                        CryptoUtils.base64Decode(new String(pribytes, DEFAULT_ENCODING)));
				PrivateKey privkey = keyf.generatePrivate(priPKCS8);
				cipher.init(Cipher.DECRYPT_MODE, privkey);
				return cipher.doFinal(CryptoUtils.base64Decode(content));
			} else {
				throw new CryptoException("RSA解密异常, 私钥为空");
			}
		} catch (Exception e) {
			throw new CryptoException("RSA解密异常", e);
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
	 *         首先base64解密，后在用key解密
	 * @throws CryptoException
	 */
	public static String aesDec(String strMi, String strKey) throws CryptoException {
		try {
			byte[] result = aesDecByte(strMi, strKey);
			return new String(result, "utf-8");
		} catch (Exception e) {
			throw new CryptoException("AES解密异常", e);
		}
	}

	/**
	 * AES解密
	 * 
	 * @param strMi
	 * @param strKey
	 * @return
	 * @throws CryptoException
	 */
	public static byte[] aesDecByte(String strMi, String strKey) throws CryptoException {
		try {
			SecretKey secretKey = CryptoUtils.getKey(strKey);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始
			byte[] bytes = CryptoUtils.base64Decode(strMi);
			return cipher.doFinal(bytes);
		} catch (Exception e) {
			throw new CryptoException("AES解密异常", e);
		}
	}

}
