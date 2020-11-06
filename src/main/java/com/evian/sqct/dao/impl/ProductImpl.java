package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.ProductDTO;
import com.evian.sqct.bean.product.ProductModel;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantIsEnableReqDTO;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantSelectReqDTO;
import com.evian.sqct.bean.product.input.ProcXHXProductSaleStatisticsReqDTO;
import com.evian.sqct.dao.IBaseDao;
import com.evian.sqct.dao.IProductDao;
import com.evian.sqct.util.DaoUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("productDao")
public class ProductImpl implements IProductDao{

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private IBaseDao baseDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ProductClass> selectProductClass(final Integer eid, final Integer cid,final Boolean enabled, final Integer pageIndex, final Integer pageSize) {
		List<ProductClass> result = (List<ProductClass>)jdbcTemplate.execute(
				"{call Proc_Backstage_product_class_select(?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("eid", eid);
					cs.setObject("cid", cid);
					cs.setObject("enabled", enabled);
					cs.setObject("PageIndex", pageIndex);
					cs.setObject("PageSize", pageSize);
					if(pageIndex!=null&&pageSize!=null){
						cs.setObject("IsSelectAll", false);
					}else{
						cs.setObject("IsSelectAll", null);
					}
					cs.registerOutParameter("Count", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					ResultSet rs = cs.executeQuery();

					try {
						return ResultSetToBeanHelper.resultSetToList(rs, ProductClass.class);
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
	public List<ProductModel> selectProductByClass(final Product product) {
		List<ProductModel> result = (List<ProductModel>)jdbcTemplate.execute(
				"{call Proc_Backstage_product_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback)  cs -> {
					cs.setObject("pid", product.getPid());
					cs.setObject("pname", product.getPname());
					cs.setObject("pcode", product.getPcode());
					cs.setObject("property", product.getProperty());
					cs.setObject("ifHot", product.getIfHot());
					cs.setObject("status", product.getStatus());
					cs.setObject("enabled", product.getEnabled());
					cs.setObject("eid", product.getEid());
					cs.setObject("cid", product.getCid());
					cs.setObject("shopId", product.getShopId());
					cs.setObject("createUser", product.getCreateUser());
					cs.setObject("eName", product.geteName());
					cs.setObject("isRelevance", product.getIsRelevance());
					cs.setObject("ifTicket", product.getIfTicket());
					cs.setObject("hashTicket", product.getHashTicket());
					cs.setObject("sortType", product.getSortTyoe());
					cs.setObject("evianPName", product.getEvianPName());
					cs.setObject("isSettingType1", product.getIsSettingTyop1());
					cs.setObject("isSettingType2", product.getIsSettingTyop2());
					cs.setObject("PageIndex", product.getPageIndex());
					cs.setObject("PageSize", product.getPageSize());
					cs.setObject("IsSelectAll", product.getIsSelectAll());
					cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
					cs.execute();
					ResultSet rs = cs.executeQuery();

					try {
						List<ProductModel> resul = ResultSetToBeanHelper.resultSetToList(rs, ProductModel.class);
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

	@Override
	public Integer insertOrDelShopProduct(Integer shopId, Integer pid,Boolean recommend, String tag) {
		// TODO Auto-generated method stub
		int update = 0;
		if("ADD".equals(tag)){
			String sql = "delete from e_shop_product where shopId=? and pid=?;insert into e_shop_product(shopId,pid,recommend) values(?,?,?)";
			Object[] args = {shopId,pid,shopId,pid,recommend};
			update = jdbcTemplate.update(sql, args);
		}else if("DEL".equals(tag)){
			String sql = "delete from e_shop_product where shopId=? and pid=?";
			Object[] args = {shopId,pid};
			update = jdbcTemplate.update(sql, args);
		}
		return update;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IProductDao#selectProductEid(com.evian.sqct.bean.product.Product)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,Object> selectProductEid(Product product) {
		return DaoUtil.resultRename(baseDao.agencyDB("Proc_Backstage_product_select", product, ProductDTO.class),"products");
	}

	@Override
	public List<Map<String, Object>> e_product_mapping_class(Integer cid, Integer pid) {
		String sql="select id from e_product_mapping_class where cid=? and pid=?";
		List<Map<String, Object>> query = jdbcTemplate.query(sql.toString(),new Object[] {cid,pid}, ResultSetToBeanHelper.resultSetToListMap());
		return query;
	}

	@Override
	public Integer insertProductClass(Integer cid, Integer pid) {
		String sql="insert into e_product_mapping_class(cid,pid) values(?,?)";
		
		return jdbcTemplate.update(sql,new Object[] {cid,pid});
	}

	@Override
	public Map<Integer, Map<String, Object>> getBatchProductStock(final int shopId, final String pids) {
		return jdbcTemplate.execute("{Call Proc_DisPark_Get_Product_Stock_Batch(?,?)}",
				(CallableStatementCallback<Map<Integer, Map<String, Object>>>) cs -> {
					cs.setInt("shopId", shopId);
					cs.setString("pids", pids);
					ResultSet rs = cs.executeQuery();

					Map<Integer, Map<String, Object>> productStocks = new HashMap<Integer, Map<String, Object>>();
					//01是否有库存管理
					int cityId = 0;
					int districtId = 0;
					boolean openStock = false;
					while (rs.next()) {
						cityId = rs.getInt("cityId");
						districtId = rs.getInt("districtId");
						openStock = rs.getBoolean("openStock");
						break;
					}
					rs.close();

					//02库存明细信息
					if (openStock) {
						List<Map<String, Object>> stocks = new ArrayList<Map<String, Object>>();
						if (cs.getMoreResults()) {
							ResultSet rs1 = cs.getResultSet();
							while (rs1.next()) {
								Map<String, Object> stockMap = new HashMap<String, Object>();
								stockMap.put("pid", rs1.getInt("pid"));
								stockMap.put("schemeId", rs1.getInt("schemeId"));
								stockMap.put("leftStockNum", rs1.getInt("leftStockNum"));
								stockMap.put("tsId", rs1.getInt("tsId"));
								stockMap.put("cityId", rs1.getInt("cityId"));
								stockMap.put("districtId", rs1.getInt("districtId"));
								stockMap.put("shopId", rs1.getInt("shopId"));
								stockMap.put("pname", rs1.getString("pname"));
								stocks.add(stockMap);
							}
							rs1.close();
						}

						//根据方案整理数据
						String[] pidArrays = pids.split(",");
						List<Integer> pidList = new ArrayList<Integer>();
						for (String p : pidArrays) {
							pidList.add(Integer.valueOf(p));
						}

						for (Integer p : pidList) {
							int schemeId = 0;
							for (Map<String, Object> v : stocks) {
								if (p.intValue() == (Integer) v.get("pid")) {
									schemeId = (Integer) v.get("schemeId");
									break;
								}
							}

							int csdId = 0;
							String schemeKey = "";
							switch (schemeId) {
								//按城市商品
								case 1:
									csdId = cityId;
									schemeKey = "cityId";
									break;
								//按区域
								case 2:
									csdId = districtId;
									schemeKey = "districtId";
									break;
								//按水店
								case 3:
									csdId = shopId;
									schemeKey = "shopId";
									break;
								default:
							}

							for (Map<String, Object> v : stocks) {
								if (p.intValue() == (Integer) v.get("pid") && schemeId == (Integer) v.get("schemeId") && csdId == (Integer) v.get(schemeKey)) {
									Map<String, Object> pMap = new HashMap<String, Object>();
									pMap.put("stockNum", v.get("leftStockNum"));
									pMap.put("pname", v.get("pname"));
									pMap.put("tsId", v.get("tsId"));
									productStocks.put(p.intValue(), pMap);
								}
							}
						}
					}
					return productStocks;
				});
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String Proc_Backstage_product_stock_num_operat(final Integer tsId, final Integer eid, final Integer changeStockNum, final String createUser) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_product_stock_num_operat(?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("tsId", tsId);
					cs.setObject("eid", eid);
					cs.setObject("changeStockNum", changeStockNum);
					cs.setObject("createUser", createUser);
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
				});
		return result;
	}

	@Override
	public Map<String, Object> Proc_Backstage_product_specs_relevant_select(ProcBackstageProductSpecsRelevantSelectReqDTO dto) {
		return DaoUtil.resultRename(baseDao.agencyDB("Proc_Backstage_product_specs_relevant_select",dto),"specs");
	}

	@Override
	public String Proc_Backstage_product_specs_relevant_isEnable(ProcBackstageProductSpecsRelevantIsEnableReqDTO dto) {
		return DaoUtil.resultTAG(baseDao.agencyDB("Proc_Backstage_product_specs_relevant_isEnable",dto));
	}

	@Override
	public String Proc_Backstage_product_specs_relevant_operat(Integer pid) {
		return null;
	}

	@Override
	public Map<String, Object> Proc_XHX_product_sale_statistics(ProcXHXProductSaleStatisticsReqDTO dto) {
		return DaoUtil.resultRename(baseDao.agencyDB("Proc_XHX_product_sale_statistics",dto),"total");
	}
}
