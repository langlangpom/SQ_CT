package com.evian.sqct.service;

import com.evian.sqct.bean.pay.WXPayConstants;
import com.evian.sqct.bean.pay.WxPayNotifyModel;
import com.evian.sqct.bean.vendor.PayParam;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.util.DES3_CBCUtil;
import com.evian.sqct.util.DateUtil;
import com.evian.sqct.util.XmlStringUtil;
import com.evian.sqct.wxPay.APPWxPayBean;
import com.evian.sqct.wxPay.APPWxPayUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            m = APPWxPayUtils.xmlToMap(notityXml);
        } catch (Exception e) {
            logger.info("支付回調錯誤");
            return "no";
        }
        //Map m = parseXmlToList2(notityXml);
        WxPayNotifyModel wpr = new WxPayNotifyModel();
        wpr.setAppid(m.get("appid").toString());
        wpr.setBankType(m.get("bank_type").toString());
        wpr.setCashFee(String.valueOf(Double.valueOf(m.get("cash_fee").toString())/100.0));
        wpr.setFeeType(m.get("fee_type").toString());
        wpr.setIsSubscribe(m.get("is_subscribe").toString());
        wpr.setMchId(m.get("mch_id").toString());
        wpr.setNonceStr(m.get("nonce_str").toString());
        wpr.setOpenid(m.get("openid").toString());
        wpr.setOutTradeNo(m.get("out_trade_no").toString());
        wpr.setResultCode(m.get("result_code").toString());
        wpr.setReturnCode(m.get("return_code").toString());
        wpr.setSign(m.get("sign").toString());
        wpr.setTimeEnd(m.get("time_end").toString());
        wpr.setTotalFee(String.valueOf(Double.valueOf(m.get("total_fee").toString())/100.0));
        wpr.setTradeType(m.get("trade_type").toString());
        wpr.setTransactionId(m.get("transaction_id").toString());
        wpr.setPromotion_detail(m.get("promotion_detail"));
        if(m.containsKey("attach")&&m.get("attach")!=null){
            wpr.setAttach(m.get("attach").toString());
        }
        if("APP".equals(wpr.getTradeType())){
            wpr.setSubAppid(m.get("sub_appid").toString());
            wpr.setSubIsSubscribe(m.get("sub_is_subscribe").toString());
            wpr.setSubMchId(m.get("sub_mch_id").toString());
            wpr.setSubOpenid(m.get("sub_openid").toString());
        }

        Map<String, Object> param = orderDao.selectEEnterpriseAppPayParamByAppId(m.get("appid"));
        if (param==null) {
            logger.info("支付回調錯誤");
            return "faild";
        }

        if ("SUCCESS".equals(wpr.getResultCode())) {
            Boolean isSignatureValid = false;
            try {
                if("APP".equals(wpr.getTradeType())){
                    isSignatureValid = APPWxPayUtils.isSignatureValid(m, DES3_CBCUtil.des3DecodeCBC(PayParam.getWeChatAppPayKey()), WXPayConstants.SignType.MD5);
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
            if(!StringUtils.isEmpty(attach)){
                if(attach.indexOf("billIdEVIAN")>-1){
                    billId = attach.replace("billIdEVIAN","");
                }
                if(attach.indexOf("gboIdEVIAN")>-1){
                    gbo = attach.replace("gboIdEVIAN","");
                }
            }

            // 支付成功
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            String result = "";
            if(!StringUtils.isEmpty(billId)){
                result = baseOrderManager.rechargeCardPaySucNotify(wpr.getOpenid(), wpr.getOutTradeNo(), wpr.getTransactionId(), wpr.getBankType(), wpr.getTotalFee(),wpr.getCashFee(), DateUtil.getStringDate(),billId,orderId,wpr.getPromotion_detail(),wpr.getAppid());
            }else if(!StringUtils.isEmpty(gbo)){

                String gboId = gbo.replace("EVIAN", ",").split(",")[0];
                String xaId = gbo.replace("EVIAN", ",").split(",")[1];
                String isCommander = gbo.replace("EVIAN", ",").split(",")[2];

                result = baseOrderManager.groupBuyPaySucNotify(wpr.getOpenid(), wpr.getOutTradeNo(), wpr.getTransactionId(), wpr.getBankType(), wpr.getTotalFee(),wpr.getCashFee(), DateUtil.getStringDate(),gboId,xaId,isCommander,orderId,wpr.getPromotion_detail(),wpr.getAppid());
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
            String nonce_str = APPWxPayUtils.create_nonce_str();
            wx.setNonce_str(nonce_str);
            String orderquery = APPWxPayUtils.orderquery(wx);
            try {
                Map<String,Object> sendredpackMap = XmlStringUtil.stringToXMLParse(orderquery);
                return sendredpackMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
