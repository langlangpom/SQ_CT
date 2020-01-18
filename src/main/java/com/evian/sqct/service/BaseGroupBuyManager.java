package com.evian.sqct.service;

import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.vendor.PayParam;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IGroupBuyDao;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.util.*;
import com.evian.sqct.wxHB.RequestHandler;
import com.evian.sqct.wxPay.APPWxPayBean;
import com.evian.sqct.wxPay.APPWxPayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @date   2019年8月9日 上午11:47:53
 * @author XHX
 * @Description 该函数的功能描述
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class BaseGroupBuyManager extends BaseManager{
	

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("groupBuyDao")
	private IGroupBuyDao groupBuyDao;
	
	@Autowired
	@Qualifier("orderDao")
	private IOrderDao orderDao;
	
	public Map<String, Object> selectGroupBuyOrder(String beginTime,String endTime,Integer eid,String orderNo,String nickName,Boolean isCommander,Boolean isRefund,String account,Integer groupBuyState,String paymentNo,String orderGroup,String eName,String pname,String pcode,String sdkType,Integer shopId,Boolean isFilterEndOrder, Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [eid:{}] [orderNo:{}] [nickName:{}] [isCommander:{}] [isRefund:{}] [account:{}] [groupBuyState:{}] [paymentNo:{}] [orderGroup:{}] [eName:{}] [pname:{}] [pcode:{}] [sdkType:{}] [shopId:{}] [isFilterEndOrder:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName,beginTime,endTime,eid,orderNo,nickName,isCommander,isRefund,account,groupBuyState,paymentNo,orderGroup,eName,pname,pcode,sdkType,shopId,isFilterEndOrder,PageIndex,PageSize,IsSelectAll});
		Map<String, Object> stringObjectMap = groupBuyDao.Proc_Backstage_groupbuy_order_select(beginTime, endTime, eid, orderNo, nickName, isCommander, isRefund, account, groupBuyState, paymentNo, orderGroup, eName, pname, pcode, sdkType, shopId, isFilterEndOrder, PageIndex, PageSize, IsSelectAll);
		return stringObjectMap;
	}
	
	public Map<String,Object> selectGroupBuyProducts(String beginTime,String endTime,Integer eid,Integer cityId,Integer groupBuyType,Boolean isEnabled,String eName,String shopName,Integer shopId,String pname,String pcode,Integer cid,String groupName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [eid:{}] [cityId:{}] [groupBuyType:{}] [isEnabled:{}] [eName:{}] [shopName:{}] [shopId:{}] [pname:{}] [pcode:{}] [cid:{}] [groupName:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName,beginTime,endTime,eid,cityId,groupBuyType,isEnabled,eName,shopName,shopId,pname,pcode,cid,groupName,PageIndex,PageSize,IsSelectAll});
		Map<String, Object> proc_Backstage_groupbuy_product_select = groupBuyDao.Proc_Backstage_groupbuy_product_select(beginTime, endTime, eid, cityId, groupBuyType, isEnabled, eName, shopName, shopId, pname, pcode, cid,groupName, PageIndex, PageSize, IsSelectAll);
		Object groupBuyProducts = proc_Backstage_groupbuy_product_select.get("groupBuyProducts");
		if(groupBuyProducts!=null) {
			List<Map<String,Object>> groupBuyProductsList = (List<Map<String,Object>>)groupBuyProducts;
			for (Map<String, Object> map : groupBuyProductsList) {
				Object xa = map.get("xaId");
				if(xa!=null) {
					Integer xaId = (Integer) xa;
					List<Map<String, Object>> priceScheme = groupBuyDao.e_groupbuy_price_schemeByXaId(xaId);
					map.put("priceScheme", priceScheme);
				}
			}
		}
		return proc_Backstage_groupbuy_product_select;
	}
	

	/** 242.拼团商品详情  Json */ 
	public String getGroupBuyProductDetail(String clientId,Integer eid,Integer xaId,Integer pid,String appId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("eid", eid!=null?eid.toString():""));
		params.add(new BasicNameValuePair("xaId", xaId!=null?xaId.toString():""));
		params.add(new BasicNameValuePair("pid", pid!=null?pid.toString():""));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/groupbuy/getGroupBuyProductDetail.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return webContent;
	}


	/** 243.保存拼团单据  Json */ 
	public String saveGroupBuyOrder(String clientId,String openid,String orderJson,String appId,String ip,String body) throws Exception {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("orderJson", orderJson));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/groupbuy/saveGroupBuyOrder.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		return savePayParam(webContent,appId,ip,body);
	}

	/** 246.订单继续支付、取消订单、取消支付操作  Json */
	public String groupBuyOrderUpdateStatus(String clientId,Integer gboId,Integer operateId,String appId,String ip,String body) throws Exception {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("clientId", clientId));
		params.add(new BasicNameValuePair("gboId", gboId!=null?gboId.toString():""));
		params.add(new BasicNameValuePair("operateId", operateId!=null?operateId.toString():""));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/groupbuy/groupBuyOrderUpdateStatus.action", params);
		webContent = stringCodeExchangeIntCode(webContent);
		if(operateId==1){
			return savePayParam(webContent,appId,ip,body);

		}else{
			return webContent;
		}
	}

	private String savePayParam(String webContent,String appId,String ip,String body)throws Exception{
		JSONObject parseObject = JSONObject.parseObject(webContent);
		if(1!=parseObject.getInteger("code")){
			return webContent;
		}

		Map<String, Object> param = orderDao.selectEEnterpriseAppPayParamByAppId(appId);
		if(param!=null){
			String sub_appid = (String) param.get("wechatAppId");
			String sub_mch_id = (String) param.get("wechatMchId");

			JSONObject data = parseObject.getJSONObject("data");

			String group = data.getString("group");
			Integer gboId = data.getInteger("gboId");
			Integer xaId = data.getInteger("xaId");
			Boolean isCommander = data.getBoolean("isCommander");
			Double lineMoney = data.getDouble("lineMoney");
			BigDecimal bigDecimal = new BigDecimal(lineMoney.toString());
			BigDecimal big100 = new BigDecimal("100");
			int total_fee = big100.multiply(bigDecimal).intValue();

			String nonce_str = APPWxPayUtils.create_nonce_str();
			String timestamp = APPWxPayUtils.create_timestamp();

			APPWxPayBean wx = new APPWxPayBean();
			wx.setNonce_str(nonce_str);
			wx.setMch_id(PayParam.getWeChatAppPayMchId());
			wx.setAppid(PayParam.getWeChatAppPayAppId());
			wx.setSub_appid(sub_appid);
			wx.setSub_mch_id(sub_mch_id);
			// 商品或支付单简要描述
			if(!StringUtils.isEmpty(body)){
				wx.setBody(body);
			}else{
				wx.setBody(gboId+"");
			}
			wx.setAttach("gboIdEVIAN"+gboId+"EVIAN"+xaId+"EVIAN"+isCommander);
			wx.setOut_trade_no(group);
			wx.setTotal_fee(total_fee+"");
			wx.setSpbill_create_ip(ip);
			String shuiqooMchantUrl = UrlManage.getShuiqooMchantUrl();
			if(StringUtils.isEmpty(shuiqooMchantUrl)) {
				throw new Exception("水趣商户域名没获取到，请检查配置文件，或者缓存程序");
			}
			wx.setNotify_url(shuiqooMchantUrl+"/evian/sqct/pay/notifyUrl");
			wx.setTrade_type("APP");
			wx.setAppKey(DES3_CBCUtil.des3DecodeCBC(PayParam.getWeChatAppPayKey()));
			String pay = APPWxPayUtils.pay(wx);

			SortedMap<String,String> packageParams = new TreeMap<>();
			JSONObject payReq = new JSONObject();
			try {
				Map<String,Object> sendredpackMap = XmlStringUtil.stringToXMLParse(pay);
				String  prepayId = (String) sendredpackMap.get("prepay_id");
				payReq.put("prepayId",prepayId);
				packageParams.put("prepayid",prepayId);
			} catch (Exception e) {
				logger.error("{}",e);
				return ERROR_SYSTEM();
			}

			packageParams.put("appid",sub_appid);
			packageParams.put("partnerid",sub_mch_id);
			packageParams.put("package","Sign=WXPay");
			packageParams.put("noncestr", nonce_str);
			packageParams.put("timestamp", timestamp);
			RequestHandler reqHandler = new RequestHandler();
			reqHandler.init(wx.getAppKey());
			// 生成app端发起支付签名
			String sign = reqHandler.createSign(packageParams);

			payReq.put("appId",sub_appid);
			payReq.put("partnerId",sub_mch_id);
			payReq.put("package","Sign=WXPay");
			payReq.put("nonceStr", nonce_str);
			payReq.put("timeStamp", timestamp);
			payReq.put("sign",sign);
			payReq.put("gboId",gboId);
			payReq.put("lineMoney",lineMoney);
			payReq.put("xaId",xaId);
			payReq.put("group",group);
			parseObject.put("data",payReq);
			return parseObject.toJSONString();
		}
		logger.error("[生成订单失败：{} 没有配置子商户]",appId);
		return ERROR_SYSTEM();
	}


	
	public Map<String, Object> selectGroupBuyShareInfo(Integer eid,Integer parent_gboId,Integer gboId,Integer xaId,Integer pid,String state){
		Map<String, Object> result = new HashMap<String, Object>();
		List<EEnterpriseWechatliteapp> selectMchidAndPartnerKey = orderDao.selectMchidAndPartnerKey(eid);
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		String wxParaComponentAppID = (String) selectSysConfig.get("第三方平台AppId");
		String ParamWXWebSit = (String) selectSysConfig.get("微信域名");
		if(!StringUtils.isEmpty(ParamWXWebSit)){
			UrlManage.setShuiqooGZHUrl(ParamWXWebSit);
		}
		Map<String, Object> WXMiniProgramObject = new HashMap<String, Object>();
		result.put("WXMiniProgramObject", "");
		Map<String, Object> gzh = new HashMap<String,Object>();
		result.put("gzh", "");
		for (EEnterpriseWechatliteapp eEnterpriseWechatliteapp : selectMchidAndPartnerKey) {
			// 1第一个第三方平台公众号，2第一个第三方平台小程序，3第二个第三方平台公众号，4第二个第三方平台小程序
			int appType = eEnterpriseWechatliteapp.getAppType().intValue();
			String appId = eEnterpriseWechatliteapp.getAppId();
			// 商品分享
			if("product".equals(state)) {
				// 小程序
				if(appType==4&&!StringUtils.isEmpty(eEnterpriseWechatliteapp.getOriginalId())) {
					WXMiniProgramObject.put("webpageUrl", "");
					WXMiniProgramObject.put("userName", eEnterpriseWechatliteapp.getOriginalId());
					String path = "/pages/goodsDetail/index?xaId="+xaId+"&pid="+pid;
					WXMiniProgramObject.put("path", path);
					String xhxShareImgUrl = UrlManage.getShuiqooMchantUrl()+"/evian/sqct/groupbuy/xcxGroupBuyProductShareImgUrl?state=product&appId="+appId+"&xaId="+xaId+"&pid="+pid;
					WXMiniProgramObject.put("shareImgUrl", xhxShareImgUrl);
					result.put("WXMiniProgramObject", WXMiniProgramObject);
				}else if(appType==1||appType==3) {
					String gzhShareUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=https%3A%2F%2F" + ParamWXWebSit + "%2Fweixin%2Foauth2&response_type=code&scope=snsapi_base&state=groupBookingProductDetailEVIAN"+xaId+"EVIAN"+pid+"&component_appid=" + wxParaComponentAppID + "#wechat_redirect";
					gzh.put("shareUrl", gzhShareUrl);
					String gzhShareImgUrl = UrlManage.getShuiqooMchantUrl()+"/evian/sqct/groupbuy/gzhGroupBuyProductShareImgUrl/"+ParamWXWebSit+"/"+wxParaComponentAppID+"/"+appId+"/"+xaId+"/"+pid;
					gzh.put("shareImgUrl", gzhShareImgUrl);
					result.put("gzh", gzh);
				}
			// 订单分享 
			}else if("order".equals(state)) {
				// 小程序
				if(appType==4&&!StringUtils.isEmpty(eEnterpriseWechatliteapp.getOriginalId())) {
					WXMiniProgramObject.put("webpageUrl", "");
					WXMiniProgramObject.put("userName", eEnterpriseWechatliteapp.getOriginalId());
					String path = "/pages/purchase/index?parent_gboId"+parent_gboId+"&gboId="+gboId;
					WXMiniProgramObject.put("path", path);
					String xhxShareImgUrl = UrlManage.getShuiqooMchantUrl()+"/evian/sqct/groupbuy/xcxGroupBuyProductShareImgUrl?state=order&appId="+appId+"&parent_gboId="+parent_gboId+"&gboId="+gboId;
					WXMiniProgramObject.put("shareImgUrl", xhxShareImgUrl);
					result.put("WXMiniProgramObject", WXMiniProgramObject);
				}else if(appType==1||appType==3) {
					String gzhShareUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=https%3A%2F%2F" + ParamWXWebSit + "%2Fweixin%2Foauth2&response_type=code&scope=snsapi_base&state=groupBookingOrderDetailsEVIAN"+gboId+"&component_appid=" + wxParaComponentAppID + "#wechat_redirect";
					gzh.put("shareUrl", gzhShareUrl);
					String gzhShareImgUrl = UrlManage.getShuiqooMchantUrl()+"/evian/sqct/groupbuy/gzhGroupBuyOrderShareImgUrl/"+ParamWXWebSit+"/"+wxParaComponentAppID+"/"+appId+"/"+gboId;
					gzh.put("shareImgUrl", gzhShareImgUrl);
					result.put("gzh", gzh);
				}
			}
			
			
		}
		
		return result;
	}
	


	
	public BufferedImage groupBuyOrderXCXShareImg(String state,Integer parent_gboId,Integer gboId,Integer xaId,Integer pid,String appId) throws IOException {
		String access_token = WxTokenAndJsticketCache.getAccess_token(appId);
		
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
		if("product".equals(state)) {
			String postQrcodeJson = "{\"scene\":\"agbs,"+xaId+","+pid+"\",\"page\":\"pages/goodsDetail/index\"}";
			logger.info("[url:{}] [postQrcodeJson:{}]",new Object[] {postQrcodeJson});
			return CredentialFileDownload.downLoadFromUrlPOST(url, postQrcodeJson,xaId);
		}else if("order".equals(state)) {
			String postQrcodeJson = "{\"scene\":\"agbs,"+parent_gboId+","+gboId+"\",\"page\":\"pages/purchase/index\"}";
			logger.info("[url:{}] [postQrcodeJson:{}]",new Object[] {postQrcodeJson});
			return CredentialFileDownload.downLoadFromUrlPOST(url, postQrcodeJson,xaId);
		}
		return null;
	}

	/**
	 * 查询当前用户当前活动是否有未支付拼团订单
	 * @param identityCode
	 * @param xaId
	 * @param pid
	 * @return
	 */
	public List<Map<String,Object>> selectClientNotPayGroupBuyOrder(String identityCode, Integer eid, Integer xaId, Integer pid){
		logger.info("[project:{}] [step:enter] [identityCode:{}] [eid:{}] [xaId:{}] [pid:{}]",
				new Object[] { WebConfig.projectName,identityCode,eid,xaId,pid});
		return groupBuyDao.selectClientNotPayGroupBuyOrder(identityCode, eid, xaId, pid);
	}
}
