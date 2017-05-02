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
 * 创建公钥私钥
 * @author Administrator
 *
 */
public class CreateRsaKey {

	public static void main(String[] args) throws Exception {
		createKeyPairs("d:\\");
	}
	/**
	 * @param path
	 *            文件路径
	 * @throws Exception
	 * 
	 *             生成rsa 公私钥，加密base64编码存储到文件系统
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
		// base64编码，屏蔽特殊字符
		String strpk = new String(Base64.encodeBytes(pk));
		String strprivk = new String(Base64.encodeBytes(privk));

		// 输出私钥文件
		File priKeyfile = new File(path + "rsa_pri_key.pem");

		FileOutputStream out = new FileOutputStream(priKeyfile);

		out.write(strprivk.getBytes());
		out.close();

		// 输出公钥文件
		File pubKeyfile = new File(path + "rsa_pub_key.pem");

		FileOutputStream outPub = new FileOutputStream(pubKeyfile);

		outPub.write(strpk.getBytes());

		outPub.close();
	}
}
