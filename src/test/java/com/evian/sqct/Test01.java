package com.evian.sqct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2018年11月7日 下午3:20:10
 * @author XHX
 * @Description 该函数的功能描述
 */
public class Test01 {

	public static void main(String[] args) {
		List<String> a = new ArrayList<String>();
		a.add("ss");
		a.add("dd");
		a.add("ee");
		a.add("qq");
		
		List<Map<String, Object>> f = new ArrayList<Map<String, Object>>();
		/*for (int i =  a.size()-1;i>=0 ; i--) {
			
			Map<String, Object> e = new HashMap<String, Object>();
			e.put("e", a.get(i));
			f.add(e);
			String tt =a.get(i);
			a.remove(tt);
		}
		System.out.println(a);
		System.out.println(f);*/
		for (String string : a) {
			Map<String, Object> e = new HashMap<String, Object>();
			String v = string;
			e.put("e", string);
			f.add(e);
			a.remove(v);
		}
		System.out.println(f);
	}
}
