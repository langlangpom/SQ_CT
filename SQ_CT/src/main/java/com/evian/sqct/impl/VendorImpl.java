package com.evian.sqct.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.evian.sqct.bean.vendor.AdLedPrice;
import com.evian.sqct.bean.vendor.GisCity;
import com.evian.sqct.bean.vendor.GisCommunity;
import com.evian.sqct.bean.vendor.GisDistrict;
import com.evian.sqct.bean.vendor.GisProvince;
import com.evian.sqct.bean.vendor.VendorContainer;
import com.evian.sqct.bean.vendor.VendorContainerTemplate;
import com.evian.sqct.bean.vendor.VendorDoor;
import com.evian.sqct.bean.vendor.VendorMainboard2;
import com.evian.sqct.bean.vendor.VendorMainboardContainer;
import com.evian.sqct.bean.vendor.VendorMainboardInstruct;
import com.evian.sqct.bean.vendor.VendorProductReplenishmentClass;
import com.evian.sqct.bean.vendor.VendorShopContainer;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.evian.sqct.util.SQLOper;

@Repository("vendorDao")
public class VendorImpl implements IVendorDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("vendorTemplate")
	private JdbcTemplate vendorDataSource;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String verndorContainerOperat(final Integer vmId,final  Integer bmId,
			final Integer doorNumber,final Integer vmIndex, final String createdUser,final String TAG) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_container_operat(?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("vmId", vmId);
						cs.setObject("bmId", bmId);
						cs.setObject("doorNumber", doorNumber);
						cs.setObject("vmIndex", vmIndex);
						cs.setObject("createdUser", createdUser);
						cs.setObject("TAG", TAG);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<VendorContainer> selectContainerByBmId(final Integer bmId) {
		List<VendorContainer> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_container_select(?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("bmId", bmId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
							try {
								List<VendorContainer> result = ResultSetToBeanHelper.resultSetToList(rs, VendorContainer.class);
								return result;
							} catch (Exception e) {
								// TODO Auto-generated catch block
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
	public List<VendorDoor> selectDoorAndProuct(final Integer mainboardId,final Integer eid,final Integer doorIndex,final Integer classId,final String beginTime,final String endTime,final String account,final String containerCode,final Integer doorReplenishmentDays,final Integer doorStatus,final Integer PageIndex,final Integer PageSize,final Boolean IsSelectAll) {
		List<VendorDoor> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_door_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("eid", eid);
						cs.setObject("doorIndex", doorIndex);
						cs.setObject("classId", classId);
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("account", account);
						cs.setObject("containerCode", containerCode);
						cs.setObject("doorReplenishmentDays", doorReplenishmentDays);
						cs.setObject("doorStatus", doorStatus);
						cs.setObject("operatType", null);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
							try {
								List<VendorDoor> result = ResultSetToBeanHelper.resultSetToList(rs, VendorDoor.class);
								return result;
							} catch (Exception e) {
								// TODO Auto-generated catch block
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
	public Map<String, Object> selectDoorAndProuct_v2(final Integer mainboardId,final Integer eid,final Integer replenishmentClassId,final Integer PageIndex,final Integer PageSize) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_product_select(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("id", null);
						cs.setObject("beginTime", null);
						cs.setObject("endTime", null);
						cs.setObject("productName", null); 
						cs.setObject("productCode", null);
						cs.setObject("eid", eid);
						cs.setObject("isLine", null);
						cs.setObject("classId", null);
						cs.setObject("replenishmentClassId", replenishmentClassId);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(PageIndex==null){
								cs.setObject("IsSelectAll", null);
						}else{
							if(PageSize==null){
								cs.setObject("PageSize", 10); // 默认10行
							}
							cs.setObject("IsSelectAll", false);
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("count", cs.getObject("Count"));
						ResultSet rs = cs.executeQuery();
							try {
								System.out.println("sdafasdf = "+map);
								List<Map<String, Object>> result = new ResultSetToBeanHelper().extractData(rs);
								List<Map<String, Object>> lineFalse = new ArrayList<Map<String, Object>>();
								// 去掉下架的商品
								for (Map<String, Object> map2 : result) {
									Boolean isLine = (Boolean) map2.get("isLine");
									if(!isLine) {
										lineFalse.add(map2);
									}
								}
								
								for (Map<String, Object> map2 : lineFalse) {
									result.remove(map2);
								}
								map.put("vendorDoorProucts", result);
								return map;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								return null;
							}finally{
								if(rs!=null){
									rs.close();
								}
							}
					}
				});
		
		if(mainboardId!=null) {
			List<Map<String, Object>> eDrawWinrecord = eDrawWinrecord(mainboardId);
			result.put("prizes", eDrawWinrecord);
		}else {
			result.put("prizes", new Object[] {});
		}
		
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> mainboardBindContainer(final String mainboardNo,
			final String containerCode,final Integer eid) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_MainboardBindContainer(?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("mainboardNoMD5", mainboardNo);
						cs.setObject("containerCode", containerCode);
						cs.setObject("eid", eid);
						cs.registerOutParameter("doorIndex", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("bmId", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("tag", cs.getString("tag"));
						map.put("doorIndex", cs.getString("doorIndex"));
						map.put("bmId", cs.getInt("bmId"));
						return map;
					}
				});
		return result;
	}

	@Override
	public VendorMainboardInstruct selectmainboardInstructByBmIdByDoorIndex(Integer bmId, Integer doorIndex) {
		String sql = "select * from (select ROW_NUMBER() OVER(ORDER BY v.dateCreated DESC) AS rownum,v.instructId, v.mainboardId, v.mainboardNo, v.doorIndex, v.portNo, v.instructType, v.instructSuc, v.dateCreated, v.lockDate, v.orderId from vendor_mainboard_instruct v where v.bmId=? and v.doorIndex=?) a where a.rownum=1";
		
		try {
			VendorMainboardInstruct query = vendorDataSource.queryForObject(sql,new Object[]{bmId,doorIndex}, new BeanPropertyRowMapper<>(VendorMainboardInstruct.class));
			return query;
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String verificationVendorDoor(final Integer mainboardId,final Integer doorIndex,final Integer portNo,
			final String alias,final Integer doorStatus,final String breakDownRemark) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_DoorValid(?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("doorIndex", doorIndex);
						cs.setObject("portNo", portNo);
						cs.setObject("alias", alias);
						cs.setObject("doorStatus", doorStatus);
						cs.setObject("breakDownRemark", breakDownRemark);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<VendorShopContainer> VendorManage(final Integer shopId) {
		List<VendorShopContainer> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_ShopContainer_select(?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("shopId", shopId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
							try {
								List<VendorShopContainer> result = ResultSetToBeanHelper.resultSetToList(rs, VendorShopContainer.class);
								return result;
							} catch (Exception e) {
								// TODO Auto-generated catch block
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
	public List<Map<String,Object>> AppCustomerMainboardSelect(final Integer eid,
			final Integer shopId,final Integer containerStatus,final Integer accountId,final String mainboardNoMD5) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_MainboardContainer_select(?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("shopId", shopId);
						cs.setObject("containerStatus", containerStatus);
						cs.setObject("accountId", accountId);
						cs.setObject("mainboardNoMD5", mainboardNoMD5);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public String doorReplenishment(final Integer eid,final Integer mainboardId,
			final Integer doorIndex,final Integer productId,final String account,final Integer operatType,final Integer wxId) {
		String result = null;
		if(account==null) {
			result = vendorDataSource.execute(
					"{call Proc_Backstage_vendor_AppCustomer_DoorReplenishment(?,?,?,?,?,?,?)}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException {
							cs.setObject("eid", eid);
							cs.setObject("mainboardId", mainboardId);
							cs.setObject("doorIndex", doorIndex);
							cs.setObject("productId", productId);
							cs.setObject("operatType", operatType);
							cs.setObject("wxid", wxId);
							cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
							cs.execute();
							
							return cs.getString("tag");
						}
					});
		}else {
			result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_DoorReplenishment(?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("doorIndex", doorIndex);
						cs.setObject("productId", productId);
						cs.setObject("operatType", operatType);
						cs.setObject("account", account);
						cs.setObject("wxid", wxId);
						cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						
						return cs.getString("tag");
					}
				});
		}
		
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> vendorProductSelect(final Integer id,
			final String beginTime, final String endTime,final String productName,final Integer eid,
			final Boolean isLine,final Integer PageIndex,final Integer PageSize,
			final Boolean IsSelectAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_mainboard_select(?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("id", id);
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("productName", productName);
						cs.setObject("eid", eid);
						cs.setObject("isLine", isLine);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public List<Map<String, Object>> vendorDoorProductStatistics(final Integer staffId,final Integer productState,final Boolean isAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_VendorDoorProduct_statistics(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("accountId", staffId);
						cs.setObject("productState", productState);
						cs.setObject("isAll", isAll);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public String vendorAppCustomerConfirmReceipt(final Integer eid,final String guidStr) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_confirm_receipt(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("guidStr", guidStr);
//						cs.setObject("userName", userName);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						String tag = cs.getString("TAG");
						return tag;
					}
				});
		return result;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String,Object>> vendorAppCustomerDoorProduct_statistics(final Integer eid,
			final Integer accountId, final Boolean isAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_DoorProduct_statistics(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("accountId", accountId);
						cs.setObject("isAll", isAll);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public List<Map<String,Object>> vendorAppCustomerDoorstatistics(final Integer eid,
			final Integer accountId) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_Door_statistics(?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("accountId", accountId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerstatistics(final Integer eid,
			final Integer accountId) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_MainboardContainer_statistics(?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("accountId", accountId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public String vendorAppCustomerMainboardContainerstatus(final Integer mainboardId,final Integer accountId,final Integer containerStatus, final String remark) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_MainboardContainer_status(?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("accountId", accountId);
						cs.setObject("containerStatus", containerStatus);
						cs.setObject("remark", remark);
//						cs.setObject("userName", userName);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						String tag = cs.getString("TAG");
						return tag;
					}
				});
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String vendorAppCustomerMainboardContainerAddressEdit(final Integer mainboardId,final Integer accountId,final String shopContainerName, final String location, final String containerAddress,final Integer communityId) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_MainboardContainer_address_edit(?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("accountId", accountId);
						cs.setObject("shopContainerName", shopContainerName);
						cs.setObject("location", location);
						cs.setObject("containerAddress", containerAddress);
						cs.setObject("communityId", communityId);
//						cs.setObject("userName", userName);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						String tag = cs.getString("TAG");
						return tag;
					}
				});
		return result;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerDoorStatistics(final Integer eid,
			final Integer accountId) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_MainboardContainerDoor_statistics(?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("accountId", accountId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public Map<String, Object> vendorSelectOrder(final String beginTime,
			final String endTime,final String orderNo, final Integer eid, final String mainboardNo,
			final Integer orderStatus, final Integer dataSourse, final Boolean isPay,
			final String productName, final String shopName,final String nickName,final Integer doorIndex,final Boolean isTest,final String openId, final Integer PageIndex,
			final Integer PageSize, final Boolean IsSelectAll) {
		Map<String,Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_order_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eName", null);
						cs.setObject("orderNo", orderNo);
						cs.setObject("eid", eid);
						cs.setObject("mainboardNo", mainboardNo);
						cs.setObject("orderStatus", orderStatus);
						cs.setObject("dataSourse", dataSourse);
						cs.setObject("isPay", isPay);
						cs.setObject("productName", productName);
						cs.setObject("shopName", shopName);
						cs.setObject("nickName", null);
						cs.setObject("doorIndex", doorIndex);
						cs.setObject("isTest", isTest);
						cs.setObject("openId", openId);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("sellPrice", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("discountsPrice", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("codeDiscountMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("randomDiscountMoney", Types.NUMERIC);// 注册输出参数的类型
//						cs.execute();
						Map<String, Object> map = new HashMap<String, Object>();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
//							System.out.println("count = "+cs.getObject("Count"));
							map.put("Count", cs.getInt("Count"));
							map.put("orderMoney", cs.getDouble("orderMoney"));
							map.put("sellPrice", cs.getDouble("sellPrice"));
							map.put("discountsPrice", cs.getDouble("discountsPrice"));
							map.put("codeDiscountMoney", cs.getDouble("codeDiscountMoney"));
							map.put("randomDiscountMoney", cs.getDouble("randomDiscountMoney"));
							map.put("vendorOrders", result);
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
	public Map<String, Object> vendorBatchDoorOpenEmpty(
			final Integer mainboardId, final Integer eid) {
			Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_Vendor_BatchDoor_OpenEmpty(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("eid", eid);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						ResultSet rs=cs.executeQuery();
				
						Map<String, Object> resultMap =new HashMap<String, Object>();
						List<Map<String, Object>> result=null;
						try {
							result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						} catch (Exception e) {
							
						}
						
						resultMap.put("dataMap", result);
						resultMap.put("tag", cs.getString("TAG"));
						return resultMap;
					}
				});
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#getNowDbName()
	 */
	@Override
	public String getNowDbName(){
	  String sql = "Select Name From Master..SysDataBases Where DbId=(Select Dbid From Master..SysProcesses Where Spid = @@spid)";
	  return vendorDataSource.queryForObject(sql, String.class);
	 }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String vendorGisCommunityOperat( final String tag,final Integer communityId,final Integer districtId, final String location,final String communityName, final Integer eid,
			final Integer accountId, final String account, final String remark) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_gis_community_operat(?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("communityId", communityId);
						cs.setObject("districtId", districtId);
						cs.setObject("location", location);
						cs.setObject("communityName", communityName);
						cs.setObject("eid", eid);
						cs.setObject("accountId", accountId);
						cs.setObject("account", account);
						cs.setObject("remark", remark);
						cs.setObject("isAudit", false);
						cs.setObject("TAG", tag);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						String tag = cs.getString("TAG");
						return tag;
					}
				});
		return result;
	}

	@Override
	public List<GisProvince> findAllProvince() {
		String sql="select provinceId, provinceName, dateCreated from e_gis_province";
		try {
			List<GisProvince> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<GisProvince>(GisProvince.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public List<GisCity> findCityByProvince(Integer provinceId) {
		String sql="select  cityId, cityName, zipCode, baiduCode, location, zoom, provinceId, dateCreated, ifLine, dateLine, ifHot, dateUpdated, updateUser from e_gis_city where provinceId=?";
		try {
			List<GisCity> query = jdbcTemplate.query(sql,new Object[] {provinceId}, new BeanPropertyRowMapper<GisCity>(GisCity.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public List<GisDistrict> findDistrictByCity(Integer cityId) {
		String sql="select districtId, districtName, cityId, dateCreated, status, dateUpdated, updateUser from e_gis_district where cityId=?";
		try {
			List<GisDistrict> query = jdbcTemplate.query(sql,new Object[] {cityId}, new BeanPropertyRowMapper<GisDistrict>(GisDistrict.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/*@Override
	public List<GisCommunity> findCommunityBydistrict(Integer districtId) {
		String sql="select * from vendor_gis_community where districtId=?";
		try {
			List<GisCommunity> query = vendorDataSource.query(sql, new Object[] { districtId }, new BeanPropertyRowMapper<GisCommunity>(GisCommunity.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String,Object>> findCommunityBydistrict(final Integer eid,final Integer cityId,final Integer districtId, final Boolean isAudit, final Integer PageIndex,final Integer PageSize,final Boolean IsSelectAll) {
		List<Map<String,Object>> result = vendorDataSource
				.execute(
						"{call Proc_Backstage_vendor_gis_community_select(?,?,?,?,?,?,?,?)}",
						new CallableStatementCallback() {
							public Object doInCallableStatement(
									CallableStatement cs) throws SQLException {
								cs.setObject("eid", eid);
								cs.setObject("cityId", cityId);
								cs.setObject("districtId", districtId);
								cs.setObject("isAudit", isAudit);
								cs.setObject("PageIndex", PageIndex);
								cs.setObject("PageSize", PageSize);
								cs.setObject("IsSelectAll", IsSelectAll);
								cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
								cs.execute();
								ResultSet rs = cs.executeQuery();
								try {
									List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
									return result;
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
	public List<GisCommunity> findCommunityByCommunityId(String communityIds){
		String sql = "select a.communityId, a.districtId, a.communityName, location, a.eid, a.accountId, a.account, a.remark, a.createTime " + 
				"	, (select count(1) from vendor_mainboard_container where communityId=a.communityId) as vendorCount 	 " + 
				" from vendor_gis_community a  where communityId in ("+communityIds+")";
		List<GisCommunity> query = vendorDataSource.query(sql, new BeanPropertyRowMapper<>(GisCommunity.class));
		return query;
	}

	
	@Override
	public List<GisCity> findAllCity() {
		String sql="select  cityId, cityName, zipCode, baiduCode, location, zoom, provinceId, dateCreated, ifLine, dateLine, ifHot, dateUpdated, updateUser from e_gis_city";
		try {
			List<GisCity> query = jdbcTemplate.query(sql,new BeanPropertyRowMapper<GisCity>(GisCity.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	
	@Override
	public List<GisDistrict> findAllDistrict() {
		String sql="select districtId, districtName, cityId, dateCreated, status, dateUpdated, updateUser from e_gis_district";
		try {
			List<GisDistrict> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<GisDistrict>(GisDistrict.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public List<GisCommunity> findAllCommunity() {
		String sql="select communityId, districtId, communityName, location, eid, accountId, account, remark, createTime, isAudit from vendor_gis_community";
		try {
			List<GisCommunity> query = vendorDataSource.query(sql,  new BeanPropertyRowMapper<GisCommunity>(GisCommunity.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> vendorAdLedOrderSave(final Integer clientId,final String adContent,final String beginDate,final Integer dayQuantity,final Double orderMoney,final Double discountMoney,final String codeNo,final Double finalMoney,final String communityNameJson) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_ad_led_order_save(?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("clientId", clientId);
						cs.setObject("adContent", adContent);
						cs.setObject("beginDate", beginDate);
						cs.setObject("dayQuantity", dayQuantity);
						cs.setObject("orderMoney", orderMoney);
						cs.setObject("discountMoney", discountMoney);
						cs.setObject("finalMoney", finalMoney);
						cs.setObject("codeNo", codeNo);
						cs.setObject("communityNameJson", communityNameJson);
						cs.registerOutParameter("orderId", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						Map<String, Object> resultMap =new HashMap<String, Object>();
						resultMap.put("orderId", cs.getInt("orderId"));
						resultMap.put("tag", cs.getString("TAG"));
						return resultMap;
					}
				});
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#findAdLedPrices()
	 */
	@Override
	public List<AdLedPrice> findAdLedPrices() {
		String sql="select priceId, minCharQuantity, maxCharQuantity, price, creater, createTime from vendor_ad_led_price";
		try {
			List<AdLedPrice> query = vendorDataSource.query(sql, new BeanPropertyRowMapper<AdLedPrice>(AdLedPrice.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#findAdLedPricesBy(int)
	 */
	@Override
	public List<AdLedPrice> findAdLedPricesByCharQuantity(int charQuantity) {
		String sql="select priceId, minCharQuantity, maxCharQuantity, price, creater, createTime from vendor_ad_led_price where ?>=minCharQuantity and ?<=maxCharQuantity";
		try {
			List<AdLedPrice> query = vendorDataSource.query(sql,new Object[] {charQuantity,charQuantity}, new BeanPropertyRowMapper<AdLedPrice>(AdLedPrice.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> vendorAdLedOrderSelect(final String beginTime, final String endTime,final  Integer orderId,
			final Boolean isPay, final Integer orderStatus, final String orderNo, final String account, final Integer PageIndex, final Integer PageSize,
			final Boolean IsSelectAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_ad_led_order_select(?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("orderId", orderId);
						cs.setObject("isPay", isPay);
						cs.setObject("orderStatus", orderStatus);
						cs.setObject("orderNo", orderNo);
						cs.setObject("account", account);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	 * 自动售货机用户代金券查询
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> vendorCodeAppCustomerSelect(final Integer eid, final Integer clientId) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_code_AppCustomer_select(?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("clientId", clientId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	 * @see com.evian.sqct.dao.IVendorDao#selectMainboardBymainboardNoMD5(java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" }) 
	@Override
	public List<VendorMainboard2> selectMainboardBymainboardNo(final String mainboardNo) {
		List<VendorMainboard2> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_mainboard_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", null);
						cs.setObject("endTime", null);
						cs.setObject("eName", null);
						cs.setObject("shopName", null);
						cs.setObject("eid", null);
						cs.setObject("mainboardNo", mainboardNo);
						cs.setObject("mainboardNoMD5", null);
						cs.setObject("isBind", null);
						cs.setObject("containerStatus", null);
						cs.setObject("containerAddress", null);
						cs.setObject("containerId", null);
						cs.setObject("communityName", null);
						cs.setObject("shopContainerName", null);
						cs.setObject("isTest", null);
						cs.setObject("PageIndex", null);
						cs.setObject("PageSize", null);
						cs.setObject("IsSelectAll", null);
						cs.registerOutParameter("Count", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<VendorMainboard2> result = ResultSetToBeanHelper.resultSetToList(rs, VendorMainboard2.class);
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
	public List<Map<String, Object>> balanceProductidMainboardidAccountidCommunityidByhourSelect(final Integer searchType,
			final String beginTime, final String endTime, final Integer eid, final Integer productId, final Integer mainboardId, final Integer accountId,
			final Integer communityId) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_balance_productid_mainboardid_accountid_communityid_byhour_select(?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("searchType", searchType);
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("productId", productId);
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("accountId", accountId);
						cs.setObject("communityId", communityId);
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public List<VendorMainboardContainer> selectAllMainboardContainer() {
		String sql = "SELECT     b.mainboardNo,a.mainboardId, a.containerCode, a.containerId, a.eid, a.containerStatus, a.qrcodePath, a.shopId, a.shopContainerName, a.location, a.containerAddress, a.remark, a.createTime, a.creater, a.communityId FROM  vendor_mainboard_container a,vendor_mainboard b where a.mainboardId=b.mainboardId";
		
		List<VendorMainboardContainer> query = vendorDataSource.query(sql,new BeanPropertyRowMapper<>(VendorMainboardContainer.class));
		return query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> vendorErrorLogOperat(final Integer eid, final Integer mainboardId, final Integer dataSource,
			final String exceptionMsg, final String exceptionCode, final String openId) {
		List<Map<String, Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_error_log_operat(?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("mainboardId", mainboardId);
						cs.setObject("dataSource", dataSource);
						cs.setObject("exceptionMsg", exceptionMsg);
						cs.setObject("exceptionCode", exceptionCode);
						cs.setObject("openId", openId);
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String, Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
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

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#vendorErrorLogSelect(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Boolean, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> vendorErrorLogSelect(final String beginTime, final String endTime, final Integer eid,
			final Integer dataSource, final String containerCode, final Boolean isSend,final Integer accountId, final String nickName, final String eName, final Boolean isDispose,final Integer PageIndex,
			final Integer PageSize, final Boolean IsSelectAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_error_log_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("dataSource", dataSource);
						cs.setObject("containerCode", containerCode);
						cs.setObject("isSend", isSend);
						cs.setObject("accountId", accountId);
						cs.setObject("nickName", nickName);
						cs.setObject("eName", eName);
						cs.setObject("isDispose", isDispose);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.registerOutParameter("Count", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public Integer updateErrResult(Integer logId, Boolean isDispose, String disposeMsg) {
		String sql = "update vendor_error_log set isDispose=?,disposeMsg=? where logId=?";
		int update = vendorDataSource.update(sql, new Object[]{isDispose,disposeMsg,logId});
		return update;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#appMerchantAccountShopVendorSelect(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> appMerchantAccountShopVendorSelect(Integer accountId) {
		String sql = "select a.communityId, a.communityName from vendor_gis_community a inner join vendor_mainboard_container b on a.communityId = b.communityId inner join EVIAN_CDSP.dbo.e_appMerchant_account_shop_vendor c on b.mainboardId = c.mainboardId where c.accountId = ? group by a.communityId, a.communityName ";
		List<Map<String, Object>> query = vendorDataSource.query(sql, new Object[]{accountId},ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#viewVisitSelect(java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.Boolean)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> viewVisitSelect(final String beginTime, final String endTime,final String mainboardNoMD5,
			final Boolean isTest,final Integer communityId,final Integer accountId, final Integer eid,final String openId,final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_view_visit_select(?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("mainboardNoMD5", mainboardNoMD5);
						cs.setObject("isTest", isTest);
						cs.setObject("communityId", communityId);
						cs.setObject("accountId", accountId);
						cs.setObject("eid", eid);
						cs.setObject("openId", openId);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(IsSelectAll==null) {
							cs.setObject("IsSelectAll", false);
						}else {
							cs.setObject("IsSelectAll", IsSelectAll);
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	public Map<String, Object> replenishmentStatistics(final Integer eid, final String beginTime, final String endTime,
			final String account,final String productName,final String productCode) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_AppCustomer_replenishment_statistics(?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("account", account);
						
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("productStatistics", result);
							if(cs.getMoreResults()) {
								rs = cs.getResultSet();
								List<Map<String,Object>> result2 = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
								map.put("vendorStatistics", result2);
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
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> productSupplementSelect(final String beginTime,final  String endTime, final Integer eid,
			final String account, final String productCode, final String productName,final String shopName,final Boolean isTest, final Integer PageIndex, final Integer PageSize,
			final Boolean IsSelectAll) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_appMerchant_product_supplement_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("account", account);
						cs.setObject("productCode", productCode);
						cs.setObject("productName", productName);
						cs.setObject("shopName", shopName);
						cs.setObject("isTest", isTest);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(IsSelectAll==null) {
							cs.setObject("IsSelectAll", false);
						}else {
							cs.setObject("IsSelectAll", IsSelectAll);
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("supplementQuantity", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("realQuantity", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("Count", cs.getInt("Count"));
							map.put("supplementQuantity", cs.getInt("supplementQuantity"));
							map.put("realQuantity", cs.getInt("realQuantity"));
							map.put("productSupplementRecord", result);
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
	public String productSupplementConfirm(final Integer supplementId, final Integer eid, final Integer accountId, final Integer realQuantity,
			final String remark,final Integer productId) {
		String result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_appMerchant_product_supplement_confirm(?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("supplementId", supplementId);
						cs.setObject("eid", eid);
						cs.setObject("accountId", accountId);
						cs.setObject("realQuantity", realQuantity);
						cs.setObject("remark", remark);
						cs.setObject("productId", productId);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		
		return result;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> vendorErrorLogStatistics(final String beginTime, final String endTime, final Integer eid,
			final Integer dataSource, final String containerCode, final Boolean isSend,final Integer accountId,final Boolean isDispose,final Integer PageIndex,
			final Integer PageSize, final Boolean IsSelectAll) {
		List<Map<String,Object>> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_error_log_statistics(?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("dataSource", dataSource);
						cs.setObject("containerCode", containerCode);
						cs.setObject("isSend", isSend);
						cs.setObject("accountId", accountId);
						cs.setObject("isDispose", isDispose);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(IsSelectAll==null) {
							cs.setObject("IsSelectAll", false);
						}else {
							cs.setObject("IsSelectAll", IsSelectAll);
						}
						
						cs.registerOutParameter("Count", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
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
	 * @see com.evian.sqct.dao.IVendorDao#repetitionBuySelect(java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> repetitionBuySelect(final String beginTime, final String endTime, final Boolean isSearchTimeForAuth,
			final String eName, final Integer eid, final String mainboardNo, final Integer orderStatus, final Integer dataSourse, final Boolean isPay,
			final String productName, final String shopName, final Integer doorIndex, final Boolean isTest, final Integer buyTimesMoreThen,
			final String cellphone,final Integer sortType, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_order_select_groupByOpenId(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("isSearchTimeForAuth", isSearchTimeForAuth);
						cs.setObject("eName", eName);
						cs.setObject("eid", eid);
						cs.setObject("mainboardNo", mainboardNo);
						cs.setObject("orderStatus", orderStatus);
						cs.setObject("dataSourse", dataSourse);
						cs.setObject("isPay", isPay);
						cs.setObject("productName", productName);
						cs.setObject("shopName", shopName);
						cs.setObject("doorIndex", doorIndex);
						cs.setObject("isTest", isTest);
						cs.setObject("buyTimesMoreThen", buyTimesMoreThen);
						cs.setObject("cellphone", cellphone);
						cs.setObject("sortType", sortType);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(PageIndex!=null&&IsSelectAll==null) {
							cs.setObject("IsSelectAll", false);
						}else if(PageIndex==null&&IsSelectAll==null){
							cs.setObject("IsSelectAll", true);
						}else {
							cs.setObject("IsSelectAll", IsSelectAll);
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("buyTimes", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("sellPrice", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("discountsPrice", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("codeDiscountMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("randomDiscountMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("Count", cs.getInt("Count"));
							map.put("buyTimes", cs.getInt("buyTimes"));
							map.put("orderMoney", cs.getInt("orderMoney"));
							map.put("sellPrice", cs.getInt("sellPrice"));
							map.put("discountsPrice", cs.getInt("discountsPrice"));
							map.put("codeDiscountMoney", cs.getInt("codeDiscountMoney"));
							map.put("randomDiscountMoney", cs.getInt("randomDiscountMoney"));
							map.put("repetitionBuySelect", result);
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
	 * @see com.evian.sqct.dao.IVendorDao#vendorMainboardOrProductIdBuySelect(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> vendorMainboardOrProductIdBuySelect(final Integer statisticsType, final String beginTime,
			final String endTime, final String eName, final Integer eid, final String mainboardNo, final Integer orderStatus, final Integer dataSourse,
			final Boolean isPay, final String productName, final String shopName, final Integer doorIndex, final Boolean isTest,
			final Integer buyTimesMoreThen, final String cellphone, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_order_select_groupByMainboardIdOrProductId(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("statisticsType", statisticsType);
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eName", eName);
						cs.setObject("eid", eid);
						cs.setObject("mainboardNo", mainboardNo);
						cs.setObject("orderStatus", orderStatus);
						cs.setObject("dataSourse", dataSourse);
						cs.setObject("isPay", isPay);
						cs.setObject("productName", productName);
						cs.setObject("shopName", shopName);
						cs.setObject("doorIndex", doorIndex);
						cs.setObject("isTest", isTest);
						cs.setObject("buyTimesMoreThen", buyTimesMoreThen);
						cs.setObject("cellphone", cellphone);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(PageIndex!=null&&IsSelectAll==null) {
							cs.setObject("IsSelectAll", false);
						}else if(PageIndex==null&&IsSelectAll==null){
							cs.setObject("IsSelectAll", true);
						}else {
							cs.setObject("IsSelectAll", IsSelectAll);
						}
						cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("buyTimes", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("orderMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("sellPrice", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("discountsPrice", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("codeDiscountMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.registerOutParameter("randomDiscountMoney", Types.NUMERIC);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("Count", cs.getInt("Count"));
							map.put("buyTimes", cs.getInt("buyTimes"));
							map.put("orderMoney", cs.getInt("orderMoney"));
							map.put("sellPrice", cs.getInt("sellPrice"));
							map.put("discountsPrice", cs.getInt("discountsPrice"));
							map.put("codeDiscountMoney", cs.getInt("codeDiscountMoney"));
							map.put("randomDiscountMoney", cs.getInt("randomDiscountMoney"));
							map.put("vendorMainboardOrProductIdBuySelect", result);
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
	public List<Map<String, Object>> vendorReplenishmentPlanSelect(Integer eid,Integer shopId, Integer containerId,Integer accountId, Integer PageIndex, Integer PageSize, Boolean IsSelectAll) {
		if(accountId!=null) {
		
			StringBuilder sql = new StringBuilder("select   ROW_NUMBER() OVER(Order by a.createTime desc) AS row, a.planId, a.containerId, a.shopId, a.planName, a.doorNum, a.isShopAll, a.isDel, a.createTime, a.creater ,e.mainboardId, e.containerCode as mainboardNo, e.shopContainerName, e.containerAddress, e.location ");
						  sql.append(" from vendor_replenishment_plan a ");
						  
						  sql.append(" inner join vendor_replenishment_plan_mapping  c on c.planId=a.planId ");
						  sql.append(" inner join EVIAN_CDSP.dbo.e_appMerchant_account_shop_vendor  b on c.mainboardId=b.mainboardId ");
						  sql.append(" inner join vendor_mainboard_container  e on e.mainboardId=c.mainboardId   ");
							
						  sql.append(" where  1=1  and a.isDel=0 and b.accountId =? ");
			List<Object> args = new ArrayList<Object>();
			
			
			if(containerId!=null) {
				sql.append(" and a.containerId=? ");
				args.add(containerId);
			}
			
			if(shopId!=null) {
				sql.append(" and a.shopId=? ");
				args.add(shopId);
			}
			
			if(eid!=null) {
				sql.append(" and a.eid=? ");
				args.add(eid);
			}
			
			
			int o = args.size()+1;
			
			Object[] a = new Object[o];
			
			a[0] = accountId;
			
			for (int i = 0; i < args.size(); i++) {
				a[i+1]=args.get(i);
			}
			
			// 加上分页
			SQLOper.addPaging(sql, a, PageIndex, PageSize, IsSelectAll);
			logger.info("[sql:{}] [a:{}]",new Object[] {sql.toString(),a[0]});
			List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),a, ResultSetToBeanHelper.resultSetToListMap());
			return query;
		}else {
			StringBuilder sql = new StringBuilder("select   ROW_NUMBER() OVER(Order by a.createTime desc) AS row, a.planId, a.containerId, a.shopId, a.planName, a.doorNum, a.isShopAll, a.isDel, a.createTime, a.creater ,e.mainboardId, e.containerCode as mainboardNo, e.shopContainerName, e.containerAddress, e.location ");
			  sql.append(" from vendor_replenishment_plan a ");
			  sql.append(" left join vendor_replenishment_plan_mapping b on b.planId=a.planId ");
			  sql.append(" left join vendor_mainboard_container  e on b.mainboardId=e.mainboardId   ");
			  sql.append(" where 1=1 and a.isDel=0 ");
			  
			  
			  List<Object> args = new ArrayList<Object>();
				
				
				
				if(containerId!=null) {
					sql.append(" and a.containerId=? ");
					args.add(containerId);
				}
				
				if(shopId!=null) {
					sql.append(" and a.shopId=? ");
					args.add(shopId);
				}
				
				if(eid!=null) {
					sql.append(" and a.eid=? ");
					args.add(eid);
				}
				
				int o = args.size();
				
				Object[] a = new Object[o];
				
				
				for (int i = 0; i < args.size(); i++) {
					a[i]=args.get(i);
				}
			
			// 加上分页
			SQLOper.addPaging(sql, a, PageIndex, PageSize, IsSelectAll);
			logger.info("[sql:{}] [a:{}]",new Object[] {sql.toString(),a[0]});
			List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),a, ResultSetToBeanHelper.resultSetToListMap());
			return query;
		}
	}

	@Override
	public List<Map<String, Object>> vendorReplenishmentPlanDetailsSelect(Integer planId,Integer mainboardId) {
		
		StringBuilder sql = new StringBuilder("select  a.itemId, a.planId, a.sortId, a.productId, a.planNum, a.createTime,b.picture,b.price,b.productName,b.eid,b.productCode ");
					  sql.append(" from vendor_replenishment_plan_item a ");
					  sql.append(" inner join vendor_product b on a.productId=b.id ");
					  Object[] args = null;
					  if(planId!=null) {
						  sql.append(" where a.planId=? ");
						  args = new Object[] {planId};
					  }else {
						  sql.append(" inner join vendor_replenishment_plan_mapping c on a.planId=c.mainboardId ");
						  sql.append(" where c.mainboardId=? ");
						  args = new Object[] {mainboardId};
					  }
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),args, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}
	
	
	@Override
	public Integer insertVendorReplenishmentPlan(final Integer eid,final Integer containerId,final Integer shopId,final String planName,final Integer doorNum,final Boolean isShopAll,final String creater) {
		final String sql="insert into vendor_replenishment_plan( eid, containerId, shopId, planName, doorNum, isShopAll, creater) values(?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		vendorDataSource.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, eid);
                ps.setObject(2, containerId);
                ps.setObject(3, shopId);
                ps.setObject(4, planName);
                ps.setObject(5, doorNum);
                ps.setObject(6,isShopAll);
                ps.setObject(7,creater);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
	}

	
	@Override
	public Integer insertvendorReplenishmentPlanItem(Integer planId, Integer sortId, Integer productId,
			Integer planNum) {
		String sql = "insert into vendor_replenishment_plan_item( planId, sortId, productId, planNum) values(?,?,?,?) ";
		
		return vendorDataSource.update(sql,new Object[] {planId,sortId,productId,planNum});
	}

	
	@Override
	public Integer removeVendorReplenishmentPlanItem(Integer planId) {
		String sql = "delete from  vendor_replenishment_plan_item where planId=? ";
		return vendorDataSource.update(sql,new Object[] {planId});
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#selectVendorContainerByMainboardId(java.lang.Integer)
	 */
	@Override
	public VendorContainer selectVendorContainerByMainboardId(Integer mainboardId) {
		String sql = "select  a.containerId, a.containerName, a.doorNum, a.remark, a.createTime, a.creater, a.storageQuantity, a.containerPic, a.qrcodeDoorIndex, b.eid ,b.shopId from vendor_container_template a inner join vendor_mainboard_container b on a.containerId=b.containerId where b.mainboardId=?";
		VendorContainer queryForObject = null;
		try {
			queryForObject = vendorDataSource.queryForObject(sql,new Object[] {mainboardId}, new BeanPropertyRowMapper<>(VendorContainer.class));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return queryForObject;
	}

	@Override
	public Integer insertVendorReplenishmentPlanMapping(Integer mainboardId, Integer planId,String creater) {
		String sql = "insert into vendor_replenishment_plan_mapping(mainboardId,planId,creater) values(?,?,?)";
		return vendorDataSource.update(sql,new Object[] {mainboardId,planId,creater});
	}

	@Override
	public Integer updateVendorReplenishmentPlanMapping(Integer mainboardId, Integer planId) {
		String sql = "update  vendor_replenishment_plan_mapping set planId=? where mainboardId=? ";
		return vendorDataSource.update(sql,new Object[] {planId,mainboardId});
	}

	@Override
	public Integer removeVendorReplenishmentPlanMapping(Integer planId) {
		String sql = "delete from  vendor_replenishment_plan_mapping where planId=?  ";
		return vendorDataSource.update(sql,new Object[] {planId});
	}

	@Override
	public Integer removeUpdateVendorReplenishmentPlan(Integer planId) {
		String sql="update vendor_replenishment_plan set isDel=1 where planId=? and shopId!=0 ";
		
		return vendorDataSource.update(sql,new Object[] {planId});
	}

	@Override
	public Integer updateVendorReplenishmentPlan(Integer planId, String planName,
			Boolean isShopAll) {
		StringBuilder sql = new StringBuilder("update  vendor_replenishment_plan set ");
		
		if(planName==null&&isShopAll==null) {
			return 0;
		}
		Object[] args = null;
		if(planName!=null&&isShopAll==null) {
			sql.append(" planName=? ");
			args = new Object[] {planName,planId};
		}else if(planName!=null&&isShopAll!=null){
			sql.append(" planName=?,isShopAll=? ");
			args = new Object[] {planName,isShopAll,planId};
		}
		
		sql.append(" where planId=? ");
		
		return vendorDataSource.update(sql.toString(),args);
	}

	@Override
	public List<Map<String, Object>> vendorReplenishmentPlanMappingSelect(Integer mainboardId, Integer planId) {
		StringBuilder sql = new StringBuilder("select  mainboardId, planId, createTime, creater from vendor_replenishment_plan_mapping where ");
		Object[] args = null;
		if(mainboardId!=null&&planId!=null) {
			sql.append("mainboardId=? and planId=?");
			args = new Object[] {mainboardId,planId};
		}else if(planId!=null){
			sql.append(" planId=?");
			args = new Object[] {planId};
		}else if(mainboardId!=null) {
			sql.append(" mainboardId=? ");
			args = new Object[] {mainboardId};
		}
		
		
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),args, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	@Override
	public List<Map<String, Object>> vendorReplenishmentPlanSelectByPlanId(Integer planId) {
		String sql = "select   a.planId, a.containerId, a.shopId, a.planName, a.doorNum, a.isShopAll, a.isDel, a.createTime, a.creater from vendor_replenishment_plan a where a.planId=?";
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),new Object[] {planId}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#vendorReplenishmentPlanMappingSelectNoShopId(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> vendorReplenishmentPlanMappingSelectNoShopId(Integer mainboardId) {
		String sql = "select  a.mainboardId, a.planId, a.createTime, a.creater from vendor_replenishment_plan_mapping a inner join vendor_replenishment_plan b on a.planId=b.planId where a.mainboardId=? and b.shopId!=0";
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),new Object[] {mainboardId}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	@Override
	public List<Map<String, Object>> selectVendorSellProductKindNum(Integer mainboardId) {
		StringBuilder sql = new StringBuilder(" select c.productId,d.productName,d.picture,d.price,c.num  from ");
					  sql.append(" (select a.id as productId,COUNT(*) as num from vendor_product a ");
					  sql.append(" inner join vendor_door b on a.id=b.productId ");
					  sql.append(" where b.doorStatus=2 and b.mainboardId=? group by a.id) as c  ");
					  sql.append(" inner join vendor_product d on c.productId=d.id  ");
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),new Object[] {mainboardId}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	@Override
	public List<Map<String, Object>> selectVendorContainerByAccountId(Integer accountId) {
		String sql = "select  b.mainboardId, b.containerCode, b.containerId, b.eid, b.containerStatus, b.qrcodePath, b.shopId, b.shopContainerName, b.location, b.containerAddress from EVIAN_CDSP.dbo.e_appMerchant_account_shop_vendor a inner join vendor_mainboard_container b on a.mainboardId=b.mainboardId where a.accountId=? ";
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),new Object[] {accountId}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#selectVendorContainerTemplateByContainerId(java.lang.Integer)
	 */
	@Override
	public VendorContainerTemplate selectVendorContainerTemplateByContainerId(Integer containerId) {
		String sql = "select containerId, containerName, doorNum, remark, createTime, creater, storageQuantity, containerPic, qrcodeDoorIndex from vendor_container_template where containerId=?";
		try {
			VendorContainerTemplate vendorContainerTemplate = vendorDataSource.queryForObject(sql, new Object[] {containerId},  new BeanPropertyRowMapper<>(VendorContainerTemplate.class));
			
			return vendorContainerTemplate;
		} catch (DataAccessException e) {
			
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> vendorAppMerchantProductStorageDetailSelect(final String beginTime, final String endTime,
			final Integer eid, final String account, final String productCode,final  String productName,final  String shopName,final  Boolean isTest,final String remark,
			final Integer PageIndex,final  Integer PageSize,final  Boolean IsSelectAll) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_appMerchant_product_storage_detail_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("beginTime", beginTime);
						cs.setObject("endTime", endTime);
						cs.setObject("eid", eid);
						cs.setObject("account", account);
						cs.setObject("productCode", productCode);
						cs.setObject("productName", productName);
						cs.setObject("shopName", shopName);
						cs.setObject("remark", remark);
						cs.setObject("isTest", isTest);
						if(IsSelectAll!=null&&IsSelectAll) {
							cs.setObject("IsSelectAll", IsSelectAll);
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
						cs.registerOutParameter("storageInQuantity", Types.INTEGER);// 注册输出参数的类型
						cs.registerOutParameter("storageOutQuantity", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("Count", cs.getInt("Count"));
							map.put("storageInQuantity", cs.getInt("storageInQuantity"));
							map.put("storageOutQuantity", cs.getInt("storageOutQuantity"));
							map.put("storageDetail", result);
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
	public Map<String, Object> vendorDoorSelectGroupByProductId(final Integer eid, final Integer doorStatus, final String productName,
			final String productCode, final String shortName, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_door_select_GroupByProductId(?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("doorStatus", doorStatus);
						cs.setObject("productName", productName);
						cs.setObject("productCode", productCode);
						cs.setObject("shortName", shortName);
						if(IsSelectAll!=null&&IsSelectAll) {
							cs.setObject("IsSelectAll", IsSelectAll);
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
						cs.registerOutParameter("num", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("Count", cs.getInt("Count"));
							map.put("num", cs.getInt("num"));
							map.put("onSaleCommodityStatistics", result);
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
	public Map<String, Object> vendorDoorSelectGroupByProductIdAndMainboardId(final Integer eid, final Integer productId,
			final Integer doorStatus, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = vendorDataSource.execute(
				"{call Proc_Backstage_vendor_door_select_GroupByProductIdAndMainboardId(?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("eid", eid);
						cs.setObject("doorStatus", doorStatus);
						cs.setObject("productId", productId);
						cs.setObject("PageIndex", PageIndex);
						cs.setObject("PageSize", PageSize);
						if(IsSelectAll!=null&&IsSelectAll) {
							cs.setObject("IsSelectAll", IsSelectAll);
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
						cs.registerOutParameter("num", Types.INTEGER);// 注册输出参数的类型
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							map.put("Count", cs.getInt("Count"));
							map.put("num", cs.getInt("num"));
							map.put("onSaleCommodityStatistics", result);
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
	 * @see com.evian.sqct.dao.IVendorDao#eDrawWinrecord(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> eDrawWinrecord(Integer mainboardId) {
		String sql = "select wxId, cellPhone from e_draw_winrecord where mainboardId = ? and ifShenHe = 1 and inAccount = 0 and cellphone is not null";
		List<Map<String, Object>> query = jdbcTemplate.query(sql.toString(),new Object[] {mainboardId}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	
	@Override
	public List<VendorProductReplenishmentClass> selectVendorProductReplenishmentClass() {
		String sql="select  replenishmentClassId, eid, className, sortId, remark, createTime, creater from vendor_product_replenishment_class";
		try {
			List<VendorProductReplenishmentClass> query = vendorDataSource.query(sql, new BeanPropertyRowMapper<VendorProductReplenishmentClass>(VendorProductReplenishmentClass.class));
			return query;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IVendorDao#selectUnlockingByOrderId(java.lang.Integer)
	 */
	@Override
	public List<Map<String, Object>> selectUnlockingByOrderId(Integer orderId) {
		String sql = "select mainboardNo,doorIndex,czTag,CONVERT( nvarchar(19),dateCreated,120) AS dateCreated,instructSuc,ISNULL(CONVERT(nvarchar(19),lockDate,120),'') lockDate from vendor_mainboard_instruct where orderId=? and instructType=3 order by dateCreated";
		List<Map<String, Object>> query = vendorDataSource.query(sql.toString(),new Object[] {orderId}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

}
