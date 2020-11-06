package com.evian.sqct.service;

import com.evian.sqct.bean.enterprise.EWeixinSendMessageTemplateEnterprise;
import com.evian.sqct.bean.user.EEnterpriseMsgWeixin;
import com.evian.sqct.bean.vendor.VendorOrder;
import com.evian.sqct.dao.IEnterpriseDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.util.DateUtil;
import com.evian.sqct.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:BaseMessageManager
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2020/7/16 11:05
 * @Author:XHX
 */
@Service
public class BaseMessageManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IEnterpriseDao enterpriseDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IVendorDao vendorDao;

    @Autowired
    private IWeixinApiService weixinApiService;

    /**
     * 推送售货机出货通知
     * @param orderId
     * @return
     */
    public String pushVendorShipmentMessage(String groupSign){
        VendorOrder vendorOrder = vendorDao.selectVendorOrderByGroupSign(groupSign);
        EWeixinSendMessageTemplateEnterprise template = enterpriseDao.selectEWeixinSendMessageTemplateEnterpriseByTid(vendorOrder.getEid(), 10);
        String templateId = template.getTemplateId();
        List<EEnterpriseMsgWeixin> msgWeixins = userDao.selectEEnterpriseMsgWeixinByEid(vendorOrder.getEid());
        String result = null;
        // 发送微信配送员信息：{"appId":"wxb7cdca4bcbd85874","data":"{\"first\": {\"value\":\"您的订单已提交\"},\"keyword1\":{\"value\":\"QY200716009254\"},\"keyword2\":{\"value\":\"0元\"},\"keyword3\":{\"value\":\"手动水抽子*1\"}, \"remark\":{\"value\":\"点击查看订单详情\",\"color\":\"#1E90FF\"}}","openId":"ohIo91b_p6E2L9pXHQr43oUTuyqM","orderId":3856306,"sourceId":1,"templateId":"HfR-tx-QqNA5oTVrPCLAYUm8j23jbclDfMDo-neGCHQ"}

        Map<String,Object> first = new HashMap<>(2);
        first.put("value","水趣矿泉驿站"+vendorOrder.getMainboardNo()+"第"+vendorOrder.getDoorIndex()+"号门出货");
        first.put("color","#666666");
        Map<String,Object> keyword1 = new HashMap<>(2);
        keyword1.put("value",vendorOrder.getProductName()+"【"+vendorOrder.getProductCode()+"】");
        keyword1.put("color","#5D97EF");
        Map<String,Object> keyword2 = new HashMap<>(2);
        keyword2.put("value", DateUtil.convertTimeToString(vendorOrder.getCreateTime().getTime(),"yyyy-MM-dd HH:mm:ss"));
        keyword2.put("color","#5D97EF");
        Map<String,Object> remark = new HashMap<>(2);
        remark.put("value", "请安排补货");
        remark.put("color","#5D97EF");
        Map<String,Object> data = new HashMap<>(4);
        data.put("first",first);
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        data.put("remark",remark);
        Map<String,Object> send = new HashMap<>(3);
        send.put("template_id",templateId);
        send.put("data",data);
        for (EEnterpriseMsgWeixin wxUser : msgWeixins){
            send.put("touser",wxUser.getOpenId());
            String pamaJson = JacksonUtils.obj2json(send);
            result = weixinApiService.sendTemplateMessage(wxUser.getAppId(),pamaJson);
        }

        return result;
    }

}
