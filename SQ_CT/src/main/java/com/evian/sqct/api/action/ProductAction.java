package com.evian.sqct.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.product.ProductClass;
import com.evian.sqct.service.BaseProductManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.DES.WebConfig;

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
	public Map<String, Object> productClassSelect(Integer eid,Integer cid,Boolean enabled,Integer pageIndex,Integer pageSize) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||enabled==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
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
			setData(parMap, resultMap);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
	}
	
}
