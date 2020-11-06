package com.evian.sqct.bean.wechat.image;

import com.evian.sqct.bean.util.BaseResponseDTO;

/**
 * ClassName:WechatV3mediaUploadRepDTO
 * Package:com.evian.sqct.bean.wechat.image
 * Description:微信支付v3图片上传api返回实体类
 *
 * @Date:2020/9/25 11:14
 * @Author:XHX
 */
public class WechatV3mediaUploadRepDTO extends BaseResponseDTO {
    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    @Override
    public String toString() {
        return "WechatV3mediaUploadRepDTO [" +
                "media_id=" + media_id +
                ']';
    }
}
