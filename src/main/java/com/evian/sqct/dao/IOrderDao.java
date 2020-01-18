package com.evian.sqct.dao;

import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;

import java.util.List;
import java.util.Map;

public interface IOrderDao {

	
	/**
	 * 存储过程（慢）
	 * 根据商铺Id查询商铺所有订单
	 * @param order
	 * @return
	 */
	public Map<String, Object> selectOrderByShopId(Order order);
	
	/**
	 * sql
	 * 根据商铺Id查询商铺所有订单
	 * @param order
	 * @return
	 */
	public Map<String, Object> selectOrderByShopId_v2(Order order);
	
	/**
	 * sql
	 * 根据权限和商铺Id查询商铺所有订单
	 * @param order
	 * @return
	 */
	public Map<String, Object> selectOrderByShopId_v2(Integer accountId,Order order);


	/**
	 * sql
	 * 根据权限和商铺Id查询商铺所有订单
	 * @param order
	 * @param isShopManager 是否是管理员
	 * @return
	 */
	public Map<String, Object> selectOrderByShopId_v2(Integer accountId,Order order,boolean isShopManager);
	
	/**
	 * 查询订单数量
	 * @return
	 */
	public Integer selectOrderCountByShopIdAndTime(String beginTime,String endTime);
	
	/**
	 * 查询订单商品详情
	 * @param orderId
	 * @param eid
	 * @return
	 */
	public List<OrderProductInfo> selectOrderProductInfo(Integer orderId,Integer eid);
	
	/**
	 * 订单催单
	 * @param eid
	 * @param orderId
	 * @param remark
	 * @return
	 */
	public String cuidan(Integer eid,Integer orderId,String remark);
	
	/**
	 * 申请退款
	 * @param orderId
	 * @param userId
	 * @param th_type
	 * @param th_reason
	 * @param th_remark
	 * @param th_CreateUser
	 * @return
	 */
	public String drawback(Integer orderId,Integer userId,String th_type,String th_reason,String th_remark,String th_CreateUser);
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param userId
	 * @param eid
	 * @param cancelReason 取消原因
	 * @param e_order_detail_ID 0 取消全部订单；>0取消当前行商品
	 * @return
	 */
	public String cancellationOfOrder(Integer orderId,Integer userId,Integer eid,String cancelReason,Integer e_order_detail_ID);
	
	/**
	 * 查询某些系统参数
	 * @return
	 */
	public Map<String, Object> selectSysConfig();
	
	/**
	 * 根据订单Id查询订单商品
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> selectOrderDetailByOrderId(Integer orderId);
	
	/**
	 * 查询中台设置自定义订单取消原因
	 * @return
	 */
	public List<ESysBaseType> selectSysOrderCancelCause(Integer eid,String typeClass);
	
	/**
	 * 修改订单状态
	 * @return
	 */
	public Integer updateOrderStatus(Integer orderId,Integer status,Integer eid);
	
	/**
	 * 查询订单
	 * @param orderId
	 * @param eid
	 * @return
	 */
	public Order selectOrderByOrderIdAndEid(Integer orderId,Integer eid);

	/**
	 * 查询订单
	 * @param orderId
	 * @return
	 */
	public OrderModel selectOrderByOrderId(Integer orderId);

	public List<EOrderLogistics> selectEOrderLogisticsByOrderId(Integer orderId);
	
	/**
	 * 退货退款申请查询
	 * @param orderId
	 * @return
	 */
	Map<String, Object> Proc_Backstage_order_tuihuo_select(Integer orderId);
	
	/**
	 * 退货退款申请查询
	 * @param orderId
	 * @returne_order_detail
	 */
	Map<String, Object> Proc_Backstage_order_tuihuo_select(Integer orderId,String beginTime,String endTime,Integer eid,String eName,String orderNo,String account,String th_type,String paymentPlatform,Integer ifaudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 终端退款退货申请审核(每一笔交易金额最多只能退款一次，不管结果成功与否)
	 * @param orderId
	 * @param eid
	 * @param ifaudit
	 * @param auditType
	 * @param auditRen
	 * @param th_money
	 * @param auditBankAccount
	 * @param auditremark
	 * @param auditUser
	 * @return
	 */
	public String Proc_Backstage_order_tuihuo_audit(Integer orderId,Integer eid,Integer ifaudit,String auditType,String auditRen,Double th_money,String auditBankAccount,String auditremark,String auditUser);
	
	/**
	 * 查询企业 mchid 和支付密钥
	 * @param eid
	 * @param appType 1第一个第三方品台公众号，2第一个第三方品台小程序，3第二个第三方品台公众号，4第二个第三方品台小程序
	 * @return
	 */
	EEnterpriseWechatliteapp selectMchidAndPartnerKey(Integer eid,Integer appType);
	
	List<EEnterpriseWechatliteapp> selectMchidAndPartnerKey(Integer eid);
	
	/**
	 * 支付记录查询
	 * @param orderId
	 * @return
	 */
	Map<String, Object> Proc_Backstage_order_payment_select(Integer orderId);
	
	/**
	 * 水趣退款日志
	 * @param orderId
	 * @param logger
	 * @param createUser
	 * @param dataType 订单类型：0默认水趣订单（表：e_order）；1团购未成团或取消订单（表：e_groupbuy_order）
	 * @return
	 */
	Integer insertRefundLog(Integer orderId,String logger,String createUser,Integer dataType);
	
	/**
	 * 查询退款日志
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> selectRefundLog(Integer orderId);
	
	/**
	 * 数据结存
	 * @return
	 */
	Map<String, Object> Proc_Backstage_balance_every_day_select(String beginTime,String endTime,Integer eid,String ename,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 查询电子票订单
	 * @param order
	 * @return
	 */
	Map<String, Object> selectTicketOrderByOrder(Order order);
	
	/**
	 * 根据用户权限查询电子票订单
	 * @param accountId
	 * @param order
	 * @return
	 */
	Map<String, Object> selectTicketOrderByOrder(Integer accountId,Order order);

	/**
	 * 根据用户权限查询电子票订单
	 * @param accountId
	 * @param order
	 * @param isShopManager 是否是管理员
	 * @return
	 */
	Map<String, Object> selectTicketOrderByOrder(Integer accountId,Order order,boolean isShopManager);
	/**
	 * 
	 * @param manager_accountId
	 * @param send_accountId
	 * @param eid
	 * @param shopId
	 * @param orderId
	 * @param send_status
	 * @param remark
	 * @return
	 */
	Integer insertDeliverymanOrder(Integer manager_accountId,Integer send_accountId,Integer eid,Integer shopId,Integer orderId,Integer send_status,String remark);

	Integer updateDeliverymanOrder(Integer orderId,Integer send_accountId,Integer send_status);

	List<EAppMerchantOrderSend> selectEAppMerchantOrderSendByOrderId(Integer orderId);

	/**
	 * 查询水趣商户订单用存储过程查询
	 * @param accountId
	 * @param order
	 * @return
	 */
	Map<String,Object> Proc_XHX_appMerchant_order_select(Integer accountId, Order order,Boolean isTicket);


	Map<String,Object> selectEEnterpriseAppPayParamByAppId(String appId);

	Map<String,Object> Proc_Backstage_order_detail_eticketTuiKe_select(String beginTime,String endTime,Integer eid,String tuiKeAccount,String tuiKeManageAccount,String pname,String account,Integer status,Boolean isHistory,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
}
