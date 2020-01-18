package com.evian.sqct;

import java.lang.reflect.Method;

import com.evian.sqct.bean.order.Order;

/**
 * @date   2019年4月28日 下午8:05:49
 * @author XHX
 * @Description 该函数的功能描述
 */
public class Test02 {

	public static void main(String[] args)  {
		Order o = new Order();
		o.setAccount("asfasfd");
		sss(o);
	}
	
	public static void sss(Object obj) {
		Class a = obj.getClass();
		Method[] methods = a.getMethods();
		for (Method method : methods) {
			// 获取方法名
			String name = method.getName();
//			System.out.println(name);
			// 找出set方法
			if(name.indexOf("set")!=-1) {
				String removeSetName = name.replace("set", "");
				// 参数名字
				String variableName = toLowerCaseFirstOne(removeSetName);
				// get 方法
				String getName = "get"+removeSetName;
				try {
					Method setMethod = a.getMethod(getName);
					// 执行get方法
					Object invoke = setMethod.invoke(obj);
					System.out.println(invoke);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
		}
	}
	
	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
