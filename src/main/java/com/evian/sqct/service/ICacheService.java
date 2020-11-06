package com.evian.sqct.service;

import com.evian.sqct.bean.jwt.TokenDTO;

import java.util.List;

/**
 * ClassName:ICacheService
 * Package:com.evian.sqct.service
 * Description:缓存service
 *
 * @Date:2020/5/27 15:03
 * @Author:XHX
 */
public interface ICacheService {

    /**
     * 查询用户登录token
     * @param accountId
     * @return
     */
    List<TokenDTO> getLoginAccountTokens(Integer accountId);

    /**
     * 写入用户登录token
     * @param accountId
     * @return
     */
    void setLoginAccountTokens(Integer accountId,List<TokenDTO> token);

    /**
     * 更新用户登录token
     * @param accountId
     * @param token
     * @return
     */
    List<TokenDTO> saveLoginAccountToken(Integer accountId, TokenDTO token);

    /**
     * 移除并返回列表 key 的 最后一个值
     * 没有就返回null
     * @param accountId
     * @return
     */
    TokenDTO rPopLoginAccountTokens(Integer accountId);

    void logoutAccountToken(Integer accountId,String regeditId);

    /**
     * 删除key
     * @param accountId
     * @return
     */
    void removeLoginAccountTokens(Integer accountId);
}
