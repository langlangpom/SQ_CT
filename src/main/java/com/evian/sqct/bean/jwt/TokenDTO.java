package com.evian.sqct.bean.jwt;

import java.io.Serializable;

/**
 * ClassName:TokenDTO
 * Package:com.evian.sqct.bean.jwt
 * Description:token 返回类
 *
 * @Date:2020/5/26 17:50
 * @Author:XHX
 */
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 9150743633035121270L;

    private String access_token;

    private String refresh_token;

    private Long expires_in;

    /** 极光id */
    private String regeditId;

    public TokenDTO() {}

    public TokenDTO(String access_token,String refresh_token,String regeditId) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.regeditId = regeditId;
    }

    public TokenDTO(String access_token,String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }


    public TokenDTO(String access_token,String refresh_token,Long expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRegeditId() {
        return regeditId;
    }

    public void setRegeditId(String regeditId) {
        this.regeditId = regeditId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "TokenDTO [" +
                "access_token=" + access_token +
                ", refresh_token=" + refresh_token +
                ", expires_in=" + expires_in +
                ", regeditId=" + regeditId +
                ']';
    }
}
