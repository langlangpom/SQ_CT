package com.evian.sqct.dao;

import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.ProductModel;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantIsEnableReqDTO;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantSelectReqDTO;
import com.evian.sqct.bean.product.input.ProcXHXProductSaleStatisticsReqDTO;

import java.util.List;
import java.util.Map;

public interface IProductDao {

	/**
	 * 商品分类查询
	 * @param eid
	 * @param cid
	 * @param enabled
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<ProductClass> selectProductClass(Integer eid,Integer cid,Boolean enabled,Integer pageIndex,Integer pageSize);
	
	/**
	 * 根据企业和分类查询商品
	 * @param product
	 * @return
	 */
	public List<ProductModel> selectProductByClass(Product product);
	
	/**
	 * 添加或者删除水店商品
	 * @param shopId 水店ID
	 * @param pid 商品ID
	 * @param recommend 是否热销
	 * @param tag 添加或删除 ADD DEL
	 * @return
	 */
	public Integer insertOrDelShopProduct(Integer shopId,Integer pid,Boolean recommend,String tag);
	
	/**
	 * 返回简单参数
	 * @param product
	 * @return
	 */
	Map<String,Object> selectProductEid(Product product);
	
	List<Map<String, Object>> e_product_mapping_class(Integer cid,Integer pid);
	
	/**
	 * 将商品添加到该分类
	 * @param shopId
	 * @param pid
	 * @param shopPrice
	 * @return
	 */
	Integer insertProductClass(Integer cid,Integer pid);

	/**
	 * 商品库存查询
	 * @param shopId
	 * @param pids 12,55,88
	 * @return
	 */
	Map<Integer, Map<String, Object>> getBatchProductStock(final int shopId, final String pids);

	String Proc_Backstage_product_stock_num_operat(Integer tsId,Integer eid,Integer changeStockNum,String createUser);

	Map<String,Object> Proc_Backstage_product_specs_relevant_select(ProcBackstageProductSpecsRelevantSelectReqDTO dto);

	String Proc_Backstage_product_specs_relevant_isEnable(ProcBackstageProductSpecsRelevantIsEnableReqDTO dto);

	String Proc_Backstage_product_specs_relevant_operat(Integer pid);

	Map<String, Object> Proc_XHX_product_sale_statistics(ProcXHXProductSaleStatisticsReqDTO dto);
}
