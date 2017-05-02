package com.iteedu.crypto.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * RSA加密解密 AES加密解密 RSA公私密钥生成
 * 
 */
public class EnUtils  extends CryptoUtils{

	/**
	 * RSA公钥加密明文
	 * 
	 * @param content
	 *            待加密明�?
	 * @return 密文
	 */
	public static String rsaEnc(String content, String pk) {
		return rsaEnc(content.getBytes(), pk);
	}

	/**
	 * RSA加密
	 * 
	 * @param content
	 * @param pk
	 * @return
	 */
	public static String rsaEnc(byte[] content, String pk) {
		try {
			InputStream is = new ByteArrayInputStream(pk.getBytes("utf-8"));
			byte[] pubbytes = new byte[new Long(pk.length()).intValue()];
			if (is.read(pubbytes) > 0) {
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(
						base64Decode(new String(pubbytes)));

				PublicKey pubkey = keyf.generatePublic(pubX509);
				cipher.init(Cipher.ENCRYPT_MODE, pubkey);
				byte[] cipherText = cipher.doFinal(content);
				// 转换为Base64编码存储，以便于internet传�??
				return base64Encode(cipherText);
			} else {
				throw new RuntimeException("RSA加密异常, 公钥为空");
			}
		} catch (Exception e) {
			throw new RuntimeException("RSA加密异常", e);
		}
	}

	/**
	 * 加密以String明文输入,String密文输出
	 * 
	 * @param strContent
	 *            待加密字符串
	 * @param strKey
	 *            加密用的Key
	 * @return 加密后转换为base64格式字符�?
	 */
	public static String aesEnc(String strContent, String strKey) {
		try {
			return aesEnc(strContent.getBytes("utf-8"), strKey);
		} catch (Exception e) {
			throw new RuntimeException("AES加密异常", e);
		}
	}

	/**
	 * AES加密
	 * @param byteContent
	 * @param strKey
	 * @return
	 */
	public static String aesEnc(byte[] byteContent, String strKey) {
		String strMi = "";
		try {
			SecretKey secretKey = getKey(strKey);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码�?
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始�?
			byte[] result = cipher.doFinal(byteContent);
			strMi = base64Encode(result);
		} catch (Exception e) {
			throw new RuntimeException("AES加密异常", e);
		}
		return strMi; // 加密
	}

	/**
	 * @param charCount
	 *            字符串数�?
	 * @return 键盘上字符产生数量为charCount的随机字符串
	 */
	public static String getAesKey(int charCount) {
		String charValue = "";
		// 生成随机字母�?
		for (int i = 0; i < charCount; i++) {
			// 键盘上字符产生随机数
			char c = (char) (randomInt(33, 128));
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	/**
	 * 返回[from,to)之间的一个随机整�?
	 * 
	 * @param from
	 *            起始�?
	 * @param to
	 *            结束�?
	 * @return [from,to)之间的一个随机整�?
	 */
	private static int randomInt(int from, int to) {
		// Random r = new Random();
		return from + new Random().nextInt(to - from);
	}

}
