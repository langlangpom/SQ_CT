package com.evian.sqct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.ProductDTO;
import com.evian.sqct.bean.product.ProductModel;
import com.evian.sqct.dao.IProductDao;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseProductManager {

	@Autowired
	@Qualifier("productDao")
	private IProductDao poductDao;
	
	public List<ProductClass> findProductClass(Integer eid,Integer cid,Boolean enabled,Integer pageIndex,Integer pageSize){
		return poductDao.selectProductClass(eid, cid, enabled, pageIndex, pageSize);
	}
	
	public List<ProductModel> findProductByClass(Product product){
		return poductDao.selectProductByClass(product);
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer addProductByShopIdAndPid(Integer shopId, Integer pid,Boolean recommend){
		return poductDao.insertOrDelShopProduct(shopId, pid, recommend, "ADD");
	}
	@Transactional(rollbackFor=Exception.class)
	public Integer removeProductByShopIdAndPid(Integer shopId, Integer pid){
		return poductDao.insertOrDelShopProduct(shopId, pid, false, "DEL");
	}
	
	public List<ProductDTO> selectProductEid(Product product){
		
		return poductDao.selectProductEid(product);
	}
	
}
