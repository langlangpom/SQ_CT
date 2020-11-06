package com.evian.sqct.api.action;

import com.evian.sqct.annotation.TokenNotVerify;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.groupBuy.input.FindGroupBuyProductReqDTO;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseGroupBuyManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.code.AddPri;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2019年8月9日 上午11:59:54
 * @author XHX
 * @Description 拼团api
 */
@RestController
@RequestMapping("/evian/sqct/groupbuy")
public class GroupBuyAction  extends BaseAction {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseGroupBuyManager baseGroupBuyManager;
	
	@RequestMapping("findGroupBuyOrder.action")
	public ResultVO findGroupBuyOrder(String beginTime, String endTime, Integer eid, String orderNo, String nickName, Boolean isCommander, Boolean isRefund, String account, Integer groupBuyState, String paymentNo, String orderGroup, String eName, String pname, String pcode, String sdkType, Integer shopId, Boolean isFilterEndOrder, Integer sucMode, Integer minGroupBuyNum, Integer maxGroupBuyNum, Integer sortType, Integer clientId, Integer PageIndex, Integer PageSize, Boolean IsSelectAll) {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> result = baseGroupBuyManager.selectGroupBuyOrder(beginTime, endTime, eid, orderNo, nickName, isCommander, isRefund, account,
				groupBuyState, paymentNo, orderGroup, eName, pname, pcode, sdkType, shopId, isFilterEndOrder,sucMode, minGroupBuyNum, maxGroupBuyNum, sortType,clientId, PageIndex, PageSize, IsSelectAll);

		return new ResultVO(result);
	}
	
	@RequestMapping("findGroupBuyProduct.action")
	public ResultVO findGroupBuyProduct(@Valid FindGroupBuyProductReqDTO dto) {
		Map<String, Object> result = baseGroupBuyManager.selectGroupBuyProducts(dto);
		return new ResultVO(result);
	}

	/**
	 * 131.拼团商品详情
	 */
	@RequestMapping("getGroupBuyProductDetail.action")
	public String getGroupBuyProductDetail(String identityCode,Integer eid,Integer xaId,Integer pid,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseGroupBuyManager.getGroupBuyProductDetail(identityCode, eid, xaId, pid, appId);
		return getSysEnterPriseTemplate;
	}
	

	/**
	 * 140.保存拼团单据
	 */
	@RequestMapping("saveGroupBuyOrder.action")
	public String saveGroupBuyOrder(HttpServletRequest request, String identityCode, String openid, String orderJson, String appId,String body) throws Exception{
		JSONObject order = null;
		try {
			order = JSONObject.fromObject(orderJson);
			if(order.containsKey("sucMode")){
				int sucMode = order.getInt("sucMode");
				if(sucMode!=1&&sucMode!=2){
					order.put("sucMode",1);
				}
			}else{
				order.put("sucMode",1);
			}
		} catch (Exception e) {
			String parString = CallBackPar.getParString();
			return setERROR_PARAM(parString);

		}
		String getSysEnterPriseTemplate = baseGroupBuyManager.saveGroupBuyOrder(identityCode,openid,order.toString(),appId,getIp(request),body);
		return getSysEnterPriseTemplate;
	}
	
	/**
	 * 获取微信分享信息
	 * @param eid
	 * @param parent_gboId
	 * @param gboId
	 * @param xaId
	 * @param pid
	 * @param state   product 是商品分享  order是订单分享
	 * @return
	 */
	@RequestMapping("findGroupBuyShareInfo.action")
	public ResultVO findGroupBuyShareInfo(Integer eid,Integer parent_gboId,Integer gboId,Integer xaId,Integer pid,String state) {
		if(eid==null||state==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> result = baseGroupBuyManager.selectGroupBuyShareInfo(eid, parent_gboId, gboId,xaId,pid,state);
		return new ResultVO(result);
	}
	
	@RequestMapping("gzhGroupBuyOrderShareImgUrl/{paramWXWebSit}/{wxParaComponentAppID}/{appId}/{gboId}")
	@TokenNotVerify
	public void gzhGroupBuyOrderShareImgUrl(@PathVariable(value = "paramWXWebSit") String paramWXWebSit,
			@PathVariable(value = "wxParaComponentAppID") String wxParaComponentAppID,
			@PathVariable(value = "appId") String appId,
			@PathVariable(value = "gboId") String gboId,
			HttpServletResponse response) {
		String gzhShareUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=https%3A%2F%2F" + paramWXWebSit + "%2Fweixin%2Foauth2&response_type=code&scope=snsapi_base&state=groupBookingOrderDetailsEVIAN"+gboId+"&component_appid=" + wxParaComponentAppID + "#wechat_redirect";
		try {
			BufferedImage ImageOutput = AddPri.generateQRCodeOutput(gzhShareUrl, 250, 250, "png");
			response.setContentType("image/png");
			ImageIO.write(ImageOutput, "png", response.getOutputStream());
		} catch (Exception e) {
			logger.error("生成二维码错误:{}",e);
		}
	}
	@RequestMapping("gzhGroupBuyProductShareImgUrl/{paramWXWebSit}/{wxParaComponentAppID}/{appId}/{xaId}/{pid}")
	@TokenNotVerify
	public void gzhGroupBuyProductShareImgUrl(@PathVariable(value = "paramWXWebSit") String paramWXWebSit,
			@PathVariable(value = "wxParaComponentAppID") String wxParaComponentAppID,
			@PathVariable(value = "appId") String appId,
			@PathVariable(value = "xaId") Integer xaId,
			@PathVariable(value = "pid") Integer pid,
			HttpServletResponse response) {
		String gzhShareUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=https%3A%2F%2F" + paramWXWebSit + "%2Fweixin%2Foauth2&response_type=code&scope=snsapi_base&state=groupBookingProductDetailEVIAN"+xaId+"EVIAN"+pid+"&component_appid=" + wxParaComponentAppID + "#wechat_redirect";
		try {
			BufferedImage ImageOutput = AddPri.generateQRCodeOutput(gzhShareUrl, 250, 250, "png");
			response.setContentType("image/png");
			ImageIO.write(ImageOutput, "png", response.getOutputStream());
		} catch (Exception e) {
			logger.error("生成二维码错误:{}",e);
		}
	}
	@RequestMapping("xcxGroupBuyProductShareImgUrl")
	@TokenNotVerify
	public void xcxGroupBuyProductShareImgUrl(String state,Integer parent_gboId,Integer gboId,Integer xaId,Integer pid,String appId,HttpServletResponse response) {
		
		if(!StringUtils.isEmpty(state)) {
			try {
				BufferedImage ImageOutput = baseGroupBuyManager.groupBuyOrderXCXShareImg(state, parent_gboId, gboId, xaId, pid, appId);
				response.setContentType("image/png");
				ImageIO.write(ImageOutput, "png", response.getOutputStream());
			} catch (Exception e) {
				logger.error("生成二维码错误：{}",e);
			}
		}
	}
	

	/**
	 * 146.拼团订单继续支付、取消订单、取消支付操作
	 */
	@RequestMapping("groupBuyOrderUpdateStatus.action")
	public String groupBuyOrderUpdateStatus(HttpServletRequest request,String identityCode,Integer gboId,Integer operateId,String appId,String body) throws Exception{
		String getSysEnterPriseTemplate = baseGroupBuyManager.groupBuyOrderUpdateStatus(identityCode,gboId,operateId,appId,getIp(request),body);
		return getSysEnterPriseTemplate;
	}

	/**
	 * 155.查询当前用户当前活动是否有未支付拼团订单
	 * @param identityCode
	 * @param eid
	 * @param xaId
	 * @param pid
	 * @return
	 */
	@RequestMapping("findClientNotPayGroupBuyOrder.action")
	public ResultVO findClientNotPayGroupBuyOrder(String identityCode, Integer eid, Integer xaId, Integer pid) {
		if(eid==null||identityCode==null||xaId==null||pid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> result = baseGroupBuyManager.selectClientNotPayGroupBuyOrder(identityCode, eid, xaId, pid);
		if(result!=null&&result.size()>0){
			return new ResultVO(result.get(0));
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gboId",0);
			return new ResultVO(map);
		}


	}
}
