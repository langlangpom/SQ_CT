package com.evian.sqct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.order.EAppMerchantOrderSend;
import com.evian.sqct.bean.order.Order;
import com.evian.sqct.bean.order.OrderDetail;
import com.evian.sqct.bean.order.OrderProductInfo;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.user.*;
import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.bean.util.WXHB;
import com.evian.sqct.bean.vendor.*;
import com.evian.sqct.dao.*;
import com.evian.sqct.service.*;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.evian.sqct.util.XmlStringUtil;
import com.evian.sqct.wxHB.WxPayHB;
import com.evian.sqct.wxPay.APPWxPayBean;
import com.evian.sqct.wxPay.APPWxPayUtils;
import com.qiniu.common.QiniuException;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqCtApplicationTests {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WxPayHB wxph;

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IVendorDao vendorDao;
	
	@Autowired
	private IShopDao shopDao;
	
	@Autowired
	private IGroupBuyDao groupBuyDao;

	@Autowired
	private IActivityDao activityDao;

	@Autowired
	private IEntityCardDao entityCardDao;
	
	@Autowired
	private BaseVendorManager vendorManager;
	
	@Autowired
	private BaseWxHBManager baseWxHBManager;
	
	@Autowired
	private BaseShopManager baseShopManager;
	
	@Autowired
	private BaseOrderManager baseOrderManager;
	
	@Autowired
	private BaseUserManager baseUserManager;

	@Autowired
	private BaseActivityManager baseActivityManager;

	@Autowired
	private BaseGroupBuyManager groupBuyManager;

	@Autowired
	private JpushShangHuService jpushShangHuService;


	@Autowired
	private BaseLedManager ledManager;

	@Autowired
	private Environment env;


	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void contextLoads() {
		System.out.println(userDao.getNowDbName());
	}
	
	@Test
	public void test001() {
		System.out.println(vendorDao.getNowDbName());
	}
	
	@Test
	public void test02() {
		List<GisProvince> findAllProvince = vendorDao.findAllProvince();
		System.out.println(findAllProvince);
	}
	
	@Test
	public void test11() {
		String allOnLineMainboards = vendorManager.allOnLineMainboards();
		System.out.println(allOnLineMainboards);
	}
	
	@Test
	public void test12() {
		List<VendorMainboardContainer> selectAllMainboardContainer = vendorManager.selectAllMainboardContainer();
		System.out.println(selectAllMainboardContainer);
	}
	
	@Test
	public void test13() {
		/*List<Map<String, Object>> vendorErrorLogSelect = vendorDao.vendorErrorLogSelect(null, null,null, null, null, null, null, null, null, null, null, null);
		System.out.println(vendorErrorLogSelect);*/
	}
	
	/*@Test
	public void test03() {
		List<GisCommunity> findCommunityBydistrict = vendorDao.findCommunityBydistrict(1772);
		System.out.println(findCommunityBydistrict);
	}
	
	@Test
	public void test04() {
		Map<String, Object> findArea = vendorManager.findArea(0, 0, 0);
		System.out.println(findArea);
	}
	
	@Test
	public void test05() {
		Map<String, Object> findAreaByDistrictId = vendorManager.findAreaByDistrictId(0);
		String jsonString = JSONObject.toJSONString(findAreaByDistrictId);
		System.out.println(jsonString);
	}*/

	@Test
	public void test06() {
		Integer[] a = {1,2,3};
		List<GisCommunity> v = vendorDao.findCommunityByCommunityId("1,2,3");
		System.out.println(v);
		
		
	}
	
	@Test
	public void test07() {
		Integer[] a = {1,2,3};
		List<AdLedPrice> v = vendorDao.findAdLedPricesByCharQuantity(3);
		System.out.println(v);
		
		
	}
	@Test
	public void test08() {
		Eclient findUniqueBy = userDao.findUniqueBy("1353386254");
		System.out.println(findUniqueBy);
	}
	@Test
	public void test09() {
		String clientRegisterGetCode = userDao.clientRegisterGetCode("18596358741", "asfas", "", 0);
		System.out.println(clientRegisterGetCode);
	}
	
	@Test
	public void test10() throws QiniuException {
		String headImg = "";
		String upResult = QiniuFileSystemUtil.uploadShearPic(headImg);
		if (!StringUtils.isEmpty(upResult)) {
			JSONObject ject = JSON.parseObject(upResult);
			if (!StringUtils.isEmpty((String) ject.get("hash"))
					&& !StringUtils.isEmpty((String) ject.get("key"))) {

				upResult = QiniuConfig.namespace + (String) ject.get("key");
			}
		}
		System.out.println(upResult);
	}
	
//	@Test
	public void test05() throws Exception{
		WXHB w = new WXHB();
		UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
		w.setNonce_str(uuidStr);
		Random ran=new Random();
		int a=ran.nextInt(99999999);
		int b=ran.nextInt(99999999);
		long l=a*10000000L+b;
//		String t = "845431986481718";
		String num=String.valueOf(l);
		w.setMchBillno(num);
		w.setMchId("1507172651");
		w.setWxappid("wx1dbcf02571e7beca");
		w.setSendName("私包"); // 商户名称
		w.setReOpenid("oMWdZ1Kavat9j7bgfHoiZ9DxE6yo"); // 用户openid
		w.setTotalAmount("100"); // 付款金额
		w.setTotalNum("1"); // 红包发放总人数
//		w.setAmt_type("ALL_RAND"); // 红包金额设置方式 
		w.setWishing("测试2"); // 红包祝福语
		w.setClientIp("183.238.231.83"); // Ip地址
		w.setActName("测试"); // 活动名称
		w.setRemark("备注不能空着"); // 备注
		w.setAppKey("sdhkjlbBSKADLSJCBSAUR3987YFR93YR");
		String pay = wxph.sendredpack(w);
		System.out.println(pay);
	}
	
	@Test
	public void test006() {
		String sendredpack = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[参数错误:re_openid字段不正确,请检查是否合法]]></return_msg><result_code><![CDATA[FAIL]]></result_code><err_code><![CDATA[PARAM_ERROR]]></err_code><err_code_des><![CDATA[参数错误:re_openid字段不正确,请检查是否合法]]></err_code_des><mch_billno><![CDATA[210443605612987]]></mch_billno><mch_id><![CDATA[1507172651]]></mch_id><wxappid><![CDATA[wx1dbcf02571e7beca]]></wxappid><re_openid><![CDATA[oMWdZ1FV3t7f9Nw4fgumwzIqYFh]]></re_openid><total_amount>100</total_amount></xml>";
		Map<String, Object> map = new HashMap<String, Object>();
		
			try {
				map = XmlStringUtil.stringToXMLParse(sendredpack);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				logger.error("[Exception:{}]",new Object[] {});
			}
			String tag = baseWxHBManager.redpackUserRecordWechatSendLogOperat(map);
			logger.info("[发放结果TAG:{}]",tag);
			// TODO Auto-generated catch block
			
	}
	
//	@Test
	public void test0006() {
		try {
			List<Map<String, Object>> sendList = baseWxHBManager.redpackSserRecordSendSelect(null);
			
			for (Map<String, Object> map : sendList) {
				/*map.get("openId");
				map.get("appId");
				map.get("eid");
				map.get("mchId");
				map.get("partnerKey");
				map.get("redpackMoney");*/
				
				WXHB w = new WXHB();
				UUID uuid=UUID.randomUUID();
			    String str = uuid.toString(); 
			    String uuidStr=str.replace("-", "");
				w.setNonce_str(uuidStr);
				w.setMchBillno(map.get("sendSign").toString());
				w.setMchId(map.get("mchId").toString());
				w.setWxappid(map.get("appId").toString());
				w.setSendName("水趣驿站欢乐送");
				w.setReOpenid(map.get("openId").toString());
				if(map.get("redpackMoney")!=null) {
					BigDecimal bigDecimal = new BigDecimal(map.get("redpackMoney").toString());
					BigDecimal big100 = new BigDecimal("100");
					w.setTotalAmount(big100.multiply(bigDecimal).intValue()+"");
				}
				w.setTotalNum("1");
				w.setWishing("测试2");
				w.setClientIp("183.238.231.83");
				w.setActName("水趣驿站欢乐送");
				w.setRemark("红包发送");
				w.setAppKey(map.get("redpackMoney").toString());
				String sendredpack = wxph.sendredpack(w);
				Map<String, Object> sendredpackMap = new HashMap<String, Object>();
				try {
					sendredpackMap = XmlStringUtil.stringToXMLParse(sendredpack);
					String tag = baseWxHBManager.redpackUserRecordWechatSendLogOperat(sendredpackMap);
					logger.info("[发放结果记录TAG:{}]",tag);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					logger.error("[Exception:{}] [sendredpack:{}]",new Object[] {e.getMessage(),sendredpack});
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error(e.getMessage());
		}
	}
	@Test
	public void test0007() {
		
		try {
			List<Map<String, Object>> sendList = baseWxHBManager.redpackSserRecordSendSelect(null);
			if(true){
				throw new Exception("保存xxx失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	@Test
	public void test15() {
		Map<String, Object> vendorSelectOrder = vendorManager.vendorSelectOrder(null, null, null, null, null, null, null, null, null, null, null, null,null, null, null, null,null);
//		System.out.println(vendorSelectOrder);
	}
	
	@Test
	public void test17() {
		vendorDao.replenishmentStatistics(1, "2019-01-11 00:00:00", "2019-01-11 00:00:00", "13372821182", null, null);
	}
	
//	@Test
	public void test16() throws Exception {
		WXHB w = new WXHB();
		UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
		w.setNonce_str(uuidStr);
		Random ran=new Random();
		int a=ran.nextInt(99999999);
		int b=ran.nextInt(99999999);
		long l=a*10000000L+b;
//		String t = "845431986481718";
		String num=String.valueOf(l);
		w.setMchBillno("1812261604424730010100008");
		w.setMchId("1507172651");
		w.setWxappid("wx1dbcf02571e7beca");
		w.setBill_type("MCHT"); // MCHT:通过商户订单号获取红包信息。
		w.setAppKey("sdhkjlbBSKADLSJCBSAUR3987YFR93YR");
		String pay = wxph.gethbinfo(w);
		System.out.println(pay);
	}
	
	@Test
	public void test18() {
		/*List<Map<String, Object>> vendorReplenishmentPlanSelect = vendorDao.vendorReplenishmentPlanSelect(1,null, null, 1530, null,null, null);
		System.out.println(vendorReplenishmentPlanSelect);*/
		
		List<Map<String, Object>> vendorReplenishmentPlanSelect2 = vendorManager.vendorReplenishmentPlanSelect(1,null, null, 1530, null,null, null);
		for (Map<String, Object> map : vendorReplenishmentPlanSelect2) {
			
			System.out.println(map);
		}
	}
	
	@Test
	public void test19() {
		VendorContainer selectVendorContainerByMainboardId = vendorDao.selectVendorContainerByMainboardId(19);
		System.out.println(selectVendorContainerByMainboardId);
	}
	
	@Test
	public void test20() {
		Integer mainboardId = 19;
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("sortId", 5);
		json.put("productId", 5);
		json.put("planNum", 10);
		jsonArray.add(json);
		JSONObject json2 = new JSONObject();
		json2.put("sortId", 6);
		json2.put("productId", 6);
		json2.put("planNum", 10);
		jsonArray.add(json2);
		Integer insertVendorReplenishmentPlan = vendorManager.insertVendorReplenishmentPlan(null ,2,13, "测试", false, "xhx", jsonArray);
		System.out.println("--------- = "+insertVendorReplenishmentPlan);
	}
	
	@Test
	public void test21() {
		Integer removeVendorReplenishmentPlan = vendorManager.removeVendorReplenishmentPlan(2);
		System.out.println(removeVendorReplenishmentPlan);
	}
	
	@Test
	public void test22() {
		List<Integer> mainboardId = new ArrayList<>();
		mainboardId.add(19);
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("sortId", 3);
		json.put("productId", 3);
		json.put("planNum", 30);
		jsonArray.add(json);
		JSONObject json2 = new JSONObject();
		json2.put("sortId", 4);
		json2.put("productId", 4);
		json2.put("planNum", 40);
		jsonArray.add(json2);
		Integer removeVendorReplenishmentPlan = vendorManager.updateVendorReplenishmentPlan(mainboardId, 3, "ccc", false, jsonArray );
		System.out.println(removeVendorReplenishmentPlan);
	}
	
	@Test
	public void test23() {
		List<Map<String, Object>> vendorReplenishmentPlanSelectByPlanId = vendorDao.vendorReplenishmentPlanSelectByPlanId(5);
		System.out.println(vendorReplenishmentPlanSelectByPlanId);
	}
	
	@Test
	public void test24() {
		List<Map<String, Object>> v = vendorDao.vendorReplenishmentPlanMappingSelectNoShopId(19);
		System.out.println(v);
	}
	
	@Test
	public void test25() {
		List<Map<String, Object>> selectVendorSellProductKindNum = vendorDao.selectVendorSellProductKindNum(21);
		System.out.println(selectVendorSellProductKindNum);
	}
	
	@Test
	public void test26() {
		List<Map<String, Object>> selectVendorSellProductKindNum = vendorDao.selectVendorContainerByAccountId(1530);
		System.out.println(selectVendorSellProductKindNum);
	}
	
	@Test
	public void test27() {
		Integer planId = 15;
		List<Integer> mainboardIds = new ArrayList<Integer>();
		boolean add = mainboardIds.add(21);
		Integer updateVendorReplenishmentPlanByPlanId = vendorManager.updateVendorReplenishmentPlanByPlanId(mainboardIds , planId);
		System.out.println(updateVendorReplenishmentPlanByPlanId);
	}
	
	@Test
	public void test28() {
		Map<String, Object> vendorAppMerchantProductStorageDetailSelect = vendorManager.vendorDoorSelectGroupByProductIdAndMainboardId(null, null, null, null, null, null);
		System.out.println(vendorAppMerchantProductStorageDetailSelect);
	}
	
	@Test
	public void test29() {
		List<OrderDetail> selectOrderDetailByOrderId = orderDao.selectOrderDetailByOrderId(6701);
		System.out.println(selectOrderDetailByOrderId);
	}
	
	@Test
	public void test30() {
		Map<String, Object> vendorDoorSelectGroupByProductIdAndMainboardId = vendorDao.vendorDoorSelectGroupByProductIdAndMainboardId(1, 935, null, null, null, null);
		System.out.println(vendorDoorSelectGroupByProductIdAndMainboardId);
	}
	
	@Test
	public void test31() {
		Map<String, Object> selectEnterpriseQRCode = baseShopManager.selectEnterpriseQRCode(1, "13372821182");
		System.out.println(selectEnterpriseQRCode);
	}
	
	@Test
	public void test32() {
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		System.out.println(selectSysConfig);
	}
	
	@Test
	public void test33() {
		List<VendorProductReplenishmentClass> selectVendorProductReplenishmentClass = vendorDao.selectVendorProductReplenishmentClass();
		System.out.println(selectVendorProductReplenishmentClass);
	}
	
	@Test
	public void test34() {
		List<GisProvince> findAllProvince = vendorDao.findAllProvince();
		System.out.println(findAllProvince);
	}
	
	@Test
	public void test35() {
		Order order = new Order();
		order.setEid(1);
		order.setPageIndex(1);
		order.setPageSize(2);
		order.setIsSelectAll(false);
		order.setBeginTime("1970-01-01 00:00:00");
		order.setEndTime("2030-01-01 00:00:00");
		order.setStatus(0);
		order.setShopId(1);
		Map<String, Object> selectOrderByShopId = orderDao.selectOrderByShopId(order);
		System.out.println(selectOrderByShopId);
	}
	
	@Test
	public void test36() {
		List<OrderProductInfo> selectOrderProductInfo = orderDao.selectOrderProductInfo(7161, 1);
		System.out.println(selectOrderProductInfo);
	}
	
	@Test
	public void test37() {
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		System.out.println(selectSysConfig.get("后台取消订单时间"));
		
	}
	

	@Test
	public void test38() {
		List<Map<String, Object>> ticketAccount = baseShopManager.ticketSelectMx(1, 836,null);
		System.out.println(ticketAccount);
	}
	
	@Test
	public void test39() {
		Order order = new Order();
//		order.setEid(1);
//		order.setShopId(1);
//		order.setEndTime(endTime);
//		order.setBeginTime(beginTime);
//		order.setStatus(1);
		order.setPageIndex(1);
		order.setPageSize(10);
//		order.setOrderNo(orderNo);
//		order.seteName(eName);
		Map<String, Object> selectOrderByShopId_v2 = orderDao.selectOrderByShopId_v2(order );
		System.out.println(selectOrderByShopId_v2);
	}
	
	@Test
	public void test40() {
//		Map<String, Object> selectShopByShopId = shopDao.selectShopByShopId(1);
//		System.out.println(selectShopByShopId);
		Map<String, Object> selectShopAndBrand = baseShopManager.selectShopAndBrand(1);
		System.out.println(selectShopAndBrand);
	}
	
	@Test
	public void test41() {
//		Map<String, Object> selectShopByShopId = shopDao.selectShopByShopId(1);
//		System.out.println(selectShopByShopId);
		Integer is = baseShopManager.updateShop(1, null, null, null, "8:00", "9:00",null,null);
		System.out.println(is);
	}
	
	@Test
	public void test42() {
		Map<String, Object> s = orderDao.Proc_Backstage_order_payment_select(7355);
		System.out.println(s);
	}
	
	@Test
	public void test43() {
		EEnterpriseWechatliteapp selectMchidAndPartnerKey = orderDao.selectMchidAndPartnerKey(1, 2);
		System.out.println(selectMchidAndPartnerKey);
	}
	
	@Test
	public void test44() throws Exception {
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		String paramAdminWebSit = (String) selectSysConfig.get("后台域名");
		String apiPath = "https://" + paramAdminWebSit + "/";
		System.out.println(selectSysConfig);
		System.out.println(apiPath);
	}
	
	@Test
	public void test45() throws Exception {
		Map<String, Object> orderRefund = baseOrderManager.selectBalanceEvery(null, null, null, null, null, null, null);
		System.out.println(orderRefund);
	}
	
	@Test
	public void test46() throws Exception {
		Order order=new Order();
//		order.setStatus(2);
		order.setShopId(1);
		order.setBeginTime("1970-01-01 00:00:00");
		order.setEndTime("2020-01-01 00:00:00");
		order.setEid(1);
		// [ip:10.16.100.1] [params:{sign=B6373FCCB26B56B6368FAD355072B275, timestamp=1556265782508, status=2, shopId=1, beginTime=1970-01-01 00:00:00, equipment=ANDROID, endTime=2020-01-01 00:00:00, eid=1, appver=1.0.5}] [start:1556265753843]
//		Map<String, Object> findOrderByShopId_v2 = baseOrderManager.findOrderByShopId_v2(order,null);
		Map<String, Object> findOrderByShopId_v2 = baseOrderManager.findOrderByShopId_v3(1530,order,null);
		List<Map<String, Object>> orders = (List<Map<String, Object>>) findOrderByShopId_v2.get("orders");
//		System.out.println(orders.size());
		System.out.println(findOrderByShopId_v2);
	}

	@Test
	public void test47() throws Exception {
		List<OrderDetail> selectOrderDetailByOrderId = orderDao.selectOrderDetailByOrderId(7459);
		System.out.println(selectOrderDetailByOrderId);
	}
	
	@Test
	public void test48() throws Exception {
		Order order = new Order();
		order.setEid(1);
		Map<String, Object> orderl = orderDao.selectOrderByShopId_v2(order );
		System.out.println(orderl);
	}
	
	@Test
	public void test49() throws Exception {
		Order order = new Order();
		order.setEid(1);
		order.setPageIndex(1);
		order.setPageSize(10);
		Map<String, Object> s = orderDao.selectTicketOrderByOrder(order);
		System.out.println(s);
	}

	@Test
	public void test50() throws Exception {
		List<Map<String, Object>> commodityManage = shopDao.commodityManage(1, 1);
		
		LinkedHashMap<Integer, Map<String,Object>> classDic=new LinkedHashMap<Integer, Map<String,Object>>();
		
		for (Map<String, Object> map : commodityManage) {
			int cid=(Integer)map.get("cid");
			Map<String,Object> p=new HashMap<>();
			p.put("pid", map.get("pid"));
			p.put("pname", map.get("pname"));
			p.put("vipPrice", map.get("vipPrice"));
			p.put("pictureUrl", map.get("pictureUrl"));
			p.put("isEnabled", map.get("isEnabled"));
			
			if(!classDic.containsKey(cid))
			{
				Map<String,Object> classMap=new HashMap<>();
				classMap.put("cid", cid);
				classMap.put("className", map.get("className"));
				
				List<Map<String,Object>> products=new ArrayList<Map<String,Object>>();
				products.add(p);
				classMap.put("products",products);
				classDic.put(cid, classMap);
			}
			else
			{
				((ArrayList<Map<String,Object>>)classDic.get(cid).get("products")).add( p);
			}
		}
		
	System.out.println(classDic.values());
	}
	
	@Test
	public void test51() throws Exception {
		List<Map<String, Object>> commodityManage = baseShopManager.commodityManage(1, 1);
		System.out.println(commodityManage);
	}
	
	@Test
	public void test52() throws Exception {
		JSONArray a = new JSONArray();
		a.add(1);
		a.add(2);
		Integer insertDeliverymanOrder = baseOrderManager.insertDeliverymanOrder(0, 0, 0, 0, a, 2, "sdaf");
	}
	
	@Test
	public void test53() throws Exception {
		List<VendorShopAdministratorDTO> d = userDao.selectVendorShopAdmin(1, 1, null, null, null);
		System.out.println(d);
	}
	
	@Test
	public void test54() throws Exception {
		List<StaffDTO> proc_Backstage_staff_select = baseUserManager.Proc_Backstage_staff_select(1, "松岗9店", null,null, null, null);
		System.out.println(proc_Backstage_staff_select);
	}
	
	@Test
	public void test55() throws Exception {
		EAppMerchantAccountRole eAppMerchantAccountRole = userDao.selectEAppMerchantAccountRoleByAccountId(1542);
		String userAuthorization = eAppMerchantAccountRole==null?null:eAppMerchantAccountRole.getUserAuthorization();
		System.out.println(userAuthorization);
		System.out.println((!StringUtils.isEmpty(userAuthorization))&&userAuthorization.charAt(0)=='1');
		System.out.println(!StringUtils.isEmpty(userAuthorization)&&userAuthorization.charAt(0)=='1');
	}
	
	@Test
	public void test56() throws Exception {
		
		Map<String, Object> selectGroupBuyOrder = groupBuyManager.selectGroupBuyOrder(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 1,null, 1, 20, true);
		System.out.println(selectGroupBuyOrder);
	}
	
	@Test
	public void test57() throws Exception {
		Integer selectWxIsRegister = userDao.selectWxIsRegister(1, "15920034655");
		System.out.println(selectWxIsRegister);
	}
	
	@Test
	public void test58() throws Exception {
		List<Map<String, Object>> e_groupbuy_price_schemeByXaId = groupBuyDao.e_groupbuy_price_schemeByXaId(1);
		System.out.println(e_groupbuy_price_schemeByXaId);
	}
	@Test
	public void test59() throws Exception {
		Order order = new Order();
		order.setEid(1);
		order.setShopId(2);
		order.setEndTime("2030-01-01 00:00:00");
		order.setBeginTime("2019-09-24 00:00:00");
		order.setStatus(3);
		order.setPageIndex(1);
		order.setPageSize(10);
		Map<String, Object> orderByShopId_v3 = baseOrderManager.findOrderByShopId_v3(1542,order,false);
		System.out.println(orderByShopId_v3);
	}

	@Test
	public void test60() throws Exception {
		List<EAppMerchantOrderSend> eAppMerchantOrderSends = orderDao.selectEAppMerchantOrderSendByOrderId(1052022);
		System.out.println(eAppMerchantOrderSends);

	}
	@Test
	public void test61() throws Exception {
		String clientId = "LeoWKRvWOeURc6CGB2tmVhCWR+iKczMvnAjH1BnFaE6HKs0SfQ0Oc1ywkis8ByqTQ0eY9k6MFuh6Y2pzwBTzGm3S3QRWBaehdGhnHl+Z0aI=";
		Integer eid = 1;
		String cellphone = "18718000629";
		String authorizer_appid = "wx57c89676f397cfd1";

		String shopRegeditGetCode = baseUserManager.getShopRegeditGetCode(clientId, eid, cellphone, authorizer_appid);
		System.out.println(shopRegeditGetCode);

	}

	@Test
	public void test62() throws Exception {
		Integer xid =1000;
		String title = "测试发送";
		String message = "看看就好";
		Integer type = 10000;
		String sendTime = "2019-09-30 11:36:00";
		Integer platform = 2;
		String registerId = "18171adc035986d197e";
		String jpushTag = "";
		String voiceContent = "来，左边跟我一起画个龙，右边画一道彩虹，走起，左边跟我一起画彩虹，右边再画个龙";
		JPushShangHuModel model = new JPushShangHuModel(xid, title, message, type, sendTime, platform, registerId, jpushTag, voiceContent);
		jpushShangHuService.pushMsg(model);

	}


	@Test
	public void test63() throws Exception {
		Map<String, Object> stringObjectMap = baseActivityManager.selectEappMerchantShareCodeActivityByEidAndActivityType(1,1530);
		System.out.println(stringObjectMap);
	}
	@Test
	public void test64() throws Exception {
		Map<String, Object> stringObjectMap = baseActivityManager.Proc_Backstage_appMerchant_share_code_activity_present_record_select(null, null, null, null, null, null, null, null, null, null, null,null, 2, 10, null);
		System.out.println(stringObjectMap);
	}
	@Test
	public void test65() throws Exception {
		List<Map<String, Object>> maps = groupBuyDao.selectClientNotPayGroupBuyOrder("7JuC+/HBK99m3pHnTODrX9/AMEKS1op2VUMoV9/oQOg=", 1, 16, 744);
		System.out.println(maps);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test66() throws Exception {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call sp_sproc_columns_90(?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("procedure_name", "Proc_Backstage_appMerchant_share_code_activity_present_record_select");
						cs.execute();
						ResultSet rs = cs.executeQuery();
						Map<String, Object> map = new HashMap<String, Object>();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							for (int i =0;i<result.size();i++){
								System.out.println(result.get(i));
							}
							return map;
						} catch (Exception e) {
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					}
				});
	}

	@Test
	public void test67() throws Exception {
		EclientDTO eclientDTO = baseUserManager.searchingAccount("15920034657");
		System.out.println(eclientDTO);
	}

	@Test
	public void test68() throws Exception {
		List<EclientDTO> eclientDTOS = entityCardDao.selectEntityCardOrderBuyAccountByCardCode("00000041");
		System.out.println(eclientDTOS);
	}

	@Test
	public void test69() throws Exception {
		List<Map<String, Object>> maps = vendorDao.selectVendorManagementByLEDOrderId(1);
		System.out.println(maps);
	}

	@Test
	public void test70() throws Exception {
		ledManager.ledAOrderuditJPush(1);
	}
	@Test
	public void test71() throws Exception {
		Map<String, Object> stringObjectMap = userDao.selectAppMerchantJpush(1819);
		System.out.println(stringObjectMap);
	}
	@Test
	public void test72() throws Exception {
		APPWxPayBean w = new APPWxPayBean();
		UUID uuid=UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr=str.replace("-", "");
		w.setNonce_str(uuidStr);
		Random ran=new Random();
		int a=ran.nextInt(99999999);
		int b=ran.nextInt(99999999);
		long l=a*10000000L+b;
//		String t = "845431986481718";
		String num=String.valueOf(l);

		w.setMch_id("1316303101");
		w.setAppid("wx57c89676f397cfd1");
		w.setSub_appid("wx9beb468eb050fa85");
		w.setSub_mch_id("1563942391");
		w.setBody("Ipad mini  16G  白色"); // 商户名称
		w.setOut_trade_no(num); // 用户openid
		w.setTotal_fee("100"); // 付款金额
		w.setSpbill_create_ip("123.12.12.123"); // 红包发放总人数
//		w.setAmt_type("ALL_RAND"); // 红包金额设置方式
		w.setNotify_url("https://weixin.shuiqoo.cn/weixin/notifyUrl"); // 红包祝福语
		w.setTrade_type("APP"); // Ip地址
		w.setAppKey("SQces568jkldjf8weknNDfy5uhvFNjje");
		String pay = new APPWxPayUtils().pay(w);
		System.out.println(pay);
	}

	@Test
	public void test73(){
		System.out.println(env.getProperty("spring.datasource.url"));
	}
	@Test
	public void test74(){
		Map<String, Object> stringObjectMap = orderDao.Proc_Backstage_order_detail_eticketTuiKe_select(null, null, null, null, null, null, null, null, null,null, null, null);
		System.out.println(stringObjectMap);
	}


}
