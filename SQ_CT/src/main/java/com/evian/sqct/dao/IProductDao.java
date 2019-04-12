package com.evian.sqct.dao;

import java.util.List;

import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.ProductModel;

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
	
	
}