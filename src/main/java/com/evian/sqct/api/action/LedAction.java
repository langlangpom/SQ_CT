package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseLedManager;
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
 * Description:led广告action
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
    public Object findLedOrder(Integer orderId) {
        if(orderId==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        Map<String, Object> result = baseLedManager.selectLedOrderByOrderId(orderId);
        return result;
    }

    /**
     * 166.审核Led广告订单
     * @param orderId
     * @return
     */
    @RequestMapping("auditLEDOrder.action")
    public ResultVO auditLEDOrder(Integer orderId,Integer auditStatus,String auditRemark,String auditor) {
        if(orderId==null||auditStatus==null||auditor==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        int result = baseLedManager.updateLedOrderStatus(orderId, auditStatus, auditRemark, auditor);

        return new ResultVO();
    }

    /**
     * 审核Led广告订单发送极光推送
     * @param orderId
     * @return
     */
    @RequestMapping("auditLEDOrderJPush.action")
    public ResultVO auditLEDOrderJPush(Integer orderId) {
        if(orderId==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        baseLedManager.ledAOrderuditJPush(orderId);

        return new ResultVO();
    }



    /**
     * 167.查询企业Led广告订单
     * @param eid
     * @return
     */
    @RequestMapping("findLedOrderList.action")
    public Object findLedOrderList(Integer eid) {
        if(eid==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        List<Map<String, Object>> result = baseLedManager.selectLedOrderByEid(eid);
        Map<String, Object> map = new HashMap<>();
        map.put("orders",result);

        return map;
    }

    /**
     * 181.查询Led广告订单
     * @param orderId
     * @return
     */
    @RequestMapping("findLedOrder_new.action")
    public Map<String, Object> findLedOrder_new(String beginTime,String endTime,Integer orderId,String orderNo,Integer eid,String mainboardNo,Integer auditStatus,Integer dataSourse,String shopName,Boolean isTest,String nickName,String openId,Integer PageIndex,Integer  PageSize,Boolean IsSelectAll) {
        Map<String, Object> result = baseLedManager.Proc_Backstage_vendor_ad_led_order_new_select(beginTime, endTime,orderId, orderNo, eid, mainboardNo, auditStatus, dataSourse, shopName, isTest, nickName, openId, PageIndex, PageSize, IsSelectAll);
        return result;
    }
    /**
     * 7.售货机广告下发
     * @param orderId
     * @return
     */
    @RequestMapping("downAdvertingToVendor.action")
    public String downAdvertingToVendor(Integer mainboardId,String mainboardNo,String content,String dir,Integer speed,Integer time,Integer id) {
        String result = baseLedManager.downAdvertingToVendor(mainboardId, mainboardNo, content, dir, speed, time, id);
        return result;
    }

    /**
     * 8.售货机广告功能的关闭和开启
     * @param orderId
     * @return
     */
    @RequestMapping("vendorOpenCloseAD.action")
    public String vendorOpenCloseAD(Integer mainboardId,String mainboardNo,Integer status) {
        String result = baseLedManager.vendorOpenCloseAD(mainboardId, mainboardNo, status);
        return result;
    }

    /**
     * 9.对售货机某条广告进行变更
     * @param orderId
     * @return
     */
    @RequestMapping("updateAdvertingStatus.action")
    public String updateAdvertingStatus(Integer mainboardId,String mainboardNo,Integer status,Integer id) {
        String result = baseLedManager.updateAdvertingStatus(mainboardId, mainboardNo, status, id);
        return result;
    }
}
