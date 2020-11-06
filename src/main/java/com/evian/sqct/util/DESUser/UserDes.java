package com.evian.sqct.util.DESUser;


import com.evian.sqct.util.DES3_CBCUtil;
import com.evian.sqct.util.MD5Util;

import java.io.Serializable;

/**
 * 水趣 用户信息导出初始密码，身份ID DES加解密
 */
public class UserDes implements Serializable {

	private static final long serialVersionUID = 7499599759466619149L;
	private int clientId;
	private String passWord;
	private String identityCode;

	/**
	 *
	 * @param clientId:客户ID(int)
	 * @param account:用户账号(注册手机号)(String)
	 * @return
	 */
	public UserDes(int clientId, String account){
		this.clientId = clientId;
		String passWord = String
				.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		String passWordDes = DES3_CBCUtil.des3EncodeCBC(MD5Util
				.convertMD5(passWord));
		this.passWord = passWordDes;
		this.identityCode = DES3_CBCUtil.des3EncodeCBC(clientId
				+ "|"
				+ account
				+ "|"
				+ passWordDes);
	}


	public int getClientId() {
		return clientId;
	}


	public String getPassWord() {
		return passWord;
	}


	public String getIdentityCode() {
		return identityCode;
	}


}
