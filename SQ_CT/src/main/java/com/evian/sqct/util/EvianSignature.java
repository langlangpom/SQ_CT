/**
 * 
 */
package com.evian.sqct.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @FileName: EvianSignature.java
 * @Package com.evian.mobile.util
 * @Description: URL签名算法
 * @author EVIAN(PA)
 * @date 2016年4月5日 下午6:21:43
 * @version V3.0
 */
public class EvianSignature {
	public static final byte[] signKey = { 53, 102, 122, 50, 87, 112, 80, 49,
			113, 110, 51, 90, 120, 100, 52, 90, 111, 120, 100, 50, 118, 113,
			43, 102, 115, 65, 65, 115, 121, 52, 121, 43, 118, 120, 51, 89, 110,
			56, 98, 99, 78, 121, 87, 79, 68, 112, 66, 116, 122, 112, 103, 49,
			110, 100, 54, 90, 50, 103, 103, 80 };
	
	public static void main(String[] args) {
		System.out.println(new String(EvianSignature.signKey));
	}

	/**
	 * 签名生成算法
	 * 
	 * @param HashMap
	 *            <String,String> params请求参数集，所有参数必须已转换为字符串类型
	 * @param Stringsecret
	 *            签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(Map<String, String> params, String secret)
			throws IOException {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();

		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Entry<String, String> param : entrys) {
			// 过滤掉没有参数值的键值对
			if (!param.getKey().equalsIgnoreCase("sign")
					&& StringUtils.isEmpty(param.getKey()) == false
					&& StringUtils.isEmpty(param.getValue()) == false) {
				basestring.append(param.getKey()).append("=")
						.append(param.getValue());
			}
		}
		basestring.append(secret);

		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}

		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString().toUpperCase();
	}
}
