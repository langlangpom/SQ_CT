package com.evian.sqct.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.shop.FindShopModel;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.shop.ShopCode;
import com.evian.sqct.bean.shop.ShopTimeframe;
import com.evian.sqct.bean.user.UserModel;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.dao.IShopDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.util.HttpClientUtilOkHttp;
import com.evian.sqct.util.WebConfig;

@Service
@Transactional
public class BaseShopManager {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("shopDao")
	private IShopDao shopDao;
	
	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;
	
	@Autowired
	@Qualifier("orderDao")
	private IOrderDao orderDao;

	public String addShop(Integer eid, String shopNo, String shopName,
			String address, String tel, String linkman, Integer cityId,
			Integer districtId, String location, String description,
			String pictureUrl, String shopType, String startTime,
			String endTime, String scopeDescription, Integer sendOnTime,
			Double minSendPrice, Double freight, String createUser,
			Boolean ifLine,Integer userId) {
		
		if(ifLine==null){
			ifLine=false;
		}
		if(description==null){
			description="";
		}
		if(shopType==null){
			shopType="";
		}
		if(scopeDescription==null){
			scopeDescription="";
		}
		if(minSendPrice==null){
			minSendPrice=0.00;
		}
		if(freight==null){
			freight=0.00;
		}
		String addShop = shopDao.addShop(null,eid, shopNo, shopName, address, tel, linkman,
				cityId, districtId, location, description, pictureUrl,
				shopType, startTime, endTime, scopeDescription, sendOnTime,
				minSendPrice, freight, createUser, ifLine,userId);
		Integer shopId =null;
		try {
			shopId = Integer.parseInt(addShop);
			UserModel selectUserByName = userDao.selectUserByName(createUser);
			shopDao.inserteAuthUserShop(selectUserByName.getUserId(), eid, shopId, new Date(), createUser);
		} catch (Exception e) {
			
		}
		return addShop;
	}
	
	public List<Map<String,Object>> findShopByEidAndUserId(Integer accountId,Integer eid){
		return shopDao.selectShopByEidAndUserId(accountId, eid);
	}
	
	public List<Map<String,Object>> findAreaByEid(Integer eid){
		return shopDao.selectAreaByEid(eid);
	}
	
	public List<FindShopModel> findShopByEidAndShopId(Integer shopId,Integer eid){
		return shopDao.selectShopByEidAndShopId(shopId, eid);
	}
	
	public Map<String, Object> updateShop(Shop shop){
		if(shop.getIfLine()==null){
			shop.setIfLine(false);
		}
		if(shop.getDescription()==null){
			shop.setDescription("");
		}
		if(shop.getShopType()==null){
			shop.setShopType("");
		}
		if(shop.getScopeDescription()==null){
			shop.setScopeDescription("");
		}
		if(shop.getMinSendPrice()==null){
			shop.setMinSendPrice(0.00);
		}
		if(shop.getFreight()==null){
			shop.setFreight(0.00);
		}
		return shopDao.updateShop(shop);
	}
	
	public List<Map<String, Object>> findShopCode(Integer eid,Integer shopId){
		List<ShopCode> findShopCode =shopDao.selectShopCode(eid, shopId);
		List<Map<String, Object>> shopCodes = new ArrayList<Map<String, Object>>();
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		for (ShopCode shopCode : findShopCode) {
			Map<String, Object> map = new HashMap<String, Object>();
			if(shopCode.getPicUrl()!=null){
				map.put("picUrl", "https://"+selectSysConfig.get("文件管理域名")+shopCode.getPicUrl());
				shopCodes.add(map);
			}
		}
		return shopCodes;
	}
	
	public String upTimeframe(Integer eid,Integer shopId,JSONArray times){
		
		if(times!=null){
//			Integer delTimeframe = shopDao.delTimeframe(eid, shopId);
				
//			JSONArray jsonArray = timeframe.getJSONArray("times");
			for (int i = 0; i < times.size(); i++) {
				JSONObject jsonObject2 = times.getJSONObject(i);
				String beginHour;
				String endHour;
				try {
					beginHour = jsonObject2.getString("beginHour");
					endHour = jsonObject2.getString("endHour");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return "2";
				}
				shopDao.upTimeframe(eid,shopId ,beginHour,endHour );
			}
		}else{
			return "2";
		}
		return "1";
	}
	
	public List<ShopTimeframe> findTimeframe(Integer eid,Integer shopId){
		List<ShopTimeframe> selectTimeframe = shopDao.selectTimeframe(eid, shopId);
		return selectTimeframe;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> commodityManage(Integer eid,Integer shopId){
		List<Map<String, Object>> commodityManage = shopDao.commodityManage(eid, shopId);
		Map<Integer,Map<String,Object>> classDic=new HashMap<Integer, Map<String,Object>>();

		for (Map<String, Object> map : commodityManage) {
			int key=(Integer)map.get("cid");
			Map<String,Object> productMap=new HashMap<String,Object>();
			productMap.put("pid", (Integer)map.get("pid"));
			productMap.put("pname", map.get("pname").toString());
			productMap.put("pictureUrl",map.get("pictureUrl").toString());
			productMap.put("price", Double.valueOf(map.get("vipPrice").toString()));
			productMap.put("ifLine", (Integer)map.get("lineId")>0);
			productMap.put("sort", (Integer)map.get("sort"));
			if(!classDic.containsKey(key))
			{
				Map<String,Object> classMap=new HashMap<String,Object>();
				classMap.put("cid", key);
				classMap.put("className", map.get("className").toString());
				List<Map<String,Object>> products= new ArrayList<Map<String,Object>>();
				products.add(productMap);
				classMap.put("products",products);
				classDic.put(key, classMap);
			}else
				((ArrayList<Map<String,Object>>)classDic.get(key).get("products")).add(productMap);
			
		}
		
		List<Map<String, Object>> classResults=new ArrayList<Map<String, Object>>();
		for (Integer key : classDic.keySet()) {
			classResults.add(classDic.get(key));
		}
		return classResults;
	}
	
	public String productLine(Integer pid,Integer shopId,Boolean recommend,Integer lineBit){
		return shopDao.productLine(pid, shopId, recommend, lineBit);
	}
	
	/**
	 * 修改商品属性
	 * @param pid
	 * @param eid
	 * @param enabled 上下架（不是真正的上下架 只是将该商品从店铺里移除）
	 * @param vipPrice 价格
	 * @param sort 排序
	 * @param type 0 是上下架 1 是价格 2是排序
	 * @return
	 */
	public Integer updateProductProperty(Integer shopId,Integer pid,Integer eid,Boolean enabled,Double vipPrice,Integer sort,int type) {
		int result = 0;
		if(type==0) {
//			result = shopDao.updateProductEnabled(enabled, pid, eid);
			if(enabled) {
				result = shopDao.insertShopProduct(shopId, pid, vipPrice);
			}else {
				result = shopDao.removeShopProduct(shopId, pid);
			}
		}else if(type==1) {
//			result = shopDao.updateProductPrice(vipPrice, pid, eid);
			result = shopDao.updateShopProductPrice(shopId, pid, vipPrice);
		}else if(type==2) {
			result = shopDao.updateProductSort(sort, pid, eid);
		}
		return result;
	}
	
	/**
	 * 查询企业二维码
	 * @return
	 */
	public Map<String, Object> selectEnterpriseQRCode(Integer eid,String account){
		Map<String, Object> map = new HashMap<String, Object>();
		String liteappShearPic = shopDao.selectGZHQRCode(eid);
		map.put("liteappShearPic", liteappShearPic);
		map.put("miniProgramPic", "");
		if(!StringUtils.isEmpty(account)) {
			String miniProgramPic = shopDao.selectXCXQRCode(eid, account);
			Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
			
			map.put("miniProgramPic", "https://"+selectSysConfig.get("后台域名")+miniProgramPic);
		}
		return map;
	}
	
	public List<Map<String, Object>> appMerchantNotify(Integer eid) {
		logger.info("[project:{}] [step:enter] [eid:{}] ",
				new Object[] { WebConfig.projectName, eid,});
		return shopDao.appMerchantNotify(eid);
	}
	
	public Map<String, Object> appMerchantTodayStatistics(Integer accountId,Integer shopId) {
		logger.info("[project:{}] [step:enter] [accountId:{}] [shopId:{}]",
				new Object[] { WebConfig.projectName, accountId,shopId});
		return shopDao.appMerchantTodayStatistics(accountId, shopId);
	}
	
	public Integer updateShopIfLine(Boolean ifLine,Integer eid,Integer shopId) {
		logger.info("[project:{}] [step:enter] [ifLine:{}] [eid:{}] [shopId:{}]",
				new Object[] { WebConfig.projectName, ifLine,eid,shopId});
		return shopDao.updateShopIsLine(ifLine, eid,shopId);
	}
	

	public Map<String, Object> shopLeagueClientsSelect(String beginTime,String endTime,Integer shopId,Integer eid,String nickName,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		logger.info("[project:{}] [step:enter] [beginTime:{}] [endTime:{}] [shopId:{}] [eid:{}] [nickName:{}] [cellphone:{}] [PageIndex:{}] [PageSize:{}] [IsSelectAll:{}] ",
				new Object[] { WebConfig.projectName, beginTime,endTime,shopId,eid,nickName,cellphone,PageIndex,PageSize,IsSelectAll});
		return shopDao.shopLeagueClientsSelect(beginTime, endTime, shopId, eid, nickName, cellphone, PageIndex, PageSize, IsSelectAll);
	}
	
	public List<Map<String,Object>> getTicketAccount(Integer clientId,Integer type){
		logger.info("[project:{}] [step:enter] [clientId:{}] [type:{}] ",
				new Object[] { WebConfig.projectName, clientId, type});
		return shopDao.getTicketAccount(clientId, type);
	}
	
	public List<Map<String,Object>> ticketSelectMx(Integer eid,Integer clientId,Integer shopId){
		logger.info("[project:{}] [step:enter] [eid:{}] [clientId:{}] [shopId:{}]",
				new Object[] { WebConfig.projectName,eid, clientId, shopId});
		List<Map<String, Object>> ticketAccount = shopDao.ticketSelectMx(eid,clientId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		if(shopId==null) {
			return ticketAccount;
		}else {
			for (Map<String, Object> map : ticketAccount) {
				Object shopIdobj = map.get("shopId");
				if(shopIdobj!=null) {
					Integer sId = (Integer)shopIdobj;
					if(sId.intValue()==shopId.intValue()) {
						result.add(map);
					}
				}
			}
			return result;
		}
	}
	

	public List<Map<String,Object>> selectMyVouchers(Integer clientId){
		logger.info("[project:{}] [step:enter] [clientId:{}]",
				new Object[] { WebConfig.projectName, clientId});
		return shopDao.selectMyVouchers(clientId);
	}
	
	public Map<String, Object> selectShopAndBrand(Integer shopId){
		logger.info("[project:{}] [step:enter] [shopId:{}]",
				new Object[] { WebConfig.projectName, shopId});
		Map<String, Object> shop = shopDao.selectShopByShopId(shopId);
		if(shop!=null) {
			List<Map<String, Object>> brand = shopDao.selectShopBrandByShopId(shopId);
			shop.put("power", "powered by 水趣 4001114588-1");
			shop.put("brands", brand);
			return shop;
		}else {
			return null;
		}
	}
	
	public Integer updateShop(Integer shopId,String pictureUrl,String tel,String shopName,String startTime,String	endTime) {
		logger.info("[project:{}] [step:enter] [shopId:{}] [pictureUrl:{}] [tel:{}] [shopName:{}] [startTime:{}] [endTime:{}]",
				new Object[] { WebConfig.projectName, shopId,pictureUrl,tel,shopName,startTime,endTime});
		return shopDao.updateShop(shopId, pictureUrl, tel, shopName,startTime,endTime);
	}
	
}
