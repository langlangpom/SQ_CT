package com.evian.sqct.service;

import com.evian.sqct.bean.user.EclientDTO;
import com.evian.sqct.dao.IEntityCardDao;
import com.evian.sqct.exception.ResultException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BaseEntityCardManager
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2019/11/14 16:26
 * @Author:XHX
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class BaseEntityCardManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("entityCardDao")
    private IEntityCardDao entityCardDao;


    public String Proc_Backstage_entity_card_storeIn_code_activate(Integer eid,Integer accountId,String barCode){
        return entityCardDao.Proc_Backstage_entity_card_storeIn_code_activate(eid, accountId, barCode);
    }

    public Map<String,Object> Proc_Backstage_entity_card_storeIn_code_select(String beginTime,String endTime,Integer eid,Integer codeStatus,String cardCode,String barCode,String qrcode,Boolean isActivate,Integer accountId,String pcode,String account,String buyAccount,String useAccount,String sellBeginTime, String sellEndTime,String batchName,String storeType,String storeName,Boolean isExchange,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
        Map<String, Object> result = entityCardDao.Proc_Backstage_entity_card_storeIn_code_select(beginTime, endTime, eid, codeStatus, cardCode, barCode, qrcode,isActivate, accountId, pcode, account,buyAccount, useAccount,sellBeginTime,sellEndTime, batchName,storeType, storeName,isExchange,PageIndex, PageSize, IsSelectAll);
        List<Map<String,Object>> cards = (List<Map<String, Object>>) result.get("cards");
        for(Map<String,Object> car : cards){
            Integer ticketId = (Integer) car.get("ticketId");
            Map<String, Object> stringObjectMap = entityCardDao.selectTicketValidTime(ticketId);
            car.put("validity_BegDate",stringObjectMap.get("validity_BegDate"));
            car.put("validity_EndDate",stringObjectMap.get("validity_EndDate"));
        }

        return result;
    }

    public Map<String,Object> Proc_Backstage_entity_card_storeIn_code_AccountStatistics(Integer eid,Integer accountId){
        return  entityCardDao.Proc_Backstage_entity_card_storeIn_code_AccountStatistics(eid, accountId);
    }

    /**
     * 积分会员级别查询
     * @param vipId
     * @param eid
     * @param eName
     * @param PageIndex
     * @param PageSize
     * @param IsSelectAll
     * @return
     */
    public Map<String,Object> Proc_Backstage_integral_vip_select(Integer vipId,Integer eid,String eName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
        return entityCardDao.Proc_Backstage_integral_vip_select(vipId, eid, eName, PageIndex, PageSize, IsSelectAll);
    }

    /**
     * 生成实体卡销售订单
     * @param order
     * @return
     * @throws Exception
     */
    public Map<String,Object> saveCarSellOrder(JSONObject order) throws ResultException{
        Map<String, Object> orderNoMap = entityCardDao.Proc_DisPark_Get_OrderNo(8, 1);
        if(orderNoMap==null||orderNoMap.get("BH")==null){
            throw new ResultException("生成单号错误单号");
        }

        String orderNo = (String) orderNoMap.get("BH");
        Map<String, Object> map = entityCardDao.Proc_Backstage_entity_card_order_add(order.getInt("eid"), orderNo, order.getString("buyAccount")
                , order.getString("userName"), order.getString("address"), order.getString("vipName"), order.getDouble("totalMoney")
                , order.getDouble("realTotalMoney"), order.getString("creator"));
        if(map.get("orderId")==null){
            throw new ResultException("生成订单id错误");
        }
        Integer orderId = (Integer) map.get("orderId");
        JSONArray carDetail = order.getJSONArray("carDetail");
        for(Object temp :carDetail){
            JSONObject detail = (JSONObject) temp;
            int codeId = detail.getInt("codeId");
            String tag = entityCardDao.Proc_Backstage_entity_card_order_detail_add(orderId, codeId, detail.getInt("ticketId")
                    , detail.getDouble("price"),  order.getInt("accountId"), order.getInt("eid")
                    , order.getString("buyAccount")
                    , order.getString("userName"), order.getString("creator"));
            if(!"1".equals(tag)){
                throw new ResultException(tag);
            }

            /*int i = entityCardDao.add_e_entity_card_order_detail(orderId, codeId, detail.getInt("ticketId")
                    , detail.getDouble("price"), detail.getString("beginDate"), detail.getString("endDate"));
            if(i==1){
                // 将卡的状态变为已销售（未兑换）
                entityCardDao.updateE_entity_card_storeIn_codeCodeStatus(codeId, 4);
            }*/
        }
        return map;
    }

    public List<EclientDTO> selectEntityCardOrderBuyAccountByCardCode(String cardCode){
        return entityCardDao.selectEntityCardOrderBuyAccountByCardCode(cardCode);
    }

}
