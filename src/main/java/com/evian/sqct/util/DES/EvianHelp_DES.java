package com.evian.sqct.util.DES;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Program Name: com.evian.util.EvianHelp_DES.java
 * @author Pa
 * @Creation Date: 2011-11-25 下午04:53:34
 * @version v1.00
 * @Description : DES加密,解密(该类与.NET的加密文进行比较可能会不一样，但是解密后一样; 因此要进行密文比较的只能是数字或者字母)
 * 
 */
public class EvianHelp_DES {
	private static final Logger logger = LoggerFactory.getLogger(EvianHelp_DES.class);

	public static final byte[] CLIENT_SIGN = { 53, 74, 56, 100, 116, 83, 109,
			50 };
	public static final String java_net_key = "1a2c9g8f";

	private static String evian_decrypt(String message, String key) {
		try {
			byte[] bytesrc = convertHexString(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:EvianHelp_DES] [method:evian_decrypt] [ex:{}]",
					new Object[] { WebConfig.projectName, e });
		}
		return null;
	}

	private static byte[] evian_encrypt(String message, String key) {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			return cipher.doFinal(message.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:EvianHelp_DES] [method:evian_encrypt] [ex:{}]",
					new Object[] { WebConfig.projectName, e });
		}
		return null;
	}

	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	/**
	 * 解密数据(不区分大小写)
	 * 
	 * @param message密文
	 * @param key密钥
	 * @return 明文(失败返回:null)
	 */
	public static String decrypt(String message, String key) {
		try {
			String d = evian_decrypt(message, key);
			if (d != null)
				return java.net.URLDecoder.decode(d, "utf-8");
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:EvianHelp_DES] [method:decrypt] [ex:{}]",
					new Object[] { WebConfig.projectName, e });
		}
		return null;
	}

	/**
	 * 加密数据
	 * 
	 * @param message明文
	 * @param key密钥
	 * @return 密文(失败返回:null)
	 */
	public static String encrypt(String message, String key) {
		try {
			byte[] e = evian_encrypt(
					java.net.URLEncoder.encode(message, "utf-8"), key);
			if (e != null)
				return toHexString(e).toUpperCase();
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:EvianHelp_DES] [method:encrypt] [ex:{}]",
					new Object[] { WebConfig.projectName, e });
		}
		return null;
	}
	
	/**
	 * 解密数据(位移)
	 * 
	 * @param message密文
	 * @param key密钥
	 * @return 明文(失败返回:null)
	 */
	public static String decrypt_move(String message, String key,boolean move) {
		try {
			String d = evian_decrypt(MoveEncrypt.decrypt(message), key);
			if (d != null)
				return java.net.URLDecoder.decode(d, "utf-8");
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:EvianHelp_DES] [method:decrypt] [ex:{}]",
					new Object[] { WebConfig.projectName, e });
		}
		return null;
	}

	/**
	 * 加密数据(位移)
	 * 
	 * @param message明文
	 * @param key密钥
	 * @return 密文(失败返回:null)
	 */
	public static String encrypt_move(String message, String key,boolean move) {
		try {
			byte[] e = evian_encrypt(
					java.net.URLEncoder.encode(message, "utf-8"), key);
			if (e != null)
				return MoveEncrypt.encrypt(toHexString(e).toUpperCase());
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:EvianHelp_DES] [method:encrypt] [ex:{}]",
					new Object[] { WebConfig.projectName, e });
		}
		return null;
	}
}
