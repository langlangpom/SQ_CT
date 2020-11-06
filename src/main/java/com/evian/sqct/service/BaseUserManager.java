package com.evian.sqct.service;

import com.alibaba.fastjson.JSON;
import com.evian.sqct.bean.jwt.TokenDTO;
import com.evian.sqct.bean.user.*;
import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.mybatis.primaryDataSource.dao.IUserMapperDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.*;
import com.evian.sqct.util.DESUser.UserDes;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("baseUserManager")
public class BaseUserManager extends BaseManager{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;

	@Autowired
	private ICacheService cacheService;

	@Autowired
	private BaseVendorManager baseVendorManager;

	@Autowired
	private IJsonWebTokenService jwtService;


	@Autowired
	private IUserMapperDao userMapperDao;

	@Autowired
	private IFileUploadService fileUploadService;



	public Map<String,Object> userLogin(String loginName, String passWord,
			String lastLoginIP) {
		Map<String,Object> userLogin = userDao.userLogin(loginName, passWord,
				lastLoginIP);
		return userLogin;
	}
	
	public Map<String,Object> userLogin_v2(String loginName, String passWord) {
		Map<String,Object> userLogin = userDao.userLogin_v2(loginName, passWord);
		if (!"1".equals(userLogin.get("TAG"))) {
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_FAIL_LOGIN);
		}
		Object resultObj = userLogin.get("result");
		if(resultObj!=null){
			UserModel3 result = (UserModel3) resultObj;
			result.setOpenShuiqooBusApp(false);
			Integer eid = result.getEid();
			if(eid!=null){
				boolean openShuiqooBusApp = userDao.selectOpenShuiqooBusApp(eid);
				result.setOpenShuiqooBusApp(openShuiqooBusApp);
			}
		}
		return userLogin;
	}

	public UserModel findUserInfoById(String loginName) {
		UserModel selectUserByNameAndPwd = userDao.selectUserByName(loginName);
		return selectUserByNameAndPwd;
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean saveSMSFindPwd(String cellPhone, String ip) {
		Map<String, Object> stringObjectMap = userDao.selectEAppMerchantAccount(cellPhone);
		if (stringObjectMap != null) {
			return userDao.saveSMSFindPwd(cellPhone, ip);
		} else {
			return false;
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean setSMSFindPwd(String cellPhone, String msgCode, String npwd) {
		return userDao.setSMSFindPwd(cellPhone, msgCode, npwd);
	}

	@Transactional(rollbackFor=Exception.class)
	public boolean setSMSFindPwd_v2(String cellPhone, String msgCode, String npwd) {
		return userDao.setSMSFindPwd_v2(cellPhone, msgCode, npwd);
	}
	
	public String getNowDbName(){
		return userDao.getNowDbName();
	}

	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> clientLogin(String account,String passWord,String appver){
		Map<String, Object> map = new HashMap<String, Object>();
		Eclient eclient = userDao.findUniqueBy(account);
		if (eclient != null && eclient.getClientId() > 0) {
            //比较密码
            if (eclient.getPassWord().equalsIgnoreCase(passWord)) {
                if (eclient.getStatus() == 0) {
                    //用户正常
                    map.put("status", 0);
                    map.put("clientId", eclient.getIdentityCode());
                    map.put("nickName", StringUtils.isEmpty(eclient.getNickName()) ? "" : eclient.getNickName());
                    map.put("photo", StringUtils.isEmpty(eclient.getPhoto()) ? "" : eclient.getPhoto());

                    //更新登录时间、次数
                    eclient.setLoginNumber(eclient.getLoginNumber() + 1);
                    eclient.setDateLastLogin(new Timestamp(System.currentTimeMillis()));
                    eclient.setAppVer(appver);
                    if (StringUtils.isEmpty(eclient.getIdentityCode())) {
                        //生成客户唯一身份码
                        String tickt = DES3_CBCUtil.des3EncodeCBC(String.format("%s|%s|%s", eclient.getClientId(), account, passWord));
                        eclient.setIdentityCode(tickt);
                        map.put("clientId", tickt);
                    }
                    userDao.updateClient(eclient, eclient.getClientId());
                } else if (eclient.getStatus() == 1) {
                    //禁用的用户
                    map.put("status", 1);
                }
            } else {
                //账号或者密码输入错误
                map.put("status", 3);
            }
        } else {
            //用户未注册
            map.put("status", 2);
        }
		
		return null;
	}
	
	/**
     * 用户注册获取验证码
     *
     * @param cellphone
     * @param ip
     * @return
     */
	public String clientRegisterGetCode(String cellphone, String ip, String weixinId, int tokenId) {
		return userDao.clientRegisterGetCode(cellphone, ip, weixinId, tokenId);
	}
	
	public Ecity getCityByCode(Integer baiduCode) {
		return userDao.getCityByCode(baiduCode);
	}

	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> registerVerifyCode(String cellphone, String passWord, String code, String sdkType, String mobileIMEL, String mobileType, String sdkVer, String weixinId, String location, int cityId, String appId, int tokenId, int appType,String regSource) {
		return userDao.registerVerifyCode(cellphone, passWord, code, sdkType, mobileIMEL, mobileType, sdkVer, weixinId, location, cityId, appId, tokenId, appType, regSource);
	}

	@Transactional(rollbackFor=Exception.class)
	public Map<String, String> regeditZongSongCode(String source, String account, int clientId, int cityId,int appType,int eid) {
		return userDao.regeditZongSongCode(source, account, clientId, cityId, appType, eid);
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer updateHealthCertificate(Integer accountId,Integer eid,String headImg,String fullname,String healthCertificateImg,String SQ_IDCard) {
		return userDao.updateHealthCertificate(accountId, eid, headImg, fullname, healthCertificateImg,SQ_IDCard);
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer updateAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId,Integer platformId) {
		/*List<Map<String, Object>> jpushs = userDao.selectAppMerchantJpush(regeditId);
		for (Map<String, Object> account:jpushs) {
			Integer id = (Integer) account.get("accountId");
			if(accountId.intValue()!=id){
				userDao.removeAppMerchantJpush(id,regeditId);
			}
		}*/
		Map<String, Object> selectAppMerchantJpush = userDao.selectAppMerchantJpush(accountId);
		if(selectAppMerchantJpush==null) {
			return userDao.insertAppMerchantJpush(accountId, regeditId, sourceId,platformId);
		}else {
			return userDao.updateAppMerchantJpush(accountId, regeditId, sourceId,platformId);
		}
	}
	
	public List<Map<String, Object>> clientAddressSelect(Integer clientId,Integer eid){
		List<Map<String, Object>> clientAddressSelect = userDao.clientAddressSelect(clientId, eid);
		Iterator<Map<String, Object>> iterator = clientAddressSelect.iterator();
		while (iterator.hasNext()){
			Map<String, Object> next = iterator.next();
			if(next.get("status")!=null) {
				Integer status  = (Integer) next.get("status");
				if(status.intValue()==-2) {
					iterator.remove();
				}
			}
			
		}
		
		return clientAddressSelect;
	}
	
	public List<StaffDTO> Proc_Backstage_staff_select(Integer eid,String shopName,Integer shopId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		List<StaffDTO> proc_Backstage_staff_select = userDao.Proc_Backstage_staff_select(eid,shopName,shopId,null,null, PageIndex, PageSize, IsSelectAll);
		System.out.println(proc_Backstage_staff_select);
		for (int i = proc_Backstage_staff_select.size()-1; i >= 0; i--) {
			StaffDTO staffDTO = proc_Backstage_staff_select.get(i);
			EAppMerchantAccountEnterpriseRole role = userDao.selectEAppMerchantAccountEnterpriseRoleByAccountId(staffDTO.getId());
			if(role==null||!"deliveryStaff".equals(role.getSign())) {
				proc_Backstage_staff_select.remove(i);
			}
		}
		return proc_Backstage_staff_select;
	}
	
	/**
	 * 查询用户
	 * @param account
	 * @return
	 */
	public Integer findUser(String account) {
		Map<String, Object> selectEAppMerchantAccount = userDao.selectEAppMerchantAccount(account);
		if(selectEAppMerchantAccount!=null) {
			Integer accountId = (Integer) selectEAppMerchantAccount.get("accountId");
				return accountId;
		}else {
			return null;
		}
	}
	
	public List<VendorShopAdministratorDTO> selectVendorShopAdmin(Integer eid,Integer shopId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return userDao.selectVendorShopAdmin(eid,shopId, PageIndex, PageSize, IsSelectAll);
	}

	@Transactional(rollbackFor=Exception.class)
	public String Proc_Backstage_staff_add(Integer shopId,Integer eid,String name,String phone,String picture,String SQ_remark,String SQ_IDCard,String workCard,String healthCard,String creditCard,String SQ_staffNO,Boolean isRelevanceEvian){
		return userDao.Proc_Backstage_staff_add(shopId, eid, name, phone, picture, SQ_remark, SQ_IDCard, workCard, healthCard, creditCard, SQ_staffNO, isRelevanceEvian);
	}
	
	/**
	 * 查询该账户微信（小程序、公众号）是否注册了
	 * @return
	 */
	public Boolean selectWxIsRegister(Integer eid,String account) {
		Integer num = userDao.selectWxIsRegister(eid, account);
		if(num>0) {
			return true;
		}
		return false;
	}

	/** 138.水趣推客 获取申请信息  Json */
	public String getShareApplyInfo(String clientId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getShareApplyInfo.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 139.水趣推客 保存开店申请  Json */
	public String saveShareApplyInfo(String clientId, Integer eid, String fullname, String tel, String resource, String ip, String location,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("fullname", fullname));
		params.add(new BasicNameValuePair("tel", tel));
		params.add(new BasicNameValuePair("resource", resource));
		params.add(new BasicNameValuePair("ip", ip));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("authorizer_appid", ""));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/saveShareApplyInfo.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** 152.获取推客经理要审核的推客信息  Json */
	public String getManagerTuiKes(String clientId, String openId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("openId", openId));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getManagerTuiKes.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	
	
	/** 153.微信端审核推客  Json */
	public String auditTuike(String clientId,Integer applyId, Integer eid, Integer status, String remark,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("applyId", applyId.toString()));
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("status", status.toString()));
		params.add(new BasicNameValuePair("remark", remark));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/auditTuike.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	

	/** 154.推客收益相关客户明细  Json */
	public String tuikeEarningsDetail(Integer xgcid,String clientId, Integer month,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("xgcid", xgcid.toString()));
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("month", month.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/tuikeEarningsDetail.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	

	/** 155.保存我的提现账号-银行卡号  Json */
	public String saveEarningBankAccount(String clientId,String bankNo,String bankName ,String openBank ,String openSmallBank,String dealPass, String mappingTel , String identityId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("bankNo", bankNo));
		params.add(new BasicNameValuePair("bankName", bankName));
		params.add(new BasicNameValuePair("openBank", openBank));
		params.add(new BasicNameValuePair("openSmallBank", openSmallBank));
		params.add(new BasicNameValuePair("dealPass", MD5Util.md5(dealPass)));
		params.add(new BasicNameValuePair("mappingTel", mappingTel));
		params.add(new BasicNameValuePair("identityId", identityId));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/saveEarningBankAccount.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** 156.推客获取系统或者企业模板  Json */
	public String getSysEnterPriseTemplate(String clientId,Integer type,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("type", type.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/getSysEnterPriseTemplate.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	

	

	/** 157.根据条码获取码对应信息  Json */
	public String getShopManagerCode(String clientId,Long shopCode,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("shopCode", shopCode.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getShopManagerCode.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/**
	 * 158.根据手机号获取绑定相关信息，验证码
	 *	水趣商户 如果是水趣商户职员 不发验证码直接注册，如果不是水趣商户职员，就要发验证码，让用户手动注册
	 * Json */
	public String getShopRegeditGetCode(String clientId,Integer eid,String cellphone,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("cellphone", cellphone));
		params.add(new BasicNameValuePair("appSource", "ctapp"));		// 2019-09-16单这个接口传 为了知道是水趣商户访问
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getShopRegeditGetCode.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 159.根据手机号验证码保存推客经理绑定  Json */
	public String saveShopManagerCode(String clientId,String openId,String cellphone,Integer existCid,String nickname,String code,Long shopCode,Integer shopId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("openId", openId));
		params.add(new BasicNameValuePair("cellphone", cellphone));
		params.add(new BasicNameValuePair("nickname", nickname));
		params.add(new BasicNameValuePair("existCid", existCid.toString()));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("shopCode", shopCode.toString()));
		params.add(new BasicNameValuePair("shopId", shopId.toString()));
		params.add(new BasicNameValuePair("appSource", "ctapp"));		// 2019-09-16单这个接口传 为了知道是水趣商户访问
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/saveShopManagerCode.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 161.推客经理相关信息  Json */
	public String getClientShareInfo(String clientId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getClientShareInfo.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** 162.推客经理汇总报表  Json */
	public String getShareTotalReport(String clientId,Integer eid,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getShareTotalReport.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 163.店铺编码明细  Json */
	public String getShopCodeRegeditReport(String clientId,Integer eid,String endDate,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("endDate", endDate));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getShopCodeRegeditReport.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 164.店铺推客明细  Json */
	public String getShopClientRegeditReport(String clientId,Integer eid,String endDate,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("endDate", endDate));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shareApply/getShopClientRegeditReport.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 173.店铺推广_我的账户情况  Json */
	public String myLiteAppEarningInfo(String authorizer_appid,String clientId,Integer type) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("type", type.toString()));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/myLiteAppEarningInfo.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 176.用户推客所属公众号,是否界面可跳转  Json */
	public String tuikeBelongEnterprise(String authorizer_appid,String clientId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		params.add(new BasicNameValuePair("clientId", clientId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/enterprise/tuikeBelongEnterprise.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/**
	 * 推客经理下的所有推客码
	 * @param shopCode
	 * @param eid
	 * @param shopId
	 * @param beginTime
	 * @param endTime
	 * @param managerFullName
	 * @param tuikeFullName
	 * @param shopName
	 * @param eName
	 * @param isEnable
	 * @param managerAccount
	 * @param tuikeAccount
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	public Map<String, Object> tuikeManagerCodes(String shopCode,Integer eid,Integer shopId,String beginTime,String endTime,String managerFullName,String tuikeFullName,String shopName,String eName,Boolean isEnable,String managerAccount,String tuikeAccount,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return userDao.Proc_Backstage_shop_wechatliteapp_code_select(shopCode, eid, shopId, beginTime, endTime, managerFullName, tuikeFullName, shopName, eName, isEnable, managerAccount, tuikeAccount, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 推客经理指定推客
	 * @param shopCode
	 * @param managerClientId
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String tuikeManagerAssignTuike(String shopCode,Integer managerClientId) {
		return userDao.Proc_Backstage_shop_wechatliteapp_code_ManagerSetting(shopCode, managerClientId);
	}
	
	/** 14.获取客户地址 json字符串  */
	public String getAddress(String clientId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("authorizer_appid",authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/getAddress.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** 291.企业经营的城市和区域信息 Json */
	public String enterPriceRunCity(String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/enterPriceRunCity.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}	
	

	/** 90.获取客户的地址标签集合  */
	public String getAddressTags(String clientId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("authorizer_appid",authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/getAddressTags.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 149.根据坐标点新增地址V2  */
	public String addAddress_v2(List<BasicNameValuePair> addressParam){
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/addAddress_v2.action", addressParam);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 292.选择城市区域的客户地址新增 Json */
	public String newClientAddrss3(List<BasicNameValuePair> addressParam) {
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/newClientAddrss3.action", addressParam);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** 16.编辑客户地址 Json */
	public String editAddress(List<BasicNameValuePair> addressParam) {
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/editAddress.action", addressParam);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	

	/** 18.删除客户地址  */
	public String deleteAddress(String clientId, Integer did,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("did", did.toString()));
		params.add(new BasicNameValuePair("authorizer_appid",authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/deleteAddress.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** APP商户端推客经理设置自己为推客并生成推客码 */
	public String shareCodeForAppTuiKeManager(Integer eid,String shopCode,Integer managerClientId) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("shopCode", shopCode));
		params.add(new BasicNameValuePair("managerClientId", managerClientId.toString()));
		String webContent = HttpClientUtil.postBackstageApi(UrlManage.getAdminWebUrl() + "/SheqooApi/ShareCodeForAppTuiKeManager", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/** 重新生成二维码 */
	public String freshMakeQrcodePic(Integer eid,String shopCode) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("shopCode", shopCode));
		String webContent = HttpClientUtil.postBackstageApi(UrlManage.getAdminWebUrl() + "/SheqooApi/FreshMakeQrcodePic", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	
	/**
	 * 根据手机号 查询企业职员
	 * @param eid
	 * @param phone
	 * @return
	 */
	public List<StaffDTO> Proc_Backstage_staff_select(Integer eid,String phone){

		List<StaffDTO> proc_Backstage_staff_select = userDao.Proc_Backstage_staff_select(eid,null,null,phone,null,null, null, null);
		return proc_Backstage_staff_select;
	}
	
	/** 123.水趣推客我的客户  Json */
	public String getDevelopClients(String clientId, Integer type, Integer pageIndex,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("type", type.toString()));
		params.add(new BasicNameValuePair("pageIndex", pageIndex.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/getDevelopClients.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	

	/** 17.设置默认客户地址 */
	public String defaultAddress(String clientId, Integer did,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("did", did.toString()));
		params.add(new BasicNameValuePair("authorizer_appid",authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/address/defaultAddress.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/**
	 * 企业APP商户端角色对应菜单
	 * @param roleId
	 * @return
	 */
	public List<Map<String,Object>> Proc_Backstage_appMerchant_account_enterprise_role_menu_select(Integer roleId){
		return userDao.Proc_Backstage_appMerchant_account_enterprise_role_menu_select(roleId);
	}

	/**
	 * 企业APP商户端菜单信息
	 * @param eid
	 * @return
	 */
	public List<Map<String,Object>> Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(Integer eid){
		return userDao.Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(eid);
	}

	public List<Map<String, Object>> selectEServiceUserlistByEid(Integer eid) {
		return userDao.selectEServiceUserlistByEid(eid);
	}

	public EclientDTO searchingAccount(String account){
		EclientDTO edto = new EclientDTO();
		Eclient client = userDao.findUniqueBy(account);
		if(client!=null&&client.getClientId()!=0){
			BeanUtils.copyProperties(client,edto);
		}
		return edto;
	}

	@Transactional(rollbackFor=Exception.class)
	public Integer removeAppMerchantJpush(Integer accountId,String regeditId) {
		return userDao.removeAppMerchantJpush(accountId,regeditId);
	}

	@Transactional(rollbackFor=Exception.class)
	public void updateNotSubscribeUserHeadimgurl(){
		BatchWriteInDataByLog.init("C:\\Users\\XHX\\Desktop\\日志", "log", "未关注用户", true);
		List<String> resultList = BatchWriteInDataByLog.executeInquire();
		int e_weixin_subscribe_count = 0;
		int e_weixin_subscribe_succeed_count = 0;
		int e_client_wx_photo_count = 0;
		int e_client_wx_photo_succeed_count = 0;
		int e_groupbuy_order_count = 0;
		int e_groupbuy_order_succeed_count = 0;
		for (int i = 0; i < resultList.size(); i++) {
			JSONObject json = JSONObject.fromObject(resultList.get(i));
			String openId = json.getString("openid");
			String headimgurl = json.getString("headimgurl");
			Integer integer = userDao.updateNotSubscribeUserHeadimgurl_e_weixin_subscribe(openId, headimgurl);
			e_weixin_subscribe_count++;
			if(integer>0){
				e_weixin_subscribe_succeed_count+=integer;
			}
			Integer integer1 = userDao.updateNotSubscribeUserHeadimgurl_e_client_wx_photo(openId, headimgurl);
			e_client_wx_photo_count++;
			if(integer1>0){
				e_client_wx_photo_succeed_count+=integer1;
			}
			Integer integer2 = userDao.updateNotSubscribeUserHeadimgurl_e_groupbuy_order(openId, headimgurl);
			e_groupbuy_order_count++;
			if(integer2>0){
				e_groupbuy_order_succeed_count+=integer2;
			}
		}
		logger.info("该文件夹执行e_weixin_subscribe表{}条语句，{}次成功,e_client_wx_photo{}条语句，{}次成功,e_groupbuy_order{}条语句，{}次成功"
				, new Object[]{e_weixin_subscribe_count,e_weixin_subscribe_succeed_count
						,e_client_wx_photo_count,e_client_wx_photo_succeed_count,
						e_groupbuy_order_count,e_groupbuy_order_succeed_count});
	}

	/** 138.水趣推客 获取申请信息  Json */
	public String unBoundLogOutShareTuiKe(Integer eid,Integer type,String account,String clientId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("eid", eid.toString()));
		params.add(new BasicNameValuePair("type", type.toString()));
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/unBoundLogOutShareTuiKe.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	/** 174.微信钱包实名认证  Json */
	public String saveEarningWXAutonym(String identityCode,String openId,String autonym,String dealPass,String appId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", identityCode));
		params.add(new BasicNameValuePair("openId", openId));
		params.add(new BasicNameValuePair("autonym", autonym));
		params.add(new BasicNameValuePair("isAutonym", "true"));
		params.add(new BasicNameValuePair("dealPass", dealPass));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/saveEarningWXAutonym.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	/** 175.用户提现到微信钱包  Json */
	public String txToWxWallet(String identityCode,String openId,Double money,String dealPass,String appId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", identityCode));
		params.add(new BasicNameValuePair("openId", openId));
		params.add(new BasicNameValuePair("money", money.toString()));
		params.add(new BasicNameValuePair("dealPass", dealPass));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/txToWxWallet.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}
	/** 104.人人开店_我的账户情况  Json */
	public String myEarningInfo(String identityCode,String appId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", identityCode));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/peopleShare/myEarningInfo.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}

	@Transactional(rollbackFor=Exception.class)
	public Integer saveAppFeedback(AppFeedbackDTO dto, MultipartFile[] feedbackImg) throws IOException {
		StringBuilder pictures = new StringBuilder();
		for (int i = 0; i <feedbackImg.length ; i++) {
			String s = fileUploadService.fileUpload(feedbackImg[i].getBytes());
			pictures.append(s);
			if(i!=feedbackImg.length-1){
				pictures.append(",");
			}
		}
		dto.setPictures(pictures.toString());
		return userDao.saveAppFeedback(dto);
	}

	/**
	 * 查询客户反馈
	 * @param accountId
	 * @param eid
	 * @return
	 */
	public List<AppFeedbackDTO> selectAppFeedback(Integer accountId,Integer eid){
		return userDao.selectAppFeedback(accountId, eid);
	}

	@Async
	public Integer accountChange(Integer accountId, Integer sourceId,TokenDTO token,Integer platformId){
		Integer result = updateAppMerchantJpush(accountId, token.getRegeditId(), sourceId,platformId);
		// 插入并输出挤下线的机器
		List<TokenDTO> tokenDTOs = cacheService.saveLoginAccountToken(accountId, token);
		System.out.println("ttttttttttt = "+tokenDTOs);
		if(tokenDTOs.size()>0){
			for (TokenDTO tokenDTO:tokenDTOs){
				// 通知挤下线的机器
//				baseVendorManager.sendJpushMessage(0, 20001, "下线通知，您的账号已在其他设备登录", "如不是您亲自操作，请及时修改密码。", 0, tokenDTO.getRegeditId(), "");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(new Date());
				JPushShangHuModel model = new JPushShangHuModel(0,"下线通知，您的账号已在其他设备登录","如不是您亲自操作，请及时修改密码。",20001,dateString,sourceId,tokenDTO.getRegeditId(),"","",platformId);
				JpushShangHuService.pushMsg(model);
			}
		}
		return result;
	}

	@Async
	public void logout(Integer accountId,String regeditId){
		cacheService.logoutAccountToken(accountId,regeditId);
	}

	public TokenDTO refreshToken(String refresh_token){

		try {
			Integer accountId = jwtService.getTokenAccountId(refresh_token);
			com.alibaba.fastjson.JSONObject payload = new com.alibaba.fastjson.JSONObject();
			payload.put("accountId",accountId);
			TokenDTO token = jwtService.createToken(payload);
			List<TokenDTO> accountTokens = cacheService.getLoginAccountTokens(accountId);
			if(accountTokens.size()==0){
				throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_REFRESH_TOKEN);
			}
			for (TokenDTO dto: accountTokens){
				if(refresh_token.equals(dto.getRefresh_token())){
					dto.setAccess_token(token.getAccess_token());
					dto.setRefresh_token(token.getRefresh_token());
					token.setRegeditId(dto.getRegeditId());
					cacheService.setLoginAccountTokens(accountId,accountTokens);
					return token;
				}
			}
			logger.error("[refresh_token校验失败:{} accountTokens{}]",refresh_token,accountTokens);
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_REFRESH_TOKEN);
		} catch (ExpiredJwtException e) {
			logger.error("[refresh_token校验失败:{}]",e.getMessage());
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_REFRESH_TOKEN);
		}

	}


	/**
	 * 查询店铺补货员
	 * @return
	 */
	public List<StaffDTO> selectVendorReplenishmentPart(VendorReplenishmentPartSelectDTO dto){
		List<StaffDTO> result = userDao.Proc_Backstage_staff_select(dto.getEid(), null, dto.getShopId(), null, true, dto.getPageIndex(), dto.getPageSize(), dto.getIsSelectAll());

		// 去重
		List<StaffDTO> collect = result.stream()
				.filter(StreamUtil.distinctByKey(s -> s.getId()))
				.collect(Collectors.toList());
		return collect;
	}

	public List<SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO> selectEAppMerchantAccountAndRegeditIdByShopId(Integer shopId){
		return userDao.selectEAppMerchantAccountAndRegeditIdByShopId(shopId);
	}

	/**
	 * 更新账号密码
	 * @param clientId
	 * @param account
	 * @return
	 */
	public int updateEclientDes(Integer clientId,String account){
		// 生成pwd和identityCode
		UserDes userDes = new UserDes(clientId, account);

		return userDao.updateEclient(clientId, userDes.getPassWord(), userDes.getIdentityCode());
	}

	/**
	 * 查看账号
	 * @param account
	 * @return
	 */
	public Map<String, Object> lookAccount(String account){
		return userMapperDao.selectNicknameByAccount(account);
	}

}
