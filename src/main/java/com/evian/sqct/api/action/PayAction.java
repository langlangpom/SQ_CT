package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.service.BasePayManager;
import com.evian.sqct.util.CallBackPar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:PayAction
 * Package:com.evian.sqct.api.action
 * Description:支付action
 *
 * @Date:2019/12/18 14:27
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/pay")
public class PayAction extends BaseAction{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BasePayManager basePayManager;

    @RequestMapping("notifyUrl")
    public String showOrderData(HttpServletRequest request){
        try {
            return basePayManager.saveNotifyUrlOrder(request);
        } catch (Exception e) {
            logger.error("{}",e);
            return "SUCCESS";
        }
    }

    /**
     * 168.查询微信订单
     * @param group
     * @param appId
     * @return
     */
    @RequestMapping("orderquery.action")
    public Map<String, Object> orderquery(String group,String appId){
        Map<String, Object>  parMap = CallBackPar.getParMap();
        if(group==null||appId==null) {
            setERROR_PARAM(parMap);
            return parMap;
        }
        Map<String, Object> orderquery = basePayManager.orderquery(group, appId);
        Map<String, Object> data = new HashMap<>();
        data.put("orderquery",orderquery);
        setData(parMap,data);
        return  parMap;
    }
}
