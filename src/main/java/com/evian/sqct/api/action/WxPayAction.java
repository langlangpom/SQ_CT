package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseEnterpriseManager;
import com.evian.sqct.service.WxPayService;
import com.evian.sqct.wxPay.EnterprisePayByLooseChangeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

/**
 * ClassName:WxPayAction
 * Package:com.evian.sqct.api.action
 * Description:微信支付相关action
 *
 * @Date:2020/5/7 17:04
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/wxPay")
public class WxPayAction extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseEnterpriseManager baseEnterpriseManager;

    @Autowired
    private WxPayService wxPayService;

    @RequestMapping("withdrawDeposit")
    public ResultVO withdrawDeposit(String appId,String openId,String ip){
        if(openId==null||appId==null) {
            return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
        }

        EEnterpriseWechatliteapp wechatliteapp = baseEnterpriseManager.selectEnterpriseEidByAppId(appId);
        if(wechatliteapp==null){
            // 企业支付信息错误
            return new ResultVO<>(ResultCode.CODE_ERROR_ENTERPRISE_PAY);
        }

        EnterprisePayByLooseChangeBean w = new EnterprisePayByLooseChangeBean();
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");

        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long l=a*10000000L+b;
//		String t = "845431986481718";
        String num=String.valueOf(l);
        w.setMch_appid(appId);
        w.setMchid(wechatliteapp.getMchId());
        w.setNonce_str(uuidStr);
        w.setPartner_trade_no(num);
        w.setOpenid(openId);
        w.setCheck_name("NO_CHECK");
        w.setAmount("100");
        w.setDesc("发钱");
        w.setSpbill_create_ip(ip);
        w.setAppKey(wechatliteapp.getPartnerKey());

        try {
            String pay = wxPayService.enterprisePayByLooseChange(w);
            logger.info("提现到零钱返回结果{}",pay);
        } catch (Exception e) {
            logger.error("{}",e);
        }

        return new ResultVO();
    }

}
