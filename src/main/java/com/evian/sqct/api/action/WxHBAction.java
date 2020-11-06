package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.util.WXHB;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseWxHBManager;
import com.evian.sqct.service.WxPayService;
import com.evian.sqct.util.XmlStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @date   2018年9月29日 上午11:50:10
 * @author XHX
 * @Description 微信红包action
 */
@RestController
@RequestMapping("/redPacket")
public class WxHBAction  extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	


	@Autowired
	private WxPayService wxPayService;
	
	@Autowired
	private BaseWxHBManager baseWxHBManager;

	
	/**
	 * 
	 * @param request
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("commonRedPacketSendDetection")
	public ResultVO commonRedPacketSendDetection(HttpServletRequest request,String openId,Integer recordId) throws Exception{
		if(openId==null) {
			return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> sendList = null;
		if(recordId!=null) {
			sendList = baseWxHBManager.redpackSserRecordResendSelect(openId, recordId);
		}else {
			
			sendList = baseWxHBManager.redpackSserRecordSendSelect(openId);
		}
		
		for (Map<String, Object> map : sendList) {
			if(map.get("tag")!=null&&!"1".equals(map.get("tag").toString())) {
				throw BaseRuntimeException.jointCodeAndMessage("E00001",map.get("tag").toString());
			}
			String mchId = map.get("mchId").toString();
			
			WXHB w = new WXHB();
			UUID uuid=UUID.randomUUID();
		    String str = uuid.toString(); 
		    String uuidStr=str.replace("-", "");
			w.setNonce_str(uuidStr);
			w.setMchBillno(map.get("sendSign").toString());
			w.setMchId(mchId);
			w.setWxappid(map.get("appId").toString());
//			w.setSendName("水趣驿站欢乐送");	// 商户名称
			w.setSendName(map.get("liteappName")==null?"水趣驿站欢乐送":map.get("liteappName").toString());	// 商户名称
			w.setReOpenid(map.get("openId").toString());
			if(map.get("redpackMoney")!=null) {
				BigDecimal bigDecimal = new BigDecimal(map.get("redpackMoney").toString());
				BigDecimal big100 = new BigDecimal("100");
				w.setTotalAmount(big100.multiply(bigDecimal).intValue()+"");
			}
			w.setTotalNum("1");
//			w.setWishing("领红包的最美");	// 红包祝福语
			w.setWishing(map.get("synopsis")==null?"红包发送":map.get("synopsis").toString());	// 红包祝福语
			w.setClientIp(getIp(request));
//			w.setActName("水趣驿站欢乐送");	// 活动名称
			w.setActName(map.get("activityName")==null?"水趣驿站欢乐送":map.get("activityName").toString());	// 活动名称
			w.setRemark(map.get("synopsis")==null?"红包发送":map.get("synopsis").toString());			// 备注信息  不显示的
			w.setAppKey(map.get("partnerKey").toString());
			try {
				String sendredpack = wxPayService.sendredpack(w);

				Map<String, Object> sendredpackMap = XmlStringUtil.stringToXMLParse(sendredpack);
				
				String tag = baseWxHBManager.redpackUserRecordWechatSendLogOperat(sendredpackMap);
				logger.info("[发放结果记录TAG:{}]",tag);
			} catch (BaseRuntimeException.ResultErrorCodeMessage e) {
				// TODO Auto-generated catch block
				logger.error("[红包发放错误 Exception:{}]",new Object[] {e});
				throw e;

			}
		}
		
		return new ResultVO();
	}

	/**
	 * 服务Api
	 * @param status
	 * @return
	 */
	@RequestMapping("gethbinfo")
	public ResultVO gethbinfo(String status){
		// 红包查询
		if("SELECT_HBZT".equals(status)) {
			try {
				baseWxHBManager.recordSelectForValid();
			} catch (Exception e) {
				logger.error("[e:{}]",new Object[] {e.getMessage()});
			}
		}else {
			
		}
		
		return new ResultVO();
	}

}
