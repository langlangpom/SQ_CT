package com.evian.sqct.service.impl;

import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.bean.wechat.WeChatApiV3MerchantMessage;
import com.evian.sqct.bean.wechat.image.WechatV3mediaUploadRepDTO;
import com.evian.sqct.bean.wechat.oauth.AccessTokenRepDTO;
import com.evian.sqct.dao.ISystemDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.service.IWeixinApiService;
import com.evian.sqct.util.HttpClientUtil;
import com.evian.sqct.util.JacksonUtils;
import com.evian.sqct.util.WxTokenAndJsticketCache;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.PrivateKey;
import java.util.List;

/**
 * ClassName:WeixinApiServiceImpl
 * Package:com.evian.sqct.service.impl
 * Description:请为该功能做描述
 *
 * @Date:2020/7/16 14:16
 * @Author:XHX
 */
@Service
public class WeixinApiServiceImpl implements IWeixinApiService {

    @Autowired
    private ISystemDao systemDao;

    @Override
    public String sendTemplateMessage(String appid, String paramJson) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WxTokenAndJsticketCache.getAccess_token(appid);
        return HttpClientUtil.post(url, paramJson);
    }

    @Override
    public AccessTokenRepDTO app_access_token(String appid, String secret, String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        String result = HttpClientUtil.doGet(url);
        return JacksonUtils.json2pojo(result,AccessTokenRepDTO.class);
    }

    /**
     * 微信支付v3图片上传
     *
     * @param s1
     * @return
     */
    @Override
    public WechatV3mediaUploadRepDTO wechatV3mediaUpload(File file) throws Exception {

        WeChatApiV3MerchantMessage merchantMessage = getMerchantMessage();
        // 商户号
        String mchId = merchantMessage.getMchId();
        // 商户证书序列号
        String mchSerialNo = merchantMessage.getMchSerialNo();
        // api密钥
        String apiV3Key = merchantMessage.getApiV3Key();
        // 你的商户私钥
        String privateKey = merchantMessage.getPrivateKey();

        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
                new ByteArrayInputStream(privateKey.getBytes("utf-8")));
        //使用自动更新的签名验证器，不需要传入证书
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
                apiV3Key.getBytes("utf-8"));

        CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier))
                .build();

        URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");
        /*String guid = UUID.randomUUID().toString();
        String fileName = MD5Util.md5(guid).toUpperCase();
        fileName = fileName+".png";*/
        try (FileInputStream s1 = new FileInputStream(file)) {
            String sha256 = DigestUtils.sha256Hex(s1);
            try (InputStream s2 = new FileInputStream(file)) {
                WechatPayUploadHttpPost request = new WechatPayUploadHttpPost.Builder(uri)
                        .withImage(file.getName(), sha256, s2)
                        .build();

                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    HttpEntity entity1 = response.getEntity();
                    // do something useful with the response body
                    // and ensure it is fully consumed
                    String result = EntityUtils.toString(entity1, "utf-8");
                    System.out.println(result);
                    return JacksonUtils.json2pojo(result, WechatV3mediaUploadRepDTO.class);
                } finally {
                    response.close();
                }
            }
        }
    }

    private WeChatApiV3MerchantMessage getMerchantMessage(){
        List<SysParamModel> wechatServicePayMchId = systemDao.selectESystemConfig("WechatServicePayMchId");
        List<SysParamModel> WechatServicePaySecret = systemDao.selectESystemConfig("WechatServicePaySecret");
        List<SysParamModel> WechatServicePayCertSn = systemDao.selectESystemConfig("WechatServicePayCertSn");
        List<SysParamModel> WechatServicePayPrivateKey = systemDao.selectESystemConfig("WechatServicePayPrivateKey");
        if(wechatServicePayMchId.size()==0
                || WechatServicePaySecret.size()==0
                || WechatServicePayCertSn.size()==0
                || WechatServicePayPrivateKey.size()==0
                || StringUtils.isBlank(wechatServicePayMchId.get(0).getSysValue())
                || StringUtils.isBlank(WechatServicePaySecret.get(0).getSysValue())
                || StringUtils.isBlank(WechatServicePayCertSn.get(0).getSysValue())
                || StringUtils.isBlank(WechatServicePayPrivateKey.get(0).getSysValue())){
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_MCCHANT_MESSAGE_NOT);
        }
        return new WeChatApiV3MerchantMessage(wechatServicePayMchId.get(0).getSysValue()
                , WechatServicePayCertSn.get(0).getSysValue()
                , WechatServicePaySecret.get(0).getSysValue()
                , WechatServicePayPrivateKey.get(0).getSysValue());
    }
}
