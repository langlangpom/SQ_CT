package com.evian.sqct.api.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.user.Ecity;
import com.evian.sqct.bean.user.UserModel2;
import com.evian.sqct.bean.user.UserModel3;
import com.evian.sqct.service.BaseUserManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import com.evian.sqct.util.DES.WebConfig;

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
		try {
			Map<String,Object> userLogin = baseUserManager.userLogin_v2(account, passWord);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if ("1".equals(userLogin.get("TAG"))) {
				UserModel3 userModel = (UserModel3) userLogin.get("result");
				/*resultMap.put("accountId",userModel.getAccountId());
				resultMap.put("account",userModel.getAccount());
				resultMap.put("userAuthorization",userModel.getUserAuthorization());
				resultMap.put("isEnable",userModel.getIsEnable());
				resultMap.put("remark",userModel.getRemark());
				resultMap.put("eName",userModel.geteName());
				resultMap.put("eid",userModel.getEid());*/
				parMap.put("data", userModel);
			}else {
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
	 * 用户普通登录验证
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
	 * 用户注册获取验证码
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
			switch (getCode) {
			case "E00000":
				break;
			case "C00001":
				parMap.put("code", 150);
				parMap.put("message", "手机号码已被注册");
				break;
			case "C00003":
				parMap.put("code", 150);
				parMap.put("message", "注册频繁,稍后再试");
				break;
			case "C00040":
				parMap.put("code", 150);
				parMap.put("message", "微信ID已被绑定");
				break;
			default:
				int code = Constants.CODE_ERROR_SYSTEM;
				String message = Constants.getCodeValue(code);
				parMap.put("code", code);
				parMap.put("message", message);
				break;
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
	 * 验证验证码,且生成账号
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
                    int cid = Integer.valueOf(hashout.get("cId").toString());
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
				int code1 = 150;
				String message = "修改失败";
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
				int code1 = 150;
				String message = "修改失败";
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
}
