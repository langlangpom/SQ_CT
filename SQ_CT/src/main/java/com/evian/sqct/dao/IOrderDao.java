package com.evian.sqct.dao;

import java.util.List;
import java.util.Map;

import com.evian.sqct.bean.order.EOrderLogistics;
import com.evian.sqct.bean.order.ESysBaseType;
import com.evian.sqct.bean.order.Order;
import com.evian.sqct.bean.order.OrderDetail;
import com.evian.sqct.bean.order.OrderModel;
import com.evian.sqct.bean.order.OrderProductInfo;

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
	 * 查询订单数量
	 * @return
	 */
	public Integer selectOrderCountByShopIdAndTime(String beginTime,String endTime);
	
	/**
	 * 查询店铺商品信息
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
	
	public List<EOrderLogistics> selectEOrderLogisticsByOrderId(Integer orderId);
}
