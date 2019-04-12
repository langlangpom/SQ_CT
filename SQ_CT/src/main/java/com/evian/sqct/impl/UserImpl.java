package com.evian.sqct.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.evian.sqct.bean.user.Ecity;
import com.evian.sqct.bean.user.Eclient;
import com.evian.sqct.bean.user.UserModel;
import com.evian.sqct.bean.user.UserModel2;
import com.evian.sqct.bean.user.UserModel3;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.util.DES3_CBCUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;

@Repository("userDao")
public class UserImpl implements IUserDao {

	private static final Logger logger = LoggerFactory
			.getLogger(UserImpl.class);

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,Object> userLogin(final String loginName,
			final String passWord, final String lastLoginIP) {
		Map<String,Object> result = jdbcTemplate.execute(
				"{call Proc_Backstage_Auth_Login_Customer(?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setString("loginName", loginName);
						cs.setString("passWord", passWord);
						cs.setString("lastLoginIP", lastLoginIP);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						String tag = cs.getString("TAG");
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("TAG", tag);
						ResultSet rs = null;
						try {
							rs = cs.executeQuery();
							List<UserModel2> result = ResultSetToBeanHelper.resultSetToList(rs, UserModel2.class);
							map.put("result", result.get(0));
						} catch (Exception e) {
							
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
						return map;
					}
				});
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#userLogin_v2(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> userLogin_v2(final String loginName,final String passWord) {
		Map<String,Object> result = jdbcTemplate.execute(
				"{call Proc_Backstage_appMerchant_account_login(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setString("account", loginName);
						cs.setString("userPwd", passWord);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						String tag = cs.getString("TAG");
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("TAG", tag);
						ResultSet rs = null;
						try {
							rs = cs.executeQuery();
							List<UserModel3> result = ResultSetToBeanHelper.resultSetToList(rs, UserModel3.class);
							map.put("result", result.get(0));
						} catch (Exception e) {
							
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
						return map;
					}
				});
		return result;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UserModel selectUserByName(final String loginName) {
		UserModel result = jdbcTemplate
				.execute(
						"{call Proc_Backstage_auth_user_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
						new CallableStatementCallback() {
							int i = 0;

							public Object doInCallableStatement(
									CallableStatement cs) throws SQLException {
								cs.setObject(++i, null);
								cs.setObject(++i, loginName);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.setObject(++i, null);
								cs.registerOutParameter("Count", Types.NVARCHAR);// 注册输出参数的类型
								cs.execute();
								ResultSet rs = cs.executeQuery();

								/*
								 * try { List<UserModel> result =
								 * ResultSetToBeanHelper.resultSetToList(rs,
								 * UserModel.class); Map<String, Object> map =
								 * new HashMap<String, Object>();
								 * if(result.size()<=1){ map.put("nickName",
								 * result.get(0).getNickName());
								 * map.put("photo", result.get(0).getPhoto());
								 * map.put("userId", result.get(0).getUserId());
								 * } return new
								 * JsonResponseHelper(JsonResponseResultCodeDefine
								 * .SUCCESS, map).getJsonModel().toString(); }
								 * catch (Exception e) { logger.error("[ex:{}]",
								 * new Object[] { e }); return
								 * JsonResponseHelper
								 * .getDBErrorJsonModel().toString(); }
								 */
								try {
									List<UserModel> result = ResultSetToBeanHelper
											.resultSetToList(rs,
													UserModel.class);
									if (result.size() > 0 && result.size() <= 1) {

										return result.get(0);
									}
									return null;
								} catch (Exception e) {
									logger.error("[ex:{}]", new Object[] { e });
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
	public boolean saveSMSFindPwd(final String cellPhone, final String ip) {
		String result = jdbcTemplate.execute(
				"{call Proc_Service_Sms_SAVEup_e_sms_send(?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setString("cellPhone", cellPhone);
						cs.setString("ip", ip);
						cs.setString("msgContent", "");
						cs.setString("TAG", "SHZHMM");
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		if (result != null && result.toString().equals("1"))
			return true;
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean setSMSFindPwd(final String cellPhone, final String msgCode,
			final String npwd) {
		String result = jdbcTemplate.execute(
				"{call Proc_Backstage_AppCustomer_Find_Pwd(?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setString("phone", cellPhone);
						cs.setString("msgCode", msgCode);
						cs.setString("npwd", npwd);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		if (result != null && result.toString().equals("1"))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#setSMSFindPwd_v2(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean setSMSFindPwd_v2(final String cellPhone, final String msgCode,
			final String npwd) {
		String result = jdbcTemplate.execute(
				"{call Proc_Backstage_appMerchant_account_findPassword(?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setString("account", cellPhone);
						cs.setString("msgCode", msgCode);
						cs.setString("npwd", npwd);
						cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
						cs.execute();
						return cs.getString("TAG");
					}
				});
		if (result != null && result.toString().equals("1"))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#getNowDbName()
	 */
	@Override
	public String getNowDbName(){
		  String sql = "Select Name From Master..SysDataBases Where DbId=(Select Dbid From Master..SysProcesses Where Spid = @@spid)";
		  return jdbcTemplate.queryForObject(sql, String.class);
		 }

	
	@Override
	public Eclient findUniqueBy(String account) {
		String sql = "select clientId, account, passWord, email, boundPhone, nickName, sex, birthday, photo, status, sdkType, mobileIMEL, mobileType, sdkVer, loginNumber, identityCode, dateLastLogin, dateCreated,weixinId, location, wxphoto, appver, cityId from e_client where account=?";
		try {
			Eclient queryForObject = jdbcTemplate.queryForObject(sql,new Object[]{account}, new BeanPropertyRowMapper<>(Eclient.class));
			return queryForObject;
		} catch (DataAccessException e) {
			return null;
		}
	}

	
	@Override
	public Boolean updateClient(Eclient eclient, int clientId) {
		String sql = "update e_client set loginNumber=?,dateLastLogin=?,appVer=?,identityCode=? where clientId=?";
		int update = jdbcTemplate.update(sql, new Object[]{eclient.getLoginNumber(),eclient.getDateLastLogin(),eclient.getAppVer(),eclient.getIdentityCode(),clientId});
		if(update==1) {
			return true;
		}else {
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String clientRegisterGetCode(final String cellphone, final String ip, final String weixinId, final int tokenId) {
		String result = "";
		if (weixinId.length() == 0) {
			result = jdbcTemplate.execute(
					"{call Proc_DisPark_Register_GetCode(?,?)}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException {
							cs.setString("cellPhone", cellphone);
							cs.setString("ip", ip);
							ResultSet rs = cs.executeQuery();
							try {
								List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
								return result.get(0).get("TAG").toString();
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
		}else {
			result = jdbcTemplate.execute(
					"{call Proc_DisPark_Register_GetCode_TOWX(?,?,?,?)}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException {
							cs.setString("cellPhone", cellphone);
							cs.setString("ip", ip);
							cs.setString("weixinId", weixinId);
							cs.setInt("eid", tokenId);
							ResultSet rs = cs.executeQuery();
							try {
								List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
								return result.get(0).get("TAG").toString();
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
		}
		return result;
	}

	
	@Override
	public Ecity getCityByCode(Integer baiduCode) {
		String sql = "select cityId, cityName, zipCode, baiduCode, location, zoom, provinceId, dateCreated, ifLine, dateLine, ifHot, dateUpdated, updateUser from e_gis_city where baiduCode=?";
		try {
			Ecity queryForObject = jdbcTemplate.queryForObject(sql,new Object[]{baiduCode}, new BeanPropertyRowMapper<>(Ecity.class));
			return queryForObject;
		} catch (DataAccessException e) {
			return null;
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> registerVerifyCode(final String cellphone, final String passWord, final String code, final String sdkType,
			final String mobileIMEL, final String mobileType, final String sdkVer, final String weixinId, final String location, final int cityId,
			final String appId, final int tokenId, final int appType, final String regSource) {
		Map<String, Object> result = null;
		if (weixinId.length() == 0) {
			
		}else {
			result = jdbcTemplate.execute(
					"{call Proc_DisPark_Register_Verify_Code_TOWX(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException {
							cs.setObject("cellPhone", cellphone);
							cs.setObject("passWord", passWord);
							cs.setObject("code", code);
							cs.setObject("sdkType", sdkType);
							cs.setObject("mobileIMEL", mobileIMEL);
							cs.setObject("mobileType", mobileType);
							cs.setObject("sdkVer", sdkVer);
							cs.setObject("weixinId", weixinId);
							cs.setObject("location", location);
							cs.setObject("cityId", cityId);
							cs.setObject("appId", appId);
							cs.setObject("eid", tokenId);
							cs.setObject("appType", appType);
							cs.setObject("regSource", regSource);
							ResultSet rs = cs.executeQuery();
							Map<String, Object> hashResult = new HashMap<String, Object>();
							try {
								List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
								Map fristMap = result.get(0);
								if (StringUtils.isEmpty(fristMap.get("TAG").toString()) == false) {
					                String tagResult = fristMap.get("TAG").toString();
					                hashResult.put("result", tagResult);
					                if (tagResult.equals("E00000")) {
					                    int clientId = (Integer) (fristMap.get("clientId"));
					                    Eclient eclient = getById(clientId);
					                    if (eclient != null) {
					                        //生成客户唯一身份码
					                        String tickt = DES3_CBCUtil.des3EncodeCBC(String.format("%s|%s|%s", clientId,
					                                cellphone, eclient.getPassWord()));
					                        if (StringUtils.isEmpty(tickt) == false) {
					                            eclient.setIdentityCode(tickt);
					                            updateClient(eclient, clientId);
					                            hashResult.put("clientId", tickt);
					                            hashResult.put("cId", clientId);
					                        }
					                    }
					                } else {
					                    hashResult.put("cId", 0);
					                    hashResult.put("clientId", "");
					                }
					            }
					            return hashResult;
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
		}
		return result;
	}

	@Override
	public Eclient getById(Integer clientId) {
		String sql = "select  clientId, account, passWord, email, boundPhone, nickName, sex, birthday, photo, status, sdkType, mobileIMEL, mobileType, sdkVer, loginNumber, identityCode, dateLastLogin, dateCreated,weixinId, location, wxphoto, appver, cityId  from e_client where clientId=?";
		try {
			Eclient queryForObject = jdbcTemplate.queryForObject(sql,new Object[]{clientId}, new BeanPropertyRowMapper<>(Eclient.class));
			return queryForObject;
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> regeditZongSongCode(final String source, final String account, final int clientId, final int cityId, final int appType, final int eid) {
		Map<String,String> result = jdbcTemplate.execute(
				"{call PROC_LIDAN_ZhuangHu_SAVEup_Code(?,?,?,?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setString("DataSource", source);
                        cs.setString("account", account);
                        cs.setInt("clientID", clientId);
                        cs.setInt("CityID", cityId);
                        cs.setInt("appType", appType);
                        cs.setInt("eid", eid);
						ResultSet rs = cs.executeQuery();
						Map<String, String> tempMap = new HashMap<String, String>();
                        if (rs != null) {
                            while (rs.next()) {
                                tempMap.put("TAG", rs.getString("TAG"));
                                tempMap.put("typeName", rs.getString("typeName"));
                            }
                            rs.close();
                        }
                        return tempMap;
					}
				});
		return result;
	}

	
	@Override
	public Integer updateHealthCertificate(Integer accountId,Integer eid,String headImg,String fullname,String healthCertificateImg,String SQ_IDCard) {
		String sql = "update e_staff set picture=?,name=?,healthCard=?,SQ_IDCard=? where id=? and eid=?";
		int update = jdbcTemplate.update(sql, new Object[]{headImg,fullname,healthCertificateImg,SQ_IDCard,accountId,eid});
		return update;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#selectAppMerchantJpush(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> selectAppMerchantJpush(Integer accountId) {
		String sql = "select accountId, regeditId, sourceId, dateCreated, createUser from e_appMerchant_jpush_regedit where accountId=?";
		List<Map<String, Object>> query = jdbcTemplate.query(sql, new Object[]{accountId},ResultSetToBeanHelper.resultSetToListMap());
		if(query!=null&&query.size()>0) {
			return query.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#insertAppMerchantJpush(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer insertAppMerchantJpush(Integer accountId, String regeditId, Integer sourceId) {
		String sql = "insert into e_appMerchant_jpush_regedit(accountId,regeditId, sourceId) values(?,?,?)";
		int update = jdbcTemplate.update(sql, new Object[]{accountId,regeditId,sourceId});
		return update;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#updateAppMerchantJpush(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer updateAppMerchantJpush(Integer accountId, String regeditId, Integer sourceId) {
		String sql = "update e_appMerchant_jpush_regedit set regeditId=?,sourceId=? where accountId=?";
		int update = jdbcTemplate.update(sql, new Object[]{regeditId,sourceId,accountId});
		return update;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> clientAddressSelect(final Integer clientId,final Integer eid) {
		List<Map<String,Object>> result = jdbcTemplate.execute(
				"{call Proc_Backstage_client_address_select(?,?,?)}",
				new CallableStatementCallback() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException {
						cs.setObject("clientId", clientId);
						cs.setObject("eid", eid);
						cs.setObject("isSelectNoAudit", true);
						cs.execute();
						ResultSet rs = null;
						try {
							
							rs = cs.executeQuery();
							
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

}
