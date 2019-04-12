package com.evian.sqct.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.evian.sqct.bean.order.EOrderLogistics;
import com.evian.sqct.bean.order.ESysBaseType;
import com.evian.sqct.bean.order.Order;
import com.evian.sqct.bean.order.OrderDetail;
import com.evian.sqct.bean.order.OrderModel;
import com.evian.sqct.bean.order.OrderProductInfo;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.util.HttpClientUtilOkHttp;
import com.evian.sqct.util.WebConfig;

@Service
@Transactional
public class BaseOrderManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("orderDao")
	private IOrderDao orderDao;
	
	public Map<String, Object>  findOrderByShopId(Order order){
		logger.info("[project:{}] [step:enter] [order:{}]",
				new Object[] { WebConfig.projectName,order});
		return orderDao.selectOrderByShopId(order);
	}
	
	public Map<String, Object>  findOrderByShopId_v2(Order order){
		logger.info("[project:{}] [step:enter] [order:{}]",
				new Object[] { WebConfig.projectName,order});
		Map<String, Object> OrderByShopIdMap = orderDao.selectOrderByShopId_v2(order);
		
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
			List<OrderDetail> details = orderDao.selectOrderDetailByOrderId(findOrderByShopId.get(i).getOrderId());
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
			
			
			Order order = orderDao.selectOrderByOrderIdAndEid(orderId, eid);
			if(order.getStatus().intValue()==1||order.getStatus().intValue()==0) {
				
				return orderDao.updateOrderStatus(orderId, 2, eid);
			}
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
	
}
