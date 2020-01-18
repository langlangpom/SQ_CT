package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.user.EclientDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.service.BaseEntityCardManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:EntityCardAction
 * Package:com.evian.sqct.api.action
 * Description:实体卡Action
 *
 * @Date:2019/11/14 16:28
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/entityCard")
public class EntityCardAction extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BaseEntityCardManager baseEntityCardManager;

    /**
     * 157.查询实体卡码
     * @param eid
     * @param accountId
     * @param barCode
     * @return
     */
    @RequestMapping("findEntityCardCode.action")
    public Map<String,Object> findEntityCardCode(String beginTime,String endTime,Integer eid,Integer codeStatus,String cardCode,String barCode,String qrcode,Boolean isActivate,Integer accountId,String pcode,String account,String buyAccount,String useAccount,String sellBeginTime, String sellEndTime,String batchName,String storeType,String storeName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
        Map<String, Object> parMap = CallBackPar.getParMap();

        Map<String, Object> result = baseEntityCardManager.Proc_Backstage_entity_card_storeIn_code_select(beginTime, endTime, eid, codeStatus, cardCode, barCode, qrcode,isActivate, accountId, pcode, account,buyAccount, useAccount,sellBeginTime,sellEndTime,batchName,storeType, storeName, null,PageIndex, PageSize, IsSelectAll);
        setData(parMap,result);
        return parMap;
    }

    /**
     * 158.实体卡码数量统计
     * @param eid
     * @param accountId
     * @param barCode
     * @return
     */
    @RequestMapping("entityCardNumStatistics.action")
    public Map<String,Object> entityCardNumStatistics(Integer eid,Integer accountId){
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(eid==null||accountId==null) {
            setERROR_PARAM(parMap);
            return parMap;
        }
        Map<String, Object> result = baseEntityCardManager.Proc_Backstage_entity_card_storeIn_code_AccountStatistics(eid, accountId);
        setData(parMap,result);
        return parMap;
    }

    /**
     * 159.实体卡激活
     * @param eid
     * @param accountId
     * @param barCode
     * @return
     */
    @RequestMapping("entityCardActivation.action")
    public Map<String,Object> entityCardActivation(Integer eid,Integer accountId,String barCode){
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(eid==null||accountId==null||barCode==null) {
            setERROR_PARAM(parMap);
            return parMap;
        }
        String tag = baseEntityCardManager.Proc_Backstage_entity_card_storeIn_code_activate(eid, accountId, barCode);
        if(!"1".equals(tag)){
            setCode(parMap, 150);
            setMessage(parMap, tag);
        }
        return parMap;
    }

    /**
     * 160.实体卡码数量统计
     * @param eid
     * @param accountId
     * @param barCode
     * @return
     */
    @RequestMapping("findIntegralVip.action")
    public Map<String,Object> findIntegralVip(Integer vipId,Integer eid,String eName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
        Map<String, Object> parMap = CallBackPar.getParMap();
        Map<String, Object> result = baseEntityCardManager.Proc_Backstage_integral_vip_select(vipId, eid, eName, PageIndex, PageSize, IsSelectAll);
        setData(parMap,result);
        return parMap;
    }

    /**
     * 生成卡销售订单
     * @param carSellOrder
     * @return
     */
    @RequestMapping("saveCarSellOrder.action")
    public Map<String,Object> saveCarSellOrder(String carSellOrder) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(carSellOrder==null){
            setERROR_PARAM(parMap);
            return parMap;
        }
        JSONObject order = JSONObject.fromObject(carSellOrder);
        try {
            baseEntityCardManager.saveCarSellOrder(order);
        } catch (ResultException e) {
            logger.error("[e:{}]",e);
            setERROR_150(parMap,e.getMessage());
        }

        return parMap;
    }
    /**
     * 164.根据卡号查询购买用户信息
     * @param carSellOrder
     * @return
     */
    @RequestMapping("findEntityCardOrderBuyAccountByCardCode.action")
    public Map<String,Object> findEntityCardOrderBuyAccountByCardCode(String cardCode) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(cardCode==null){
            setERROR_PARAM(parMap);
            return parMap;
        }
        List<EclientDTO> eclientDTOS = baseEntityCardManager.selectEntityCardOrderBuyAccountByCardCode(cardCode);
        if(eclientDTOS.size()==0){
            // 未查询到相关数据
            setERROR_BY_CODE(parMap, Constants.CODE_SDDV3_NO_DATA);
            return parMap;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("buyAccounts",eclientDTOS);
        setData(parMap,map);

        return parMap;
    }

}

