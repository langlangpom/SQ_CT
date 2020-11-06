package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.user.EclientDTO;
import com.evian.sqct.dao.IEntityCardDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:EntityCardImpl
 * Package:com.evian.sqct.impl
 * Description:实体卡dao
 *
 * @Date:2019/11/14 16:21
 * @Author:XHX
 */
@Repository("entityCardDao")
public class EntityCardImpl implements IEntityCardDao {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public String Proc_Backstage_entity_card_storeIn_code_activate(final Integer eid, final Integer accountId, final String barCode) {
        String result = (String)jdbcTemplate.execute(
                "{call Proc_Backstage_entity_card_storeIn_code_activate(?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("eid", eid);
                    cs.setObject("accountId", accountId);
                    cs.setObject("barCode", barCode);
                    cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
                    cs.execute();
                    return cs.getString("tag");
                });
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_Backstage_entity_card_storeIn_code_select(final String beginTime, final String endTime
            , final Integer eid, final Integer codeStatus, final String cardCode,final String barCode,final String qrcode
            , final Boolean isActivate, final Integer accountId, final String pcode, final String account, final String buyAccount, final String useAccount
            , final String sellBeginTime, final String sellEndTime, final String batchName,final String storeType,final String storeName,final Boolean isExchange, final Integer PageIndex
            , final Integer PageSize, final Boolean IsSelectAll) {
        Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_entity_card_storeIn_code_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("beginTime", beginTime);
                    cs.setObject("endTime", endTime);
                    cs.setObject("eid", eid);
                    cs.setObject("codeStatus", codeStatus);
                    cs.setObject("cardCode", cardCode);
                    cs.setObject("barCode", barCode);
                    cs.setObject("qrcode", qrcode);
                    cs.setObject("isActivate", isActivate);
                    cs.setObject("accountId", accountId);
                    cs.setObject("pcode", pcode);
                    cs.setObject("account", account);
                    cs.setObject("buyAccount", buyAccount);
                    cs.setObject("useAccount", useAccount);
                    cs.setObject("sellBeginTime", sellBeginTime);
                    cs.setObject("sellEndTime", sellEndTime);
                    cs.setObject("batchName", batchName);
                    cs.setObject("storeType", storeType);
                    cs.setObject("storeName", storeName);
                    cs.setObject("isExchange", isExchange);
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
                        List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                        map.put("cards", resul);
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
    public Map<String, Object> Proc_Backstage_entity_card_storeIn_code_AccountStatistics(final Integer eid, final Integer accountId) {
        Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_entity_card_storeIn_code_AccountStatistics(?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("eid", eid);
                    cs.setObject("accountId", accountId);
                    cs.execute();
                    ResultSet rs = cs.executeQuery();
                    Map<String, Object> map = new HashMap<String, Object>();
                    try {
                        List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                        if(resul!=null&&resul.size()>0){
                            map = resul.get(0);
                        }
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


    @Override
    public Map<String, Object> selectTicketValidTime(Integer ticketId) {
        String sql = "select top 1 validity_BegDate, validity_EndDate from e_ticket_combo where ticketId=? order by validity_EndDate desc";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, ticketId);
            return result;
        } catch (DataAccessException e) {

        }
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_DisPark_Get_OrderNo(final Integer DanJuLeiXingID, final Integer City_ID) {
        Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
                "{call Proc_DisPark_Get_OrderNo(?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("DanJuLeiXingID", DanJuLeiXingID);
                    cs.setObject("City_ID", City_ID);
                    cs.execute();
                    ResultSet rs = cs.executeQuery();
                    Map<String, Object> map = new HashMap<String, Object>();
                    try {
                        List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                        if(resul!=null&&resul.size()>0){
                            map = resul.get(0);
                        }
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
    public Map<String, Object> Proc_Backstage_entity_card_order_add(final Integer eid, final String orderNo, final String buyAccount
            , final String userName, final String address, final String vipName, final Double totalMoney, final Double realTotalMoney
            , final String creator) {
        Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_entity_card_order_add(?,?,?,?,?,?,?,?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("eid", eid);
                    cs.setObject("orderNo", orderNo);
                    cs.setObject("buyAccount", buyAccount);
                    cs.setObject("userName", userName);
                    cs.setObject("address", address);
                    cs.setObject("vipName", vipName);
                    cs.setObject("totalMoney", totalMoney);
                    cs.setObject("realTotalMoney", realTotalMoney);
                    cs.setObject("creator", creator);
                    cs.registerOutParameter("orderId", Types.INTEGER);// 注册输出参数的类型
                    cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
                    cs.execute();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("orderId",cs.getInt("orderId"));
                    map.put("TAG",cs.getString("TAG"));
                    return map;
                });
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_Backstage_integral_vip_select(final Integer vipId, final Integer eid, final String eName
            , final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
        Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_integral_vip_select(?,?,?,?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("vipId", vipId);
                    cs.setObject("eid", eid);
                    cs.setObject("eName", eName);
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
                        List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                        map.put("vips", resul);
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

    @Override
    public int add_e_entity_card_order_detail(Integer orderId, Integer codeId, Integer ticketId, Double price, String beginDate, String endDate) {
        String sql = "insert into e_entity_card_order_detail( orderId, codeId, ticketId, price, beginDate, endDate) values(?,?,?,?,?,?) ";

        return jdbcTemplate.update(sql,new Object[] {orderId,codeId,ticketId,price,beginDate,endDate});
    }

    @Override
    public int updateE_entity_card_storeIn_codeCodeStatus(Integer codeId, Integer codeStatus) {
        String sql = "update e_entity_card_storeIn_code set codeStatus=? where codeId=? ";

        return jdbcTemplate.update(sql,new Object[] {codeStatus,codeId});
    }

    @Override
    public String Proc_Backstage_entity_card_order_detail_add(final Integer orderId, final Integer codeId
            , final Integer ticketId, final Double price,  final Integer accountId
            , final Integer eid,final String buyAccount,final String userName,final String creator) {
        String result = (String)jdbcTemplate.execute(
                "{call Proc_Backstage_entity_card_order_detail_add(?,?,?,?,?,?,?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("eid", eid);
                    cs.setObject("orderId", orderId);
                    cs.setObject("accountId", accountId);
                    cs.setObject("codeId", codeId);
                    cs.setObject("ticketId", ticketId);
                    cs.setObject("price", price);
                    cs.setObject("buyAccount", buyAccount);
                    cs.setObject("userName", userName);
                    cs.setObject("creator", creator);
                    cs.registerOutParameter("TAG", Types.NVARCHAR);// 注册输出参数的类型
                    cs.execute();
                    return cs.getString("TAG");
                });
        return result;
    }

    @Override
    public List<EclientDTO> selectEntityCardOrderBuyAccountByCardCode(String cardCode) {
        String sql = "select c.clientId,c.identityCode,d.nickname,d.photo,b.buyAccount as account from e_entity_card_order_detail a  left join e_entity_card_order b on a.orderId=b.orderId left join e_client c on b.buyAccount=c.account left join e_client_wx_photo d on c.clientId=d.clientId  where cardCode=?  ";
        List<EclientDTO> query = jdbcTemplate.query(sql, new Object[]{cardCode}, new BeanPropertyRowMapper<>(EclientDTO.class));
        return query;
    }
}
