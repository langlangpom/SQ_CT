package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.product.Product;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantIsEnableReqDTO;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantSelectReqDTO;
import com.evian.sqct.bean.product.input.ProcXHXProductSaleStatisticsReqDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseProductManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.DES.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2018年10月8日 上午11:15:28
 * @author XHX
 * @Description 商品action
 */
@RestController
@RequestMapping("/evian/sqct/product")
public class ProductAction extends BaseAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BaseProductManager baseProductManager;
	
	@RequestMapping("productClassSelect.action")
	public Object productClassSelect(Integer eid,Integer cid,Boolean enabled,Integer pageIndex,Integer pageSize) {
		if(eid==null) {
			return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
		}
		try {
			List<ProductClass> findProductClass = baseProductManager.findProductClass(eid, cid, enabled, pageIndex, pageSize);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < findProductClass.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cid", findProductClass.get(i).getCid());
				map.put("className", findProductClass.get(i).getClassName());
				map.put("classNo", findProductClass.get(i).getClassNo());
				map.put("classSort", findProductClass.get(i).getClassSort());
				map.put("description", findProductClass.get(i).getDescription());
				map.put("eid", findProductClass.get(i).getEid());
				map.put("enabled", findProductClass.get(i).getEnabled());
				map.put("property", findProductClass.get(i).getProperty());
				list.add(map);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("productClass", list);
			return resultMap;
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			return new ResultVO<>(ResultCode.CODE_ERROR_SYSTEM);
		}
	}
	

	/**
	 * 108.查询企业商品
	 * @param shopId
	 * @return
	 */
	@RequestMapping("findEidProduct.action")
	public Map<String,Object> findEidProduct(Product product){
		return baseProductManager.selectProductEid(product);
	}


	/**
	 * 172.库存数量修改
	 * @param shopId
	 * @return
	 */
	@RequestMapping("stockNumOperat.action")
	public ResultVO stockNumOperat(Integer tsId,Integer eid,Integer changeStockNum,String createUser){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(tsId==null||eid==null||changeStockNum==null||createUser==null) {
			return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
		}
		String tag = baseProductManager.Proc_Backstage_product_stock_num_operat(tsId, eid, changeStockNum, createUser);
		if(!"1".equals(tag)){
			throw new ResultException(tag);
		}
		return new ResultVO();
	}


	/**
	 * 250.商品规格查询
	 * @param shopId
	 * @return
	 */
	@RequestMapping("findProductSpecs.action")
	public Map<String,Object> findProductSpecs(@Valid ProcBackstageProductSpecsRelevantSelectReqDTO dto){
		return baseProductManager.Proc_Backstage_product_specs_relevant_select(dto);
	}


	/**
	 * 251.商品规格上下架
	 * @param shopId
	 * @return
	 */
	@RequestMapping("productSpecsEnable.action")
	public ResultVO productSpecsEnable(@Valid ProcBackstageProductSpecsRelevantIsEnableReqDTO dto){
		String tag = baseProductManager.Proc_Backstage_product_specs_relevant_isEnable(dto);
		if(!"1".equals(tag)){
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}


	/**
	 * 252.商品销售统计
	 * @param shopId
	 * @return
	 */
	@RequestMapping("productSaleStatistics.action")
	public Map<String,Object> productSaleStatistics(@Valid ProcXHXProductSaleStatisticsReqDTO dto){
		return baseProductManager.Proc_XHX_product_sale_statistics(dto);
	}

	
}
