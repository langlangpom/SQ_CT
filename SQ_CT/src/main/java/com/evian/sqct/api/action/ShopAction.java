package com.evian.sqct.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.shop.FindShopModel;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.shop.ShopTimeframe;
import com.evian.sqct.service.BaseShopManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import com.evian.sqct.util.DES.WebConfig;

/**
 * @date   2018年10月8日 上午11:22:03
 * @author XHX
 * @Description 店铺action
 */
@RestController
@RequestMapping("/evian/sqct/shop")
public class ShopAction extends BaseAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseShopManager baseShopManager;
	
	@RequestMapping("showComTenantShop.action")
	public Map<String, Object> showComTenantShop(Integer eid,Integer accountId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String,Object>> findShopByEidAndUserId = baseShopManager
					.findShopByEidAndUserId(accountId,eid);
			List<Map<String, Object>> resultMaps = new ArrayList<Map<String, Object>>();
			for (Map<String,Object> findShopModel : findShopByEidAndUserId) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("shopId", findShopModel.get("shopId"));
				map.put("shopName", findShopModel.get("shopName"));
				map.put("eid", findShopModel.get("eid"));
				map.put("pictureUrl", findShopModel.get("pictureUrl"));
				map.put("ifLine", findShopModel.get("ifLine"));
				resultMaps.add(map);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("shoplings", resultMaps);
			resultMap.put("isHaveShop", true);
			if (findShopByEidAndUserId.size() < 1) {
				resultMap.put("isHaveShop", false);
			}
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
	
	/**
	 * 5.商户选择店铺
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopId
	 *            店铺Id
	 * @return
	 */
	@RequestMapping("comTenantOptShop.action")
	public Map<String, Object> comTenantOptShop(String clientId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(clientId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		return parMap;
	}
	
	/**
	 * 6.商户创建店铺
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopName
	 *            店的名称
	 * @param shopNum
	 *            店的编号
	 * @param area
	 *            所在地区
	 * @param address
	 *            详细地址
	 * @param shopCoordinate
	 *            店铺坐标
	 * @param businessTime
	 *            营业时间
	 * @param averageTime
	 *            平均送达
	 * @param firmName
	 *            公司名称
	 * @param shopMainImg
	 *            店铺主图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createrShop.action")
	public Map<String, Object> createrShop(Integer eid,String createUser,String shopName,String shopNo,Integer cityId,String address,Integer districtId,String tel,String linkman,String location,String startTime,String endTime,Integer sendOnTime,String pictureUrl,Integer accountId,String description,String shopType,String scopeDescription,Double minSendPrice,Double freight,Boolean ifLine) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||createUser==null||shopName==null||shopNo==null||cityId==null||address==null||districtId==null||tel==null||linkman==null||location==null||startTime==null||endTime==null||sendOnTime==null||pictureUrl==null||accountId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			String upResult = QiniuFileSystemUtil.uploadShearPic(pictureUrl);
			if (!StringUtils.isEmpty(upResult)) {
				JSONObject ject = JSON.parseObject(upResult);
				if (!StringUtils.isEmpty((String) ject.get("hash"))
						&& !StringUtils.isEmpty((String) ject.get("key"))) {

					upResult = QiniuConfig.namespace + (String) ject.get("key");
				}
			}
			String addShop = baseShopManager.addShop(eid, shopNo, shopName,
					address, tel, linkman, cityId, districtId, location,
					description, upResult, shopType, startTime, endTime,
					scopeDescription, sendOnTime, minSendPrice, freight,
					createUser, ifLine, accountId);
			if(!"1".equals(addShop)){
				setCode(parMap, 150);
				setMessage(parMap, addShop);
			}
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
	
	/**
	 * 7.店铺管理
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopId
	 *            店铺Id
	 * @return
	 */
	@RequestMapping("shopManage.action")
	public Map<String,Object> shopManage() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 8.店铺认证
	 * 
	 * @param shopId
	 *            店铺Id
	 * @param shopName
	 *            公司名称
	 * @param businLicenNo
	 *            营业执照号
	 * @param businLicenImg
	 *            营业执照
	 * @param juriPerName
	 *            法人姓名
	 * @param juriPerIDcardFront
	 *            法人身份证正面
	 * @param juriPerIDcardCon
	 *            法人身份证反面
	 * @param clientPhone
	 *            手机号
	 * @param identifyingCode
	 *            验证码
	 * @return
	 */
	@RequestMapping("shopIdentification.action")
	public Map<String, Object> shopIdentification() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 9.店铺认证验证验证码
	 * 
	 * @param clientPhone
	 *            手机号
	 * @return
	 */
	@RequestMapping("shopIdentificationCode.action")
	public Map<String, Object> shopIdentificationCode() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 10.商户信息
	 * 
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("clientInfo.action")
	public Map<String, Object> clientInfo() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 11.商户商品管理
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("commodityManage.action")
	public Map<String, Object> commodityManage(Integer eid,Integer shopId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> commodityManage = baseShopManager.commodityManage(eid, shopId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("count", commodityManage.size());
			resultMap.put("productClass", commodityManage);
			logger.info("resultMap = "+resultMap);
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
	
	/**
	 * 12.店铺已绑定商品展示
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("showBindCommodity.action")
	public Map<String, Object> showBindCommodity() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 14.商品绑定
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param commodityId
	 *            商品ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("commodityBindOperat.action")
	public Map<String, Object> commodityBind(Integer shopId,Integer pid,Integer lineBit,Boolean recommend) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||pid==null||lineBit==null||recommend==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String productLine = baseShopManager.productLine(pid, shopId, recommend, lineBit);
			if(!"1".equals(productLine)){
				setCode(parMap, 150);
				setMessage(parMap, productLine);
			}
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
	
	/**
	 * 15.店铺未绑定商品展示
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("showNoBindCommodity.action")
	public Map<String, Object> showNoBindCommodity() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 1.增加商品
	 * 
	 * @param clientId
	 *            用户身份ID
	 * @param shopId
	 *            店铺ID
	 * @param commodityName
	 *            商品名称
	 * @param commodityPrice
	 *            商品价格
	 * @param commodityImg
	 *            商品图片
	 * @return
	 */
	@RequestMapping("addCommodity.action")
	public Map<String, Object> addCommodity() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		return parMap;
	}
	
	/**
	 * 20.选择区域
	 * 
	 * @param eid
	 *            企业ID
	 * @return
	 */
	@RequestMapping("queryCity.action")
	public Map<String, Object> queryCity(Integer eid) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> findAreaByEid = baseShopManager.findAreaByEid(eid);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("allCitys", findAreaByEid);
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
	
	/**
	 * 显示店铺信息
	 * 
	 * @return
	 */
	@RequestMapping("showShopInfo.action")
	public Map<String, Object> showShopInfo(Integer shopId,Integer eid) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<Map<String, Object>> resultMaps = new ArrayList<Map<String, Object>>();
			List<FindShopModel> shop = baseShopManager.findShopByEidAndShopId(shopId, eid);
			for (FindShopModel findShopModel : shop) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("shopId", findShopModel.getShopId());
				map.put("shopName", findShopModel.getShopName());
				map.put("eid", findShopModel.getEid());
				map.put("pictureUrl", findShopModel.getPictureUrl());
				resultMaps.add(map);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("shoplings", resultMaps);
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
	
	/**
	 * 19.商户更改店铺信息
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopName
	 *            店的名称
	 * @param shopNum
	 *            店的编号
	 * @param area
	 *            所在地区
	 * @param address
	 *            详细地址
	 * @param shopCoordinate
	 *            店铺坐标
	 * @param businessTime
	 *            营业时间
	 * @param averageTime
	 *            平均送达
	 * @param firmName
	 *            公司名称
	 * @param shopMainImg
	 *            店铺主图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateShopInfo.action")
	public Map<String, Object> updateShopInfo(Integer shopId,Integer eid,String createUser,String shopName,String shopNo,Integer cityId,String address,Integer districtId,String tel,String linkman,String location,String startTime,String endTime,Integer sendOnTime,String pictureUrl,String description,String shopType,String scopeDescription,Double minSendPrice,Double freight,Boolean ifLine) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||eid==null||createUser==null||shopName==null||shopNo==null||cityId==null||address==null||districtId==null||tel==null||linkman==null||location==null||startTime==null||endTime==null||sendOnTime==null||pictureUrl==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Shop shop = new Shop();
			shop.setShopId(shopId);
			shop.setEid(eid);
			shop.setShopNo(shopNo);
			shop.setShopName(shopName);
			shop.setAddress(address);
			shop.setTel(tel);
			shop.setLinkman(linkman);
			shop.setCityId(cityId);
			shop.setDistrictId(districtId);
			shop.setLocation(location);
			shop.setDescription(description);
			shop.setShopType(shopType);
			shop.setStartTime(startTime);
			shop.setEndTime(endTime);
			shop.setScopeDescription(scopeDescription);
			shop.setSendOnTime(sendOnTime);
			shop.setMinSendPrice(minSendPrice);
			shop.setFreight(freight);
			shop.setCreateUser(createUser);
			shop.setIfLine(ifLine);
			String upResult = QiniuFileSystemUtil.uploadShearPic(pictureUrl);
			if (!StringUtils.isEmpty(upResult)) {
				JSONObject ject = JSON.parseObject(upResult);
				if (!StringUtils.isEmpty((String) ject.get("hash"))
						&& !StringUtils.isEmpty((String) ject.get("key"))) {

					upResult = QiniuConfig.namespace + (String) ject.get("key");
				}
			}
			shop.setPictureUrl(upResult);
			Map<String, Object> updateShop = baseShopManager.updateShop(shop);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (updateShop.get("TAG") != null
					&& "1".equals(updateShop.get("TAG"))) {
				resultMap.put("shopId", updateShop.get("shopId"));
				resultMap.put("pictureUrl", upResult);
				setData(parMap, resultMap);
			}else {
				setCode(parMap, 150);
				setMessage(parMap, (String) updateShop.get("TAG"));
			}
			
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
	
	@RequestMapping("showShopCodes.action")
	public Map<String, Object> showShopCodes(Integer shopId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> shopCodes = baseShopManager.findShopCode(eid, shopId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("shopCodes", shopCodes);
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
	
	/**
	 * 42.查询配送时间
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findTimeframe.action")
	public Map<String, Object> findTimeframe(Integer shopId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<ShopTimeframe> findTimeframe = baseShopManager.findTimeframe(eid, shopId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("timeframes", findTimeframe);
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
	
	/**
	 * 43.修改配送时间
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upTimeframe.action")
	public Map<String, Object> upTimeframe(String times,Integer shopId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(times==null||shopId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			JSONArray parseArray = JSON.parseArray(times);
			String upTimeframe = baseShopManager.upTimeframe(eid,shopId,parseArray);
			if(!"1".equals(upTimeframe)){
				int code = Constants.CODE_ERROR_PARAM;
				setCode(parMap, code);
				setMessage(parMap, Constants.getCodeValue(code));
			}
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
	
	/**
	 * 84.修改商品属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateProductProperty.action")
	public Map<String, Object> updateProductProperty(Integer shopId,Integer productId,Integer eid,Boolean enabled,Double price,Integer sort,Integer type) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(productId==null||eid==null||type==null||shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		if(type.intValue()==0&&(shopId==null||productId==null||enabled==null||price==null)) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}else if(type.intValue()==1&&(shopId==null||productId==null||price==null)) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}else if(type.intValue()==2&&(productId==null||sort==null||eid==null)) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			
			Integer updateProductProperty = baseShopManager.updateProductProperty(shopId,productId, eid, enabled, price, sort, type);
			
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
	

	/**
	 * 85.查询企业二维码（公众号二维码，小程序推客码）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findEnterpriseQRCode.action")
	public Map<String, Object> findEnterpriseQRCode(Integer eid,String account) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> selectEnterpriseQRCode = baseShopManager.selectEnterpriseQRCode(eid, account);
			setData(parMap, selectEnterpriseQRCode);
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
	
	/**
	 * 86.app商户端公告
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("appMerchantNotify.action")
	public Map<String, Object> appMerchantNotify(Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> appMerchantNotify = baseShopManager.appMerchantNotify(eid);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appMerchantNotify", appMerchantNotify);
			setData(parMap, map);
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
	
	/**
	 * 87.手机商户端门店今日统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("appMerchantTodayStatistics.action")
	public Map<String, Object> appMerchantTodayStatistics(Integer accountId,Integer shopId) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null||shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> map = baseShopManager.appMerchantTodayStatistics(accountId, shopId);
			if(!"1".equals(map.get("TAG"))) {
				parMap.put("code", 150);
				parMap.put("message", map.get("TAG"));
				return parMap;
			}
			setData(parMap, map.get("appMerchantTodayStatistics"));
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
	

	/**
	 * 89.修改水店上下线
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateShopIfLine.action")
	public Map<String, Object> updateShopIfLine(Boolean ifLine,Integer eid,Integer shopId) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(ifLine==null||eid==null||shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			
			Integer updateProductProperty = baseShopManager.updateShopIfLine(ifLine, eid, shopId);
			
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
	
	/**
	 * 94.水店用户信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shopLeagueClientsSelect.action")
	public Map<String, Object> shopLeagueClientsSelect(String beginTime,String endTime,Integer shopId,Integer eid,String nickName,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> map = baseShopManager.shopLeagueClientsSelect(beginTime, endTime, shopId, eid, nickName, cellphone, PageIndex, PageSize, IsSelectAll);
			
			setData(parMap, map);
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
	

	/**
	 * 95.电子票有效剩余量
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTicketAccount.action")
	public Map<String, Object> getTicketAccount(Integer clientId,Integer type) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(clientId==null||type==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> ticketAccount = baseShopManager.getTicketAccount(clientId, type);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("ticketAccount", ticketAccount);
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
	
	/**
	 * 96.电子票消费列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("ticketSelectMx.action")
	public Map<String, Object> ticketSelectMx(Integer eid,Integer clientId,Integer shopId) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(clientId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> ticketAccount = baseShopManager.ticketSelectMx(eid, clientId,shopId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("consumeList", ticketAccount);
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

	/**
	 * 97.用户优惠卷
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectMyVouchers.action")
	public Map<String, Object> selectMyVouchers(Integer clientId) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(clientId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> selectMyVouchers = baseShopManager.selectMyVouchers(clientId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Vouchers", selectMyVouchers);
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
	
	/**
	 * 101.查询店铺内容和品牌
	 * @param shopId
	 * @return
	 */
	@RequestMapping("findShopAndBrand.action")
	public Map<String, Object> findShopAndBrand(Integer shopId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> resultMap = baseShopManager.selectShopAndBrand(shopId);
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
	
	/**
	 * 102.修改店铺名称图片电话
	 * @param shopId
	 * @return
	 */
	@RequestMapping("updateShopName.action")
	public Map<String, Object> updateShopName(Integer shopId,String pictureUrl, String tel, String shopName,String startTime,String	endTime){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String upResult = null;
			if(!StringUtils.isEmpty(pictureUrl)) {
				upResult = QiniuFileSystemUtil.uploadShearPic(pictureUrl);
				if (!StringUtils.isEmpty(upResult)) {
					JSONObject ject = JSON.parseObject(upResult);
					if (!StringUtils.isEmpty((String) ject.get("hash"))
							&& !StringUtils.isEmpty((String) ject.get("key"))) {
						
						upResult = QiniuConfig.namespace + (String) ject.get("key");
					}
				}
				
			}
			Integer updateShop = baseShopManager.updateShop(shopId, upResult, tel, shopName,startTime,endTime);
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
