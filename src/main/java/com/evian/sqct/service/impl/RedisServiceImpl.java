package com.evian.sqct.service.impl;

import com.evian.sqct.bean.jwt.JWTHeaderBean;
import com.evian.sqct.bean.jwt.TokenDTO;
import com.evian.sqct.dao.ICacheDao;
import com.evian.sqct.service.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:RedisServiceImpl
 * Package:com.evian.sqct.service.impl
 * Description:redis缓存操作
 *
 * @Date:2020/5/27 15:04
 * @Author:XHX
 */
@Service
public class RedisServiceImpl implements ICacheService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICacheDao cacheDao;

    @Autowired
    private JWTHeaderBean jwtHeaderBean;


    private String appendTALHeader(Integer accountId){
        StringBuilder tokenAccountLoginHeader = new StringBuilder("tokenAccountLogin:");
        return tokenAccountLoginHeader.append(accountId).toString();
    }

    /**
     * 查询用户登录token
     *
     * @param accountId
     * @return
     */
    @Override
    public List<TokenDTO> getLoginAccountTokens(Integer accountId) {
        return (List<TokenDTO>) cacheDao.get(appendTALHeader(accountId));
    }

    /**
     * 写入用户登录token
     *
     * @param accountId
     * @param token
     * @return
     */
    @Override
    public void setLoginAccountTokens(Integer accountId, List<TokenDTO> token) {
        cacheDao.setExpireSeconds(appendTALHeader(accountId),token,jwtHeaderBean.getRefreshExpiration());
    }

    /**
     * 写入用户登录token
     *
     * @param accountId
     * @param token
     * @return
     */
    @Override
    public List<TokenDTO> saveLoginAccountToken(Integer accountId, TokenDTO token) {
        // 获取缓存的token已登录用户
        List<TokenDTO> loginAccountTokens = getLoginAccountTokens(accountId);
        logger.info("loginAccountTokens:11{}",loginAccountTokens);

        // 超过登录限制数量的用户
        List<TokenDTO> tokenDTOs = new ArrayList<>();
        if(loginAccountTokens!=null&&loginAccountTokens.size()>0){
            // 筛选出 极光id regeditId 不相同的集合  (将同设备的删掉，说明是同设备反复登录了)
            loginAccountTokens = loginAccountTokens.stream().filter(dto -> !dto.getRegeditId().equals(token.getRegeditId())).collect(Collectors.toList());

            loginAccountTokens.add(0,token);
            int size = loginAccountTokens.size();
            Integer numberOfSharedAccounts = jwtHeaderBean.getNumberOfSharedAccounts();
            int differ = size - numberOfSharedAccounts;
            if(differ>0){
                for (int i = 0; i <differ ; i++) {
                    TokenDTO tokenDTO = rPopListToken(loginAccountTokens);
                    tokenDTOs.add(tokenDTO);
                }
            }
        }else{
            loginAccountTokens = new ArrayList<>();
            loginAccountTokens.add(token);
        }
        logger.info("loginAccountTokens22:{}",loginAccountTokens);
        setLoginAccountTokens(accountId,loginAccountTokens);
        return tokenDTOs;
    }



    /**
     * 移除并返回列表 key 的 最后一个值
     * 没有就返回null
     *
     * @param accountId
     * @return
     */
    @Override
    public TokenDTO rPopLoginAccountTokens(Integer accountId) {
        List<TokenDTO> loginAccountTokens = getLoginAccountTokens(accountId);
        if(loginAccountTokens!=null&&loginAccountTokens.size()>0){
            TokenDTO tokenDTO = rPopListToken(loginAccountTokens);
            // 赋值回去
            setLoginAccountTokens(accountId,loginAccountTokens);
            // 返回最后一个值
            return tokenDTO;
        }
        return null;
    }


    /**
     * 移除并返回列表 key 的 最后一个值
     * 没有就返回null
     *
     * @param accountId
     * @return
     */
    private TokenDTO rPopListToken(List<TokenDTO> tokens){
        if(tokens!=null&&tokens.size()>0){
            int sizeMinusOne = tokens.size()- 1;
            TokenDTO tokenDTO = tokens.get(sizeMinusOne);
            // 删除最后一个值
            tokens.remove(sizeMinusOne);
            // 返回最后一个值
            return tokenDTO;
        }
        return null;
    }

    @Override
    public void logoutAccountToken(Integer accountId,String regeditId) {
        // 获取缓存的token已登录用户
        List<TokenDTO> loginAccountTokens = getLoginAccountTokens(accountId);
        if(loginAccountTokens!=null&&loginAccountTokens.size()>0){
            // 筛选出 极光id regeditId 不相同的集合  (将同设备的删掉，说明是同设备反复登录了)
            for (int i = 0; i <loginAccountTokens.size() ; i++) {
                if(loginAccountTokens.get(i).getRegeditId().equals(regeditId)){
                    loginAccountTokens.remove(i);
                    if(loginAccountTokens.size()==0){
                        removeLoginAccountTokens(accountId);
                    }else {
                        // 在缓存里清除此账号
                        setLoginAccountTokens(accountId,loginAccountTokens);
                    }
                    break;
                }
            }
        }
    }

    /**
     * 删除key
     *
     * @param accountId
     * @return
     */
    @Override
    public void removeLoginAccountTokens(Integer accountId) {
        cacheDao.removeValue(appendTALHeader(accountId));
    }
}
