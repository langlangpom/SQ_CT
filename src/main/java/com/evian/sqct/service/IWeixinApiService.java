package com.evian.sqct.service;

import com.evian.sqct.bean.wechat.image.WechatV3mediaUploadRepDTO;
import com.evian.sqct.bean.wechat.oauth.AccessTokenRepDTO;

import java.io.File;

/**
 * ClassName:IWeixinApiService
 * Package:com.evian.sqct.service
 * Description:微信api 接口
 *
 * @Date:2020/7/16 14:14
 * @Author:XHX
 */
public interface IWeixinApiService {

    String sendTemplateMessage(String appid,String paramJson);

    /**
     * 微信app授权获取access_token unionid
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    AccessTokenRepDTO app_access_token(String appid,String secret,String code);

    /**
     * 微信支付v3图片上传
     * @param s1
     * @return
     */
    WechatV3mediaUploadRepDTO wechatV3mediaUpload(File file) throws Exception;
}
