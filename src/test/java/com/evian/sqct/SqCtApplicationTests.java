package com.evian.sqct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.api.action.UserAction;
import com.evian.sqct.bean.enterprise.EEntererpriseConfig;
import com.evian.sqct.bean.enterprise.EWeixinSendMessageTemplateEnterprise;
import com.evian.sqct.bean.enterprise.input.ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO;
import com.evian.sqct.bean.jwt.TokenDTO;
import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.order.request.ProcBackstageOrderPaymentSelectReqDTO;
import com.evian.sqct.bean.pay.PayOnDeliveryByWeCathReqDTO;
import com.evian.sqct.bean.product.input.ProcBackstageProductSpecsRelevantSelectReqDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageShopSelectDTO;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.bean.sys.SpSprocColumns90;
import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.bean.thirdParty.input.ProcBackstageClientOperatMakeReqDTO;
import com.evian.sqct.bean.user.*;
import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.bean.util.WXHB;
import com.evian.sqct.bean.vendor.*;
import com.evian.sqct.bean.vendor.input.ProcXhxVendorStatusImageRecordSelect;
import com.evian.sqct.bean.vendor.input.VendorDoorStatisticsDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyProvinceRepDTO;
import com.evian.sqct.dao.*;
import com.evian.sqct.dao.impl.GoodsShopCar;
import com.evian.sqct.dao.mybatis.primaryDataSource.dao.IThirdPartyMapperDao;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.*;
import com.evian.sqct.util.*;
import com.evian.sqct.wxPay.APPWxPayBean;
import com.evian.sqct.wxPay.EnterprisePayByLooseChangeBean;
import com.qiniu.common.QiniuException;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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

import java.io.*;
import java.math.BigDecimal;
import java.net.URI;
import java.security.PrivateKey;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqCtApplicationTests {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IVendorDao vendorDao;
	
	@Autowired
	private IShopDao shopDao;

	@Autowired
	private IProductDao productDao;
	
	@Autowired
	private IGroupBuyDao groupBuyDao;

	@Autowired
	private IActivityDao activityDao;

	@Autowired
	private IEntityCardDao entityCardDao;

	@Autowired
	private IEnterpriseDao enterpriseDao;
	
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
	private WxPayService wxPayService;

	@Autowired
	private ICacheDao cacheDao;

	@Autowired
	private ISystemDao systemDao;

	@Autowired
	private BasePayManager basePayManager;

	@Autowired
	private IBaseDao baseDao;

	@Autowired
	private BaseAppletLiveStreamingManager appletLiveStreamingManager;

	@Autowired
	private BaseMessageManager messageManager;

	@Autowired
	private UserAction userAction;



	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	@Autowired
	private IThirdPartyMapperDao thirdPartyMapperDao;

	@Autowired
	private IThirdPartyDao thirdPartyDao;
	
	@Test
	public void contextLoads() {
		System.out.println(userDao.getNowDbName());
	}
	
	@Test
	public void test001() {
		System.out.println(vendorDao.getNowDbName());
	}

	@Test
	public void getAccessToken(){
//		passWord=F59BD65F7EDAFB087A81D4DCA06C4910&appver=1.4.1&access_token=&sign=CD8F6F5BE2805EC160F3509807DB2A43&equipment=ANDROID&account=18598251875&timestamp=1595812488047
		String account = "18598251875";
		List<Map<String, Object>> maps = userDao.tempLogin(account);
		if(maps.size()>0){
			BackLoginReqDTO dto = new BackLoginReqDTO();
			dto.setAccount(account);
			dto.setPassWord((String)maps.get(0).get("userPwd"));
			dto.setPlatformId((Integer)maps.get(0).get("platformId"));
			dto.setRegeditId((String)maps.get(0).get("regeditId"));
			dto.setSourceId((Integer)maps.get(0).get("sourceId"));
			ResultVO<Map<String,Object>> resultVO = userAction.backLogin(null, null, dto);
			Map<String, Object> data = resultVO.getData();
			TokenDTO token = (TokenDTO) data.get("token");
			String access_token = token.getAccess_token();
			System.out.println("Authorization:Bearer "+access_token);
		}
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
	
	@Test
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
		w.setReOpenid("oMWdZ1FV3t7f9Nw4fgumwzIqYFhY"); // 用户openid
		w.setTotalAmount("100"); // 付款金额
		w.setTotalNum("1"); // 红包发放总人数
//		w.setAmt_type("ALL_RAND"); // 红包金额设置方式 
		w.setWishing("测试2"); // 红包祝福语
		w.setClientIp("183.238.231.83"); // Ip地址
		w.setActName("测试"); // 活动名称
		w.setRemark("备注不能空着"); // 备注
		w.setAppKey("sdhkjlbBSKADLSJCBSAUR3987YFR93YR");
		String pay = wxPayService.sendredpack(w);
		System.out.println(pay);
	}
	@Test
	public void test005() throws Exception{
//		baseOrderManager.qrcodeVisitReply("1316186301");
		EnterprisePayByLooseChangeBean w = new EnterprisePayByLooseChangeBean();
		UUID uuid=UUID.randomUUID();
	    String str = uuid.toString();
	    String uuidStr=str.replace("-", "");

		Random ran=new Random();
		int a=ran.nextInt(99999999);
		int b=ran.nextInt(99999999);
		long l=a*10000000L+b;
//		String t = "845431986481718";
		String num=String.valueOf(l);
		w.setMch_appid("wx1dbcf02571e7beca");
		w.setMchid("1507172651");
		w.setNonce_str(uuidStr);
		w.setPartner_trade_no(num);
		w.setOpenid("oMWdZ1FV3t7f9Nw4fgumwzIqYFhY");
		w.setCheck_name("NO_CHECK");
		w.setAmount("100");
		w.setDesc("测试第一次");
		w.setSpbill_create_ip("183.238.231.83");
		w.setAppKey("sdhkjlbBSKADLSJCBSAUR3987YFR93YR");
		String pay = wxPayService.enterprisePayByLooseChange(w);
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
				String sendredpack = wxPayService.sendredpack(w);
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
		String pids = "12182,12180,12181,2000,2000,13235,1504,1504,11128,7567,1992,10437,10431,10429,45,4590,4591,5936,5937,7839,2003,2003,10313,1993,8750,8751,9538,896,896,1996,1996,1998,1998,3887,1999,1999,4582,2041,5264,1077,1077,1424,1424,3169,8569,3920,8752,10311,10311,2031,12179,8618,10191,1293,1079,42,1525,1525,987,22";
		System.out.println(pids.length());
		Map<Integer, Map<String, Object>> batchProductStock = productDao.getBatchProductStock(1, pids);
		System.out.println(batchProductStock);
	}

	
	@Test
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
		String pay = wxPayService.gethbinfo(w);
		System.out.println(pay);
	}
	
	@Test
	public void test18() {
		/*List<Map<String, Object>> vendorReplenishmentPlanSelect = vendorDao.vendorReplenishmentPlanSelect(1,null, null, 1530, null,null, null);
		System.out.println(vendorReplenishmentPlanSelect);*/
		
		List<Map<String, Object>> vendorReplenishmentPlanSelect2 = vendorManager.vendorReplenishmentPlanSelect(1,null, null, 1542, null,null, null);
		Map<String,Object> tt = new HashMap<>();
		tt.put("data",vendorReplenishmentPlanSelect2);
		JSONObject jsonObject = new JSONObject(tt);
		System.out.println(jsonObject);
	}

	@Test
	public void test18_v2() {

		List<Map<String, Object>> vendorReplenishmentPlanSelect2 = vendorDao.vendorReplenishmentPlanSelect(1,0, null, null, null,null, null);
		Map<String,Object> tt = new HashMap<>();
		tt.put("data",vendorReplenishmentPlanSelect2);
		JSONObject jsonObject = new JSONObject(tt);
		System.out.println(jsonObject);
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
		Integer warningNum = null;
		Integer insertVendorReplenishmentPlan = vendorManager.insertVendorReplenishmentPlan(null ,2,13, "测试", false, "xhx", warningNum, jsonArray);
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
		List<VendorProductReplenishmentClass> selectVendorProductReplenishmentClass = vendorDao.selectVendorProductReplenishmentClass(1);
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
		Map<String, Object> s = orderDao.Proc_Backstage_order_payment_select(new ProcBackstageOrderPaymentSelectReqDTO(7355));
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
		List<Map<String, Object>> commodityManage = baseShopManager.commodityManage(1, 1);
		
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
		List<StaffDTO> proc_Backstage_staff_select = baseUserManager.Proc_Backstage_staff_select(1, null, 1,null, null, null);
		System.out.println(proc_Backstage_staff_select);
	}
	@Test
	public void test054() throws Exception {
		EAppMerchantAccountEnterpriseRole eAppMerchantAccountEnterpriseRole = userDao.selectEAppMerchantAccountEnterpriseRoleByAccountId(3885);
		System.out.println(eAppMerchantAccountEnterpriseRole);
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
		//[beginTime:null] [endTime:null] [eid:1] [orderNo:null] [nickName:null] [isCommander:true] [isRefund:null] [account:null] [groupBuyState:2] [paymentNo:null] [orderGroup:null] [eName:null] [pname:null] [pcode:null] [sdkType:null] [shopId:null] [isFilterEndOrder:true] [sucMode:null] [minGroupBuyNum:null] [maxGroupBuyNum:null] [sortType:0] [PageIndex:20] [PageSize:false] [IsSelectAll:{}]
		Map<String, Object> selectGroupBuyOrder = groupBuyManager.selectGroupBuyOrder(null, null, 1, null, null, true, null, null, 2, null, null, null, null, null, null, 1,null, null,null,null,null,null,1, 20, false);
		JSONObject jsonObject = new JSONObject(selectGroupBuyOrder);
		System.out.println(jsonObject);
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
		// [accountId:3484] [order:Order [orderId=null, shopId=1, beginTime=2020-05-11 00:00:00, endTime=2030-01-01 00:00:00, eid=1, orderNo=null, status=-99, sdkType=null, ifMutual=null, ifreply=null, in_come=null, account=null, shopName=null, confirm_take=null, eName=null, PageIndex=1, PageSize=10, IsSelectAll=null, payMode=null, linePaySuceed=null, has_return=null, sourceGroup=null, sendAddress=null, deliverMan=null, isFreight=null, phone=null]] [isTicket:null]
		Order order = new Order();
		order.setEid(1);
		order.setShopId(1);
		order.setEndTime("2030-01-01 00:00:00");
		order.setBeginTime("2020-05-11 00:00:00");
		order.setStatus(null);
		order.setPageIndex(1);
		order.setPageSize(10);
		Map<String, Object> orderByShopId_v3 = baseOrderManager.findOrderByShopId_v3(3484,order,null);
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
		Integer platform = 1;
		String registerId = "0862741031300185300007667700CN01";
		String jpushTag = "";
		String voiceContent = "来，左边跟我一起画个龙，右边画一道彩虹，走起，左边跟我一起画彩虹，右边再画个龙";
		Integer platformId = 2;
		JPushShangHuModel model = new JPushShangHuModel(xid, title, message, type, sendTime, platform, registerId, jpushTag, voiceContent,platformId);
		jpushShangHuService.pushMsg(model);
	}
	@Test
	public void test62_3() throws Exception {
//		Integer accountId,String regeditId,Integer sourceId,Integer platformId
//				[accountId:1821] [regeditId:0862741031300185300007667700CN01] [sourceId:1] [platformId:2]
		Integer integer = baseUserManager.updateAppMerchantJpush(1821, "0862741031300185300007667700CN01", 1, 2);
		System.out.println(integer);
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
		String pay = wxPayService.pay(w);
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
	@Test
	public void test75(){
		String s = vendorManager.Proc_Backstage_vendor_AppCustomer_DoorReplenishment_Stand(1, 23, 934, 1, 0, 0, "18522003016", 1,"","");
		System.out.println(s);
	}
	@Test
	public void test76(){
		String sql ="select * from e_weixin_reply";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
		Map<String, Object> stringObjectMap = maps.get(maps.size() - 1);
		JSONObject a =new JSONObject(stringObjectMap);
		System.out.println(a);
	}

	@Test
	public void test77(){
		Map<String, Object> df = vendorManager.vendorMainboardOrProductIdBuySelect(1, null, null, null, 1, null, null, null, null, null, "松岗9店", null, false, null, null, null, null, null, false);
		JSONObject jsonObject = new JSONObject(df);
		System.out.println(jsonObject);
	}
	@Test
	public void test78(){
		List<Map<String, Object>> maps = vendorDao.Proc_Backstage_vendor_replenishment_type_select(1);
		System.out.println(maps);
	}
	@Test
	public void test79(){
		List<Map<String, Object>> maps = vendorDao.Proc_Backstage_vendor_replenishment_type_select_statistics(1, null, null, null, null, null, null, null, null, null, null, null);
		System.out.println(maps);
	}

	@Test
	public void test80(){
		baseUserManager.updateNotSubscribeUserHeadimgurl();
	}

	@Test
	public void test81(){
		ledManager.downAdvertingToVendor(517,"10280109", "现在是十七点二十二分零三秒", "R", 10, 20, 1);
	}

	@Test
	public void test82(){
		Map<String, Object> stringObjectMap = vendorManager.selectVendorCouponToWechatActivityByUser(1, "oGLfUwE5ZHCB_d5tWdXaxZ-MOq_k", null);
		System.out.println(stringObjectMap);
	}

	@Test
	public void test83(){
		Map<String, Object> stringObjectMap = shopDao.PROC_ZH_DisCosts_SELECT_ShopAccount(1, 1);
		System.out.println(stringObjectMap);
	}
	@Test
	public void test84() throws Exception{
		// [eid:1] [clientId:901] [shopId:1] [account:18718000629] [cashOut:1.0] [remark:] [appId:wx57c89676f397cfd1] [openId:oGLfUwH4kM0lej4QTJns6AeKopzA] [ip:192.168.99.241]
		baseShopManager.withdrawalOfDeliveryFee(1, 901, 1, "18718000629", 1.0, "", "wx57c89676f397cfd1", "oGLfUwH4kM0lej4QTJns6AeKopzA", "192.168.99.241",null);

	}
	@Test
	public void test85() throws Exception{
		// [eid:1] [clientId:901] [shopId:1] [account:18718000629] [cashOut:1.0] [remark:] [appId:wx57c89676f397cfd1] [openId:oGLfUwH4kM0lej4QTJns6AeKopzA] [ip:192.168.99.241]
		List<Map<String, Object>> maps = baseShopManager.withdrawDepositOrderSelect(1, 2, "2020-05-13", "2020-05-13 23:59:59", "", 1, 10);
		Map<String, Object> m = new HashMap<>();
		m.put("sss",maps);
		JSONObject jsonObject = new JSONObject(m);
		System.out.println(jsonObject);

	}

	@Test
	public void test86() throws Exception{
		// [clientId:EAPyCVTB78jcDd+sbetDx1+FOxVGLkWr46uutPj0bV4wOr1ebIylnbzocFTXrL7xH9h4I55MxzIZC60IGAdZYxkRH34BkzADtdwZnuuTAeY=] [eid:1] [endDate:null] [authorizer_appid:wxe96781856240e981]
		String myLiteAppEarningInfo = baseUserManager.getShopClientRegeditReport("EAPyCVTB78jcDd+sbetDx1+FOxVGLkWr46uutPj0bV4wOr1ebIylnbzocFTXrL7xH9h4I55MxzIZC60IGAdZYxkRH34BkzADtdwZnuuTAeY=", 1, null,"wxe96781856240e981");
		System.out.println(myLiteAppEarningInfo);

	}
	@Test
	public void test87(){
		// [sendAccountId=0, managerAccountId=1814, orderSend=[{"eid":1,"ShopID":369,"orderID":23986}], sendStatus=-4, remark=W, creater=岛主, DataSource=APP]]
		JSONArray orderSend = new JSONArray();
		JSONObject r = new JSONObject();
		r.put("orderID",23986);
		r.put("eid",1);
		r.put("ShopID",369);
		orderSend.add(r);

		ProcAppOrderSendSaveUPDTO procAppOrderSendSaveUPDTO =
				new ProcAppOrderSendSaveUPDTO(0,1814,orderSend.toString(),-4,"W","岛主","APP");

		String s = baseOrderManager.appOrderSendSaveUP(procAppOrderSendSaveUPDTO);
		System.out.println(s);
	}

	@Test
	public void test88(){
		JSONArray orderSend = new JSONArray();
		JSONObject r = new JSONObject();
		r.put("orderID",23430);
		r.put("eid",1);
		r.put("ShopID",1);
		orderSend.add(r);



		ProcAppOrderSendSaveUPDTO procAppOrderSendSaveUPDTO = new ProcAppOrderSendSaveUPDTO(0,0,orderSend.toString(),-4,"ddd","xhx","测试");

		String s = baseOrderManager.appOrderSendSaveUPXML(procAppOrderSendSaveUPDTO);
		System.out.println(s);

	}


	@Test
	public void test91(){
		Map<String, Object> stringObjectMap = entityCardDao.Proc_DisPark_Get_OrderNo(13, 1);
		System.out.println(stringObjectMap);
	}

	@Test
	public void test92(){
		Map<String, Object> stringObjectMap = enterpriseDao.Proc_Backstage_enterprise_config_select( 1,"59");
		System.out.println(stringObjectMap);
	}
	@Test
	public void test93(){
		// [aop记录 ent:cancellationOfOrder] [orderId:23970] [userId:856] [eid:1] [cancelReason:客户不在家] [e_order_detail_ID:[28467]]
		// [orderId:23890] [userId:901] [eid:1] [cancelReason:客户不在家] [e_order_detail_ID:null]
		JSONArray r = new JSONArray();
		r.add(28469);
		baseOrderManager.cancellationOfOrder(23971,856,1,"家中无人",r,null);
	}
	@Test
	public void test93_v2(){
		List<OrderProductInfo> orderProductInfos = orderDao.selectOrderProductInfo(23970, 1);
		System.out.println(orderProductInfos);
	}
	@Test
	public void test93_2(){

		// [orderId:23890] [userId:901] [eid:1] [cancelReason:客户不在家] [e_order_detail_ID:null]
		OrderModel orderModel = orderDao.selectOrderByOrderId(23890);
		System.out.println(orderModel);
	}
	@Test
	public void test94(){
		// [orderId:2967775] [userId:5984] [eid:1] [cancelReason:家中无人] [e_order_detail_ID:null]
		boolean b = userDao.setSMSFindPwd_v2("cellPhone", "msgCode", "npwd");
		System.out.println(b);
	}
	@Test
	public void test95(){
		Map<String, Object> user = baseUserManager.userLogin_v2("18718000629", "EA983C0D57E04E8D214F63E1228CBF15");
		JSONObject jsonObject = new JSONObject(user);
		System.out.println(jsonObject);
	}
	@Test
	public void test96(){
		List<AppFeedbackDTO> appFeedbackDTOS = baseUserManager.selectAppFeedback(1, 1);
		Map<String,Object> user = new HashMap<>();
		user.put("app",appFeedbackDTOS);
		JSONObject jsonObject = new JSONObject(user);
		System.out.println(jsonObject);
	}

	@Test
	public void test97(){
		EEntererpriseConfig e = enterpriseDao.selectEntererpriseConfig(1, 25);
		System.out.println(e);
	}

	@Test
	public void test98(){
		List<StaffDTO> staffDTOS = userDao.Proc_Backstage_staff_select(null, null, 1, null, null,null, null,null);
		System.out.println(staffDTOS);
	}

	@Test
	public void test99(){
		String key = "SQSHTest";
		long size = 0;
		TokenDTO t1 = new TokenDTO("vvvv", "ccc");
		TokenDTO t2 = new TokenDTO("xxxx", "nnnnnn");
		List<TokenDTO> tt = new ArrayList<>();
		cacheDao.lpush(key,tt);
		/*long size = cacheDao.lpush(key, t1);
		System.out.println(size);*/
		size = cacheDao.llen(key);
		System.out.println(size);
		/*size = cacheDao.lpush(key, t2);
		System.out.println(size);*/
		Object o = cacheDao.rPop(key);
		System.out.println(o);
		size = cacheDao.llen(key);
		System.out.println(size);

		/*String key2 = "gggggggg";
		cacheDao.set(key2,"3333333333333");
		Object o1 = cacheDao.get(key2);
		System.out.println(o1);*/
	}

	@Test
	public void test100(){
		String key = "SQSHTest";
		TokenDTO t1 = new TokenDTO("vvvv", "ccc");
		TokenDTO t2 = new TokenDTO("xxxx", "nnnnnn");
		List<TokenDTO> tt = new ArrayList<>();
		tt.add(t1);
		tt.add(t2);
		cacheDao.setExpireSeconds(key,tt,1000);
		List<TokenDTO> o = (List<TokenDTO>) cacheDao.get(key);
		System.out.println(o);
	}

	@Test
	public void test101(){
		List<OrderHistorySixAccount> orderHistorySixAccounts = orderDao.selectOrderHistorySixAccountByOrderId(23751);
		System.out.println(orderHistorySixAccounts);

	}
	@Test
	public void test102() throws Exception {
		TuiyaPayDTO dto = new TuiyaPayDTO();
		dto.setEid(1);
		dto.setIp("10.5.415.38");
		dto.setState(1);
		dto.setTotal(6.6);
		dto.setTransferOperator("xhx");
		dto.setTransferRemark("测试");
		dto.setTuiyaOrderId(23751);
		basePayManager.tuiyaPay(dto);

	}

	@Test
	public void sp_sproc_columns_90(){
		String storedProcedureName = "PROC_APP_orderSend_saveUP";
//		String storedProcedureName = "Proc_Backstage_client_pledge_tuiya_select";
		List<SpSprocColumns90> spSprocColumns90s = systemDao.sp_sproc_columns_90(storedProcedureName);
		Map<String,Object> map = new HashMap<>();
		map.put("tt",spSprocColumns90s);
		JSONObject d = new JSONObject(map);
		System.out.println(d);
	}
	@Test
	public void test103(){
		VendorReplenishmentPartSelectDTO dto = new VendorReplenishmentPartSelectDTO();
		dto.setEid(1);
		List<StaffDTO> staffDTOS = baseUserManager.selectVendorReplenishmentPart(dto);

	}

	@Test
	public void test104(){
		Object o = baseDao.agencyDB("Proc_Backstage_client_pledge_tuiya_select", null);
		System.out.println(o);
	}

	@Test
	public void test104_v2(){
		List<SpSprocColumns90> t = systemDao.sp_sproc_columns_90("Proc_Backstage_client_pledge_tuiya_select");
		System.out.println(t);
	}

	@Test
	public void test105(){
		ProcBackstageClientPledgeTuiyaSelectDTO dto = new ProcBackstageClientPledgeTuiyaSelectDTO();
		dto.setEid(1);
		dto.setIsAxceedOneYear(true);
		Map<String, Object> stringObjectMap = orderDao.Proc_Backstage_client_pledge_tuiya_select(dto);
		System.out.println(stringObjectMap);
	}

	@Test
	public void test106(){
		ProcBackstageClientPledgeTuiyaSelectDTO dto = new ProcBackstageClientPledgeTuiyaSelectDTO();
		dto.setEid(1);
		dto.setIsAxceedOneYear(true);
		Map<String,Object> t = baseDao.agencyDB("Proc_Backstage_client_pledge_tuiya_select", dto);
		JSONObject y = new JSONObject(t);
		System.out.println(y);
	}


	@Test
	public void test17() {
		long l = System.currentTimeMillis();
		ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO dto = new ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO();
		dto.setEid(1);
		dto.setBeginTime("2019-01-11 00:00:00");
		dto.setEndTime("2019-01-11 00:00:00");
		dto.setAccount("13372821182");
		Map<String, Object> stringObjectMap = vendorDao.replenishmentStatistics(dto);
		long l1 = System.currentTimeMillis();
		System.out.println(l1-l);
		System.out.println(stringObjectMap);
	}

	@Test
	public void test107(){
		ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO dto = new ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO();
		dto.setEid(1);
		dto.setBeginTime("2019-01-11 00:00:00");
		dto.setEndTime("2019-12-30 00:00:00");
		dto.setAccount("13372821182");
		long l = System.currentTimeMillis();

		Map<String,Object> t = baseDao.agencyDBVendor("Proc_Backstage_vendor_AppCustomer_replenishment_statistics", dto);
		long l1 = System.currentTimeMillis();
		System.out.println(l1-l);
		JSONObject y = new JSONObject(t);
		System.out.println(y);
	}


	@Test
	public void test89(){
		long l = System.currentTimeMillis();
		ProcAppOrderSendSelectDTO procAppOrderSendSaveUPDTOdto =
				new ProcAppOrderSendSelectDTO(1,1,null,"2010-05-01","2020-05-16",null,2,null,null,1,10);
		Map<String, Object> orderByShopId_v4 = baseOrderManager.findOrderByShopId_v4(procAppOrderSendSaveUPDTOdto);
		long l1 = System.currentTimeMillis();
		System.out.println(l1-l);
		System.out.println(orderByShopId_v4);

	}
	@Test
	public void test89_v2(){
		long l0 = System.currentTimeMillis();
		ProcAppOrderSendSelectDTO dto =
				new ProcAppOrderSendSelectDTO(1,1,null,"2010-05-01","2020-05-16",null,2,null,null,1,10);
		dto = new ProcAppOrderSendSelectDTO();
		dto.setEid(1);
		dto.setShopId(1);
		dto.setOrderNo("QY200623000067");
		dto.setIfIncludeChargeback(true);
		Map<String, Object> orderByShopId_v4 = orderDao.PROC_APP_orderSend_SELECT(dto);
		long l00 = System.currentTimeMillis();
		System.out.println(l00-l0);
		System.out.println(orderByShopId_v4);
	}

	@Test
	public void test108(){
		long l = System.currentTimeMillis();
		ProcBackstageOrderCompensateIntegralDTO dto = new ProcBackstageOrderCompensateIntegralDTO();
		dto.setEid(1);
		dto.setOrderId(1);
		dto.setAccountId(1);
		Map<String,Object> t = baseDao.agencyDBVendor("Proc_Backstage_order_compensateIntegral", dto);
		long l1 = System.currentTimeMillis();
		System.out.println(l1-l);
		JSONObject y = new JSONObject(t);
		System.out.println(y);
	}
	@Test
	public void test109(){
		PayOnDeliveryByWeCathReqDTO dto = new PayOnDeliveryByWeCathReqDTO();
		dto.setAppId("wx57c89676f397cfd1");
		dto.setIp("10.108.15.13");
		String nonce_str = wxPayService.create_nonce_str();
		dto.setOrderNo("QY200617000021");
		dto.setOrderId(24008);
		dto.setTotal(0.02);
		String s = basePayManager.payOnDeliveryByWeCath(dto);
		System.out.println(s);
	}

	@Test
	public void test110(){
		ProcXHXStaffScanLocationSelectReqDTO dto = new ProcXHXStaffScanLocationSelectReqDTO();
		Map<String, Object> map = vendorManager.staffScanLocationSelect(dto);
		JSONObject a = new JSONObject(map);
		System.out.println(a);
	}

	@Test
	public void test111(){
		// [dto:ProcAppOrderSendSelectDTO [eid=1, shopId=369, accountId=null, beginTime=null, endTime=null, status=3, payMode=null, orderNo=null, isTicket=null, iRows=0, PageSize=10]]
		// [aop记录 ent:findOrderByShopId_v4] [dto:ProcAppOrderSendSelectDTO [eid=1, shopId=369, accountId=null, beginTime=null, endTime=null, status=0, payMode=null, orderNo=null, isTicket=null, ifIncludeChargeback=null, PageSize=10, iRows=0]]
		// [aop记录 ent:findOrderByShopId_v4] [dto:ProcAppOrderSendSelectDTO [eid=1, shopId=369, accountId=null, beginTime=null, endTime=null, status=1, payMode=null, orderNo=null, isTicket=null, ifIncludeChargeback=null, PageSize=10, iRows=10]]
		ProcAppOrderSendSelectDTO dto = new ProcAppOrderSendSelectDTO();
		dto.setEid(1);
		dto.setShopId(369);
		dto.setStatus(1);
		dto.setPageSize(10);
		dto.setiRows(10);
		Map<String, Object> orderByShopId_v4 = baseOrderManager.findOrderByShopId_v4(dto);
		System.out.println(orderByShopId_v4);
	}
	@Test
	public void test112(){
		// [aop记录 ent:selectSendOrderLogisticsByAccountId] [dto:FindSendOrderLogisticsByAccountIdReqDTO [accountId=1814, PageIndex=0, PageSize=10, IsSelectAll=false, beginTime=null, endTime=null]]
		FindSendOrderLogisticsByAccountIdReqDTO dto = new FindSendOrderLogisticsByAccountIdReqDTO();
		dto.setAccountId(1814);
		dto.setPageIndex(1);
		dto.setPageSize(10);
		dto.setIsSelectAll(false);
		baseOrderManager.selectSendOrderLogisticsByAccountId(dto);
	}

	@Test
	public void test113(){
		ProcAppOrderSendSaveUPDTO d = new ProcAppOrderSendSaveUPDTO();
		d.setSendAccountId(0);
		d.setManagerAccountId(1826);
		d.setOrderSend("[  {    \"eid\" : 1,    \"ShopID\" : 369,    \"orderID\" : 24139  }]");
		d.setSendStatus(-3);
		d.setRemark("地址送不到");
		d.setCreater("赵敏");
		d.setDataSource("APP");
		baseOrderManager.operOrderSendLogs(d);
	}
	@Test
	public void test114(){
		List<OrderHistorySixAccount> orderHistorySixAccounts = orderDao.selectOrderAccountByOrderId(3620428);
		System.out.println(orderHistorySixAccounts);
	}
	@Test
	public void test115(){
		// [{eid=1, isSettingWechatliteapp=true, keepOpenId=oGLfUwF1xL36aLz0zVYVN1FUI81c, matchQuantity=0, yw_mdId=11, keeperMobile=18598251875, sendOnTime=90, freight=0.0000, chooseTimeItem=true, shopName=松岗9店, description=这是个好店11, keeperHeadImgUrl=http://thirdwx.qlogo.cn/mmopen/PiajxSqBRaEIrQAxTg1EMPDQYD3t84WVVnqrtEibbPThVOyHoHVWtpQwa6DpicQBOBkPHrPbak8bYPnbxoicwkGTgQ/132, cityId=199, keeperId=1, freightRemark=无电梯三楼以上每加一层加运费2元, dateCreated=2015-11-23 14:55:17.8, cityName=深圳, isSettingTimeframe=true, cityIfLine=true, yw_mdName=, isVendor=true, tel=18745217896, startTime=8:00, shopId=1, scopeDescription=沙井顶峰科技园周边2公里, minSendPrice=10.0000, keeperNickName=LY, ifLine=true, sendAddMinute=60, address=琦丰达大厦A1901, districtName=宝安区, eName=青云蓝海科技, sendLocation=, pictureUrl=http://sdd.haoshui.com.cn/Upload/Client/photos/images/20190604/201906041559614890415.jpg, shareClientId=164, shareAccount=15914000858, shopProductCount=31, ifGroupBuy=false, shareId=1137, provinceId=19, linkman=经理, districtId=1772, location=113.8677870875775,22.750026147631388, createUser=18598251875, shopType=加盟门, endTime=12:30, shopNo=1658748}]
		// [{eid=1, isSettingWechatliteapp=true, keepOpenId=oGLfUwF1xL36aLz0zVYVN1FUI81c, matchQuantity=0, yw_mdId=11, keeperMobile=18598251875, sendOnTime=90, freight=0.0000, chooseTimeItem=true, shopName=松岗9店, description=这是个好店11, keeperHeadImgUrl=http://thirdwx.qlogo.cn/mmopen/PiajxSqBRaEIrQAxTg1EMPDQYD3t84WVVnqrtEibbPThVOyHoHVWtpQwa6DpicQBOBkPHrPbak8bYPnbxoicwkGTgQ/132, cityId=199, keeperId=1, freightRemark=无电梯三楼以上每加一层加运费2元, dateCreated=2015-11-23 14:55:17.8, cityName=深圳, isSettingTimeframe=true, cityIfLine=true, yw_mdName=, isVendor=true, tel=18745217896, startTime=8:00, shopId=1, scopeDescription=沙井顶峰科技园周边2公里, minSendPrice=10.0000, keeperNickName=LY, ifLine=true, sendAddMinute=60, address=琦丰达大厦A1901, districtName=宝安区, eName=青云蓝海科技, sendLocation=, pictureUrl=http://sdd.haoshui.com.cn/Upload/Client/photos/images/20190604/201906041559614890415.jpg, shareClientId=164, shareAccount=15914000858, shopProductCount=31, ifGroupBuy=false, shareId=1137, provinceId=19, linkman=经理, districtId=1772, location=113.8677870875775,22.750026147631388, createUser=18598251875, shopType=加盟门, endTime=12:30, shopNo=1658748}]
		ProcBackstageShopSelectDTO dto = new ProcBackstageShopSelectDTO();
		dto.setShopId(1);
		dto.setEid(1);
		Map<String, Object> map = shopDao.selectShopByEidAndShopId(dto);

		System.out.println(map);
	}

	@Test
	public void test116(){
		ProcXhxVendorStatusImageRecordSelect dto = new ProcXhxVendorStatusImageRecordSelect();
		Map<String, Object> map = vendorManager.Proc_XHX_vendor_status_image_record_select(dto);
		System.out.println(map);
	}

	@Test
	public void test117(){
		List<EEnterpriseMsgWeixin> eEnterpriseMsgWeixins = userDao.selectEEnterpriseMsgWeixinByEid(1);
		EWeixinSendMessageTemplateEnterprise eWeixinSendMessageTemplateEnterprise = enterpriseDao.selectEWeixinSendMessageTemplateEnterpriseByTid(1, 10);
		System.out.println(eEnterpriseMsgWeixins);
		System.out.println(eWeixinSendMessageTemplateEnterprise);
	}

	@Test
	public void test118(){
		String s = messageManager.pushVendorShipmentMessage("89d8732d7e8d48cd9323e35fda1908c3");
		System.out.println(s);
	}

	@Test
	public void test119(){
		// [params:{eid=1, PageSize=10, IsSelectAll=false, appver=1.4.1, sign=653F7115F4236084EFBED5DF134F6566, equipment=ANDROID, ifAudit=false, PageIndex=1, timestamp=1595836687746}] [start:1595836687838]
		ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO dfs = new ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO();
		dfs.setEid(1);
		dfs.setIfAudit(false);
		Map<String, Object> map = enterpriseDao.Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select(dfs);
		System.out.println(map);
	}

	@Test
	public void test120(){
		List<GoodsShopCar> asdf = new ArrayList<>();
		GoodsShopCar t = new GoodsShopCar();
		t.setId(1);
		t.setEid(1);
		t.setShopId(1);
		t.setClientId(752);
		t.setPid(43);
		t.setNumber(1);
		t.setSettleStyle("现金");
		t.setDateCreated(System.currentTimeMillis());
		t.setDateUpdated(System.currentTimeMillis());
		t.setFpid(0);
		t.setActivityId(0);
		asdf.add(t);
		List<Map<String, Object>> shopCartGoodsOptimizeRedis = systemDao.getShopCartGoodsOptimizeRedis(752, 1, asdf);
		System.out.println(shopCartGoodsOptimizeRedis);
		shopCartGoodsOptimizeRedis = systemDao.getShopCartGoodsOptimizeRedis(752, 1, asdf);
		System.out.println(shopCartGoodsOptimizeRedis);
		shopCartGoodsOptimizeRedis = systemDao.getShopCartGoodsOptimizeRedis(752, 1, asdf);
		System.out.println(shopCartGoodsOptimizeRedis);

	}

	@Test
	public void test121(){
		VendorDoorStatisticsDTO dto = new VendorDoorStatisticsDTO();
		dto.setEid(1);
		dto.setMainboardType(1);
		Map<String, Object> map = vendorManager.vendorAppCustomerMainboardContainerDoorStatistics(dto);
		System.out.println(map);

	}

	@Test
	public void test122(){
		JPushShangHuModel model = new JPushShangHuModel(560873,"","测试用的19门 10240001号:服务端检测到异常离线！",2,"2020-08-26 00:08:38",1,"140fe1da9ec9f8a3dfd","","",1);
		String s = JacksonUtils.obj2json(model);
        System.out.println(s);
		Map<String, Object> map = JpushShangHuService.pushMsg(model);
		System.out.println(map);
	}


	@Test
	public void test123() throws Exception {
		List<SysParamModel> wechatServicePayMchId = systemDao.selectESystemConfig("WechatServicePayMchId");
		List<SysParamModel> WechatServicePaySecret = systemDao.selectESystemConfig("WechatServicePaySecret");
		List<SysParamModel> WechatServicePayCertSn = systemDao.selectESystemConfig("WechatServicePayCertSn");
		List<SysParamModel> WechatServicePayPrivateKey = systemDao.selectESystemConfig("WechatServicePayPrivateKey");
		String mchId = wechatServicePayMchId.get(0).getSysValue(); // 商户号
		String mchSerialNo = WechatServicePayCertSn.get(0).getSysValue(); // 商户证书序列号
		String apiV3Key = WechatServicePaySecret.get(0).getSysValue(); // api密钥
		// 你的商户私钥
		String privateKey = WechatServicePayPrivateKey.get(0).getSysValue();

		PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
				new ByteArrayInputStream(privateKey.getBytes("utf-8")));

		//使用自动更新的签名验证器，不需要传入证书
		AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
				new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
				apiV3Key.getBytes("utf-8"));

		CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
				.withMerchant(mchId, mchSerialNo, merchantPrivateKey)
				.withValidator(new WechatPay2Validator(verifier))
				.build();
		String filePath = "C:/Users/XHX/Desktop/日志/190321153432302617-d.jpg";

		URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");

		File file = new File(filePath);
		try (FileInputStream s1 = new FileInputStream(file)) {
			String sha256 = DigestUtils.sha256Hex(s1);
			try (InputStream s2 = new FileInputStream(file)) {
				WechatPayUploadHttpPost request = new WechatPayUploadHttpPost.Builder(uri)
						.withImage(file.getName(), sha256, s2)
						.build();

				CloseableHttpResponse response1 = httpClient.execute(request);
				assertEquals(200, response1.getStatusLine().getStatusCode());
				try {
					HttpEntity entity1 = response1.getEntity();
					// do something useful with the response body
					// and ensure it is fully consumed
					String s = EntityUtils.toString(entity1);
					System.out.println(s);
				} finally {
					response1.close();
				}
			}
		}
	}


	@Test
	public void test124(){
		List<EWechatServicepaySubaccountApplyProvinceRepDTO> eWechatServicepaySubaccountApplyProvinceRepDTOS = vendorManager.selectEWechatServicepaySubaccountApplyProvince();
		System.out.println(eWechatServicepaySubaccountApplyProvinceRepDTOS);
	}
	@Test
	public void test125(){
		Integer integer = thirdPartyMapperDao.deleteRecruitGood(9);
		System.out.println(integer);
	}
	@Test
	public void test126(){
		ProcBackstageClientOperatMakeReqDTO dto = new ProcBackstageClientOperatMakeReqDTO();
		dto.setAccount("15920034657");
		Map<String, Object> map = thirdPartyDao.Proc_Backstage_client_operat_Make(dto);
		System.out.println(map);
	}
	@Test
	public void test127() throws IOException {
				baseOrderManager.qrcodeVisitReply("1316303101");
	}
	@Test
	public void test128() throws IOException {
		ProcBackstageProductSpecsRelevantSelectReqDTO dto = new ProcBackstageProductSpecsRelevantSelectReqDTO();
		dto.setPid(988);
		Map<String, Object> map = productDao.Proc_Backstage_product_specs_relevant_select(dto);
		System.out.println(map);
	}
}
