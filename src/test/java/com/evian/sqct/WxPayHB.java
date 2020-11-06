package com.evian.sqct;

import com.evian.sqct.bean.util.WXHB;
import com.evian.sqct.util.HttpClientUtil;
import com.evian.sqct.wxHB.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.SortedMap;
import java.util.TreeMap;
@Repository
public class WxPayHB {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 微信裂变红包支付
	 * 2018-12-25
	 * xhx
	 * @throws Exception 
	 */
	public String Pay(WXHB wxhb) throws Exception{
		logger.info("=======================================================================微信裂变红包支付开始");
		logger.info("微信裂变红包支付入口参数："+wxhb.toString());
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("nonce_str", wxhb.getNonce_str());
		packageParams.put("mch_billno", wxhb.getMchBillno());
		packageParams.put("mch_id", wxhb.getMchId());
		packageParams.put("wxappid", wxhb.getWxappid());
//		packageParams.put("msgappid", wxhb.getMsgappid());
		packageParams.put("send_name", wxhb.getSendName());
		packageParams.put("re_openid", wxhb.getReOpenid());
		packageParams.put("total_amount", wxhb.getTotalAmount());
		packageParams.put("total_num", wxhb.getTotalNum());
		packageParams.put("amt_type", wxhb.getAmt_type());
		packageParams.put("wishing", wxhb.getWishing());
		packageParams.put("client_ip", wxhb.getClientIp());
		packageParams.put("act_name", wxhb.getActName());
		packageParams.put("remark", wxhb.getRemark());
		RequestHandler reqHandler = new RequestHandler();
		reqHandler.init(wxhb.getAppKey());
		String sign = reqHandler.createSign(packageParams);
		/** 封装报文 */
		String xml = "<xml>"+
			"<nonce_str><![CDATA[" + wxhb.getNonce_str() + "]]></nonce_str>"+
			"<sign><![CDATA[" + sign + "]]></sign>"+
			"<mch_billno><![CDATA["+wxhb.getMchBillno()+"]]></mch_billno>"+
			"<mch_id><![CDATA[" + wxhb.getMchId() + "]]></mch_id>"+
			"<wxappid><![CDATA[" + wxhb.getWxappid() + "]]></wxappid>"+
			"<send_name>" + wxhb.getSendName() + "</send_name>"+
			"<re_openid><![CDATA[" + wxhb.getReOpenid() + "]]></re_openid>"+
			"<total_amount><![CDATA[" + wxhb.getTotalAmount() + "]]></total_amount>"+
			"<total_num><![CDATA[" + wxhb.getTotalNum() + "]]></total_num>"+
			"<amt_type><![CDATA[" + wxhb.getAmt_type() + "]]></amt_type>"+
			"<wishing><![CDATA[" + wxhb.getWishing() + "]]></wishing>"+
			"<client_ip><![CDATA[" + wxhb.getClientIp() + "]]></client_ip>"+
			"<act_name><![CDATA[" + wxhb.getActName() + "]]></act_name>"+
			"<remark><![CDATA["+wxhb.getRemark()+"]]></remark>"+
			"</xml>";
		
		//获取预支付ID
		String createOrderURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack";
		logger.info("微信裂变红包支付prepayId请求地址: "+createOrderURL+", 请求数据: "+xml);
		String prepayContent = HttpClientUtil.sslPost(createOrderURL , xml,wxhb.getMchId());
		logger.info("微信裂变红包支付prepayId请求返回结果: "+prepayContent);
		return prepayContent;
	}
	
	/**
	 * 微信普通红包支付
	 * 2018-12-25
	 * xhx
	 * @throws Exception 
	 */
	public String sendredpack(WXHB wxhb) throws Exception {
		logger.info("=======================================================================微信普通红包支付开始");
		logger.info("微信普通红包支付入口参数："+wxhb.toString());
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("nonce_str", wxhb.getNonce_str());
		packageParams.put("mch_billno", wxhb.getMchBillno());
		packageParams.put("mch_id", wxhb.getMchId());
		packageParams.put("wxappid", wxhb.getWxappid());
//		packageParams.put("msgappid", wxhb.getMsgappid());
		packageParams.put("send_name", wxhb.getSendName());
		packageParams.put("re_openid", wxhb.getReOpenid());
		packageParams.put("total_amount", wxhb.getTotalAmount());
		packageParams.put("total_num", wxhb.getTotalNum());
		packageParams.put("wishing", wxhb.getWishing());
		packageParams.put("client_ip", wxhb.getClientIp());
		packageParams.put("act_name", wxhb.getActName());
		packageParams.put("remark", wxhb.getRemark());
		RequestHandler reqHandler = new RequestHandler();
		reqHandler.init(wxhb.getAppKey());
		String sign = reqHandler.createSign(packageParams);
		/** 封装报文 */
		String xml = "<xml>"+
			"<nonce_str><![CDATA[" + wxhb.getNonce_str() + "]]></nonce_str>"+
			"<sign><![CDATA[" + sign + "]]></sign>"+
			"<mch_billno><![CDATA["+wxhb.getMchBillno()+"]]></mch_billno>"+
			"<mch_id><![CDATA[" + wxhb.getMchId() + "]]></mch_id>"+
			"<wxappid><![CDATA[" + wxhb.getWxappid() + "]]></wxappid>"+
			"<send_name>" + wxhb.getSendName() + "</send_name>"+
			"<re_openid><![CDATA[" + wxhb.getReOpenid() + "]]></re_openid>"+
			"<total_amount><![CDATA[" + wxhb.getTotalAmount() + "]]></total_amount>"+
			"<total_num><![CDATA[" + wxhb.getTotalNum() + "]]></total_num>"+
			"<wishing><![CDATA[" + wxhb.getWishing() + "]]></wishing>"+
			"<client_ip><![CDATA[" + wxhb.getClientIp() + "]]></client_ip>"+
			"<act_name><![CDATA[" + wxhb.getActName() + "]]></act_name>"+
			"<remark><![CDATA["+wxhb.getRemark()+"]]></remark>"+
			"</xml>";
		
		//获取预支付ID
		String createOrderURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
		logger.info("微信普通红包支付prepayId请求地址: "+createOrderURL+", 请求数据: "+xml);
		String prepayContent = HttpClientUtil.sslPost(createOrderURL , xml,wxhb.getMchId());
		logger.info("微信普通红包支付prepayId请求返回结果: "+prepayContent);
		return prepayContent;
	}
	
	/**
	 * 微信红包查询
	 * 2018-12-25
	 * xhx
	 * @throws Exception 
	 */
	public String gethbinfo(WXHB wxhb) throws Exception {
		logger.info("=======================================================================微信红包查询开始");
		logger.info("微信红包查询入口参数："+wxhb.toString());
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("mch_billno", wxhb.getMchBillno());
		packageParams.put("mch_id", wxhb.getMchId());
		packageParams.put("appid", wxhb.getWxappid());
		packageParams.put("bill_type", wxhb.getBill_type());
		packageParams.put("nonce_str", wxhb.getNonce_str());
		RequestHandler reqHandler = new RequestHandler();
		reqHandler.init(wxhb.getAppKey());
		String sign = reqHandler.createSign(packageParams);
		/** 封装报文 */
		String xml = "<xml>"+
				"<nonce_str><![CDATA[" + wxhb.getNonce_str() + "]]></nonce_str>"+
				"<sign><![CDATA[" + sign + "]]></sign>"+
				"<mch_billno><![CDATA["+wxhb.getMchBillno()+"]]></mch_billno>"+
				"<mch_id><![CDATA[" + wxhb.getMchId() + "]]></mch_id>"+
				"<appid><![CDATA[" + wxhb.getWxappid() + "]]></appid>"+
				"<bill_type>" + wxhb.getBill_type() + "</bill_type>"+
				"</xml>";
		
		//获取预支付ID
		String createOrderURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";
		logger.info("微信红包查询prepayId请求地址: "+createOrderURL+", 请求数据: "+xml);
		String prepayContent = HttpClientUtil.sslPost(createOrderURL , xml,wxhb.getMchId());
		logger.info("微信红包查询prepayId请求返回结果: "+prepayContent);
		return prepayContent;
	}
}
