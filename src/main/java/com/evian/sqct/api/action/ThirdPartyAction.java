package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.thirdParty.input.ProcBackstageRecruitGoodSelectReqDTO;
import com.evian.sqct.bean.thirdParty.input.ProcBackstageRecruitOrderSelectReqDTO;
import com.evian.sqct.bean.thirdParty.input.ProcDisParkWDTSaveOrderSendToSqReqDTO;
import com.evian.sqct.bean.thirdParty.input.Proc_BackstageRecruitGoodOperatReqDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseThirdPartyManager;
import com.evian.sqct.service.BaseUserManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * ClassName:ThirdPartyAction
 * Package:com.evian.sqct.api.action
 * Description:第三方 action
 *
 * @Date:2020/10/26 10:44
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/thirdParty")
public class ThirdPartyAction  extends BaseAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseThirdPartyManager thirdPartyManager;

    @Autowired
    private BaseUserManager baseUserManager;

    /**
     * 236.查询第三方关联商品
     * @param dto
     * @return
     */
    @RequestMapping("recruitGoodSelect")
    public Map<String,Object> recruitGoodSelect(@Valid ProcBackstageRecruitGoodSelectReqDTO dto){
        return thirdPartyManager.Proc_Backstage_recruit_good_select(dto);
    }

    /**
     * 237.第三方关联商品操作
     * @param dto
     * @return
     */
    @RequestMapping("recruitGoodOperat")
    public ResultVO recruitGoodOperat(@Valid Proc_BackstageRecruitGoodOperatReqDTO dto){
        String tag = thirdPartyManager.Proc_Backstage_recruit_good_operat(dto);
        if(!"1".equals(tag)){
            throw new ResultException(tag);
        }
        return new ResultVO();
    }

    /**
     * 238.刪除第三方关联商品
     * @param dto
     * @return
     */
    @RequestMapping("removeRecruitGood")
    public ResultVO removeRecruitGood(Integer id){
        if(id==null){
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        int i = thirdPartyManager.removeRecruitGood(id);
        if(i==0){
            return new ResultVO(ResultCode.CODE_ERROR_OPERATION);
        }
        return new ResultVO();
    }


    /**
     * 239.查询第三方订单
     * @param dto
     * @return
     */
    @RequestMapping("recruitOrderSelect")
    public Map<String,Object> recruitOrderSelect(@Valid ProcBackstageRecruitOrderSelectReqDTO dto){
        return thirdPartyManager.Proc_Backstage_recruit_order_select(dto);
    }

    /**
     * 240.关联账号前点查看账号
     * @return
     */
    @RequestMapping("lookAccount")
    public ResultVO lookAccount(String account){
        if(StringUtils.isBlank(account)){
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        return new ResultVO(baseUserManager.lookAccount(account));
    }


    /**
     * 241.关联账号
     * @return
     */
    @RequestMapping("associatedAccount")
    public ResultVO associatedAccount(String account,String order_guid){
        if(StringUtils.isBlank(account)||StringUtils.isBlank(order_guid)){
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        String tag = thirdPartyManager.associatedAccount(account, order_guid);
        if(!"1".equals(tag)){
            throw new ResultException(tag);
        }
        return new ResultVO();
    }

    /**
     * 242.提交第三方订单到水趣
     * @return
     */
    @RequestMapping("submitThirdPartyOrderByShuiqoo")
    public ResultVO submitThirdPartyOrderByShuiqoo(@Valid ProcDisParkWDTSaveOrderSendToSqReqDTO dto){
        String tag = thirdPartyManager.Proc_DisPark_WDT_Save_Order_SendToSq(dto);
        if(!"1".equals(tag)){
            throw new ResultException(tag);
        }
        return new ResultVO();
    }
}
