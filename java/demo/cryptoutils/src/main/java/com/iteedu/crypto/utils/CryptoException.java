package com.iteedu.crypto.utils;

/**
 * 加解密异常
 */
public class CryptoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3468254403608248794L;

	/**
	 * 空构造器
	 */
	public CryptoException() {
		super();
	}

	/**
	 * 构造器
	 */
	public CryptoException(String msg) {
		super(msg);
	}

	/**
	 * 构造器
	 */
	public CryptoException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
