package com.evian.sqct.util.DES;
/**
 * 
 * @FileName: MoveEncrypt.java
 * @Package com.evian.mobile.util
 * @Description: 位移加密解密
 * @author EVIAN(PA)
 * @date 2016年9月5日 下午6:21:43
 * @version V3.0
 */
public class MoveEncrypt {
	// 加密移位参照
	private static final String STR_AZ = "0QA1ZW9SX8ED3CR4FV5TG7BYHNU6JMI2KOLP";
	/**
	 * 加密
	 * @param s
	 * @return
	 */
	public static  String encrypt(String s) {
		StringBuffer ss = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char tempi = s.charAt(i);
			for (int j = 0; j < STR_AZ.length(); j++) {
				// 大于最大位后，从初的A取。形成循环
				if (tempi == STR_AZ.charAt(j)) {
					if ((j + 3) >= STR_AZ.length()) {
						int z = j + 3 - STR_AZ.length();
						ss.append(STR_AZ.charAt(z));
					} else {
						ss.append(STR_AZ.charAt(j + 3));
					}
				}
			}
		}
		return ss.toString();
	}

	/**
	 * 	解密
	 */
	public static String decrypt(String s) {
		StringBuffer ssde = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char tempi = s.charAt(i);
			for (int j = 0; j < STR_AZ.length(); j++) {
				if (tempi == STR_AZ.charAt(j)) {
					// 小于最小位后，从最大的Z取，形成循环
					if ((j - 3) < 0) {
						int z = STR_AZ.length() + (j - 3);
						ssde.append(STR_AZ.charAt(z));
					} else {
						ssde.append(STR_AZ.charAt(j - 3));
					}
				}
			}
		}
		return ssde.toString();
	}
}
