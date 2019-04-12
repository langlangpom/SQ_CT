package com.evian.sqct.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.evian.sqct.bean.order.EOrderLogistics;
import com.evian.sqct.bean.order.ESysBaseType;
import com.evian.sqct.bean.order.Order;
import com.evian.sqct.bean.order.OrderDetail;
import com.evian.sqct.bean.order.OrderModel;
import com.evian.sqct.bean.order.OrderProductInfo;
import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.evian.sqct.util.SQLOper;
@Repository("orderDao")
public class OrderImpl implements IOrderDao {

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Map<String, Object> selectOrderByShopId_v2(Order order) {
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("orderId, orderGroup, orderGuid, orderNo, eid, shopId, clientId, did, delivery, sendAddress, phone, contacts, dateCreated, appointmentTime, sendRemark, account, sdkType, mobileType, mobileIMEL,");
		sql.append("appVer, orderRemark, ticketCount, bumfCount, receivableTotal, balanceTotal, integralTotal, linePaySuceed, payMode, status, ifEvaluate, startMutual, ifMutual, mutualLog, mutualTime, mutualId,");
		sql.append("deliverTime, deliverMan, completeTime, affirmTime, activityType, privilegeId, code_no, discountMoney, discountDescribe, freight, integral, del, cancelReason, cancelTime, has_invoice,");
		sql.append("confirm_take, cityId, districtId, location, has_return, staffId, paymentPlatform, sourceGroup ");
		sql.append(" from e_order where 1=1 ");
		
		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();		
		
		
		if(order.getEid()!=null) {
			sqlAppend.append(" and eid=? ");
			args.add(order.getEid());
		}
		
		if(order.getShopId()!=null) {
			sqlAppend.append(" and shopId=? ");
			args.add(order.getShopId());
		}
		
		if(order.getOrderNo()!=null) {
			sqlAppend.append(" and orderNo=? ");
			args.add(order.getOrderNo());
		}
		

		if(order.getBeginTime()!=null&&order.getEndTime()!=null) {
			sqlAppend.append(" and (dateCreated between ? and ?) ");
			args.add(order.getBeginTime());
			args.add(order.getEndTime());
		}

		if(order.getStatus()!=null) {
			
			if(order.getStatus().intValue()==-99) {
				sqlAppend.append(" and (status=1 or status=0) ");
			}else {
				sqlAppend.append(" and status=? ");
				args.add(order.getStatus());
			}
			
		}

		int o = args.size();
		
		Object[] a = new Object[o];
		for (int i = 0; i < args.size(); i++) {
			a[i]=args.get(i);
		}
		
		Integer Count = 0;
		
		sql.append(sqlAppend);
		
		if(order.getPageIndex()!=null&&order.getPageSize()!=null) {
			
			StringBuilder CountSql = new StringBuilder("select ");
			CountSql.append(" Count = count(1) ");
			CountSql.append(" from e_order where 1=1 ");
			CountSql.append(sqlAppend);
			List<Map<String, Object>> countMap = jdbcTemplate.query(CountSql.toString(),a, ResultSetToBeanHelper.resultSetToListMap());
			
			if(countMap.size()!=0) {
				Map<String, Object> map = countMap.get(0);
				Count = (Integer) map.get("Count");
			}
			
			
			// 加上分页
			a = SQLOper.addPaging(sql, a, order.getPageIndex(), order.getPageSize(), false);
			
		}
		
		
		List<OrderModel> query = jdbcTemplate.query(sql.toString(), a,new BeanPropertyRowMapper<>(OrderModel.class));
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Count", Count);
		result.put("orders", query);
		
		return result;
	}
	
	@Override
	public Integer selectOrderCountByShopIdAndTime(String beginTime,String endTime) {
		StringBuilder sql = new StringBuilder("select Count = count(1) FROM e_order ");
		
		Object[] a = null;
		
		if(beginTime!=null&&endTime!=null) {
			sql.append(" and (dateCreated between ? and ?) ");
			a = new Object[2]; 
			a[0] = beginTime;
			a[1] = endTime;
		}else {
			a = new Object[0];
		}
		List<Map<String, Object>> query = jdbcTemplate.query(sql.toString(),a, ResultSetToBeanHelper.resultSetToListMap());
		if(query.size()==0) {
			return 0;
		}else {
			Map<String, Object> map = query.get(0);
			return (Integer) map.get("Count");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> selectOrderByShopId(final Order order) {
		Map<String, Object> result = jdbcTemplate.execute(
				"{call Proc_Backstage_order_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", order.getOrderId());
						cs.setObject("shopId", order.getShopId());
						cs.setObject("beginTime", order.getBeginTime());
						cs.setObject("endTime", order.getEndTime());
						cs.setObject("eid", order.getEid());
						cs.setObject("orderNo", order.getOrderNo());
						cs.setObject("status", order.getStatus());
						cs.setObject("sdkType", order.getSdkType());
						cs.setObject("ifMutual", order.getIfMutual());
						cs.setObject("ifreply", order.getIfreply());
						cs.setObject("in_come", order.getIn_come());
						cs.setObject("account", order.getAccount());
						cs.setObject("shopName", order.getShopName());
						cs.setObject("confirm_take", order.getConfirm_take());
						cs.setObject("eName", order.geteName());
						cs.setObject("PageIndex", order.getPageIndex());
						cs.setObject("PageSize", order.getPageSize());
						if(order.getPageIndex()!=null&&order.getPageSize()!=null){
							cs.setObject("IsSelectAll", false);
						}else{
							cs.setObject("IsSelectAll", null);
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<OrderModel> result = ResultSetToBeanHelper.resultSetToList(rs, OrderModel.class);
							map.put("orders", result);
							map.put("Count", cs.getInt("Count"));
							return map;
						} catch (Exception e) {
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<OrderProductInfo> selectOrderProductInfo(final Integer orderId,
			final Integer eid) {
		List<OrderProductInfo> result = jdbcTemplate.execute(
				"{call Proc_Backstage_order_detail_select(?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("eid", eid);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						
						try {
							List<OrderProductInfo> result = ResultSetToBeanHelper.resultSetToList(rs, OrderProductInfo.class);
							return result;
						} catch (Exception e) {
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String cuidan(final Integer eid, final Integer orderId, final String remark) {
		String result = jdbcTemplate.execute(
				"{call Proc_Backstage_order_cuishui_operat(?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("orderId", orderId);
						cs.setObject("remark", remark);
						cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("tag");
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String drawback(final Integer orderId, final Integer userId, final String th_type,
			final String th_reason, final String th_remark, final String th_CreateUser) {
		String result = jdbcTemplate.execute(
				"{call Proc_Backstage_Order_Refund(?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("clientId", userId);
						cs.setObject("th_type", th_type);
						cs.setObject("th_reason", th_reason);
						cs.setObject("th_remark", th_remark);
						cs.setObject("th_CreateUser", th_CreateUser);
						cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("tag");
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String cancellationOfOrder(final Integer orderId, final Integer userId,
			final Integer eid, final String cancelReason, final Integer e_order_detail_ID) {
		String result = jdbcTemplate.execute(
				"{call PROC_LIDAN_Backstage_mid_ORDER_QX(?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("clientId", userId);
						cs.setObject("eid", eid);
						cs.setObject("cancelReason", cancelReason);
						cs.setObject("e_order_detail_ID", e_order_detail_ID);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		return result;
	}

	@Override
	public Map<String, Object> selectSysConfig() {
		String sql = "select sysId, sysParam, sysParamName, sysValue, sysDescription, sysSort, sysGroup, enabled from e_sys_config where sysGroup=? or sysGroup=? ";
		RowMapper<SysParamModel> rowMapper = new BeanPropertyRowMapper<>(SysParamModel.class);
		List<SysParamModel> sysParam = jdbcTemplate.query(sql, new Object[]{"基础设置","公众号相关"}, rowMapper );
		if(sysParam.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			for (SysParamModel sysParamModel : sysParam) {
				map.put(sysParamModel.getSysParamName(), sysParamModel.getSysValue());
			}
			return map;
		}
		return null;
	}

	@Override
	public List<OrderDetail> selectOrderDetailByOrderId(Integer orderId) {
		String sql = "select d.id, d.orderId, d.pid, d.number, d.price, d.total, d.settlementType, d.integral, d.genre, d.ifEvaluate, d.voucherCode, d.voucherMoney, d.activityId, d.beginDate, d.endDate, d.hashTicket, d.typeId, d.typeName, d.comboPid,d.ifKouJian,p.pname,p.pictureUrl,p.vipPrice from e_order_detail as d left join e_product as p on d.pid=p.pid where orderId=?";
		List<OrderDetail> queryForObject = null;
		try {
			queryForObject = jdbcTemplate.query(sql,new Object[] {orderId}, new BeanPropertyRowMapper<>(OrderDetail.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return queryForObject;
	}
	
	/**
	 * 查询中台设置自定义订单取消原因
	 * @return
	 */
	@Override
	public List<ESysBaseType> selectSysOrderCancelCause(Integer eid, String typeClass) {
		StringBuilder sql = new StringBuilder("select typeId, eid, typeName, typeClass, typeRemark, createTime, creator from e_sys_base_type ");
		List<Object> args = new ArrayList<Object>();
		if(eid!=null&&typeClass!=null) {
			sql.append(" where eid=? and typeClass=?");
			args.add(eid);
			args.add(typeClass);
		}else if(eid!=null) {
			sql.append(" where eid=? ");
			args.add(eid);
		}else if(typeClass!=null) {
			sql.append(" where typeClass=? and eid=0 ");
			args.add(typeClass);
		}else {
			sql.append(" where eid=0 ");
		}
		int o = args.size();
		Object[] a = new Object[o];
		
		for (int i = 0; i < args.size(); i++) {
			a[i]=args.get(i);
		}
		
		List<ESysBaseType> queryForObject = null;
		try {
			queryForObject = jdbcTemplate.query(sql.toString(),a, new BeanPropertyRowMapper<>(ESysBaseType.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return queryForObject;
	}

	@Override
	public Integer updateOrderStatus(Integer orderId, Integer status, Integer eid) {
		String sql = "update e_order set status=? where orderId=? and eid=?";
		Integer update = jdbcTemplate.update(sql,new Object[] {status,orderId,eid});
		return update;
	}

	
	@Override
	public Order selectOrderByOrderIdAndEid(Integer orderId, Integer eid) {
		String sql="select status,eid,orderId from e_order where orderId=? and eid=?";
		try {
			Order queryForObject = jdbcTemplate.queryForObject(sql.toString(),new Object[] {orderId,eid}, new BeanPropertyRowMapper<>(Order.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	@Override
	public List<EOrderLogistics> selectEOrderLogisticsByOrderId(Integer orderId) {
		String sql = "select logisticsId, logKey, logisticsType, orderId, logisticsDescribe, contactsId, contacts, phone, photo, location, arriveTime, dateCreated, log_state, dataReson, WEBID from e_order_logistics where orderId=? order by dateCreated DESC";
		List<EOrderLogistics> query = jdbcTemplate.query(sql.toString(),new Object[] {orderId}, new BeanPropertyRowMapper<>(EOrderLogistics.class));
		return query;
	}





}
