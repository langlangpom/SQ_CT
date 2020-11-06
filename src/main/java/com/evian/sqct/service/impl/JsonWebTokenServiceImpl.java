package com.evian.sqct.service.impl;

import com.evian.sqct.bean.jwt.JWTHeaderBean;
import com.evian.sqct.bean.jwt.TokenDTO;
import com.evian.sqct.service.IJsonWebTokenService;
import io.jsonwebtoken.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * ClassName:JsonWebTokenServiceImpl
 * Package:com.eviano2o.service.impl
 * Description:请为该功能做描述
 *
 * @Date:2020/4/16 16:33
 * @Author:XHX
 */
@Service
public class JsonWebTokenServiceImpl implements IJsonWebTokenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTHeaderBean jwtHeaderBean;

    private volatile Key KEY;

    private volatile JwtParser jwtParser;

    public Key getKEY() {
        if(KEY==null){
            synchronized (this){
                if(KEY==null){
                    KEY = new SecretKeySpec(jwtHeaderBean.getSecretKey().getBytes(), SignatureAlgorithm.HS512.getJcaName());
                }
            }
        }
        return KEY;
    }

    public JwtParser getJwtParser() {
        if(jwtParser==null){
            synchronized (this){
                if(jwtParser==null){
                    jwtParser = Jwts.parser().setSigningKey(this.getKEY());
                }
            }
        }
        return jwtParser;
    }

    /**
     *
     * @param payload   携带内容 JSON格式 需要自己加过期时间
     * @return
     *                      .setSubject(username)           // 代表这个JWT的主体，即它的所有人
     *                     .setIssuer(audience.getClientId())              // 代表这个JWT的签发主体；
     *                     .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
     *                     .setAudience(audience.getName())          // 代表这个JWT的接收对象；
     *                     .setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
     *                     .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
     *                     时间戳 10位数，不是13位数
     */
    @Override
    public String createAccessToken(String payload) {
        long nowDate = System.currentTimeMillis();
        //过期时间
        long expireDate = nowDate + jwtHeaderBean.getExpiration() * 1000;

        JSONObject claims = JSONObject.fromObject(payload);

        claims.put(Claims.ISSUED_AT,nowDate/1000);
        //过期时间
        claims.put(Claims.EXPIRATION,expireDate/1000);
        payload = claims.toString();

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setPayload(payload)
                .signWith(SignatureAlgorithm.HS512, this.getKEY())
                .compact();
        return token;
    }

    /**
     *
     * @param claims   携带内容 map
     * @return
     * String ISSUER = "iss";       代表这个JWT的签发主体；
     * String SUBJECT = "sub";      代表这个JWT的主体，即它的所有人
     * String AUDIENCE = "aud";     代表这个JWT的接收对象；
     * String EXPIRATION = "exp";   是一个时间戳，代表这个JWT的过期时间；
     * String NOT_BEFORE = "nbf";   是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
     * String ISSUED_AT = "iat";    是一个时间戳，代表这个JWT的签发时间；
     */
    @Override
    public String createAccessToken(Map<String, Object> claims) {
        long l = System.currentTimeMillis();
        Date nowDate = new Date(l);
        //过期时间
        Date expireDate = new Date(l + jwtHeaderBean.getExpiration() * 1000);
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, this.getKEY())
                .compact();
        return token;
    }

    /**
     *
     * @param payload   携带内容 JSON格式 需要自己加过期时间
     * @return
     *                      .setSubject(username)           // 代表这个JWT的主体，即它的所有人
     *                     .setIssuer(audience.getClientId())              // 代表这个JWT的签发主体；
     *                     .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
     *                     .setAudience(audience.getName())          // 代表这个JWT的接收对象；
     *                     .setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
     *                     .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
     *                     时间戳 10位数，不是13位数
     */
    @Override
    public String createRefreshToken(String payload) {
        long nowDate = System.currentTimeMillis();
        //过期时间
        long expireDate = nowDate + jwtHeaderBean.getRefreshExpiration() * 1000;

        JSONObject claims = JSONObject.fromObject(payload);

        claims.put(Claims.ISSUED_AT,nowDate/1000);
        //过期时间
        claims.put(Claims.EXPIRATION,expireDate/1000);
        payload = claims.toString();

        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setPayload(payload)
                .signWith(SignatureAlgorithm.HS512, this.getKEY())
                .compact();
        return token;
    }

    /**
     *
     * @param claims   携带内容 map
     * @return
     * String ISSUER = "iss";       代表这个JWT的签发主体；
     * String SUBJECT = "sub";      代表这个JWT的主体，即它的所有人
     * String AUDIENCE = "aud";     代表这个JWT的接收对象；
     * String EXPIRATION = "exp";   是一个时间戳，代表这个JWT的过期时间；
     * String NOT_BEFORE = "nbf";   是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
     * String ISSUED_AT = "iat";    是一个时间戳，代表这个JWT的签发时间；
     */
    @Override
    public String createRefreshToken(Map<String, Object> claims) {
        long l = System.currentTimeMillis();
        Date nowDate = new Date(l);
        //过期时间
        Date expireDate = new Date(l + jwtHeaderBean.getRefreshExpiration() * 1000);
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, this.getKEY())
                .compact();
        return token;
    }

    /**
     * 生成 token
     *
     * @param payload 携带内容 JSON格式
     * @return
     */
    @Override
    public TokenDTO createToken(String payload) {
        return new TokenDTO(createAccessToken(payload),createRefreshToken(payload),jwtHeaderBean.getExpiration());
    }

    /**
     * 生成 token
     *
     * @param claims 携带内容 map
     * @return
     */
    @Override
    public TokenDTO createToken(Map<String, Object> claims) {
        return new TokenDTO(createAccessToken(claims),createRefreshToken(claims),jwtHeaderBean.getExpiration());
    }

    @Override
    public Claims parseToken(String token) {

        Claims claims = this.getJwtParser().parseClaimsJws(token).getBody();
        return claims;
    }

    @Override
    public String getHeaderToken(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtHeaderBean.getAUTH_HEADER_KEY());
        String bearer = jwtHeaderBean.getTOKEN_PREFIX();
        if(StringUtils.isBlank(authHeader)|| !authHeader.startsWith(bearer)){
            return null;
        }

        String token = authHeader.substring(bearer.length());
        if(!StringUtils.isBlank(token)){
            return token;
        }

        return null;
    }

    @Override
    public Integer getTokenClientId(HttpServletRequest request) {
        return null;
    }

    @Override
    public Integer getTokenAccountId(HttpServletRequest request) {
        String token = getHeaderToken(request);
        if(token!=null){
            Claims claims = parseToken(token);
            return (Integer) claims.get("accountId");
        }
        return null;
    }

    @Override
    public Integer getTokenAccountId(String token) {
        if(token!=null){
            Claims claims = parseToken(token);
            return (Integer) claims.get("accountId");
        }
        return null;
    }

    @Override
    public String getTokenRegeditId(HttpServletRequest request) {
        String token = getHeaderToken(request);
        if(token!=null){
            Claims claims = parseToken(token);
            return (String) claims.get("regeditId");
        }
        return null;
    }
}
