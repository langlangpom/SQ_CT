package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.thirdParty.input.*;
import com.evian.sqct.dao.IBaseDao;
import com.evian.sqct.dao.IThirdPartyDao;
import com.evian.sqct.util.DaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * ClassName:ThirdPartyDaoImpl
 * Package:com.evian.sqct.dao.impl
 * Description:第三方 dao
 *
 * @Date:2020/10/26 10:51
 * @Author:XHX
 */
@Repository("thirdPartyDao")
public class ThirdPartyDaoImpl implements IThirdPartyDao {

    @Autowired
    IBaseDao baseDao;

    @Override
    public Map<String, Object> Proc_Backstage_recruit_good_select(ProcBackstageRecruitGoodSelectReqDTO dto) {
        return DaoUtil.resultRename(baseDao.agencyDB("Proc_Backstage_recruit_good_select",dto),"goods");
    }

    @Override
    public String Proc_Backstage_recruit_good_operat(Proc_BackstageRecruitGoodOperatReqDTO dto) {
        return DaoUtil.resultTAG(baseDao.agencyDB("Proc_Backstage_recruit_good_select",dto));
    }

    @Override
    public Map<String, Object> Proc_Backstage_recruit_order_select(ProcBackstageRecruitOrderSelectReqDTO dto) {
        return DaoUtil.resultRename(baseDao.agencyDB("Proc_Backstage_recruit_order_select",dto),"orders");
    }

    @Override
    public Map<String, Object> Proc_Backstage_client_operat_Make(ProcBackstageClientOperatMakeReqDTO dto) {
        return baseDao.agencyDB("Proc_Backstage_client_operat_Make",dto);
    }

    @Override
    public String Proc_Backstage_recruit_order_relation_operat(ProcBackstageRecruitOrderRelationOperatReqDTO dto) {
        return DaoUtil.resultTAG(baseDao.agencyDB("Proc_Backstage_recruit_order_relation_operat",dto));
    }

    @Override
    public String Proc_DisPark_WDT_Save_Order_SendToSq(ProcDisParkWDTSaveOrderSendToSqReqDTO dto) {
        return DaoUtil.resultTAG(baseDao.agencyDB("Proc_DisPark_WDT_Save_Order_SendToSq",dto));
    }
}
