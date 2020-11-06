package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.user.*;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.util.DES3_CBCUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.evian.sqct.util.SQLOper;
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

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String,Object> result = (Map<String,Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_Auth_Login_Customer(?,?,?,?)}",
				(CallableStatementCallback) cs -> {
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
						List<UserModel2> resul = ResultSetToBeanHelper.resultSetToList(rs, UserModel2.class);
						map.put("result", resul.get(0));
					} catch (Exception e) {

					}finally{
						if(rs!=null){
							rs.close();
						}
					}
					return map;
				});
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#userLogin_v2(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> userLogin_v2(final String loginName,final String passWord) {
		Map<String,Object> result = (Map<String,Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_appMerchant_account_login(?,?,?)}",
				(CallableStatementCallback) cs -> {
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
						List<UserModel3> resul = ResultSetToBeanHelper.resultSetToList(rs, UserModel3.class);
						map.put("result", resul.size()>0?resul.get(0):null);
						return map;
					} catch (Exception e) {
						return map;
					}finally{
						if(rs!=null){
							rs.close();
						}
					}
				});
		return result;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public UserModel selectUserByName(final String loginName) {
		UserModel result = (UserModel)jdbcTemplate
				.execute(
						"{call Proc_Backstage_auth_user_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
						(CallableStatementCallback) cs -> {
							int i = 0;
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
							cs.setObject(++i, null);
							cs.registerOutParameter("Count", Types.NVARCHAR);// 注册输出参数的类型
							cs.execute();
							ResultSet rs = cs.executeQuery();
							try {
								List<UserModel> resul = ResultSetToBeanHelper
										.resultSetToList(rs,
												UserModel.class);
								return resul.size() > 0 && resul.size() <= 1?resul.get(0):null;
							} catch (Exception e) {
								logger.error("[ex:{}]", new Object[] { e });
								return null;
							}finally{
								if(rs!=null){
									rs.close();
								}
							}
						});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean saveSMSFindPwd(final String cellPhone, final String ip) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Service_Sms_SAVEup_e_sms_send(?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setString("cellPhone", cellPhone);
					cs.setString("ip", ip);
					cs.setString("msgContent", "");
					cs.setString("TAG", "SHZHMM1");
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
				});
		return "1".equals(result);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean setSMSFindPwd(final String cellPhone, final String msgCode,
			final String npwd) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_AppCustomer_Find_Pwd(?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setString("phone", cellPhone);
					cs.setString("msgCode", msgCode);
					cs.setString("npwd", npwd);
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
				});
		return "1".equals(result);
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#setSMSFindPwd_v2(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean setSMSFindPwd_v2(final String cellPhone, final String msgCode,
			final String npwd) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_appMerchant_account_findPassword(?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setString("account", cellPhone);
					cs.setString("msgCode", msgCode);
					cs.setString("npwd", npwd);
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
				});
		return "1".equals(result);
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#getNowDbName()
	 */
	@Override
	public String getNowDbName(){
		  String sql = "Select Name From Master.SysDataBases Where DbId=(Select Dbid From Master.SysProcesses Where Spid = @@spid)";
		  return jdbcTemplate.queryForObject(sql, String.class);
		 }

	
	@Override
	public Eclient findUniqueBy(String account) {
		String sql = "select a.clientId, a.account, a.passWord, a.email, a.boundPhone, b.nickName, a.sex, a.birthday, b.photo, a.status, a.sdkType, a.mobileIMEL, a.mobileType, a.sdkVer, a.loginNumber, a.identityCode, a.dateLastLogin, a.dateCreated,a.weixinId, a.location, a.wxphoto, a.appver, a.cityId from e_client as a left join e_client_wx_photo as b on a.clientId=b.clientId  where account=?";
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
		return update==1;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String clientRegisterGetCode(final String cellphone, final String ip, final String weixinId, final int tokenId) {
		String result = "";
		if (weixinId.length() == 0) {
			result = (String)jdbcTemplate.execute(
					"{call Proc_DisPark_Register_GetCode(?,?)}",
					(CallableStatementCallback) cs -> {
						cs.setString("cellPhone", cellphone);
						cs.setString("ip", ip);
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return resul.get(0).get("TAG").toString();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}finally{
							if(rs!=null){
								rs.close();
							}
						}
					});
		}else {
			result = (String)jdbcTemplate.execute(
					"{call Proc_DisPark_Register_GetCode_TOWX(?,?,?,?)}",
					(CallableStatementCallback )cs -> {
						cs.setString("cellPhone", cellphone);
						cs.setString("ip", ip);
						cs.setString("weixinId", weixinId);
						cs.setInt("eid", tokenId);
						ResultSet rs = cs.executeQuery();
						try {
							List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							return resul.get(0).get("TAG").toString();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}finally{
							if(rs!=null){
								rs.close();
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
			result = (Map<String, Object>)jdbcTemplate.execute(
					"{call Proc_DisPark_Register_Verify_Code_TOWX(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
					(CallableStatementCallback) cs ->{
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
							List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
							Map fristMap = resul.get(0);
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
		Map<String,String> result = (Map<String, String>)jdbcTemplate.execute(
				"{call PROC_LIDAN_ZhuangHu_SAVEup_Code(?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
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
		String sql = "select accountId, regeditId, sourceId, dateCreated, createUser, platformId from e_appMerchant_jpush_regedit where accountId=?";
		List<Map<String, Object>> query = jdbcTemplate.query(sql, new Object[]{accountId},ResultSetToBeanHelper.resultSetToListMap());
		if(query!=null&&query.size()>0) {
			return query.get(0);
		}
		return null;
	}

	/** 根据极光Id查找用户 */
	@Override
	public List<Map<String, Object>> selectAppMerchantJpush(String regeditId) {
		String sql = "select accountId, regeditId, sourceId, dateCreated, createUser, platformId from e_appMerchant_jpush_regedit where regeditId=?";
		List<Map<String, Object>> query = jdbcTemplate.queryForList(sql, regeditId);
		return query;
	}

	/** 删除用户的极光Id */
	@Override
	public int removeAppMerchantJpush(Integer accountId,String regeditId){
		String sql = "delete from e_appMerchant_jpush_regedit where accountId=? and regeditId =?";
		return jdbcTemplate.update(sql, new Object[]{accountId,regeditId});
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#insertAppMerchantJpush(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer insertAppMerchantJpush(Integer accountId, String regeditId, Integer sourceId,Integer platformId) {
		String sql = "insert into e_appMerchant_jpush_regedit(accountId, regeditId, sourceId, platformId) values(?,?,?,?)";
		int update = jdbcTemplate.update(sql, new Object[]{accountId,regeditId,sourceId,platformId});
		return update;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#updateAppMerchantJpush(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer updateAppMerchantJpush(Integer accountId, String regeditId, Integer sourceId, Integer platformId) {
		String sql = "update e_appMerchant_jpush_regedit set regeditId=?,sourceId=?,platformId=?,updateCreated=GETDATE() where accountId=?";
		int update = jdbcTemplate.update(sql, new Object[]{regeditId,sourceId,platformId,accountId});
		return update;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> clientAddressSelect(final Integer clientId,final Integer eid) {
		List<Map<String,Object>> result = (List<Map<String,Object>>)jdbcTemplate.execute(
				"{call Proc_Backstage_client_address_select(?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("clientId", clientId);
					cs.setObject("eid", eid);
					cs.setObject("isSelectNoAudit", true);
					cs.execute();
					ResultSet rs = null;
					try {

						rs = cs.executeQuery();

						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						return resul;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}finally{
						if(rs!=null){
							rs.close();
						}
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StaffDTO> Proc_Backstage_staff_select(final Integer eid,final String shopName,final Integer shopId,final String phone, Boolean isVendorAccount, final Integer PageIndex, final Integer PageSize,
			final Boolean IsSelectAll) {
		List<StaffDTO> result = (List<StaffDTO>)jdbcTemplate.execute(
				"{call Proc_Backstage_staff_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("id", null);
					cs.setObject("eid", eid);
					cs.setObject("staffNo", null);
					cs.setObject("name", null);
					cs.setObject("phone", phone);
					cs.setObject("YW_ShopName", null);
					cs.setObject("isMove", null);
					cs.setObject("SQ_status", null);
					cs.setObject("SQ_check", null);
					cs.setObject("isHavePicture", null);
					cs.setObject("isHaveWorkCard", null);
					cs.setObject("isHaveHealthCard", null);
					cs.setObject("isHaveCreditCard", null);
					cs.setObject("eName", null);
					cs.setObject("isRelevanceYW", null);
					cs.setObject("shopName", shopName);
					cs.setObject("shopId", shopId);
					cs.setObject("isAudit", null);
					cs.setObject("isEnable", null);
					cs.setObject("isVendorAccount", isVendorAccount);
					if(IsSelectAll!=null&&IsSelectAll) {
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.setObject("PageIndex", null);
						cs.setObject("PageSize", null);
					}else {
						cs.setObject("IsSelectAll", false);
						if(PageIndex!=null&&PageSize!=null) {
							cs.setObject("PageIndex", PageIndex);
							cs.setObject("PageSize", PageSize);
						}else {
							cs.setObject("PageIndex", 1);
							cs.setObject("PageSize", 20);
						}
					}
					cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
					cs.execute();
					ResultSet rs = null;
					try {

						rs = cs.executeQuery();

						return ResultSetToBeanHelper.resultSetToList(rs, StaffDTO.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}finally{
						if(rs!=null){
							rs.close();
						}
					}
				});
		return result;
	}

	@Override
	public EAppMerchantAccountShop selectEAppMerchantAccountShop(Integer accountId, Integer eid, Integer shopId) {
		String sql = "select accountId, eid, shopId, createTime, createUser, shopManager from e_appMerchant_account_shop where accountId=? and eid=? and shopId=?";
		try {
			EAppMerchantAccountShop queryForObject = jdbcTemplate.queryForObject(sql, new Object[] {accountId,eid,shopId}, new BeanPropertyRowMapper<>(EAppMerchantAccountShop.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}

	@Override
	public Map<String, Object> selectEAppMerchantAccount(String account) {
		String sql="select top 1 accountId from e_appMerchant_account where account=?";
		List<Map<String, Object>> query = jdbcTemplate.query(sql, new Object[]{account},ResultSetToBeanHelper.resultSetToListMap());
		if(query!=null&&query.size()>0) {
			return query.get(0);
		}
		return null;
	}

	@Override
	public List<VendorShopAdministratorDTO> selectVendorShopAdmin(Integer eid,Integer shopId,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
		StringBuilder sql= new StringBuilder("select a.accountId,a.account,e.picture,e.name,c.userAuthorization from e_appMerchant_account as a ");
		sql.append("inner join e_appMerchant_account_shop b on a.accountId=b.accountId ");
		sql.append("inner join e_appMerchant_account_role c on a.roleId = c.roleId and a.eid=c.eid ");
		sql.append("inner join e_staff e on a.accountId = e.id  ");
		sql.append("where 1=1 ");
		
		List<Object> args = new ArrayList<Object>();
		StringBuilder sqlAppend = new StringBuilder();
		
		if(eid!=null) {
			sqlAppend.append(" and a.eid=? ");
			args.add(eid);
		}
		
		if(shopId!=null) {
			sqlAppend.append(" and b.shopId=? ");
			args.add(shopId);
		}
		
		int o = args.size();
		
		Object[] a = new Object[o];
		for (int i = 0; i < args.size(); i++) {
			a[i]=args.get(i);
		}
		
		sql.append(sqlAppend);
		
		if(PageIndex!=null&&PageSize!=null) {
			// 加上分页
			a = SQLOper.addPaging(sql, a, PageIndex, PageSize, false);
		}
		List<VendorShopAdministratorDTO> query = jdbcTemplate.query(sql.toString(), a,new BeanPropertyRowMapper<>(VendorShopAdministratorDTO.class));
		
		return query;
	}

	@Override
	public EAppMerchantAccountRole selectEAppMerchantAccountRoleByAccountId(Integer accountId) {
		String sql="select b.roleId, b.eid, b.roleName, b.userAuthorization, b.createTime, b.creater from e_appMerchant_account as a inner join e_appMerchant_account_role as b on a.roleId=b.roleId where a.accountId=?";
		try {
			EAppMerchantAccountRole queryForObject = jdbcTemplate.queryForObject(sql,new Object[] {accountId}, new BeanPropertyRowMapper<>(EAppMerchantAccountRole.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}

	@Override
	public EAppMerchantAccountEnterpriseRole selectEAppMerchantAccountEnterpriseRoleByAccountId(Integer accountId) {
		String sql="select b.roleId, b.eid, b.roleName, b.sign, b.isCanEditMenuItem, b.isEnable, b.createTime from e_appMerchant_account as a inner join e_appMerchant_account_enterprise_role as b on a.roleId=b.roleId where a.accountId=?";
		try {
			EAppMerchantAccountEnterpriseRole queryForObject = jdbcTemplate.queryForObject(sql,new Object[] {accountId}, new BeanPropertyRowMapper<>(EAppMerchantAccountEnterpriseRole.class));
			return queryForObject;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String Proc_Backstage_staff_add(final Integer shopId,final  Integer eid, final String name, final String phone, final String picture,
			final String SQ_remark, final String SQ_IDCard, final String workCard, final String healthCard, final String creditCard,
			final String SQ_staffNO, final Boolean isRelevanceEvian) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_staff_add(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("shopId", shopId);
					cs.setObject("eid", eid);
					cs.setObject("name", name);
					cs.setObject("phone", phone);
					cs.setObject("picture", picture);
					cs.setObject("SQ_remark", SQ_remark);
					cs.setObject("SQ_IDCard", SQ_IDCard);
					cs.setObject("workCard", workCard);
					cs.setObject("healthCard", healthCard);
					cs.setObject("creditCard", creditCard);
					cs.setObject("SQ_staffNO", SQ_staffNO);
					cs.setObject("isRelevanceEvian", isRelevanceEvian);
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
				});
		return result;
	}

	/**
	 * 查询该账户微信（小程序、公众号）是否注册了
	 * @return
	 */
	@Override
	public Integer selectWxIsRegister(Integer eid, String account) {
		String sql="select COUNT(id) as num from e_client_wxuser_mapping where cellphone=? and eid=?";
		try {
			Map<String, Object> queryForMap = jdbcTemplate.queryForMap(sql, new Object[] {account,eid});
			if(queryForMap.size()>0) {
				return (Integer) queryForMap.get("num");
			}
		} catch (DataAccessException e) {
		}
		return 0;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Proc_Backstage_shop_wechatliteapp_code_select(final String shopCode, final Integer eid,
			final Integer shopId, final String beginTime, final String endTime, final String managerFullName, final String tuikeFullName,
			final String shopName, final String eName, final Boolean isEnable, final String managerAccount, final String tuikeAccount,
			final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
		Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
				"{call Proc_Backstage_shop_wechatliteapp_code_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("shopCode", shopCode);
					cs.setObject("eid", eid);
					cs.setObject("shopId", shopId);
					cs.setObject("beginTime", beginTime);
					cs.setObject("endTime", endTime);
					cs.setObject("managerFullName", managerFullName);
					cs.setObject("tuikeFullName", tuikeFullName);
					cs.setObject("shopName", shopName);
					cs.setObject("eName", eName);
					cs.setObject("isEnable", isEnable);
					cs.setObject("managerAccount", managerAccount);
					cs.setObject("tuikeAccount", tuikeAccount);
					if(IsSelectAll!=null&&IsSelectAll) {
						cs.setObject("IsSelectAll", IsSelectAll);
						cs.setObject("PageIndex", null);
						cs.setObject("PageSize", null);
					}else {
						cs.setObject("IsSelectAll", false);
						if(PageIndex!=null&&PageSize!=null) {
							cs.setObject("PageIndex", PageIndex);
							cs.setObject("PageSize", PageSize);
						}else {
							cs.setObject("PageIndex", 1);
							cs.setObject("PageSize", 20);
						}
					}
					cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
					cs.execute();
					ResultSet rs = cs.executeQuery();
					Map<String, Object> map = new HashMap<String, Object>();
					try {
						List<TuikeManagerCodeDTO> resul = ResultSetToBeanHelper.resultSetToList(rs, TuikeManagerCodeDTO.class);
						map.put("codeList", resul);
						map.put("Count", cs.getInt("Count"));
						return map;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}finally{
						if(rs!=null){
							rs.close();
						}
					}
				});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String Proc_Backstage_shop_wechatliteapp_code_ManagerSetting(final String shopCode, final Integer managerClientId) {
		String result = (String)jdbcTemplate.execute(
				"{call Proc_Backstage_shop_wechatliteapp_code_ManagerSetting(?,?,?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("shopCode", shopCode);
					cs.setObject("managerClientId", managerClientId);
					cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
					cs.execute();
					return cs.getString("TAG");
				});
		return result;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#userLogin_v2(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> Proc_Backstage_appMerchant_account_enterprise_role_menu_select(final Integer roleId) {
		List<Map<String,Object>> result = (List<Map<String,Object>>)jdbcTemplate.execute(
				"{call Proc_Backstage_appMerchant_account_enterprise_role_menu_select(?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("roleId", roleId);
					cs.execute();
					ResultSet rs = null;
					try {

						rs = cs.executeQuery();

						List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
						return resul;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}finally{
						if(rs!=null){
							rs.close();
						}
					}
				});
		return result;
	}

	/* (non-Javadoc)
	 * @see com.evian.sqct.dao.IUserDao#userLogin_v2(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(final Integer eid) {
		List<Map<String,Object>> result = (List<Map<String,Object>>)jdbcTemplate.execute(
				"{call Proc_Backstage_appMerchant_account_enterprise_menu_select_forAppLogin(?)}",
				(CallableStatementCallback) cs -> {
					cs.setObject("eid", eid);
					cs.execute();
					ResultSet rs = null;
					try {

						rs = cs.executeQuery();

						return ResultSetToBeanHelper.resultSetToList(rs, Map.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}finally{
						if(rs!=null){
							rs.close();
						}
					}
				});
		return result;
	}

	@Override
	public List<Map<String, Object>> selectEServiceUserlistByEid(Integer eid) {
		String sql=null;
		if(eid!=null){
			sql="select firstName from e_service_userlist where eid=? ";
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, eid);
			return result;

		}else{
			sql="select firstName from e_service_userlist  ";
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
			return result;
		}
	}

	@Override
	public boolean selectOpenShuiqooBusApp(Integer eid) {
		try {
			Map<String,Object> result = jdbcTemplate.queryForMap("select eid from e_enterprise_mutual where eid=? and openShuiqooBusApp=1",eid);
			if(result == null){
				return false;
			}else{
				eid = (Integer)result.get("eid");
				if(eid!=null){
					return true;
				}else{
					return false;
				}
			}
		} catch (DataAccessException e) {
			return false;
		}
	}

	@Override
	public Integer updateNotSubscribeUserHeadimgurl_e_weixin_subscribe(String openId, String headimgurl) {
		String sql = " update e_weixin_subscribe set headimgurl=? where openid=? and isSubscribe=0 and len(headimgurl)=0 ";
		return jdbcTemplate.update(sql,new Object[]{headimgurl,openId});
	}

	@Override
	public Integer updateNotSubscribeUserHeadimgurl_e_client_wx_photo(String openId, String headimgurl) {
		String sql = " update  A set a.photo=?  from e_client_wx_photo A INNER JOIN e_client_wxuser_mapping  B  ON A.clientId=B.clientId where B.openid=? and len(A.photo)=0 ";
		return jdbcTemplate.update(sql,new Object[]{headimgurl,openId});
	}

	@Override
	public Integer updateNotSubscribeUserHeadimgurl_e_groupbuy_order(String openId, String headimgurl) {
		String sql = " update e_groupbuy_order set headImgUrl=? where openId=? and len(headImgUrl)=0 ";
		return jdbcTemplate.update(sql,new Object[]{headimgurl,openId});
	}

	/**
	 * 用户反馈
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public Integer saveAppFeedback(AppFeedbackDTO dto) {
		String sql = "insert into e_appMerchant_feedback(eid,accountId,feedbackTitle,feedbackContent,pictures) values(?,?,?,?,?);";
		return jdbcTemplate.update(sql,new Object[]{dto.getEid(),dto.getAccountId(),dto.getFeedbackTitle(),dto.getFeedbackContent(),dto.getPictures()});
	}

	/**
	 * 查询客户反馈
	 *
	 * @param accountId
	 * @param eid
	 * @return
	 */
	@Override
	public List<AppFeedbackDTO> selectAppFeedback(Integer accountId, Integer eid) {
		String sql = "select feedbackId, eid, accountId, feedbackTitle, feedbackContent, pictures, createTime, writeBackContent, writeBackTime from e_appMerchant_feedback where accountId=? and eid=? order by createTime desc";
		return jdbcTemplate.query(sql,new Object[]{accountId,eid},new BeanPropertyRowMapper<>(AppFeedbackDTO.class));
	}

	@Override
	public List<SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO> selectEAppMerchantAccountAndRegeditIdByShopId(Integer shopId) {
		String sql ="select a.accountId,a.eid,a.shopId,a.shopManager,a.isReceiveOrder,b.regeditId,b.sourceId, b.platformId from e_appMerchant_account_shop a inner join e_appMerchant_jpush_regedit b on a.accountId = b.accountId where a.shopId=? and (a.shopManager=1 or isReceiveOrder=1)";
		return jdbcTemplate.query(sql,new Object[]{shopId},new BeanPropertyRowMapper<>(SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO.class));
	}

	/**
	 * 查询微信模板信息企业员工接收人微信
	 *
	 * @param eid
	 * @return
	 */
	@Override
	public List<EEnterpriseMsgWeixin> selectEEnterpriseMsgWeixinByEid(Integer eid) {
		String sql = "select a.id, a.eid, a.openId, a.forType, a.enabled, a.isValid, a.createTime, a.validCode, b.appId from e_enterprise_msg_weixin a inner join e_client_wxuser_mapping b on a.eid=b.eid and a.openId=b.openid where a.eid=? and a.isValid = 1 and a.[enabled] = 1 and a.forType = 2";
		return jdbcTemplate.query(sql,new Object[]{eid},new BeanPropertyRowMapper<>(EEnterpriseMsgWeixin.class));
	}

	@Override
	public List<Map<String, Object>> tempLogin(String account) {
//		e_appMerchant_account
		String sql = "select a.accountId, a.userPwd, b.regeditId, b.sourceId, b.platformId from e_appMerchant_account a inner join e_appMerchant_jpush_regedit b on a.accountId=b.accountId where a.account=?";
		return jdbcTemplate.queryForList(sql,account);
	}

	/**
	 * 生成pwd和identityCode
	 * @param clientId
	 * @param passWord
	 * @param identityCode
	 * @return
	 */
	@Override
	public int updateEclient(Integer clientId, String passWord, String identityCode) {
		String sql = "update e_client set passWord=?,identityCode=?,account=? where clientId=?";
		int update = jdbcTemplate.update(sql, new Object[]{passWord, identityCode,clientId.toString(), clientId});
		return update;
	}
}
