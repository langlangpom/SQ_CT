package com.evian.sqct.dao;

import java.util.List;
import java.util.Map;

/**
 * @date   2018年12月26日 下午12:59:51
 * @author XHX
 * @Description 该函数的功能描述
 */
public interface IWxHBDao {

	/**
	 * 
	 * @param return_code
	 * @param return_msg
	 * @param result_code
	 * @param err_code
	 * @param err_code_des
	 * @param mch_billno
	 * @param mch_id
	 * @param wxappid
	 * @param re_openid
	 * @param total_amount
	 * @param send_listid
	 * @return
	 */
	String redpackUserRecordWechatSendLogOperat(String return_code,String return_msg,String result_code,String err_code,String err_code_des,String mch_billno,String mch_id,String wxappid,String re_openid,Integer total_amount,String send_listid);
	
	List<Map<String, Object>> redpackSserRecordSendSelect(String openId);
	List<Map<String, Object>> redpackSserRecordResendSelect(String openId,Integer recordId);
	
	/**
	 * 查询需要查询红包记录的订单
	 * @return
	 */
	List<Map<String, Object>> recordSelectForValid();
	
	/**
	 * 
	 * @param sendSign
	 * @param validStatus 腾讯官网验证记录结果：0未验证 1:发放中 2:已发放待领取 3:发放失败 4:已领取 5:退款中 6:已退款
	 * @return
	 */
	String updateForValid(String sendSign,Integer validStatus);
}
