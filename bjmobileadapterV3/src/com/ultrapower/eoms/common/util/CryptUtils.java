package com.ultrapower.eoms.common.util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptUtils {
	// 设置密钥,最少为8位
	private final static byte[] DES_KEY = { 1, 9, 7, 9, 1, 1, 2, 4 };
	// 设置向量,必须为8位
	private final static byte[] DES_IV = { 1, 9, 8, 0, 0, 6, 1, 4 };

	private static BASE64Encoder base64Encoder = new BASE64Encoder();
	private static BASE64Decoder base64Decoder = new BASE64Decoder();

	private static Cipher getCrypto(byte[] bKey, byte[] bIv, int mode) {
		// 设置密钥参数
		DESKeySpec keySpec;
		Cipher cipher = null;
		try {
			keySpec = new DESKeySpec(bKey);
			// 加密算法的参数接口，IvParameterSpec是它的一个实现
			// 设置向量
			AlgorithmParameterSpec iv = new IvParameterSpec(bIv);
			// 获得密钥工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 得到密钥对象
			Key key = keyFactory.generateSecret(keySpec);
			// 得到加密对象Cipher
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// 设置工作模式为加密模式，给出密钥和向量
			cipher.init(mode, key, iv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return cipher;
	}

	/**
	 * 加密明文
	 * 
	 * @param data
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		Cipher cipher = getCrypto(DES_KEY, DES_IV, Cipher.ENCRYPT_MODE);
		byte[] pasByte = cipher.doFinal(data.getBytes("utf-8"));
		return base64Encoder.encode(pasByte);
	}

	/**
	 * 解密密文
	 * 
	 * @param data
	 *            密文
	 * @return 明文
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {
		Cipher cipher = getCrypto(DES_KEY, DES_IV, Cipher.DECRYPT_MODE);
		byte[] pasByte = cipher.doFinal(base64Decoder.decodeBuffer(data));
		return new String(pasByte, "UTF-8");
	}

	/**
	 * 将明文按base64进行编码
	 * 
	 * @param data
	 *            明文
	 * @return 将明文按base64进行编码后的密文
	 */
	public static String base64Encode(String data) {
		if (data == null) {
			return "";
		}
		return base64Encoder.encode(data.getBytes());
	}

	/**
	 * 将明文按base64进行编码的密文转换为明文
	 * 
	 * @param data
	 *            base64密文
	 * @return 明文
	 */
	public static String base64Decode(String data) {
		if (data == null) {
			return "";
		}
		byte[] dByte = null;
		try {
			dByte = base64Decoder.decodeBuffer(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(dByte);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String eTxt = CryptUtils.encode("http://www.ultrapower.com");
		System.out.println(eTxt);
		String dTxt = CryptUtils.decode(eTxt);
		System.out.println(dTxt);
		eTxt = CryptUtils.base64Encode("北京神州泰岳软件有限公司");
		System.out.println("Base64Encode=" + eTxt);
		dTxt = CryptUtils.base64Decode(eTxt);
		System.out.println("Base64Decode=" + dTxt);
	}
}
