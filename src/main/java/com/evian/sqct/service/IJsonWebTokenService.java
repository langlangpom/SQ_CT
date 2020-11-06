package com.evian.sqct.service;

import com.evian.sqct.bean.jwt.TokenDTO;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ClassName:IJwtService
 * Package:com.eviano2o.service
 * Description:JWT服务
 *
 * @Date:2020/4/16 16:32
 * @Author:XHX
 */
public interface IJsonWebTokenService {

    /**
     * 生成 access_token
     * @param payload   携带内容 JSON格式
     * @return
     */
    String createAccessToken(String payload);

    /**
     * 生成 access_token
     * @param claims   携带内容 map
     * @return
     */
    String createAccessToken(Map<String, Object> claims);

    /**
     * 生成 access_token
     * @param payload   携带内容 JSON格式
     * @return
     */
    String createRefreshToken(String payload);

    /**
     * 生成 access_token
     * @param claims   携带内容 map
     * @return
     */
    String createRefreshToken(Map<String, Object> claims);



    /**
     * token 解密 验证
     * @param token
     * @return
     */
    Claims parseToken(String token);

    /**
     * 获取头部token
     * @param request
     * @return
     */
    String getHeaderToken(HttpServletRequest request);

    Integer getTokenClientId(HttpServletRequest request);

    Integer getTokenAccountId(HttpServletRequest request);

    Integer getTokenAccountId(String request);

    String getTokenRegeditId(HttpServletRequest request);

    /**
     * 生成 token
     * @param payload   携带内容 JSON格式
     * @return
     */
    TokenDTO createToken(String payload);

    /**
     * 生成 token
     * @param claims   携带内容 map
     * @return
     */
    TokenDTO createToken(Map<String, Object> claims);
}
