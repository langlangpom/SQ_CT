package com.evian.sqct.dao;

import com.evian.sqct.bean.groupBuy.input.FindGroupBuyProductReqDTO;

import java.util.List;
import java.util.Map;

/**
 * @date   2019年8月9日 上午10:49:54
 * @author XHX
 * @Description 拼团Dao
 */
public interface IGroupBuyDao {

	/**
	 * 查询拼团订单
	 * @return
	 */
	Map<String, Object> Proc_Backstage_groupbuy_order_select(String beginTime,String endTime,Integer eid,String orderNo,String nickName,Boolean isCommander,Boolean isRefund,String account,Integer groupBuyState,String paymentNo,String orderGroup,String eName,String pname,String pcode,String sdkType,Integer shopId,Boolean isFilterEndOrder,Integer sucMode,Integer minGroupBuyNum,Integer maxGroupBuyNum,Integer sortType,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);
	
	/**
	 * 拼团活动商品
	 * @param beginTime
	 * @param endTime
	 * @param eid
	 * @param cityId
	 * @param groupBuyType
	 * @param isEnabled
	 * @param eName
	 * @param shopName
	 * @param shopId
	 * @param pname
	 * @param pcode
	 * @param cid
	 * @param PageIndex
	 * @param PageSize
	 * @param IsSelectAll
	 * @return
	 */
	Map<String, Object> Proc_Backstage_groupbuy_product_select(FindGroupBuyProductReqDTO dto);
	
	List<Map<String, Object>> e_groupbuy_price_schemeByXaId(Integer xaId);

	/**
	 * 查询当前用户当前活动是否有未支付订单
	 * @param identityCode
	 * @param xaId
	 * @param pid
	 * @return
	 */
	List<Map<String,Object>> selectClientNotPayGroupBuyOrder(String identityCode, Integer eid, Integer xaId, Integer pid);

	/**
	 * 根据gboId 查询 参团人的clientId
	 * @param gboId
	 * @return
	 */
	List<Map<String,Object>> selectClientId_e_groupbuy_orderByGboId(Integer gboId);
	
}
