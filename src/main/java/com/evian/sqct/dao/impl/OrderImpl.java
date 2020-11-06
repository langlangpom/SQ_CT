package com.evian.sqct.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.order.request.*;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.dao.IBaseDao;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.DaoUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.evian.sqct.util.SQLOper;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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

	@Autowired
	private IBaseDao baseDao;

	@Override
	public Map<String, Object> selectOrderByShopId_v2(Order order) {
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("orderId, orderGroup, orderGuid, orderNo, eid, shopId, clientId, did, delivery, sendAddress, phone, contacts, dateCreated, appointmentTime, sendRemark, account, sdkType, mobileType, mobileIMEL,");
		sql.append("appVer, orderRemark, ticketCount, bumfCount, receivableTotal, balanceTotal, integralTotal, linePaySuceed, payMode, (select top 1 [status] from e_order_status with(nolock) where orderId=orderId order by xid desc) as status, ifEvaluate,");
		sql.append(" affirmTime, activityType, privilegeId, code_no, discountMoney, discountDescribe, freight, integral, del, has_invoice,");
		sql.append("confirm_take, cityId, districtId, location, has_return, paymentPlatform, sourceGroup ");
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
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate,");
		sql.append("A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "(select top 1 [status] from e_order_status with(nolock) where orderId=A.orderId order by xid desc) ";
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
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate,");
		sql.append(" A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "(select top 1 [status] from e_order_status with(nolock) where orderId=A.orderId order by xid desc) ";
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
				"{call Proc_Backstage_order_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
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
					cs.setObject("linePaySuceed", order.getLinePaySuceed());
					cs.setObject("has_return", order.getHas_return());
					cs.setObject("sourceGroup", order.getSourceGroup());
					cs.setObject("sendAddress", order.getSendAddress());
					cs.setObject("deliverMan", order.getDeliverMan());
					cs.setObject("isFreight", order.getIsFreight());
					cs.setObject("phone", order.getPhone());
					if(order.getIsSelectAll()!=null&&order.getIsSelectAll()) {
						cs.setObject("IsSelectAll", order.getIsSelectAll());
						cs.setObject("PageIndex", null);
						cs.setObject("PageSize", null);
					}else {
						cs.setObject("IsSelectAll", false);
						if(order.getPageIndex()!=null&&order.getPageIndex()!=null) {
							cs.setObject("PageIndex", order.getPageIndex());
							cs.setObject("PageSize", order.getPageSize());
						}else {
							cs.setObject("PageIndex", 1);
							cs.setObject("PageSize", 20);
						}
					}
					cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
					cs.registerOutParameter("paymentMoney", Types.INTEGER);// 注册输出参数的类型
					cs.registerOutParameter("refundMoney", Types.INTEGER);// 注册输出参数的类型
					cs.registerOutParameter("balanceTotal", Types.INTEGER);// 注册输出参数的类型
					cs.registerOutParameter("integralTotal", Types.INTEGER);// 注册输出参数的类型
					cs.execute();
					ResultSet rs = cs.executeQuery();
					Map<String, Object> map = new HashMap<String, Object>();
					try {
						List<OrderModel> resul = ResultSetToBeanHelper.resultSetToList(rs, OrderModel.class);
						map.put("orders", resul);
						map.put("Count", cs.getInt("Count"));
						map.put("paymentMoney", cs.getInt("paymentMoney"));
						map.put("refundMoney", cs.getInt("refundMoney"));
						map.put("balanceTotal", cs.getInt("balanceTotal"));
						map.put("integralTotal", cs.getInt("integralTotal"));
						return map;
					} catch (Exception e) {
						return null;
					}finally{
						if(rs!=null){
							rs.close();
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
				(CallableStatementCallback) cs -> {
					cs.setObject("orderId", orderId);
					cs.setObject("eid", eid);
					cs.execute();
					ResultSet rs = cs.executeQuery();

					try {
						List<OrderProductInfo> resul = ResultSetToBeanHelper.resultSetToList(rs, OrderProductInfo.class);
						return resul;
					} catch (Exception e) {
						return null;
					}finally{
						if(rs!=null){
							rs.close();
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
				(CallableStatementCallback) cs -> {
					cs.setObject("eid", eid);
					cs.setObject("orderId", orderId);
					cs.setObject("remark", remark);
					cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("tag");
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String drawback(final Integer orderId, final Integer userId, final String th_type,
			final String th_reason, final String th_remark, final String th_CreateUser) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_order_tuihuo_apply(?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("orderId", orderId);
					cs.setObject("clientId", userId);
					cs.setObject("th_type", th_type);
					cs.setObject("th_reason", th_reason);
					cs.setObject("th_remark", th_remark);
					cs.setObject("th_CreateUser", th_CreateUser);
					cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("tag");
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String cancellationOfOrder(final Integer orderId, final Integer userId,
			final Integer eid, final String cancelReason, final Integer e_order_detail_ID) {
		String result = (String)jdbcTemplate.execute(
				"{call PROC_LIDAN_Backstage_mid_ORDER_QX(?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("orderId", orderId);
					cs.setObject("clientId", userId);
					cs.setObject("eid", eid);
					cs.setObject("cancelReason", cancelReason);
					cs.setObject("e_order_detail_ID", e_order_detail_ID);
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.registerOutParameter("xMsg", Types.NVARCHAR);// 注册输出参数的类型
					cs.registerOutParameter("OrderStatuMsg", Types.NVARCHAR);// 注册输出参数的类型
					cs.registerOutParameter("payMode", Types.BIT);// 注册输出参数的类型
					cs.registerOutParameter("linePaySuceed", Types.BIT);// 注册输出参数的类型
					cs.registerOutParameter("smsMsg", Types.NVARCHAR);// 注册输出参数的类型
					cs.registerOutParameter("shopID", Types.INTEGER);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
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
//		String e_order_sql = "update e_order set status=? where orderId=? and eid=?";
		String e_order_sql = "insert into e_order_status(orderId,status) values(?,?)";
		String e_appMerchant_order_send_sql = "update e_appMerchant_order_send set send_status=? where orderId=? and eid=?";
		jdbcTemplate.update(e_order_sql,new Object[] {orderId,status});
		jdbcTemplate.update(e_appMerchant_order_send_sql,new Object[] {status,orderId,eid});
		return 1;
	}

	
	@Override
	public Order selectOrderByOrderIdAndEid(Integer orderId, Integer eid) {
		String sql="select (select top 1 [status] from e_order_status with(nolock) where orderId=? order by xid desc) as status,eid,orderId from e_order where orderId=?";
		try {
			Order queryForObject = jdbcTemplate.queryForObject(sql.toString(),new Object[] {orderId,orderId}, new BeanPropertyRowMapper<>(Order.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	@Override
	public OrderModel selectOrderByOrderId(Integer orderId) {
		StringBuilder sql= new StringBuilder("select ")
		.append("orderId, orderGroup, orderGuid, orderNo, eid, shopId, clientId, did, delivery, sendAddress, phone, contacts, dateCreated,")
		.append("appointmentTime, sendRemark, account, sdkType, mobileType, mobileIMEL, appVer, orderRemark, ticketCount,")
		.append("bumfCount, receivableTotal, balanceTotal, integralTotal, linePaySuceed, payMode, (select top 1 [status] from e_order_status with(nolock) where orderId=orderId order by xid desc) as status, ifEvaluate,")
		.append(" affirmTime, activityType, privilegeId, code_no, discountMoney, discountDescribe, freight,")
		.append("integral, del, has_invoice, confirm_take, cityId, districtId, location, has_return,")
		.append("paymentPlatform, sourceGroup ")
		.append(" from e_order where orderId=?");
		try {
			OrderModel queryForObject = jdbcTemplate.queryForObject(sql.toString(),new Object[] {orderId}, new BeanPropertyRowMapper<>(OrderModel.class));
			return queryForObject;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<EOrderLogistics> selectEOrderLogisticsByOrderId(Integer orderId) {
		String sql = "select logisticsId, logKey, logisticsType, orderId, logisticsDescribe, contactsId, contacts, phone, photo, location, arriveTime, dateCreated, log_state, dataReson, WEBID from e_order_logistics where orderId=? order by dateCreated DESC";
		List<EOrderLogistics> query = jdbcTemplate.query(sql.toString(),new Object[] {orderId}, new BeanPropertyRowMapper<>(EOrderLogistics.class));
		return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_order_tuihuo_select(ProcBackstageOrderTuihuoSelectReqDTO dto) {
		return DaoUtil.resultRename(baseDao.agencyDB("Proc_Backstage_order_tuihuo_select", dto),"orderTuihuos");
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IOrderDao#Proc_Backstage_order_tuihuo_audit(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Double, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String Proc_Backstage_order_tuihuo_audit(ProcBackstageOrderTuihuoAuditReqDTO dto) {
		return DaoUtil.resultTAG(baseDao.agencyDB("Proc_Backstage_order_tuihuo_audit",dto));
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
	public Map<String, Object> Proc_Backstage_order_payment_select(ProcBackstageOrderPaymentSelectReqDTO dto) {
		return DaoUtil.resultRename(baseDao.agencyDB("Proc_Backstage_order_payment_select",dto),"orderPayment");

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
				(CallableStatementCallback) cs -> {
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
						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						map.put("balanceEvery", resul);
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
				});
		return result;
	}

	@Override
	public Map<String, Object> selectTicketOrderByOrder(Order order) {
		StringBuilder sql = new StringBuilder("select Row_Number() over(order by dateCreated DESC) as row,");
		sql.append("A.orderId, A.orderGroup, A.orderGuid, A.orderNo, A.eid, A.shopId, A.clientId, A.did, A.delivery, A.sendAddress, A.phone, A.contacts, A.dateCreated, A.appointmentTime, A.sendRemark, A.account, A.sdkType, A.mobileType, A.mobileIMEL,");
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, (select top 1 [status] from e_order_status with(nolock) where orderId=A.orderId order by xid desc) as status, A.ifEvaluate,");
		sql.append(" A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.paymentPlatform, A.sourceGroup ");
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
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate,");
		sql.append(" A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
			status = "(select top 1 [status] from e_order_status with(nolock) where orderId=A.orderId order by xid desc)";
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
		sql.append("A.appVer, A.orderRemark, A.ticketCount, A.bumfCount, A.receivableTotal, A.balanceTotal, A.integralTotal, A.linePaySuceed, A.payMode, S.send_status as status, A.ifEvaluate,");
		sql.append(" A.affirmTime, A.activityType, A.privilegeId, A.code_no, A.discountMoney, A.discountDescribe, A.freight, A.integral, A.del, A.has_invoice,");
		sql.append("A.confirm_take, A.cityId, A.districtId, A.location, A.has_return, A.paymentPlatform, A.sourceGroup,Z.account as manager_account,Z.remark as manager_remark,X.account as send_account,X.remark as send_remark ");
		sql.append(" from e_order A  ");

		// 如果是accountId==null 说明是查询全部订单  需要查询e_order 的状态status
		String status = "S.send_status";
		if(accountId==null) {
//			status = "A.status";
			status = "(select top 1 [status] from e_order_status with(nolock) where orderId=A.orderId order by xid desc)";
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
		String sql="insert into e_appMerchant_order_send(manager_accountId,send_accountId,eid,shopId,orderId,creater,send_status,remark) values(?,?,?,?,?,'SQSHAPIsys',?,?);";
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
				(CallableStatementCallback) cs -> {
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
						List<OrderModel> resul = ResultSetToBeanHelper.resultSetToList(rs, OrderModel.class);
						map.put("orders", resul);
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
				(CallableStatementCallback) cs -> {
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
						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						map.put("eticketTuiKeStatistics", resul);
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
				});
		return result;

	}@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_order_detail_eticketTuiKe_select_statistics(final String beginTime, final String endTime
			, final Integer eid,final  String tuiKeAccount, final String tuiKeManageAccount, final String pname
			, final Integer status,final Boolean isHistory, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_order_detail_eticketTuiKe_select_statistics(?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("beginTime", beginTime);
					cs.setObject("endTime", endTime);
					cs.setObject("eid", eid);
					cs.setObject("tuiKeAccount", tuiKeAccount);
					cs.setObject("tuiKeManageAccount", tuiKeManageAccount);
					cs.setObject("pname", pname);
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
						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						map.put("eticketTuiKeStatistics", resul);
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
				});
		return result;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String PROC_APP_OrderConfirm_UPDATE(final Integer orderID, final Integer eid, final String UserAccount) {
		String result = (String)jdbcTemplate.execute(
				"{call PROC_APP_OrderConfirm_UPDATE(?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("orderID", orderID);
					cs.setObject("eid", eid);
					cs.setObject("UserAccount", UserAccount);
					cs.registerOutParameter("RETURN_message", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					try {
						return cs.getString("RETURN_message");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				});
		return result;
	}


	/**
	 * 调用表变量 入参数组
	 * @param Send_GUID
	 * @param procAppOrderSendSaveUPDTO
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String PROC_APP_orderSend_saveUP(final String Send_GUID, final ProcAppOrderSendSaveUPDTO procAppOrderSendSaveUPDTO) {
		String result = (String)jdbcTemplate.execute(
				(CallableStatementCreator) con -> {
					SQLServerCallableStatement cs = con.prepareCall("{call PROC_APP_orderSend_saveUP(?,?,?,?,?,?,?,?,?)}").unwrap(com.microsoft.sqlserver.jdbc.SQLServerCallableStatement.class);
					// 使用SQLServerDataTable对象来传递表参数
					SQLServerDataTable sourceTable = new SQLServerDataTable();
					// 在表对象中添加字段的信息
					sourceTable.addColumnMetadata("orderID", Types.INTEGER);
					sourceTable.addColumnMetadata("eid", Types.INTEGER);
					sourceTable.addColumnMetadata("ShopID", Types.INTEGER);
					JSONArray orderSend = null;
					try {
						orderSend = procAppOrderSendSaveUPDTO.getOrderSend();
					} catch (JSONException e) {
						logger.error("{}",e);
						throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
					}
					for (int i = 0; i <orderSend.size() ; i++) {
						JSONObject jsonObject = orderSend.getJSONObject(i);
						sourceTable.addRow(jsonObject.getInteger("orderID"),
								jsonObject.getInteger("eid"),
								jsonObject.getInteger("ShopID"));
					}

					cs.setStructured(4,"TVP_e_order_Send",sourceTable);
					cs.setObject("send_accountId", procAppOrderSendSaveUPDTO.getSendAccountId());
					cs.setObject("manager_accountId", procAppOrderSendSaveUPDTO.getManagerAccountId());
					cs.setObject("send_status", procAppOrderSendSaveUPDTO.getSendStatus());
					cs.setObject("remark", procAppOrderSendSaveUPDTO.getRemark());
					cs.setObject("creater", procAppOrderSendSaveUPDTO.getCreater());
					cs.setObject("DataSource", procAppOrderSendSaveUPDTO.getDataSource());
					cs.setObject("Send_GUID", Send_GUID);// 注册输出参数的类型
					cs.registerOutParameter("RETURN_message", Types.NVARCHAR);// 注册输出参数的类型
					return cs;
			},
			(CallableStatementCallback) cs -> {
				try {
					cs.execute();
					return cs.getString("RETURN_message");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String PROC_APP_orderSend_saveUP_XML(final String Send_GUID, final ProcAppOrderSendSaveUPDTO procAppOrderSendSaveUPDTO) {
		String result = (String)jdbcTemplate.execute(
				"{call PROC_APP_orderSend_saveUP_XML(?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					StringBuilder e_order_Send = new StringBuilder();
					JSONArray orderSend = null;
					try {
						orderSend = procAppOrderSendSaveUPDTO.getOrderSend();
					} catch (JSONException e) {
						logger.error("{}",e);
						throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
					}
					for (int i = 0; i <orderSend.size() ; i++) {
						JSONObject or = orderSend.getJSONObject(i);
						e_order_Send.append("<X_order orderID=\"");
						Integer orderID = or.getInteger("orderID");
						if(orderID==null){
							throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
						}
						e_order_Send.append(orderID);
						e_order_Send.append("\" eid=\"");
						Integer eid = or.getInteger("eid");
						if(eid==null){
							throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
						}
						e_order_Send.append(eid);
						e_order_Send.append("\" ShopID=\"");
						Integer shopID = or.getInteger("ShopID");
						if(shopID==null){
							throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
						}
						e_order_Send.append(shopID);
						e_order_Send.append("\" />");
					}
					System.out.println(e_order_Send);
					cs.setObject("Send_GUID",Send_GUID);
					cs.setObject("send_accountId", procAppOrderSendSaveUPDTO.getSendAccountId());
					cs.setObject("manager_accountId", procAppOrderSendSaveUPDTO.getManagerAccountId());
					cs.setObject("e_order_Send",e_order_Send.toString());
					cs.setObject("send_status", procAppOrderSendSaveUPDTO.getSendStatus());
					cs.setObject("remark", procAppOrderSendSaveUPDTO.getRemark());
					cs.setObject("creater", procAppOrderSendSaveUPDTO.getCreater());
					cs.setObject("DataSource", procAppOrderSendSaveUPDTO.getDataSource());
					cs.registerOutParameter("RETURN_message", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					try {
						return cs.getString("RETURN_message");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> PROC_APP_orderSend_SELECT(ProcAppOrderSendSelectDTO dto) {
		Map proc_app_orderSend_select = baseDao.agencyDB("PROC_APP_orderSend_SELECT", dto);
		Map result = DaoUtil.resultRename(proc_app_orderSend_select, "orders", "counts");
		System.out.println(result);
		List<Map<String,Object>> counts = (List<Map<String, Object>>) result.get("counts");
		if(counts!=null&&counts.size()>0){
			result.put("counts",counts.get(0));
		}else{
			result.remove("counts");
		}
		return result;

		/*Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call PROC_APP_orderSend_SELECT(?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					System.out.println(dto);
					cs.setObject("eid", dto.getEid());
					cs.setObject("shopId", dto.getShopId());
					cs.setObject("accountId", dto.getAccountId());
					cs.setObject("beginTime", dto.getBeginTime());
					cs.setObject("endTime", dto.getEndTime());
					cs.setObject("status", dto.getStatus());
					cs.setObject("payMode", dto.getPayMode());
					cs.setObject("orderNo", dto.getOrderNo());
					cs.setObject("isTicket", dto.getIsTicket());
					cs.setObject("iRows", dto.getiRows());
					cs.setObject("PageSize", dto.getPageSize());
//						cs.execute();
					ResultSet rs = cs.executeQuery();
					Map<String, Object> map = new HashMap<String, Object>();
					try {
						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						map.put("orders", resul);
						if(cs.getMoreResults()) {
							rs = cs.getResultSet();
							List<Map<String,Object>> result2 = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("counts", result2.get(0));
						}
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
				});
		return result;*/
	}

	/**
	 * 查询订单日志
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectSendOrderLogistics(Integer orderId) {
		String sql = "select  logId, orderId,orderNo,send_accountId, logMsg, createTime, creater, dataReson,msgType from e_appMerchant_order_logistics where orderId=? order by createTime desc";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, orderId);
		return maps;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String PROC_APP_orderSend_saveLogistics(final ProcAppOrderSendSaveLogisticsDTO dto) {

		Map tagMap = baseDao.agencyDB("PROC_APP_orderSend_saveLogistics", dto);
		return (String) tagMap.get("TAG");
/*
		String result = (String)jdbcTemplate.execute(
				"{call PROC_APP_orderSend_saveLogistics(?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("orderId", dto.getOrderId());
					cs.setObject("logMsg", dto.getLogMsg());
					cs.setObject("dataReson", dto.getDataReson());
					cs.setObject("creater", dto.getCreater());
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					try {
						return cs.getString("TAG");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				});
		return result;*/
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_client_pledge_tuiya_select(ProcBackstageClientPledgeTuiyaSelectDTO dto) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_client_pledge_tuiya_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("xid", dto.getXid());
					cs.setObject("beginTime", dto.getBeginTime());
					cs.setObject("endTime", dto.getEndTime());
					cs.setObject("eid", dto.getEid());
					cs.setObject("account", dto.getAccount());
					cs.setObject("tyState", dto.getTyState());
					cs.setObject("isAxceedOneYear", dto.getIsAxceedOneYear());
					cs.setObject("auditType", dto.getAuditType());
					cs.setObject("auditBeginTime", dto.getAuditBeginTime());
					cs.setObject("auditEndTime", dto.getAuditEndTime());
					cs.setBoolean("isTransfer", true);
					cs.setObject("transferStatus", dto.getTransferStatus());
					cs.setObject("PageIndex", dto.getPageIndex());
					cs.setObject("PageSize", dto.getPageSize());
					cs.setObject("IsSelectAll", dto.getIsSelectAll());
					cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
					cs.registerOutParameter("refundMoney", Types.NUMERIC);// 注册输出参数的类型
					cs.execute();
					ResultSet rs = cs.executeQuery();
					Map<String, Object> map = new HashMap<String, Object>();
					try {
						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						map.put("Count", cs.getInt("Count"));
						map.put("refundMoney", cs.getDouble("refundMoney"));
						map.put("tuiyaRecord", resul);
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
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String Proc_Backstage_client_pledge_tuiya_transfer(ProcBackstageClientPledgeTuiyaTransferDTO dto) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_client_pledge_tuiya_transfer(?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("eid", dto.getEid());
					cs.setObject("dxid", dto.getDxid());
					cs.setObject("xid", dto.getXid());
					cs.setObject("transferStatus", dto.getTransferStatus());
					cs.setObject("transferOperator", dto.getTransferOperator());
					cs.setObject("transferRemark", dto.getTransferRemark());
					cs.setObject("orderNo", dto.getOrderNo());
					cs.setObject("paymentNo", dto.getPaymentNo());
					cs.setObject("payResult", dto.getPayResult());
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					try {
						return cs.getString("TAG");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				});
		return result;
	}

	@Override
	public ClientPledgeTuiya selectClientPledgeTuiyaByOrderId(Integer orderId) {
		String sql = "select a.orderId,a.orderNo,a.dxid,a.xid,a.transferStatus,b.orderId as buyOrderId ,a.total from e_client_pledge_tuiya a with (nolock) inner join e_client_pledge b with (nolock) on a.xid=b.xid where a.orderId=?";
		return jdbcTemplate.queryForObject(sql,new Object[]{orderId},new BeanPropertyRowMapper<>(ClientPledgeTuiya.class));
	}

	/**
	 * 根据订单id查询企业用户appid openid
	 * @param orderId
	 * @return
	 */
	@Override
	public List<OrderHistorySixAccount> selectOrderHistorySixAccountByOrderId(Integer orderId) {
		StringBuilder sql = new StringBuilder("select a.orderId,b.appId,b.openid,c.mchId,c.partnerKey from e_order_history_six as a ")
				.append(" inner join e_client_wxuser_mapping as b on a.clientId = b.clientId ")
				.append(" inner join e_enterprise_wechatliteapp as c on b.appId = c.appId and a.eid=c.eid ")
				.append(" where a.orderId=?");
		return jdbcTemplate.query(sql.toString(),new Object[]{orderId},new BeanPropertyRowMapper<>(OrderHistorySixAccount.class));
	}

	/**
	 * 查询订单日志
	 *
	 * @param orderId
	 * @return
	 * [dto:FindSendOrderLogisticsByAccountIdReqDTO [accountId=1814, PageIndex=0, PageSize=10, IsSelectAll=false, beginTime=null, endTime=null]]
	 */
	@Override
	public Map<String, Object> selectSendOrderLogisticsByAccountId(FindSendOrderLogisticsByAccountIdReqDTO dto) {
		Map result = baseDao.agencyDB("Proc_XHX_send_order_logistics_select", dto);
		return DaoUtil.resultRename(result,"logistics");
	}

	@Override
	public Integer insertEOrderLogisticsByOrderId(EOrderLogistics orderLogistics) {
		String sql ="insert into e_order_logistics(logisticsType, orderId, logisticsDescribe) values(?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{orderLogistics.getLogisticsType(),orderLogistics.getOrderId(),orderLogistics.getLogisticsDescribe()});
	}

	@Override
	public Integer selectOrderStatusByOrderId(Integer orderId) {
		String sql = "(select top 1 [status] from e_order_status with(nolock) where orderId=? order by xid desc) as status";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql, orderId);
		return (Integer)map.get("status");
	}

	@Override
	public String PROC_APP_Order_PayAnother(PROCAPPOrderPayAnotherReqDTO dto) {
		Map result = baseDao.agencyDB("PROC_APP_Order_PayAnother", dto);
		return DaoUtil.resultTAG(result);
	}

	@Override
	public String PROC_APP_Order_PayAnother_Notify(PROCAPPOrderPayAnotherNotifyReqDTO dto) {
		Map result = baseDao.agencyDB("PROC_APP_Order_PayAnother_Notify", dto);
		return DaoUtil.resultTAG(result);
	}

	@Override
	public String PROC_APP_order_statusUPdate(PROCAPPOrderStatusUPdateReqDTO dto) {
		Map result = baseDao.agencyDB("PROC_APP_order_statusUPdate", dto);
		return (String) result.get("RETURN_message");
	}


	/**
	 * 根据订单id查询企业用户appid openid
	 * 根据开会说明， 企业支付到零钱，只查公众号支付信息，如果没有公众号信息不给他退
	 * 但是仔细想了一下，谁也不能保证以后只做小程序不做公众号的这种情况，到时候又来问为什么明明有支付信息却不给退的情况，说不清楚，所以只要有支付信息就给他退
	 * 2020-06-30 彭安说 退押订单在退押的时候是新生成的，不会在结存表里面
	 * @param orderId
	 * @return
	 */
	@Override
	public List<OrderHistorySixAccount> selectOrderAccountByOrderId(Integer orderId) {
		StringBuilder sql = new StringBuilder("select a.orderId,b.appId,b.openid,c.mchId,c.partnerKey from e_order as a ")
				.append(" inner join e_client_wxuser_mapping as b on a.clientId = b.clientId ")
				.append(" inner join e_enterprise_wechatliteapp as c on b.appId = c.appId and a.eid=c.eid ")
				.append(" where a.orderId=?");
		return jdbcTemplate.query(sql.toString(),new Object[]{orderId},new BeanPropertyRowMapper<>(OrderHistorySixAccount.class));
	}

	/**
	 * 根据订单id查询企业用户appid openid
	 * 根据开会说明， 企业支付到零钱，只查公众号支付信息，如果没有公众号信息不给他退
	 * 但是仔细想了一下，谁也不能保证以后只做小程序不做公众号的这种情况，到时候又来问为什么明明有支付信息却不给退的情况，说不清楚，所以只要有支付信息就给他退
	 * 2020-06-30 彭安说 退押订单在退押的时候是新生成的，不会在结存表里面
	 * @param orderId
	 * @return
	 */
	@Override
	public List<OrderHistorySixAccount> selectOrderAccountByClientId(Integer clientId,Integer eid) {
		StringBuilder sql = new StringBuilder("select b.appId,b.openid,c.mchId,c.partnerKey from e_client_wxuser_mapping as b ")
				.append(" inner join e_enterprise_wechatliteapp as c on b.appId = c.appId and c.eid=? ")
				.append(" where b.clientId=?");
		return jdbcTemplate.query(sql.toString(),new Object[]{eid,clientId},new BeanPropertyRowMapper<>(OrderHistorySixAccount.class));
	}

	@Override
	public void Proc_Backstage_order_tuihuo_audit_TZ(ProcBackstageOrderTuihuoAuditTZReqDTO dto) {
		baseDao.agencyDB("Proc_Backstage_order_tuihuo_audit_TZ",dto);
	}

	@Override
	public Map<String, Object> Proc_Backstage_order_tuihuo_audit_SH(ProcBackstageOrderTuihuoAuditReqDTO dto) {
		return baseDao.agencyDB("Proc_Backstage_order_tuihuo_audit_SH",dto);
	}
}
