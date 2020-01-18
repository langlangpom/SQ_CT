package com.evian.sqct.bean.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * @date   2019年4月17日 上午11:22:04
 * @author XHX
 * @Description 企业商户号和支付密钥 e_enterprise_wechatliteapp
 */
public class EEnterpriseWechatliteapp implements Serializable{

	
	private static final long serialVersionUID = 4058793502638579424L;

	private String appId;					// appId
	private Integer eid;					//
	private Integer liteappType;			//	1:水趣平台主体小程序;2:企业主体申请公众号小程序.
	private String authorizerRefreshToken;	//	授权刷新令牌（小程序授权给水趣时返回的刷新令牌，并非授权码，当授权码过期时则需要该刷新码来刷新授权码， 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌）
	private Date createTime;				//	
	private String liteappName;				//	名称(在小程序首页中显示的标题)
	private String appSecret;				//	密钥
	private String mchId;					//	支付商户号
	private String partnerKey;				//	支付partnerKey
	private Boolean isValid;				//	是否检验mchId,如果已经检验过则不能修改，仅在appType=2的情况下有效
	private Integer appType;				//	1第一个第三方品台公众号，2第一个第三方品台小程序，3第二个第三方品台公众号，4第二个第三方品台小程序
	private Boolean isAuthorization;		//	是否授权：0取消授权，1授权
	private Integer codeTemplateId;			//	当前正在使用的小程序代码版本id
	private Integer nextTemplateId;			//	正在升级的小程序代码版本id
	private String styleCode;				// 	页面颜色样式
	private String liteappShearPic;			// 	小程序分享图片
	private String liteappShearDesc;		// 	小程序分享描述
	private Boolean isJoinWechatOpen;		//	是否加入腾讯开放平台，默认0为未加入，1为加入，获取用户unionId判断
	private String joinWechatOpenRemark;	//	加入腾讯开放平台 备注
	private String originalId;				//	公众号或小程序原始Id，格式如：gh_db370a7*****，用于客服信息接收人字段
	private Integer componentIndex;			//	第几个第三方平台
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getLiteappType() {
		return liteappType;
	}
	public void setLiteappType(Integer liteappType) {
		this.liteappType = liteappType;
	}
	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}
	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLiteappName() {
		return liteappName;
	}
	public void setLiteappName(String liteappName) {
		this.liteappName = liteappName;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getPartnerKey() {
		return partnerKey;
	}
	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	public Boolean getIsAuthorization() {
		return isAuthorization;
	}
	public void setIsAuthorization(Boolean isAuthorization) {
		this.isAuthorization = isAuthorization;
	}
	public Integer getCodeTemplateId() {
		return codeTemplateId;
	}
	public void setCodeTemplateId(Integer codeTemplateId) {
		this.codeTemplateId = codeTemplateId;
	}
	public Integer getNextTemplateId() {
		return nextTemplateId;
	}
	public void setNextTemplateId(Integer nextTemplateId) {
		this.nextTemplateId = nextTemplateId;
	}
	public String getStyleCode() {
		return styleCode;
	}
	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}
	public String getLiteappShearPic() {
		return liteappShearPic;
	}
	public void setLiteappShearPic(String liteappShearPic) {
		this.liteappShearPic = liteappShearPic;
	}
	public String getLiteappShearDesc() {
		return liteappShearDesc;
	}
	public void setLiteappShearDesc(String liteappShearDesc) {
		this.liteappShearDesc = liteappShearDesc;
	}
	public Boolean getIsJoinWechatOpen() {
		return isJoinWechatOpen;
	}
	public void setIsJoinWechatOpen(Boolean isJoinWechatOpen) {
		this.isJoinWechatOpen = isJoinWechatOpen;
	}
	public String getJoinWechatOpenRemark() {
		return joinWechatOpenRemark;
	}
	public void setJoinWechatOpenRemark(String joinWechatOpenRemark) {
		this.joinWechatOpenRemark = joinWechatOpenRemark;
	}
	public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	public Integer getComponentIndex() {
		return componentIndex;
	}
	public void setComponentIndex(Integer componentIndex) {
		this.componentIndex = componentIndex;
	}
	@Override
	public String toString() {
		return "EEnterpriseWechatliteapp [appId=" + appId + ", eid=" + eid + ", liteappType=" + liteappType
				+ ", authorizerRefreshToken=" + authorizerRefreshToken + ", createTime=" + createTime + ", liteappName="
				+ liteappName + ", appSecret=" + appSecret + ", mchId=" + mchId + ", partnerKey=" + partnerKey
				+ ", isValid=" + isValid + ", appType=" + appType + ", isAuthorization=" + isAuthorization
				+ ", codeTemplateId=" + codeTemplateId + ", nextTemplateId=" + nextTemplateId + ", styleCode="
				+ styleCode + ", liteappShearPic=" + liteappShearPic + ", liteappShearDesc=" + liteappShearDesc
				+ ", isJoinWechatOpen=" + isJoinWechatOpen + ", joinWechatOpenRemark=" + joinWechatOpenRemark
				+ ", originalId=" + originalId + ", componentIndex=" + componentIndex + "]";
	}
}
