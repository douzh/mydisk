package com.iteedu.crypto.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import net.iharder.Base64;


public class CryptoUtils {

	/**
	 * @param strKey
	 *            ��Կ
	 * @return ��ȫ��Կ
	 * 
	 *         ָ���������key���㷨�������ϵͳ���� SecretKey�������ָ�������ֲ���ϵͳ�����İ�ȫkey��һ�¡�
	 */
	protected static SecretKey getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("��ʼ����Կ�����쳣");
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
