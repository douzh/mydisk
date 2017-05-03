package com.iteedu.crypto.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import net.iharder.Base64;

/**
 * ������Կ˽Կ
 * @author Administrator
 *
 */
public class CreateRsaKey {

	public static void main(String[] args) throws Exception {
		createKeyPairs("d:\\");
	}
	/**
	 * @param path
	 *            �ļ�·��
	 * @throws Exception
	 * 
	 *             ����rsa ��˽Կ������base64����洢���ļ�ϵͳ
	 */
	public static void createKeyPairs(String path) throws Exception {
		// create the keys
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();
		PublicKey pubKey = pair.getPublic();
		PrivateKey privKey = pair.getPrivate();
		byte[] pk = pubKey.getEncoded();
		byte[] privk = privKey.getEncoded();
		// base64���룬���������ַ�
		String strpk = new String(Base64.encodeBytes(pk));
		String strprivk = new String(Base64.encodeBytes(privk));

		// ���˽Կ�ļ�
		File priKeyfile = new File(path + "rsa_pri_key.pem");

		FileOutputStream out = new FileOutputStream(priKeyfile);

		out.write(strprivk.getBytes());
		out.close();

		// �����Կ�ļ�
		File pubKeyfile = new File(path + "rsa_pub_key.pem");

		FileOutputStream outPub = new FileOutputStream(pubKeyfile);

		outPub.write(strpk.getBytes());

		outPub.close();
	}
}
