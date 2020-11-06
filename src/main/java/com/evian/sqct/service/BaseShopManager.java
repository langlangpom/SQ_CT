package com.evian.sqct.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.shop.ShopCode;
import com.evian.sqct.bean.shop.ShopTimeframe;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageAppMerchantTodayStatisticsDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageShopLeagueClientsSelectDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageShopSelectDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcDisParkGetTicketAccountDTO;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.user.UserModel;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.*;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.HttpClientUtilOkHttp;
import com.evian.sqct.wxPay.EnterprisePayByLooseChangeBean;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BaseShopManager extends BaseManager{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("shopDao")
	private IShopDao shopDao;
	
	@Autowired
	@Qualifier("productDao")
	private IProductDao productDao;
	
	@Autowired
	@Qualifier("userDao")
	private IUserDao userDao;
	
	@Autowired
	@Qualifier("orderDao")
	private IOrderDao orderDao;


	@Autowired
	@Qualifier("enterpriseDao")
	private IEnterpriseDao enterpriseDao;

	@Autowired
	@Qualifier("entityCardDao")
	private IEntityCardDao entityCardDao;
	
	@Autowired
	private BasePayManager basePayManager;

	@Autowired
	private BaseEnterpriseManager baseEnterpriseManager;

	@Autowired
	private WxPayService wxPayService;

	@Transactional(rollbackFor=Exception.class)
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
	
	public Map<String,Object> findShopByEidAndShopId(ProcBackstageShopSelectDTO dto){
		return shopDao.selectShopByEidAndShopId(dto);
	}

	@Transactional(rollbackFor=Exception.class)
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

	@Transactional(rollbackFor=Exception.class)
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

	/**
	 * 查询店铺商品 跟前端展示一样
	 * @param eid
	 * @param shopId
	 * @return
	 */
	public List<Map<String, Object>> commodityManage(Integer eid,Integer shopId){

		// 查询商品
		List<Map<String, Object>> commodityManage = shopDao.commodityManage(eid, shopId);

		if(commodityManage==null||commodityManage.size()==0){
			return new ArrayList<>();
		}

		Map<Integer,Map<String,Object>> classDic=new HashMap<Integer, Map<String,Object>>();
		StringBuilder pids = new StringBuilder();

		for (Map<String, Object> map : commodityManage) {
			Map<String,Object> productMap=new HashMap<String,Object>();
			Object pid = map.get("pid");
			if(pid!=null){
				pids.append(pid+",");
			}
			productMap.put("pid", pid);
			productMap.put("pname", map.get("pname"));
			productMap.put("pictureUrl",map.get("pictureUrl"));
			productMap.put("price", map.get("vipPrice"));
			productMap.put("ifLine", map.get("isEnabled"));
			productMap.put("sort", map.get("sort"));
			int key=(Integer)map.get("cid");
			if(classDic.containsKey(key)) {
				((List<Map<String,Object>>)classDic.get(key).get("products")).add(productMap);
			}else {
				Map<String,Object> classMap=new HashMap<String,Object>();
				classMap.put("cid", key);
				classMap.put("className", map.get("className").toString());
				List<Map<String,Object>> products= new ArrayList<Map<String,Object>>();
				products.add(productMap);
				classMap.put("products",products);
				classDic.put(key, classMap);
			}

			Integer ticketId = (Integer) map.get("ticketId");
			// 大于0代表是电子票套餐
			if(ticketId>0){
				productMap.put("ifTicketSetMeal",true);
			}else{
				productMap.put("ifTicketSetMeal",false);
			}
			productMap.put("hashTicket",map.get("hashTicket"));
		}
		// 去除最后一个字符 7,88,258, 的,
		pids = pids.deleteCharAt(pids.length() - 1);
		System.out.println("pids = "+pids);

		// 查询商品库存
		Map<Integer, Map<String, Object>> batchProductStock = productDao.getBatchProductStock(shopId, pids.toString());

		List<Map<String, Object>> classResults=new ArrayList<Map<String, Object>>();
		for (Integer key : classDic.keySet()) {
			Map<String, Object> stringObjectMap = classDic.get(key);
			List<Map<String,Object>> products = ((List<Map<String,Object>>)classDic.get(key).get("products"));
			for (Map<String,Object> p:products) {
				Integer pid = (Integer) p.get("pid");
				Map<String, Object> repertory = batchProductStock.get(pid);
				p.put("repertoryNum",99999);
				p.put("tsId",0);
				if(repertory!=null){
					p.put("repertoryNum",repertory.get("stockNum"));
					p.put("tsId",repertory.get("tsId"));
				}
			}
			classResults.add(stringObjectMap);
		}
		
		
		return classResults;
	}

	@Transactional(rollbackFor=Exception.class)
	public String productLine(Integer pid,Integer shopId,Boolean recommend,Integer lineBit){
		return shopDao.productLine(pid, shopId, recommend, lineBit);
	}
	
	/**
	 * 修改商品属性
	 * @param pid
	 * @param eid
	 * @param enabled // 上下架（不是真正的上下架 只是将该商品从店铺里移除）
	 * @param enabled 上下架（2019-05-17 对应店铺的商品上下架）
	 * @param vipPrice 价格
	 * @param sort 排序
	 * @param type 0 是上下架 1 是价格 2是排序
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer updateProductProperty(Integer shopId,Integer pid,Integer eid,Boolean enabled,Double vipPrice,Integer sort,int type) {
		int result = 0;
		if(type==0) {
//			result = shopDao.updateProductEnabled(enabled, pid, eid);
			/*if(enabled) {
				result = shopDao.insertShopProduct(shopId, pid, vipPrice);
				
			}else {
				result = shopDao.removeShopProduct(shopId, pid);
				
			}*/
			shopDao.updateShopProductEnabled(enabled, pid, shopId);
		}else if(type==1) {
//			result = shopDao.updateProductPrice(vipPrice, pid, eid);
			result = shopDao.updateShopProductPrice(shopId, pid, vipPrice);
		}else if(type==2) {
			result = shopDao.updateProductSort(sort, pid, eid);
		}
		return result;
	}
	
	/**
	 * 添加店铺商品
	 * @param shopId
	 * @param pid
	 * @param vipPrice
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Integer insertShopProduct(Integer shopId,Integer pid,Double vipPrice,Integer cid) {
		// 李丹：反正就插入这个表就行了，不会有问题的。
		if(cid!=null) {
			List<Map<String, Object>> mapping_class = productDao.e_product_mapping_class(cid, pid);
			if(mapping_class.size()==0) {
				productDao.insertProductClass(cid, pid);
			}
		}
		
		List<Map<String, Object>> shopProduct = shopDao.selectShopProduct(shopId, pid);
		if(shopProduct.size()>0) {
			return 0;
		}
		
		
		return shopDao.insertShopProduct(shopId, pid, vipPrice);
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
			if(!StringUtils.isEmpty(miniProgramPic)) {
				Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
				map.put("miniProgramPic", "https://"+selectSysConfig.get("后台域名")+miniProgramPic);
			}
		}
		return map;
	}
	
	public List<Map<String, Object>> appMerchantNotify(Integer eid) {
		return shopDao.appMerchantNotify(eid);
	}

	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> appMerchantTodayStatistics(ProcBackstageAppMerchantTodayStatisticsDTO dto) {
		Map<String, Object> map = shopDao.appMerchantTodayStatistics(dto);
		if(!"1".equals(map.get("TAG"))) {
			throw new ResultException((String) map.get("TAG"));
		}
		Object result0 = map.get("result0");
		if(result0 !=null){
			List<Map<String,Object>> list = (List<Map<String,Object>>) result0;
			if(list.size()>0){
				return list.get(0);
			}
		}
		return new HashMap<>();
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Integer updateShopIfLine(Boolean ifLine,Integer eid,Integer shopId) {
		return shopDao.updateShopIsLine(ifLine, eid,shopId);
	}
	

	public Map<String, Object> shopLeagueClientsSelect(ProcBackstageShopLeagueClientsSelectDTO dto) {
		return shopDao.shopLeagueClientsSelect(dto);
	}
	
	public Map<String,Object> getTicketAccount(ProcDisParkGetTicketAccountDTO dto){
		return shopDao.getTicketAccount(dto);
	}
	
	public List<Map<String,Object>> ticketSelectMx(Integer eid,Integer clientId,Integer shopId){
		List<Map<String, Object>> ticketAccount = shopDao.ticketSelectMx(eid,clientId);
		if(shopId==null) {
			return ticketAccount;
		}else {
			return ticketAccount.stream().filter(map-> map.get("shopId")==null?false:(Integer)map.get("shopId")==shopId.intValue()).collect(Collectors.toList());
		}
	}
	

	public List<Map<String,Object>> selectMyVouchers(Integer clientId,Integer eid){
		return shopDao.selectMyVouchers(clientId,eid);
	}
	
	public Map<String, Object> selectShopAndBrand(Integer shopId){
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
	@Transactional(rollbackFor=Exception.class)
	public Integer updateShop(Integer shopId,String pictureUrl,String tel,String shopName,String startTime,String	endTime,String address,String location) {
		return shopDao.updateShop(shopId, pictureUrl, tel, shopName,startTime,endTime,address,location);
	}
	
	public List<Map<String, Object>> selectEidProduct(Integer eid,String pcode,String pname,String property,String shopName,Boolean enabled,Integer shopId,Integer pid,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
		return shopDao.selectEidProduct(eid, pcode, pname, property, shopName, enabled, shopId, pid, PageIndex, PageSize, IsSelectAll);
	}
	

	/** 129 地址坐标是否在水店围栏内  Json */
	public String validateShopWL(String location, Integer shopId,String authorizer_appid) {
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("shopId", shopId.toString()));
		params.add(new BasicNameValuePair("authorizer_appid", authorizer_appid));
		String webContent = HttpClientUtilOkHttp.postEvianApi(UrlManage.getShuiqooApiUrl()+ "/shop/validateShopWL.action", params);
		return webContent;
	}

	@Transactional(rollbackFor=Exception.class)
	public String Proc_Backstage_product_stock_num_init(Integer eid,Integer pid,Integer shopId,Integer initStockNum,Integer saleStockNum,String createUser){
		return shopDao.Proc_Backstage_product_stock_num_init(eid, pid, shopId, initStockNum, saleStockNum, createUser);
	}

	@Transactional(rollbackFor=Exception.class)
	public String Proc_Backstage_product_stock_num_reInit(Integer eid,Integer tsId){
		return shopDao.Proc_Backstage_product_stock_num_reInit(eid, tsId);
	}

	/**
	 * 配送费提现账户总账
	 * @param shopId
	 * @param eid
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> generalLedgerOfDeliveryFeeWithdrawalAccount(Integer shopId,Integer eid){
		return shopDao.PROC_ZH_DisCosts_SELECT_ShopAccount(shopId, eid);
	}

	

	/**
	 * 配送提现
	 * @param eid
	 * @param clientId
	 * @param shopId
	 * @param account
	 * @param cashOut
	 * @param cashOutNo
	 * @param remark
	 * @param appId
	 * @param openId
	 * 不回滚
	 */
	public void withdrawalOfDeliveryFee(Integer eid, Integer clientId, Integer shopId, String account, Double cashOut, String remark,String appId,String openId,String ip,String APPUserName) throws Exception{
		EEnterpriseWechatliteapp wechatliteapp = baseEnterpriseManager.selectEnterpriseEidByAppId(appId);
		if(wechatliteapp==null||StringUtils.isBlank(wechatliteapp.getMchId())||StringUtils.isBlank(wechatliteapp.getPartnerKey())){
			logger.error("wechatliteapp:{}",wechatliteapp);
			// 企业支付信息错误
			throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_ENTERPRISE_PAY);
		}
		// 校验提现参数
		verifyWithdrawDepositParam();

		Map<String, Object> orderNoMap = entityCardDao.Proc_DisPark_Get_OrderNo(13, 1);
		if(orderNoMap==null||orderNoMap.get("BH")==null){
			throw new ResultException("生成单号错误单号");
		}
		String cashOutNo = (String) orderNoMap.get("BH");
		Map<String, Object> applicationForWithdrawal = basePayManager.applicationForWithdrawal(eid, clientId, shopId, account, cashOut, cashOutNo, remark,APPUserName);
		String return_message = (String) applicationForWithdrawal.get("RETURN_message");

		if(!"1".equals(return_message)){
			logger.error("applicationForWithdrawal:{}",return_message);
			throw new ResultException(return_message);
		}
		Integer CashOutID = (Integer) applicationForWithdrawal.get("CashOutID");

		Map<String, Object> ifParamValue = baseEnterpriseManager.voluntarilyWithdrawDepositNotAudit(eid);
		boolean ifPayment = false;
		Integer iStatus = -1;
		String payment_no = "";
		String SH_remark = "";
		if(ifParamValue!=null){
			String paramValue = (String) ifParamValue.get("paramValue");
			if("1".equals(paramValue)){
				// 开始提现
				EnterprisePayByLooseChangeBean w = new EnterprisePayByLooseChangeBean();
				UUID uuid=UUID.randomUUID();
				String str = uuid.toString();
				String uuidStr=str.replace("-", "");

				BigDecimal bigDecimal = new BigDecimal(cashOut);
				BigDecimal big100 = new BigDecimal("100");
				String cashOutStr = String.valueOf(big100.multiply(bigDecimal).intValue());
				w.setMch_appid(appId);
				w.setMchid(wechatliteapp.getMchId());
				w.setNonce_str(uuidStr);
				w.setPartner_trade_no(cashOutNo);
				w.setOpenid(openId);
				w.setCheck_name("NO_CHECK");
				w.setAmount(cashOutStr);
				w.setDesc("提现");
				w.setSpbill_create_ip(ip);
				w.setAppKey(wechatliteapp.getPartnerKey());
				String pay = wxPayService.enterprisePayByLooseChange(w);
				logger.info("提现到零钱返回结果{}",pay);

				Map<String, String> prepayMap = wxPayService.xmlToMap(pay);

				if(!pay.contains(wxPayService.ERR_CODE)){
					ifPayment = true;
					iStatus =1;
					payment_no = prepayMap.get("payment_no");
				}else{
					String errCode = prepayMap.get(wxPayService.ERR_CODE);
					if(wxPayService.SUCCESS.equals(errCode)){
						ifPayment = true;
						iStatus =1;
						payment_no = prepayMap.get("payment_no");
					}else{
						SH_remark = prepayMap.get("err_code_des");
						iStatus = -1;
					}
				}
			}else{
				// 提交审核
				iStatus = 2;
			}
		}else{
			// 提交审核
			iStatus = 2;
		}


		String applicationForWithdrawalRecord = basePayManager.applicationForWithdrawalRecord(CashOutID, cashOutNo, ifPayment, iStatus, payment_no, SH_remark);
		if(!"1".equals(applicationForWithdrawalRecord)){
			logger.error("applicationForWithdrawalRecord:{}",applicationForWithdrawalRecord);
			throw new RuntimeException(applicationForWithdrawalRecord);
		}

		if(iStatus ==2){
			throw new ResultException("提现申请已提交");
		}
	}


	/**
	 * 校验提现参数
	 */
	private void verifyWithdrawDepositParam(){

	}

	public List<Map<String,Object>> withdrawDepositOrderSelect(Integer eid,Integer shopId,String begDate,String endDate,String RE_TAG, Integer PageIndex, Integer PageSize){
		PageIndex = ((PageIndex-1) *PageSize);
		return shopDao.PROC_ZH_DistributionCosts_order_SELECT(eid, shopId, begDate, endDate, RE_TAG,PageIndex,PageSize);
	}
	public List<Map<String,Object>> withdrawDepositRecord(Integer eid,Integer shopId,String begDate,String endDate,String RE_TAG, Integer PageIndex, Integer PageSize){
		PageIndex = ((PageIndex-1) *PageSize);
		return shopDao.PROC_ZH_DistributionCosts_CashOut_SELECT(eid, shopId, begDate, endDate, RE_TAG,PageIndex,PageSize);
	}


}
