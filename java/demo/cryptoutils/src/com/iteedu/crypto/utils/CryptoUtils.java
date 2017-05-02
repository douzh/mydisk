package com.iteedu.crypto.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import net.iharder.Base64;


public class CryptoUtils {

	/**
	 * @param strKey
	 *            密钥
	 * @return 安全密钥
	 * 
	 *         指定具体产生key的算法，跨操作系统产生 SecretKey，如果不指定，各种操作系统产生的安全key不一致。
	 */
	protected static SecretKey getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("初始化密钥出现异常");
		}
	}
	
	/**
	 * 
	 * @param content
	 * @return
	 * @throws IOException 
	 */
	protected static byte[] base64Decode(String content) throws IOException{
		return Base64.decode(content);
	}
	
	/**
	 * @param bytes
	 * @return
	 */
	protected static String base64Encode(byte[] bytes){
		return Base64.encodeBytes(bytes);
	}
}
