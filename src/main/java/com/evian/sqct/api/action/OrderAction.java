package com.evian.sqct.api.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.evian.sqct.annotation.ResponseNotAdvice;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.order.request.ProcBackstageOrderTuihuoSelectReqDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseOrderManager;
import com.evian.sqct.util.CallBackPar;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 17.根据用户权限显示分配的订单信息
	 * @param eid				企业ID
	 * @param shopId			店铺ID
	 * @param beginTime			下单时间
	 * @param endTime			下单时间
	 * @param status			状态(0:待审核 1:待派送 2:已派送 3:已送到  -1:已取消)
	 * @param isTicket			是否只查询电子票订单
	 * @param payMode			支付方式  1:货到付款 2:在线支付 3:电子水票
	 * @param pageIndex			第几页(不传的话 默认显示全部)
	 * @param pageSize			每页显示多少行
	 * @return
	 */
	@RequestMapping("showOrderData.action")
	public ResultVO showOrderData(Integer accountId, Integer eid, Integer shopId, String beginTime, String endTime, String orderNo, Integer status, Boolean isTicket, Integer payMode, Integer PageIndex, Integer PageSize) {
		long action = System.currentTimeMillis();
		if(eid==null||shopId==null||beginTime==null||endTime==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		
		Order order = new Order();
		order.setEid(eid);
		order.setShopId(shopId);
		order.setEndTime(endTime);
		order.setBeginTime(beginTime);
		order.setStatus(status);
		order.setPageIndex(PageIndex);
		order.setPageSize(PageSize);
		order.setOrderNo(orderNo);
		order.setPayMode(payMode);
//			if(accountId!=null) {
			Map<String, Object> resultMap = baseOrderManager.findOrderByShopId_v3(accountId,order,isTicket);

		/*}else {
			Map<String, Object> resultMap = baseOrderManager.findOrderByShopId_v2(order,isTicket);
			setData(parMap, resultMap);
		}*/
			
		long time = System.currentTimeMillis()-action;
		logger.info("[showOrderDataTime:{}]",time);
		return new ResultVO(resultMap);
	}
	
	
	/**
	 * 17.显示订单信息 2019-06-04 改为根据用户权限查订单（上面那个接口）
	 * @param eid				企业ID
	 * @param shopId			店铺ID
	 * @param beginTime			下单时间
	 * @param endTime			下单时间
	 * @param status			状态(0:待审核 1:待派送 2:已派送 3:已送到  -1:已取消)
	 * @param isTicket			是否只查询电子票订单
	 * @param payMode			支付方式  1:货到付款 2:在线支付 3:电子水票
	 * @param pageIndex			第几页(不传的话 默认显示全部)
	 * @param pageSize			每页显示多少行
	 * @return
	 */
//	@RequestMapping("showOrderData.action")
	public ResultVO showOrderData(Integer eid,Integer shopId,String beginTime,String endTime,String orderNo,Integer status,Boolean isTicket,Integer payMode,Integer PageIndex,Integer PageSize) {
		long action = System.currentTimeMillis();
		if(eid==null||shopId==null||beginTime==null||endTime==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		
		Order order = new Order();
		order.setEid(eid);
		order.setShopId(shopId);
		order.setEndTime(endTime);
		order.setBeginTime(beginTime);
		order.setStatus(status);
		order.setPageIndex(PageIndex);
		order.setPageSize(PageSize);
		order.setOrderNo(orderNo);
		order.setPayMode(payMode);
		Map<String, Object> resultMap = baseOrderManager.findOrderByShopId_v2(order,isTicket);
			
		long time = System.currentTimeMillis()-action;
		logger.info("[showOrderDataTime:{}]",time);
		return new ResultVO(resultMap);
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
	public ResultVO showOrderData(Integer eid,Integer shopId,String beginTime,String endTime,String orderNo,Integer status,String eName,Integer PageIndex,Integer PageSize) {
		long action = System.currentTimeMillis();
		if(eid==null||shopId==null||beginTime==null||endTime==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		
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

		long time = System.currentTimeMillis()-action;
		logger.info("[showOrderDataTime:{}]",time);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 26.显示订单商品信息
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("showOrderProductData.action")
	public ResultVO showOrderProductData(Integer eid,Integer orderId) {
		if(eid==null||orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		
		List<OrderProductInfo> findOrderProductInfo = baseOrderManager.findOrderProductInfo(orderId, eid);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("orderProducts", findOrderProductInfo);

		return new ResultVO(resultMap);
	}
		
	/**
	 * 18.显示余额信息
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("showBalanceData.action")
	public ResultVO showBalanceData() {

		return new ResultVO();
	}
		
	/**
	 * 27.催单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("ordercuishui.action")
	public ResultVO ordercuishui(Integer eid,Integer orderId,String remark) {
		if(eid==null||orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		
		if(remark==null){
			remark = "赶紧整";
		}
		String cuidan = baseOrderManager.cuidan(eid, orderId, remark);
		if(!"1".equals(cuidan)){
			throw new ResultException(cuidan);
		}
		return new ResultVO();
	}
		
	/**
	 * 28.订单申请退款
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("applyForDrawback.action")
	public ResultVO applyForDrawback(Integer orderId,Integer userId,String th_type,String th_reason,String th_remark,String th_CreateUser) {
		if(orderId==null||userId==null||th_type==null||th_reason==null||th_remark==null||th_CreateUser==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}

		String drawback = baseOrderManager.drawback(orderId, userId, th_type, th_reason, th_remark, th_CreateUser);
		if(!"1".equals(drawback)){
			throw new ResultException(drawback);
		}
		return new ResultVO();
	}
	
	/**
	 * 29.取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("cancellationOfOrder.action")
	public ResultVO cancellationOfOrder(Integer orderId,Integer clientId,Integer eid,String cancelReason,String e_order_detail_ID,Integer accountId) {
		if(orderId==null||clientId==null||eid==null||cancelReason==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		JSONArray parseArray = null;
		if(!StringUtils.isEmpty(e_order_detail_ID)) {
			try {
				parseArray = JSONArray.parseArray(e_order_detail_ID);
			} catch (JSONException e) {
				return new ResultVO(ResultCode.CODE_ERROR_PARAM);
			}
		}
		String cancellationOfOrder = baseOrderManager.cancellationOfOrder(orderId, clientId, eid, cancelReason,parseArray,accountId);
		if(!"1".equals(cancellationOfOrder)){
			throw new ResultException(cancellationOfOrder);
		}
		StringBuilder logisticsDescribe = new StringBuilder("商家取消单据，原因：");
		if(parseArray==null){
			logisticsDescribe.append(cancelReason);
		}else{
			logisticsDescribe.append(cancelReason);
			// 部分取消商品名
		}
		// 物流消息
		baseOrderManager.saveEOrderLogisticsByOrderId(new EOrderLogistics(orderId,1,logisticsDescribe.toString()));
		if(accountId==null){
			return new ResultVO();
		}
		// 变更消息
		ProcAppOrderSendSaveLogisticsDTO dto = new ProcAppOrderSendSaveLogisticsDTO();
		dto.setMsgType(OrderLogisticsMsgType.APP_CANCEL.getType());
		dto.setOrderId(orderId);
		dto.setSend_accountId(accountId);
		dto.setDataReson(3);
		dto.setCreater(accountId+"");
		dto.setLogMsg("水趣商户取消订单");
		baseOrderManager.addOrderSendLogs(dto);
		return new ResultVO();
	}
	
	/**
	 * 29.取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("orderChangeInformation.action")
	public ResultVO orderChangeInformation(Integer orderId,String orderNo,Integer eid) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}


		// 添加变更信息
		List<Map<String, Object>> changeInformation = new ArrayList<Map<String, Object>>();
//			resultMap.put("changeInformation", changeInformation);
		return new ResultVO();
	}
		

	/**
	 * 92.查询系统默认取消原因
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("findSysOrderCancelCause.action")
	public Map<String, Object> findSysOrderCancelCause(Integer eid, String typeClass) {
		List<ESysBaseType> selectSysOrderCancelCause = baseOrderManager.selectSysOrderCancelCause(eid, typeClass);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cause", selectSysOrderCancelCause);
		return resultMap;
	}
	

	/**
	 * 93.订单配送完成
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("updateOrderAccomplish.action")
	public ResultVO updateOrderAccomplish(Integer eid, Integer orderId,String account) {
		if(orderId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		 String updateOrderAccomplish = baseOrderManager.updateOrderAccomplish(orderId,eid,account);
		 if(!"1".equals(updateOrderAccomplish)){
		 	if(StringUtils.isBlank(updateOrderAccomplish)){
		 		updateOrderAccomplish = "单据信息已变更";
			}
		 	throw new ResultException(updateOrderAccomplish);
		 }
		baseOrderManager.saveEOrderLogisticsByOrderId(new EOrderLogistics(orderId,1,"订单已配送完成，感谢您的支持！"));
		return new ResultVO();
	}
	
	/**
	 * 99.预计送达发送消息并将订单状态改为已配送
	 *  2019-06-10 黄亮东说不需要改状态 只用发消息
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("sendJpushMessageAndUpdateOrderStatus.action")
	public ResultVO sendJpushMessageAndUpdateOrderStatus(Integer eid, Integer orderId,Integer contactsId,String contacts,String phone,String arriveTime) {
		if(orderId==null||eid==null||contactsId==null||contacts==null||phone==null||arriveTime==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Integer updateOrderAccomplish = baseOrderManager.sendJpushMessageAndUpdateOrderStatus(orderId, eid, contactsId, contacts, phone, arriveTime);

		return new ResultVO();
	}
	

	/**
	 * 100.查询订单物流
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("findLogistics.action")
	public ResultVO findLogistics(Integer orderId) {
		if(orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<EOrderLogistics> selectEOrderLogisticsByOrderId = baseOrderManager.selectEOrderLogisticsByOrderId(orderId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("logistics", selectEOrderLogisticsByOrderId);

		return new ResultVO(resultMap);
	}
	
	/**
	 * 103.订单退款
	 * @param orderId
	 * @param eid
	 * @param refundMoney	退款金额
	 * @param auditType		自动退款或自定义退款
	 * @param taAccount		退款人
	 * @param auditUser		操作人
	 * @param auditRemark	备注
	 * @return
	 */
	@RequestMapping("orderRefund.action")
	@ResponseNotAdvice
	public Object orderRefund(Integer orderId,Integer eid,Double refundMoney,String auditType,String taAccount,String auditUser,String auditRemark,Integer ifaudit) {
		if(orderId==null||eid==null||auditUser==null||auditRemark==null||ifaudit==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		if(ifaudit.intValue()==1) {
			if(refundMoney==null||auditType==null) {
				return new ResultVO(ResultCode.CODE_ERROR_PARAM);
			}
		}

		return baseOrderManager.orderRefund(eid, orderId, refundMoney, auditType, taAccount, auditUser, auditRemark,ifaudit);
	}

	/**
	 * 104.查询退款退货申请订单
	 * @param orderId			订单ID
	 * @param eid				企业ID
	 * @return
	 */
	@RequestMapping("findTuihuo.action")
	public Map<String, Object> findTuihuo(ProcBackstageOrderTuihuoSelectReqDTO dto) {
		Map<String, Object> resultMap = baseOrderManager.selectTuihuo(dto);
		return resultMap;
	}
	
	/**
	 * 105.查询退款日志
	 * @param orderId
	 * @return
	 */
	@RequestMapping("findRefundLog.action")
	public ResultVO findRefundLog(Integer orderId) {
		if(orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> selectRefundLog = baseOrderManager.selectRefundLog(orderId);
		return new ResultVO(selectRefundLog);
	}
	
	/**
	 * 106.数据结存
	 * @param orderId
	 * @return
	 */
	@RequestMapping("findBalanceEvery.action")
	public Map<String, Object> findBalanceEvery(String beginTime,String endTime,Integer eid,String eName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> balance = baseOrderManager.selectBalanceEvery(beginTime, endTime, eid, eName, PageIndex, PageSize, IsSelectAll);
		return balance;
	}
	
	/**
	 * 111.店长分配订单
	 * 一分配就是派送中
	 * @param orderId
	 * @return
	 */
	@RequestMapping("shopManagerAllotOrder.action")
	public ResultVO shopManagerAllotOrder(Integer manager_accountId,Integer send_accountId,Integer eid,Integer shopId,String orderIds,String remark) {
		if(manager_accountId==null||send_accountId==null||eid==null||shopId==null||orderIds==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}

		JSONArray os = null;
		try {
			os = JSONArray.parseArray(orderIds);
		} catch (Exception e) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		int o = baseOrderManager.insertDeliverymanOrder(manager_accountId, send_accountId, eid, shopId, os, 2, remark);

		return new ResultVO();
	}
	
	@RequestMapping("getStatusOrders.action")
	public ResultVO getStatusOrders(Integer statusId, Integer pageIndex, Integer clientId,Integer eid,Integer shopId) {
		if(clientId==null||eid==null||shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> result = baseOrderManager.getStatusOrders(statusId, pageIndex, clientId, eid,shopId);
		return new ResultVO(result);
	}

	/**
	 *169.推客电子票兑换汇总报表
	 * @return
	 */
	@RequestMapping("findEticketTuiKeStatistics.action")
	public Map<String, Object> findEticketTuiKeStatistics(String beginTime,String endTime,Integer eid,String tuiKeAccount,String tuiKeManageAccount,String pname,String account,Integer status,Boolean isHistory,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> map = baseOrderManager.Proc_Backstage_order_detail_eticketTuiKe_select(beginTime, endTime, eid, tuiKeAccount, tuiKeManageAccount, pname, account, status, isHistory, PageIndex, PageSize, IsSelectAll);
		return map;
	}
	/**
	 *170.水趣中台-近期电子票配送合计
	 * @return
	 */
	@RequestMapping("findEticketTuiKeSelectStatistics.action")
	public ResultVO findEticketTuiKeSelectStatistics(String beginTime,String endTime,Integer eid,String tuiKeAccount,String tuiKeManageAccount,String pname,Integer status,Boolean isHistory,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		if(isHistory==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> map = baseOrderManager.Proc_Backstage_order_detail_eticketTuiKe_select_statistics(beginTime, endTime, eid, tuiKeAccount, tuiKeManageAccount, pname, status, isHistory, PageIndex, PageSize, IsSelectAll);
		return new ResultVO(map);
	}

	/**
	 *186.中台及APP端订单派单处理
	 * @return
	 */
	@RequestMapping("appOrderSendSaveUP.action")
	public ResultVO appOrderSendSaveUP(@Valid ProcAppOrderSendSaveUPDTO procAppOrderSendSaveUPDTO) {
		String result = baseOrderManager.appOrderSendSaveUP(procAppOrderSendSaveUPDTO);
		if(!"1".equals(result)){
			if(StringUtils.isBlank(result)){
				// 订单状态已变更cancellationOfOrder
				return new ResultVO(ResultCode.CODE_ERROR_ORDER_ALTERATION);
			}else{
				throw new ResultException(result);
			}
		}else{
			baseOrderManager.operOrderSendLogs(procAppOrderSendSaveUPDTO);
		}
		return new ResultVO();
	}


	/**
	 * 187.根据用户权限显示分配的订单信息
	 * @param eid				企业ID
	 * @param shopId			店铺ID
	 * @param beginTime			下单时间
	 * @param endTime			下单时间
	 * @param status			状态(0:待审核 1:待派送 2:已派送 3:已送到  -1:已取消)
	 * @param isTicket			是否只查询电子票订单
	 * @param payMode			支付方式  1:货到付款 2:在线支付 3:电子水票
	 * @param pageIndex			第几页(不传的话 默认显示全部)
	 * @param pageSize			每页显示多少行
	 * @return [eid=1, shopId=369, accountId=null, beginTime=null, endTime=null, status=1, payMode=null, orderNo=null, isTicket=null, iRows=0, PageSize=10]]
	 */
	@RequestMapping("sendOrderDataSelect.action")
	public Map<String, Object> sendOrderDataSelect(@Valid ProcAppOrderSendSelectDTO dto) {
		return baseOrderManager.findOrderByShopId_v4(dto);
	}

	/**
	 * 188.查询配送订单日志跟踪
	 * @param orderId
	 * @return
	 */
	@RequestMapping("sendOrdertailAfter.action")
	public ResultVO sendOrdertailAfter(Integer orderId){
		if(orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> maps = baseOrderManager.sendOrdertailAfter(orderId);
		Map<String, Object> map = new HashMap<>(1);
		map.put("logs",maps);
		return new ResultVO(map);
	}
	/**
	 * 188.查询配送订单日志跟踪
	 * @param orderId
	 * @return
	 */
	@RequestMapping("addOrderSendLogs.action")
	public ResultVO addOrderSendLogs(@Valid ProcAppOrderSendSaveLogisticsDTO dto){
		String result = baseOrderManager.addOrderSendLogs(dto);
		if(!"E00000".equals(result)){
			// 操作失败
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 199.退押申请记录
	 * @param orderId
	 * @return
	 */
	@RequestMapping("findTuiyaRecord.action")
	public Map<String,Object> findTuiyaRecord(ProcBackstageClientPledgeTuiyaSelectDTO dto){
		return baseOrderManager.selectTuiyaRecord(dto);
	}

	/**
	 * 205.查询变更通知列表
	 * @param orderId
	 * @return
	 */
	@RequestMapping("findSendOrderLogisticsByAccountId.action")
	public Map<String,Object> findSendOrderLogisticsByAccountId(@Valid FindSendOrderLogisticsByAccountIdReqDTO dto){
		return  baseOrderManager.selectSendOrderLogisticsByAccountId(dto);
	}



}
