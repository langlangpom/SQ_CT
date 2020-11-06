package com.evian.sqct;

import com.evian.sqct.bean.pay.WXPayConstants;
import com.evian.sqct.bean.pay.WXPayConstants.SignType;
import com.evian.sqct.util.HttpClientUtil;
import com.evian.sqct.util.MD5Util;
import com.evian.sqct.wxHB.RequestHandler;
import com.evian.sqct.wxPay.APPWxPayBean;
import com.evian.sqct.wxPay.EnterprisePayByLooseChangeBean;
import com.evian.sqct.wxPay.WXPayXmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.*;


/**
 * ClassName:APPWxPayUtils
 * Package:com.evian.sqct.wxPay
 * Description:app微信支付工具类
 *
 * @Date:2019/12/11 15:36
 * @Author:XHX
 */
public class APPWxPayUtils {
    private static final Logger logger = LoggerFactory.getLogger(APPWxPayUtils.class);
    /**
     * 微信app支付
     * @param wx
     * @return
     */
    public static String pay(APPWxPayBean wx){
        logger.info("=======================================================================微信app支付开始");
        logger.info("微信app支付入口参数："+wx.toString());
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", wx.getAppid());
        packageParams.put("mch_id", wx.getMch_id());
        packageParams.put("nonce_str", wx.getNonce_str());
        packageParams.put("sub_appid", wx.getSub_appid());
        packageParams.put("sub_mch_id", wx.getSub_mch_id());
        packageParams.put("body", wx.getBody());
        //2019-07-08 彭安需要orderId来优化回调速度    DIVISION分隔符  DIVISION前是orderId ， DIVISION后是充值、拼团id
        wx.setAttach(wx.getOrderId()+"DIVISION"+(wx.getAttach()==null?"":wx.getAttach()));
        packageParams.put("attach", wx.getAttach());
        packageParams.put("total_fee", wx.getTotal_fee());
        packageParams.put("out_trade_no", wx.getOut_trade_no());
        packageParams.put("spbill_create_ip", wx.getSpbill_create_ip());
        packageParams.put("notify_url", wx.getNotify_url());
        packageParams.put("trade_type", wx.getTrade_type());
        RequestHandler reqHandler = new RequestHandler();
        reqHandler.init(wx.getAppKey());
        String sign = reqHandler.createSign(packageParams);
        wx.setSign(sign);
        /** 封装报文 */
        String xml = "<xml>"+
                "<nonce_str><![CDATA[" + wx.getNonce_str() + "]]></nonce_str>"+
                "<sign><![CDATA[" + wx.getSign() + "]]></sign>"+
                "<appid><![CDATA["+wx.getAppid()+"]]></appid>"+
                "<mch_id><![CDATA["+wx.getMch_id()+"]]></mch_id>"+
                "<sub_appid><![CDATA[" + wx.getSub_appid() + "]]></sub_appid>"+
                "<sub_mch_id><![CDATA[" + wx.getSub_mch_id() + "]]></sub_mch_id>"+
                "<body>" + wx.getBody() + "</body>"+
                "<attach>" + wx.getAttach() + "</attach>"+
                "<total_fee><![CDATA[" + wx.getTotal_fee() + "]]></total_fee>"+
                "<out_trade_no><![CDATA[" + wx.getOut_trade_no() + "]]></out_trade_no>"+
                "<spbill_create_ip><![CDATA[" + wx.getSpbill_create_ip() + "]]></spbill_create_ip>"+
                "<notify_url><![CDATA[" + wx.getNotify_url() + "]]></notify_url>"+
                "<trade_type><![CDATA[" + wx.getTrade_type() + "]]></trade_type>"+
                "</xml>";

        //获取预支付ID

        String createOrderURL = "https://"+WXPayConstants.DOMAIN_API+WXPayConstants.UNIFIEDORDER_URL_SUFFIX;
        logger.info("微信app支付prepayId请求地址: "+createOrderURL+", 请求数据: "+xml);
        String prepayContent = HttpClientUtil.post(createOrderURL , xml);
        logger.info("微信app支付prepayId请求返回结果: "+prepayContent);
        return prepayContent;
    }

    /**
     * 企业支付到零钱
     * @return
     */
    public static String enterprisePayByLooseChange(EnterprisePayByLooseChangeBean wx) throws Exception {
        logger.info("=======================================================================微信支付到零钱开始");
        logger.info("微信支付到零钱入口参数："+wx.toString());
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mch_appid", wx.getMch_appid());
        packageParams.put("mchid", wx.getMchid());
        packageParams.put("nonce_str", wx.getNonce_str());
        packageParams.put("partner_trade_no", wx.getPartner_trade_no());
        packageParams.put("openid", wx.getOpenid());
        packageParams.put("check_name", wx.getCheck_name());
        packageParams.put("amount", wx.getAmount());
        packageParams.put("desc", wx.getDesc());
        packageParams.put("spbill_create_ip", wx.getSpbill_create_ip());
        RequestHandler reqHandler = new RequestHandler();
        reqHandler.init(wx.getAppKey());
        String sign = reqHandler.createSign(packageParams);
        wx.setSign(sign);
        /** 封装报文 */
        String xml = "<xml>"+
                "<mch_appid><![CDATA[" + wx.getMch_appid() + "]]></mch_appid>"+
                "<mchid><![CDATA[" + wx.getMchid() + "]]></mchid>"+
                "<nonce_str><![CDATA["+wx.getNonce_str()+"]]></nonce_str>"+
                "<partner_trade_no><![CDATA["+wx.getPartner_trade_no()+"]]></partner_trade_no>"+
                "<openid><![CDATA[" + wx.getOpenid() + "]]></openid>"+
                "<check_name><![CDATA[" + wx.getCheck_name() + "]]></check_name>"+
                "<amount>" + wx.getAmount() + "</amount>"+
                "<desc><![CDATA[" + wx.getDesc() + "]]></desc>"+
                "<spbill_create_ip><![CDATA[" + wx.getSpbill_create_ip() + "]]></spbill_create_ip>"+
                "<sign><![CDATA[" + wx.getSign() + "]]></sign>"+
                "</xml>";

        //获取预支付ID

        String createOrderURL = "https://"+WXPayConstants.DOMAIN_API+WXPayConstants.MMPAYMKTTRANSFERS_TRANSFERS_URL_SUFFIX;
        logger.info("微信支付到零钱prepayId请求地址: "+createOrderURL+", 请求数据: "+xml);
        String prepayContent = HttpClientUtil.sslPost(createOrderURL , xml,wx.getMchid());
        logger.info("微信支付到零钱prepayId请求返回结果: "+prepayContent);
        return prepayContent;
    }

    /**
     * 微信订单查询
     * @param wx
     * @return
     */
    public static String orderquery(APPWxPayBean wx){
        logger.info("=======================================================================微信订单查询开始");
        logger.info("微信订单查询入口参数："+wx.toString());
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", wx.getAppid());
        packageParams.put("mch_id", wx.getMch_id());
        packageParams.put("sub_appid", wx.getSub_appid());
        packageParams.put("sub_mch_id", wx.getSub_mch_id());
        packageParams.put("nonce_str", wx.getNonce_str());
        packageParams.put("out_trade_no", wx.getOut_trade_no());
        RequestHandler reqHandler = new RequestHandler();
        reqHandler.init(wx.getAppKey());
        String sign = reqHandler.createSign(packageParams);
        wx.setSign(sign);
        /** 封装报文 */
        String xml = "<xml>"+
                "<appid><![CDATA["+wx.getAppid()+"]]></appid>"+
                "<mch_id><![CDATA["+wx.getMch_id()+"]]></mch_id>"+
                "<sub_appid><![CDATA[" + wx.getSub_appid() + "]]></sub_appid>"+
                "<sub_mch_id><![CDATA[" + wx.getSub_mch_id() + "]]></sub_mch_id>"+
                "<nonce_str><![CDATA[" + wx.getNonce_str() + "]]></nonce_str>"+
                "<out_trade_no><![CDATA[" + wx.getOut_trade_no() + "]]></out_trade_no>"+
                "<sign><![CDATA[" + wx.getSign() + "]]></sign>"+
                "</xml>";

        //获取预支付ID
        String createOrderURL = "https://"+WXPayConstants.DOMAIN_API+WXPayConstants.ORDERQUERY_URL_SUFFIX;
        logger.info("微信订单查询prepayId请求地址: "+createOrderURL+", 请求数据: "+xml);
        String prepayContent = HttpClientUtil.post(createOrderURL , xml);
        logger.info("微信订单查询prepayId请求返回结果: "+prepayContent);
        return prepayContent;
    }

    public static String create_nonce_str() {
        String guid = UUID.randomUUID().toString();
        return MD5Util.md5(guid).toUpperCase();
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML
     *            XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(
                    strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            logger.error(
                    "[Invalid XML, can not convert to map. Error message: {}. XML content: {}]",
                    new Object[] { ex.getMessage(), strXML });
            throw ex;
        }

    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data
     *            Map类型数据
     * @param key
     *            API密钥
     * @param signType
     *            签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data,
                                           String key, SignType signType) throws Exception {
        if (!data.containsKey(WXPayConstants.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data
     *            待签名数据
     * @param key
     *            API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data,
                                           String key) throws Exception {
        return generateSignature(data, key, SignType.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data
     *            待签名数据
     * @param key
     *            API密钥
     * @param signType
     *            签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data,
                                           String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) {// 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        } else if (SignType.HMACSHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        } else {
            throw new Exception(
                    String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 生成 MD5
     *
     * @param data
     *            待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100)
                    .substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 生成 HMACSHA256
     *
     * @param data
     *            待处理数据
     * @param key
     *            密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"),
                "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100)
                    .substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }


}
