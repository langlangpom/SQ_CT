package com.evian.sqct.impl;

import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.evian.sqct.util.SQLOper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository("orderDao")
public class OrderImpl implements IOrderDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

		if(order.getPayMode()!=null) {
			sqlAppend.append(" and A.payMode=? ");
			args.add(order.getPayMode());
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

		logger.info("[sql:{}]",sql.toString());
		List<OrderModel> query = jdbcTemplate.query(sql.toString(), a,new BeanPropertyRowMapper<>(OrderModel.class));
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Count", Count);
		result.put("orders", query);
		
		return result;
	}
	
	@Override
	public Map<String, Object> selectOrderByShopId_v2(Integer accountId, Order order) {
		
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("A.orderId, A.orderGroup, A.orderGuid, A.orderNo, A.eid, A.shopId, A.clientId, A.did, A.delivery, A.sendAddress, A.phone, A.contacts, A.dateCreated, A.appointmentTime, A.sendRemark, A.account, A.sdkType, A.mobileType, A.mobileIMEL,");
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate, A.startMutual, A.ifMutual, A.mutualLog, A.mutualTime, A.mutualId,");
		sql.append("A.deliverTime, A.deliverMan, A.completeTime, A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.cancelReason, A.cancelTime, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.staffId, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "A.status";
			sql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
		}else {
			sql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
		}

		sql.append(" left join e_appMerchant_account AS Z ON S.manager_accountId=Z.accountId ");
		sql.append(" left join e_appMerchant_account AS X ON S.send_accountId=X.accountId ");

		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();		
		
		if(accountId==null) {
			sql.append(" where 1=1 ");
		}else {
			// 管理员可以查询0和1和 0 or 1
			if(order.getStatus()!=null&&(order.getStatus().intValue()==-99||order.getStatus().intValue()==0||order.getStatus().intValue()==1)) {
				sql.append(" where S.manager_accountId=? ");
			}else {
				// 除了0和1就查send_accountId
				sql.append(" where S.send_accountId=? ");
			}
			args.add(accountId);
		}
		
		if(order.getEid()!=null) {
			sqlAppend.append(" and A.eid=? ");
			args.add(order.getEid());
		}

		if(order.getPayMode()!=null) {
			sqlAppend.append(" and A.payMode=? ");
			args.add(order.getPayMode());
		}
		
		if(order.getShopId()!=null) {
			sqlAppend.append(" and A.shopId=? ");
			args.add(order.getShopId());
		}
		
		if(order.getOrderNo()!=null) {
			sqlAppend.append(" and A.orderNo=? ");
			args.add(order.getOrderNo());
		}
		

		if(order.getBeginTime()!=null&&order.getEndTime()!=null) {
			sqlAppend.append(" and (A.dateCreated between ? and ?) ");
			args.add(order.getBeginTime());
			args.add(order.getEndTime());
		}

		if(order.getStatus()!=null) {
			
			if(order.getStatus().intValue()==-99) {
				sqlAppend.append(" and ("+status+"=1 or "+status+"=0) ");
			}else {
				sqlAppend.append(" and "+status+"=? ");
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
			CountSql.append(" from e_order as A ");
			if(accountId==null) {
				CountSql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
			}else {
				CountSql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
			}
			if(accountId==null) {
				CountSql.append(" where 1=1 ");
			}else {
				// 管理员可以查询0和1和 0 or 1
				if(order.getStatus()!=null&&(order.getStatus().intValue()==-99||order.getStatus().intValue()==0||order.getStatus().intValue()==1)) {
					CountSql.append(" where S.manager_accountId=? ");
				}else {
					// 除了0和1就查send_accountId
					CountSql.append(" where S.send_accountId=? ");
				}
				args.add(accountId);
			}
			CountSql.append(sqlAppend);
			List<Map<String, Object>> countMap = jdbcTemplate.query(CountSql.toString(),a, ResultSetToBeanHelper.resultSetToListMap());
			
			if(countMap.size()!=0) {
				Map<String, Object> map = countMap.get(0);
				Count = (Integer) map.get("Count");
			}
			
			
			// 加上分页
			a = SQLOper.addPaging(sql, a, order.getPageIndex(), order.getPageSize(), false);
			
		}
		
//		System.out.println(sql.toString());
		logger.info("[sql:{}]",sql.toString());
		List<OrderModel> query = jdbcTemplate.query(sql.toString(), a,new BeanPropertyRowMapper<>(OrderModel.class));
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Count", Count);
		result.put("orders", query);
		
		return result;
	}

	@Override
	public Map<String, Object> selectOrderByShopId_v2(Integer accountId, Order order, boolean isShopManager) {


		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("A.orderId, A.orderGroup, A.orderGuid, A.orderNo, A.eid, A.shopId, A.clientId, A.did, A.delivery, A.sendAddress, A.phone, A.contacts, A.dateCreated, A.appointmentTime, A.sendRemark, A.account, A.sdkType, A.mobileType, A.mobileIMEL,");
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate, A.startMutual, A.ifMutual, A.mutualLog, A.mutualTime, A.mutualId,");
		sql.append("A.deliverTime, A.deliverMan, A.completeTime, A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.cancelReason, A.cancelTime, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.staffId, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "A.status";
			sql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
		}else {
			if(isShopManager){
				sql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId and S.manager_accountId="+accountId);
			}else{
				sql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
			}
		}

		sql.append(" left join e_appMerchant_account AS Z ON S.manager_accountId=Z.accountId ");
		sql.append(" left join e_appMerchant_account AS X ON S.send_accountId=X.accountId ");

		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();

		if(accountId==null) {
			sql.append(" where 1=1 ");
		}else {
			// 管理员可以查询0和1和 0 or 1
			if(isShopManager) {
				sql.append(" where S.manager_accountId=? ");
			}else {
				// 除了0和1就查send_accountId
				sql.append(" where S.send_accountId=? ");
			}
			args.add(accountId);
		}

		if(order.getEid()!=null) {
			sqlAppend.append(" and A.eid=? ");
			args.add(order.getEid());
		}

		if(order.getPayMode()!=null) {
			sqlAppend.append(" and A.payMode=? ");
			args.add(order.getPayMode());
		}

		if(order.getShopId()!=null) {
			sqlAppend.append(" and A.shopId=? ");
			args.add(order.getShopId());
		}

		if(order.getOrderNo()!=null) {
			sqlAppend.append(" and A.orderNo=? ");
			args.add(order.getOrderNo());
		}


		if(order.getBeginTime()!=null&&order.getEndTime()!=null) {
			sqlAppend.append(" and (A.dateCreated between ? and ?) ");
			args.add(order.getBeginTime());
			args.add(order.getEndTime());
		}

		if(order.getStatus()!=null) {

			if(order.getStatus().intValue()==-99) {
				sqlAppend.append(" and ("+status+"=1 or "+status+"=0) ");
			}else {
				sqlAppend.append(" and "+status+"=? ");
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
			CountSql.append(" from e_order as A ");
			if(accountId==null) {
				CountSql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
			}else {
				if(isShopManager){
					CountSql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId and S.manager_accountId="+accountId);
				}else{
					CountSql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
				}
			}
			if(accountId==null) {
				CountSql.append(" where 1=1 ");
			}else {
				// 管理员可以查询0和1和 0 or 1
				if(isShopManager) {
					CountSql.append(" where S.manager_accountId=? ");
				}else {
					// 除了0和1就查send_accountId
					CountSql.append(" where S.send_accountId=? ");
				}
				args.add(accountId);
			}
			CountSql.append(sqlAppend);
			List<Map<String, Object>> countMap = jdbcTemplate.query(CountSql.toString(),a, ResultSetToBeanHelper.resultSetToListMap());

			if(countMap.size()!=0) {
				Map<String, Object> map = countMap.get(0);
				Count = (Integer) map.get("Count");
			}


			// 加上分页
			a = SQLOper.addPaging(sql, a, order.getPageIndex(), order.getPageSize(), false);

		}

//		System.out.println(sql.toString());
		logger.info("[sql:{}]",sql.toString());
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
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
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
		List<OrderProductInfo> result = (List<OrderProductInfo>)jdbcTemplate.execute(
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
		String result = (String)jdbcTemplate.execute(
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
		String result = (String)jdbcTemplate.execute(
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
		String result = (String)jdbcTemplate.execute(
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
		String sql = "select d.id, d.orderId, d.pid, d.number, d.price, d.total, d.settlementType, d.integral, d.genre, d.ifEvaluate, d.voucherCode, d.voucherMoney, d.activityId, d.beginDate, d.endDate, d.hashTicket, d.typeId, d.typeName, d.comboPid,d.ifKouJian,p.pname,p.pictureUrl,p.vipPrice,s.shopPrice from e_order_detail as d left join e_product as p on d.pid=p.pid left join e_order as o on d.orderId=o.orderId left join e_shop_product as s on o.shopId=s.shopId and d.pid=s.pid where d.orderId=?";
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
		String e_order_sql = "update e_order set status=? where orderId=? and eid=?";
		String e_appMerchant_order_send_sql = "update e_appMerchant_order_send set send_status=? where orderId=? and eid=?";
		jdbcTemplate.update(e_order_sql,new Object[] {status,orderId,eid});
		jdbcTemplate.update(e_appMerchant_order_send_sql,new Object[] {status,orderId,eid});
		return 1;
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
	public OrderModel selectOrderByOrderId(Integer orderId) {
		String sql="select status,eid,orderId,orderNo,sendAddress from e_order where orderId=? ";
		try {
			OrderModel queryForObject = jdbcTemplate.queryForObject(sql.toString(),new Object[] {orderId}, new BeanPropertyRowMapper<>(OrderModel.class));
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_order_tuihuo_select(final Integer orderId, final String beginTime, final String endTime,
			final Integer eid, final String eName, final String orderNo, final String account, final String th_type, final String paymentPlatform,
			final Integer ifaudit, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_order_tuihuo_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("eName", eName);
						cs.setObject("orderNo", orderNo);
						cs.setObject("account", account);
						cs.setObject("th_type", th_type);
						cs.setObject("paymentPlatform", paymentPlatform);
						cs.setObject("ifaudit", ifaudit);
						if(IsSelectAll!=null&&IsSelectAll) {
							cs.setObject("IsSelectAll", IsSelectAll);
							cs.setObject("PageIndex", null);
							cs.setObject("PageSize", null);
						}else {
							cs.setObject("IsSelectAll", false);
							if(PageIndex!=null&&PageSize!=null) {
								cs.setObject("PageIndex", PageIndex);
								cs.setObject("PageSize", PageSize);
							}else {
								cs.setObject("PageIndex", 1);
								cs.setObject("PageSize", 20);
							}
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("paymentMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("balanceTotal", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("integralTotal", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("th_money", Types.NUMERIC);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("orderTuihuos", result);
							map.put("Count", cs.getInt("Count"));
							map.put("paymentMoney", cs.getDouble("paymentMoney"));
							map.put("balanceTotal", cs.getDouble("balanceTotal"));
							map.put("integralTotal", cs.getDouble("integralTotal"));
							map.put("th_money", cs.getDouble("th_money"));
							return map;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
	public Map<String, Object> Proc_Backstage_order_tuihuo_select(final Integer orderId) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_order_tuihuo_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("beginTime", null);
						cs.setObject("endTime", null);
						cs.setObject("eid", null);
						cs.setObject("eName", null);
						cs.setObject("orderNo", null);
						cs.setObject("account", null);
						cs.setObject("th_type", null);
						cs.setObject("paymentPlatform", null);
						cs.setObject("ifaudit", null);
						cs.setObject("PageIndex", 1);
						cs.setObject("PageSize", 10);
						cs.setObject("IsSelectAll", false);
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("paymentMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("balanceTotal", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("integralTotal", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("th_money", Types.NUMERIC);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							if(result.size()>0) {
								map.put("orderTuihuo", result.get(0));
							}else {
								map.put("orderTuihuo", null);
							}
							map.put("Count", cs.getInt("Count"));
							map.put("paymentMoney", cs.getDouble("paymentMoney"));
							map.put("balanceTotal", cs.getDouble("balanceTotal"));
							map.put("integralTotal", cs.getDouble("integralTotal"));
							map.put("th_money", cs.getDouble("th_money"));
							return map;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IOrderDao#Proc_Backstage_order_tuihuo_audit(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Double, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String Proc_Backstage_order_tuihuo_audit(final Integer orderId, final Integer eid, final Integer ifaudit, final String auditType,
			final String auditRen, final Double th_money, final String auditBankAccount, final String auditremark, final String auditUser) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_order_tuihuo_audit(?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("eid", eid);
						cs.setObject("ifaudit", ifaudit);
						cs.setObject("auditType", auditType);
						cs.setObject("auditRen", auditRen);
						cs.setObject("th_money", th_money);
						cs.setObject("auditBankAccount", auditBankAccount);
						cs.setObject("auditremark", auditremark);
						cs.setObject("auditUser", auditUser);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						try {
							return cs.getString("TAG");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}
					}
				});
		return result;
	}

	@Override
	public EEnterpriseWechatliteapp selectMchidAndPartnerKey(Integer eid, Integer appType) {
		String sql="select appId, eid, liteappType, authorizerRefreshToken, createTime, liteappName, appSecret, mchId, partnerKey, isValid, appType, isAuthorization, codeTemplateId, nextTemplateId, styleCode, liteappShearPic, liteappShearDesc, isJoinWechatOpen, joinWechatOpenRemark, originalId, componentIndex from e_enterprise_wechatliteapp where eid=? and appType=? ";
		try {
			EEnterpriseWechatliteapp queryForObject = jdbcTemplate.queryForObject(sql, new Object[] {eid,appType}, new BeanPropertyRowMapper<>(EEnterpriseWechatliteapp.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	@Override
	public List<EEnterpriseWechatliteapp> selectMchidAndPartnerKey(Integer eid) {
		String sql="select appId, eid, liteappType, authorizerRefreshToken, createTime, liteappName, appSecret, mchId, partnerKey, isValid, appType, isAuthorization, codeTemplateId, nextTemplateId, styleCode, liteappShearPic, liteappShearDesc, isJoinWechatOpen, joinWechatOpenRemark, originalId, componentIndex from e_enterprise_wechatliteapp where eid=?";
			List<EEnterpriseWechatliteapp> query = jdbcTemplate.query(sql, new Object[] {eid}, new BeanPropertyRowMapper<>(EEnterpriseWechatliteapp.class));
			return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_order_payment_select(final Integer orderId) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_order_payment_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("orderId", orderId);
						cs.setObject("beginTime", null);
						cs.setObject("endTime", null);
						cs.setObject("eid", null);
						cs.setObject("eName", null);
						cs.setObject("orderNo", null);
						cs.setObject("paymentPlatform", null);
						cs.setObject("paymentAccount", null);
						cs.setObject("paymentNo", null);
						cs.setObject("in_come", null);
						cs.setObject("paySource", null);
						cs.setObject("isApplyWithdraw", null);
						cs.setObject("PageIndex", 1);
						cs.setObject("PageSize", 10);
						cs.setObject("IsSelectAll", false);
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("paymentMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							if(result.size()>0) {
								map.put("orderPayment", result.get(0));
							}else {
								map.put("orderPayment", null);
							}
							map.put("Count", cs.getInt("Count"));
							map.put("paymentMoney", cs.getDouble("paymentMoney"));
							return map;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IOrderDao#insertReLog(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer insertRefundLog(Integer orderId, String logger, String createUser, Integer dataType) {
		String sql = "insert into e_order_tuihuo_progress(orderId, disposeDate, logger, createUser, dataType) values(?, getdate(), ?, ?, ?)";
		int update = jdbcTemplate.update(sql,new Object[] {orderId,logger,createUser,dataType});
		return update;
	}

	@Override
	public List<Map<String, Object>> selectRefundLog(Integer orderId) {
		// TODO Auto-generated method stub
		String sql="select  id, orderId, disposeDate, logger, dateCreated, createUser, dataType  from e_order_tuihuo_progress where orderId=? order by dateCreated desc";
		List<Map<String, Object>> query = jdbcTemplate.query(sql, new Object[] {orderId},ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_balance_every_day_select(final String beginTime,final String endTime,final Integer eid,final String ename,final Integer PageIndex,final Integer PageSize,final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_balance_every_day_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", null);
						cs.setObject("ename", null);
						if(IsSelectAll!=null&&IsSelectAll) {
							cs.setObject("IsSelectAll", IsSelectAll);
							cs.setObject("PageIndex", null);
							cs.setObject("PageSize", null);
						}else {
							cs.setObject("IsSelectAll", false);
							if(PageIndex!=null&&PageSize!=null) {
								cs.setObject("PageIndex", PageIndex);
								cs.setObject("PageSize", PageSize);
							}else {
								cs.setObject("PageIndex", 1);
								cs.setObject("PageSize", 20);
							}
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("registerWechatCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("registerLiteappCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderWechatCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderLiteappCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderClientCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("productSellCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("eticketUsedCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderCODCount", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("linePayMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("balanceEvery", result);
							map.put("Count", cs.getInt("Count"));
							map.put("registerWechatCount", cs.getInt("registerWechatCount"));
							map.put("registerLiteappCount", cs.getInt("registerLiteappCount"));
							map.put("orderWechatCount", cs.getInt("orderWechatCount"));
							map.put("orderLiteappCount", cs.getInt("orderLiteappCount"));
							map.put("orderClientCount", cs.getInt("orderClientCount"));
							map.put("productSellCount", cs.getInt("productSellCount"));
							map.put("eticketUsedCount", cs.getInt("eticketUsedCount"));
							map.put("orderCODCount", cs.getInt("orderCODCount"));
							map.put("linePayMoney", cs.getDouble("linePayMoney"));
							return map;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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

	@Override
	public Map<String, Object> selectTicketOrderByOrder(Order order) {
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("A.orderId, A.orderGroup, A.orderGuid, A.orderNo, A.eid, A.shopId, A.clientId, A.did, A.delivery, A.sendAddress, A.phone, A.contacts, A.dateCreated, A.appointmentTime, A.sendRemark, A.account, A.sdkType, A.mobileType, A.mobileIMEL,");
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, A.status, A.ifEvaluate, A.startMutual, A.ifMutual, A.mutualLog, A.mutualTime, A.mutualId,");
		sql.append("A.deliverTime, A.deliverMan, A.completeTime, A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.cancelReason, A.cancelTime, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.staffId, A.paymentPlatform, A.sourceGroup ");
		sql.append(" from e_order A  ");
		sql.append(" inner join (select orderId from e_ticket_contacts where eid=1 and ticketType='票销售'  group by orderId  ) AS B ON A.orderId=B.orderId ");
		sql.append(" where 1=1 ");
		
		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();		
		
		
		if(order.getEid()!=null) {
			sqlAppend.append(" and A.eid=? ");
			args.add(order.getEid());
		}
		
		if(order.getPayMode()!=null) {
			sqlAppend.append(" and A.payMode=? ");
			args.add(order.getPayMode());
		}
		
		if(order.getShopId()!=null) {
			sqlAppend.append(" and A.shopId=? ");
			args.add(order.getShopId());
		}
		
		if(order.getOrderNo()!=null) {
			sqlAppend.append(" and A.orderNo=? ");
			args.add(order.getOrderNo());
		}
		

		if(order.getBeginTime()!=null&&order.getEndTime()!=null) {
			sqlAppend.append(" and (A.dateCreated between ? and ?) ");
			args.add(order.getBeginTime());
			args.add(order.getEndTime());
		}

		if(order.getStatus()!=null) {
			
			if(order.getStatus().intValue()==-99) {
				sqlAppend.append(" and (A.status=1 or A.status=0) ");
			}else {
				sqlAppend.append(" and A.status=? ");
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
			CountSql.append(" from e_order as A where 1=1 ");
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
	public Map<String, Object> selectTicketOrderByOrder(Integer accountId, Order order) {
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("A.orderId, A.orderGroup, A.orderGuid, A.orderNo, A.eid, A.shopId, A.clientId, A.did, A.delivery, A.sendAddress, A.phone, A.contacts, A.dateCreated, A.appointmentTime, A.sendRemark, A.account, A.sdkType, A.mobileType, A.mobileIMEL,");
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate, A.startMutual, A.ifMutual, A.mutualLog, A.mutualTime, A.mutualId,");
		sql.append("A.deliverTime, A.deliverMan, A.completeTime, A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.cancelReason, A.cancelTime, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.staffId, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "A.status";
			sql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
		}else {
			sql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
		}
		sql.append(" inner join (select orderId from e_ticket_contacts where eid=1 and ticketType='票销售'  group by orderId  ) AS B ON A.orderId=B.orderId ");

		sql.append(" left join e_appMerchant_account AS Z ON S.manager_accountId=Z.accountId ");
		sql.append(" left join e_appMerchant_account AS X ON S.send_accountId=X.accountId ");

		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();		
		if(accountId==null) {
			sql.append(" where 1=1 ");
		}else {
			// 管理员可以查询0和1和 0 or 1
			if(order.getStatus()!=null&&(order.getStatus().intValue()==-99||order.getStatus().intValue()==0||order.getStatus().intValue()==1)) {
				sql.append(" where S.manager_accountId=? ");
			}else {
				// 除了0和1就查send_accountId
				sql.append(" where S.send_accountId=? ");
			}
			args.add(accountId);
		}
		
		if(order.getEid()!=null) {
			sqlAppend.append(" and A.eid=? ");
			args.add(order.getEid());
		}
		
		if(order.getPayMode()!=null) {
			sqlAppend.append(" and A.payMode=? ");
			args.add(order.getPayMode());
		}
		
		if(order.getShopId()!=null) {
			sqlAppend.append(" and A.shopId=? ");
			args.add(order.getShopId());
		}
		
		if(order.getOrderNo()!=null) {
			sqlAppend.append(" and A.orderNo=? ");
			args.add(order.getOrderNo());
		}
		

		if(order.getBeginTime()!=null&&order.getEndTime()!=null) {
			sqlAppend.append(" and (A.dateCreated between ? and ?) ");
			args.add(order.getBeginTime());
			args.add(order.getEndTime());
		}

		if(order.getStatus()!=null) {
			
			if(order.getStatus().intValue()==-99) {
				sqlAppend.append(" and ("+status+"=1 or "+status+"=0) ");
			}else {
				sqlAppend.append(" and "+status+"=? ");
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
			CountSql.append(" from e_order as A where 1=1 ");
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
	public Map<String, Object> selectTicketOrderByOrder(Integer accountId, Order order, boolean isShopManager) {
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("A.orderId, A.orderGroup, A.orderGuid, A.orderNo, A.eid, A.shopId, A.clientId, A.did, A.delivery, A.sendAddress, A.phone, A.contacts, A.dateCreated, A.appointmentTime, A.sendRemark, A.account, A.sdkType, A.mobileType, A.mobileIMEL,");
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate, A.startMutual, A.ifMutual, A.mutualLog, A.mutualTime, A.mutualId,");
		sql.append("A.deliverTime, A.deliverMan, A.completeTime, A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.cancelReason, A.cancelTime, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.staffId, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "A.status";
			sql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
		}else {
			if(isShopManager){
				sql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId and S.mananger_accountId="+accountId);
			}else{
				sql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
			}
		}
		sql.append(" inner join (select orderId from e_ticket_contacts where eid=1 and ticketType='票销售'  group by orderId  ) AS B ON A.orderId=B.orderId ");

		sql.append(" left join e_appMerchant_account AS Z ON S.manager_accountId=Z.accountId ");
		sql.append(" left join e_appMerchant_account AS X ON S.send_accountId=X.accountId ");

		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();
		if(accountId==null) {
			sql.append(" where 1=1 ");
		}else {
			// 管理员可以查询0和1和 0 or 1
			if(isShopManager) {
				sql.append(" where S.manager_accountId=? ");
			}else {
				// 除了0和1就查send_accountId
				sql.append(" where S.send_accountId=? ");
			}
			args.add(accountId);
		}

		if(order.getEid()!=null) {
			sqlAppend.append(" and A.eid=? ");
			args.add(order.getEid());
		}

		if(order.getPayMode()!=null) {
			sqlAppend.append(" and A.payMode=? ");
			args.add(order.getPayMode());
		}

		if(order.getShopId()!=null) {
			sqlAppend.append(" and A.shopId=? ");
			args.add(order.getShopId());
		}

		if(order.getOrderNo()!=null) {
			sqlAppend.append(" and A.orderNo=? ");
			args.add(order.getOrderNo());
		}


		if(order.getBeginTime()!=null&&order.getEndTime()!=null) {
			sqlAppend.append(" and (A.dateCreated between ? and ?) ");
			args.add(order.getBeginTime());
			args.add(order.getEndTime());
		}

		if(order.getStatus()!=null) {

			if(order.getStatus().intValue()==-99) {
				sqlAppend.append(" and ("+status+"=1 or "+status+"=0) ");
			}else {
				sqlAppend.append(" and "+status+"=? ");
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
			CountSql.append(" from e_order as A  ");

			if(accountId==null) {
				CountSql.append(" left join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
			}else {
				if(isShopManager){
					CountSql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId and S.manager_accountId="+accountId);
				}else{
					CountSql.append(" inner join e_appMerchant_order_send AS S ON A.orderId=S.orderId ");
				}
			}
			if(accountId==null) {
				CountSql.append(" where 1=1 ");
			}else {
				// 管理员可以查询0和1和 0 or 1
				if(isShopManager) {
					CountSql.append(" where S.manager_accountId=? ");
				}else {
					// 除了0和1就查send_accountId
					CountSql.append(" where S.send_accountId=? ");
				}
				args.add(accountId);
			}

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

	/**
	 * 
	 */
	@Override
	public Integer insertDeliverymanOrder(Integer manager_accountId, Integer send_accountId, Integer eid,
			Integer shopId, Integer orderId, Integer send_status, String remark) {
		String sql="insert into e_appMerchant_order_send(manager_accountId,send_accountId,eid,shopId,orderId,creater,send_status,remark) values(?,?,?,?,?,'sys',?,?);";
		int update = jdbcTemplate.update(sql,new Object[] {manager_accountId,send_accountId,eid,shopId,orderId,send_status,remark});
		return update;
	}



	/**
	 *
	 */
	@Override
	public List<EAppMerchantOrderSend> selectEAppMerchantOrderSendByOrderId(Integer orderId) {
		String sql="select xid, manager_accountId, send_accountId, eid, shopId, orderId, createTime, creater, send_status, remark from e_appMerchant_order_send where orderId=?";

		try {
			List<EAppMerchantOrderSend> queryForObject = jdbcTemplate.query(sql.toString(),new Object[]{orderId}, new BeanPropertyRowMapper<>(EAppMerchantOrderSend.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public Integer updateDeliverymanOrder(Integer orderId, Integer send_accountId, Integer send_status) {
		String sql="update e_appMerchant_order_send set send_accountId=?,send_status=? where orderId=?";
		int update = jdbcTemplate.update(sql,new Object[] {send_accountId,send_status,orderId});
		return update;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_XHX_appMerchant_order_select(final Integer accountId, final Order order,final Boolean isTicket) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_XHX_appMerchant_order_select(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					@Override
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("accountId", accountId);
						cs.setObject("status", order.getStatus());
						cs.setObject("shopId", order.getShopId());
						cs.setObject("beginTime", order.getBeginTime());
						cs.setObject("endTime", order.getEndTime());
						cs.setObject("payMode", order.getPayMode());
						cs.setObject("orderNo", order.getOrderNo());
						cs.setObject("isTicket", isTicket);
						cs.setObject("eid", order.getEid());
						if(order.getIsSelectAll()!=null&&order.getIsSelectAll()) {
							cs.setObject("IsSelectAll", order.getIsSelectAll());
							cs.setObject("PageIndex", null);
							cs.setObject("PageSize", null);
						}else {
							cs.setObject("IsSelectAll", false);
							if(order.getPageIndex()!=null&&order.getPageSize()!=null) {
								cs.setObject("PageIndex", order.getPageIndex());
								cs.setObject("PageSize", order.getPageSize());
							}else {
								cs.setObject("PageIndex", 1);
								cs.setObject("PageSize", 20);
							}
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
							// TODO Auto-generated catch block
							e.printStackTrace();
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

	@Override
	public Map<String, Object> selectEEnterpriseAppPayParamByAppId(String appId) {
		String sql = "select a.eid,a.wechatAppId,a.wechatMchId from e_enterprise_app_pay_param a inner join e_enterprise_wechatliteapp b on a.eid=b.eid where b.appId=?";
		Map<String, Object> stringObjectMap = null;
		try {
			stringObjectMap = jdbcTemplate.queryForMap(sql, appId);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return stringObjectMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_order_detail_eticketTuiKe_select(final String beginTime, final String endTime
			, final Integer eid,final  String tuiKeAccount, final String tuiKeManageAccount, final String pname, final String account
			, final Integer status,final Boolean isHistory, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_order_detail_eticketTuiKe_select(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					@Override
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("tuiKeAccount", tuiKeAccount);
						cs.setObject("tuiKeManageAccount", tuiKeManageAccount);
						cs.setObject("pname", pname);
						cs.setObject("account", account);
						cs.setObject("status", status);
						cs.setObject("isHistory", isHistory);
						if(IsSelectAll!=null&&IsSelectAll) {
							cs.setObject("IsSelectAll", IsSelectAll);
							cs.setObject("PageIndex", null);
							cs.setObject("PageSize", null);
						}else {
							cs.setObject("IsSelectAll", false);
							if(PageIndex!=null&&PageSize!=null) {
								cs.setObject("PageIndex", PageIndex);
								cs.setObject("PageSize", PageSize);
							}else {
								cs.setObject("PageIndex", 1);
								cs.setObject("PageSize", 20);
							}
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("eticketTuiKeStatistics", result);
							map.put("Count", cs.getInt("Count"));
							return map;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
}
