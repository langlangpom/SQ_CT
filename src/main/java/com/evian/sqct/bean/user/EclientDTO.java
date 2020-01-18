package com.evian.sqct.bean.user;

import java.io.Serializable;

/**
 * @date   2018年11月22日 下午1:01:01
 * @author XHX
 * @Description 用户表DTO
 */
public class EclientDTO implements Serializable{

	private static final long serialVersionUID = 443938452527559567L;

	private Integer clientId;  //客户ID
    private String account; //账号
    private String nickName;//昵称
    private String photo; //头像
	private String identityCode; //唯一身份验证码
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	@Override
	public String toString() {
		return "EclientDTO [" +
				"clientId=" + clientId +
				", account=" + account +
				", nickName=" + nickName +
				", photo=" + photo +
				", identityCode=" + identityCode +
				']';
	}
}
