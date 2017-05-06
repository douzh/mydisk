package com.iteedu.crypto.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 * 
 */
public class EnUtils{

    private static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * 私有构造，限制创建
	 */
	private EnUtils() {

	}

	/** RSA公钥加密明文
     * 
     * @param content
     *            待加密明
     * @return 密文
     * @throws CryptoException
     * @throws UnsupportedEncodingException */
    public static String rsaEnc(String content, String pk) throws CryptoException, UnsupportedEncodingException {
        return rsaEnc(content.getBytes(DEFAULT_ENCODING), pk);
	}

	/**
	 * RSA加密
	 * 
	 * @param content
	 * @param pk
	 * @return
	 * @throws CryptoException
	 */
	public static String rsaEnc(byte[] content, String pk) throws CryptoException {
		try {
			InputStream is = new ByteArrayInputStream(pk.getBytes("utf-8"));
			byte[] pubbytes = new byte[pk.length()];
			if (is.read(pubbytes) > 0) {
				KeyFactory keyf = KeyFactory.getInstance("RSA");
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(
                        CryptoUtils.base64Decode(new String(pubbytes, DEFAULT_ENCODING)));

				PublicKey pubkey = keyf.generatePublic(pubX509);
				cipher.init(Cipher.ENCRYPT_MODE, pubkey);
				byte[] cipherText = cipher.doFinal(content);
				// 转换为Base64编码存储，以便于internet传
				return CryptoUtils.base64Encode(cipherText);
			} else {
				throw new CryptoException("RSA加密异常, 公钥为空");
			}
		} catch (Exception e) {
			throw new CryptoException("RSA加密异常", e);
		}
	}

	/**
	 * 加密以String明文输入,String密文输出
	 * 
	 * @param strContent
	 *            待加密字符串
	 * @param strKey
	 *            加密用的Key
	 * @return 加密后转换为base64格式字符
	 * @throws CryptoException
	 */
	public static String aesEnc(String strContent, String strKey) throws CryptoException {
		try {
			return aesEnc(strContent.getBytes("utf-8"), strKey);
		} catch (Exception e) {
			throw new CryptoException("AES加密异常", e);
		}
	}

	/**
	 * AES加密
	 * 
	 * @param byteContent
	 * @param strKey
	 * @return
	 * @throws CryptoException
	 */
	public static String aesEnc(byte[] byteContent, String strKey) throws CryptoException {
		String strMi = "";
		try {
			SecretKey secretKey = CryptoUtils.getKey(strKey);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始
			byte[] result = cipher.doFinal(byteContent);
			strMi = CryptoUtils.base64Encode(result);
		} catch (Exception e) {
			throw new CryptoException("AES加密异常", e);
		}
		return strMi;
	}

	/**
	 * @param charCount
	 *            字符串数
	 * @return 键盘上字符产生数量为charCount的随机字符串
	 */
	public static String getAesKey(int charCount) {
		String charValue = "";
		// 生成随机字母
		for (int i = 0; i < charCount; i++) {
			// 键盘上字符产生随机数
			char c = (char) (randomInt(33, 128));
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	/**
	 * 返回[from,to)之间的一个随机整
	 * 
	 * @param from
	 *            起始
	 * @param to
	 *            结束
	 * @return [from,to)之间的一个随机整
	 */
	private static int randomInt(int from, int to) {
		return from + new Random().nextInt(to - from);
	}

}
