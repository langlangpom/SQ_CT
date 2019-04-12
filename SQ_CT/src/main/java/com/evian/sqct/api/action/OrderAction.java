package com.evian.sqct.api.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.order.EOrderLogistics;
import com.evian.sqct.bean.order.ESysBaseType;
import com.evian.sqct.bean.order.Order;
import com.evian.sqct.bean.order.OrderDetail;
import com.evian.sqct.bean.order.OrderModel;
import com.evian.sqct.bean.order.OrderProductInfo;
import com.evian.sqct.service.BaseOrderManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.DES.WebConfig;

/**
 * @date   2018年10月8日 上午10:39:16
 * @author XHX
 * @Description 订单action
 */
@RestController
@RequestMapping("/evian/sqct/order")
public class OrderAction extends BaseAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseOrderManager baseOrderManager;
	

	/**
	 * 17.显示订单信息
	 * @param eid				企业ID
	 * @param shopId			店铺ID
	 * @param beginTime			下单时间
	 * @param endTime			下单时间
	 * @param status			状态(0:待审核 1:待派送 2:已派送 3:已送到  -1:已取消)
	 * @param pageIndex			第几页(不传的话 默认显示全部)
	 * @param pageSize			每页显示多少行
	 * @return
	 */
	@RequestMapping("showOrderData.action")
	public Map<String, Object> showOrderData(Integer eid,Integer shopId,String beginTime,String endTime,String orderNo,Integer status,Integer PageIndex,Integer PageSize) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		long action = new Date().getTime();
		if(eid==null||shopId==null||beginTime==null||endTime==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			Order order = new Order();
			order.setEid(eid);
			order.setShopId(shopId);
			order.setEndTime(endTime);
			order.setBeginTime(beginTime);
			order.setStatus(status);
			order.setPageIndex(PageIndex);
			order.setPageSize(PageSize);
			order.setOrderNo(orderNo);
			Map<String, Object> resultMap = baseOrderManager.findOrderByShopId_v2(order);
			
			setData(parMap, resultMap);
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		long time = new Date().getTime()-action;
		logger.info("[showOrderDataTime:{}]",time);
		return parMap;
	}
	
	/**
	 * 17.显示订单信息(查询太慢)
	 * @param eid				企业ID
	 * @param shopId			店铺ID
	 * @param beginTime			下单时间
	 * @param endTime			下单时间
	 * @param status			状态(0:待审核 1:待派送 2:已派送 3:已送到  -1:已取消)
	 * @param pageIndex			第几页(不传的话 默认显示全部)
	 * @param pageSize			每页显示多少行
	 * @return
	 */
//	@RequestMapping("showOrderData.action")
	public Map<String, Object> showOrderData(Integer eid,Integer shopId,String beginTime,String endTime,String orderNo,Integer status,String eName,Integer PageIndex,Integer PageSize) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		long action = new Date().getTime();
		if(eid==null||shopId==null||beginTime==null||endTime==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			Order order = new Order();
			order.setEid(eid);
			order.setShopId(shopId);
			order.setEndTime(endTime);
			order.setBeginTime(beginTime);
			order.setStatus(status);
			order.setPageIndex(PageIndex);
			order.setPageSize(PageSize);
			order.setOrderNo(orderNo);
			order.seteName(eName);
			Map<String, Object> OrderByShopIdMap = baseOrderManager.findOrderByShopId(order);
			
			List<OrderModel> findOrderByShopId = OrderByShopIdMap.get("orders")==null?new ArrayList<OrderModel>():(List<OrderModel>) OrderByShopIdMap.get("orders");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < findOrderByShopId.size(); i++) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderId", findOrderByShopId.get(i).getOrderId());
				map.put("orderNo", findOrderByShopId.get(i).getOrderNo());
				map.put("phone", findOrderByShopId.get(i).getPhone());
				map.put("contacts", findOrderByShopId.get(i).getContacts());
				map.put("dateCreated", findOrderByShopId.get(i).getDateCreated());
				map.put("receivableTotal", findOrderByShopId.get(i).getReceivableTotal());
				map.put("sendAddress", findOrderByShopId.get(i).getSendAddress());
				map.put("mobileType", findOrderByShopId.get(i).getMobileType());
				map.put("orderRemark", findOrderByShopId.get(i).getOrderRemark());
				map.put("status", findOrderByShopId.get(i).getStatus());
				map.put("clientId", findOrderByShopId.get(i).getClientId());
				map.put("payMode", findOrderByShopId.get(i).getPayMode());
				List<OrderDetail> details = baseOrderManager.selectOrderDetailByOrderId(findOrderByShopId.get(i).getOrderId());
				List<Map<String, Object>> detailsList = new ArrayList<Map<String, Object>>();
				for (OrderDetail orderDetail : details) {
					Map<String, Object> detailMap = new HashMap<String, Object>();
					detailMap.put("pname", orderDetail.getPname());
					detailMap.put("pictureUrl", orderDetail.getPictureUrl());
					detailMap.put("price", orderDetail.getVipPrice());
					detailMap.put("number", orderDetail.getNumber());
					detailMap.put("settlementType", orderDetail.getSettlementType());
					detailMap.put("id", orderDetail.getId());
					detailsList.add(detailMap);
				}
				map.put("orderProductDetail", detailsList);
				list.add(map);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("orders", list);
			resultMap.put("Count", OrderByShopIdMap.get("Count"));
			if(PageIndex!=null){
				resultMap.put("page", PageIndex);
			}
			setData(parMap, resultMap);
			
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		long time = new Date().getTime()-action;
		logger.info("[showOrderDataTime:{}]",time);
		return parMap;
	}
	
	/**
	 * 26.显示订单商品信息
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("showOrderProductData.action")
	public Map<String, Object> showOrderProductData(Integer eid,Integer orderId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||orderId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			List<OrderProductInfo> findOrderProductInfo = baseOrderManager.findOrderProductInfo(orderId, eid);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("orderProducts", findOrderProductInfo);
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
	 * 18.显示余额信息
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("showBalanceData.action")
	public Map<String, Object> showBalanceData() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		
		return parMap;
	}
		
	/**
	 * 27.催单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("ordercuishui.action")
	public Map<String, Object> ordercuishui(Integer eid,Integer orderId,String remark) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||orderId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			if(remark==null){
				remark = "赶紧整";
			}
			String cuidan = baseOrderManager.cuidan(eid, orderId, remark);
			if(!"1".equals(cuidan)){
				setCode(parMap, 150);
				setMessage(parMap, cuidan);
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
	 * 28.订单申请退款
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("applyForDrawback.action")
	public Map<String, Object> applyForDrawback(Integer orderId,Integer userId,String th_type,String th_reason,String th_remark,String th_CreateUser) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null||userId==null||th_type==null||th_reason==null||th_remark==null||th_CreateUser==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			String drawback = baseOrderManager.drawback(orderId, userId, th_type, th_reason, th_remark, th_CreateUser);
			if(!"1".equals(drawback)){
				setCode(parMap, 150);
				setMessage(parMap, drawback);
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
	 * 29.取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("cancellationOfOrder.action")
	public Map<String, Object> cancellationOfOrder(Integer orderId,Integer clientId,Integer eid,String cancelReason,String e_order_detail_ID) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null||clientId==null||eid==null||cancelReason==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			JSONArray parseArray = null;
			if(!StringUtils.isEmpty(e_order_detail_ID)) {
				parseArray = JSONArray.parseArray(e_order_detail_ID);
			}
			String cancellationOfOrder = baseOrderManager.cancellationOfOrder(orderId, clientId, eid, cancelReason,parseArray);
			if(!"1".equals(cancellationOfOrder)){
				setCode(parMap, 150);
				setMessage(parMap, cancellationOfOrder);
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
	 * 29.取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("orderChangeInformation.action")
	public Map<String, Object> orderChangeInformation(Integer orderId,String orderNo,Integer eid) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			

			// 添加变更信息
			List<Map<String, Object>> changeInformation = new ArrayList<Map<String, Object>>();
//			resultMap.put("changeInformation", changeInformation);
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
	 * 92.查询系统默认取消原因
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("findSysOrderCancelCause.action")
	public Map<String, Object> findSysOrderCancelCause(Integer eid, String typeClass) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<ESysBaseType> selectSysOrderCancelCause = baseOrderManager.selectSysOrderCancelCause(eid, typeClass);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("cause", selectSysOrderCancelCause);
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
	 * 92.查询系统默认取消原因
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("updateOrderAccomplish.action")
	public Map<String, Object> updateOrderAccomplish(Integer eid, Integer orderId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			 Integer updateOrderAccomplish = baseOrderManager.updateOrderAccomplish(orderId,eid);
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
	 * 99.预计送达发送消息并将订单状态改为已配送
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("sendJpushMessageAndUpdateOrderStatus.action")
	public Map<String, Object> sendJpushMessageAndUpdateOrderStatus(Integer eid, Integer orderId,Integer contactsId,String contacts,String phone,String arriveTime) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null||eid==null||contactsId==null||contacts==null||phone==null||arriveTime==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Integer updateOrderAccomplish = baseOrderManager.sendJpushMessageAndUpdateOrderStatus(orderId, eid, contactsId, contacts, phone, arriveTime);
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
	 * 100.查询订单物流
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("findLogistics.action")
	public Map<String, Object> findLogistics(Integer orderId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<EOrderLogistics> selectEOrderLogisticsByOrderId = baseOrderManager.selectEOrderLogisticsByOrderId(orderId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("logistics", selectEOrderLogisticsByOrderId);
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
