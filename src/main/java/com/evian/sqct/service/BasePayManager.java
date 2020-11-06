package com.evian.sqct.service;

import com.evian.sqct.bean.enterprise.ZHDistributionCostsCashOutFromEvian;
import com.evian.sqct.bean.enterprise.input.PROCZHCashOutBackstageSAVEupInputDTO;
import com.evian.sqct.bean.enterprise.input.ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO;
import com.evian.sqct.bean.order.*;
import com.evian.sqct.bean.order.request.PROCAPPOrderPayAnotherNotifyReqDTO;
import com.evian.sqct.bean.order.request.PROCAPPOrderPayAnotherReqDTO;
import com.evian.sqct.bean.pay.PayOnDeliveryByWeCathReqDTO;
import com.evian.sqct.bean.pay.SddPayInputDTO;
import com.evian.sqct.bean.pay.WXPayConstants;
import com.evian.sqct.bean.pay.WxPayNotifyModel;
import com.evian.sqct.bean.vendor.PayParam;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IEnterpriseDao;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.dao.IShopDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.*;
import com.evian.sqct.wxPay.APPWxPayBean;
import com.evian.sqct.wxPay.EnterprisePayByLooseChangeBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * ClassName:BasePayManager
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2019/12/18 14:28
 * @Author:XHX
 */
@Service
public class BasePayManager extends BaseManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("orderDao")
    private IOrderDao orderDao;

    @Autowired
    @Qualifier("baseOrderManager")
    private BaseOrderManager baseOrderManager;

    @Autowired
    @Qualifier("shopDao")
    private IShopDao shopDao;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private IEnterpriseDao enterpriseDao;

    public String saveNotifyUrlOrder(HttpServletRequest request){
        HttpServletRequest _request = request;

        logger.info("微信支付回调数据开始！");
        // 示例报文
        // String xml =
        // "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
        String inputLine;
        String notityXml = "";
        String resXml = "";

        try {
            while ((inputLine = _request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            _request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("解析错误：" + e.toString());
            return "no";
        }

        logger.info("接收到的报文：" + notityXml);

        if(StringUtils.isEmpty(notityXml)){
            logger.info("接收到的报文为空");
            return "empty";
        }

        Map<String, String> m;
        try {
            m = wxPayService.xmlToMap(notityXml);
        } catch (Exception e) {
            logger.info("支付回調錯誤");
            return "no";
        }
        //Map m = parseXmlToList2(notityXml);
        WxPayNotifyModel wpr = new WxPayNotifyModel();
        wpr.setAppid(m.get("appid"));
        wpr.setBankType(m.get("bank_type"));
        wpr.setCashFee(String.valueOf(Double.valueOf(m.get("cash_fee"))/100.0));
        wpr.setFeeType(m.get("fee_type"));
        wpr.setIsSubscribe(m.get("is_subscribe"));
        wpr.setMchId(m.get("mch_id"));
        wpr.setNonceStr(m.get("nonce_str"));
        wpr.setOpenid(m.get("openid"));
        wpr.setOutTradeNo(m.get("out_trade_no"));
        wpr.setResultCode(m.get("result_code"));
        wpr.setReturnCode(m.get("return_code"));
        wpr.setSign(m.get("sign"));
        wpr.setTimeEnd(m.get("time_end"));
        wpr.setTotalFee(String.valueOf(Double.valueOf(m.get("total_fee"))/100.0));
        wpr.setTradeType(m.get("trade_type"));
        wpr.setTransactionId(m.get("transaction_id"));
        wpr.setPromotion_detail(m.get("promotion_detail"));
        if(m.containsKey("attach")&&m.get("attach")!=null){
            wpr.setAttach(m.get("attach"));
        }
        if("APP".equals(wpr.getTradeType())){
            wpr.setSubAppid(m.get("sub_appid"));
            wpr.setSubIsSubscribe(m.get("sub_is_subscribe"));
            wpr.setSubMchId(m.get("sub_mch_id"));
            wpr.setSubOpenid(m.get("sub_openid"));
        }

        Map<String, Object> param = orderDao.selectEEnterpriseAppPayParamByAppId(m.get("appid"));
        if (param==null) {
            logger.info("支付回調錯誤");
            return "faild";
        }

        if ("SUCCESS".equals(wpr.getResultCode())) {
            Boolean isSignatureValid = false;
            try {
                if("APP".equals(wpr.getTradeType())||"NATIVE".equals(wpr.getTradeType())) {
                    isSignatureValid = wxPayService.isSignatureValid(m, DES3_CBCUtil.des3DecodeCBC(PayParam.getWeChatAppPayKey()), WXPayConstants.SignType.MD5);
                }else{
                    isSignatureValid = false;
                }
            } catch (Exception e) {
                isSignatureValid = false;
            }
            //验证签名
            if (isSignatureValid == false) {
                logger.info("接收到的报文验证签名失败");
                return "faild";
            }

            // 携带参数
            String attach = wpr.getAttach();

            // 订单id
            String orderId = null;

            if(!StringUtils.isEmpty(attach)){
                //2019-07-08 彭安需要orderId来优化回调速度    DIVISION分隔符  DIVISION前是orderId ， DIVISION后是充值、拼团id
                String[] split = attach.split("DIVISION");
                orderId = split[0];
                if(split.length>1){
                    attach = split[1];
                }else{
                    attach = null;
                }
            }

            // 充值卡回单
            String billId = null;
            // 拼团回单
            String gbo = null;
            // 代客支付
            String payTag = null;
            if(!StringUtils.isEmpty(attach)){
                if(attach.indexOf("billIdEVIAN")>-1){
                    billId = attach.replace("billIdEVIAN","");
                }
                if(attach.indexOf("gboIdEVIAN")>-1){
                    gbo = attach.replace("gboIdEVIAN","");
                }
                if(attach.indexOf("payOnDeliveryEVIAN")>-1){
                    payTag = attach.replace("payOnDeliveryEVIAN","");
                }
            }

            // 支付成功
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            String result = "";
            if(!StringUtils.isEmpty(billId)){
                result = baseOrderManager.rechargeCardPaySucNotify(wpr.getOpenid(), wpr.getOutTradeNo(), wpr.getTransactionId(), wpr.getBankType(), wpr.getTotalFee(),wpr.getCashFee(), DateUtil.getStringDate(),billId,orderId,wpr.getPromotion_detail(),wpr.getAppid());
            }else if(!StringUtils.isEmpty(gbo)) {

                String gboId = gbo.replace("EVIAN", ",").split(",")[0];
                String xaId = gbo.replace("EVIAN", ",").split(",")[1];
                String isCommander = gbo.replace("EVIAN", ",").split(",")[2];

                result = baseOrderManager.groupBuyPaySucNotify(wpr.getOpenid(), wpr.getOutTradeNo(), wpr.getTransactionId(), wpr.getBankType(), wpr.getTotalFee(), wpr.getCashFee(), DateUtil.getStringDate(), gboId, xaId, isCommander, orderId, wpr.getPromotion_detail(), wpr.getAppid());
            }else if(!StringUtils.isEmpty(payTag)){
                PROCAPPOrderPayAnotherNotifyReqDTO dto = new PROCAPPOrderPayAnotherNotifyReqDTO();
                dto.setOrderId(Integer.parseInt(orderId));
                dto.setPaymentNo(wpr.getOutTradeNo());
                dto.setPaymentPlatform("微信");
                dto.setPayTag(payTag);
                String totalFee = wpr.getTotalFee();
                BigDecimal payMoney = new BigDecimal(totalFee);
                BigDecimal divide = new BigDecimal("100");
                payMoney.divide(divide);
                dto.setPayMoney(payMoney.doubleValue());
                String timeEnd = wpr.getTimeEnd();
                Long time = DateUtil.convertTimeToLong(timeEnd, "yyyyMMddHHmmss");
                String paymentTime = DateUtil.convertTimeToString(time,"yyyy-MM-dd HH:mm:ss");
                dto.setPaymentTime(paymentTime);
                baseOrderManager.savePayOnDeliveryNotify(dto);
                return "SUCCESS";
            }else {
                result = baseOrderManager.weixinPayToSuc(wpr.getOpenid(), wpr.getOutTradeNo(), wpr.getTransactionId(), wpr.getBankType(), wpr.getTotalFee(),wpr.getCashFee(), DateUtil.getStringDate(),orderId,wpr.getPromotion_detail(),wpr.getAppid());
            }

            JSONObject jsonResult = JSONObject.fromObject(result);
            if(jsonResult.has("code") && jsonResult.getString("code").equals("E00000")){

            }else{
                return  "faild1";
            }
        } else {;
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return  "faild";
        }

        logger.info("微信支付回调数据结束！");
        return "SUCCESS";
    }

    /**
     * description: 解析微信通知xml
     *
     * @param xml
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    private static Map parseXmlToList2(String xml) {
        Map retMap = new HashMap();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();// 指向根节点
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }


    public Map<String,Object> orderquery(String out_trade_no,String appId){
        Map<String, Object> param = orderDao.selectEEnterpriseAppPayParamByAppId(appId);
        if(param!=null) {
            String sub_appid = (String) param.get("wechatAppId");
            String sub_mch_id = (String) param.get("wechatMchId");
            APPWxPayBean wx = new APPWxPayBean();
            wx.setMch_id(PayParam.getWeChatAppPayMchId());
            wx.setAppid(PayParam.getWeChatAppPayAppId());
            wx.setSub_appid(sub_appid);
            wx.setSub_mch_id(sub_mch_id);
            wx.setOut_trade_no(out_trade_no);
            wx.setAppKey(DES3_CBCUtil.des3DecodeCBC(PayParam.getWeChatAppPayKey()));
            String nonce_str = wxPayService.create_nonce_str();
            wx.setNonce_str(nonce_str);
            String orderquery = wxPayService.orderquery(wx);
            try {
                Map<String,Object> sendredpackMap = XmlStringUtil.stringToXMLParse(orderquery);
                return sendredpackMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 申请提现保存
     * @param eid
     * @param clientId
     * @param shopId
     * @param account 水趣商户帐号
     * @param cashOut 提现金额
     * @param cashOutNo 提现单号
     * @param remark 提现说明
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String,Object> applicationForWithdrawal(Integer eid, Integer clientId, Integer shopId, String account, Double cashOut, String cashOutNo, String remark,String APPUserName){
        return shopDao.PROC_ZH_DistributionCosts_CashOut_SAVE(eid, clientId, shopId, account, cashOut, cashOutNo, remark,APPUserName);
    }
    /**
     * 申请提现保存
     * @param eid
     * @param clientId
     * @param shopId
     * @param account 水趣商户帐号
     * @param cashOut 提现金额
     * @param cashOutNo 提现单号
     * @param remark 提现说明
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public String applicationForWithdrawalRecord(Integer CashOutID,String CashOutNO,Boolean ifPayment,Integer iStatus,String paymentNo,String SH_remark){
        return shopDao.PROC_ZH_DistributionCosts_CashOut_SAVE_Pay(CashOutID, CashOutNO, ifPayment, iStatus, paymentNo, SH_remark);
    }

    /**
     * 退押提现
     * 查找单据是否正确
     * 然后去找用户openid appid 要从单据看客户来源于公众号还是小程序还是小程序拼团
     * 然后发起支付
     * @param dto
     * @return
     */
    public String tuiyaPay(TuiyaPayDTO dto)throws Exception{
        ClientPledgeTuiya clientPledgeTuiya;
        try {
            clientPledgeTuiya = orderDao.selectClientPledgeTuiyaByOrderId(dto.getTuiyaOrderId());
        } catch (DataAccessException e) {
            // 未查询到相关单据信息
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_NOT_ORDER);
        }

        if(clientPledgeTuiya.getTransferStatus()!=0){
            // 订单状态已变更
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_ORDER_ALTERATION);
        }
        Integer buyOrderId = clientPledgeTuiya.getBuyOrderId();
        ProcBackstageClientPledgeTuiyaTransferDTO cptt = new ProcBackstageClientPledgeTuiyaTransferDTO();
        cptt.setEid(dto.getEid());
        cptt.setDxid(clientPledgeTuiya.getDxid());
        cptt.setXid(clientPledgeTuiya.getXid());

        cptt.setTransferOperator(dto.getTransferOperator());
        cptt.setTransferRemark(dto.getTransferRemark());
        // 转回中台人工转账处理
        if(dto.getState()==0){
            cptt.setTransferStatus(-2);
            cptt.setOrderNo(clientPledgeTuiya.getOrderNo());
            String tag = baseOrderManager.tuiyaTransferAccounts(cptt);
            return tag;
        }

        List<OrderHistorySixAccount> oAccounts = orderDao.selectOrderAccountByOrderId(dto.getTuiyaOrderId());
        if(oAccounts.size()==0){
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_SDDV3_NO_DATA);
        }

        OrderHistorySixAccount account = oAccounts.get(0);
        String appId = account.getAppId();
        String openId = account.getOpenId();
        String mchId = account.getMchId();
        String partnerKey = account.getPartnerKey();


        EnterprisePayByLooseChangeBean w = new EnterprisePayByLooseChangeBean();

        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long l=a*10000000L+b;
        String num=String.valueOf(l);
        w.setMch_appid(appId);
        w.setMchid(mchId);
        w.setNonce_str(num);
        w.setPartner_trade_no(clientPledgeTuiya.getOrderNo());
        w.setOpenid(openId);
        w.setCheck_name("NO_CHECK");
        Double total = clientPledgeTuiya.getTotal();
        BigDecimal bigDecimal = new BigDecimal(total);
        BigDecimal big100 = new BigDecimal("100");
        String totalStr = String.valueOf(big100.multiply(bigDecimal).intValue());
        w.setAmount(totalStr);
        w.setDesc("退押金");
        w.setSpbill_create_ip(dto.getIp());
        w.setAppKey(partnerKey);

        String pay = wxPayService.enterprisePayByLooseChange(w);
        logger.info("提现到零钱返回结果{}",pay);
        Map<String, String> prepayMap = wxPayService.xmlToMap(pay);
        Integer transferStatus = 0;
        String payment_no = "";

        String SH_remark = null;
        if(!pay.contains(wxPayService.ERR_CODE)){
            transferStatus =1;
            payment_no = prepayMap.get("payment_no");
            pay = "";
        }else{
            String errCode = prepayMap.get(wxPayService.ERR_CODE);
            if(wxPayService.SUCCESS.equals(errCode)){
                transferStatus =1;
                payment_no = prepayMap.get("payment_no");
                pay = "";
            }else{
                SH_remark = prepayMap.get("err_code_des");
                transferStatus = -1;
            }
        }

        cptt.setTransferStatus(transferStatus);
        cptt.setOrderNo(clientPledgeTuiya.getOrderNo());
        cptt.setPaymentNo(payment_no);
        cptt.setPayResult(pay);
        String tag = baseOrderManager.tuiyaTransferAccounts(cptt);
        if("1".equals(tag)&&!StringUtils.isEmpty(SH_remark)){
            return SH_remark;
        }
        return tag;
    }


    /**
     * 货到付款微信支付
     * @return
     */
    public String payOnDeliveryByWeCath(PayOnDeliveryByWeCathReqDTO dto){
        String appId = dto.getAppId();
        String ip = dto.getIp();
        String orderNo = dto.getOrderNo();
        Double total = dto.getTotal();
        Integer orderId = dto.getOrderId();

        OrderModel orderModel = orderDao.selectOrderByOrderId(orderId);
        BigDecimal receivableTotal = orderModel.getReceivableTotal();
        String orderGroup = orderModel.getOrderGroup();
        double payMoney = receivableTotal.doubleValue();
        if(payMoney ==0){
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PAY_MONEY_ZERO);
        }



        Map<String, Object> param = orderDao.selectEEnterpriseAppPayParamByAppId(appId);
        if(param!=null){
            String sub_appid = (String) param.get("wechatAppId");
            String sub_mch_id = (String) param.get("wechatMchId");

            BigDecimal bigDecimal = new BigDecimal(total.toString());
            BigDecimal big100 = new BigDecimal("100");
            int total_fee = big100.multiply(receivableTotal).intValue();
            String nonce_str = wxPayService.create_nonce_str();
            String timestamp = wxPayService.create_timestamp();
            String payTag = UUID.randomUUID().toString();

            APPWxPayBean wx = new APPWxPayBean();
            wx.setNonce_str(nonce_str);
            wx.setMch_id(PayParam.getWeChatAppPayMchId());
            wx.setAppid(PayParam.getWeChatAppPayAppId());
            wx.setSub_appid(sub_appid);
            wx.setSub_mch_id(sub_mch_id);
            wx.setBody(orderModel.getOrderNo());
            wx.setOrderId(orderId);
            // attach 不需要加 orderIdDIVISION  只需要wx.setOrderId(orderId) 即可
            wx.setAttach("payOnDeliveryEVIAN"+payTag);
            wx.setOut_trade_no(orderGroup);
            wx.setTotal_fee(total_fee+"");
            wx.setSpbill_create_ip(ip);
            String shuiqooMchantUrl = UrlManage.getShuiqooMchantUrl();
            if(StringUtils.isEmpty(shuiqooMchantUrl)) {
                throw new RuntimeException("水趣商户域名没获取到，请检查配置文件，或者缓存程序");
            }
            wx.setNotify_url(shuiqooMchantUrl+"/evian/sqct/pay/notifyUrl");
            wx.setTrade_type("NATIVE");
            wx.setProduct_id(orderGroup);
            wx.setAppKey(DES3_CBCUtil.des3DecodeCBC(PayParam.getWeChatAppPayKey()));
            String pay = wxPayService.pay(wx);
            try {
                Map<String,Object> sendredpackMap = XmlStringUtil.stringToXMLParse(pay);
                String return_code = (String) sendredpackMap.get("return_code");
                String result_code = (String) sendredpackMap.get("result_code");
                String SUCCESS = "SUCCESS";
                if(!(SUCCESS.equals(result_code)&&SUCCESS.equals(return_code))){
                    String err_code_des = (String) sendredpackMap.get("err_code_des");
                    throw new ResultException(err_code_des);
                }
                String code_url = (String) sendredpackMap.get("code_url");
                code_url = URLEncoder.encode(code_url,"UTF-8");
                JSONObject parJSON = CallBackPar.getParJSON();
                JSONObject data = new JSONObject();
                data.put("code_url",shuiqooMchantUrl+"/evian/sqct/pay/wxPayCodeByCodeUrl?code_url="+code_url);
                parJSON.put("data",data);

                PROCAPPOrderPayAnotherReqDTO reqDto = new PROCAPPOrderPayAnotherReqDTO();
                reqDto.setOrderId(orderId);
                reqDto.setPayCode(code_url);
                reqDto.setPaymentPlatform("微信");
                reqDto.setPayMoney(payMoney);
                reqDto.setPayTag(payTag);
                String tag = baseOrderManager.savePayOnDelivery(reqDto);
                if(!"1".equals(tag)){
                    throw new ResultException(tag);
                }

                return parJSON.toString();
            } catch (Exception e) {
                logger.error("{}",e);
                return ERROR_SYSTEM();
            }
        }
        logger.error("[生成订单失败：{} 没有配置子商户]",appId);
        return ERROR_SYSTEM();
    }


    /**
     * 水叮咚提现
     * 查找单据是否正确
     * 然后去找用户openid appid 要从单据看客户来源于公众号还是小程序还是小程序拼团
     * 然后发起支付
     * @param dto
     * @return
     */
    public String SDDPay(SddPayInputDTO dto)throws Exception{
        ZHDistributionCostsCashOutFromEvian payMessage;
        try {
            payMessage = enterpriseDao.selectZHDistributionCostsCashOutFromEvianByCashOutID(dto.getCashOutID());

        } catch (DataAccessException e) {
            // 未查询到相关单据信息
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_NOT_ORDER);
        }

        if(payMessage.getIfPayment()){
            // 单据已支付,不能重复支付
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_QR_PAY_REPEAT);
        }

        PROCZHCashOutBackstageSAVEupInputDTO rcos = new PROCZHCashOutBackstageSAVEupInputDTO();
        rcos.setCashOutID(dto.getCashOutID());
        rcos.setClientid(payMessage.getClientId());
        rcos.setIfPayment(false);
        rcos.setSH_UserAccount(dto.getCreaterUser());
        rcos.setSH_remark(dto.getRemark());


        ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO esww = new ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO();
        esww.setWangLaiId(dto.getCashOutID());
        esww.setAppId("");
        esww.setOpenId("");
        esww.setPaymentNo("");
        esww.setPayMoney(payMessage.getnCashOut());
        esww.setResult("");
        esww.setCreateUser(dto.getCreaterUser());
        esww.setLogType(2);

        // 转回中台人工转账处理
        Integer state = dto.getState();
        if(state ==-2|| state ==1){
            rcos.setiStatus(state);
            rcos.setIpaymentPlatform(1);
            rcos.setPaymentNo("");
            switch (state){
                case -2:
                    esww.setRemark("水趣商户：人工拒绝");
                    break;
                default:
                    esww.setRemark("水趣商户：线下付钱");
            }
            String tag = enterpriseDao.PROC_ZH_CashOut_Backstage_SAVEup(rcos);
            enterpriseDao.Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat(esww);
            return tag;
        }

        List<OrderHistorySixAccount> oAccounts = orderDao.selectOrderAccountByClientId(payMessage.getClientId(),payMessage.getEid());
        if(oAccounts.size()==0){
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_SDDV3_NO_DATA);
        }

        OrderHistorySixAccount account = oAccounts.get(0);
        String appId = account.getAppId();
        String openId = account.getOpenId();
        String mchId = account.getMchId();
        String partnerKey = account.getPartnerKey();


        EnterprisePayByLooseChangeBean w = new EnterprisePayByLooseChangeBean();

        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long l=a*10000000L+b;
        String num=String.valueOf(l);
        w.setMch_appid(appId);
        w.setMchid(mchId);
        w.setNonce_str(num);
        w.setPartner_trade_no(payMessage.getCashOutID()+"");
        w.setOpenid(openId);
        w.setCheck_name("NO_CHECK");
        Double total = payMessage.getnCashOut();
        BigDecimal bigDecimal = new BigDecimal(total);
        BigDecimal big100 = new BigDecimal("100");
        String totalStr = String.valueOf(big100.multiply(bigDecimal).intValue());
        w.setAmount(totalStr);
        w.setDesc("水叮咚提现");
        w.setSpbill_create_ip(dto.getIp());
        w.setAppKey(partnerKey);

        String pay = wxPayService.enterprisePayByLooseChange(w);
        logger.info("提现到零钱返回结果{}",pay);
        Map<String, String> prepayMap = wxPayService.xmlToMap(pay);
        Integer transferStatus = 0;
        String payment_no = "";

        String SH_remark = null;
        // <xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[支付失败]]></return_msg><mch_appid><![CDATA[wxe96781856240e981]]></mch_appid><mchid><![CDATA[1387919602]]></mchid><result_code><![CDATA[FAIL]]></result_code><err_code><![CDATA[SEND_FAILED]]></err_code><err_code_des><![CDATA[发送失败，请查单确认付款状态]]></err_code_des></xml>
        if(!pay.contains(wxPayService.ERR_CODE)){
            transferStatus =1;
            payment_no = prepayMap.get("payment_no");
            SH_remark = "SUCCESS";
            pay = "";
        }else{
            String errCode = prepayMap.get(wxPayService.ERR_CODE);
            if(wxPayService.SUCCESS.equals(errCode)){
                transferStatus =1;
                payment_no = prepayMap.get("payment_no");
                SH_remark = "SUCCESS";
                pay = "";
            }else{
                SH_remark = prepayMap.get("err_code_des");
                transferStatus = -1;
            }
        }
        rcos.setiStatus(1);
        rcos.setIpaymentPlatform(0);
        rcos.setiStatus(transferStatus);
        rcos.setPaymentNo(payment_no);
        esww.setAppId(appId);
        esww.setOpenId(openId);
        esww.setPaymentNo(payment_no);
        esww.setResult(SH_remark);
        esww.setRemark(dto.getRemark());

        String tag = enterpriseDao.PROC_ZH_CashOut_Backstage_SAVEup(rcos);
        enterpriseDao.Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat(esww);
        if("1".equals(tag)&&!StringUtils.isEmpty(SH_remark)&&!wxPayService.SUCCESS.equals(SH_remark)){
            return SH_remark;
        }
        return tag;
    }
}
