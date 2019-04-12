package com.evian.sqct.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.evian.sqct.dao.IWxHBDao;
import com.evian.sqct.util.ResultSetToBeanHelper;

/**
 * @date   2018年12月26日 下午1:03:52
 * @author XHX
 * @Description 该函数的功能描述
 */
@Repository("wxHBDao")
public class WxHBImpl implements IWxHBDao {

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String redpackUserRecordWechatSendLogOperat(final String return_code, final String return_msg, final String result_code,
			final String err_code, final String err_code_des, final String mch_billno, final String mch_id, final String wxappid, final String re_openid,
			final Integer total_amount, final String send_listid) {
		String result = jdbcTemplate.execute(
				"{call Proc_Backstage_redpack_user_record_wechat_send_log_operat(?,?,?,?,?,?,?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("return_code", return_code);
						cs.setObject("return_msg", return_msg);
						cs.setObject("result_code", result_code);
						cs.setObject("err_code", err_code);
						cs.setObject("err_code_des", err_code_des);
						cs.setObject("mch_billno", mch_billno);
						cs.setObject("mch_id", mch_id);
						cs.setObject("wxappid", wxappid);
						cs.setObject("re_openid", re_openid);
						cs.setObject("total_amount", total_amount);
						cs.setObject("send_listid", send_listid);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> redpackSserRecordSendSelect(final String openId) {
		List<Map<String,Object>> result = jdbcTemplate.execute(
				"{call Proc_Backstage_redpack_user_record_send(?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("openId", openId);
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					}
				});
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> redpackSserRecordResendSelect(final String openId,final Integer recordId) {
		List<Map<String,Object>> result = jdbcTemplate.execute(
				"{call Proc_Backstage_redpack_user_record_resend(?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("openId", openId);
						cs.setObject("recordId", recordId);
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> recordSelectForValid() {
		// Proc_Backstage_redpack_user_record_select_forValid
		List<Map<String,Object>> result = jdbcTemplate.execute(
				"{call Proc_Backstage_redpack_user_record_select_forValid }",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return result;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String updateForValid(final String sendSign, final Integer validStatus) {
		String result = jdbcTemplate.execute(
				"{call Proc_Backstage_redpack_user_record_valid(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("sendSign", sendSign);
						cs.setObject("validStatus", validStatus);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		return result;
	}

}
