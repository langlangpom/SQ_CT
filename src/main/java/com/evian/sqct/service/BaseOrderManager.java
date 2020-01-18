package com.evian.sqct.service;

import com.alibaba.fastjson.JSONArray;
import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.bean.util.WxPayRefundModel;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseOrderManager extends BaseManager{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("orderDao")
	private IOrderDao orderDao;
	
	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;
	
	public Map<String, Object>  findOrderByShopId(Order order){
		logger.info("[project:{}] [step:enter] [order:{}]",
				new Object[] { WebConfig.projectName,order});
		return orderDao.selectOrderByShopId(order);
	}
	
	public Map<String, Object>  findOrderByShopId_v2(Order order,Boolean isTicket){
		logger.info("[project:{}] [step:enter] [order:{}]",
				new Object[] { WebConfig.projectName,order});
		Map<String, Object> OrderByShopIdMap = null;
		
		// 是否只查询电子票订单
		if(isTicket!=null&&isTicket) {
			OrderByShopIdMap = orderDao.selectTicketOrderByOrder(order);
		}else {
			OrderByShopIdMap = orderDao.selectOrderByShopId_v2(order);
		}
		
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
			map.put("manager_account", findOrderByShopId.get(i).getManager_account());
			map.put("send_account", findOrderByShopId.get(i).getSend_account());
			map.put("manager_remark", findOrderByShopId.get(i).getManager_remark());
			map.put("send_remark", findOrderByShopId.get(i).getSend_remark());
			
			// 2019-05-06   -1不能取消 因为-1 一送就已经回单完成  app同中台一样 -1的单 取消的按钮隐藏
			map.put("sourceGroup", findOrderByShopId.get(i).getSourceGroup());
			List<OrderDetail> details = orderDao.selectOrderDetailByOrderId(findOrderByShopId.get(i).getOrderId());
			List<Map<String, Object>> detailsList = new ArrayList<Map<String, Object>>();
			for (OrderDetail orderDetail : details) {
				Map<String, Object> detailMap = new HashMap<String, Object>();
				detailMap.put("pname", orderDetail.getPname());
				detailMap.put("pictureUrl", orderDetail.getPictureUrl());
				if(orderDetail.getShopPrice()!=null) {
					detailMap.put("price", orderDetail.getShopPrice());
				}else {
					detailMap.put("price", orderDetail.getVipPrice());
				}
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
		if(order.getPageIndex()!=null){
			resultMap.put("page", order.getPageIndex());
		}
		return resultMap;
	}
	
	/**
	 *  根据用户权限查询单据
	 * @param order
	 * @param isTicket
	 * @return
	 */
	public Map<String, Object>  findOrderByShopId_v3(Integer accountId,Order order,Boolean isTicket){
		logger.info("[project:{}] [step:enter] [accountId:{}] [order:{}] [isTicket:{}]",
				new Object[] { WebConfig.projectName,accountId,order,isTicket});
		Map<String, Object> OrderByShopIdMap = null;
/*
//		EAppMerchantAccountShop eAppMerchantAccountShop = userDao.selectEAppMerchantAccountShop(accountId, order.getEid(), order.getShopId());
		EAppMerchantAccountRole eAppMerchantAccountRole = userDao.selectEAppMerchantAccountRoleByAccountId(accountId);
//		if(eAppMerchantAccountShop!=null&&eAppMerchantAccountShop.getShopManager()) {
		String userAuthorization = eAppMerchantAccountRole==null?null:eAppMerchantAccountRole.getUserAuthorization();
		if(!StringUtils.isEmpty(userAuthorization)&&userAuthorization.charAt(0)=='1') {
			// 是否只查询电子票订单
			if(isTicket!=null&&isTicket) {
				OrderByShopIdMap = orderDao.selectTicketOrderByOrder(accountId,order,true);
			}else {
				OrderByShopIdMap = orderDao.selectOrderByShopId_v2(accountId,order,true);
			}
		}else {
			// 是否只查询电子票订单
			if(isTicket!=null&&isTicket) {
				OrderByShopIdMap = orderDao.selectTicketOrderByOrder(accountId,order,false);
			}else {
				OrderByShopIdMap = orderDao.selectOrderByShopId_v2(accountId,order,false);
			}
		}*/

		OrderByShopIdMap = orderDao.Proc_XHX_appMerchant_order_select(accountId, order, isTicket);
		
		
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
			map.put("manager_account", findOrderByShopId.get(i).getManager_account());
			map.put("send_account", findOrderByShopId.get(i).getSend_account());
			map.put("manager_remark", findOrderByShopId.get(i).getManager_remark());
			map.put("send_remark", findOrderByShopId.get(i).getSend_remark());
			map.put("send_name", findOrderByShopId.get(i).getSend_name());

			// 2019-05-06   -1不能取消 因为-1 一送就已经回单完成  app同中台一样 -1的单 取消的按钮隐藏
			map.put("sourceGroup", findOrderByShopId.get(i).getSourceGroup());
			List<OrderDetail> details = orderDao.selectOrderDetailByOrderId(findOrderByShopId.get(i).getOrderId());
			List<Map<String, Object>> detailsList = new ArrayList<Map<String, Object>>();
			for (OrderDetail orderDetail : details) {
				Map<String, Object> detailMap = new HashMap<String, Object>();
				detailMap.put("pname", orderDetail.getPname());
				detailMap.put("pictureUrl", orderDetail.getPictureUrl());
				if(orderDetail.getShopPrice()!=null) {
					detailMap.put("price", orderDetail.getShopPrice());
				}else {
					detailMap.put("price", orderDetail.getVipPrice());
				}
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
		if(order.getPageIndex()!=null){
			resultMap.put("page", order.getPageIndex());
		}
		return resultMap;
	}

	public List<OrderProductInfo> findOrderProductInfo(Integer orderId,Integer eid) {
		return orderDao.selectOrderProductInfo(orderId, eid);
	}
	
	public String cuidan(Integer eid,Integer orderId,String remark){
		return orderDao.cuidan(eid, orderId, remark);
	}
	
	public String drawback(Integer orderId,Integer userId,String th_type,String th_reason,String th_remark,String th_CreateUser){
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		Order order = new Order();
		order.setOrderId(orderId);
		if(selectSysConfig.get("ParamTuiKuanDay")!=null){
			Map<String, Object> OrderByShopIdMap = orderDao.selectOrderByShopId(order);
			
			List<OrderModel> selectOrderByShopId = OrderByShopIdMap.get("orders")==null?new ArrayList<OrderModel>():(List<OrderModel>) OrderByShopIdMap.get("orders");
			OrderModel orderModel = selectOrderByShopId.get(0);
			Timestamp dateCreated = orderModel.getDateCreated();
			Date date = new Date(dateCreated.getTime());
			Date date2 = new Date();
			long a =(date2.getTime()) - (date.getTime());
			long b = Long.parseLong((String)selectSysConfig.get("ParamTuiKuanDay"));
			if(a>b){
				return "订单已经超过"+selectSysConfig.get("ParamTuiKuanDay")+"分钟,不能退款";
			}
		}
		return orderDao.drawback(orderId, userId, th_type, th_reason, th_remark, th_CreateUser);
	}
	
	public String cancellationOfOrder(Integer orderId,Integer userId,Integer eid,String cancelReason,JSONArray e_order_detail_ID){
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		
		
		Order order = new Order();
		order.setOrderId(orderId);
		
		String BackstageCancelOrderTime = selectSysConfig.get("后台取消订单时间")!=null?(String)selectSysConfig.get("后台取消订单时间"):null;
		if(BackstageCancelOrderTime!=null){
			Map<String, Object> OrderByShopIdMap = orderDao.selectOrderByShopId(order);
			
			List<OrderModel> selectOrderByShopId = OrderByShopIdMap.get("orders")==null?new ArrayList<OrderModel>():(List<OrderModel>) OrderByShopIdMap.get("orders");
			OrderModel orderModel = selectOrderByShopId.get(0);
			Timestamp dateCreated = orderModel.getDateCreated();
			Date date2 = new Date();
			long a =(date2.getTime()) - (dateCreated.getTime());
			logger.info("[now:{}] [dateCreated:{}] [a:{}]",new Object[] {date2.getTime(),dateCreated.getTime(),a});
			a = a/(1000*60);
			long b = Long.parseLong(BackstageCancelOrderTime);
			logger.info("[a:{}] [后台取消订单时间:{}]",new Object[] {a,b});
			if(a<b){
				return "超过"+BackstageCancelOrderTime+"分钟后才能取消订单";
			}
		}
		
		String cancellationOfOrder = "该订单为现金支付不能取消！";
		// 判断商品是否全部为货到付款
		List<OrderProductInfo> selectOrderProductInfo = orderDao.selectOrderProductInfo(orderId, eid);
//		logger.info("selectOrderProductInfo:{}",selectOrderProductInfo);
		
		if(e_order_detail_ID!=null&&e_order_detail_ID.size()>0) {
			
			for (int i = 0; i < e_order_detail_ID.size(); i++) {
				for (OrderProductInfo orderProductInfo : selectOrderProductInfo) {
					Integer id = e_order_detail_ID.getInteger(i);
					if(orderProductInfo.getId().intValue()==id.intValue()&&!"现金".equals(orderProductInfo.getSettlementType())) {
						cancellationOfOrder = orderDao.cancellationOfOrder(orderId, userId, eid, cancelReason, id);
						break;
					}
				}
				
			}
			
		}else {
			
			for (int i = 0; i < selectOrderProductInfo.size(); i++) {
				String settlementType = selectOrderProductInfo.get(i).getSettlementType();
				// 如果全部是货到付款 则e_order_detail_ID 传0
				if(!"现金".equals(settlementType)){
					if(i==(selectOrderProductInfo.size()-1)){
						cancellationOfOrder = orderDao.cancellationOfOrder(orderId, userId, eid, cancelReason, 0);
						logger.info("[cancellationOfOrder:{}] [i:{}] [orderId:{}] [userId:{}] [eid:{}] [cancelReason:{}] [pid:{}]",
								new Object [] {cancellationOfOrder,i,orderId, userId, eid, cancelReason, 0});
					}
				}else{
					// 如果不全是货到付款 则循环传e_order_detail_ID 为 商品id
					for (int j = 0; j < selectOrderProductInfo.size(); j++) {
						if(!"现金".equals(selectOrderProductInfo.get(j).getSettlementType())){
							Integer id = selectOrderProductInfo.get(j).getId();
							cancellationOfOrder = orderDao.cancellationOfOrder(orderId, userId, eid, cancelReason, id);
							logger.info("[cancellationOfOrder:{}] [i:{}] [orderId:{}] [userId:{}] [eid:{}] [cancelReason:{}] [pid:{}]",
									new Object [] {cancellationOfOrder,i,orderId, userId, eid, cancelReason, id});
						}
					}
					break;
				}
			}
		}
		
		
		return cancellationOfOrder;
	}
	
	/**
	 * 根据订单Id查询订单商品
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> selectOrderDetailByOrderId(Integer orderId) {
		
		return orderDao.selectOrderDetailByOrderId(orderId);
	}
	
	/**
	 * 查询中台设置自定义订单取消原因
	 * @return
	 */
	public List<ESysBaseType> selectSysOrderCancelCause(Integer eid, String typeClass) {
		logger.info("[project:{}] [step:enter] [eid:{}] [typeClass:{}]",
				new Object[] { WebConfig.projectName,eid,typeClass});
		return orderDao.selectSysOrderCancelCause(eid,typeClass);
	}
	
	/**
	 * 订单完成
	 * @param orderId
	 * @param eid
	 * @return
	 */
	public Integer updateOrderAccomplish(Integer orderId,Integer eid) {
		logger.info("[project:{}] [step:enter] [eid:{}] [orderId:{}]",
				new Object[] { WebConfig.projectName,eid,orderId});
			Order order = orderDao.selectOrderByOrderIdAndEid(orderId, eid);
			if(order.getStatus().intValue()!=-1&&order.getStatus().intValue()!=-2) {
				return orderDao.updateOrderStatus(orderId, 3, eid);
			}
			return 0;
	}
	
	/**
	 * 预计送达
	 * @param orderId
	 * @param eid
	 * @param contactsId
	 * @param contacts
	 * @param phone
	 * @param arriveTime
	 * @return
	 */
	public Integer sendJpushMessageAndUpdateOrderStatus(Integer orderId,Integer eid,Integer contactsId,String contacts, String phone,String arriveTime) {
		logger.info("[project:{}] [step:enter] [eid:{}] [orderId:{}]",
				new Object[] { WebConfig.projectName,eid,orderId});
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			sendJpushMessage(orderId, contactsId, contacts, phone, "", "", 0, arriveTime, dateString);
			
			// 2019-06-10 黄亮东说不需要改状态 只用发消息
			/*Order order = orderDao.selectOrderByOrderIdAndEid(orderId, eid);
			if(order.getStatus().intValue()==1||order.getStatus().intValue()==0) {
				
				return orderDao.updateOrderStatus(orderId, 2, eid);
			}*/
			return 0;
	}
	
	/**
	 * 推送公众号预计送达模板信息
	 * @param orderId
	 * @param contactsId
	 * @param contacts
	 * @param phone
	 * @param photo
	 * @param location
	 * @param mdid
	 * @param arriveTime
	 * @param dateCreated
	 * @return
	 */
	@Async
	public String sendJpushMessage(Integer orderId,Integer contactsId,String contacts, String phone, String photo,String location,Integer mdid,String arriveTime,String dateCreated) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("orderId", orderId.toString()));
		params.add(new BasicNameValuePair("contactsId", contactsId.toString()));
		params.add(new BasicNameValuePair("contacts", contacts));
		params.add(new BasicNameValuePair("phone", phone));
		params.add(new BasicNameValuePair("photo", photo));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("mdid", mdid!=null?mdid.toString():"0"));
		params.add(new BasicNameValuePair("arriveTime", arriveTime));
		params.add(new BasicNameValuePair("dateCreated", dateCreated));
		params.add(new BasicNameValuePair("sourceId", "1"));
		
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/push/sddSendOrderLog.action", params);
		return webContent;
	}
	
	
	public List<EOrderLogistics> selectEOrderLogisticsByOrderId(Integer orderId) {
		logger.info("[project:{}] [step:enter] [orderId:{}]",
				new Object[] { WebConfig.projectName,orderId});
		return orderDao.selectEOrderLogisticsByOrderId(orderId);
	}
	
	public Map<String, Object> selectTuihuo(Integer orderId,String beginTime,String endTime,Integer eid,String eName,String orderNo,String account,String th_type,String paymentPlatform,Integer ifaudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [orderId:{}] [beginTime:{}] [endTime:{}] [eid:{}] [eName:{}] [orderNo:{}] [account:{}] [th_type:{}] [paymentPlatform:{}] [ifaudit:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName,orderId,beginTime,endTime,eid,eName,orderNo,account,th_type,paymentPlatform,ifaudit,PageIndex,PageSize,IsSelectAll});
		return orderDao.Proc_Backstage_order_tuihuo_select(orderId, beginTime, endTime, eid, eName, orderNo, account, th_type, paymentPlatform, ifaudit, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> orderRefund(Integer eid,Integer orderId,Double refundMoney,String auditType,String taAccount,String auditUser,String auditRemark,Integer ifaudit){
		logger.info("[project:{}] [step:enter] [eid:{}] [orderId:{}] [refundMoney:{}] [auditType:{}] [taAccount:{}] [auditUser:{}] [auditRemark:{}] [ifaudit:{}]",
				new Object[] { WebConfig.projectName,eid,orderId,refundMoney,auditType,taAccount,auditUser,auditRemark,ifaudit});
		
		Map<String, Object> result = CallBackPar.getParMap();
		if(ifaudit.intValue()==-1) {
			String s = orderDao.Proc_Backstage_order_tuihuo_audit(orderId, eid, ifaudit, null, auditUser, null, null, auditRemark, auditUser);
			if("1".equals(s)) {
				orderDao.insertRefundLog(orderId, "审核已撤回："+auditRemark, auditUser, 0);
				return result;
				
			}else {
				result.put("code", 150);
				result.put("message", s);
				return result;
			}
		}
		
		Map<String, Object> tuihuo_select = orderDao.Proc_Backstage_order_tuihuo_select(orderId);
		Object object = tuihuo_select.get("orderTuihuo");
		if(tuihuo_select==null||object==null) {
			// 未查询到相关数据
			int code = Constants.CODE_SDDV3_NO_DATA;
			String message = Constants.getCodeValue(code);
			result.put("code", code);
			result.put("message", message);
			return result;
		}
		Map<String, Object> select = (Map<String, Object>) tuihuo_select.get("orderTuihuo");
		Integer selectEid = (Integer) select.get("eid");
		if(eid.intValue()!=selectEid.intValue()) {
			// 无权修改该信息
			int code = Constants.CODE_LIMITED_AUTHORITY_MESSAGE;
			String message = Constants.getCodeValue(code);
			result.put("code", code);
			result.put("message", message);
			return result;
		}
		BigDecimal integralTotals = (BigDecimal) select.get("integralTotal");		// 积分抵扣金额
		BigDecimal paymentMoneys = (BigDecimal) select.get("paymentMoney");			// 在线支付金额
		BigDecimal balanceTotals = (BigDecimal) select.get("balanceTotal");			// 账户余额抵扣金额
		BigDecimal receivableTotals = (BigDecimal) select.get("receivableTotal");	// 应收合计(扣除优惠后的金额)/在线支付金额
		
		Double integralTotal = integralTotals.doubleValue();		// 积分抵扣金额
		Double paymentMoney = paymentMoneys.doubleValue();			// 在线支付金额
		Double balanceTotal = balanceTotals.doubleValue();			// 账户余额抵扣金额
		Double receivableTotal = receivableTotals.doubleValue();	// 应收合计(扣除优惠后的金额)/在线支付金额
		String orderNo = (String) select.get("orderNo");					// 退款单号
		if(paymentMoney>0&&balanceTotal==0&&integralTotal==0) {
			
		}
		
		// 1.balanceTotal=0&&integralTotal>0 积分支付 2.integralTotal==0&&balanceTotal>0 余额支付
		if(paymentMoney==0&&(balanceTotal>0||integralTotal>0)) {
			if(!"自动退款".equals(auditType)) {
				// 审核类型错误
				int code = Constants.CODE_ERROE_AUDIT_TYPE;
				String message = Constants.getCodeValue(code);
				result.put("code", code);
				result.put("message", message);
				return result;
			}
		}
		
		BigDecimal refundMoneyBig = new BigDecimal(Double.toString(refundMoney));
		BigDecimal integralTotalBig = new BigDecimal(Double.toString(integralTotal));
		BigDecimal paymentMoneyBig = new BigDecimal(Double.toString(paymentMoney));
		BigDecimal balanceTotalBig = new BigDecimal(Double.toString(balanceTotal));
		BigDecimal receivableTotalBig = new BigDecimal(Double.toString(receivableTotal));
		
		// compareTo 通过BigDecimal的compareTo方法来进行比较。 返回的结果是int类型，-1表示小于，0是等于，1是大于。
		if(refundMoneyBig.compareTo(receivableTotalBig.add(balanceTotalBig).add(integralTotalBig))!=0) {
			// 订单是组合支付，必须全额退款
			int code = Constants.CODE_NO_FULL_REFUND;
			String message = Constants.getCodeValue(code);
			result.put("code", code);
			result.put("message", message);
			return result;
		}
		
		String tuihuo_audit = orderDao.Proc_Backstage_order_tuihuo_audit(orderId, eid, 1, auditType, auditUser, refundMoney, taAccount, auditRemark, auditUser);
		if("1".equals(tuihuo_audit)) {
			
			// 金额为0的不走线上退款
			if(receivableTotal==0) {
				StringBuilder logInsert = new StringBuilder();
				logInsert.append("发起退款，单号："+orderNo+" 退款金额：0 ");
				orderDao.insertRefundLog(orderId, logInsert.toString(), auditUser, 0);
				return result;
			}
			
//			&&"自动退款".equals(auditType)
			
			
			 logger.info("[单号:{}] [退款金额:{}] [备注:{}]",new Object[] {orderNo,refundMoney,auditRemark});
			 Map<String, Object> order_payment = orderDao.Proc_Backstage_order_payment_select(orderId);
			 if(order_payment!=null&&order_payment.get("orderPayment")!=null) {
				 Map<String, Object> orderPayment = (Map<String, Object>) order_payment.get("orderPayment");
				 String orderGroup = (String) orderPayment.get("orderGroup");
				 // 支付来源 1:APP 2:微信公众号 3.小程序
				 Integer paySource = (Integer) orderPayment.get("paySource");
				 BigDecimal payMoneys = (BigDecimal) orderPayment.get("paymentMoney");
				 Double payMoney = payMoneys.doubleValue();
				 BigDecimal payMoneyBig = new BigDecimal(Double.toString(payMoney));
				 StringBuilder logInsert = new StringBuilder();
				 int appType = 1;
				 if(paySource.intValue()==3) {
					 appType = 2;
					 logInsert.append("发起小程序退款，单号："+orderNo+" 退款金额："+refundMoney+" 退款发起单号："+orderGroup);
				 }else if(paySource.intValue()==2) {
					 logInsert.append("发起公众号退款，单号："+orderNo+" 退款金额："+refundMoney+" 退款发起单号："+orderGroup);
				 }else if(paySource.intValue()==1) {
					 logInsert.append("此订单为APP支付,不能进行退款");
					 orderDao.insertRefundLog(orderId, logInsert.toString(), auditUser, 0);
					 logger.debug("此订单为APP支付");
					 // 此订单为APP支付,不能进行退款
					 int code = Constants.CODE_ERREO_APP_REFUND_TYPE;
					String message = Constants.getCodeValue(code);
					result.put("code", code);
					result.put("message", message);
					return result;
				 }
				 EEnterpriseWechatliteapp enter = orderDao.selectMchidAndPartnerKey(eid, appType);
//				 "FileNotFoundException"
				 WxPayRefundModel wx = new WxPayRefundModel();
					wx.setAppId(enter.getAppId());
					wx.setAppKey(enter.getPartnerKey());
					wx.setMchId(enter.getMchId());
					UUID uuid=UUID.randomUUID();
				    String str = uuid.toString(); 
				    String uuidStr=str.replace("-", "");
					wx.setNonceStr(uuidStr);
					wx.setOutTradeNo(orderGroup);// 退款单号
					Random ran=new Random();
					int a=ran.nextInt(99999999);
					int g=ran.nextInt(99999999);
					long l=a*1000000000L+g;
					String num=String.valueOf(l);
					wx.setOutRefundNo(num);
					BigDecimal big100 = new BigDecimal("100");
					wx.setTotalFee(payMoneyBig.multiply(big100).intValue()+"");
					wx.setRefundFee(receivableTotalBig.multiply(big100).intValue()+"");
					WxPayRefund wxPayRefund = new WxPayRefund();
					String pay = "";
					try {
							pay = wxPayRefund.Pay(wx);
							Map payRe = XmlStringUtil.stringToXMLParse(pay);
							if(payRe.containsKey("err_code_des")) {
								logInsert.append(" 退款提交失败："+payRe.get("err_code_des"));
							}else {
								logInsert.append(" 退款提交成功，退款单号："+orderGroup);
							}
					} catch (FileNotFoundException e) {
						try {
							logger.error("找不到系统路径下的商户证书：开始下载证书");
							qrcodeVisitReply(enter.getMchId());
							pay = wxPayRefund.Pay(wx);
							Map payRe = XmlStringUtil.stringToXMLParse(pay);
							if(payRe.containsKey("err_code_des")) {
								logInsert.append(" 退款提交失败："+payRe.get("err_code_des"));
							}else {
								logInsert.append(" 退款提交成功，退款单号："+orderGroup);
							}
						} catch (Exception e1) {
							logInsert.append("退款提交失败，未找到支付证书："+enter.getMchId()+".p12");
							logger.error(e.getMessage());
							pay = e.getMessage();
						}
					} catch (Exception e) {
						logInsert.append("退款提交失败，系统异常");
						logger.error(e.getMessage());
						pay = e.getMessage();
					}
					orderDao.insertRefundLog(orderId, logInsert.toString(), auditUser, 0);
					return result;
			 }else {
				 logger.debug("没有支付信息");
				 // 没有支付信息
				 int code = Constants.CODE_NO_PAY_MESSAGE;
				 String message = Constants.getCodeValue(code);
				 result.put("code", code);
				 result.put("message", message);
				 return result;
			 }
		}else {
			result.put("code", 150);
			result.put("message", tuihuo_audit);
			return result;
		}
	}
	
	/** 下载证书 
	 * @throws IOException */
	public String qrcodeVisitReply(String mchid) throws IOException {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mchid", mchid));
		// 后台域名
		String apiPath = UrlManage.getAdminWebUrl();
		String webContent = HttpClientUtil.postBackstageApi(apiPath + "SheqooApi/WechatDownloadCert", params);
		if(webContent.length()>100){
			String urlStr = apiPath + "SheqooApi/WechatDownloadCert"+"?"+basicNameValuePairToString(params);
			
				CredentialFileDownload.downLoadFromUrl(urlStr, mchid+".p12");
		}else {
			
		}
		return webContent;
	}
	
	private static String basicNameValuePairToString(List<BasicNameValuePair> list){
		if(list == null || list.size() == 0)return "无参数";
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			BasicNameValuePair cur = list.get(i);
			if(i!=list.size()-1){
				result += cur.getName() + "=" + cur.getValue() + "&";
			}else{
				result += cur.getName() + "=" + cur.getValue();
			}
		}
		return result;
	}
	
	/**
	 * 查询退款日志
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> selectRefundLog(Integer orderId) {
		return orderDao.selectRefundLog(orderId);
	}
	
	/**
	 * 数据结存
	 * @return
	 */
	public Map<String, Object> selectBalanceEvery(String beginTime,String endTime,Integer eid,String eName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [eid:{}] [eName:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName,beginTime,endTime,eid,eName,PageIndex,PageSize,IsSelectAll});
		return orderDao.Proc_Backstage_balance_every_day_select(beginTime, endTime, eid, eName, PageIndex, PageSize, IsSelectAll);
	}


	@Transactional(rollbackFor=Exception.class)
	public Integer insertDeliverymanOrder(Integer manager_accountId,Integer send_accountId,Integer eid,Integer shopId,JSONArray orderIds,Integer send_status,String remark) {
		logger.info("[project:{}] [step:enter] [manager_accountId:{}] [send_accountId:{}] [eid:{}] [shopId:{}] [orderIds:{}] [send_status:{}] [remark:{}]",
				new Object[] { WebConfig.projectName,manager_accountId,send_accountId,eid,shopId,orderIds,send_status,remark});
		for (int i = 0; i < orderIds.size(); i++) {
			Integer orderId = orderIds.getInteger(i);
			List<EAppMerchantOrderSend> eAppMerchantOrderSend = orderDao.selectEAppMerchantOrderSendByOrderId(orderId);
			if(eAppMerchantOrderSend.size()>0){
				orderDao.updateDeliverymanOrder(orderId,send_accountId,send_status);
			}else{
				orderDao.insertDeliverymanOrder(manager_accountId, send_accountId, eid, shopId, orderId, send_status, remark);
			}
			// 彭安说  改订单状态  两个表都要改 e_order , e_appMerchant_order_send
			orderDao.updateOrderStatus(orderId, send_status, eid);
		}
		jpushPushMsg(send_accountId, orderIds);
		return 1;
	}
	
	public Map<String,Object> getStatusOrders(Integer statusId, Integer pageIndex, Integer clientId,Integer eid,Integer shopId) {
		return null;
	}

	/**
	 * 订单分配派送时给送货员极光推送
	 * @param send_accountId
	 * @param orderIds
	 */
	@Async
	public void jpushPushMsg(Integer send_accountId,JSONArray orderIds){

		Map<String, Object> stringObjectMap = userDao.selectAppMerchantJpush(send_accountId);
		if(stringObjectMap==null||stringObjectMap.get("regeditId")==null){
			logger.error("[param:{jpushPushMsg}] [send_accountId:{}] [error:{accountId没有极光账号}]",send_accountId);
			return;
		}
		String regeditId = (String) stringObjectMap.get("regeditId");
		Integer sourceId = (Integer) stringObjectMap.get("sourceId");
		for (int i = 0; i < orderIds.size(); i++) {
			Integer orderId = orderIds.getInteger(i);
			OrderModel order = orderDao.selectOrderByOrderId(orderId);
			if(order==null||order.getOrderId()==null){
				logger.error("[param:{jpushPushMsg}] [orderId:{}] [error:{查询不到该订单}]",orderId);
				continue;
			}
			Integer xid =orderId;
			String title = "配送通知";
			String message = "配送有新单"+order.getOrderNo();
			Integer type = 10000;

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());

			String sendTime = dateString;
			Integer platform = sourceId;
			String registerId = regeditId;
			String jpushTag = "";
			List<OrderProductInfo> orderProductInfos = orderDao.selectOrderProductInfo(orderId, null);
			StringBuffer voiceContent = new StringBuffer("配送地址："+order.getSendAddress());
			for (OrderProductInfo orderProductInfo:orderProductInfos) {
				voiceContent.append(","+orderProductInfo.getpName()+orderProductInfo.getNumber()+orderProductInfo.getUnit());
			}
			JPushShangHuModel model = new JPushShangHuModel(xid, title, message, type, sendTime, platform, registerId, jpushTag, voiceContent.toString());
			JpushShangHuService.pushMsg(model);
		}

	}

	/** 257.电子卡支付成功微信回调  Json */
	public String rechargeCardPaySucNotify(String openid,String out_trade_no,String transaction_id,String bank_type,String total_fee,String cash_fee,String paytime,String billId,String orderId,String weixinPromotion,String appId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("out_trade_no", out_trade_no));
		params.add(new BasicNameValuePair("transaction_id", transaction_id));
		params.add(new BasicNameValuePair("bank_type", bank_type));
		params.add(new BasicNameValuePair("total_fee", total_fee));
		params.add(new BasicNameValuePair("cash_fee", cash_fee));
		params.add(new BasicNameValuePair("paytime", paytime));
		params.add(new BasicNameValuePair("billId", billId));
		params.add(new BasicNameValuePair("orderId", orderId));
		if(!StringUtils.isEmpty(weixinPromotion)){ 
			params.add(new BasicNameValuePair("weixinPromotion", weixinPromotion));
		}
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/weixin/rechargeCardPaySucNotify.action", params);
		logger.info("保存支付回调："+webContent);
		return webContent;
	}

	/** 244.拼团支付成功回调更新支付结果  Json */
	public String groupBuyPaySucNotify(String openid,String out_trade_no,String transaction_id,String bank_type,String total_fee,String cash_fee,String paytime,String gboId,String xaId,String isCommander,String orderId,String weixinPromotion,String appId) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("out_trade_no", out_trade_no));
		params.add(new BasicNameValuePair("transaction_id", transaction_id));
		params.add(new BasicNameValuePair("bank_type", bank_type));
		params.add(new BasicNameValuePair("total_fee", total_fee!=null?total_fee.toString():""));
		params.add(new BasicNameValuePair("cash_fee", cash_fee!=null?cash_fee.toString():""));
		params.add(new BasicNameValuePair("paytime", paytime));
		params.add(new BasicNameValuePair("gboId", gboId!=null?gboId.toString():""));
		params.add(new BasicNameValuePair("xaId", xaId!=null?xaId.toString():""));
		params.add(new BasicNameValuePair("isCommander", isCommander!=null?isCommander.toString():""));
		params.add(new BasicNameValuePair("orderId", orderId));
		if(!StringUtils.isEmpty(weixinPromotion)){ 
			params.add(new BasicNameValuePair("weixinPromotion", weixinPromotion));
		}
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/weixin/groupBuyPaySucNotify.action", params);
		return webContent;
	}


	/** 103.微信支付成功,更新单据支付状态  Json */
	public String weixinPayToSuc(String openid, String out_trade_no, String transaction_id, String bank_type, String total_fee,String cash_fee, String paytime,String orderId,String weixinPromotion,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("out_trade_no", out_trade_no));
		params.add(new BasicNameValuePair("transaction_id", transaction_id));
		params.add(new BasicNameValuePair("bank_type", bank_type));
		params.add(new BasicNameValuePair("total_fee", total_fee));
		params.add(new BasicNameValuePair("cash_fee", cash_fee!=null?cash_fee.toString():""));
		params.add(new BasicNameValuePair("paytime", paytime));
		params.add(new BasicNameValuePair("orderId", orderId));
		if(!StringUtils.isEmpty(weixinPromotion)){ 
			params.add(new BasicNameValuePair("weixinPromotion", weixinPromotion));
		}
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/weixin/weixinPayToSuc.action", params);
		logger.info("保存支付回调："+webContent);
		return webContent;
	}

	public Map<String,Object> Proc_Backstage_order_detail_eticketTuiKe_select(String beginTime,String endTime,Integer eid,String tuiKeAccount,String tuiKeManageAccount,String pname,String account,Integer status,Boolean isHistory,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [eid:{}] [tuiKeAccount:{}] [tuiKeManageAccount:{}] [pname:{}] [account:{}] [status:{}] [isHistory:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName,beginTime,endTime,eid,tuiKeAccount,tuiKeManageAccount,pname,account,status,isHistory,PageIndex,PageSize,IsSelectAll});
		return orderDao.Proc_Backstage_order_detail_eticketTuiKe_select(beginTime, endTime, eid, tuiKeAccount, tuiKeManageAccount, pname, account, status,isHistory, PageIndex, PageSize, IsSelectAll);
	}
}
