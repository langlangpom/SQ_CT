package com.evian.sqct;

import com.evian.sqct.util.DES.EvianHelp_DES;

/**
 * @date   2019年6月12日 下午2:07:44
 * @author XHX
 * @Description 该函数的功能描述
 */
public class Test03 {
	public static void main(String[] args) {
		String decrypt_move = EvianHelp_DES.decrypt_move("971C4N11N115H11CTW37I1CH8H3339H1TLCHT8I71FHW87L911IWFHF8N418NHN97FWNLW9RILL1L8C311H7WI1NFHNHR4LF8H1TFW8WC7RLFWTR5N4I8CC999NW8CT39313F7CN999LNHTF", EvianHelp_DES.java_net_key,true);
		System.out.println(decrypt_move);
		decrypt_move = EvianHelp_DES.decrypt_move("971C4N11N115H11CTW37I1CH8H3339H1TLCHT8I71FHW87L911IWFHF8N418NHN97FWNLW9RILL1L8C311H7WI1NFHNHR4LF8H1TFW8WC7RLFWTR5N4I8CC999NW8CT313T7H9T894RW81THHRCN118RN5NNH45N", EvianHelp_DES.java_net_key,true);
		System.out.println(decrypt_move);
		String encrypt_move = EvianHelp_DES.encrypt_move("test2",EvianHelp_DES.java_net_key,true);
		System.out.println(encrypt_move);
		encrypt_move = EvianHelp_DES.encrypt_move("JKSDHCCIE_1235qauid&_%()_",EvianHelp_DES.java_net_key,true);
		System.out.println(encrypt_move);
		decrypt_move = EvianHelp_DES.decrypt_move(encrypt_move, EvianHelp_DES.java_net_key,true);
		System.out.println(decrypt_move);
	}

}
