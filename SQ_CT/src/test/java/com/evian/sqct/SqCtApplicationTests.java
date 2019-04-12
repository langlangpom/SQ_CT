package com.evian.sqct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.order.Order;
import com.evian.sqct.bean.order.OrderDetail;
import com.evian.sqct.bean.order.OrderProductInfo;
import com.evian.sqct.bean.user.Eclient;
import com.evian.sqct.bean.util.WXHB;
import com.evian.sqct.bean.vendor.AdLedPrice;
import com.evian.sqct.bean.vendor.GisCommunity;
import com.evian.sqct.bean.vendor.GisProvince;
import com.evian.sqct.bean.vendor.VendorContainer;
import com.evian.sqct.bean.vendor.VendorMainboardContainer;
import com.evian.sqct.bean.vendor.VendorProductReplenishmentClass;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.dao.IShopDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.service.BaseShopManager;
import com.evian.sqct.service.BaseVendorManager;
import com.evian.sqct.service.BaseWxHBManager;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import com.evian.sqct.util.XmlStringUtil;
import com.evian.sqct.wxHB.WxPayHB;
import com.qiniu.common.QiniuException;
import com.squareup.okhttp.OkHttpClient;

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
	private BaseVendorManager vendorManager;
	
	@Autowired
	private BaseWxHBManager baseWxHBManager;
	
	@Autowired
	private BaseShopManager baseShopManager;
	
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
		Integer is = baseShopManager.updateShop(1, null, null, null, "8:00", "9:00");
		System.out.println(is);
	}
}
