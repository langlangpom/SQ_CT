package com.evian.sqct.service.impl;

import com.evian.sqct.bean.sys.DaoConfig;
import com.evian.sqct.dao.ICacheDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:SystemServiceImpl
 * Package:com.evian.sqct.service.impl
 * Description:请为该功能做描述
 *
 * @Date:2020/6/9 11:17
 * @Author:XHX
 */
@Service
public class SystemServiceImpl implements ISystemService {

    @Autowired
    private DaoConfig daoConfig;

    @Autowired
    private ICacheDao cacheDao;

    @Override
    public boolean updateStoredProcedureCacheSwitch() {
        boolean storedProcedureCacheSwitch = daoConfig.getStoredProcedureCacheSwitch();
        storedProcedureCacheSwitch = !storedProcedureCacheSwitch;
        daoConfig.setSpcSwitch(storedProcedureCacheSwitch);
        return storedProcedureCacheSwitch;
    }

    @Override
    public boolean updateStoredProcedureCacheState(String state) {
        if(state.equals(DaoConfig.MAP) || state.equals(DaoConfig.REDIS)){
            daoConfig.setStoredProcedureCacheState(state);
            return true;
        }
        throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_UPDATE);
    }

    @Override
    public Object test(String t) {
        return cacheDao.get("t");
    }
}
