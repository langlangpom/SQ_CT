package com.evian.sqct.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.evian.sqct.bean.shop.City;
import com.evian.sqct.bean.shop.FindShopModel;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.shop.ShopCode;
import com.evian.sqct.bean.shop.ShopTimeframe;

public interface IShopDao {

	/**
	 * 
	 * @return
	 */
	public String selectUserAllShop();

	/**
	 * 创建店铺
	 * 
	 * @return
	 */
	public String addShop(Integer shopId,Integer eid, String shopNo, String shopName,
			String address, String tel, String linkman, Integer cityId,
			Integer districtId, String location, String description,
			String pictureUrl, String shopType, String startTime,
			String endTime, String scopeDescription, Integer sendOnTime,
			Double minSendPrice, Double freight, String createUser,
			Boolean ifLine,Integer userId);
	
	/**
	 *
	 * 通过企业id和用户id查询水店
	 * 
	 * @param eid
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> selectShopByEidAndUserId(Integer accountId,Integer eid);
	
	/**
	 *
	 * 通过企业id和水店id查询水店
	 * 
	 * @param eid
	 * @param userId
	 * @return
	 */
	public List<FindShopModel> selectShopByEidAndShopId(Integer shopId,Integer eid);
	
	/**
	 * 更改店铺
	 * @param shop 店铺参数
	 * @return
	 */
	public Map<String, Object> updateShop(Shop shop);
	
	/**
	 * 选择区域
	 * @param eid
	 * @return
	 */
	public List<Map<String,Object>> selectAreaByEid(Integer eid);
	
	/**
	 * 根据城市ID和区域Id查询区域
	 * @param eid
	 * @return
	 */
	public City selectCityByCityIdAndDistrictId(Integer cityId,Integer districtId);
	
	/**
	 * 创建店铺后插入的关联用户表
	 * @param userId
	 * @param eid
	 * @param shopId
	 * @param createTime
	 * @param createUser
	 * @return
	 */
	public Integer inserteAuthUserShop(Integer userId,Integer eid,Integer shopId,Date createTime,String createUser);
	
	/**
	 * 查询店铺推广码
	 *
	 * @param eid
	 * @param shopId
	 * @return
	 */
	public List<ShopCode> selectShopCode(Integer eid,Integer shopId);


	public Integer upTimeframe(Integer eid,Integer shopId,String beginHour,String endHour);

	public Integer delTimeframe(Integer eid,Integer shopId);
	
	public List<ShopTimeframe> selectTimeframe(Integer eid,Integer shopId);
	
	/**
	 * 查询店铺管理商品
	 * @param eid
	 * @param shopId
	 * @return
	 */
	public List<Map<String, Object>> commodityManage(Integer eid,Integer shopId);
	
	/**
	 * 
	 * @param pid
	 * @param shopId
	 * @param recommend
	 * @param lineBit
	 * @return
	 */
	public String productLine(Integer pid,Integer shopId,Boolean recommend,Integer lineBit);
	
	Shop selectEidByShopId(Integer shopId);
	
	/**
	 * 修改商品序号
	 */
	Integer updateProductSort(Integer sort,Integer pid,Integer eid);
	
	/**
	 * 修改商品上下架
	 * @param enabled
	 * @param pid
	 * @param eid
	 * @return
	 */
	Integer updateProductEnabled(Boolean enabled,Integer pid,Integer eid);
	
	/**
	 * 修改商品价格
	 * @param vipPrice
	 * @param pid
	 * @param eid
	 * @return
	 */
	Integer updateProductPrice(Double vipPrice,Integer pid,Integer eid);
	
	/**
	 * 查询公众号二维码
	 * @return
	 */
	String selectGZHQRCode(Integer eid);
	
	/**
	 * 小程序推客二维码
	 * @param eid
	 * @param clientId
	 * @return
	 */
	String selectXCXQRCode(Integer eid,String account);
	
	/**
	 * 给店铺添加关联商品
	 * @param shopId
	 * @param pid
	 * @param shopPrice
	 * @return
	 */
	Integer insertShopProduct(Integer shopId,Integer pid,Double shopPrice);
	
	/**
	 * 删除店铺商品
	 * @param shopId
	 * @param pid
	 * @return
	 */
	Integer removeShopProduct(Integer shopId,Integer pid);
	
	/**
	 * 修改店铺商品价格
	 * @param shopId
	 * @param pid
	 * @param shopPrice
	 * @return
	 */
	Integer updateShopProductPrice(Integer shopId,Integer pid,Double shopPrice);
	
	/**
	 * app商户端公告
	 * @param eid
	 * @return
	 */
	List<Map<String, Object>> appMerchantNotify(Integer eid);
	
	/**
	 * 手机商户端门店今日统计
	 * @param accountId 手机商户端账号ID
	 * @param shopId 店铺ID
	 * @return
	 */
	Map<String, Object> appMerchantTodayStatistics(Integer accountId,Integer shopId);
	
	/**
	 * 操作店铺上下线
	 * @param isLine
	 * @return
	 */
	Integer updateShopIsLine(Boolean ifLine,Integer eid,Integer shopId);
	

	/**
	 * 手机商户端门店今日统计
	 * @param accountId 手机商户端账号ID
	 * @param shopId 店铺ID
	 * @return
	 */
	Map<String, Object> shopLeagueClientsSelect(String beginTime,String endTime,Integer shopId,Integer eid,String nickName,String cellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 电子票有效剩余量
	 * @param clientId
	 * @param type
	 * @return
	 */
	List<Map<String,Object>> getTicketAccount(Integer clientId,Integer type);
	
	/**
	 * 电子票消费列表
	 * @param eid
	 * @param clientId
	 * @return
	 */
	List<Map<String,Object>> ticketSelectMx(Integer eid,Integer clientId);
	
	/**
	 * 
	 * @param clientId
	 * @return
	 */
	List<Map<String,Object>> selectMyVouchers(Integer clientId);
	
	/**
	 * 查询店铺
	 * @return
	 */
	Map<String, Object> selectShopByShopId(Integer shopId);
	
	/**
	 * 店铺经营的品牌
	 * @param shopId
	 * @return
	 */
	List<Map<String, Object>> selectShopBrandByShopId(Integer shopId);
	
	
	Integer updateShop(Integer shopId,String pictureUrl,String tel,String shopName,String startTime,String	endTime);
}


