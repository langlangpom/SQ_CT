package com.evian.sqct.api.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.user.*;
import com.evian.sqct.service.BaseUserManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.DES.WebConfig;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2018年9月29日 下午3:47:05
 * @author XHX
 * @Description 该函数的功能描述
 */
@RestController
@RequestMapping("/evian/sqct/user")
public class UserAction extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseUserManager baseUserManager;
	
	/**
	 * 商户登录
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("backLogin")
	public Map<String, Object> backLogin(HttpServletRequest request ,HttpServletResponse response ,String account,String passWord) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account==null||passWord==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String,Object> userLogin = baseUserManager.userLogin(account, passWord,getIp(request));
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if ("1".equals(userLogin.get("TAG"))) {
				UserModel2 userModel = (UserModel2) userLogin.get("result");
				resultMap.put("nickName", userModel.getName());
				resultMap.put("photo", userModel.getPicture());
				resultMap.put("userId", userModel.getId());
				resultMap.put("eid", userModel.getEid());
				resultMap.put("userAuthorization",
						userModel.getUserAuthorization());
				parMap.put("data", resultMap);
			} else {
				int code = Constants.CODE_FAIL_LOGIN;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			}
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 商户登录
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("backLogin_v2.action")
	public Map<String, Object> backLogin_v2(HttpServletRequest request ,HttpServletResponse response ,String account,String passWord) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account==null||passWord==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> userLogin = baseUserManager.userLogin_v2(account, passWord);
		if ("1".equals(userLogin.get("TAG"))) {
			UserModel3 userModel = (UserModel3) userLogin.get("result");
			// 返回sign=superAdmin或者shopAdmin时，无需判断权限
			String sign = userModel.getSign();
			if(!("superAdmin".equals(sign)||"shopAdmin".equals(sign))){
				Integer roleId = userModel.getRoleId();
				List<Map<String, Object>> roleMenus = baseUserManager.Proc_Backstage_appMerchant_account_enterprise_role_menu_select(roleId);
				result.put("roleMenus",roleMenus);
			}else{
				result.put("roleMenus",new ArrayList<>());
			}

			List<Map<String, Object>> enterpriseMenus = baseUserManager.Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(userModel.getEid());
			result.put("enterpriseMenus",enterpriseMenus==null?new ArrayList<>():enterpriseMenus);

			result.put("userInfo",userModel);

		}else {
			int code = Constants.CODE_FAIL_LOGIN;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		parMap.put("data", result);

		return parMap;
	}
	
	/**
	 * 2.商户找回密码获取验证码
	 * 
	 * @param cellphone
	 *            手机号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("retrievePwd.action")
	public Map<String, Object> retrievePwd(String cellPhone,String ip) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(cellPhone==null||ip==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			boolean isSuccess = baseUserManager.saveSMSFindPwd(cellPhone, ip);
			if (!isSuccess) {
				int code = Constants.CODE_NO_DENTIFYING_CODE;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			} 
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 3.商户登录
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("identifyingCode.action")
	public Map<String, Object> identifyingCode(String cellPhone,String msgCode,String npwd) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(cellPhone==null||msgCode==null||npwd==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			boolean isSuccess = baseUserManager.setSMSFindPwd(cellPhone,msgCode, npwd);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (isSuccess) {
				resultMap.put("success", isSuccess);
				parMap.put("data", resultMap);
			} else {
				int code = Constants.CODE_ERROR_UPDATEPASS;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			}
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 3.商户登录
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("identifyingCode_v2.action")
	public Map<String, Object> identifyingCode_v2(String cellPhone,String msgCode,String npwd) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(cellPhone==null||msgCode==null||npwd==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			boolean isSuccess = baseUserManager.setSMSFindPwd_v2(cellPhone,msgCode, npwd);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (isSuccess) {
				resultMap.put("success", isSuccess);
				parMap.put("data", resultMap);
			} else {
				int code = Constants.CODE_ERROR_UPDATEPASS;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			}
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 测试 获取数据库名
	 * 
	 */
	@RequestMapping("getNowDbName.action")
	public Map<String, Object> getNowDbName() throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			String nowDbName = baseUserManager.getNowDbName();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("success", nowDbName);
			parMap.put("data", resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 63.用户普通登录验证
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login.action")
	public Map<String, Object> login(HttpServletRequest request ,HttpServletResponse response ,String account,String passWord) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account==null||passWord==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			//进行验证码验证
			Map<String,Object> hashout = baseUserManager.clientLogin(account, passWord,"");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (hashout.containsKey("status")) {
				int status = (Integer) hashout.get("status");
				int code = Constants.CODE_SUC;
                switch (status) {
                    case 0:
                        resultMap.put("clientId", hashout.get("clientId").toString());
                        resultMap.put("nickName", hashout.get("nickName").toString());
                        resultMap.put("photo", hashout.get("photo").toString());
                        parMap.put("data", resultMap);
                        break;
                    case 1:
                        code = Constants.CODE_ERROR_CLIENT_ENABLE;
                        break;
                    case 2:
                        code = Constants.CODE_ERROR_NO_REGISTER;
                        break;
                    case 3:
                        code = Constants.CODE_ERROR_LOGIN_PARAM;
                        break;
                    default:
                        code = Constants.CODE_ERROR_LOGIN_PARAM;
                        break;
                }
                String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			} else {
				int code = Constants.CODE_ERROR_SYSTEM;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			}
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 61.用户注册获取验证码
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCode.action")
	public Map<String, Object> getCode(HttpServletRequest request ,HttpServletResponse response ,String cellphone,String weixinId) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(cellphone==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			//进行验证码验证
			String getCode = baseUserManager.clientRegisterGetCode(cellphone, getIp(request), "", 0);
			int code = Constants.CODE_SUC;
			
			switch (getCode) {
			case "E00000":
				break;
			case "C00001":
				// 手机号码已被注册
				code = Constants.CODE_ERROR_CELL_PHONE_NUMBER_ALREADY_IN_USE;
				break;
			case "C00003":
				// 操作频繁,稍后再试
				code = Constants.CODE_OPERATION_OFTEN;
				break;
			case "C00040":
				// 微信ID已被绑定
				code = Constants.CODE_WECHAT_ID_ALREADY_BINDING;
				break;
			default:
				code = Constants.CODE_ERROR_SYSTEM;
				break;
			}
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 62.验证验证码,且生成账号
	 * 
	 * @param account
	 *            用户名
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("verifyCode.action")
	public Map<String, Object> verifyCode(HttpServletRequest request ,HttpServletResponse response ,String cellphone,String code,String passWord,String sdkType,String mobileIMEL,String mobileType,String sdkVer,Integer cityId,Integer citycode,String weixinId,String location,String regSource,String equipment,String authorizer_appid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(cellphone==null||code==null||passWord==null||cityId==null||citycode==null) {
			int code1 = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code1);
			parMap.put("code", code1);
			parMap.put("message", message);
			return parMap;
		}
		try {

			if(weixinId==null) {
				weixinId = "";
			}
			if(authorizer_appid ==null) {
				authorizer_appid = "";
			}
			//根据坐标查出cityId
            if (cityId == 0) {
                if (citycode > 0) {
                    Ecity ecity = baseUserManager.getCityByCode(citycode);
                    if (ecity != null) {
                        cityId = ecity.getCityId();
                    }
                } else {
                    /*if (location!=null&&location.length() > 5 && location.indexOf(",") > 2) {
                        //反解析地址
                        CityParse cityM = BaiDuPortAnalysis.getCityParse(params.get("location"));
                        if (cityM != null) {
                            Ecity ecity = baseCityManager.getCityByCodeDao(cityM.getCityCode());
                            if (ecity != null) {
                                cityId = ecity.getCityId();
                            }
                        }
                    }*/
                }
            }
			
            //1:公众号 2:小程序 3:APP
            int apptype = 3;
            if (equipment!=null) {
                if (equipment.equals("wxLiteapp")) {
                    apptype = 2;
                } else if (equipment.equals("wx")) {
                    apptype = 1;
                }
            }
            
            if(regSource==null) {
            	regSource = "微信关注";
            }
            Map<String, Object> hashout = baseUserManager.registerVerifyCode(cellphone, passWord, code, sdkType, mobileIMEL, mobileType, sdkVer, weixinId, location, cityId, authorizer_appid==null?"":authorizer_appid, 0, apptype, regSource);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (hashout.size() > 0) {
                String code2 = hashout.get("result").toString();
                //生成身份，返回数据
                if ("E00000".equals(code2)) {
                    resultMap.put("clientId", hashout.get("clientId"));
                    //客户ID
                    Integer.valueOf(hashout.get("cId").toString());
                    /*if (cityId > 0) {
                        //注册赠送优惠券
                        Map<String, String> imaMap = baseUserManager.regeditZongSongCodeDao(equipment, cellphone, cid, cityId, apptype, 0);
                        logger.info("[用户注册赠送优惠券: 手机号:{} 客户ID:{} 城市ID:{} 企业ID:{} 结果:{}]", new Object[]{params.get("cellphone"), cid, cityId, tokenId, imaMap});
                        if (imaMap.containsKey("TAG") && imaMap.get("TAG").equals("1")) {
                            resultMap.put("typeName", imaMap.get("typeName").toString());
                        }
                    }*/
                }
            } else {
    			int code1 = Constants.CODE_ERROR_SYSTEM;
    			String message = Constants.getCodeValue(code1);
    			parMap.put("code", code1);
    			parMap.put("message", message);
            }
            
            parMap.put("data", resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code1 = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code1);
			parMap.put("code", code1);
			parMap.put("message", message);
		}
		return parMap;
	}

	/**
	 * 提交/修改健康证
	 * @param accountId				用户 Id
	 * @param eid					企业 id
	 * @param headImg				头像
	 * @param fullname				姓名
	 * @param healthCertificateImg	身份证号
	 * @param SQ_IDCard				健康证号
	 * @return
	 */
	@RequestMapping("updateHealthCertificate.action")
	 public Map<String, Object> updateHealthCertificate(Integer accountId,Integer eid,String headImg,String fullname,String healthCertificateImg,String SQ_IDCard){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null||eid==null||headImg==null||fullname==null||healthCertificateImg==null||SQ_IDCard==null) {
			int code1 = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code1);
			parMap.put("code", code1);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String upResult = QiniuFileSystemUtil.uploadShearPic(headImg);
			if (!StringUtils.isEmpty(upResult)) {
				JSONObject ject = JSON.parseObject(upResult);
				if (!StringUtils.isEmpty((String) ject.get("hash"))
						&& !StringUtils.isEmpty((String) ject.get("key"))) {

					upResult = QiniuConfig.namespace + (String) ject.get("key");
				}
			}
			String upResult2 = QiniuFileSystemUtil.uploadShearPic(healthCertificateImg);
			if (!StringUtils.isEmpty(upResult2)) {
				JSONObject ject = JSON.parseObject(upResult2);
				if (!StringUtils.isEmpty((String) ject.get("hash"))
						&& !StringUtils.isEmpty((String) ject.get("key"))) {

					upResult2 = QiniuConfig.namespace + (String) ject.get("key");
				}
			}
			Integer updateHealthCertificate = baseUserManager.updateHealthCertificate(accountId, eid, upResult, fullname,  upResult2,SQ_IDCard);
			if(updateHealthCertificate.intValue()!=1) {
				logger.error("[project:{}] [updateHealthCertificate:{}]", new Object[] {
						WebConfig.projectName, updateHealthCertificate });
				// 修改失败
				int code1 = Constants.CODE_ERROR_UPDATE;
				String message = Constants.getCodeValue(code1);
				parMap.put("code", code1);
				parMap.put("message", message);
			}
			
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code1 = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code1);
			parMap.put("code", code1);
			parMap.put("message", message);
		}
		return parMap;
	}

	/**
	 * 66.提交/修改极光 Id
	 * @param accountId	职员 ID
	 * @param regeditId	极光注册 ID
	 * @param sourceId	1:Android 2:iOS
	 * @return
	 */
	@RequestMapping("updateAppMerchantJpush.action")
	public Map<String, Object> updateAppMerchantJpush(Integer accountId,String regeditId,Integer sourceId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null||regeditId==null||sourceId==null) {
			int code1 = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code1);
			parMap.put("code", code1);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Integer updateAppMerchantJpush = baseUserManager.updateAppMerchantJpush(accountId, regeditId, sourceId);
			if(updateAppMerchantJpush.intValue()!=1) {
				logger.error("[project:{}] [updateHealthCertificate:{}]", new Object[] {
						WebConfig.projectName, updateAppMerchantJpush });
				// 修改失败
				int code1 = Constants.CODE_ERROR_UPDATE;
				String message = Constants.getCodeValue(code1);
				parMap.put("code", code1);
				parMap.put("message", message);
			}
			
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code1 = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code1);
			parMap.put("code", code1);
			parMap.put("message", message);
		}
		return parMap;
	}
	

	/**
	 * 98.用户地址查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("clientAddressSelect.action")
	public Map<String, Object> clientAddressSelect(Integer clientId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(clientId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> selectMyVouchers = baseUserManager.clientAddressSelect(clientId, eid);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("address", selectMyVouchers);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 110.查询企业职员 只查询送水员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findEnterpriseStaff.action")
	public Map<String, Object> findEnterpriseStaff(Integer eid,String shopName,Integer shopId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<StaffDTO> staffs = baseUserManager.Proc_Backstage_staff_select(eid,shopName,shopId, PageIndex, PageSize, IsSelectAll);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("staffs", staffs);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 112.查询用户是否注册
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findUser.action")
	public Map<String, Object> findUser(String account) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Integer accountId = baseUserManager.findUser(account);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if(accountId!=null) {
				resultMap.put("accountId", accountId);
				setData(parMap, resultMap);
			}else {
				int code = Constants.CODE_ERROR_NOUSER;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
			}
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	

	/**
	 * 113.查询售货机店铺管理人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findVendorShopAdmin.action")
	public Map<String, Object> findVendorShopAdmin(Integer eid,Integer shopId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<VendorShopAdministratorDTO> staffs = baseUserManager.selectVendorShopAdmin(eid,shopId, PageIndex, PageSize, IsSelectAll);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("staffs", staffs);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	
	/**
	 * 114.添加职员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("staffAdd.action")
	public Map<String, Object> staffAdd(Integer shopId,Integer eid,String name,String phone,String picture,String SQ_remark,String SQ_IDCard,String workCard,String healthCard,String creditCard,String SQ_staffNO,Boolean isRelevanceEvian) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||shopId==null||phone==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			if(!StringUtils.isEmpty(picture)) {
				String upResult = QiniuFileSystemUtil.uploadShearPic(picture);
				if (!StringUtils.isEmpty(upResult)) {
					JSONObject ject = JSON.parseObject(upResult);
					if (!StringUtils.isEmpty((String) ject.get("hash"))
							&& !StringUtils.isEmpty((String) ject.get("key"))) {

						upResult = QiniuConfig.namespace + (String) ject.get("key");
					}
				}
				picture = upResult;
			}
			
			if(!StringUtils.isEmpty(SQ_IDCard)) {
				String upResult = QiniuFileSystemUtil.uploadShearPic(SQ_IDCard);
				if (!StringUtils.isEmpty(upResult)) {
					JSONObject ject = JSON.parseObject(upResult);
					if (!StringUtils.isEmpty((String) ject.get("hash"))
							&& !StringUtils.isEmpty((String) ject.get("key"))) {

						upResult = QiniuConfig.namespace + (String) ject.get("key");
					}
				}
				SQ_IDCard = upResult;
			}
			
			if(!StringUtils.isEmpty(healthCard)) {
				String upResult = QiniuFileSystemUtil.uploadShearPic(healthCard);
				if (!StringUtils.isEmpty(upResult)) {
					JSONObject ject = JSON.parseObject(upResult);
					if (!StringUtils.isEmpty((String) ject.get("hash"))
							&& !StringUtils.isEmpty((String) ject.get("key"))) {
						
						upResult = QiniuConfig.namespace + (String) ject.get("key");
					}
				}
				healthCard = upResult;
			}
			
			if(!StringUtils.isEmpty(creditCard)) {
				String upResult = QiniuFileSystemUtil.uploadShearPic(creditCard);
				if (!StringUtils.isEmpty(upResult)) {
					JSONObject ject = JSON.parseObject(upResult);
					if (!StringUtils.isEmpty((String) ject.get("hash"))
							&& !StringUtils.isEmpty((String) ject.get("key"))) {
						
						upResult = QiniuConfig.namespace + (String) ject.get("key");
					}
				}
				creditCard = upResult;
			}
			
			
			baseUserManager.Proc_Backstage_staff_add(shopId, eid, name, phone, picture, SQ_remark, SQ_IDCard, workCard, healthCard, creditCard, SQ_staffNO, isRelevanceEvian);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	

	/**
	 * 117.查询用户是否在微信、小程序、拼团小程序注册过
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findUserIsRegisterWx.action")
	public Map<String, Object> findUserIsRegisterWx(String account,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Boolean isWXRegister = baseUserManager.selectWxIsRegister(eid, account);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("isWXRegister", isWXRegister);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
	/**
	 * 118.水趣推客 获取申请信息
	 */
	@RequestMapping("getShareApplyInfo.action")
	public String getShareApplyInfo(String identityCode,String appId) throws Exception{
		String shareApplyInfo = baseUserManager.getShareApplyInfo(identityCode, appId);
		return shareApplyInfo;
	}
	
	/**
	 * 119.水趣推客 保存推客申请
	 */
	@RequestMapping("saveShareApplyInfo.action")
	public String saveShareApplyInfo(String identityCode, Integer eid, String fullname, String tel, String resource, String ip, String location,String appId) throws Exception{
		String saveShareApplyInfo = baseUserManager.saveShareApplyInfo(identityCode, eid, fullname, tel, resource, ip, location, appId);
		return saveShareApplyInfo;
	}
	

	
	/**
	 * 120.推客获取系统或者企业模板
	 */
	@RequestMapping("getSysEnterPriseTemplate.action")
	public String getSysEnterPriseTemplate(String identityCode,Integer type,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getSysEnterPriseTemplate(identityCode , type , appId);
		return getSysEnterPriseTemplate;
	}
	
	
	/**
	 * 121.根据条码获取码对应信息
	 */
	@RequestMapping("getShopManagerCode.action")
	public String getShopManagerCode(String identityCode,Long shopCode,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getShopManagerCode(identityCode, shopCode, appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 122.根据手机号获取绑定相关信息，验证码
	 */
	@RequestMapping("getShopRegeditGetCode.action")
	public String getShopRegeditGetCode(String identityCode,Integer eid,String cellphone,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getShopRegeditGetCode(identityCode, eid, cellphone, appId);
		return getSysEnterPriseTemplate;
	}

	/**
	 * 123.根据手机号验证码保存推客经理绑定
	 */
	@RequestMapping("saveShopManagerCode.action")
	public String saveShopManagerCode(String identityCode,String openId,String cellphone,Integer existCid,String nickname,String code,Long shopCode,Integer shopId,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.saveShopManagerCode(identityCode, openId, cellphone, existCid, nickname, code, shopCode, shopId, appId);
		return getSysEnterPriseTemplate;
	}

	/**
	 * 124.推客经理相关信息
	 */
	@RequestMapping("getClientShareInfo.action")
	public String getClientShareInfo(String identityCode,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getClientShareInfo(identityCode , appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 125.店铺编码明细
	 */
	@RequestMapping("getShopCodeRegeditReport.action")
	public String getShopCodeRegeditReport(String identityCode,Integer eid,String endDate,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getShopCodeRegeditReport(identityCode, eid, endDate, appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 126.店铺推客明细
	 */
	@RequestMapping("getShopClientRegeditReport.action")
	public String getShopClientRegeditReport(String identityCode,Integer eid,String endDate,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getShopClientRegeditReport(identityCode, eid, endDate, appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 127.店铺推广_我的账户情况
	 */
	@RequestMapping("myLiteAppEarningInfo.action")
	public String myLiteAppEarningInfo(String appId,String identityCode,Integer type) throws Exception{
		String myLiteAppEarningInfo = baseUserManager.myLiteAppEarningInfo(appId , identityCode , type);
		return myLiteAppEarningInfo;
	}
	
	/**
	 * 128.用户推客所属公众号,是否界面可跳转
	 */
	@RequestMapping("tuikeBelongEnterprise.action")
	public String tuikeBelongEnterprise(String identityCode,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.tuikeBelongEnterprise(appId , identityCode);
		return getSysEnterPriseTemplate;
	}

	/**
	 * 129.推客经理汇总报表
	 */
	@RequestMapping("getShareTotalReport.action")
	public String getShareTotalReport(String identityCode,Integer eid,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getShareTotalReport(identityCode ,eid, appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 130.推客经理下的所有推客码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tuikeManagerCodes.action")
	public Map<String, Object> tuikeManagerCodes(String shopCode,Integer eid,Integer shopId,String beginTime,String endTime,String managerFullName,String tuikeFullName,String shopName,String eName,Boolean isEnable,String managerAccount,String tuikeAccount,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> resultMap = baseUserManager.tuikeManagerCodes(shopCode, eid, shopId, beginTime, endTime, managerFullName, tuikeFullName, shopName, eName, isEnable, managerAccount, tuikeAccount, PageIndex, PageSize, IsSelectAll);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}

	/**
	 * 132.获取客户地址
	 */
	@RequestMapping("getAddress.action")
	public String getUserAddressListJsonStr(String identityCode,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getAddress(identityCode , appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 133.企业经营的城市和区域信息
	 */
	@RequestMapping("enterPriceRunCity.action")
	public String enterPriceRunCity(String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.enterPriceRunCity(appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 134.获取客户的地址标签集合
	 */
	@RequestMapping("getAddressTags.action")
	public String getAddressTags(String identityCode,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getAddressTags(identityCode,appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 135.选择城市区域的客户地址新增
	 */
	@RequestMapping(value = "addAddress_v2", produces = "application/json; charset=utf-8")
	public String addAddress_v2(@ModelAttribute("requestModel") WxUserAddressModel requestModel,String identityCode,String appId, HttpServletRequest request) {
		
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", identityCode));
		params.add(new BasicNameValuePair("streetName", requestModel.getStreetName()));
		params.add(new BasicNameValuePair("address", requestModel.getAddress()));
		params.add(new BasicNameValuePair("phone", requestModel.getPhone()));
		params.add(new BasicNameValuePair("doorplate", requestModel.getDoorplate()));
		params.add(new BasicNameValuePair("contacts", requestModel.getContacts()));
		params.add(new BasicNameValuePair("location", requestModel.getLocation()));
		params.add(new BasicNameValuePair("tag", requestModel.getTag()));	
		params.add(new BasicNameValuePair("sdkType", "3"));
		params.add(new BasicNameValuePair("authorizer_appid",appId));
		return baseUserManager.addAddress_v2(params);
	}
	
	/**
	 * 136.选择城市区域的客户地址新增
	 */
	@RequestMapping(value = "newClientAddrss3", produces = "application/json; charset=utf-8")
	public String newClientAddrss3(@ModelAttribute("requestModel") WxUserAddressModel requestModel,String identityCode,String appId, HttpServletRequest request) {
		
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", identityCode));
		params.add(new BasicNameValuePair("streetName", requestModel.getStreetName()));
		params.add(new BasicNameValuePair("address", requestModel.getAddress()));
		params.add(new BasicNameValuePair("phone", requestModel.getPhone()));
		params.add(new BasicNameValuePair("doorplate", requestModel.getDoorplate()));
		params.add(new BasicNameValuePair("cityId", requestModel.getCityId().toString()));
		params.add(new BasicNameValuePair("districtId", requestModel.getDistrictId().toString()));
		params.add(new BasicNameValuePair("contacts", requestModel.getContacts()));
		params.add(new BasicNameValuePair("tag", requestModel.getTag()));	
		params.add(new BasicNameValuePair("sdkType", "3"));
		params.add(new BasicNameValuePair("authorizer_appid",appId));
		return baseUserManager.newClientAddrss3(params);
	}
	
	/**
	 * 137.编辑客户地址
	 */
	@RequestMapping(value = "editAddress", produces = "application/json; charset=utf-8")
	public String editAddress(@ModelAttribute("requestModel") WxUserAddressModel requestModel,String identityCode,String appId, HttpServletRequest request) {
		
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", identityCode));
		params.add(new BasicNameValuePair("did", requestModel.getDid().toString()));
		params.add(new BasicNameValuePair("streetName", requestModel.getStreetName()));
		params.add(new BasicNameValuePair("streetDescribe", requestModel.getStreetDescribe()));
		params.add(new BasicNameValuePair("doorplate", requestModel.getDoorplate()));
		params.add(new BasicNameValuePair("phone", requestModel.getPhone()));
		params.add(new BasicNameValuePair("contacts", requestModel.getContacts()));
		params.add(new BasicNameValuePair("location", requestModel.getLocation()));
		params.add(new BasicNameValuePair("tag", requestModel.getTag()));	
		params.add(new BasicNameValuePair("sdkType", "3"));
		params.add(new BasicNameValuePair("authorizer_appid",appId));
		return baseUserManager.editAddress(params);
	}
	
	/**
	 * 138.获取客户的地址标签集合
	 */
	@RequestMapping("deleteAddress.action")
	public String deleteAddress(String identityCode,Integer did,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.deleteAddress(identityCode,did,appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 141.APP商户端推客经理设置自己为推客并生成推客码
	 */
	@RequestMapping("shareCodeForAppTuiKeManager.action")
	public String shareCodeForAppTuiKeManager(Integer eid,String shopCode,Integer managerClientId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.shareCodeForAppTuiKeManager(eid, shopCode, managerClientId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 142.重新生成推客二维码
	 */
	@RequestMapping("freshMakeQrcodePic.action")
	public String freshMakeQrcodePic(Integer eid,String shopCode) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.freshMakeQrcodePic(eid, shopCode);
		return getSysEnterPriseTemplate;
	}

	/**
	 * 144.根据手机号查询企业职员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("isExistEnterpriseStaff.action")
	public Map<String, Object> isExistEnterpriseStaff(Integer eid,String phone) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||phone==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<StaffDTO> staffs = baseUserManager.Proc_Backstage_staff_select(eid,phone);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("staffs", staffs);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}

	/**
	 * 145.推客我的客户、推客经理我的团队
	 */
	@RequestMapping("getDevelopClients.action")
	public String getDevelopClients(String identityCode, Integer type, Integer pageIndex,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.getDevelopClients(identityCode,type,pageIndex,appId);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 147.设置默认客户地址
	 */
	@RequestMapping("defaultAddress.action")
	public String defaultAddress(String identityCode, Integer did, String appId) throws Exception{
		String getSysEnterPriseTemplate = baseUserManager.defaultAddress(identityCode,did,appId);
		return getSysEnterPriseTemplate;
	}

	/**
	 * 154.根据企业id查询客服名称
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findEServiceUserlistByEid.action")
	public Map<String, Object> findEServiceUserlistByEid(Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> serverUserList = baseUserManager.selectEServiceUserlistByEid(eid);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("serverUsers", serverUserList);
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}

	/**
	 * 154.用户权限查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("roleMenuSelect.action")
	public Map<String, Object> roleMenuSelect(Integer roleId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(roleId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> roleMenus = baseUserManager.Proc_Backstage_appMerchant_account_enterprise_role_menu_select(roleId);
		resultMap.put("roleMenus",roleMenus);

		List<Map<String, Object>> enterpriseMenus = baseUserManager.Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(eid);
		resultMap.put("enterpriseMenus",enterpriseMenus==null?new ArrayList<>():enterpriseMenus);

		setData(parMap, resultMap);

		return parMap;
	}

	/**
	 * 162.查询账户是否在微信、app、app商户注册过
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchingAccount.action")
	public Map<String,Object> searchingAccount(String account){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account == null){
			setERROR_PARAM(parMap);
			return parMap;
		}
		EclientDTO dto = baseUserManager.searchingAccount(account);
		if(dto.getClientId()!=null){
			setData(parMap,dto);
		}else{
			// 用户不存在
			int code = Constants.CODE_ERROR_NOUSER;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
}
