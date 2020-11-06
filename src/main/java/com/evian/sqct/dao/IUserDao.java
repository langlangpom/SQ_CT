package com.evian.sqct.dao;

import com.evian.sqct.bean.user.*;

import java.util.List;
import java.util.Map;

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

	/** 根据极光Id查找用户 */
	List<Map<String, Object>> selectAppMerchantJpush(String regeditId);

	int removeAppMerchantJpush(Integer accountId,String regeditId);

	
	Integer insertAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId,Integer platformId);
	
	Integer updateAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId,Integer platformId);
	
	List<Map<String, Object>> clientAddressSelect(Integer clientId,Integer eid);
	
	List<StaffDTO> Proc_Backstage_staff_select(Integer eid,String shopName,Integer shopId,String phone,Boolean isVendorAccount, Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	EAppMerchantAccountShop selectEAppMerchantAccountShop(Integer accountId,Integer eid,Integer shopId);
	
	/**
	 * 查询该账号在水趣商户是否注册
	 * @param account
	 * @return
	 */
	Map<String, Object> selectEAppMerchantAccount(String account);
	
	List<VendorShopAdministratorDTO> selectVendorShopAdmin(Integer eid,Integer shopId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	EAppMerchantAccountRole selectEAppMerchantAccountRoleByAccountId(Integer accountId);

	EAppMerchantAccountEnterpriseRole selectEAppMerchantAccountEnterpriseRoleByAccountId(Integer accountId);

	String Proc_Backstage_staff_add(Integer shopId,Integer eid,String name,String phone,String picture,String SQ_remark,String SQ_IDCard,String workCard,String healthCard,String creditCard,String SQ_staffNO,Boolean isRelevanceEvian);
	
	/**
	 * 查询该账户微信（小程序、公众号）是否注册了
	 * @return
	 */
	Integer selectWxIsRegister(Integer eid,String account);
	
	Map<String, Object> Proc_Backstage_shop_wechatliteapp_code_select(String shopCode,Integer eid,Integer shopId,String beginTime,String endTime,String managerFullName,String tuikeFullName,String shopName,String eName,Boolean isEnable,String managerAccount,String tuikeAccount,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 推客经理指定推客
	 * @param shopCode
	 * @param managerClientId
	 * @return
	 */
	String Proc_Backstage_shop_wechatliteapp_code_ManagerSetting(String shopCode,Integer managerClientId);

	/**
	 * 企业APP商户端角色对应菜单
	 * @param roleId
	 * @return
	 */
	List<Map<String,Object>> Proc_Backstage_appMerchant_account_enterprise_role_menu_select(Integer roleId);

	/**
	 * 企业APP商户端菜单信息
	 * @param eid
	 * @return
	 */
	List<Map<String,Object>> Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(Integer eid);

	List<Map<String,Object>> selectEServiceUserlistByEid(Integer eid);

	boolean selectOpenShuiqooBusApp(Integer eid);

	Integer updateNotSubscribeUserHeadimgurl_e_weixin_subscribe(String openId,String headimgurl);

	Integer updateNotSubscribeUserHeadimgurl_e_client_wx_photo(String openId,String headimgurl);

	Integer updateNotSubscribeUserHeadimgurl_e_groupbuy_order(String openId,String headimgurl);

	/**
	 * 用户反馈
	 * @param dto
	 * @return
	 */
	Integer saveAppFeedback(AppFeedbackDTO dto);

	/**
	 * 查询客户反馈
	 * @param accountId
	 * @param eid
	 * @return
	 */
	List<AppFeedbackDTO> selectAppFeedback(Integer accountId,Integer eid);

	/**
	 * 查询店铺管理员和接单员
	 * @param shopId
	 * @return
	 */
	List<SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO> selectEAppMerchantAccountAndRegeditIdByShopId(Integer shopId);

	/**
	 * 查询微信模板信息企业员工接收人微信
	 * @param eid
	 * @return
	 */
	List<EEnterpriseMsgWeixin> selectEEnterpriseMsgWeixinByEid(Integer eid);

	List<Map<String,Object>> tempLogin(String account);

	/**
	 * 生成pwd和identityCode
	 * @param clientId
	 * @param passWord
	 * @param identityCode
	 * @return
	 */
	int updateEclient(Integer clientId,String passWord,String identityCode);
}
