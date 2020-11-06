package com.evian.sqct.bean.sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ClassName:SystemConfig
 * Package:com.evian.sqct.bean.sys
 * Description:dao层参数配置
 *
 * @Date:2020/6/9 10:42
 * @Author:XHX
 */
@Component
public class DaoConfig {

    public final static String REDIS = "redis";

    public final static String MAP = "map";

    /**
     * 存储开关
     */
    @Value("${storedProcedureCache.switch}")
    private String storedProcedureCacheSwitch;

    private Boolean spcSwitch;

    /**
     * 缓存类型
     * 1. redis
     * 2. map
     */
    @Value("${storedProcedureCache.state}")
    private String storedProcedureCacheState;

    public boolean getStoredProcedureCacheSwitch() {
        if(getSpcSwitch()==null){
            setSpcSwitch(Boolean.parseBoolean(storedProcedureCacheSwitch));
        }
        return getSpcSwitch();
    }

    public void setStoredProcedureCacheSwitch(String storedProcedureCacheSwitch) {
        this.storedProcedureCacheSwitch = storedProcedureCacheSwitch;
    }

    public String getStoredProcedureCacheState() {
        return storedProcedureCacheState;
    }

    public void setStoredProcedureCacheState(String storedProcedureCacheState) {
        this.storedProcedureCacheState = storedProcedureCacheState;
    }

    public void setSpcSwitch(Boolean spcSwitch) {
        this.spcSwitch = spcSwitch;
    }

    private Boolean getSpcSwitch() {
        return spcSwitch;
    }
}
