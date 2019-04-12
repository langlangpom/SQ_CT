package com.evian.sqct.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evian.sqct.bean.user.Ecity;
import com.evian.sqct.bean.user.Eclient;
import com.evian.sqct.bean.user.UserModel;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.util.DES3_CBCUtil;
import com.evian.sqct.util.WebConfig;

@Service
@Transactional
public class BaseUserManager {
	
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
		UserModel selectUserByNameAndPwd = userDao.selectUserByName(cellPhone);
		if (selectUserByNameAndPwd != null) {
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
	
	public Integer updateHealthCertificate(Integer accountId,Integer eid,String headImg,String fullname,String healthCertificateImg,String SQ_IDCard) {
		logger.info("[project:{}] [step:enter] [accountId:{}] [eid:{}] [headImg:{}] [fullname:{}] [healthCertificateImg:{}] [SQ_IDCard:{}]",
				new Object[] { WebConfig.projectName, accountId, eid, headImg, fullname,healthCertificateImg,SQ_IDCard});
		return userDao.updateHealthCertificate(accountId, eid, headImg, fullname, healthCertificateImg,SQ_IDCard);
	}
	
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
}
