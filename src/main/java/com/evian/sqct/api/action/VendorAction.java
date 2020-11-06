package com.evian.sqct.api.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.baseBean.PagingPojo;
import com.evian.sqct.bean.vendor.*;
import com.evian.sqct.bean.vendor.input.*;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyBankRepDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyProvinceRepDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyRepDTO;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseVendorManager;
import com.evian.sqct.util.CallBackPar;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

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
		String nowDbName = baseVendorManager.getNowDbName();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", nowDbName);
		return resultMap;
	}
	
	/**
	 * 31取消订单
	 * @return
	 */
	@RequestMapping("saveVerndorContainer.action")
	public ResultVO saveVerndorContainer(Integer vmId, Integer bmId, Integer doorNumber, Integer vmIndex, String createdUser) {
		if(vmId==null||bmId==null||doorNumber==null||vmIndex==null||createdUser==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String verndorContainerADD = baseVendorManager.verndorContainerADD(vmId, bmId, doorNumber, vmIndex, createdUser);
		if(!"1".equals(verndorContainerADD)){
			throw new ResultException(verndorContainerADD);
		}
		return new ResultVO();
	}
	
	/**
	 * 32.取消订单
	 * @return
	 */
	@RequestMapping("findContainer.action")
	public ResultVO findContainer() {
		return new ResultVO();
	}
	
	/**
	 * 33.取消订单
	 * @return
	 */
	@RequestMapping("findDoorAndProuct.action")
	public ResultVO findDoorAndProuct(Integer mainboardId,Integer eid,Integer doorIndex,Integer classId,String beginTime,String endTime,String account,String containerCode,Integer doorReplenishmentDays,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		if(mainboardId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<VendorDoor> findDoorAndProuct = baseVendorManager.findDoorAndProuct(mainboardId, eid,doorIndex, classId, beginTime, endTime, account, containerCode, doorReplenishmentDays, doorStatus, PageIndex, PageSize, IsSelectAll);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("vendorDoorProucts", findDoorAndProuct);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 34自动售货机主板链接货柜
	 * @param clientId			用户身份ID
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("mainboardBindContainer.action")
	public ResultVO mainboardBindContainer(String mainboardNo,String containerCode,Integer eid) {
		if(mainboardNo==null||eid==null||containerCode==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> mainboardBindContainerMap = baseVendorManager.mainboardBindContainer(mainboardNo, containerCode, eid);
		logger.info("mainboardBindContainerMap : "+mainboardBindContainerMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("1".equals(mainboardBindContainerMap.get("tag"))){
			resultMap.put("doorIndex", mainboardBindContainerMap.get("doorIndex"));
			resultMap.put("bmId", mainboardBindContainerMap.get("bmId"));
			return new ResultVO(resultMap);
		}else{
			throw new ResultException((String) mainboardBindContainerMap.get("tag"));
		}
	}
	
	/**
	 * 35.验证是否成功
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("validateIsSuccess.action")
	public ResultVO validateIsSuccess(Integer bmId,Integer doorIndex) {
		if(bmId==null||doorIndex==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Boolean isSuccess = baseVendorManager.findmainboardInstructByBmIdByDoorIndex(bmId, doorIndex);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isSuccess", isSuccess);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 36.自动售货机柜门校验通过
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("verificationVendorDoor.action")
	public ResultVO verificationVendorDoor(Integer mainboardId,Integer doorIndex,Integer portNo,String alias,Integer doorStatus,String breakDownRemark) {
		if(mainboardId==null||doorIndex==null||alias==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String verificationVendorDoor = baseVendorManager.verificationVendorDoor(mainboardId, doorIndex, portNo,alias,doorStatus,breakDownRemark);
		if(!"1".equals(verificationVendorDoor)){
			throw new ResultException(verificationVendorDoor);
		}
		return new ResultVO();
	}
	
	/**
	 * 37.自动售货机主板绑定柜门信息（用于APP商户端设备管理）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("vendorManageSelect.action")
	public ResultVO vendorManageSelect(Integer shopId) {
		if(shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<VendorShopContainer> vendorManage = baseVendorManager.VendorManage(shopId);
		resultMap.put("ShopContainers", vendorManage);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 38.自动售货机主板绑定柜门信息（用于APP商户端设备管理）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("AppCustomerMainboardSelect.action")
	public ResultVO AppCustomerMainboardSelect(Integer eid,Integer shopId,Integer containerStatus,Integer accountId,String mainboardNoMD5,Integer communityId) {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> appCustomerMainboardSelect = baseVendorManager.AppCustomerMainboardSelect(eid, shopId, containerStatus, accountId, mainboardNoMD5,communityId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("customers", appCustomerMainboardSelect);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 39.自动售货机柜门补货（手机商户端）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("doorReplenishment.action")
	public ResultVO doorReplenishment(Integer eid,Integer mainboardId,Integer doorIndex,Integer productId,String account,Integer typeId, String typeName,Integer wxId,String location) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||mainboardId==null||doorIndex==null||productId==null||typeId==null||typeName==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String doorReplenishment = baseVendorManager.doorReplenishment(eid, mainboardId, doorIndex, productId,account,typeId,typeName,wxId,location);
		if(!"1".equals(doorReplenishment)){
			throw new ResultException(doorReplenishment);
		}
		return new ResultVO();
	}
	
	/**
	 * 40.自动售货机商品信息
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("vendorProductSelect.action")
	public ResultVO vendorProductSelect(Integer eid,String productName,Boolean isLine) {
		if(eid==null||productName==null||isLine==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorProductSelect = baseVendorManager.vendorProductSelect(productName, eid, isLine);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("products", vendorProductSelect);
		return new ResultVO(resultMap);
	}

	/**
	 * 41.自动售货机在售、上次销售商品统计   （废弃）
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("vendorDoorProductStatistics.action")
	public ResultVO vendorDoorProductStatistics(Integer accountId,Integer productState,Boolean isAll) {
		if(accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorProductSelect = baseVendorManager.vendorDoorProductStatistics(accountId, productState, isAll);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("statistics", vendorProductSelect);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 44.自动售货机主板柜门以及商品信息
	 * @param clientId			用户身份ID
	 * @return
	 */
	@RequestMapping("findDoorAndProuct_v2.action")
	public ResultVO findDoorAndProuct_v2(@Valid ProcBackstageVendorProductSelectReqDTO dto) {
		Map<String, Object> findDoorAndProuct = baseVendorManager.findDoorAndProuct_v2(dto);
		/*Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("vendorDoorProucts", findDoorAndProuct.get("vendorDoorProucts"));
		resultMap.put("count", findDoorAndProuct.get("count"));*/

		return new ResultVO(findDoorAndProuct);
	}
	
	/**
	 * 45.货柜门校验
	 * 
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("doorInitValid.action")
	public ResultVO doorInitValid(Integer mainboardId,Integer eid,Integer doorIndex,Integer portNo,String mainboardNo) {
		if(eid==null||mainboardId==null||doorIndex==null||portNo==null||mainboardNo==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String doorInitValid = baseVendorManager.doorInitValid(mainboardId,mainboardNo,eid, doorIndex, portNo);
		JSONObject parseObject = JSON.parseObject(doorInitValid);
		return new ResultVO(parseObject.getIntValue("code"),parseObject.getString("message"));
	}
	
	/**
	 * 46.货柜补货批量开锁
	 * 
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("batchOpenEmptyDoor.action")
	public ResultVO batchOpenEmptyDoor(Integer mainboardId,Integer eid,String mainboardNo) {
		if(eid==null||mainboardId==null||mainboardNo==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String batchOpenEmptyDoor = baseVendorManager.batchOpenEmptyDoor(mainboardId,mainboardNo, eid);
		JSONObject parseObject = JSON.parseObject(batchOpenEmptyDoor);
		return new ResultVO(parseObject.getIntValue("code"),parseObject.getString("message"));
	}
	
	
	/**
	 * 47.查询货柜机是否正常运行
	 * 
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("mainboardLine.action")
	public ResultVO mainboardLine(Integer mainboardId,String mainboardNo) {
		if(mainboardId==null||mainboardNo==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}

		String mainboardLine = baseVendorManager.mainboardLine(mainboardId, mainboardNo);
		JSONObject parseObject = JSON.parseObject(mainboardLine);
		return new ResultVO(parseObject.getIntValue("code"),parseObject.getString("message"));
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
	public ResultVO vendorAppCustomerConfirmReceipt(Integer eid,String guidStr) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||guidStr==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String result = baseVendorManager.vendorAppCustomerConfirmReceipt(eid, guidStr);
		if(!"1".equals(result)){
			throw new ResultException(result);
		}
		return new ResultVO();
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
	public ResultVO vendorAppCustomerDoorProductStatistics(Integer eid,Integer accountId,Boolean isAll) {
		if(eid==null||isAll==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorAppCustomerDoorProductStatistics = baseVendorManager.vendorAppCustomerDoorProductStatistics(eid, accountId,isAll);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("doorProductStatistics", vendorAppCustomerDoorProductStatistics);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 49.柜门状态统计
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorAppCustomerDoorstatistics.action")
	public ResultVO vendorAppCustomerDoorstatistics(Integer eid,Integer accountId,Integer shopId) {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorAppCustomerDoorstatistics = baseVendorManager.vendorAppCustomerDoorstatistics(eid, accountId,shopId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("vendorAppCustomerDoorstatistics", vendorAppCustomerDoorstatistics);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 50.设备状态统计
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorAppCustomerMainboardContainerstatistics.action")
	public ResultVO vendorAppCustomerMainboardContainerstatistics(Integer eid,Integer accountId) {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorAppCustomerMainboardContainerstatistics = baseVendorManager.vendorAppCustomerMainboardContainerstatistics(eid, accountId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("vendorAppCustomerMainboardContainerstatistics", vendorAppCustomerMainboardContainerstatistics);
		return new ResultVO(resultMap);
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
	public ResultVO vendorAppCustomerMainboardContainerstatus(Integer mainboardId,Integer accountId,Integer containerStatus,String remark) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardId==null||accountId==null||containerStatus==null||remark==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String result = baseVendorManager.vendorAppCustomerMainboardContainerstatus(mainboardId, accountId, containerStatus, remark);
		if(!"1".equals(result)){
			throw new ResultException(result);
		}
		return new ResultVO();
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
	public ResultVO vendorAppCustomerMainboardContainerAddressEdit(Integer mainboardId,Integer accountId,String shopContainerName,String location,String containerAddress,Integer communityId) {
		if(mainboardId==null||accountId==null||shopContainerName==null||location==null||containerAddress==null||communityId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String result = baseVendorManager.vendorAppCustomerMainboardContainerAddressEdit(mainboardId, accountId, shopContainerName, location, containerAddress,communityId);
		if(!"1".equals(result)){
			throw new ResultException(result);
		}
		return new ResultVO();
	}
	
	/**
	 * 53.设备状态统计
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorDoorStatistics.action")
	public Map<String,Object> vendorDoorStatistics(@Valid VendorDoorStatisticsDTO dto) {

		Map<String, Object> vendorAppCustomerMainboardContainerDoorStatistics = baseVendorManager.vendorAppCustomerMainboardContainerDoorStatistics(dto);
		return vendorAppCustomerMainboardContainerDoorStatistics;
	}
	
	/**
	 * 54.订单
	 * 
	 * @param eid		企业ID
	 *        accountId	账户ID
	 * @return
	 */
	@RequestMapping("vendorSelectOrder.action")
	public Map<String, Object> vendorSelectOrder(String beginTime,String endTime,String orderNo,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,String nickName,Integer doorIndex,Integer accountId,Boolean isTest,String openId,Integer mainboardType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		return baseVendorManager.vendorSelectOrder(beginTime, endTime, orderNo, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName,nickName,doorIndex,accountId,isTest,openId,mainboardType, PageIndex, PageSize, IsSelectAll);
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
	public ResultVO vendorGisCommunityOperat(String tag,Integer communityId,Integer districtId,String location,String communityName,Integer eid,Integer accountId,String account,String remark) {
		if(tag==null||districtId==null||location==null||communityName==null||eid==null||accountId==null||account==null||(!tag.equals("ADD")&&communityId==null)) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String result = baseVendorManager.vendorGisCommunityOperat(tag, communityId,districtId,location, communityName, eid, accountId, account, remark);
		if(!"1".equals(result)){
			throw new ResultException(result);
		}
		return new ResultVO();
	}
	
	/**
	 * 56.查询省市区
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("findArea.action")
	public ResultVO findArea(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll,Integer provinceId) {
		if(districtId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> findArea = baseVendorManager.findArea(eid, cityId, districtId, isAudit, PageIndex, PageSize, IsSelectAll, provinceId);
		return new ResultVO(findArea);
	}
	
	/**
	 * 56.查询省市区
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("findArea_v2.action")
	public ResultVO findArea_v2(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll,Integer provinceId) {
		if(districtId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> findArea = baseVendorManager.findAreaByDistrictId(provinceId, cityId, districtId, isAudit, PageIndex, PageSize, IsSelectAll);
		return new ResultVO(findArea);
	}
	

	/**
	 * 57.广告价格查询
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("findAdLedPrices.action")
	public Map<String, Object> findAdLedPrices() {
		List<AdLedPrice> findAdLedPrices = baseVendorManager.findAdLedPrices();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adLedPrices", findAdLedPrices);
		return map;
	}
	
	/**
	 * 58.广告订单
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("vendorAdLedOrderSave.action")
	public ResultVO vendorAdLedOrderSave(Integer clientId,String adContent,String beginDate,Integer dayQuantity,String codeNo,Double orderMoney,String communityIds) {
		if(clientId==null||adContent==null||beginDate==null||dayQuantity==null||orderMoney==null||communityIds==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> findArea = baseVendorManager.vendorAdLedOrderSave(clientId, adContent, beginDate, dayQuantity,codeNo, orderMoney, communityIds);
		if(!"1".equals(findArea.get("tag"))){
			throw new ResultException((String) findArea.get("tag"));
		}
		return new ResultVO(findArea);
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
		List<Map<String, Object>> vendorAdLedOrderSelect = baseVendorManager.vendorAdLedOrderSelect(beginTime, endTime, orderId, isPay, orderStatus, orderNo, account, PageIndex, PageSize, IsSelectAll);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("vendorAdLedOrders", vendorAdLedOrderSelect);
		return resultMap;
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
		List<Map<String, Object>> vendorCodeAppCustomerSelect = baseVendorManager.vendorCodeAppCustomerSelect(eid, clientId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("vendorCodeAppCustomers", vendorCodeAppCustomerSelect);
		return resultMap;
	}
	
	/**
	 * 61.售货机信息
	 * 
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	@RequestMapping("selectMainboardBymainboardNo.action")
	public ResultVO selectMainboardBymainboardNo(String mainboardNo) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(mainboardNo==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<VendorMainboard2> selectMainboardBymainboardNo = baseVendorManager.selectMainboardBymainboardNo(mainboardNo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("nainboard", selectMainboardBymainboardNo);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 65.自动售货机小时结存统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("hourBalanceStatistics.action")
	public ResultVO hourBalanceStatistics(Integer searchType,String beginTime,String endTime,Integer eid,Integer productId,Integer mainboardId,Integer accountId,Integer communityId){
		if(searchType==null||beginTime==null||endTime==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> balanceProductidMainboardidAccountidCommunityidByhourSelect = baseVendorManager.balanceProductidMainboardidAccountidCommunityidByhourSelect(searchType, beginTime, endTime, eid, productId, mainboardId, accountId, communityId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("hourBalanceStatistics", balanceProductidMainboardidAccountidCommunityidByhourSelect);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 67.自动售货机小时结存统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorErrorLogSelect.action")
	public ResultVO vendorErrorLogSelect(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,String nickName,String eName,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		if(accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorErrorLogSelect = baseVendorManager.vendorErrorLogSelect(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, nickName, eName,isDispose, PageIndex, PageSize, IsSelectAll);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vendorErrorLogs", vendorErrorLogSelect);
		return new ResultVO(map);
	}
	
	/**
	 * 68.修改处理结果
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("updateErrResult.action")
	public ResultVO updateErrResult(Integer logId,Boolean isDispose,String disposeMsg) {
		if(logId==null||isDispose==null||disposeMsg==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Integer updateErrResult = baseVendorManager.updateErrResult(logId, isDispose, disposeMsg);
		if(updateErrResult.intValue()!=1) {
			// 修改失败
			return new ResultVO(ResultCode.CODE_ERROR_UPDATE);
		}
		return new ResultVO();
	}
	
	/**
	 * 69.app商户账户所管理社区
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("appMerchantAccountShopVendorSelect.action")
	public ResultVO appMerchantAccountShopVendorSelect(Integer accountId) {
		if(accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> appMerchantAccountShopVendorSelect = baseVendorManager.appMerchantAccountShopVendorSelect(accountId);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountShopVendor", appMerchantAccountShopVendorSelect);
		return new ResultVO(map);
	}
	
	/**
	 * 70.查询时间内访问信息(柱形报表点击)
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("viewVisitSelect.action")
	public Map<String, Object> viewVisitSelect(String beginTime,String endTime,String mainboardNoMD5,Boolean isTest,Integer communityId,Integer accountId,Integer shopId,Integer eid,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		List<Map<String, Object>> viewVisitSelect = baseVendorManager.viewVisitSelect(beginTime,endTime, mainboardNoMD5, isTest,communityId, accountId, shopId, eid,openId,PageIndex, PageSize, IsSelectAll);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("viewVisits", viewVisitSelect);
		return map;
	}
	
	/**
	 * 71.APP商户端补货统计
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("replenishmentStatistics.action")
	public Map<String, Object> replenishmentStatistics(@Valid ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO dto) {
		return baseVendorManager.replenishmentStatistics(dto);
	}
	
	/**
	 * 72.自动售货机补货员商品补充记录
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("productSupplementSelect.action")
	public Map<String, Object> productSupplementSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		return baseVendorManager.productSupplementSelect(beginTime, endTime, eid, account, productCode, productName,shopName,isTest, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 73.自动售货机补货员商品补充记录确认 补货入库
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("productSupplementConfirm.action")
	public ResultVO productSupplementConfirm(Integer supplementId,Integer eid,Integer accountId,Integer realQuantity,String remark,Integer productId) {
		if(supplementId==null||accountId==null||realQuantity==null||eid==null||productId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String tag = baseVendorManager.productSupplementConfirm(supplementId, eid, accountId, realQuantity, remark,productId);
		if("1".equals(tag)) {
			Map<String, Object> map =new HashMap<>();
			map.put("TAG", tag);
			return new ResultVO(map);
		}else {
			return new ResultVO(ResultCode.CODE_ERROR_SYSTEM);
		}
	}
	

	/**
	 * 74.自动售货机设备错误记录统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorErrorLogStatistics.action")
	public ResultVO vendorErrorLogStatistics(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		if(accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> vendorErrorLogStatistics = baseVendorManager.vendorErrorLogStatistics(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, isDispose, PageIndex, PageSize, IsSelectAll);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vendorErrorLogStatistics", vendorErrorLogStatistics);
		return new ResultVO(map);
	}
	
	/**
	 * 75.自动售货机用户购买统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("repetitionBuySelect.action")
	public Map<String, Object> repetitionBuySelect(String beginTime,String endTime,Boolean isSearchTimeForAuth,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer sortType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return baseVendorManager.repetitionBuySelect(beginTime, endTime, isSearchTimeForAuth, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, cellphone,sortType, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 76.自动售货机 货柜或商品购买统计
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorMainboardOrProductIdBuySelect.action")
	public Map<String, Object> vendorMainboardOrProductIdBuySelect(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen, Integer mainboardType, String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return baseVendorManager.vendorMainboardOrProductIdBuySelect(statisticsType, beginTime, endTime, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, mainboardType, cellphone, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 77.查询补货计划
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorReplenishmentPlan.action")
	public Map<String, Object> findVendorReplenishmentPlan(Integer eid,Integer shopId,Integer containerId,Integer accountId,Integer PageIndex, Integer PageSize, Boolean IsSelectAll){
		List<Map<String, Object>> vendorReplenishmentPlanSelect = baseVendorManager.vendorReplenishmentPlanSelect(eid,shopId, containerId, accountId,PageIndex, PageSize, IsSelectAll);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replenishmentPlans", vendorReplenishmentPlanSelect);
		return map;
	}
	
	/**
	 * 78.查询补货计划详情
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorReplenishmentPlanDetails.action")
	public Map<String, Object> findVendorReplenishmentPlanDetails(Integer planId,Integer mainboardId){
		List<Map<String, Object>> details = baseVendorManager.vendorReplenishmentPlanDetailsSelect(planId,mainboardId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replenishmentPlanDetails", details);
		return map;
	}
	
	/**
	 * 79.补货计划操作
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorReplenishmentPlanOper.action")
	public ResultVO vendorReplenishmentPlanOper(String oper,Integer planId,String mainboardIds,Integer shopId,Integer containerId,String planName,Boolean isShopAll,String creater,Integer warningNum,String productJsonArray){
		if(oper==null||(!"ADD".equals(oper)&&!"DEL".equals(oper)&&!"UPDATE".equals(oper)&&!"REPLACE".equals(oper))) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Integer> mIds = new ArrayList<Integer>();
		if(mainboardIds!=null&&!StringUtils.isEmpty(mainboardIds)) {
			String[] split = mainboardIds.split(",");
			for (String mIdstr : split) {
				int parseInt = Integer.parseInt(mIdstr);
				mIds.add(parseInt);
			}
		}

		if("ADD".equals(oper)){
			if((mIds.size()==0&&(shopId==null||containerId==null))||planName==null||isShopAll==null||creater==null||warningNum==null||productJsonArray==null) {
				return new ResultVO(ResultCode.CODE_ERROR_PARAM);
			}

			JSONArray parseArray = JSONArray.parseArray(productJsonArray);
			Integer result = baseVendorManager.insertVendorReplenishmentPlan(mIds,shopId,containerId, planName, isShopAll, creater, warningNum, parseArray);
			if(-1==result.intValue()) {
				// 货柜店铺或类型不相同
				return new ResultVO(ResultCode.CODE_VENDOE_SHOP_OR_TYPE_DIFFERENT);
			}
		}else if("DEL".equals(oper)) {
			if(planId==null) {
				return new ResultVO(ResultCode.CODE_ERROR_PARAM);
			}
			baseVendorManager.removeVendorReplenishmentPlan(planId);
		}else if("UPDATE".equals(oper)) {
			if(planId==null) {
				return new ResultVO(ResultCode.CODE_ERROR_PARAM);
			}
			JSONArray parseArray = JSONArray.parseArray(productJsonArray);
			baseVendorManager.updateVendorReplenishmentPlan(mIds, planId, planName, isShopAll, parseArray);
		}else if("REPLACE".equals(oper)) {
			if(mIds.size()==0||planId==null) {
				return new ResultVO(ResultCode.CODE_ERROR_PARAM);
			}
			baseVendorManager.updateVendorReplenishmentPlanByPlanId(mIds, planId);
		}
			
		return new ResultVO();
	}
	

	/**
	 * 80.售货机正在售卖中的商品中各种类的数量
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorSellProductKindNum.action")
	public ResultVO findVendorSellProductKindNum(Integer accountId){
		if(accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> details = baseVendorManager.selectVendorSellProductKindNum(accountId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("vendorProductNum", details);
		return new ResultVO(map);
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
		return baseVendorManager.vendorAppMerchantProductStorageDetailSelect(beginTime, endTime, eid, account, productCode, productName, shopName, isTest, remark,PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 82.在售商品统计根据商品
	 * @return
	 */
	@RequestMapping("onSaleCommodityStatisticsByProudct.action")
	public Map<String, Object> onSaleCommodityStatistics(Integer eid,Integer doorStatus,String productName,String productCode,String shortName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return baseVendorManager.vendorDoorSelectGroupByProductId(eid, doorStatus, productName, productCode, shortName, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 83.在售商品统计根据商品Id
	 * @return
	 */
	@RequestMapping("onSaleCommodityStatisticsProudctId.action")
	public Map<String, Object> onSaleCommodityStatistics(Integer eid,Integer doorStatus,Integer productId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return baseVendorManager.vendorDoorSelectGroupByProductIdAndMainboardId(eid, productId, doorStatus, PageIndex, PageSize, IsSelectAll);
	}
	

	/**
	 * 88.补货分类查询
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findVendorProductReplenishmentClass.action")
	public ResultVO findVendorProductReplenishmentClass(Integer eid){
		/*if(eid==null){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}*/
		List<VendorProductReplenishmentClass> details = baseVendorManager.selectVendorProductReplenishmentClass(eid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("replenishmentClass", details);
		return new ResultVO(map);
	}
	
	/**
	 * 90.查询开锁明细
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("findUnlockingByOrderId.action")
	public ResultVO findUnlockingByOrderId(Integer orderId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(orderId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> selectUnlockingByOrderId = baseVendorManager.selectUnlockingByOrderId(orderId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unlockingDetail", selectUnlockingByOrderId);
		return new ResultVO(map);
	}
	
	/**
	 * 90.查询开锁明细
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("allOnLineMainboards.action")
	public String allOnLineMainboards(){
		return baseVendorManager.allOnLineMainboards();
	}
	
	/**
	 * 107.售货机订单退款申请
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("vendorOrderAppleFor.action")
	public ResultVO vendorOrderAppleFor(Integer orderId,Double applyRefundMoney,String explain,String cellphone,String openId){
		Map<String, Object> refundApply = baseVendorManager.refundApply(orderId,applyRefundMoney, explain, cellphone, openId);
		String tag = (String) refundApply.get("TAG");
		if("1".equals(tag)) {
			Map<String, Object> map =new HashMap<>();
			map.put("TAG", tag);
			return new ResultVO(map);
		}else {
			return new ResultVO(ResultCode.CODE_ERROR_SYSTEM);
		}
	}


	/**
	 * 150.自动售货机 货柜或商品购买统计
	 *
	 *
	 * @return
	 */
	@RequestMapping("findVendorOrProductBuyStatistics.action")
	public ResultVO findVendorOrProductBuyStatistics(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,Integer mainboardType,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		if(statisticsType==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> vendorOrProductBuyStatistics = baseVendorManager.Proc_Backstage_vendor_order_select_groupByMainboardIdOrProductId(statisticsType, beginTime, endTime, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, mainboardType, cellphone, PageIndex, PageSize, IsSelectAll);
		return new ResultVO(vendorOrProductBuyStatistics);
	}

	/**
	 *	163.补货记录查询
	 * @param beginTime
	 * @param endTime
	 * @param eid
	 * @param account
	 * @param productCode
	 * @param productName
	 * @param isTest
	 * @param containerCode
	 * @param doorIndex
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	@RequestMapping("replenishmentRecordSelect.action")
	public Map<String,Object> replenishmentRecordSelect(ReplenishmentRecordSelectReqDTO dto){
		return baseVendorManager.Proc_Backstage_vendor_replenishment_select(dto);
	}

	/**
	 *	171.补货
	 * @param beginTime
	 * @param endTime
	 * @param eid
	 * @param account
	 * @param productCode
	 * @param productName
	 * @param isTest
	 * @param containerCode
	 * @param doorIndex
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	@RequestMapping("shelfReplenishmentOper.action")
	public ResultVO shelfReplenishmentOper(Integer eid,Integer mainboardId,Integer productId,Integer productQuantity,Integer outProductId,Integer outProductQuantity,String account,Integer typeId, String typeName,String location){
		if(eid==null||mainboardId==null||productId==null||productQuantity==null||outProductId==null||outProductQuantity==null||account==null||typeId==null||typeName==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String tag = baseVendorManager.Proc_Backstage_vendor_AppCustomer_DoorReplenishment_Stand(eid,mainboardId,productId,productQuantity,outProductId,outProductQuantity,account,typeId, typeName,location);
		if(!"1".equals(tag)){
			throw new ResultException(tag);
		}
		return new ResultVO();
	}

	/**
	 * 175.批量校验开锁
	 *
	 * @param clientId
	 *            商户ID
	 * @return
	 */
	@RequestMapping("doorInitValidByBatch.action")
	public ResultVO doorInitValidByBatch(Integer mainboardId,Integer eid,String doorList,String mainboardNo) {
		if(eid==null||mainboardId==null||doorList==null||mainboardNo==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		try {
			JSONArray objects = JSONArray.parseArray(doorList);
			for (int i=0;i<objects.size();i++){
				JSONObject jsonObject = objects.getJSONObject(i);
				if(jsonObject.isEmpty()||jsonObject.get("doorIndex")==null||jsonObject.get("portNo")==null){
					return new ResultVO(ResultCode.CODE_ERROR_PARAM);
				}
			}
		} catch (Exception e) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String doorInitValid = baseVendorManager.doorInitValid(mainboardId,mainboardNo,eid, doorList);
		JSONObject parseObject = JSON.parseObject(doorInitValid);
		return new ResultVO(parseObject.getIntValue("code"),parseObject.getString("message"));
	}

	/**
	 * 177.赔偿积分
	 * @param eid
	 * @param orderId
	 * @param accountId
	 * @return
	 */
	@RequestMapping("compensateIntegral.action")
	public ResultVO compensateIntegral(Integer eid,Integer orderId,Integer accountId){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null||orderId==null||accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String tag = baseVendorManager.Proc_Backstage_order_compensateIntegral(eid,orderId,accountId);
		if(!"1".equals(tag)){
			throw new ResultException(tag);
		}
		return new ResultVO();
	}

	/**
	 * 178.修改缺货预警数值
	 * @param planId
	 * @param warningNum
	 * @return
	 */
	@RequestMapping("updateStockoutWarning.action")
	public ResultVO updateStockoutWarning(Integer planId,Integer warningNum){
		if(planId==null||warningNum==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Integer integer = baseVendorManager.updateStockoutWarning(planId, warningNum);
		if(1!=integer){
			return new ResultVO(ResultCode.CODE_ERROR_UPDATE);
		}
		return new ResultVO();
	}

	/**
	 * 179.补货类型
	 *
	 *
	 * @return
	 */
	@RequestMapping("replenishmentType.action")
	public ResultVO replenishmentType(Integer eid){
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> replenishmentType = baseVendorManager.Proc_Backstage_vendor_replenishment_type_select(eid);
		Map<String,Object> data = new HashMap<>();
		data.put("replenishmentTypes",replenishmentType);
		return new ResultVO(data);
	}
	/**
	 * 180.自动售货机补货类型统计
	 *
	 *
	 * @return
	 */
	@RequestMapping("replenishmentTypeStatistics.action")
	public ResultVO replenishmentTypeStatistics(Integer statisticsType,String beginTime,String endTime,Integer eid,String mainboardNo,Integer operatType,Integer shopId	,Boolean isTest,Integer mainboardType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		if(statisticsType==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> replenishmentType = baseVendorManager.Proc_Backstage_vendor_replenishment_type_select_statistics(statisticsType, beginTime, endTime, eid, mainboardNo, operatType, shopId, isTest, mainboardType, PageIndex, PageSize, IsSelectAll);
		Map<String,Object> data = new HashMap<>();
		data.put("replenishmentTypeStatistics",replenishmentType);
		return new ResultVO(data);
	}

	/**
	 * 查询驿站关注领取活动
	 * @param eid
	 * @param openId
	 * @param clientId
	 * @return
	 */
	@RequestMapping("findVendorCouponToWechatActivity")
	public ResultVO findVendorCouponToWechatActivity(Integer eid,String openId,Integer clientId){
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> data = baseVendorManager.selectVendorCouponToWechatActivityByUser(eid, openId, clientId);
		return new ResultVO(data);
	}
	/**
	 * 驿站创建临时用户
	 * @param eid
	 * @param openId
	 * @param clientId
	 * @return
	 */
	@RequestMapping("creationTempClientId")
	public ResultVO creationTempClientId(Integer eid,String openId,String appId,String unionId) throws Exception{
		if(openId==null||appId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> data = baseVendorManager.creationTempClientId(eid, openId, appId, unionId);
		return new ResultVO(data);
	}
	/**
	 * 查询驿站临时用户和水趣注册用户
	 * @param eid
	 * @param openId
	 * @param clientId
	 * @return
	 */
	@RequestMapping("vendorLoginSelect")
	public ResultVO vendorLoginSelect(String openId){
		if(openId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> data = baseVendorManager.vendorLoginSelect(openId);
		return new ResultVO(data);
	}

	/**
	 * 查询红包发送供水趣公众号关注时用
	 * @param eid
	 * @param openId
	 * @param clientId
	 * @return
	 */
	@RequestMapping("vendorQueueSendPacket")
	public String vendorQueueSendPacket(Integer orderId,String openId,Integer typeId,String authorizer_appid){
		String parMap = CallBackPar.getParString();
		baseVendorManager.vendorQueueSendPacket(orderId, openId, typeId, authorizer_appid);
		return parMap;
	}

	/**
	 * 202.记录补货员扫码位置
	 * @return
	 */
	@RequestMapping("recordStaffScanCodeLocation.action")
	public ResultVO recordStaffScanCodeLocation(@Valid VendorStaffScanCodeLocationReqDTO dto){
		int i = baseVendorManager.recordStaffScanCodeLocation(dto);
		if(1!=i){
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 203.查询补货员扫码补货位置轨迹
	 * @return
	 */
	@RequestMapping("staffScanLocationSelect.action")
	public Map<String,Object> staffScanLocationSelect(ProcXHXStaffScanLocationSelectReqDTO dto){
		return baseVendorManager.staffScanLocationSelect(dto);
	}

	/**
	 * 212.记录柜门图片状态
	 * @return
	 */
	@RequestMapping("recordVendorStatusImage.action")
	public ResultVO recordVendorStatusImage(@Valid InsertVendorStatusImageRecordInputDTO dto,@RequestParam("statusImage") MultipartFile statusImage) throws IOException {
		if(statusImage==null){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		int update = baseVendorManager.insertVendorStatusImageRecord(dto,statusImage);
		if(update!=1){
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 213.查询柜门图片状态记录
	 * @return
	 */
	@RequestMapping("vendorStatusImageRecordSelect.action")
	public Map<String,Object> vendorStatusImageRecordSelect(ProcXhxVendorStatusImageRecordSelect dto){
		return baseVendorManager.Proc_XHX_vendor_status_image_record_select(dto);
	}

	/**
	 * 220.操作送货上门商品
	 * @return
	 */
	@RequestMapping("operSingleProduct.action")
	public ResultVO operSingleProduct(@Valid SingleProduct product,@RequestParam("productImage") MultipartFile... productImage) throws IOException {
		if(product.getProductId()==null&&productImage.length==0){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		int count = baseVendorManager.operSingleProduct(product,productImage.length>0?productImage[0]:null);
		if(count==0){
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 221.查询送货上门商品
	 * @return
	 */
	@RequestMapping("selectSingleProduct.action")
	public Map<String,Object> selectSingleProduct(@Valid ProcBackstageSingleProductSelectReqDTO dto){
		return baseVendorManager.selectSingleProducts(dto);
	}

	/**
	 * 222.送货到家订单查询
	 * @param dto
	 * @return
	 */
	@RequestMapping("singleProductOrderSelect.action")
	public Map<String,Object> singleProductOrderSelect(ProcBackstageSingleOrderSelectReqDTO dto){
		return baseVendorManager.Proc_Backstage_single_order_select(dto);
	}

	/**
	 * 223.送货到家商品上下架
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("updateSingleProductHitTheShelf.action")
	public ResultVO updateSingleProductHitTheShelf(Boolean hitTheShelf,Integer productId){
		if(hitTheShelf==null||productId==null){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		baseVendorManager.updateSingleProductHitTheShelf(hitTheShelf, productId);
		return new ResultVO();
	}

	/**
	 * 224.送货到家商品删除
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("deleteSingleProduct.action")
	public ResultVO deleteSingleProduct(Integer productId){
		if(productId==null){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		baseVendorManager.deleteSingleProduct( productId);
		return new ResultVO();
	}

	/**
	 * 225.上传驿站支付子商户号申请
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("addPaySubAccount.action")
	public ResultVO addPaySubAccount(@Valid EWechatServicepaySubaccountApplyInputDTO dto){

		baseVendorManager.insertEWechatServicepaySubaccountApply(dto);
		return new ResultVO();
	}

	/**
	 * 226.查询驿站支付子商户号申请
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("findPaySubAccount.action")
	public ResultVO findPaySubAccount(Integer accountId){
		if(accountId==null){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<EWechatServicepaySubaccountApplyRepDTO> ePaySubAccounts = baseVendorManager.selectEWechatServicepaySubaccountApplyByAccount(accountId);
		Map result = new HashMap(1);
		if(ePaySubAccounts.size()>0){
			result.put("paySub",ePaySubAccounts.get(0));
		}else{
			result.put("paySub","");
		}
		return new ResultVO(result);
	}


	/**
	 * 227.修改驿站支付子商户号申请
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("updatePaySubAccount.action")
	public ResultVO updatePaySubAccount(@Valid EWechatServicepaySubaccountApplyInputDTO dto){

		baseVendorManager.updateEWechatServicepaySubaccountApply(dto);
		return new ResultVO();
	}

	/**
	 * 228.匹配支付银行
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("matchingApplyBank.action")
	public Map<String,Object> matchingApplyBank(String bankName, PagingPojo pojo){
		if(StringUtils.isBlank(bankName)){
			Map<String,Object> result = new HashMap();
			result.put("bankData",Arrays.asList());
			return result;
		}
		Map<String, Object> map = baseVendorManager.selectEWechatServicepaySubaccountApplyBank(bankName, pojo);
		return map;
	}

	/**
	 * 229.省市区
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("applyProvince.action")
	public List<EWechatServicepaySubaccountApplyProvinceRepDTO> applyProvince(){
		List<EWechatServicepaySubaccountApplyProvinceRepDTO> result = baseVendorManager.selectEWechatServicepaySubaccountApplyProvince();
		return result;
	}

	/**
	 * 230.支付子商户号申请图片上传
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("subaccountApplyImgUpload.action")
	public Map<String,Object> subaccountApplyImgUpload(@RequestParam("img") MultipartFile... img) throws Exception {
		if(img.length==0){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
		}
		return baseVendorManager.subaccountApplyImgUpload(img[0]);
	}

	/**
	 * 231.匹配微信支付银行
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("wechatMatchingApplyBank.action")
	public Map<String,Object> wechatMatchingApplyBank(String bankName, PagingPojo pojo){
		if(StringUtils.isBlank(bankName)){
			Map<String,Object> result = new HashMap();
			result.put("bankData",Arrays.asList());
			return result;
		}
		Map<String, Object> map = baseVendorManager.selectEWechatServicepaySubaccountApplyBankname(bankName, pojo);
		return map;
	}

	/**
	 * 232.修改送货到家订单状态
	 * @param hitTheShelf
	 * @param productId
	 * @return
	 */
	@RequestMapping("updateSingleOrderStatus.action")
	public ResultVO updateSingleOrderStatus(Integer orderStatus,Integer orderId){

		int count = baseVendorManager.updateSingleOrderStatus(orderStatus, orderId);
		if(count!=1){
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 233.添加跳转水趣商品
	 * @param skipShuiqooProduct
	 * @return
	 */
	@RequestMapping("addSkipShuiqooProduct.action")
	public ResultVO addSkipShuiqooProduct(@Valid SkipShuiqooProduct skipShuiqooProduct){
		baseVendorManager.addSkipShuiqooProduct(skipShuiqooProduct);
		return new ResultVO();
	}

	/**
	 * 234.删除跳转水趣商品
	 * @param id
	 * @return
	 */
	@RequestMapping("removeSkipShuiqooProduct.action")
	public ResultVO removeSkipShuiqooProduct(Integer id){
		baseVendorManager.removeSkipShuiqooProduct(id);
		return new ResultVO();
	}

	/**
	 * 235.查询跳转水趣商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("findSingleProductsByEid.action")
	public Map<String,Object> findSingleProductsByEid(@Valid SkipShuiqooProductReqDTO dto){
		return baseVendorManager.selectSkipShuiqooProductRepByEid(dto);
	}

	/**
	 * 243.新增售货机商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("addVendorProduct.action")
	public ResultVO addVendorProduct(@Valid AddVendorProductReqDTO dto){
		int i = baseVendorManager.insertVendorProduct(dto);
		if(1!=i){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 244.删除售货机商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("removeVendorProduct.action")
	public ResultVO removeVendorProduct(Integer id){
		if(id==null){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		int i = baseVendorManager.deleteSingleProduct(id);
		if(1!=i){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_OPERATION);
		}
		return new ResultVO();
	}

	/**
	 * 245.修改售货机商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateVendorProduct.action")
	public ResultVO updateVendorProduct(@Valid UpdateVendorProductReqDTO dto){

		int i = baseVendorManager.updateVendorProduct(dto);
		if(1!=i){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_UPDATE);
		}
		return new ResultVO();
	}

	/**
	 * 246.根据补货员查询售货机商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("findVendorProductByAccountId.action")
	public Map<String,Object> findVendorProductByAccountId(@Valid FindVendorProductByAccountIdReqDTO dto){
		return baseVendorManager.selectVendorProductByAccountId(dto);
	}

	/**
	 * 248.操作补货员常用商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("operVendorAppMerchantAccountProduct.action")
	public ResultVO operVendorAppMerchantAccountProduct(@Valid VendorAppMerchantAccountProduct product){
		int i = baseVendorManager.operVendorAppMerchantAccountProduct(product);
		if(i>0){
			return new ResultVO();
		}else{
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
	}

	/**
	 * 249.删除补货员常用商品
	 * @param dto
	 * @return
	 */
	@RequestMapping("removeVendorAppMerchantAccountProduct.action")
	public ResultVO removeVendorAppMerchantAccountProduct(@Valid VendorAppMerchantAccountProduct product){
		int i = baseVendorManager.removeVendorAppMerchantAccountProduct(product);
		if(i>0){
			return new ResultVO();
		}else{
			return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
		}
	}
}
