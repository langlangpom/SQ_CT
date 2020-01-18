package com.evian.sqct;

import java.math.BigDecimal;
import java.util.Random;

import com.evian.sqct.util.DES.EvianHelp_DES;

/**
 * @date   2018年11月6日 上午11:05:55
 * @author XHX
 * @Description 该函数的功能描述
 */
public class Test {

	public static void main(String[] args) {
		/*String decrypt_move = EvianHelp_DES.decrypt_move("971C4N11N115H11CTW37I1CH8H3339H1TLCHT8I71FHW87L911IWFHF8N418NHN97FWNLW9RILL1L8C311H7WI1NFHNHR4LF8H1TFW8WC7RLFWTR5N4I8CC999NW8CT313T7H9T894RW81THHRCN118RN5NNH45N", EvianHelp_DES.java_net_key, true);
		System.out.println(decrypt_move);*/
		/*String a = "";
		Integer.parseInt(a);*/
		
		/*String b = "谢海鑫";
		System.out.println(b.length());*/
		/*long doubleToLongBits = Double.doubleToLongBits(1.1);
		long doubleToLongBits2 = Double.doubleToLongBits(1.1);
		System.out.println(doubleToLongBits==doubleToLongBits2);
		Double b = 0.01;
		Double c = 0.01;
		System.out.println(b.toString().equals(c.toString()));*/
//		System.out.println(7.0%3.0);
		Double d = 1.12;
		Double c = 1.01;
		Double b = 2.13;
		System.out.println(d+c==b.intValue());
		BigDecimal db = new BigDecimal(Double.toString(d));
		BigDecimal cb = new BigDecimal(Double.toString(c));
		BigDecimal bb = new BigDecimal(Double.toString(b));
		BigDecimal dbaddcb = db.add(cb);
		System.out.println(dbaddcb==bb);
		System.out.println(dbaddcb.intValue());
		System.out.println(dbaddcb.compareTo(bb));
		Random ran=new Random();
		int a=ran.nextInt(99999999);
		int g=ran.nextInt(99999999);
		long l=a*1000000000L+g;
		System.out.println(l);
	}
}
