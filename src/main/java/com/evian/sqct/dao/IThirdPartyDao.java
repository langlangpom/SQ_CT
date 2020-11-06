package com.evian.sqct.dao;

import com.evian.sqct.bean.thirdParty.input.*;

import java.util.Map;

/**
 * ClassName:IThirdPartyDao
 * Package:com.evian.sqct.dao
 * Description:第三方 dao
 *
 * @Date:2020/10/26 10:50
 * @Author:XHX
 */
public interface IThirdPartyDao {

    Map<String,Object> Proc_Backstage_recruit_good_select(ProcBackstageRecruitGoodSelectReqDTO dto);

    String Proc_Backstage_recruit_good_operat(Proc_BackstageRecruitGoodOperatReqDTO dto);

    Map<String,Object> Proc_Backstage_recruit_order_select(ProcBackstageRecruitOrderSelectReqDTO dto);

    Map<String,Object> Proc_Backstage_client_operat_Make(ProcBackstageClientOperatMakeReqDTO dto);

    String Proc_Backstage_recruit_order_relation_operat(ProcBackstageRecruitOrderRelationOperatReqDTO dto);

    String Proc_DisPark_WDT_Save_Order_SendToSq(ProcDisParkWDTSaveOrderSendToSqReqDTO dto);
}
