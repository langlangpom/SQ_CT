package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.service.BaseLedManager;
import com.evian.sqct.util.CallBackPar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:LedAction
 * Package:com.evian.sqct.api.action
 * Description:请为该功能做描述
 *
 * @Date:2019/12/11 10:00
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/led")
public class LedAction extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BaseLedManager baseLedManager;

    /**
     * 165.查询Led广告订单
     * @param orderId
     * @return
     */
    @RequestMapping("findLedOrder.action")
    public Map<String, Object> findLedOrder(Integer orderId) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(orderId==null) {
            setERROR_PARAM(parMap);
        }
        Map<String, Object> result = baseLedManager.selectLedOrderByOrderId(orderId);
        setData(parMap, result);

        return parMap;
    }

    /**
     * 166.审核Led广告订单
     * @param orderId
     * @return
     */
    @RequestMapping("auditLEDOrder.action")
    public Map<String, Object> auditLEDOrder(Integer orderId,Integer auditStatus,String auditRemark,String auditor) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(orderId==null||auditStatus==null||auditor==null) {
            setERROR_PARAM(parMap);
        }
        int result = baseLedManager.updateLedOrderStatus(orderId, auditStatus, auditRemark, auditor);

        return parMap;
    }

    /**
     * 审核Led广告订单发送极光推送
     * @param orderId
     * @return
     */
    @RequestMapping("auditLEDOrderJPush.action")
    public Map<String, Object> auditLEDOrderJPush(Integer orderId) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(orderId==null) {
            setERROR_PARAM(parMap);
        }
        baseLedManager.ledAOrderuditJPush(orderId);

        return parMap;
    }



    /**
     * 167.查询企业Led广告订单
     * @param eid
     * @return
     */
    @RequestMapping("findLedOrderList.action")
    public Map<String, Object> findLedOrderList(Integer eid) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(eid==null) {
            setERROR_PARAM(parMap);
        }
        List<Map<String, Object>> result = baseLedManager.selectLedOrderByEid(eid);
        Map<String, Object> map = new HashMap<>();
        map.put("orders",result);
        setData(parMap, map);

        return parMap;
    }
}
