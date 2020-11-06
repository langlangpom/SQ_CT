package com.evian.sqct.service;

import com.evian.sqct.bean.thirdParty.RecruitOrderPojoBean;
import com.evian.sqct.bean.thirdParty.input.*;
import com.evian.sqct.dao.IThirdPartyDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.mybatis.primaryDataSource.dao.IThirdPartyMapperDao;
import com.evian.sqct.dao.mybatis.primaryDataSource.dao.IUserMapperDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.DESUser.UserDes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BaseThirdPartyManager
 * Package:com.evian.sqct.service
 * Description:第三方service
 *
 * @Date:2020/10/26 10:46
 * @Author:XHX
 */
@Service
public class BaseThirdPartyManager extends BaseManager{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private IThirdPartyDao thirdPartyDao;

    @Autowired
    private IThirdPartyMapperDao thirdPartyMapperDao;

    @Autowired
    private IUserMapperDao userMapperDao;

    @Autowired
    private IUserDao userDao;

    public Map<String,Object> Proc_Backstage_recruit_good_select(ProcBackstageRecruitGoodSelectReqDTO dto){
        return thirdPartyDao.Proc_Backstage_recruit_good_select(dto);
    }

    public String Proc_Backstage_recruit_good_operat(Proc_BackstageRecruitGoodOperatReqDTO dto) {
        return thirdPartyDao.Proc_Backstage_recruit_good_operat(dto);
    }

    public int removeRecruitGood(Integer id){
        return thirdPartyMapperDao.deleteRecruitGood(id);
    }

    public Map<String, Object> Proc_Backstage_recruit_order_select(ProcBackstageRecruitOrderSelectReqDTO dto) {
        return thirdPartyDao.Proc_Backstage_recruit_order_select(dto);
    }

    public String associatedAccount(String account,String order_guid){
        List<RecruitOrderPojoBean> recruitOrders = thirdPartyMapperDao.selectRecruitOrderByOrderGuid(order_guid);
        Map<String, Object> map = userMapperDao.selectNicknameByAccount(account);
        Map<String, Object> make = thirdPartyDao.Proc_Backstage_client_operat_Make(new ProcBackstageClientOperatMakeReqDTO(account));
        if(!"1".equals((String)make.get("TAG"))){
            throw new ResultException((String)make.get("TAG"));
        }
        Integer clientId = (Integer) make.get("clientId");
        if(clientId==null||clientId==0){
            logger.error("生成水趣订单错误:生成账号clientId错误 = {}",clientId);
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_SYSTEM);
        }
        if(map==null){
            // 生成pwd和identityCode
            UserDes userDes = new UserDes(clientId, account);

            userDao.updateEclient(clientId, userDes.getPassWord(), userDes.getIdentityCode());
        }
        Integer XID = recruitOrders.get(0).getXid();
        Integer eid = recruitOrders.get(0).getEid();
        String tag = thirdPartyDao.Proc_Backstage_recruit_order_relation_operat(new ProcBackstageRecruitOrderRelationOperatReqDTO(XID, eid, order_guid, account, clientId, "水趣商户提交"));
        if(!"1".equals(tag)){
            throw new ResultException(tag);
        }
        return tag;
    }

    public String Proc_DisPark_WDT_Save_Order_SendToSq(ProcDisParkWDTSaveOrderSendToSqReqDTO dto){
        return thirdPartyDao.Proc_DisPark_WDT_Save_Order_SendToSq(dto);
    }
}
