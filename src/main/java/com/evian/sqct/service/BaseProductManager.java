package com.evian.sqct.service;

import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.ProductModel;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantIsEnableReqDTO;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantSelectReqDTO;
import com.evian.sqct.bean.product.input.ProcXHXProductSaleStatisticsReqDTO;
import com.evian.sqct.dao.IProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseProductManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("productDao")
	private IProductDao productDao;
	
	public List<ProductClass> findProductClass(Integer eid,Integer cid,Boolean enabled,Integer pageIndex,Integer pageSize){
		return productDao.selectProductClass(eid, cid, enabled, pageIndex, pageSize);
	}
	
	public List<ProductModel> findProductByClass(Product product){
		return productDao.selectProductByClass(product);
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer addProductByShopIdAndPid(Integer shopId, Integer pid,Boolean recommend){
		return productDao.insertOrDelShopProduct(shopId, pid, recommend, "ADD");
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer removeProductByShopIdAndPid(Integer shopId, Integer pid){
		return productDao.insertOrDelShopProduct(shopId, pid, false, "DEL");
	}
	
	public Map<String,Object> selectProductEid(Product product){
		return productDao.selectProductEid(product);
	}

	public String Proc_Backstage_product_stock_num_operat(Integer tsId,Integer eid,Integer changeStockNum,String createUser){
		return productDao.Proc_Backstage_product_stock_num_operat(tsId, eid, changeStockNum, createUser);
	}

	public Map<String, Object> Proc_Backstage_product_specs_relevant_select(ProcBackstageProductSpecsRelevantSelectReqDTO dto){
		return productDao.Proc_Backstage_product_specs_relevant_select(dto);
	}

	public String Proc_Backstage_product_specs_relevant_isEnable(ProcBackstageProductSpecsRelevantIsEnableReqDTO dto){
		return productDao.Proc_Backstage_product_specs_relevant_isEnable(dto);
	}

	public Map<String, Object> Proc_XHX_product_sale_statistics(ProcXHXProductSaleStatisticsReqDTO dto) {
		return productDao.Proc_XHX_product_sale_statistics(dto);
	}

	
}
