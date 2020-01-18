package com.evian.sqct.dao;

import com.evian.sqct.bean.vendor.*;

import java.util.List;
import java.util.Map;

public interface IVendorDao { 

	public String verndorContainerOperat(Integer vmId,Integer bmId,Integer doorNumber,Integer vmIndex,String createdUser,String TAG);
	
	@Deprecated
	public List<VendorContainer> selectContainerByBmId(Integer bmId);
	
	public List<VendorDoor> selectDoorAndProuct(Integer mainboardId,Integer eid,Integer doorIndex,Integer classId,String beginTime,String endTime,String account,String containerCode,Integer doorReplenishmentDays,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	public Map<String, Object> selectDoorAndProuct_v2(Integer mainboardId,Integer eid,Integer replenishmentClassId,Integer PageIndex,Integer PageSize);
	
	@Deprecated
	public Map<String, Object> mainboardBindContainer(String mainboardNo,String containerCode,Integer eid);
	
	public VendorMainboardInstruct selectmainboardInstructByBmIdByDoorIndex(Integer bmId,Integer doorIndex);
	
	public String verificationVendorDoor(Integer mainboardId,Integer doorIndex,Integer portNo,String alias,Integer doorStatus,String breakDownRemark);
	
	public List<VendorShopContainer> VendorManage(Integer shopId);
	
	public List<Map<String,Object>> AppCustomerMainboardSelect(Integer eid,Integer shopId,Integer containerStatus,Integer accountId,String mainboardNoMD5);
	
	public String doorReplenishment(Integer eid,Integer mainboardId,Integer doorIndex,Integer productId,String account,Integer operatType,Integer wxId);
	
	public List<Map<String, Object>> vendorProductSelect(Integer id,String beginTime,String endTime,String productName,Integer eid,Boolean isLine,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);

	public List<Map<String, Object>> vendorDoorProductStatistics(Integer staffId,Integer productState,Boolean isAll);
	
	public String vendorAppCustomerConfirmReceipt(Integer eid,String guidStr);
	
	public List<Map<String,Object>> vendorAppCustomerDoorProduct_statistics(Integer eid,Integer accountId,Boolean isAll);
	
	public List<Map<String,Object>> vendorAppCustomerDoorstatistics(Integer eid,Integer accountId);
	
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerstatistics(Integer eid,Integer accountId);
	
	public String vendorAppCustomerMainboardContainerstatus(Integer mainboardId,Integer accountId,Integer containerStatus, String remark);
	
	public String vendorAppCustomerMainboardContainerAddressEdit(Integer mainboardId,Integer accountId,String shopContainerName, String location, String containerAddress,Integer communityId) ;
	
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerDoorStatistics(Integer eid,Integer accountId);
	
	public Map<String,Object> vendorSelectOrder(String beginTime,String endTime,String orderNo,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,String nickName,Integer doorIndex,Boolean isTest,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	public Map<String,Object> vendorBatchDoorOpenEmpty(Integer mainboardId,Integer eid);
	
	public String getNowDbName();
	
	public String vendorGisCommunityOperat(String tag,Integer communityId,Integer districtId,String location,String communityName,Integer eid,Integer accountId,String account,String remark);
	
	/**
	 * 查询全部省份
	 * @return
	 */
	public List<GisProvince> findAllProvince();
	
	/**
	 * 查询全部城市
	 * @return
	 */
	public List<GisCity> findAllCity();
	
	/**
	 * 查询全部区域
	 * @return
	 */
	public List<GisDistrict> findAllDistrict();
	
	/**
	 * 查询全部社区
	 * @return
	 */
	public List<GisCommunity> findAllCommunity();
	
	/**
	 * 根据省份查询城市
	 * @return
	 */
	public List<GisCity> findCityByProvince(Integer provinceId);
	
	/**
	 * 根据城市查询区
	 * @return
	 */
	public List<GisDistrict> findDistrictByCity(Integer cityId);
	
	/**
	 * 根据区域查询社区
	 * @return
	 */
	public List<Map<String,Object>> findCommunityBydistrict(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	public Map<String, Object> vendorAdLedOrderSave(Integer clientId,String adContent,String beginDate,Integer dayQuantity,Double orderMoney,Double discountMonty,String codeNo,Double finalMoney,String communityNameJson);


	/**
	 * 根据社区Ids 查询社区信息
	 * @param 
	 */
	public List<GisCommunity> findCommunityByCommunityId(String CommunityIds);
	
	public List<AdLedPrice> findAdLedPrices();
	
	/**
	 * 查询广告价格
	 * @param charQuantity
	 * @return
	 */
	public List<AdLedPrice> findAdLedPricesByCharQuantity(int charQuantity);
	
	public List<Map<String, Object>> vendorAdLedOrderSelect(String beginTime,String endTime,Integer orderId,Boolean isPay,Integer orderStatus,String orderNo,String account,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 自动售货机用户代金券查询
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	public List<Map<String, Object>> vendorCodeAppCustomerSelect(Integer eid,Integer clientId);
	
	public List<VendorMainboard2> selectMainboardBymainboardNo(String mainboardNo);
	
	public List<Map<String, Object>> balanceProductidMainboardidAccountidCommunityidByhourSelect(Integer searchType,String beginTime,String endTime,Integer eid,Integer productId,Integer mainboardId,Integer accountId,Integer communityId);
	
	List<VendorMainboardContainer> selectAllMainboardContainer();
	
	List<Map<String, Object>> vendorErrorLogOperat(Integer eid,Integer mainboardId,Integer dataSource,String exceptionMsg,String exceptionCode,String openId);
	
	List<Map<String, Object>> vendorErrorLogSelect(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,String nickName,String eName,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	Integer updateErrResult(Integer logId,Boolean isDispose,String disposeMsg);
	
	List<Map<String, Object>> appMerchantAccountShopVendorSelect(Integer accountId);
	
	List<Map<String, Object>> viewVisitSelect(String beginTime,String endTime,String mainboardNoMD5,Boolean isTest,Integer communityId,Integer accountId,Integer eid,String openId, Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	Map<String, Object> replenishmentStatistics(Integer eid,String beginTime,String endTime,String account,String productName,String productCode);
	
	Map<String, Object> productSupplementSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest, Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	String productSupplementConfirm(Integer supplementId,Integer eid,Integer accountId,Integer realQuantity,String remark,Integer productId);
	
	List<Map<String, Object>> vendorErrorLogStatistics(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	Map<String, Object> repetitionBuySelect(String beginTime,String endTime,Boolean isSearchTimeForAuth,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer sortType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	Map<String, Object> vendorMainboardOrProductIdBuySelect(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	List<Map<String, Object>> vendorReplenishmentPlanSelect(Integer eid,Integer shopId,Integer containerId,Integer accountId,Integer PageIndex, Integer PageSize, Boolean IsSelectAll);
	
	List<Map<String, Object>> vendorReplenishmentPlanDetailsSelect(Integer planId,Integer mainboardId);
	
	List<Map<String, Object>> vendorReplenishmentPlanSelectByPlanId(Integer planId);
	
	/**
	 * 插入
	 * @param eid
	 * @param containerId
	 * @param shopId
	 * @param planName
	 * @param doorNum
	 * @param isShopAll
	 * @param creater
	 * @return
	 */
	Integer insertVendorReplenishmentPlan(Integer eid,Integer containerId,Integer shopId,String planName,Integer doorNum,Boolean isShopAll,String creater);
	
	Integer removeUpdateVendorReplenishmentPlan(Integer planId);
	
	Integer updateVendorReplenishmentPlan(Integer planId,String planName,Boolean isShopAll);
	
	Integer insertvendorReplenishmentPlanItem(Integer planId,Integer sortId,Integer productId,Integer planNum);
	
	Integer removeVendorReplenishmentPlanItem(Integer planId);
	
	List<Map<String, Object>> vendorReplenishmentPlanMappingSelect(Integer mainboardId,Integer planId);
	
	/**
	 * 查询货柜不通用的补货计划
	 * @param mainboardId
	 * @return
	 */
	List<Map<String, Object>> vendorReplenishmentPlanMappingSelectNoShopId(Integer mainboardId);
	
	Integer insertVendorReplenishmentPlanMapping(Integer mainboardId,Integer planId,String creater);
	
	Integer updateVendorReplenishmentPlanMapping(Integer mainboardId,Integer planId);
	
	Integer removeVendorReplenishmentPlanMapping(Integer planId);
	
	VendorContainer selectVendorContainerByMainboardId(Integer mainboardId);
	
	/**
	 * 查询售货机售卖商品种类的数量
	 * @param mainboardId
	 * @return
	 */
	List<Map<String, Object>> selectVendorSellProductKindNum(Integer mainboardId);
	
	List<Map<String, Object>> selectVendorContainerByAccountId(Integer accountId);
	
	VendorContainerTemplate selectVendorContainerTemplateByContainerId(Integer containerId);
	
	/**
	 * 自动售货机补货员商品库存往来明细
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
	Map<String, Object> vendorAppMerchantProductStorageDetailSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest,String remark,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 在售商品统计根据商品名称
	 * @param eid
	 * @param doorStatus
	 * @param productName
	 * @param productCode
	 * @param shortName
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	Map<String, Object> vendorDoorSelectGroupByProductId(Integer eid,Integer doorStatus,String productName,String productCode,String shortName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 在售商品统计根据商品Id
	 * @param eid
	 * @param productId
	 * @param doorStatus
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	Map<String, Object> vendorDoorSelectGroupByProductIdAndMainboardId(Integer eid,Integer productId,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 补货奖品查询
	 * @param mainboardId
	 * @return
	 */
	List<Map<String, Object>> eDrawWinrecord(Integer mainboardId);
	
	/**
	 * 补货分类查询
	 * @return
	 */
	List<VendorProductReplenishmentClass> selectVendorProductReplenishmentClass();
	
	/**
	 * 查询当前订单开锁状态
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> selectUnlockingByOrderId(Integer orderId);
	
	/**
	 * 2018-09-11
	 * 退款申请
	 * @param orderId 	 	    客户订单id
	 * @param applyRemark 	    用户提交的申请说明
	 * @param cellphone  	    用户提交的手机号
	 * @param openId  		    用户Id
	 * @return
	 */
	Map<String, Object> refundApply(Integer orderId,Double applyRefundMoney,String applyRemark,String cellphone,String openId);

	Map<String, Object> Proc_Backstage_vendor_order_select_groupByMainboardIdOrProductId(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);

	Map<String, Object> Proc_Backstage_vendor_replenishment_select(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,Boolean isTest,String containerCode,Integer doorIndex,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);

	/**
	 * 根据广告订单id 查询售货机管理人员
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> selectVendorManagementByLEDOrderId(Integer orderId);
}
