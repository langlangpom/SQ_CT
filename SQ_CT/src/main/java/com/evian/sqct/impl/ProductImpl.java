package com.evian.sqct.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.ProductModel;
import com.evian.sqct.dao.IProductDao;
import com.evian.sqct.util.ResultSetToBeanHelper;

@Repository("productDao")
public class ProductImpl implements IProductDao{

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ProductClass> selectProductClass(final Integer eid, final Integer cid,final Boolean enabled, final Integer pageIndex, final Integer pageSize) {
		List<ProductClass> result = jdbcTemplate.execute(
				"{call Proc_Backstage_product_class_select(?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
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
							List<ProductClass> result = ResultSetToBeanHelper.resultSetToList(rs, ProductClass.class);
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
	public List<ProductModel> selectProductByClass(final Product product) {
		List<ProductModel> result = jdbcTemplate.execute(
				"{call Proc_Backstage_product_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
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
							List<ProductModel> result = ResultSetToBeanHelper.resultSetToList(rs, ProductModel.class);
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


	
}
