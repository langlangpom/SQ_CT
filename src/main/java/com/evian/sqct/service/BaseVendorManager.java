package com.evian.sqct.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.annotation.DataNotLogCheck;
import com.evian.sqct.annotation.LogNotCheck;
import com.evian.sqct.bean.app.APPUpgraderSelectObject;
import com.evian.sqct.bean.baseBean.PagingPojo;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.vendor.*;
import com.evian.sqct.bean.vendor.input.*;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyBankRepDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyBanknameReqDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyProvinceRepDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyRepDTO;
import com.evian.sqct.bean.wechat.image.WechatV3mediaUploadRepDTO;
import com.evian.sqct.dao.IEnterpriseDao;
import com.evian.sqct.dao.IShopDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.dao.mybatis.vendorDataSource.dao.ISkipShuiqooProductDao;
import com.evian.sqct.dao.mybatis.vendorDataSource.dao.IVendorMapperDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.DESUser.UserDes;
import com.evian.sqct.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseVendorManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("vendorDao")
	private IVendorDao vendorDao;
	
	@Autowired
	@Qualifier("shopDao")
	private IShopDao shopDao;

	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;

	@Autowired
	@Qualifier("enterpriseDao")
	private IEnterpriseDao enterpriseDao;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private IWeixinApiService weixinApiService;

	@Autowired
	private ISkipShuiqooProductDao skipShuiqooProductDao;

	@Autowired
	private IVendorMapperDao vendorMapperDao;
	
	public String verndorContainerADD(Integer vmId,Integer bmId,Integer doorNumber,Integer vmIndex,String createdUser){
		return vendorDao.verndorContainerOperat(vmId, bmId, doorNumber, vmIndex, createdUser, "ADD");
	}
	
	@Deprecated
	public Map<String, Object> findContainerByBmId(Integer bmId){
		/*List<VendorContainer> containers = vendorDao.selectContainerByBmId(bmId);
		if(containers.size()>0){
			VendorContainer container = containers.get(0);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("bmId", container.getBmId());
			map.put("vmId", container.getVmId());
			map.put("doorNumber", container.getDoorNumber());
			map.put("vmIndex", container.getVmIndex());
			map.put("dateCreated", container.getDateCreated());
			map.put("createdUser", container.getCreatedUser());
			return map;
		}else{
			return null;
		}*/
		return null;
	}

	@DataNotLogCheck
	public List<VendorDoor> findDoorAndProuct(Integer bmId,Integer vmId,Integer doorIndex,Integer classId,String beginTime,String endTime,String account,String containerCode,Integer doorReplenishmentDays,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.selectDoorAndProuct(bmId, vmId,doorIndex, classId, beginTime, endTime, account, containerCode, doorReplenishmentDays, doorStatus, PageIndex, PageSize, IsSelectAll);
	}

	@DataNotLogCheck
	public Map<String,Object> findDoorAndProuct_v2(ProcBackstageVendorProductSelectReqDTO dto){
		return vendorDao.selectDoorAndProuct_v2(dto);
	}
	
	@Deprecated
	public Map<String, Object> mainboardBindContainer(String mainboardNo,String containerCode,Integer eid){
		return vendorDao.mainboardBindContainer(mainboardNo, containerCode, eid);
	}
	
	public Boolean findmainboardInstructByBmIdByDoorIndex(Integer bmId, Integer doorIndex) {
		VendorMainboardInstruct vndorMainboardInstruct = vendorDao.selectmainboardInstructByBmIdByDoorIndex(bmId, doorIndex);
		if(vndorMainboardInstruct!=null){
			return vndorMainboardInstruct.getInstructSuc();
		}else{
			return false;
		}
	}
	
	public String verificationVendorDoor(Integer mainboardId,Integer doorIndex,Integer portNo,String alias,Integer doorStatus,String breakDownRemark){
		return vendorDao.verificationVendorDoor(mainboardId, doorIndex,portNo, alias,doorStatus,breakDownRemark);
	}
	
	public List<VendorShopContainer> VendorManage(Integer shopId){
		return vendorDao.VendorManage(shopId);
	}

	@DataNotLogCheck
	public List<Map<String,Object>> AppCustomerMainboardSelect(Integer eid,Integer shopId,Integer containerStatus,Integer accountId,String mainboardNoMD5,Integer communityId){
		return vendorDao.AppCustomerMainboardSelect(eid, shopId, containerStatus, accountId, mainboardNoMD5,communityId);
	}
	
	public String doorReplenishment(Integer eid,Integer mainboardId,Integer doorIndex,Integer productId,String account, Integer typeId, String typeName,Integer wxId,String location){
		return vendorDao.doorReplenishment(eid, mainboardId,doorIndex,productId,account,typeId,typeName,wxId,location);
	}
	
	public List<Map<String, Object>> vendorProductSelect(String productName,Integer eid,Boolean isLine){
		return vendorDao.vendorProductSelect(null, null, null, productName, eid, isLine, null, null, true);
	}
	
	public List<Map<String, Object>> vendorDoorProductStatistics(Integer staffId,Integer productState,Boolean isAll){
		return vendorDao.vendorDoorProductStatistics(staffId,productState,isAll);
	}
	
	public String doorInitValid(Integer mainboardId,String mainboardNo,Integer eid,Integer doorIndex,Integer portNo){
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		JSONArray doorList = new JSONArray();
		JSONObject door = new JSONObject();
		door.put("doorIndex", doorIndex);
		door.put("portNo", portNo);
		doorList.add(door);
		params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
		params.add(new BasicNameValuePair("doorList", doorList.toString()));
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/replenishmentEmptyOpenDoor.action", params);
		logger.info("postEvianApi:{}",postEvianApi);
		return postEvianApi;
	}

	public String doorInitValid(Integer mainboardId,String mainboardNo,Integer eid,String doorList){
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
		params.add(new BasicNameValuePair("doorList", doorList));
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/replenishmentEmptyOpenDoor.action", params);
		logger.info("postEvianApi:{}",postEvianApi);
		return postEvianApi;
	}
	
	public String batchOpenEmptyDoor(Integer mainboardId,String mainboardNo,Integer eid){
		Map<String, Object> result = vendorBatchDoorOpenEmpty(mainboardId, eid);
		logger.info("---result:{}",result);
		JSONArray doorList = new JSONArray();
			List<VendorDoor> findDoorAndProuct = findDoorAndProuct(mainboardId, eid, null,0,null,null,null,null,null,null,null,null,true);
			logger.info("---doors:{}",findDoorAndProuct);
			for (VendorDoor vendorDoor : findDoorAndProuct) {
				if(vendorDoor.getDoorStatus().intValue()==2){
					JSONObject door = new JSONObject();
					door.put("doorIndex", vendorDoor.getDoorIndex());
					door.put("portNo", vendorDoor.getPortNo());
					doorList.add(door);
				}
			}
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
		params.add(new BasicNameValuePair("doorList", doorList.toString()));
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/replenishmentEmptyOpenDoor.action", params);
		logger.info("postEvianApi:{}",postEvianApi);
		return postEvianApi;
	}
	
	public String mainboardLine(Integer mainboardId,String mainboardNo){
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/mainboardHashLine.action", params);
		return postEvianApi;
	}
	
	public String vendorAppCustomerConfirmReceipt(Integer eid,String guidStr){
		return vendorDao.vendorAppCustomerConfirmReceipt(eid, guidStr);
	}
	
	public List<Map<String,Object>> vendorAppCustomerDoorProductStatistics(Integer eid,Integer accountId,Boolean isAll){
		return vendorDao.vendorAppCustomerDoorProduct_statistics(eid, accountId,isAll);
	}
	
	public List<Map<String,Object>> vendorAppCustomerDoorstatistics(Integer eid,Integer accountId,Integer shopId){
		return vendorDao.vendorAppCustomerDoorstatistics(eid, accountId,shopId);
	}
	
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerstatistics(Integer eid,Integer accountId){
		return vendorDao.vendorAppCustomerMainboardContainerstatistics(eid, accountId);
	}
	
	public String vendorAppCustomerMainboardContainerstatus(Integer mainboardId,Integer accountId,Integer containerStatus, String remark){
		return vendorDao.vendorAppCustomerMainboardContainerstatus(mainboardId, accountId, containerStatus, remark);
	}
	
	public String vendorAppCustomerMainboardContainerAddressEdit(Integer mainboardId,Integer accountId,String shopContainerName, String location, String containerAddress, Integer communityId) {
		return vendorDao.vendorAppCustomerMainboardContainerAddressEdit(mainboardId, accountId, shopContainerName, location, containerAddress,communityId);
	}

	@DataNotLogCheck
	public Map<String,Object> vendorAppCustomerMainboardContainerDoorStatistics(VendorDoorStatisticsDTO dto) {
		return vendorDao.vendorAppCustomerMainboardContainerDoorStatistics(dto);
	}
	
	public Map<String,Object> vendorSelectOrder(String beginTime,String endTime,String orderNo,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,String nickName,Integer doorIndex,Integer accountId,Boolean isTest,String openId,Integer mainboardType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorSelectOrder(beginTime, endTime, orderNo, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName,nickName, doorIndex,accountId,isTest,openId,mainboardType,PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String,Object> vendorBatchDoorOpenEmpty(Integer mainboardId,Integer eid){
		return vendorDao.vendorBatchDoorOpenEmpty(mainboardId, eid);
	}
	
	public String getNowDbName(){
		return vendorDao.getNowDbName();
	}
	
	public String vendorGisCommunityOperat(String tag,Integer communityId,Integer districtId,String location,String communityName,Integer eid,Integer accountId,String account,String remark) {
		return vendorDao.vendorGisCommunityOperat(tag,communityId, districtId,location, communityName, eid, accountId, account, remark);
	}

	@DataNotLogCheck
	public Map<String, Object> findAreaByDistrictId(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> area = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>(); 
		area.put("provinces", list);
		area.put("communitys", list);
		if(districtId!=null&&districtId>0) {
			List<Map<String,Object>> communitys = vendorDao.findCommunityBydistrict(eid, cityId, districtId, isAudit, PageIndex, PageSize, IsSelectAll);
			area.put("communitys", communitys);
		}else {
			List<GisProvince> province = vendorDao.findAllProvince();
			List<GisCity> city = vendorDao.findAllCity();
			List<GisDistrict> district = vendorDao.findAllDistrict();
			List<Map<String, Object>> provinces = new ArrayList<Map<String, Object>>();
			for (GisProvince pro : province) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("provinceId", pro.getProvinceId());
				map.put("provinceName", pro.getProvinceName());
				List<Map<String, Object>> citys = new ArrayList<Map<String, Object>>();
				
				for (GisCity cit : city) {
					if(cit.getProvinceId().intValue()==pro.getProvinceId().intValue()) {
						Map<String, Object> citMap = new HashMap<String, Object>();
						citMap.put("cityId", cit.getCityId());
						citMap.put("cityName", cit.getCityName());
						citMap.put("baiduCode", cit.getBaiduCode());
						
						List<Map<String, Object>> districts = new ArrayList<Map<String, Object>>();
						for (GisDistrict dis : district) {
							if(dis.getCityId().intValue()==cit.getCityId().intValue()) {
								Map<String, Object> disMap = new HashMap<String, Object>();
								disMap.put("districtId", dis.getDistrictId());
								disMap.put("districtName", dis.getDistrictName());
								districts.add(disMap);
							}
						}
						
						citMap.put("districts", districts);
						
						citys.add(citMap);
					}
				}
				
				map.put("citys", citys);
				
				provinces.add(map);
			}
			area.put("provinces", provinces);
		}
		
		return area;
	}
	
	public Map<String, Object> findArea(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll,Integer provinceId){
		Map<String, Object> area = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>(); 
		area.put("provinces", list);
		area.put("citys", list);
		area.put("districts", list);
		area.put("communitys", list);
		if(districtId!=null&&districtId>0) {
			List<Map<String,Object>> communitys = vendorDao.findCommunityBydistrict(provinceId, cityId, districtId, isAudit, PageIndex, PageSize, IsSelectAll);
			area.put("communitys", communitys);
		}else if(cityId!=null&&cityId>0) {
			List<GisDistrict> district = vendorDao.findDistrictByCity(cityId);
			List<Map<String, Object>> districts = new ArrayList<Map<String, Object>>();
			for (GisDistrict dis : district) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cityId", dis.getCityId());
				map.put("districtId", dis.getDistrictId());
				map.put("districtName", dis.getDistrictName());
				districts.add(map);
			}
			area.put("districts", districts);
		}else if(provinceId!=null&&provinceId>0) {
			List<GisCity> city = vendorDao.findCityByProvince(provinceId);
			List<Map<String, Object>> citys = new ArrayList<Map<String, Object>>();
			for (GisCity cit : city) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cityId", cit.getCityId());
				map.put("cityName", cit.getCityName());
				map.put("baiduCode", cit.getBaiduCode());
				citys.add(map);
			}
			area.put("citys", citys);
		}else {
			List<GisProvince> province = vendorDao.findAllProvince();
			List<Map<String, Object>> provinces = new ArrayList<Map<String, Object>>();
			for (GisProvince pro : province) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("provinceId", pro.getProvinceId());
				map.put("provinceName", pro.getProvinceName());
				provinces.add(map);
			}
			area.put("provinces", provinces);
		}
		
		return area;
	}
	
	public Map<String, Object> vendorAdLedOrderSave(Integer clientId,String adContent,String beginDate,Integer dayQuantity,String codeNo,Double orderMoney,String communityIds){
		String[] ids = communityIds.split(",");
		Map<String, Object> communityName = new HashMap<String, Object>();
		// 查看社区id是否都为数字类型
		for (String id : ids) {
			try {
				Integer.parseInt(id);
			} catch (NumberFormatException e) {
				communityName.put("TAG", "社区Id错误");
				return communityName;
			}
		}
		
		// 广告数字字数
		int charQuantity = adContent.length();
		
		List<AdLedPrice> findAdLedPricesByCharQuantity = vendorDao.findAdLedPricesByCharQuantity(charQuantity);
		
		if(findAdLedPricesByCharQuantity==null) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("TAG", "该广告字数未设置价格");
			return map;
		}
		
		// 如果设置范围重复  就会出现多条数据
		if(findAdLedPricesByCharQuantity.size()>1) {
			logger.info("findAdLedPricesByCharQuantity.size() = "+findAdLedPricesByCharQuantity.size()+"  范围重复了！！！");
			communityName.put("TAG", "系统服务异常");
			return communityName;
		}
		
		// 取第一条数据
		AdLedPrice adLedPrice = findAdLedPricesByCharQuantity.get(0);
		
		// 计算价格  社区数量×单个价格
		BigDecimal bigDecimal = new BigDecimal(adLedPrice.getPrice());
		BigDecimal big100 = new BigDecimal(ids.length);
		BigDecimal multiply = big100.multiply(bigDecimal);
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		if(!df.format(multiply).equals(df.format(orderMoney))) {
			logger.info("[价格不正确] [multiply:{}] [orderMoney:{}]",new Object[] {adLedPrice.getPrice(),orderMoney});
			communityName.put("TAG", "价格不正确");
			return communityName;
		}
		
		List<GisCommunity> findCommunityByCommunityId = vendorDao.findCommunityByCommunityId(communityIds);
		for (GisCommunity gisCommunity : findCommunityByCommunityId) {
			String CommunityName = gisCommunity.getCommunityName()+"("+gisCommunity.getVendorCount()+")";
			communityName.put("communityName", CommunityName);
		}
		
		JSONArray communityNames = new JSONArray();
		communityNames.add(communityName);
		
		return vendorDao.vendorAdLedOrderSave(clientId, adContent, beginDate, dayQuantity, orderMoney, 0.0, codeNo,orderMoney,communityNames.toJSONString());
	}
	
	public List<AdLedPrice> findAdLedPrices(){
		return vendorDao.findAdLedPrices();
	}
	
	public List<Map<String, Object>> vendorAdLedOrderSelect(String beginTime,String endTime,Integer orderId,Boolean isPay,Integer orderStatus,String orderNo,String account,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorAdLedOrderSelect(beginTime, endTime, orderId, isPay, orderStatus, orderNo, account, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 自动售货机用户代金券查询
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	public List<Map<String, Object>> vendorCodeAppCustomerSelect(Integer eid,Integer clientId){
		return vendorDao.vendorCodeAppCustomerSelect(eid, clientId);
	}
	
	public List<VendorMainboard2> selectMainboardBymainboardNo(
			String mainboardNo) {
		List<VendorMainboard2> VendorMainboardlist = vendorDao.selectMainboardBymainboardNo(mainboardNo);
		return VendorMainboardlist;
	}
	
	public List<Map<String, Object>> balanceProductidMainboardidAccountidCommunityidByhourSelect(Integer searchType,String beginTime,String endTime,Integer eid,Integer productId,Integer mainboardId,Integer accountId,Integer communityId){
		return vendorDao.balanceProductidMainboardidAccountidCommunityidByhourSelect(searchType, beginTime, endTime, eid, productId, mainboardId, accountId, communityId);
	}
	
	public String allOnLineMainboards(){
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/allOnLineMainboards.action", params);
		return postEvianApi;
	}

	@DataNotLogCheck
	public List<VendorMainboardContainer> selectAllMainboardContainer() {
		return vendorDao.selectAllMainboardContainer();
	}
	
	public Map<String, Object> vendorErrorLogOperat(Integer eid, Integer mainboardId, Integer dataSource,
			String exceptionMsg, String exceptionCode, String openId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> vendorErrorLogOperat = vendorDao.vendorErrorLogOperat(eid, mainboardId, dataSource, exceptionMsg, exceptionCode, openId);
		if(vendorErrorLogOperat!=null&&vendorErrorLogOperat.size()>0) {
			return vendorErrorLogOperat.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> vendorErrorLogSelect(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,String nickName,String eName,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorErrorLogSelect(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, nickName, eName,isDispose ,PageIndex, PageSize, IsSelectAll);
	}
	
	@Deprecated
	public String sendJpushMessage(Integer xid,Integer type,String title, String message, Integer platform,String registerId,String jpushTag) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("xid", xid.toString()));
		params.add(new BasicNameValuePair("type", type.toString()));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("message", message));
		params.add(new BasicNameValuePair("platform", platform.toString()));
		params.add(new BasicNameValuePair("registerId", registerId));
		params.add(new BasicNameValuePair("jpushTag", jpushTag));
		
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/vendorJpush/sendJpushMessage.action", params);
		return webContent;
	}
	
	public Integer updateErrResult(Integer logId, Boolean isDispose, String disposeMsg) {
		return vendorDao.updateErrResult(logId, isDispose, disposeMsg);
	}
	
	public List<Map<String, Object>> appMerchantAccountShopVendorSelect(Integer accountId){
		return vendorDao.appMerchantAccountShopVendorSelect(accountId);
	}
	
	public List<Map<String, Object>> viewVisitSelect(String beginTime,String endTime,String mainboardNoMD5,Boolean isTest,Integer communityId,Integer accountId,Integer shopId,Integer eid,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.viewVisitSelect(beginTime,endTime, mainboardNoMD5, isTest,communityId,accountId,shopId,eid,openId, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> replenishmentStatistics(ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO dto){
		return vendorDao.replenishmentStatistics(dto);
	}
	
	public Map<String, Object> productSupplementSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest, Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.productSupplementSelect(beginTime, endTime, eid, account, productCode, productName,shopName,isTest, PageIndex, PageSize, IsSelectAll);
	}
	
	public String productSupplementConfirm(Integer supplementId,Integer eid,Integer accountId,Integer realQuantity,String remark,Integer productId) {
		return vendorDao.productSupplementConfirm(supplementId, eid, accountId, realQuantity, remark,productId);
	}
	
	public List<Map<String, Object>> vendorErrorLogStatistics(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorErrorLogStatistics(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, isDispose, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> repetitionBuySelect(String beginTime,String endTime,Boolean isSearchTimeForAuth,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer sortType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.repetitionBuySelect(beginTime, endTime, isSearchTimeForAuth, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, cellphone,sortType, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> vendorMainboardOrProductIdBuySelect(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,Integer mainboardType,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorMainboardOrProductIdBuySelect(statisticsType,beginTime, endTime,  eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, mainboardType, cellphone, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 查询补货计划 和计划详情
	 * @param eid
	 * @param shopId
	 * @param containerId
	 * @param accountId
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	public List<Map<String, Object>> vendorReplenishmentPlanSelect(Integer eid,Integer shopId,Integer containerId,Integer accountId,Integer PageIndex, Integer PageSize, Boolean IsSelectAll){
		List<Map<String, Object>> shopReplenishmentPlan = vendorDao.vendorReplenishmentPlanSelect(eid,0, null,null,null,null, null);
		List<Map<String, Object>> replenishmentPlans = vendorDao.vendorReplenishmentPlanSelect(eid,shopId, containerId,accountId, PageIndex, PageSize, IsSelectAll);
		if(shopReplenishmentPlan.size()>0) {
			replenishmentPlans.add(shopReplenishmentPlan.get(0));
		}
		for (Map<String, Object> map : replenishmentPlans) {
			/*if(map.get("mainboardId")!=null) {
				Integer mainboardId = (Integer) map.get("mainboardId");
				List<Map<String, Object>> selectVendorSellProductKindNum = vendorDao.selectVendorSellProductKindNum(mainboardId);
				map.put("vendorSellProductKindNum", selectVendorSellProductKindNum);
			}*/
			if(map.get("planId")!=null) {
				Integer planId = (Integer) map.get("planId");
				List<Map<String, Object>> vendorReplenishmentPlanDetailsSelect = vendorDao.vendorReplenishmentPlanDetailsSelect(planId,null);
				map.put("replenishmentPlanDetails", vendorReplenishmentPlanDetailsSelect);
			}
		}
		return replenishmentPlans;
	}
	
	public List<Map<String, Object>> vendorReplenishmentPlanDetailsSelect(Integer planId,Integer mainboardId){
		return vendorDao.vendorReplenishmentPlanDetailsSelect(planId,mainboardId);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Integer insertVendorReplenishmentPlan(List<Integer> mainboardIds,Integer shopId,Integer containerId,String planName,Boolean isShopAll,String creater,Integer warningNum,JSONArray productJsonArray) {
//		VendorContainer vendorContainer = vendorDao.selectVendorContainerByMainboardId(mainboardId);
		
		Integer eid = null;
		Integer doorNum = null;
		if(mainboardIds.size()>0) {
			Integer mainboardId =  mainboardIds.get(0);
			VendorContainer vendorContainer = vendorDao.selectVendorContainerByMainboardId(mainboardId);
			eid = vendorContainer.getEid();
			containerId = vendorContainer.getContainerId();
			shopId = vendorContainer.getShopId();
			for (Integer mid : mainboardIds) {
				VendorContainer vc = vendorDao.selectVendorContainerByMainboardId(mid);
				if(containerId.intValue()!=vc.getContainerId().intValue()||eid.intValue()!=vc.getEid().intValue()||shopId.intValue()!=vc.getShopId().intValue()) {
					return -1;
				}
			}
			doorNum = vendorContainer.getDoorNum();
		}else {
			Shop shop = shopDao.selectEidByShopId(shopId);
			VendorContainerTemplate vendorContainerTemplate = vendorDao.selectVendorContainerTemplateByContainerId(containerId);
			eid = shop.getEid();
			doorNum = vendorContainerTemplate.getDoorNum();
		}
		if(doorNum!=null) {
			// 插入返回主键
			Integer planId = vendorDao.insertVendorReplenishmentPlan(eid, containerId, shopId, planName, doorNum, isShopAll, creater, warningNum);
			int add = 0;
			if(planId!=null&&planId>0) {
				for (int i = 0; i < productJsonArray.size(); i++) {
					JSONObject pJson = productJsonArray.getJSONObject(i);
					
					add += vendorDao.insertvendorReplenishmentPlanItem(planId, pJson.getInteger("sortId"), pJson.getInteger("productId"), pJson.getInteger("planNum"));
				}
				
				for (Integer mainboardId : mainboardIds) {
					// 有mainboardId才将这条补货计划关联主板 没有就不关联
					if(mainboardId!=null) {
						List<Map<String, Object>> vendorReplenishmentPlanMappingSelect = vendorDao.vendorReplenishmentPlanMappingSelect(mainboardId,null);
						if(vendorReplenishmentPlanMappingSelect!=null&&vendorReplenishmentPlanMappingSelect.size()>0) {
							vendorDao.updateVendorReplenishmentPlanMapping(mainboardId, planId);
						}else {
							vendorDao.insertVendorReplenishmentPlanMapping(mainboardId, planId, creater);
						}
					}
				}
				
				
				return add;
			}
		}
		
		return 0;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Integer updateVendorReplenishmentPlan(List<Integer> mainboardIds,Integer planId,String planName,Boolean isShopAll,JSONArray productJsonArray) {
		// 替换
		Integer result = updateVendorReplenishmentPlanByPlanId(mainboardIds, planId);
		
		// 返回值为-2 说明planId是店铺通用计划
		if(result.intValue()==-2) {
			return 0;
		}
		
		vendorDao.updateVendorReplenishmentPlan(planId, planName, isShopAll);
		
		int add = 0;
		// 重置补货计划商品
		if(productJsonArray!=null&&productJsonArray.size()>0) {
			vendorDao.removeVendorReplenishmentPlanItem(planId);
			for (int i = 0; i < productJsonArray.size(); i++) {
				JSONObject pJson = productJsonArray.getJSONObject(i);
				add += vendorDao.insertvendorReplenishmentPlanItem(planId, pJson.getInteger("sortId"), pJson.getInteger("productId"), pJson.getInteger("planNum"));
			}
		}
		
		return add;
	}
	
	
	/**
	 * 删除 补货计划
	 * @param planId
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer removeVendorReplenishmentPlan(Integer planId) {
		Integer update = vendorDao.removeUpdateVendorReplenishmentPlan(planId);
		if(update>0) {
			vendorDao.removeVendorReplenishmentPlanItem(planId);
			vendorDao.removeVendorReplenishmentPlanMapping(planId);
		}
		
		return update;
	}
	
	/**
	 * 货柜更换已有的补货计划
	 * @param mainboardId
	 * @param planId
	 * @return
	 */
	public Integer updateVendorReplenishmentPlanByPlanId(List<Integer> mainboardIds,Integer planId) {
		
		// 查看这条补货计划是不是店铺通用的 
		List<Map<String, Object>> plan = vendorDao.vendorReplenishmentPlanSelectByPlanId(planId);
		if(plan==null||plan.size()<1||plan.get(0)==null||plan.get(0).get("shopId")==null||0==Integer.parseInt(plan.get(0).get("shopId").toString())) {
			return -2;
		}
		
		Integer relut = 0;
		for (Integer mainboardId : mainboardIds) {
//			List<Map<String, Object>> mappingSelect = vendorDao.vendorReplenishmentPlanMappingSelectNoShopId(mainboardId);
			// 查看是否有对应的再做修改
			List<Map<String, Object>> vendorReplenishmentPlanMappingSelect = vendorDao.vendorReplenishmentPlanMappingSelect(mainboardId, null);
			logger.info("size = "+vendorReplenishmentPlanMappingSelect.size());
			if(vendorReplenishmentPlanMappingSelect==null||vendorReplenishmentPlanMappingSelect.size()<1) {
				Integer update = vendorDao.insertVendorReplenishmentPlanMapping(mainboardId, planId, "");
				relut += update;
			}else {
				Integer update = vendorDao.updateVendorReplenishmentPlanMapping(mainboardId, planId);
				relut += update;
			}
			
			/*if(mappingSelect.size()<0) {
				planId =  (Integer) mappingSelect.get(0).get("planId");
				// 如果原来的补货计划已经没有对应的货柜所对应  就删除
				List<Map<String, Object>> vendorReplenishmentPlanMappingSelect = vendorDao.vendorReplenishmentPlanMappingSelect(null, planId);
				if(vendorReplenishmentPlanMappingSelect==null||vendorReplenishmentPlanMappingSelect.size()<1) {
					removeVendorReplenishmentPlan(planId);
				}
			}*/
		}
		return relut;
	}
	
	public List<Map<String, Object>> selectVendorSellProductKindNum(Integer accountId){
		List<Map<String, Object>> s = vendorDao.selectVendorContainerByAccountId(accountId);
		for (Map<String, Object> map : s) {
			
			Object object = map.get("mainboardId");
			if(object!=null) {
				Integer mainboardId = (Integer) map.get("mainboardId");
				List<Map<String, Object>> selectVendorSellProductKindNum = vendorDao.selectVendorSellProductKindNum(mainboardId);
				map.put("vendorSellProductKindNum", selectVendorSellProductKindNum);
			}
		}
		return s;
	}
	
	public Map<String, Object> vendorAppMerchantProductStorageDetailSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest,String remark,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorAppMerchantProductStorageDetailSelect(beginTime, endTime, eid, account, productCode, productName, shopName, isTest,remark, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> vendorDoorSelectGroupByProductId(Integer eid,Integer doorStatus,String productName,String productCode,String shortName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorDoorSelectGroupByProductId(eid, doorStatus, productName, productCode, shortName, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> vendorDoorSelectGroupByProductIdAndMainboardId(Integer eid,Integer productId,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorDoorSelectGroupByProductIdAndMainboardId(eid, productId, doorStatus, PageIndex, PageSize, IsSelectAll);
	}
	
	public List<VendorProductReplenishmentClass> selectVendorProductReplenishmentClass(Integer eid) {
		return vendorDao.selectVendorProductReplenishmentClass(eid);
	}
	
	/**
	 * 查询当前订单开锁状态
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> selectUnlockingByOrderId(Integer orderId){
		return vendorDao.selectUnlockingByOrderId(orderId);
	}
	
	/**
	 * 获取app版本号
	 * @return
	 */
	@LogNotCheck
	public APPUpgraderSelectObject getAppVersion() {
		APPUpgraderSelectObject version = new APPUpgraderSelectObject();
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("upgrade.properties");
		InputStreamReader isr= null;
		InputStream in = null;
		try {
			in = resource.getInputStream();
			isr = new InputStreamReader(in,"UTF-8");
			
			properties.load(isr);
			//获取key对应的value值
			String versionInt = properties.getProperty("vendor.sversionInt");
			String versionName = properties.getProperty("vendor.versionName");
			String downloadUrl = properties.getProperty("vendor.downloadUrl");
			String describe = properties.getProperty("vendor.describe");
			version.setVersionInt(versionInt);
			version.setVersionName(versionName);
			version.setDownloadUrl(downloadUrl);
			version.setDescribe(describe);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("获取app版本文件错误",e);
		}finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			if(isr!=null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		return version;
	}
	
	/**
	 * 设置app版本号
	 * @return
	 */
	public void setAppVersion(String versionInt,String versionName) {
		Properties properties = new Properties();
		String upgrade= "upgrade.properties";
		Resource resource = new ClassPathResource(upgrade);
		
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		path = path.replace("%20"," ");
		InputStreamReader isr= null;
		InputStream in = null;
		try {
			in = resource.getInputStream();
			isr = new InputStreamReader(in,"UTF-8");
			
			properties.load(isr);
			
			///保存属性到b.properties文件
			FileOutputStream oFile = new FileOutputStream(path+upgrade, true);//true表示追加打开
			properties.setProperty("vendor.sversionInt", versionInt);
			properties.setProperty("vendor.versionName", versionName);
			properties.store(oFile, "The New properties file");
			oFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("获取app版本文件错误",e);
		}finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			if(isr!=null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}

	public void setAppVersion(String versionInt,String versionName,String downloadUrl,String describe) {
		setAppVersion(versionInt,versionName);
		Properties properties = new Properties();
		String upgrade= "upgrade.properties";
		Resource resource = new ClassPathResource(upgrade);

		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
		path = path.replace("%20"," ");
		InputStreamReader isr= null;
		InputStream in = null;
		try {
			in = resource.getInputStream();
			isr = new InputStreamReader(in,"UTF-8");

			properties.load(isr);

			///保存属性到b.properties文件
			FileOutputStream oFile = new FileOutputStream(path+upgrade, false);//true表示追加打开
			if(!StringUtils.isBlank(downloadUrl)){
				properties.setProperty("vendor.downloadUrl", downloadUrl);

			}
			if(!StringUtils.isBlank(describe)){
				properties.setProperty("vendor.describe", describe);
			}
			properties.store(oFile, "The New properties file");
			oFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("获取app版本文件错误",e);
		}finally {
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			if(isr!=null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	
	
	/**
	 * 售货机退款申请
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> refundApply(Integer orderId, Double applyRefundMoney,String applyRemark, String cellphone, String openId) {
		return vendorDao.refundApply(orderId, applyRefundMoney,applyRemark, cellphone, openId);
	}

	public Map<String, Object> Proc_Backstage_vendor_order_select_groupByMainboardIdOrProductId(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,Integer mainboardType,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		Map<String, Object> stringObjectMap = vendorDao.vendorMainboardOrProductIdBuySelect(statisticsType, beginTime, endTime, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, mainboardType, cellphone, PageIndex, PageSize, IsSelectAll);
		Object vendorMainboardOrProductIdBuySelect = stringObjectMap.get("vendorMainboardOrProductIdBuySelect");
		stringObjectMap.remove("vendorMainboardOrProductIdBuySelect");
		stringObjectMap.put("statisticsList",vendorMainboardOrProductIdBuySelect);
		return stringObjectMap;
	}

	public Map<String, Object> Proc_Backstage_vendor_replenishment_select(ReplenishmentRecordSelectReqDTO dto){
		return vendorDao.Proc_Backstage_vendor_replenishment_select(dto);
	}

	public String Proc_Backstage_vendor_AppCustomer_DoorReplenishment_Stand(Integer eid,Integer mainboardId,Integer productId,Integer productQuantity,Integer outProductId,Integer outProductQuantity,String account,Integer typeId, String typeName,String location){
		return vendorDao.Proc_Backstage_vendor_AppCustomer_DoorReplenishment_Stand(eid,mainboardId,productId,productQuantity,outProductId,outProductQuantity,account,typeId,typeName,location);
	}
	public String Proc_Backstage_order_compensateIntegral(Integer eid,Integer orderId,Integer accountId){
		return vendorDao.Proc_Backstage_order_compensateIntegral(eid,orderId,accountId);
	}

	public Integer updateStockoutWarning(Integer planId, Integer warningNum){
		return vendorDao.updateStockoutWarning(planId, warningNum);
	}

	public List<Map<String,Object>> Proc_Backstage_vendor_replenishment_type_select(Integer eid){
		return vendorDao.Proc_Backstage_vendor_replenishment_type_select(eid);
	}
	public List<Map<String,Object>> Proc_Backstage_vendor_replenishment_type_select_statistics(Integer statisticsType,String beginTime,String endTime,Integer eid,String mainboardNo,Integer operatType,Integer shopId	,Boolean isTest,Integer mainboardType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.Proc_Backstage_vendor_replenishment_type_select_statistics(statisticsType,beginTime,endTime,eid, mainboardNo, operatType, shopId	, isTest, mainboardType, PageIndex, PageSize, IsSelectAll);
	}

	public Map<String,Object> selectVendorCouponToWechatActivityByUser(Integer eid, String openId,Integer clientId){
		Map<String, Object> resultMap = vendorDao.selectVendorCouponToWechatActivity(eid);
		if(resultMap==null){
			return null;
		}

		// 1是有活动 -1是已经参与 0 是已过期
		int state = 1;
		Map<String, Object> isParticipation = vendorDao.selectVendorCouponToWechatActivityParticipationRecord(eid, openId, clientId);
		if(isParticipation!=null){
			state = -1;
		}

		Timestamp endTime = (Timestamp) resultMap.get("endTime");
		long now = System.currentTimeMillis();
		if(now>endTime.getTime()){
			state = 0;
		}
		List<Map<String, Object>> maps = vendorDao.selectVendorCouponToWechatActivityDetail(eid);
		resultMap.put("coupon",maps);
		resultMap.put("state",state);
		return resultMap;
	}

	public Map<String,Object> creationTempClientId(Integer eid,String openId,String appId,String unionId)throws Exception{
		if(eid==null || eid==0){
			EEnterpriseWechatliteapp eEnterpriseWechatliteapp = enterpriseDao.selectEnterpriseEidByAppId(appId);
			if(eEnterpriseWechatliteapp==null){
				throw new ResultException("没有对应的企业能对应appId");
			}
			eid = eEnterpriseWechatliteapp.getEid();
		}
		Map<String, Object> crea = vendorDao.creationTempClientId(eid, openId, appId, unionId);
		if("1".equals(crea.get("tag"))){
			Integer clientId = (Integer) crea.get("clientId");
			// 生成pwd和identityCode
			UserDes userDes = new UserDes(clientId, clientId.toString());

			int integer = userDao.updateEclient(clientId, userDes.getPassWord(), userDes.getIdentityCode());
			if(integer==0){
				throw new ResultException(ResultCode.CODE_ERROR_ACCOUNT_SAVE.getMessage());
			}
			return crea;
		}else{
			throw new ResultException(ResultCode.CODE_ERROR_ACCOUNT_SAVE.getMessage());
		}
	}

	/**
	 * 查询驿站临时用户和水趣注册用户
	 * @param openId
	 * @return
	 */
	public Map<String,Object> vendorLoginSelect(String openId){
		return vendorDao.vendorLoginSelect(openId);
	}

	/* 237.货柜机下单成功，队列发送红包 */
	public String vendorQueueSendPacket(Integer orderId,String openId,Integer typeId,String appId) {

		EEnterpriseWechatliteapp e = enterpriseDao.selectEnterpriseEidByAppId(appId);
		if(e==null){
			return null;
		}

		// 查询企业是否有活动
		List<Map<String, Object>> activity = vendorDao.selectRedPackActivity(e.getEid());
		if(activity.size()==0){
			return null;
		}

		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("orderId", orderId.toString()));
		params.add(new BasicNameValuePair("openId", openId));
		params.add(new BasicNameValuePair("typeId", typeId.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", appId));
		return HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/vendorJpush/vendorQueueSendPacket.action", params);
	}

	/**
	 * 记录补货员扫码位置
	 * @param dto
	 * @return
	 */
	public int recordStaffScanCodeLocation(VendorStaffScanCodeLocationReqDTO dto){
		return vendorDao.insertVendorStaffScanCodeLocation(dto);

	}

	public Map<String,Object> staffScanLocationSelect(ProcXHXStaffScanLocationSelectReqDTO dto){
		Map<String, Object> map = vendorDao.Proc_XHX_staff_scan_location_select(dto);
		return map;
	}

	public int insertVendorStatusImageRecord(InsertVendorStatusImageRecordInputDTO dto, MultipartFile statusImage) throws IOException {
		String s = fileUploadService.fileUpload(statusImage.getBytes());
		dto.setRecordImage(s);
		return vendorDao.insertVendorStatusImageRecord(dto);
	}

	public Map<String, Object> Proc_XHX_vendor_status_image_record_select(ProcXhxVendorStatusImageRecordSelect dto) {
		Map<String, Object> result = vendorDao.Proc_XHX_vendor_status_image_record_select(dto);
		List<Map<String,Object>> imageRecords = (List<Map<String, Object>>) result.get("imageRecords");
		/*if(imageRecords!=null){
			imageRecords.stream().forEach(image -> image.put("statusImage",UrlManage.getAdminWebUrl()+image.get("statusImage")));
		}*/
		return result;
	}

	public int operSingleProduct(SingleProduct product,MultipartFile productImage) throws IOException {
		int count = 0;
		if(productImage!=null){
			String productPic = fileUploadService.fileUpload(productImage.getBytes());
			product.setProductPic(productPic);
		}
		if(product.getProductId()!=null){
			SingleProduct singleProduct = vendorMapperDao.selectSingleProductsByPid(product.getProductId());
			if(singleProduct!=null){
				if(!StringUtils.isEmpty(product.getProductName())){
					singleProduct.setProductName(product.getProductName());
				}
				if(!StringUtils.isEmpty(product.getProductPic())){
					singleProduct.setProductPic(product.getProductPic());
				}
				if(product.getEid()!=null){
					singleProduct.setEid(product.getEid());
				}
				if(product.getPrice()!=null){
					singleProduct.setPrice(product.getPrice());
				}
				if(!StringUtils.isEmpty(product.getDescribe())){
					singleProduct.setDescribe(product.getDescribe());
				}
				count = vendorMapperDao.singleProductUpdate(singleProduct);
				return count;
			}
		}
		if(StringUtils.isEmpty(product.getProductPic())||product.getPrice()==null){
			logger.error("product:{}",product);
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_SDDV3_NO_DATA);
		}
		return count = vendorMapperDao.singleProductInsert(product);
	}

	public List<SingleProduct> selectSingleProductsByEid(Integer eid){
		return vendorMapperDao.selectSingleProductsByEid(eid);
	}

	public List<SingleProduct> selectSingleProductsByAccountId(Integer accountId){
		return vendorMapperDao.selectSingleProductsByAccountId(accountId);
	}

	public Map<String,Object> Proc_Backstage_single_order_select(ProcBackstageSingleOrderSelectReqDTO dto){
		return vendorDao.Proc_Backstage_single_order_select(dto);
	}

	@Transactional(rollbackFor=Exception.class)
	public int updateSingleProductHitTheShelf(Boolean hitTheShelf,Integer productId){
		return vendorMapperDao.updateSingleProductHitTheShelf(hitTheShelf, productId);
	}

	@Transactional(rollbackFor=Exception.class)
	public int deleteSingleProduct(Integer productId){
		vendorMapperDao.updateSingleProductHitTheShelf(false,productId);
		return vendorMapperDao.deleteSingleProduct(productId);
	}

	public Map<String,Object> selectSingleProducts(ProcBackstageSingleProductSelectReqDTO dto){
		return vendorDao.Proc_Backstage_single_product_select(dto);
	}

	public int insertEPaySubAccount(EPaySubAccount ePaySubAccount) {
		List<EPaySubAccount> ePaySubAccounts = vendorMapperDao.selectEPaySubAccountByAccount(ePaySubAccount.getCreator());
		if(ePaySubAccounts.size()>0){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_OPERATION);
		}
		UUID uuid=UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr=str.replace("-", "");
		ePaySubAccount.setBusinessCode(uuidStr);
		return vendorMapperDao.insertEPaySubAccount(ePaySubAccount);
	}

	public List<EPaySubAccount> selectEPaySubAccountByAccount(String account) {
		return vendorMapperDao.selectEPaySubAccountByAccount(account);
	}

	public int insertEWechatServicepaySubaccountApply(EWechatServicepaySubaccountApplyInputDTO dto) {
		List<EWechatServicepaySubaccountApplyRepDTO> list = vendorMapperDao.selectEWechatServicepaySubaccountApplyByAccount(dto.getAccountId());
		if(list.size()>0){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_OPERATION);
		}
		ObjectMapper jsonContent = new ObjectMapper();
		try {
			JsonNode jsonNode = jsonContent.readTree(dto.getJsonContent());
			JsonNode node = jsonNode.get("business_code");
			String business_code = node.textValue();
			if(StringUtils.isBlank(business_code)){
				throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
			}
			dto.setBusinessCode(business_code);
		} catch (IOException e) {
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
		}
		return vendorMapperDao.insertEWechatServicepaySubaccountApply(dto);
	}

	public List<EWechatServicepaySubaccountApplyRepDTO> selectEWechatServicepaySubaccountApplyByAccount(Integer accountId) {
		return vendorMapperDao.selectEWechatServicepaySubaccountApplyByAccount(accountId);
	}

	public int updateEWechatServicepaySubaccountApply(EWechatServicepaySubaccountApplyInputDTO dto) {
		List<EWechatServicepaySubaccountApplyRepDTO> list = vendorMapperDao.selectEWechatServicepaySubaccountApplyByAccount(dto.getAccountId());
		if(list.size()==0){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_OPERATION);
		}
		return vendorMapperDao.updateEWechatServicepaySubaccountApply(dto);
	}

	private List<EWechatServicepaySubaccountApplyProvinceRepDTO> applyProvinces;

	private Long applyPrivincesSiwtch = new Long(3275108312769826226L);

	@LogNotCheck
	public List<EWechatServicepaySubaccountApplyProvinceRepDTO> selectEWechatServicepaySubaccountApplyProvince() {
		if(applyProvinces==null){
			synchronized(applyPrivincesSiwtch){
				if(applyProvinces==null){
					applyProvinces = vendorMapperDao.selectEWechatServicepaySubaccountApplyProvince();
				}
			}
		}
		return applyProvinces;
	}

	private volatile List<EWechatServicepaySubaccountApplyBankRepDTO> applyBank;

	@LogNotCheck
	public synchronized void selectEWechatServicepaySubaccountApplyBank() {
		if(applyBank==null){
			applyBank = vendorMapperDao.selectEWechatServicepaySubaccountApplyBank();
		}
	}

	@LogNotCheck
	public Map<String,Object> selectEWechatServicepaySubaccountApplyBank(String bankName,PagingPojo pojo) {
		if(applyBank==null){
			selectEWechatServicepaySubaccountApplyBank();
		}
		Map<String,Object> resultMap = new HashMap<>();
		List<EWechatServicepaySubaccountApplyBankRepDTO> result;
		Stream<EWechatServicepaySubaccountApplyBankRepDTO> resultStream = applyBank.stream().filter(bank -> bank.getBankName().contains(bankName));
		if(pojo.getIsSelectAll()||pojo.getPageIndex()==null||pojo.getPageSize()==null){
			result = resultStream.collect(Collectors.toList());
		}else{

			result = resultStream.skip(pojo.getPageSize() * (pojo.getPageIndex() - 1))
					.limit(pojo.getPageSize()).collect(Collectors.toList());
		}
		resultMap.put("bankData",result);
		return resultMap;
	}

	public Map<String,Object> subaccountApplyImgUpload(MultipartFile file) throws Exception {
		Map<String,Object> result = new HashMap<>();
		String fileNameTemp = file.getOriginalFilename();
		String guid = UUID.randomUUID().toString();
		String fileName = MD5Util.md5(guid).toUpperCase();
		try {
			String suffix = fileNameTemp.substring(fileNameTemp.lastIndexOf("."));

			fileName = fileName+suffix;
		} catch (StringIndexOutOfBoundsException e) {

		}
		// 自定义文件名，防止传过来的文件名规则不对
		File f = new File(fileName);
		FileUtil.inputStreamToFile(file.getInputStream(),f);
		WechatV3mediaUploadRepDTO media = weixinApiService.wechatV3mediaUpload(f);
		try {
			File del = new File(f.toURI());
			del.delete();
		} catch (Exception e) {
			logger.error("{}",e);
		}
		String media_id = media.getMedia_id();
		if(StringUtils.isBlank(media_id)){
			logger.error("[message:{}] [code:{}] [{}]",media.getMessage(),media.getCode(),media);
			if(media.getMessage()!=null){
				throw new ResultException(media.getMessage());
			}else{
				throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_SYSTEM);
			}
		}
		String qiniuUrl = fileUploadService.fileUpload(file.getBytes());
		result.put("media_id",media_id);
		result.put("qiniuUrl",qiniuUrl);
		return result;
	}

	private volatile List<EWechatServicepaySubaccountApplyBanknameReqDTO> wechatBankName =null;

	public synchronized void selectEWechatServicepaySubaccountApplyBankname() {
		if (wechatBankName==null){
			wechatBankName = vendorMapperDao.selectEWechatServicepaySubaccountApplyBankname();
		}
	}

	public Map<String,Object> selectEWechatServicepaySubaccountApplyBankname(String bankName,PagingPojo pojo) {
		if(wechatBankName==null){
			selectEWechatServicepaySubaccountApplyBankname();
		}
		Map<String,Object> resultMap = new HashMap<>();
		List<EWechatServicepaySubaccountApplyBanknameReqDTO> result;
		Stream<EWechatServicepaySubaccountApplyBanknameReqDTO> resultStream = wechatBankName.stream().filter(bank -> bank.getBankname().contains(bankName));
		if(pojo.getIsSelectAll()||pojo.getPageIndex()==null||pojo.getPageSize()==null){
			result = resultStream.collect(Collectors.toList());
		}else{

			result = resultStream.skip(pojo.getPageSize() * (pojo.getPageIndex() - 1))
					.limit(pojo.getPageSize()).collect(Collectors.toList());
		}
		resultMap.put("bankData",result);
		return resultMap;
	}

	public int updateSingleOrderStatus(Integer orderStatus,Integer orderId){
		return vendorMapperDao.updatesingleOrderStatus(orderStatus, orderId);
	}

	public int addSkipShuiqooProduct(SkipShuiqooProduct skipShuiqooProduct){
		return skipShuiqooProductDao.insertSkipShuiqooProduct(skipShuiqooProduct);
	}

	public int removeSkipShuiqooProduct(Integer id){
		return skipShuiqooProductDao.deleteSkipShuiqooProduct(id);
	}

	public Map<String,Object> selectSkipShuiqooProductRepByEid(SkipShuiqooProductReqDTO dto){
		return DaoUtil.resultPageData(dto,"products",()-> skipShuiqooProductDao.selectSkipShuiqooProductRepByEid(dto.getEid()));
	}

	public int insertVendorProduct(AddVendorProductReqDTO dto){
		return vendorMapperDao.insertVendorProduct(dto);
	}

	public int delVendorProduct(Integer id){
		return vendorMapperDao.delVendorProduct(id);
	}

	public int updateVendorProduct(UpdateVendorProductReqDTO dto){
		VendorProduct vendorProduct = vendorMapperDao.selectVendorProductById(dto.getId());
		if(vendorProduct==null){
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_SDDV3_NO_DATA);
		}
		// setIgnoreNullValue 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
		BeanUtil.copyProperties(dto,vendorProduct,true, CopyOptions.create().setIgnoreNullValue(true));
		return vendorMapperDao.updateVendorProduct(vendorProduct);
	}

	public Map<String,Object> selectVendorProductByAccountId(FindVendorProductByAccountIdReqDTO dto){
		return DaoUtil.resultPageData(dto,"products",()-> vendorMapperDao.selectVendorProductByAccountId(dto.getAccountId()));
	}

	public int operVendorAppMerchantAccountProduct(VendorAppMerchantAccountProduct product){
		VendorAppMerchantAccountProduct vProduct = vendorMapperDao.selectVendorAppMerchantAccountProduct(product);
		if(vProduct==null){
			return vendorMapperDao.insertVendorAppMerchantAccountProduct(product);
		}else if(product.getSortId()!=null&&(vProduct.getSortId()==null||product.getSortId().intValue()!=vProduct.getSortId().intValue())){
			return vendorMapperDao.updateVendorAppMerchantAccountProduct(product);
		}
		return 0;
	}

	public int removeVendorAppMerchantAccountProduct(VendorAppMerchantAccountProduct product){
		return vendorMapperDao.deleteVendorAppMerchantAccountProduct(product);
	}
}
