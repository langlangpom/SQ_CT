package com.evian.sqct.bean.appletLiveStreaming.response;

/**
 * ClassName:UploadRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:upload
 *
 * @Date:2020/6/22 11:44
 * @Author:XHX
 */
public class UploadRpeDTO {
    private String type;
    private String media_id;
    private Long created_at;
    private Integer errcode;
    private String errmsg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "UploadRpeDTO [" +
                "type=" + type +
                ", media_id=" + media_id +
                ", created_at=" + created_at +
                ", errcode=" + errcode +
                ", errmsg=" + errmsg +
                ']';
    }
}
