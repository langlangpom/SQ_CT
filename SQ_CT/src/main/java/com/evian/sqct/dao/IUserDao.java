package com.evian.sqct.dao;

import java.util.List;
import java.util.Map;

import com.evian.sqct.bean.user.Ecity;
import com.evian.sqct.bean.user.Eclient;
import com.evian.sqct.bean.user.UserModel;

public interface IUserDao {

	public Map<String,Object> userLogin(String loginName,String passWord,String lastLoginIP);
	
	public Map<String,Object> userLogin_v2(String loginName,String passWord);
	
	public UserModel selectUserByName(String loginName);
	
	/**
	 * 忘记密码 发送短信
	 * @return
	 */
	public boolean saveSMSFindPwd(String cellPhone,String ip);
	
	public boolean setSMSFindPwd(String cellPhone,String msgCode,String npwd);
	
	public boolean setSMSFindPwd_v2(String cellPhone,String msgCode,String npwd);
	
	public String getNowDbName();
	
	/**
	 * 查询用户
	 * @param account
	 * @return
	 */
	public Eclient findUniqueBy(String account);
	
	public Eclient getById(Integer clientId);
	
	/**
	 * 更改用户登录信息 例如登录次数
	 * @param eclient
	 * @param clientId
	 * @return
	 */
	public Boolean updateClient(Eclient eclient, int clientId);
	
	/**
     * 用户注册获取验证码
     *
     * @param cellphone
     * @param ip
     * @return
     */
	public String clientRegisterGetCode(String cellphone, String ip, String weixinId, int tokenId);
	
	public Ecity getCityByCode(Integer baiduCode);
	
	public Map<String, Object> registerVerifyCode(String cellphone, String passWord, String code, String sdkType, String mobileIMEL, String mobileType, String sdkVer, String weixinId, String location, int cityId, String appId, int tokenId, int appType,String regSource) ;
	
	public Map<String, String> regeditZongSongCode(String source, String account, int clientId, int cityId,int appType,int eid) ;
	
	public Integer updateHealthCertificate(Integer accountId,Integer eid,String headImg,String fullname,String healthCertificateImg,String SQ_IDCard);
	
	Map<String, Object> selectAppMerchantJpush(Integer accountId);
	
	Integer insertAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId);
	
	Integer updateAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId);
	
	List<Map<String, Object>> clientAddressSelect(Integer clientId,Integer eid);
}
