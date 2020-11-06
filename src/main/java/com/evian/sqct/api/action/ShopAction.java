package com.evian.sqct.api.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.shop.FindShopModel;
import com.evian.sqct.bean.shop.Shop;
import com.evian.sqct.bean.shop.ShopTimeframe;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageAppMerchantTodayStatisticsDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageShopLeagueClientsSelectDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcBackstageShopSelectDTO;
import com.evian.sqct.bean.shop.inputDTO.ProcDisParkGetTicketAccountDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseShopManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import com.qiniu.common.QiniuException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2018年10月8日 上午11:22:03
 * @author XHX
 * @Description 店铺action
 */
@RestController
@RequestMapping("/evian/sqct/shop")
public class ShopAction extends BaseAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseShopManager baseShopManager;
	
	@RequestMapping("showComTenantShop.action")
	public ResultVO showComTenantShop(Integer eid, Integer accountId) {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String,Object>> findShopByEidAndUserId = baseShopManager
				.findShopByEidAndUserId(accountId,eid);
		List<Map<String, Object>> resultMaps = new ArrayList<Map<String, Object>>();
		for (Map<String,Object> findShopModel : findShopByEidAndUserId) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopId", findShopModel.get("shopId"));
			map.put("shopName", findShopModel.get("shopName"));
			map.put("eid", findShopModel.get("eid"));
			map.put("pictureUrl", findShopModel.get("pictureUrl"));
			map.put("ifLine", findShopModel.get("ifLine"));
			map.put("shopManager", findShopModel.get("shopManager"));
			map.put("location", findShopModel.get("location"));
			map.put("address", findShopModel.get("address"));
			map.put("isReceiveOrder",findShopModel.get("isReceiveOrder"));
			resultMaps.add(map);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("shoplings", resultMaps);
		resultMap.put("isHaveShop", true);
		if (findShopByEidAndUserId.size() < 1) {
			resultMap.put("isHaveShop", false);
		}

		return new ResultVO(resultMap);
	}
	
	/**
	 * 5.商户选择店铺
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopId
	 *            店铺Id
	 * @return
	 */
	@RequestMapping("comTenantOptShop.action")
	public ResultVO comTenantOptShop(String clientId) {
		if(clientId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		return new ResultVO();
	}
	
	/**
	 * 6.商户创建店铺
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopName
	 *            店的名称
	 * @param shopNum
	 *            店的编号
	 * @param area
	 *            所在地区
	 * @param address
	 *            详细地址
	 * @param shopCoordinate
	 *            店铺坐标
	 * @param businessTime
	 *            营业时间
	 * @param averageTime
	 *            平均送达
	 * @param firmName
	 *            公司名称
	 * @param shopMainImg
	 *            店铺主图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createrShop.action")
	public ResultVO createrShop(Integer eid,String createUser,String shopName,String shopNo,Integer cityId,String address,Integer districtId,String tel,String linkman,String location,String startTime,String endTime,Integer sendOnTime,String pictureUrl,Integer accountId,String description,String shopType,String scopeDescription,Double minSendPrice,Double freight,Boolean ifLine) throws Exception {
		if(eid==null||createUser==null||shopName==null||shopNo==null||cityId==null||address==null||districtId==null||tel==null||linkman==null||location==null||startTime==null||endTime==null||sendOnTime==null||pictureUrl==null||accountId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String upResult = QiniuFileSystemUtil.uploadShearPic(pictureUrl);
		if (!StringUtils.isEmpty(upResult)) {
			JSONObject ject = JSON.parseObject(upResult);
			if (!StringUtils.isEmpty((String) ject.get("hash"))
					&& !StringUtils.isEmpty((String) ject.get("key"))) {

				upResult = QiniuConfig.namespace + (String) ject.get("key");
			}
		}
		String addShop = baseShopManager.addShop(eid, shopNo, shopName,
				address, tel, linkman, cityId, districtId, location,
				description, upResult, shopType, startTime, endTime,
				scopeDescription, sendOnTime, minSendPrice, freight,
				createUser, ifLine, accountId);
		if(!"1".equals(addShop)){
			throw new ResultException(addShop);
		}
		return new ResultVO();
	}
	
	/**
	 * 7.店铺管理
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopId
	 *            店铺Id
	 * @return
	 */
	@RequestMapping("shopManage.action")
	public ResultVO shopManage() {
		return new ResultVO();
	}
	
	/**
	 * 8.店铺认证
	 * 
	 * @param shopId
	 *            店铺Id
	 * @param shopName
	 *            公司名称
	 * @param businLicenNo
	 *            营业执照号
	 * @param businLicenImg
	 *            营业执照
	 * @param juriPerName
	 *            法人姓名
	 * @param juriPerIDcardFront
	 *            法人身份证正面
	 * @param juriPerIDcardCon
	 *            法人身份证反面
	 * @param clientPhone
	 *            手机号
	 * @param identifyingCode
	 *            验证码
	 * @return
	 */
	@RequestMapping("shopIdentification.action")
	public ResultVO shopIdentification() {
		return new ResultVO();
	}
	
	/**
	 * 9.店铺认证验证验证码
	 * 
	 * @param clientPhone
	 *            手机号
	 * @return
	 */
	@RequestMapping("shopIdentificationCode.action")
	public ResultVO shopIdentificationCode() {
		return new ResultVO();
	}
	
	/**
	 * 10.商户信息
	 * 
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("clientInfo.action")
	public ResultVO clientInfo() {
		return new ResultVO();
	}
	
	/**
	 * 11.商户商品管理
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("commodityManage.action")
	public ResultVO commodityManage(Integer eid,Integer shopId) {
		if(eid==null||shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> commodityManage = baseShopManager.commodityManage(eid, shopId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("count", commodityManage.size());
		resultMap.put("productClass", commodityManage);
		logger.info("resultMap = "+resultMap);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 12.店铺已绑定商品展示
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("showBindCommodity.action")
	public ResultVO showBindCommodity() {
		return new ResultVO();
	}
	
	/**
	 * 14.商品绑定
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param commodityId
	 *            商品ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("commodityBindOperat.action")
	public ResultVO commodityBind(Integer shopId,Integer pid,Integer lineBit,Boolean recommend) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||pid==null||lineBit==null||recommend==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String productLine = baseShopManager.productLine(pid, shopId, recommend, lineBit);
		if(!"1".equals(productLine)){
			throw new ResultException(productLine);
		}
		return new ResultVO();
	}
	
	/**
	 * 15.店铺未绑定商品展示
	 * 
	 * @param shopId
	 *            店铺ID
	 * @param clientId
	 *            用户身份ID
	 * @return
	 */
	@RequestMapping("showNoBindCommodity.action")
	public ResultVO showNoBindCommodity() {
		return new ResultVO();
	}
	
	/**
	 * 1.增加商品
	 * 
	 * @param clientId
	 *            用户身份ID
	 * @param shopId
	 *            店铺ID
	 * @param commodityName
	 *            商品名称
	 * @param commodityPrice
	 *            商品价格
	 * @param commodityImg
	 *            商品图片
	 * @return
	 */
	@RequestMapping("addCommodity.action")
	public ResultVO addCommodity() {
		return new ResultVO();
	}
	
	/**
	 * 20.选择区域
	 * 
	 * @param eid
	 *            企业ID
	 * @return
	 */
	@RequestMapping("queryCity.action")
	public ResultVO queryCity(Integer eid) {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> findAreaByEid = baseShopManager.findAreaByEid(eid);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("allCitys", findAreaByEid);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 显示店铺信息
	 * 
	 * @return
	 */
	@RequestMapping("showShopInfo.action")
	public Map<String, Object> showShopInfo(ProcBackstageShopSelectDTO dto) {
		return baseShopManager.findShopByEidAndShopId(dto);
		/*List<Map<String, Object>> resultMaps = new ArrayList<Map<String, Object>>();
		List<FindShopModel> shop = baseShopManager.findShopByEidAndShopId(dto);
		for (FindShopModel findShopModel : shop) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopId", findShopModel.getShopId());
			map.put("shopName", findShopModel.getShopName());
			map.put("eid", findShopModel.getEid());
			map.put("pictureUrl", findShopModel.getPictureUrl());
			map.put("location", findShopModel.getLocation());
			map.put("address", findShopModel.getAddress());
			resultMaps.add(map);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("shoplings", resultMaps);
		return resultMap;*/
	}
	
	/**
	 * 19.商户更改店铺信息
	 * 
	 * @param clientId
	 *            商户ID
	 * @param shopName
	 *            店的名称
	 * @param shopNum
	 *            店的编号
	 * @param area
	 *            所在地区
	 * @param address
	 *            详细地址
	 * @param shopCoordinate
	 *            店铺坐标
	 * @param businessTime
	 *            营业时间
	 * @param averageTime
	 *            平均送达
	 * @param firmName
	 *            公司名称
	 * @param shopMainImg
	 *            店铺主图
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateShopInfo.action")
	public ResultVO updateShopInfo(Integer shopId,Integer eid,String createUser,String shopName,String shopNo,Integer cityId,String address,Integer districtId,String tel,String linkman,String location,String startTime,String endTime,Integer sendOnTime,String pictureUrl,String description,String shopType,String scopeDescription,Double minSendPrice,Double freight,Boolean ifLine) throws Exception {
		if(shopId==null||eid==null||createUser==null||shopName==null||shopNo==null||cityId==null||address==null||districtId==null||tel==null||linkman==null||location==null||startTime==null||endTime==null||sendOnTime==null||pictureUrl==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Shop shop = new Shop();
		shop.setShopId(shopId);
		shop.setEid(eid);
		shop.setShopNo(shopNo);
		shop.setShopName(shopName);
		shop.setAddress(address);
		shop.setTel(tel);
		shop.setLinkman(linkman);
		shop.setCityId(cityId);
		shop.setDistrictId(districtId);
		shop.setLocation(location);
		shop.setDescription(description);
		shop.setShopType(shopType);
		shop.setStartTime(startTime);
		shop.setEndTime(endTime);
		shop.setScopeDescription(scopeDescription);
		shop.setSendOnTime(sendOnTime);
		shop.setMinSendPrice(minSendPrice);
		shop.setFreight(freight);
		shop.setCreateUser(createUser);
		shop.setIfLine(ifLine);
		String upResult = QiniuFileSystemUtil.uploadShearPic(pictureUrl);
		if (!StringUtils.isEmpty(upResult)) {
			JSONObject ject = JSON.parseObject(upResult);
			if (!StringUtils.isEmpty((String) ject.get("hash"))
					&& !StringUtils.isEmpty((String) ject.get("key"))) {

				upResult = QiniuConfig.namespace + (String) ject.get("key");
			}
		}
		shop.setPictureUrl(upResult);
		Map<String, Object> updateShop = baseShopManager.updateShop(shop);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (updateShop.get("TAG") != null
				&& "1".equals(updateShop.get("TAG"))) {
			resultMap.put("shopId", updateShop.get("shopId"));
			resultMap.put("pictureUrl", upResult);
			return new ResultVO();
		}else {
			throw new ResultException((String) updateShop.get("TAG"));
		}
	}
	
	@RequestMapping("showShopCodes.action")
	public ResultVO showShopCodes(Integer shopId,Integer eid) throws Exception {
		if(shopId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> shopCodes = baseShopManager.findShopCode(eid, shopId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("shopCodes", shopCodes);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 42.查询配送时间
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findTimeframe.action")
	public ResultVO findTimeframe(Integer shopId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<ShopTimeframe> findTimeframe = baseShopManager.findTimeframe(eid, shopId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("timeframes", findTimeframe);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 43.修改配送时间
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upTimeframe.action")
	public ResultVO upTimeframe(String times,Integer shopId,Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(times==null||shopId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}

		JSONArray parseArray = JSON.parseArray(times);
		String upTimeframe = baseShopManager.upTimeframe(eid,shopId,parseArray);
		if(!"1".equals(upTimeframe)){
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		return new ResultVO();
	}
	
	/**
	 * 84.修改商品属性
	 * @return
	 * @throws Exception
	 * 
	 * 跟中台不同  下架商品是删除e_shop_product 表
	 * 中台是下架  该商品
	 */
	@RequestMapping("updateProductProperty.action")
	public ResultVO updateProductProperty(Integer shopId,Integer productId,Integer eid,Boolean enabled,Double price,Integer sort,Integer type) throws Exception {
		if(productId==null||eid==null||type==null||shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		
		if(type.intValue()==0&&(shopId==null||productId==null||enabled==null||price==null)) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}else if(type.intValue()==1&&(shopId==null||productId==null||price==null)) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}else if(type.intValue()==2&&(productId==null||sort==null||eid==null)) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}

		baseShopManager.updateProductProperty(shopId,productId, eid, enabled, price, sort, type);
			
		return new ResultVO();
	}
	

	/**
	 * 85.查询企业二维码（公众号二维码，小程序推客码）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findEnterpriseQRCode.action")
	public ResultVO findEnterpriseQRCode(Integer eid,String account) throws Exception {
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> selectEnterpriseQRCode = baseShopManager.selectEnterpriseQRCode(eid, account);
		return new ResultVO(selectEnterpriseQRCode);
	}
	
	/**
	 * 86.app商户端公告
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("appMerchantNotify.action")
	public ResultVO appMerchantNotify(Integer eid) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> appMerchantNotify = baseShopManager.appMerchantNotify(eid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appMerchantNotify", appMerchantNotify);
		return new ResultVO(appMerchantNotify);
	}
	
	/**
	 * 87.手机商户端门店今日统计
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("appMerchantTodayStatistics.action")
	public Map<String,Object> appMerchantTodayStatistics(@Valid ProcBackstageAppMerchantTodayStatisticsDTO dto) throws Exception {
		return baseShopManager.appMerchantTodayStatistics(dto);
	}
	

	/**
	 * 89.修改水店上下线
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateShopIfLine.action")
	public ResultVO updateShopIfLine(Boolean ifLine,Integer eid,Integer shopId) throws Exception {
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(ifLine==null||eid==null||shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		baseShopManager.updateShopIfLine(ifLine, eid, shopId);
		return new ResultVO();
	}
	
	/**
	 * 94.水店用户信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shopLeagueClientsSelect.action")
	public Map<String, Object> shopLeagueClientsSelect(ProcBackstageShopLeagueClientsSelectDTO dto){
		return baseShopManager.shopLeagueClientsSelect(dto);
	}
	

	/**
	 * 95.电子票有效剩余量
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTicketAccount.action")
	public Map<String,Object> getTicketAccount(@Valid ProcDisParkGetTicketAccountDTO dto) throws Exception {
		return baseShopManager.getTicketAccount(dto);
	}
	
	/**
	 * 96.电子票消费列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("ticketSelectMx.action")
	public ResultVO ticketSelectMx(Integer eid,Integer clientId,Integer shopId) throws Exception {
		if(clientId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> ticketAccount = baseShopManager.ticketSelectMx(eid, clientId,shopId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("consumeList", ticketAccount);
		return new ResultVO(resultMap);
	}

	/**
	 * 97.用户优惠卷
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectMyVouchers.action")
	public ResultVO selectMyVouchers(Integer clientId,Integer eid) throws Exception {
		if(clientId==null||eid==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> selectMyVouchers = baseShopManager.selectMyVouchers(clientId,eid);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Vouchers", selectMyVouchers);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 101.查询店铺内容和品牌
	 * @param shopId
	 * @return
	 */
	@RequestMapping("findShopAndBrand.action")
	public ResultVO findShopAndBrand(Integer shopId){
		if(shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> resultMap = baseShopManager.selectShopAndBrand(shopId);
		return new ResultVO(resultMap);
	}
	
	/**
	 * 102.修改店铺名称图片电话
	 * @param shopId
	 * @return
	 */
	@RequestMapping("updateShopName.action")
	public ResultVO updateShopName(Integer shopId,String pictureUrl, String tel, String shopName,String startTime,String	endTime,String address,String location) throws QiniuException {
		if(shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String upResult = null;
		if(!StringUtils.isEmpty(pictureUrl)) {
			upResult = QiniuFileSystemUtil.uploadShearPic(pictureUrl);
			if (!StringUtils.isEmpty(upResult)) {
				JSONObject ject = JSON.parseObject(upResult);
				if (!StringUtils.isEmpty((String) ject.get("hash"))
						&& !StringUtils.isEmpty((String) ject.get("key"))) {

					upResult = QiniuConfig.namespace + (String) ject.get("key");
				}
			}

		}
		baseShopManager.updateShop(shopId, upResult, tel, shopName,startTime,endTime,address,location);
		return new ResultVO();
	}
	
	/**
	 * 109.添加店铺商品
	 * @param shopId
	 * @return
	 */
	@RequestMapping("addShopProduct.action")
	public ResultVO addShopProduct(Integer shopId,Integer pid,Double vipPrice,Integer cid){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(shopId==null||pid==null||vipPrice==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		baseShopManager.insertShopProduct(shopId, pid, vipPrice,cid);
		return new ResultVO();
	}

	/**
	 * 139.地址坐标是否在水店围栏内
	 */
	@RequestMapping("validateShopWL.action")
	public String delAddress(String location, Integer shopId,String appId) throws Exception{
		String getSysEnterPriseTemplate = baseShopManager.validateShopWL(location,shopId,appId);
		return getSysEnterPriseTemplate;
	}


	/**
	 * 173.商品库存数量初始化
	 * @param shopId
	 * @return
	 */
	@RequestMapping("productStockNumInit.action")
	public ResultVO productStockNumInit(Integer eid,Integer pid,Integer shopId,Integer initStockNum,Integer saleStockNum,String createUser){
		if(eid==null||shopId==null||pid==null||initStockNum==null||saleStockNum==null||createUser==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String tag = baseShopManager.Proc_Backstage_product_stock_num_init(eid, pid, shopId, initStockNum, saleStockNum, createUser);
		if(!"1".equals(tag)){
			throw new ResultException(tag);
		}
		return new ResultVO();
	}
	/**
	 * 174.商品库存数量重新初始化
	 * @return
	 */
	@RequestMapping("productStockNumReInit.action")
	public ResultVO productStockNumReInit(Integer eid,Integer tsId){
		if(eid==null||tsId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		String tag = baseShopManager.Proc_Backstage_product_stock_num_reInit(eid, tsId);
		if(!"1".equals(tag)){
			throw new ResultException(tag);
		}
		return new ResultVO();
	}

	/**
	 * 181.配送费提现账户总账
	 * @return
	 */
	@RequestMapping("generalLedgerOfDeliveryFeeWithdrawalAccount.action")
	public ResultVO generalLedgerOfDeliveryFeeWithdrawalAccount(Integer shopId, Integer eid){
		if(eid==null||shopId==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		Map<String, Object> result = baseShopManager.generalLedgerOfDeliveryFeeWithdrawalAccount(shopId, eid);
		return new ResultVO(result);
	}

	/**
	 * 182.店铺提现
	 * @return
	 */
	@RequestMapping("shopWithdrawDeposit.action")
	public ResultVO shopWithdrawDeposit(Integer state,Integer shopId,Integer eid,Integer clientId, Double withdrawDepositMoney,String account,String appId,String openId,String ip,String remark,String APPUserName)throws Exception{
		if(state==null||shopId==null||eid==null||clientId==null||withdrawDepositMoney==null||account==null||appId==null||openId==null||ip==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		if(state==1){
			baseShopManager.withdrawalOfDeliveryFee(eid, clientId, shopId, account, withdrawDepositMoney, remark, appId, openId, ip,APPUserName);
		}else{
			// 提现类型错误
			return new ResultVO(ResultCode.CODE_ERROR_WITHDRAW_DEOISUT);
		}
		return new ResultVO();
	}
	/**
	 * 183.提现订单查询
	 * @return
	 */
	@RequestMapping("withdrawDepositOrderSelect.action")
	public ResultVO withdrawDepositOrderSelect(Integer eid,Integer shopId,String begDate,String endDate,String RE_TAG,Integer PageIndex, final Integer PageSize){
		if(begDate==null||shopId==null||eid==null||endDate==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> maps = baseShopManager.withdrawDepositOrderSelect(eid, shopId, begDate, endDate, RE_TAG,PageIndex,PageSize);
		Map<String,Object> data = new HashMap<>();
		data.put("withdrawDepositOrders",maps);
		return new ResultVO(data);

	}

	/**
	 * 184.提现记录查询
	 * @return
	 */
	@RequestMapping("withdrawDepositRecord.action")
	public ResultVO withdrawDepositRecord(Integer eid,Integer shopId,String begDate,String endDate,String RE_TAG,Integer PageIndex, Integer PageSize){
		Map<String, Object> parMap = CallBackPar.getParMap();
		if(begDate==null||shopId==null||eid==null||endDate==null) {
			return new ResultVO(ResultCode.CODE_ERROR_PARAM);
		}
		List<Map<String, Object>> maps = baseShopManager.withdrawDepositRecord(eid, shopId, begDate, endDate, RE_TAG,PageIndex,PageSize);
		Map<String,Object> data = new HashMap<>();
		data.put("withdrawDepositRecord",maps);
		return new ResultVO(data);
	}
}
