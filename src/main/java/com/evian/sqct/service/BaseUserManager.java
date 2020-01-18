package com.evian.sqct.service;

import com.evian.sqct.bean.user.*;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseUserManager extends BaseManager{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;

	public Map<String,Object> userLogin(String loginName, String passWord,
			String lastLoginIP) {
		Map<String,Object> userLogin = userDao.userLogin(loginName, passWord,
				lastLoginIP);
		return userLogin;
	}
	
	public Map<String,Object> userLogin_v2(String loginName, String passWord) {
		Map<String,Object> userLogin = userDao.userLogin_v2(loginName, passWord);
		return userLogin;
	}

	public UserModel findUserInfoById(String loginName) {
		UserModel selectUserByNameAndPwd = userDao.selectUserByName(loginName);
		return selectUserByNameAndPwd;
	}

	public boolean saveSMSFindPwd(String cellPhone, String ip) {
		Map<String, Object> stringObjectMap = userDao.selectEAppMerchantAccount(cellPhone);
		if (stringObjectMap != null) {
			return userDao.saveSMSFindPwd(cellPhone, ip);

		} else {
			return false;
		}
	}

	public boolean setSMSFindPwd(String cellPhone, String msgCode, String npwd) {
		return userDao.setSMSFindPwd(cellPhone, msgCode, npwd);
	}
	
	public boolean setSMSFindPwd_v2(String cellPhone, String msgCode, String npwd) {
		return userDao.setSMSFindPwd_v2(cellPhone, msgCode, npwd);
	}
	
	public String getNowDbName(){
		return userDao.getNowDbName();
	}
	
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
                    eclient.setDateLastLogin(new Timestamp(new Date().getTime()));
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
	
	public Map<String, Object> registerVerifyCode(String cellphone, String passWord, String code, String sdkType, String mobileIMEL, String mobileType, String sdkVer, String weixinId, String location, int cityId, String appId, int tokenId, int appType,String regSource) {
		return userDao.registerVerifyCode(cellphone, passWord, code, sdkType, mobileIMEL, mobileType, sdkVer, weixinId, location, cityId, appId, tokenId, appType, regSource);
	}
	
	public Map<String, String> regeditZongSongCode(String source, String account, int clientId, int cityId,int appType,int eid) {
		return userDao.regeditZongSongCode(source, account, clientId, cityId, appType, eid);
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer updateHealthCertificate(Integer accountId,Integer eid,String headImg,String fullname,String healthCertificateImg,String SQ_IDCard) {
		logger.info("[project:{}] [step:enter] [accountId:{}] [eid:{}] [headImg:{}] [fullname:{}] [healthCertificateImg:{}] [SQ_IDCard:{}]",
				new Object[] { WebConfig.projectName, accountId, eid, headImg, fullname,healthCertificateImg,SQ_IDCard});
		return userDao.updateHealthCertificate(accountId, eid, headImg, fullname, healthCertificateImg,SQ_IDCard);
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer updateAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId) {
		logger.info("[project:{}] [step:enter] [accountId:{}] [regeditId:{}] [sourceId:{}] ",
				new Object[] { WebConfig.projectName, accountId, regeditId, sourceId});
		Map<String, Object> selectAppMerchantJpush = userDao.selectAppMerchantJpush(accountId);
		if(selectAppMerchantJpush==null) {
			return userDao.insertAppMerchantJpush(accountId, regeditId, sourceId);
		}else {
			return userDao.updateAppMerchantJpush(accountId, regeditId, sourceId);
		}
		
	}
	
	public List<Map<String, Object>> clientAddressSelect(Integer clientId,Integer eid){
		logger.info("[project:{}] [step:enter] [clientId:{}] [eid:{}] ",
				new Object[] { WebConfig.projectName, clientId, eid});
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
		logger.info("[project:{}] [step:enter] [eid:{}] [shopName:{}] [shopId:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName, eid, shopName,shopId,PageIndex,PageSize,IsSelectAll});
		
		List<StaffDTO> proc_Backstage_staff_select = userDao.Proc_Backstage_staff_select(eid,shopName,shopId,null, PageIndex, PageSize, IsSelectAll);
		for (int i = proc_Backstage_staff_select.size()-1; i >= 0; i--) {
			StaffDTO staffDTO = proc_Backstage_staff_select.get(i);
			EAppMerchantAccountRole role = userDao.selectEAppMerchantAccountRoleByAccountId(staffDTO.getId());
			
			if(role!=null) {
				String userAuthorization = role.getUserAuthorization();
				// 判断是否送水员
				if(!StringUtils.isEmpty(userAuthorization)&&userAuthorization.length()>=4) {
					char charAt = userAuthorization.charAt(3);
					if('1'!=charAt) {
						proc_Backstage_staff_select.remove(i);
					}
				}else {
					proc_Backstage_staff_select.remove(i);
				}
			}else {
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
		logger.info("[project:{}] [step:enter] [eid:{}] [shopId:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName, eid, shopId,PageIndex,PageSize,IsSelectAll});
		return userDao.selectVendorShopAdmin(eid,shopId, PageIndex, PageSize, IsSelectAll);
	}
	
	public String Proc_Backstage_staff_add(Integer shopId,Integer eid,String name,String phone,String picture,String SQ_remark,String SQ_IDCard,String workCard,String healthCard,String creditCard,String SQ_staffNO,Boolean isRelevanceEvian){
		logger.info("[project:{}] [step:enter] [shopId:{}] [eid:{}] [name:{}] [phone:{}] [picture:{}] [SQ_remark:{}] [SQ_IDCard:{}] [workCard:{}] [healthCard:{}] [creditCard:{}] [SQ_staffNO:{}] [isRelevanceEvian:{}]",
				new Object[] { WebConfig.projectName, shopId, eid, name,phone,picture,SQ_remark,SQ_IDCard,workCard,healthCard,creditCard,SQ_staffNO,isRelevanceEvian});
		return userDao.Proc_Backstage_staff_add(shopId, eid, name, phone, picture, SQ_remark, SQ_IDCard, workCard, healthCard, creditCard, SQ_staffNO, isRelevanceEvian);
	}
	
	/**
	 * 查询该账户微信（小程序、公众号）是否注册了
	 * @return
	 */
	public Boolean selectWxIsRegister(Integer eid,String account) {
		logger.info("[project:{}] [step:enter] [eid:{}] [account:{}]",
				new Object[] { WebConfig.projectName, eid, account});
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
		logger.info("[project:{}] [step:enter] [shopCode:{}] [eid:{}] [shopId:{}] [beginTime:{}] [endTime:{}] [managerFullName:{}] [tuikeFullName:{}] [shopName:{}] [eName:{}] [isEnable:{}] [managerAccount:{}] [tuikeAccount:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName, shopCode, eid, shopId,beginTime,endTime,managerFullName,tuikeFullName,shopName,eName,isEnable,managerAccount,tuikeAccount,PageIndex,PageSize,IsSelectAll});
		return userDao.Proc_Backstage_shop_wechatliteapp_code_select(shopCode, eid, shopId, beginTime, endTime, managerFullName, tuikeFullName, shopName, eName, isEnable, managerAccount, tuikeAccount, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 推客经理指定推客
	 * @param shopCode
	 * @param managerClientId
	 * @return
	 */
	public String tuikeManagerAssignTuike(String shopCode,Integer managerClientId) {
		logger.info("[project:{}] [step:enter] [shopCode:{}] [managerClientId:{}]",
				new Object[] { WebConfig.projectName, shopCode, managerClientId});
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
		logger.info("[project:{}] [step:enter] [eid:{}] [phone:{}]",
				new Object[] { WebConfig.projectName, eid, phone});
		
		List<StaffDTO> proc_Backstage_staff_select = userDao.Proc_Backstage_staff_select(eid,null,null,phone,null, null, null);
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
		logger.info("[project:{}] [step:enter] [roleId:{}]",
				new Object[] { WebConfig.projectName, roleId});
		return userDao.Proc_Backstage_appMerchant_account_enterprise_role_menu_select(roleId);
	}

	/**
	 * 企业APP商户端菜单信息
	 * @param eid
	 * @return
	 */
	public List<Map<String,Object>> Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(Integer eid){
		logger.info("[project:{}] [step:enter] [eid:{}]",
				new Object[] { WebConfig.projectName, eid});
		return userDao.Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(eid);
	}

	public List<Map<String, Object>> selectEServiceUserlistByEid(Integer eid) {
		logger.info("[project:{}] [step:enter] [eid:{}]",
				new Object[] { WebConfig.projectName, eid});
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
}
