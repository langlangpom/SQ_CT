package com.evian.sqct;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date   2019年6月24日 下午2:14:35
 * @author XHX
 * @Description 该函数的功能描述
 */
public class Test04 {

	public static void main(String[] args) {
		/*Set<String> s = new HashSet<>();
		s.add("ddddd");
		boolean contains = s.contains("ddddd");
		System.out.println(contains);
		s.remove("ddddd");
		contains = s.contains("ddddd");
		s.remove("ddddd");
		System.out.println(contains);*/
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		System.out.println(dateString);
	}
}
