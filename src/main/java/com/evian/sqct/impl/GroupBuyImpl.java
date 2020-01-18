package com.evian.sqct.impl;

import com.evian.sqct.dao.IGroupBuyDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2019年8月9日 上午11:10:09
 * @author XHX
 * @Description 拼团Dao
 */
@Repository("groupBuyDao")
public class GroupBuyImpl implements IGroupBuyDao {
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询拼团订单
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_groupbuy_order_select(final String beginTime,final String endTime,final Integer eid,final String orderNo,
			final String nickName,final Boolean isCommander,final Boolean isRefund,
			final String account,final Integer groupBuyState,final String paymentNo,final String orderGroup,
			final String eName,final String pname,final String pcode,final String sdkType,final Integer shopId,final Boolean isFilterEndOrder,
			final Integer PageIndex,final Integer PageSize,final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
			"{call Proc_Backstage_groupbuy_order_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
			new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs)
						throws SQLException {
					cs.setObject("beginTime", beginTime);
					cs.setObject("endTime", endTime);
					cs.setObject("eid", eid);
					cs.setObject("orderNo", orderNo);
					cs.setObject("nickName", nickName);
					cs.setObject("isCommander", isCommander);
					cs.setObject("isRefund", isRefund);
					cs.setObject("account", account);
					cs.setObject("groupBuyState", groupBuyState);
					cs.setObject("paymentNo", paymentNo);
					cs.setObject("orderGroup", orderGroup);
					cs.setObject("eName", eName);
					cs.setObject("pname", pname);
					cs.setObject("pcode", pcode);
					cs.setObject("sdkType", sdkType);
					cs.setObject("shopId", shopId);
					cs.setObject("isFilterEndOrder", isFilterEndOrder);
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
						map.put("groupBuyOrder", result);
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

	/**
	 * 拼团活动商品
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_groupbuy_product_select(final String beginTime, final String endTime, final Integer eid,
			final Integer cityId, final Integer groupBuyType, final Boolean isEnabled, final String eName, final String shopName, final Integer shopId,
			final String pname, final String pcode, final Integer cid,final String groupName, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_groupbuy_product_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("cityId", cityId);
						cs.setObject("groupBuyType", groupBuyType);
						cs.setObject("isEnabled", isEnabled);
						cs.setObject("eName", eName);
						cs.setObject("shopName", shopName);
						cs.setObject("shopId", shopId);
						cs.setObject("pname", pname);
						cs.setObject("pcode", pcode);
						cs.setObject("cid", cid);
						cs.setObject("groupName", groupName);
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
							map.put("groupBuyProducts", result);
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
	public List<Map<String, Object>> e_groupbuy_price_schemeByXaId(Integer xaId) {
		// TODO Auto-generated method stub
		String sql="select schemeId, buyNum, price, buyMoney, numTag from e_groupbuy_price_scheme where xaId =?";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, xaId);
		
		return queryForList;
	}

	@Override
	public List<Map<String, Object>> selectClientNotPayGroupBuyOrder(String identityCode, Integer eid, Integer xaId, Integer pid) {
		String sql = " select g.gboId from e_groupbuy_order as g inner join e_client as c on g.clientId=c.clientId where (g.groupBuyState=1 or g.groupBuyState=0) and c.identityCode=? and g.xaId=? and g.pid=? and g.eid=?  ";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, new Object[]{identityCode, xaId, pid, eid});
		return queryForList;
	}
}
