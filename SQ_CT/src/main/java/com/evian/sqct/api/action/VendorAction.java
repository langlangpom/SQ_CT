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
import com.evian.sqct.bean.vendor.AdLedPrice;
import com.evian.sqct.bean.vendor.VendorDoor;
import com.evian.sqct.bean.vendor.VendorMainboard2;
import com.evian.sqct.bean.vendor.VendorProductReplenishmentClass;
import com.evian.sqct.bean.vendor.VendorShopContainer;
import com.evian.sqct.service.BaseVendorManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.DES.WebConfig;

/**
 * @date   2018年9月30日 上午11:02:12
 * @author XHX
 * @Description 售货机action
 */
@RestController
@RequestMapping("/evian/sqct/vendor")
public class VendorAction extends BaseAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseVendorManager baseVendorManager;
	
	/**
	 * 测试 获取数据库名
	 * 
	 */
	@RequestMapping("getNowDbName.action")
	public Map<String, Object> getNowDbName() throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			String nowDbName = baseVendorManager.getNowDbName();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("success", nowDbName);
			parMap.put("data", resultMap);
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
	 * 31取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("saveVerndorContainer.action")
	public Map<String, Object> saveVerndorContainer(Integer vmId,Integer bmId,Integer doorNumber,Integer vmIndex,String createdUser) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(vmId==null||bmId==null||doorNumber==null||vmIndex==null||createdUser==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String verndorContainerADD = baseVendorManager.verndorContainerADD(vmId, bmId, doorNumber, vmIndex, createdUser);
			if(!"1".equals(verndorContainerADD)){
				int code = 150;
				String message = verndorContainerADD;
				logger.info("[project:{}] [step:enter] [code:{}]", new Object[] {
						WebConfig.projectName, code });
				parMap.put("code", code);
				parMap.put("message", message);
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
	 * 32.取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("findContainer.action")
	public Map<String, Object> findContainer() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		
		
		return parMap;
	}
	
	/**
	 * 33.取消订单
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("findDoorAndProuct.action")
	public Map<String, Object> findDoorAndProuct(Integer mainboardId,Integer eid,Integer doorIndex,Integer classId,String beginTime,String endTime,String account,String containerCode,Integer doorReplenishmentDays,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<VendorDoor> findDoorAndProuct = baseVendorManager.findDoorAndProuct(mainboardId, eid,doorIndex, classId, beginTime, endTime, account, containerCode, doorReplenishmentDays, doorStatus, PageIndex, PageSize, IsSelectAll);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorDoorProucts", findDoorAndProuct);
			parMap.put("data", resultMap);
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
	 * 34自动售货机主板链接货柜
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("mainboardBindContainer.action")
	public Map<String, Object> mainboardBindContainer(String mainboardNo,String containerCode,Integer eid) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardNo==null||eid==null||containerCode==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> mainboardBindContainerMap = baseVendorManager.mainboardBindContainer(mainboardNo, containerCode, eid);
			logger.info("mainboardBindContainerMap : "+mainboardBindContainerMap);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if("1".equals(mainboardBindContainerMap.get("tag"))){
				resultMap.put("doorIndex", mainboardBindContainerMap.get("doorIndex"));
				resultMap.put("bmId", mainboardBindContainerMap.get("bmId"));
				parMap.put("data", resultMap);
			}else{
				int code = 150;
				String message = (String) mainboardBindContainerMap.get("tag");
				logger.info("[project:{}] [step:enter] [code:{}]", new Object[] {
						WebConfig.projectName, code });
				parMap.put("code", code);
				parMap.put("message", message);
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
	 * 35.验证是否成功
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("validateIsSuccess.action")
	public Map<String, Object> validateIsSuccess(Integer bmId,Integer doorIndex) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(bmId==null||doorIndex==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {	
			Boolean isSuccess = baseVendorManager.findmainboardInstructByBmIdByDoorIndex(bmId, doorIndex);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("isSuccess", isSuccess);
			parMap.put("data", resultMap);
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
	 * 36.自动售货机柜门校验通过
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("verificationVendorDoor.action")
	public Map<String, Object> verificationVendorDoor(Integer mainboardId,Integer doorIndex,Integer portNo,String alias,Integer doorStatus,String breakDownRemark) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardId==null||doorIndex==null||alias==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			String verificationVendorDoor = baseVendorManager.verificationVendorDoor(mainboardId, doorIndex, portNo,alias,doorStatus,breakDownRemark);
			if(!"1".equals(verificationVendorDoor)){
				int code = 150;
				String message = verificationVendorDoor;
				logger.info("[project:{}] [step:enter] [code:{}]", new Object[] {
						WebConfig.projectName, code });
				parMap.put("code", code);
				parMap.put("message", message);
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
	 * 37.自动售货机主板绑定柜门信息（用于APP商户端设备管理）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("vendorManageSelect.action")
	public Map<String, Object> vendorManageSelect(Integer shopId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<VendorShopContainer> vendorManage = baseVendorManager.VendorManage(shopId);
			resultMap.put("ShopContainers", vendorManage);
			parMap.put("data", resultMap);
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
	 * 38.自动售货机主板绑定柜门信息（用于APP商户端设备管理）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("AppCustomerMainboardSelect.action")
	public Map<String, Object> AppCustomerMainboardSelect(Integer eid,Integer shopId,Integer containerStatus,Integer accountId,String mainboardNoMD5) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			List<Map<String, Object>> appCustomerMainboardSelect = baseVendorManager.AppCustomerMainboardSelect(eid, shopId, containerStatus, accountId, mainboardNoMD5);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("customers", appCustomerMainboardSelect);
			parMap.put("data", resultMap);
			
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
	 * 39.自动售货机柜门补货（手机商户端）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("doorReplenishment.action")
	public Map<String, Object> doorReplenishment(Integer eid,Integer mainboardId,Integer doorIndex,Integer productId,String account,Integer operatType,Integer wxId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||mainboardId==null||doorIndex==null||productId==null||operatType==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			String doorReplenishment = baseVendorManager.doorReplenishment(eid, mainboardId, doorIndex, productId,account,operatType,wxId);
			
			if(!"1".equals(doorReplenishment)){
				int code = 150;
				String message = doorReplenishment;
				parMap.put("code", code);
				parMap.put("message", message);
				logger.info("[project:{}] [step:enter] [code:{}]", new Object[] {
						WebConfig.projectName, code });
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
	 * 40.自动售货机商品信息
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("vendorProductSelect.action")
	public Map<String, Object> vendorProductSelect(Integer eid,String productName,Boolean isLine) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||productName==null||isLine==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			
			List<Map<String, Object>> vendorProductSelect = baseVendorManager.vendorProductSelect(productName, eid, isLine);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("products", vendorProductSelect);
			parMap.put("data", resultMap);
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
	 * 41.自动售货机在售、上次销售商品统计   （废弃）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("vendorDoorProductStatistics.action")
	public Map<String, Object> vendorDoorProductStatistics(Integer accountId,Integer productState,Boolean isAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			List<Map<String, Object>> vendorProductSelect = baseVendorManager.vendorDoorProductStatistics(accountId, productState, isAll);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("statistics", vendorProductSelect);
			parMap.put("data", resultMap);
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
	 * 44.自动售货机主板柜门以及商品信息
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("findDoorAndProuct_v2.action")
	public Map<String, Object> findDoorAndProuct_v2(Integer eid,Integer PageIndex,Integer PageSize,Integer mainboardId,Integer replenishmentClassId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			Map<String, Object> findDoorAndProuct = baseVendorManager.findDoorAndProuct_v2(mainboardId,eid,replenishmentClassId, PageIndex,PageSize);
			/*Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorDoorProucts", findDoorAndProuct.get("vendorDoorProucts"));
			resultMap.put("count", findDoorAndProuct.get("count"));*/
			parMap.put("data", findDoorAndProuct);
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
	 * 45.货柜门校验
	 * 
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("doorInitValid.action")
	public Map<String, Object> doorInitValid(Integer mainboardId,Integer eid,Integer doorIndex,Integer portNo,String mainboardNo) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||mainboardId==null||doorIndex==null||portNo==null||mainboardNo==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String doorInitValid = baseVendorManager.doorInitValid(mainboardId,mainboardNo,eid, doorIndex, portNo);
			JSONObject parseObject = JSON.parseObject(doorInitValid);
			parMap.put("code", parseObject.getIntValue("code"));
			parMap.put("message", parseObject.getString("message"));
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
	 * 46.货柜补货批量开锁
	 * 
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("batchOpenEmptyDoor.action")
	public Map<String, Object> batchOpenEmptyDoor(Integer mainboardId,Integer eid,String mainboardNo) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||mainboardId==null||mainboardNo==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String batchOpenEmptyDoor = baseVendorManager.batchOpenEmptyDoor(mainboardId,mainboardNo, eid);
			JSONObject parseObject = JSON.parseObject(batchOpenEmptyDoor);
			parMap.put("code", parseObject.getIntValue("code"));
			parMap.put("message", parseObject.getString("message"));
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
	 * 47.查询货柜机是否正常运行
	 * 
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("mainboardLine.action")
	public Map<String, Object> mainboardLine(Integer mainboardId,String mainboardNo) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardId==null||mainboardNo==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			String mainboardLine = baseVendorManager.mainboardLine(mainboardId, mainboardNo);
			JSONObject parseObject = JSON.parseObject(mainboardLine);
			parMap.put("code", parseObject.getIntValue("code"));
			parMap.put("message", parseObject.getString("message"));
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
	 * 48.商户确认主板和货柜收货，只能在确认收货之后才能绑定和使用
	 * 
	 * @param eid		商户ID
	 *        guidStr	分配批次标识
	 *        userName	确认收货人账号
	 * @return
	 */
	@RequestMapping("vendorAppCustomerConfirmReceipt.action")
	public Map<String, Object> vendorAppCustomerConfirmReceipt(Integer eid,String guidStr) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||guidStr==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String result = baseVendorManager.vendorAppCustomerConfirmReceipt(eid, guidStr);
			if(!"1".equals(result)){
				parMap.put("code", 150);
				parMap.put("message", result);
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
	 * 41.自动售货机在售、上次销售商品统计   （ 新 ）
	 * 
	 * @param eid		商户ID
	 *        guidStr	分配批次标识
	 *        userName	确认收货人账号
	 * @return
	 */
	@RequestMapping("vendorAppCustomerDoorProductStatistics.action")
	public Map<String, Object> vendorAppCustomerDoorProductStatistics(Integer eid,Integer accountId,Boolean isAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||isAll==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> vendorAppCustomerDoorProductStatistics = baseVendorManager.vendorAppCustomerDoorProductStatistics(eid, accountId,isAll);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("doorProductStatistics", vendorAppCustomerDoorProductStatistics);
			parMap.put("data", resultMap);
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
	 * 49.柜门状态统计
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorAppCustomerDoorstatistics.action")
	public Map<String, Object> vendorAppCustomerDoorstatistics(Integer eid,Integer accountId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> vendorAppCustomerDoorstatistics = baseVendorManager.vendorAppCustomerDoorstatistics(eid, accountId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorAppCustomerDoorstatistics", vendorAppCustomerDoorstatistics);
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
	 * 50.设备状态统计
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorAppCustomerMainboardContainerstatistics.action")
	public Map<String, Object> vendorAppCustomerMainboardContainerstatistics(Integer eid,Integer accountId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			List<Map<String, Object>> vendorAppCustomerMainboardContainerstatistics = baseVendorManager.vendorAppCustomerMainboardContainerstatistics(eid, accountId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorAppCustomerMainboardContainerstatistics", vendorAppCustomerMainboardContainerstatistics);
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
	 * 51.更改设备状态
	 * 
	 * @param eid		商户ID
	 *        guidStr	分配批次标识
	 *        userName	确认收货人账号
	 * @return
	 */
	@RequestMapping("vendorAppCustomerMainboardContainerstatus.action")
	public Map<String, Object> vendorAppCustomerMainboardContainerstatus(Integer mainboardId,Integer accountId,Integer containerStatus,String remark) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardId==null||accountId==null||containerStatus==null||remark==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			String result = baseVendorManager.vendorAppCustomerMainboardContainerstatus(mainboardId, accountId, containerStatus, remark);
			if(!"1".equals(result)){
				setCode(parMap, 150);
				setMessage(parMap, result);
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
	 * 52.自动售货机状态更改
	 * 
	 * @param eid		商户ID
	 *        guidStr	分配批次标识
	 *        userName	确认收货人账号
	 * @return
	 */
	@RequestMapping("vendorAppCustomerMainboardContainerAddressEdit.action")
	public Map<String, Object> vendorAppCustomerMainboardContainerAddressEdit(Integer mainboardId,Integer accountId,String shopContainerName,String location,String containerAddress,Integer communityId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardId==null||accountId==null||shopContainerName==null||location==null||containerAddress==null||communityId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			String result = baseVendorManager.vendorAppCustomerMainboardContainerAddressEdit(mainboardId, accountId, shopContainerName, location, containerAddress,communityId);
			if(!"1".equals(result)){
				setCode(parMap, 150);
				setMessage(parMap, result);
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
	 * 53.设备状态统计
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorDoorStatistics.action")
	public Map<String, Object> vendorDoorStatistics(Integer eid,Integer accountId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> vendorAppCustomerMainboardContainerDoorStatistics = baseVendorManager.vendorAppCustomerMainboardContainerDoorStatistics(eid, accountId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorDoorStatistics", vendorAppCustomerMainboardContainerDoorStatistics);
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
	 * 54.订单
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorSelectOrder.action")
	public Map<String, Object> vendorSelectOrder(String beginTime,String endTime,String orderNo,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,String nickName,Integer doorIndex,Boolean isTest,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> vendorSelectOrder = baseVendorManager.vendorSelectOrder(beginTime, endTime, orderNo, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName,nickName,doorIndex,isTest,openId, PageIndex, PageSize, IsSelectAll);
			setData(parMap, vendorSelectOrder);
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
	 * 55.自动售货机社区 （省->市->区->社区） 为了随机减额活动选择参与自动售货机选择，自动售货机绑定时需要增加选择所属社区 2018-11-02 王总口头需求
	 * 
	 * @param eid		商户ID
	 *        guidStr	分配批次标识
	 *        userName	确认收货人账号
	 * @return
	 */
	@RequestMapping("vendorGisCommunityOperat.action")
	public Map<String, Object> vendorGisCommunityOperat(String tag,Integer communityId,Integer districtId,String location,String communityName,Integer eid,Integer accountId,String account,String remark) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(tag==null||districtId==null||location==null||communityName==null||eid==null||accountId==null||account==null||(!tag.equals("ADD")&&communityId==null)) {
			
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		
		try {
			String result = baseVendorManager.vendorGisCommunityOperat(tag, communityId,districtId,location, communityName, eid, accountId, account, remark);
			if(!"1".equals(result)){
				parMap.put("code", 150);
				parMap.put("message", result);
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
	 * 56.查询省市区
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("findArea.action")
	public Map<String, Object> findArea(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll,Integer provinceId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(districtId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> findArea = baseVendorManager.findArea(eid, cityId, districtId, isAudit, PageIndex, PageSize, IsSelectAll, provinceId);
			parMap.put("data", findArea);
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
	 * 56.查询省市区
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("findArea_v2.action")
	public Map<String, Object> findArea_v2(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll,Integer provinceId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(districtId==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> findArea = baseVendorManager.findAreaByDistrictId(provinceId, cityId, districtId, isAudit, PageIndex, PageSize, IsSelectAll);
			parMap.put("data", findArea);
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
	 * 57.广告价格查询
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("findAdLedPrices.action")
	public Map<String, Object> findAdLedPrices() {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<AdLedPrice> findAdLedPrices = baseVendorManager.findAdLedPrices();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("adLedPrices", findAdLedPrices);
			parMap.put("data", map);
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
	 * 58.广告订单
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("vendorAdLedOrderSave.action")
	public Map<String, Object> vendorAdLedOrderSave(Integer clientId,String adContent,String beginDate,Integer dayQuantity,String codeNo,Double orderMoney,String communityIds) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(clientId==null||adContent==null||beginDate==null||dayQuantity==null||orderMoney==null||communityIds==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Map<String, Object> findArea = baseVendorManager.vendorAdLedOrderSave(clientId, adContent, beginDate, dayQuantity,codeNo, orderMoney, communityIds);
			if(!"1".equals(findArea.get("tag"))){
				parMap.put("code", 150);
				parMap.put("message", findArea.get("tag"));
			}
			parMap.put("data", findArea);
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
	 * 59.LDE广告订单查询
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorAdLedOrderSelect.action")
	public Map<String, Object> vendorAdLedOrderSelect(String beginTime,String endTime,Integer orderId,Boolean isPay,Integer orderStatus,String orderNo,String account,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<Map<String, Object>> vendorAdLedOrderSelect = baseVendorManager.vendorAdLedOrderSelect(beginTime, endTime, orderId, isPay, orderStatus, orderNo, account, PageIndex, PageSize, IsSelectAll);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorAdLedOrders", vendorAdLedOrderSelect);
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
	 * 60.自动售货机用户代金券查询
	 * 
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	@RequestMapping("vendorCodeAppCustomerSelect.action")
	public Map<String, Object> vendorCodeAppCustomerSelect(Integer eid,Integer clientId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<Map<String, Object>> vendorCodeAppCustomerSelect = baseVendorManager.vendorCodeAppCustomerSelect(eid, clientId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("vendorCodeAppCustomers", vendorCodeAppCustomerSelect);
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
	 * 61.售货机信息
	 * 
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	@RequestMapping("selectMainboardBymainboardNo.action")
	public Map<String, Object> selectMainboardBymainboardNo(String mainboardNo) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardNo==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<VendorMainboard2> selectMainboardBymainboardNo = baseVendorManager.selectMainboardBymainboardNo(mainboardNo);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("nainboard", selectMainboardBymainboardNo);
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
	 * 65.自动售货机小时结存统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("hourBalanceStatistics.action")
	public Map<String, Object> hourBalanceStatistics(Integer searchType,String beginTime,String endTime,Integer eid,Integer productId,Integer mainboardId,Integer accountId,Integer communityId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(searchType==null||beginTime==null||endTime==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> balanceProductidMainboardidAccountidCommunityidByhourSelect = baseVendorManager.balanceProductidMainboardidAccountidCommunityidByhourSelect(searchType, beginTime, endTime, eid, productId, mainboardId, accountId, communityId);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("hourBalanceStatistics", balanceProductidMainboardidAccountidCommunityidByhourSelect);
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
	 * 67.自动售货机小时结存统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorErrorLogSelect.action")
	public Map<String, Object> vendorErrorLogSelect(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,String nickName,String eName,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			logger.info("");
			List<Map<String, Object>> vendorErrorLogSelect = baseVendorManager.vendorErrorLogSelect(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, nickName, eName,isDispose, PageIndex, PageSize, IsSelectAll);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorErrorLogs", vendorErrorLogSelect);
			parMap.put("data", map);
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
	 * 68.修改处理结果
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("updateErrResult.action")
	public Map<String, Object> updateErrResult(Integer logId,Boolean isDispose,String disposeMsg) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(logId==null||isDispose==null||disposeMsg==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			Integer updateErrResult = baseVendorManager.updateErrResult(logId, isDispose, disposeMsg);
			if(updateErrResult.intValue()!=1) {
				parMap.put("code", 105);
				parMap.put("message", "修改失败");
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
	 * 69.app商户账户所管理社区
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("appMerchantAccountShopVendorSelect.action")
	public Map<String, Object> appMerchantAccountShopVendorSelect(Integer accountId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			List<Map<String, Object>> appMerchantAccountShopVendorSelect = baseVendorManager.appMerchantAccountShopVendorSelect(accountId);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("accountShopVendor", appMerchantAccountShopVendorSelect);
			parMap.put("data",map);
			
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
	 * 70.查询时间内访问信息(柱形报表点击)
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("viewVisitSelect.action")
	public Map<String, Object> viewVisitSelect(String beginTime,String endTime,String mainboardNoMD5,Boolean isTest,Integer communityId,Integer accountId,Integer eid,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			
			List<Map<String, Object>> viewVisitSelect = baseVendorManager.viewVisitSelect(beginTime,endTime, mainboardNoMD5, isTest,communityId,accountId, eid,openId,PageIndex, PageSize, IsSelectAll);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("viewVisits", viewVisitSelect);
			parMap.put("data",map);
			
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
	 * 71.APP商户端补货统计
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("replenishmentStatistics.action")
	public Map<String, Object> replenishmentStatistics(String beginTime,String endTime,String account,Integer eid,String productName,String productCode) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(account==null||beginTime==null||endTime==null||eid==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			Map<String, Object> replenishmentStatistics = baseVendorManager.replenishmentStatistics(eid, beginTime, endTime, account,productName, productCode);
			parMap.put("data",replenishmentStatistics);
			
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
	 * 72.自动售货机补货员商品补充记录
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("productSupplementSelect.action")
	public Map<String, Object> productSupplementSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			
			Map<String, Object> replenishmentStatistics = baseVendorManager.productSupplementSelect(beginTime, endTime, eid, account, productCode, productName,shopName,isTest, PageIndex, PageSize, IsSelectAll);
			parMap.put("data",replenishmentStatistics);
			
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
	 * 73.自动售货机补货员商品补充记录确认 补货入库
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("productSupplementConfirm.action")
	public Map<String, Object> productSupplementConfirm(Integer supplementId,Integer eid,Integer accountId,Integer realQuantity,String remark,Integer productId) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(supplementId==null||accountId==null||realQuantity==null||eid==null||productId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			
			String tag = baseVendorManager.productSupplementConfirm(supplementId, eid, accountId, realQuantity, remark,productId);
			if("1".equals(tag)) {
				Map<String, Object> map =new HashMap<>();
				map.put("TAG", tag);
				parMap.put("data",map);
			}else {
				int code = Constants.CODE_ERROR_SYSTEM;
				parMap.put("code", code);
				parMap.put("message", tag);
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
	 * 74.自动售货机设备错误记录统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorErrorLogStatistics.action")
	public Map<String, Object> vendorErrorLogStatistics(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> vendorErrorLogStatistics = baseVendorManager.vendorErrorLogStatistics(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, isDispose, PageIndex, PageSize, IsSelectAll);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorErrorLogStatistics", vendorErrorLogStatistics);
			parMap.put("data", map);
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
	 * 75.自动售货机用户购买统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("repetitionBuySelect.action")
	public Map<String, Object> repetitionBuySelect(String beginTime,String endTime,Boolean isSearchTimeForAuth,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer sortType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> repetitionBuySelect = baseVendorManager.repetitionBuySelect(beginTime, endTime, isSearchTimeForAuth, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, cellphone,sortType, PageIndex, PageSize, IsSelectAll);
			parMap.put("data", repetitionBuySelect);
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
	 * 76.自动售货机 货柜或商品购买统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorMainboardOrProductIdBuySelect.action")
	public Map<String, Object> vendorMainboardOrProductIdBuySelect(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> vendorMainboardOrProductIdBuySelect = baseVendorManager.vendorMainboardOrProductIdBuySelect(statisticsType, beginTime, endTime, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, cellphone, PageIndex, PageSize, IsSelectAll);
			parMap.put("data", vendorMainboardOrProductIdBuySelect);
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
	 * 77.查询补货计划
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorReplenishmentPlan.action")
	public Map<String, Object> findVendorReplenishmentPlan(Integer eid,Integer shopId,Integer containerId,Integer accountId,Integer PageIndex, Integer PageSize, Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<Map<String, Object>> vendorReplenishmentPlanSelect = baseVendorManager.vendorReplenishmentPlanSelect(eid,shopId, containerId, accountId,PageIndex, PageSize, IsSelectAll);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("replenishmentPlans", vendorReplenishmentPlanSelect);
			parMap.put("data", map);
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
	 * 78.查询补货计划详情
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorReplenishmentPlanDetails.action")
	public Map<String, Object> findVendorReplenishmentPlanDetails(Integer planId,Integer mainboardId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<Map<String, Object>> details = baseVendorManager.vendorReplenishmentPlanDetailsSelect(planId,mainboardId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("replenishmentPlanDetails", details);
			parMap.put("data", map);
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
	 * 79.补货计划操作
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorReplenishmentPlanOper.action")
	public Map<String, Object> vendorReplenishmentPlanOper(String oper,Integer planId,String mainboardIds,Integer shopId,Integer containerId,String planName,Boolean isShopAll,String creater,String productJsonArray){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(oper==null||(!"ADD".equals(oper)&&!"DEL".equals(oper)&&!"UPDATE".equals(oper)&&!"REPLACE".equals(oper))) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Integer> mIds = new ArrayList<Integer>();
			if(mainboardIds!=null&&!StringUtils.isEmpty(mainboardIds)) {
				String[] split = mainboardIds.split(",");
				for (String mIdstr : split) {
					int parseInt = Integer.parseInt(mIdstr);
					mIds.add(parseInt);
				}
			}
			
			if("ADD".equals(oper)){
				if((mIds.size()==0&&(shopId==null||containerId==null))||planName==null||isShopAll==null||creater==null||productJsonArray==null) {
					int code = Constants.CODE_ERROR_PARAM;
					String message = Constants.getCodeValue(code);
					parMap.put("code", code);
					parMap.put("message", message);
					return parMap;
				}
				
				JSONArray parseArray = JSONArray.parseArray(productJsonArray);
				Integer result = baseVendorManager.insertVendorReplenishmentPlan(mIds,shopId,containerId, planName, isShopAll, creater, parseArray);
				if(-1==result.intValue()) {
					parMap.put("code", -1);
					parMap.put("message", "货柜店铺或类型不相同");
					return parMap;
				}
			}else if("DEL".equals(oper)) {
				if(planId==null) {
					int code = Constants.CODE_ERROR_PARAM;
					String message = Constants.getCodeValue(code);
					parMap.put("code", code);
					parMap.put("message", message);
					return parMap;
				}
				baseVendorManager.removeVendorReplenishmentPlan(planId);
			}else if("UPDATE".equals(oper)) {
				if(planId==null) {
					int code = Constants.CODE_ERROR_PARAM;
					String message = Constants.getCodeValue(code);
					parMap.put("code", code);
					parMap.put("message", message);
					return parMap;
				}
				JSONArray parseArray = JSONArray.parseArray(productJsonArray);
				baseVendorManager.updateVendorReplenishmentPlan(mIds, planId, planName, isShopAll, parseArray);
			}else if("REPLACE".equals(oper)) {
				if(mIds.size()==0||planId==null) {
					int code = Constants.CODE_ERROR_PARAM;
					String message = Constants.getCodeValue(code);
					parMap.put("code", code);
					parMap.put("message", message);
					return parMap;
				}
				baseVendorManager.updateVendorReplenishmentPlanByPlanId(mIds, planId);
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
	 * 80.售货机正在售卖中的商品中各种类的数量
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorSellProductKindNum.action")
	public Map<String, Object> findVendorSellProductKindNum(Integer accountId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(accountId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> details = baseVendorManager.selectVendorSellProductKindNum(accountId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vendorProductNum", details);
			parMap.put("data", map);
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
	 * 81.自动售货机补货员商品库存往来明细
	 * @param beginTime
	 * @param endTime
	 * @param eid
	 * @param account
	 * @param productCode
	 * @param productName
	 * @param shopName
	 * @param isTest
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	@RequestMapping("findVendorAppMerchantProductStorageDetail.action")
	public Map<String, Object> findVendorAppMerchantProductStorageDetail(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest,String remark,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> details = baseVendorManager.vendorAppMerchantProductStorageDetailSelect(beginTime, endTime, eid, account, productCode, productName, shopName, isTest, remark,PageIndex, PageSize, IsSelectAll);
			parMap.put("data", details);
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
	 * 82.在售商品统计根据商品
	 * @return
	 */
	@RequestMapping("onSaleCommodityStatisticsByProudct.action")
	public Map<String, Object> onSaleCommodityStatistics(Integer eid,Integer doorStatus,String productName,String productCode,String shortName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> details = baseVendorManager.vendorDoorSelectGroupByProductId(eid, doorStatus, productName, productCode, shortName, PageIndex, PageSize, IsSelectAll);
			parMap.put("data", details);
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
	 * 83.在售商品统计根据商品Id
	 * @return
	 */
	@RequestMapping("onSaleCommodityStatisticsProudctId.action")
	public Map<String, Object> onSaleCommodityStatistics(Integer eid,Integer doorStatus,Integer productId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			Map<String, Object> details = baseVendorManager.vendorDoorSelectGroupByProductIdAndMainboardId(eid, productId, doorStatus, PageIndex, PageSize, IsSelectAll);
			parMap.put("data", details);
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
	 * 88.补货分类查询
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorProductReplenishmentClass.action")
	public Map<String, Object> findVendorProductReplenishmentClass(){
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			List<VendorProductReplenishmentClass> details = baseVendorManager.selectVendorProductReplenishmentClass();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("replenishmentClass", details);
			parMap.put("data", map);
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
	 * 90.查询开锁明细
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findUnlockingByOrderId.action")
	public Map<String, Object> findUnlockingByOrderId(Integer orderId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null) {
			int code = Constants.CODE_ERROR_PARAM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap;
		}
		try {
			List<Map<String, Object>> selectUnlockingByOrderId = baseVendorManager.selectUnlockingByOrderId(orderId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("unlockingDetail", selectUnlockingByOrderId);
			parMap.put("data", map);
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
	 * 90.查询开锁明细
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("allOnLineMainboards.action")
	public String allOnLineMainboards(){
		try {
			String allOnLineMainboards = baseVendorManager.allOnLineMainboards();
			return allOnLineMainboards;
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			JSONObject parMap = new JSONObject();
			parMap.put("code", code);
			parMap.put("message", message);
			return parMap.toJSONString();
		}
	}
}
