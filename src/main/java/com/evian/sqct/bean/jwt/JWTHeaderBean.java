package com.evian.sqct.bean.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * ClassName:JWTHeaderBean
 * Package:com.eviano2o.bean.jwt
 * Description:JWT头部信息
 *
 * @Date:2020/4/16 16:18
 * @Author:XHX
 */
@Component
public class JWTHeaderBean implements Serializable {

    private static final long serialVersionUID = -6875715041241425451L;


    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("jwt")
    private String type;

    /**
     * access_token 7天过期
     */
    @Value("${jwt.expiration}")
    private String expiration;

    /**
     * refresh_token 30天过期
     */
    @Value("${jwt.refreshExpiration}")
    private String refreshExpiration;

    /**
     * 共享登录人数
     * @return
     */
    @Value("${jwt.numberOfSharedAccounts}")
    private String numberOfSharedAccounts;

    @Value("Authorization")
    private String AUTH_HEADER_KEY;

    @Value("Bearer ")
    private String TOKEN_PREFIX;


    public Integer getNumberOfSharedAccounts() {
        return Integer.parseInt(numberOfSharedAccounts);
    }

    public void setNumberOfSharedAccounts(String numberOfSharedAccounts) {
        this.numberOfSharedAccounts = numberOfSharedAccounts;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExpiration() {
        return Long.parseLong(expiration);
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getAUTH_HEADER_KEY() {
        return AUTH_HEADER_KEY;
    }

    public void setAUTH_HEADER_KEY(String AUTH_HEADER_KEY) {
        this.AUTH_HEADER_KEY = AUTH_HEADER_KEY;
    }

    public String getTOKEN_PREFIX() {
        return TOKEN_PREFIX;
    }

    public void setTOKEN_PREFIX(String TOKEN_PREFIX) {
        this.TOKEN_PREFIX = TOKEN_PREFIX;
    }

    public Long getRefreshExpiration() {
        return Long.parseLong(refreshExpiration);
    }

    public void setRefreshExpiration(String refreshExpiration) {
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    public String toString() {
        return "JWTHeaderBean [" +
                "secretKey=" + secretKey +
                ", type=" + type +
                ", expiration=" + expiration +
                ", refreshExpiration=" + refreshExpiration +
                ", numberOfSharedAccounts=" + numberOfSharedAccounts +
                ", AUTH_HEADER_KEY=" + AUTH_HEADER_KEY +
                ", TOKEN_PREFIX=" + TOKEN_PREFIX +
                ']';
    }
}
