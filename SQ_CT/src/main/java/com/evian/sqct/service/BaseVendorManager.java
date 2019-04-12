package com.evian.sqct.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.vendor.AdLedPrice;
import com.evian.sqct.bean.vendor.GisCity;
import com.evian.sqct.bean.vendor.GisCommunity;
import com.evian.sqct.bean.vendor.GisDistrict;
import com.evian.sqct.bean.vendor.GisProvince;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.bean.vendor.VendorContainer;
import com.evian.sqct.bean.vendor.VendorContainerTemplate;
import com.evian.sqct.bean.vendor.VendorDoor;
import com.evian.sqct.bean.vendor.VendorMainboard2;
import com.evian.sqct.bean.vendor.VendorMainboardContainer;
import com.evian.sqct.bean.vendor.VendorMainboardInstruct;
import com.evian.sqct.bean.vendor.VendorProductReplenishmentClass;
import com.evian.sqct.bean.vendor.VendorShopContainer;
import com.evian.sqct.dao.IShopDao;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.util.HttpClientUtilOkHttp;
import com.evian.sqct.util.WebConfig;

@Service
@Transactional
public class BaseVendorManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("vendorDao")
	private IVendorDao vendorDao;
	
	@Autowired
	@Qualifier("shopDao")
	private IShopDao shopDao;
	
	public String verndorContainerADD(Integer vmId,Integer bmId,Integer doorNumber,Integer vmIndex,String createdUser){
		logger.info("[project:{}] [step:enter] [vmId:{}] [bmId:{}] [doorNumber:{}] [vmIndex:{}] [createdUser:{}]",
				new Object[] { WebConfig.projectName, vmId, bmId, doorNumber, vmIndex,createdUser});
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
	
	public List<VendorDoor> findDoorAndProuct(Integer bmId,Integer vmId,Integer doorIndex,Integer classId,String beginTime,String endTime,String account,String containerCode,Integer doorReplenishmentDays,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.selectDoorAndProuct(bmId, vmId,doorIndex, classId, beginTime, endTime, account, containerCode, doorReplenishmentDays, doorStatus, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String,Object> findDoorAndProuct_v2(Integer mainboardId,Integer eid,Integer replenishmentClassId,Integer PageIndex,Integer PageSize){
		return vendorDao.selectDoorAndProuct_v2(mainboardId,eid, replenishmentClassId,PageIndex,PageSize); 
	}
	
	@Deprecated
	public Map<String, Object> mainboardBindContainer(String mainboardNo,String containerCode,Integer eid){
		logger.info("[project:{}] [step:enter] [mainboardNo:{}] [containerCode:{}] [eid:{}]",
				new Object[] { WebConfig.projectName, mainboardNo, containerCode, eid});
		return vendorDao.mainboardBindContainer(mainboardNo, containerCode, eid);
	}
	
	public Boolean findmainboardInstructByBmIdByDoorIndex(Integer bmId, Integer doorIndex) {
		logger.info("[project:{}] [step:enter] [bmId:{}] [doorIndex:{}] ",
				new Object[] { WebConfig.projectName, bmId, doorIndex});
		VendorMainboardInstruct vndorMainboardInstruct = vendorDao.selectmainboardInstructByBmIdByDoorIndex(bmId, doorIndex);
		if(vndorMainboardInstruct!=null){
			return vndorMainboardInstruct.getInstructSuc();
		}else{
			return false;
		}
	}
	
	public String verificationVendorDoor(Integer mainboardId,Integer doorIndex,Integer portNo,String alias,Integer doorStatus,String breakDownRemark){
		logger.info("[project:{}] [step:enter] [mainboardId:{}] [doorIndex:{}] [portNo:{}] [alias:{}] [doorStatus:{}] [breakDownRemark:{}]",
				new Object[] { WebConfig.projectName, mainboardId, doorIndex,portNo,alias,doorStatus,breakDownRemark});
		return vendorDao.verificationVendorDoor(mainboardId, doorIndex,portNo, alias,doorStatus,breakDownRemark);
	}
	
	public List<VendorShopContainer> VendorManage(Integer shopId){
		logger.info("[project:{}] [step:enter] [shopId:{}]",
				new Object[] { WebConfig.projectName, shopId});
		return vendorDao.VendorManage(shopId);
	}
	
	public List<Map<String,Object>> AppCustomerMainboardSelect(Integer eid,Integer shopId,Integer containerStatus,Integer accountId,String mainboardNoMD5){
		logger.info("[project:{}] [step:enter] [eid:{}] [shopId:{}] [containerStatus:{}] [accountId:{}] [mainboardNoMD5:{}]",
				new Object[] { WebConfig.projectName, eid,shopId,containerStatus,accountId,mainboardNoMD5});
		return vendorDao.AppCustomerMainboardSelect(eid, shopId, containerStatus, accountId, mainboardNoMD5);
	}
	
	public String doorReplenishment(Integer eid,Integer mainboardId,Integer doorIndex,Integer productId,String account,Integer operatType,Integer wxId){
		logger.info("[project:{}] [step:enter] [eid:{}] [mainboardId:{}] [doorIndex:{}] [productId:{}] [account:{}] [operatType:{}] [wxId:{}]",
				new Object[] { WebConfig.projectName, eid,mainboardId,doorIndex,productId,account,operatType,wxId});
		return vendorDao.doorReplenishment(eid, mainboardId,doorIndex,productId,account,operatType,wxId);
	}
	
	public List<Map<String, Object>> vendorProductSelect(String productName,Integer eid,Boolean isLine){
		logger.info("[project:{}] [step:enter] [productName:{}] [eid:{}] [isLine:{}]",
				new Object[] { WebConfig.projectName,productName, eid,isLine});
		return vendorDao.vendorProductSelect(null, null, null, productName, eid, isLine, null, null, true);
	}
	
	public List<Map<String, Object>> vendorDoorProductStatistics(Integer staffId,Integer productState,Boolean isAll){
		logger.info("[project:{}] [step:enter] [staffId:{}] [productState:{}] [isAll:{}]",
				new Object[] { WebConfig.projectName,staffId, productState,isAll});
		return vendorDao.vendorDoorProductStatistics(staffId,productState,isAll);
	}
	
	public String doorInitValid(Integer mainboardId,String mainboardNo,Integer eid,Integer doorIndex,Integer portNo){
		logger.info("[project:{}] [step:enter] [mainboardId:{}] [eid:{}] [doorIndex:{}] [portNo:{}]",
				new Object[] { WebConfig.projectName,mainboardId,eid, doorIndex,portNo});
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		/*Map<String, Object> result = vendorBatchDoorOpenEmpty(mainboardId, eid);
		logger.info("---result:{}",result);
		if("1".equals(result.get("tag"))){
			List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("dataMap");
			if(list.size()>0){
				Map<String, Object> map = list.get(0);
				if(map!=null){
					mainboardNo = (String) map.get("mainboardNo");
				}
			}
		}*/
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
	
	public String batchOpenEmptyDoor(Integer mainboardId,String mainboardNo,Integer eid){
		logger.info("[project:{}] [step:enter] [mainboardId:{}] [eid:{}]",
				new Object[] { WebConfig.projectName,mainboardId,eid});
		Map<String, Object> result = vendorBatchDoorOpenEmpty(mainboardId, eid);
		logger.info("---result:{}",result);
		JSONArray doorList = new JSONArray();
		/*if("1".equals(result.get("tag"))){
			List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("dataMap");
			if(list.size()>0){
				Map<String, Object> map = list.get(0);
				if(map!=null){
					mainboardNo = (String) map.get("mainboardNo");
				}
			}*/
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
		/*}else{
			logger.error("tag:{}",result.get("tag"));
			return result.get("tag").toString();
		}*/
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
		params.add(new BasicNameValuePair("doorList", doorList.toString()));
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/replenishmentEmptyOpenDoor.action", params);
		logger.info("postEvianApi:{}",postEvianApi);
		return postEvianApi;
	}
	
	public String mainboardLine(Integer mainboardId,String mainboardNo){
		logger.info("[project:{}] [step:enter] [mainboardId:{}] [mainboardNo:{}]",
				new Object[] { WebConfig.projectName,mainboardId, mainboardNo});
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
		params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/mainboardHashLine.action", params);
		return postEvianApi;
	}
	
	public String vendorAppCustomerConfirmReceipt(Integer eid,String guidStr){
		logger.info("[project:{}] [step:enter] [eid:{}] [guidStr:{}]",
				new Object[] { WebConfig.projectName,eid, guidStr});
		return vendorDao.vendorAppCustomerConfirmReceipt(eid, guidStr);
	}
	
	public List<Map<String,Object>> vendorAppCustomerDoorProductStatistics(Integer eid,Integer accountId,Boolean isAll){
		logger.info("[project:{}] [step:enter] [eid:{}] [accountId:{}] [isAll:{}]",
				new Object[] { WebConfig.projectName,eid, accountId,isAll});
		return vendorDao.vendorAppCustomerDoorProduct_statistics(eid, accountId,isAll);
	}
	
	public List<Map<String,Object>> vendorAppCustomerDoorstatistics(Integer eid,Integer accountId){
		logger.info("[project:{}] [step:enter] [eid:{}] [accountId:{}]",
				new Object[] { WebConfig.projectName,eid, accountId});
		return vendorDao.vendorAppCustomerDoorstatistics(eid, accountId);
	}
	
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerstatistics(Integer eid,Integer accountId){
		logger.info("[project:{}] [step:enter] [eid:{}] [accountId:{}]",
				new Object[] { WebConfig.projectName,eid, accountId});
		return vendorDao.vendorAppCustomerMainboardContainerstatistics(eid, accountId);
	}
	
	public String vendorAppCustomerMainboardContainerstatus(Integer mainboardId,Integer accountId,Integer containerStatus, String remark){
		logger.info("[project:{}] [step:enter] [mainboardId:{}] [accountId:{}] [containerStatus:{}] [remark:{}] ",
				new Object[] { WebConfig.projectName,mainboardId, accountId,containerStatus,remark});
		return vendorDao.vendorAppCustomerMainboardContainerstatus(mainboardId, accountId, containerStatus, remark);
	}
	
	public String vendorAppCustomerMainboardContainerAddressEdit(Integer mainboardId,Integer accountId,String shopContainerName, String location, String containerAddress, Integer communityId) {
		logger.info("[project:{}] [step:enter] [mainboardId:{}] [accountId:{}] [shopContainerName:{}] [location:{}] [containerAddress:{}] [communityId:{}]",
				new Object[] { WebConfig.projectName,mainboardId, accountId,shopContainerName,location,containerAddress,communityId});
		return vendorDao.vendorAppCustomerMainboardContainerAddressEdit(mainboardId, accountId, shopContainerName, location, containerAddress,communityId);
	}
	
	public List<Map<String,Object>> vendorAppCustomerMainboardContainerDoorStatistics(Integer eid,Integer accountId) {
		logger.info("[project:{}] [step:enter] [eid:{}] [accountId:{}]",
				new Object[] { WebConfig.projectName,eid, accountId});
		return vendorDao.vendorAppCustomerMainboardContainerDoorStatistics(eid, accountId);
	}
	
	public Map<String,Object> vendorSelectOrder(String beginTime,String endTime,String orderNo,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,String nickName,Integer doorIndex,Boolean isTest,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [orderNo:{}] [eid:{}] [mainboardNo:{}] [orderStatus:{}] [dataSourse:{}] [isPay:{}] [productName:{}] [shopName:{}] [nickName:{}] [doorIndex:{}] [isTest:{}] [openId:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] { WebConfig.projectName,beginTime, endTime,orderNo,eid,mainboardNo,orderStatus,dataSourse,isPay,productName,shopName,nickName,doorIndex,isTest,openId,PageIndex,PageSize,IsSelectAll});
		return vendorDao.vendorSelectOrder(beginTime, endTime, orderNo, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName,nickName, doorIndex,isTest,openId,PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String,Object> vendorBatchDoorOpenEmpty(Integer mainboardId,Integer eid){
		return vendorDao.vendorBatchDoorOpenEmpty(mainboardId, eid);
	}
	
	public String getNowDbName(){
		return vendorDao.getNowDbName();
	}
	
	public String vendorGisCommunityOperat(String tag,Integer communityId,Integer districtId,String location,String communityName,Integer eid,Integer accountId,String account,String remark) {
		logger.info("[project:{}] [step:enter] [tag:{}] [communityId:{}] [districtId:{}] [location:{}] [communityName:{}] [eid:{}] [accountId:{}] [account:{}] [remark:{}]",
				new Object[] { WebConfig.projectName,tag,communityId, districtId,location,communityName,eid,accountId,account,remark});
		return vendorDao.vendorGisCommunityOperat(tag,communityId, districtId,location, communityName, eid, accountId, account, remark);
	}
	
	public Map<String, Object> findAreaByDistrictId(Integer eid,Integer cityId,Integer districtId,Boolean isAudit,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[project:{}] [step:enter] [districtId:{}]",
				new Object[] { WebConfig.projectName,districtId});
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
			System.out.println("asdfasdfas = "+district.size());
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
		logger.info("[project:{}] [step:enter] [provinceId:{}] [cityId:{}] [districtId:{}]",
				new Object[] { WebConfig.projectName,provinceId, cityId,districtId});
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
		logger.info("[project:{}] [step:enter] [clientId:{}] [adContent:{}] [beginDate:{}] [dayQuantity:{}] [codeNo:{}] [orderMoney:{}] [communityIds:{}]",
				new Object[] { WebConfig.projectName,clientId, adContent,beginDate,dayQuantity,codeNo,orderMoney,communityIds});
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
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [orderId:{}] [isPay:{}] [orderStatus:{}] [orderNo:{}] [account:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}] ",
				new Object[] { WebConfig.projectName,beginTime, endTime,orderId,isPay,orderStatus,orderNo,account,PageIndex,PageSize,IsSelectAll});
		return vendorDao.vendorAdLedOrderSelect(beginTime, endTime, orderId, isPay, orderStatus, orderNo, account, PageIndex, PageSize, IsSelectAll);
	}
	
	/**
	 * 自动售货机用户代金券查询
	 * @param eid			企业id  LED广告代金券传 0
	 * @param clientId		客户id
	 * @return
	 */
	public List<Map<String, Object>> vendorCodeAppCustomerSelect(Integer eid,Integer clientId){
		logger.info("[project:{}] [step:enter] [eid:{}] [clientId:{}]",
				new Object[] { WebConfig.projectName,eid, clientId});
		return vendorDao.vendorCodeAppCustomerSelect(eid, clientId);
	}
	
	public List<VendorMainboard2> selectMainboardBymainboardNo(
			String mainboardNo) {
		logger.info("[project:{}] [step:enter] [mainboardNo:{}] ",
				new Object[] { WebConfig.projectName,mainboardNo});
		List<VendorMainboard2> VendorMainboardlist = vendorDao.selectMainboardBymainboardNo(mainboardNo);
		return VendorMainboardlist;
	}
	
	public List<Map<String, Object>> balanceProductidMainboardidAccountidCommunityidByhourSelect(Integer searchType,String beginTime,String endTime,Integer eid,Integer productId,Integer mainboardId,Integer accountId,Integer communityId){
		logger.info("[project:{}] [step:enter] [searchType:{}] [beginTime:{}] [endTime:{}] [eid:{}] [productId:{}] [mainboardId:{}] [accountId:{}] [communityId:{}] ",
				new Object[] { WebConfig.projectName,searchType,beginTime,endTime,eid,productId,mainboardId,accountId,communityId});
		return vendorDao.balanceProductidMainboardidAccountidCommunityidByhourSelect(searchType, beginTime, endTime, eid, productId, mainboardId, accountId, communityId);
	}
	
	public String allOnLineMainboards(){
		logger.info("[project:{}] [step:enter] ",
				new Object[] { WebConfig.projectName});
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/container/allOnLineMainboards.action", params);
		return postEvianApi;
	}
	
	public List<VendorMainboardContainer> selectAllMainboardContainer() {
		return vendorDao.selectAllMainboardContainer();
	}
	
	public Map<String, Object> vendorErrorLogOperat(Integer eid, Integer mainboardId, Integer dataSource,
			String exceptionMsg, String exceptionCode, String openId) {
		logger.info("[ent:{}] [eid:{}] [mainboardId:{}] [dataSource:{}] [exceptionMsg:{}] [exceptionCode:{}] [openId:{}]",
				new Object[] {"vendorErrorLogOperat",eid,mainboardId,dataSource,exceptionMsg,exceptionCode,openId});
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
	
	public List<Map<String, Object>> viewVisitSelect(String beginTime,String endTime,String mainboardNoMD5,Boolean isTest,Integer communityId,Integer accountId,Integer eid,String openId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[ent:{}] [beginTime:{}] [endTime:{}] [mainboardNoMD5:{}] [isTest:{}] [communityId:{}] [accountId:{}] [eid:{}] [openId:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] {"vendorErrorLogOperat",beginTime,endTime,mainboardNoMD5,isTest,communityId,accountId,eid,openId,PageIndex,PageSize,IsSelectAll});
		return vendorDao.viewVisitSelect(beginTime,endTime, mainboardNoMD5, isTest,communityId,accountId,eid,openId, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> replenishmentStatistics(Integer eid,String beginTime,String endTime,String account,String productName,String productCode){
		logger.info("[ent:{}] [eid:{}] [beginTime:{}] [endTime:{}] [account:{}] [productName:{}] [productCode:{}]",
				new Object[] {"vendorErrorLogOperat",eid,beginTime,endTime,account,productName,productCode});
		return vendorDao.replenishmentStatistics(eid, beginTime, endTime, account,productName,productCode);
	}
	
	public Map<String, Object> productSupplementSelect(String beginTime,String endTime,Integer eid,String account,String productCode,String productName,String shopName,Boolean isTest, Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[ent:{}] [beginTime:{}] [endTime:{}] [eid:{}] [account:{}] [productCode:{}] [productName:{}] [shopName:{}] [isTest:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] {"productSupplementSelect",beginTime,endTime,eid,account,productCode,productName,shopName,isTest,PageIndex,PageSize,IsSelectAll});
		return vendorDao.productSupplementSelect(beginTime, endTime, eid, account, productCode, productName,shopName,isTest, PageIndex, PageSize, IsSelectAll);
	}
	
	public String productSupplementConfirm(Integer supplementId,Integer eid,Integer accountId,Integer realQuantity,String remark,Integer productId) {
		logger.info("[ent:{}] [supplementId:{}] [eid:{}] [accountId:{}] [realQuantity:{}] [remark:{}] [productId:{}]",
				new Object[] {"productSupplementConfirm",supplementId,eid,accountId,realQuantity,remark,productId});
		return vendorDao.productSupplementConfirm(supplementId, eid, accountId, realQuantity, remark,productId);
	}
	
	public List<Map<String, Object>> vendorErrorLogStatistics(String beginTime,String endTime,Integer eid,Integer dataSource,String containerCode,Boolean isSend,Integer accountId,Boolean isDispose,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return vendorDao.vendorErrorLogStatistics(beginTime, endTime, eid, dataSource, containerCode, isSend, accountId, isDispose, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> repetitionBuySelect(String beginTime,String endTime,Boolean isSearchTimeForAuth,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer sortType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[ent:{}] [beginTime:{}] [endTime:{}] [isSearchTimeForAuth:{}] [eName:{}] [eid:{}] [mainboardNo:{}] [orderStatus:{}] [dataSourse:{}] [isPay:{}] [productName:{}] [shopName:{}] [doorIndex:{}] [isTest:{}] [buyTimesMoreThen:{}] [cellphone:{}] [sortType:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] {"repetitionBuySelect",beginTime,endTime,isSearchTimeForAuth,eName,eid,mainboardNo,orderStatus,dataSourse,isPay,productName,shopName,doorIndex,isTest,buyTimesMoreThen,cellphone,sortType,PageIndex,PageSize,IsSelectAll});
		return vendorDao.repetitionBuySelect(beginTime, endTime, isSearchTimeForAuth, eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, cellphone,sortType, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> vendorMainboardOrProductIdBuySelect(Integer statisticsType,String beginTime,String endTime,String eName,Integer eid,String mainboardNo,Integer orderStatus,Integer dataSourse,Boolean isPay,String productName,String shopName,Integer doorIndex,Boolean isTest,Integer buyTimesMoreThen,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[ent:{}] [statisticsType:{}] [beginTime:{}] [endTime:{}] [eName:{}] [eid:{}] [mainboardNo:{}] [orderStatus:{}] [dataSourse:{}] [isPay:{}] [productName:{}] [shopName:{}] [doorIndex:{}] [isTest:{}] [buyTimesMoreThen:{}] [cellphone:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}]",
				new Object[] {"repetitionBuySelect",statisticsType,beginTime,endTime,eName,eid,mainboardNo,orderStatus,dataSourse,isPay,productName,shopName,doorIndex,isTest,buyTimesMoreThen,cellphone,PageIndex,PageSize,IsSelectAll});
		return vendorDao.vendorMainboardOrProductIdBuySelect(statisticsType,beginTime, endTime,  eName, eid, mainboardNo, orderStatus, dataSourse, isPay, productName, shopName, doorIndex, isTest, buyTimesMoreThen, cellphone, PageIndex, PageSize, IsSelectAll);
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
		logger.info("[ent:{}] [eid:{}] [shopId:{}] [containerId:{}] [accountId:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}] ",
				new Object[] {"vendorReplenishmentPlanSelect",eid,shopId,containerId,accountId,PageIndex,PageSize,IsSelectAll});
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
		logger.info("[ent:{}] [planId:{}]  [mainboardId:{}]",
				new Object[] {"vendorReplenishmentPlanDetailsSelect",planId,mainboardId});
		return vendorDao.vendorReplenishmentPlanDetailsSelect(planId,mainboardId);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Integer insertVendorReplenishmentPlan(List<Integer> mainboardIds,Integer shopId,Integer containerId,String planName,Boolean isShopAll,String creater,JSONArray productJsonArray) {
		logger.info("[ent:{}] [mainboardIds:{}] [planName:{}] [isShopAll:{}] [creater:{}] [productJsonArray:{}]",
				new Object[] {"insertVendorReplenishmentPlan",mainboardIds,planName,isShopAll,creater,productJsonArray});
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
			Integer planId = vendorDao.insertVendorReplenishmentPlan(eid, containerId, shopId, planName, doorNum, isShopAll, creater);
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
		logger.info("[ent:{}] [mainboardIds:{}] [planId:{}] [planName:{}] [isShopAll:{}] [productJsonArray:{}]",
				new Object[] {"updateVendorReplenishmentPlan",mainboardIds,planId,planName,isShopAll,productJsonArray});
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
		logger.info("[ent:{}] [planId:{}] ",
				new Object[] {"removeVendorReplenishmentPlan",planId});
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
		logger.info("[ent:{}] [beginTime:{}] [endTime:{}] [eid:{}] [account:{}] [productCode:{}] [productName:{}] [shopName:{}] [isTest:{}] [remark:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}] ",
				new Object[] {"vendorAppMerchantProductStorageDetailSelect",beginTime,endTime,eid,account,productCode,productName,shopName,isTest,remark,PageIndex,PageSize,IsSelectAll});
		return vendorDao.vendorAppMerchantProductStorageDetailSelect(beginTime, endTime, eid, account, productCode, productName, shopName, isTest,remark, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> vendorDoorSelectGroupByProductId(Integer eid,Integer doorStatus,String productName,String productCode,String shortName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[ent:{}] [eid:{}] [doorStatus:{}] [productName:{}] [productCode:{}] [shortName:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}] ",
				new Object[] {"vendorDoorSelectGroupByProductId",eid,doorStatus,productName,productCode,shortName,PageIndex,PageSize,IsSelectAll});
		return vendorDao.vendorDoorSelectGroupByProductId(eid, doorStatus, productName, productCode, shortName, PageIndex, PageSize, IsSelectAll);
	}
	
	public Map<String, Object> vendorDoorSelectGroupByProductIdAndMainboardId(Integer eid,Integer productId,Integer doorStatus,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		logger.info("[ent:{}] [eid:{}] [doorStatus:{}] [productId:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}] ",
				new Object[] {"vendorDoorSelectGroupByProductIdAndMainboardId",eid,doorStatus,productId,PageIndex,PageSize,IsSelectAll});
		return vendorDao.vendorDoorSelectGroupByProductIdAndMainboardId(eid, productId, doorStatus, PageIndex, PageSize, IsSelectAll);
	}
	
	public List<VendorProductReplenishmentClass> selectVendorProductReplenishmentClass() {
		return vendorDao.selectVendorProductReplenishmentClass();
	}
	
	/**
	 * 查询当前订单开锁状态
	 * @param orderId
	 * @return
	 */
	public List<Map<String, Object>> selectUnlockingByOrderId(Integer orderId){
		logger.info("[ent:{}] [orderId:{}]",
				new Object[] {"selectUnlockingByOrderId",orderId});
		return vendorDao.selectUnlockingByOrderId(orderId);
	}
}
