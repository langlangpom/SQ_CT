package com.evian.sqct.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evian.sqct.bean.util.WXHB;
import com.evian.sqct.dao.IWxHBDao;
import com.evian.sqct.util.WebConfig;
import com.evian.sqct.util.XmlStringUtil;
import com.evian.sqct.wxHB.WxPayHB;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseWxHBManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("wxHBDao")
	private IWxHBDao wxHBDao;
	
	@Autowired
	WxPayHB wxph;
	
	public 	String redpackUserRecordWechatSendLogOperat(String return_code,String return_msg,String result_code,String err_code,String err_code_des,String mch_billno,String mch_id,String wxappid,String re_openid,Integer total_amount,String send_listid) {
		logger.info("[project:{}] [step:enter] [return_code:{}] [return_msg:{}] [result_code:{}] [err_code:{}] [err_code_des:{}] [mch_billno:{}] [mch_id:{}] [wxappid:{}] [re_openid:{}] [total_amount:{}] [send_listid:{}]",
				new Object[] { WebConfig.projectName, return_code, return_msg, result_code, err_code,err_code_des,mch_billno,mch_id,wxappid,re_openid,total_amount,send_listid});
		return wxHBDao.redpackUserRecordWechatSendLogOperat(return_code, return_msg, result_code, err_code, err_code_des, mch_billno, mch_id, wxappid, re_openid, total_amount, send_listid);
	}
	
	public 	String redpackUserRecordWechatSendLogOperat(Map<String, Object> map) {
		logger.info("[project:{}] [step:enter] [map:{}]",
				new Object[] { WebConfig.projectName, map});
		return wxHBDao.redpackUserRecordWechatSendLogOperat(map.get("return_code").toString(), 
				map.get("return_msg")==null?"":map.get("return_msg").toString(), 
				map.get("result_code").toString(), 
				map.get("err_code")==null?"":map.get("err_code").toString(),
				map.get("err_code_des")==null?"":map.get("err_code_des").toString(), 
				map.get("mch_billno").toString(), 
				map.get("mch_id").toString(),
				map.get("wxappid").toString(), 
				map.get("re_openid").toString(),
				Integer.valueOf(map.get("total_amount").toString()),
				map.get("send_listid")==null?"":map.get("send_listid").toString());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> redpackSserRecordSendSelect(String openId){
		logger.info("[project:{}] [step:enter] [openId:{}]",
				new Object[] { WebConfig.projectName, openId});
		return wxHBDao.redpackSserRecordSendSelect(openId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> redpackSserRecordResendSelect(String openId,Integer recordId){
		logger.info("[project:{}] [step:enter] [openId:{}] [recordId:{}]",
				new Object[] { WebConfig.projectName, openId,recordId});
		return wxHBDao.redpackSserRecordResendSelect(openId, recordId);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void recordSelectForValid() throws Exception{
		
		List<Map<String, Object>> recordSelectForValid = wxHBDao.recordSelectForValid();
		for (Map<String, Object> map : recordSelectForValid) {
			WXHB w = new WXHB();
			UUID uuid=UUID.randomUUID();
		    String str = uuid.toString(); 
		    String uuidStr=str.replace("-", "");
			w.setNonce_str(uuidStr);
			Random ran=new Random();
			int a=ran.nextInt(99999999);
			int b=ran.nextInt(99999999);
			long l=a*10000000L+b;
//			String t = "845431986481718";
			String num=String.valueOf(l);
			w.setMchBillno(map.get("sendSign").toString());
			w.setMchId(map.get("mchId").toString());
			w.setWxappid(map.get("appId").toString());
			w.setBill_type("MCHT"); // MCHT:通过商户订单号获取红包信息。
			w.setAppKey(map.get("partnerKey").toString());
			String pay = wxph.gethbinfo(w);
			System.out.println(pay);
			Map<String, Object> sendredpackMap = new HashMap<String, Object>();
			try {
				Integer validStatus = 0; 
				sendredpackMap = XmlStringUtil.stringToXMLParse(pay);
				if("SENDING".equals(sendredpackMap.get("status").toString())) {
					validStatus = 1;
				}else if("SENT".equals(sendredpackMap.get("status").toString())) {
					validStatus = 2;
				}else if("FAILED".equals(sendredpackMap.get("status").toString())) {
					validStatus = 3;
				}else if("RECEIVED".equals(sendredpackMap.get("status").toString())) {
					validStatus = 4;
				}else if("RFUND_ING".equals(sendredpackMap.get("status").toString())) {
					validStatus = 5;
				}else if("REFUND".equals(sendredpackMap.get("status").toString())) {
					validStatus = 6;
				}
				String tag = wxHBDao.updateForValid(map.get("sendSign").toString(), validStatus);
				logger.info("[领取结果记录TAG:{}]",tag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("[Exception:{}] [pay:{}]",new Object[] {e.getMessage(),pay});
			}
		}
	}
}
