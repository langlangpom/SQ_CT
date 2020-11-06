package com.evian.sqct.api.action;

import com.evian.sqct.annotation.TokenNotVerify;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.activity.EappMerchantAhareCodeActivityItem;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseActivityManager;
import com.evian.sqct.util.WxCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ActivityAction
 * Package:com.evian.sqct.api.action
 * Description:活动Action
 *
 * @Date:2019/10/10 16:10
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/activity")
public class ActivityAction extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseActivityManager baseActivityManager;


    /**
     * 148.查询优惠券活动
     * @return
     */
    @RequestMapping("findCouponActivity.action")
    public Object findCouponActivity(Integer eid,Integer accountId) {
        if(eid==null||accountId==null) {
            return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
        }
        Map<String,Object> eappMerchantShareCodeActivities = baseActivityManager.selectEappMerchantShareCodeActivityByEidAndActivityType(eid,accountId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("couponActivity", eappMerchantShareCodeActivities);
        resultMap.put("serverTime", System.currentTimeMillis());

        return resultMap;
    }

    /**
     * 149.根据活动查询优惠券
     * @return
     */
    @RequestMapping("findCouponByActivityId.action")
    public Object findCouponByActivityId(Integer activityId) {
        if(activityId==null) {
            return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
        }
        List<EappMerchantAhareCodeActivityItem> eappMerchantAhareCodeActivityItems = baseActivityManager.selectEappMerchantAhareCodeActivityItemByActivityId(activityId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("counpons", eappMerchantAhareCodeActivityItems);

        return resultMap;
    }

    /**
     * 151.商户端运营方案代金券项查询
     * @return
     */
    @RequestMapping("appMerchantShareCodeActivityItemSelect.action")
    public Object appMerchantShareCodeActivityItemSelect(Integer eid,Integer activityId) {
        if(eid==null||activityId==null) {
            return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
        }
        List<Map<String, Object>> list = baseActivityManager.Proc_Backstage_appMerchant_share_code_activity_item_select(eid, activityId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("itemList", list);

        return resultMap;
    }

    @RequestMapping("wxGetCouponCode.action")
    @TokenNotVerify
    public void wxGetCouponCode(String appId,String scene_str,HttpServletResponse response){
        try {
            BufferedImage ImageOutput = WxCodeUtil.perpetualCodeFabrication(appId, scene_str);
            response.setContentType("image/png");
            ImageIO.write(ImageOutput, "png", response.getOutputStream());
        } catch (Exception e) {
            logger.error("生成二维码错误:{}",e);
        }
    }

    /**
     * 152.商户端运营方案代金券项赠送
     * @return
     */
    @RequestMapping("couponActivitySend.action")
    public Object couponActivitySend(Integer eid,Integer activityId,Integer accountId,Integer codeTypeId,String cellphone,String appId) {
        if(eid==null||activityId==null||accountId==null||codeTypeId==null) {
            return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
        }
        Map<String, Object> map = baseActivityManager.Proc_Backstage_appMerchant_share_code_activity_present_record_present(eid, activityId, accountId, codeTypeId, cellphone);
        if(!"1".equals(map.get("tag"))){
            throw BaseRuntimeException.jointCodeAndMessage(150,(String)map.get("tag"));
        }else{
            if(!StringUtils.isEmpty(appId)&&map.get("code")!=null&&!StringUtils.isEmpty((String)map.get("code"))){
                String imgUrl = UrlManage.getShuiqooMchantUrl()+"/evian/sqct/activity/wxGetCouponCode.action?appId="+appId+"&scene_str=appCouponsSend_"+map.get("code")+"EVIAN"+activityId+"EVIAN"+codeTypeId;
                map.put("imgUrl",imgUrl);
            }
            return map;
        }
    }


    /**
     * 153.app商户端优惠券赠送记录
     * @return
     */
    @RequestMapping("activityCouponsPresentRecord.action")
    public Map<String, Object> activityCouponsPresentRecord(String beginTime,String endTime,Integer activityId,Integer accountId,Integer eid,Integer codeTypeId,String code,Boolean isComplete,Integer clientId,String cellphone,String staffAccount,Boolean isSendCellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll) {
        Map<String, Object> map = baseActivityManager.Proc_Backstage_appMerchant_share_code_activity_present_record_select(beginTime, endTime, activityId, accountId, eid, codeTypeId, code, isComplete, clientId, cellphone, staffAccount,isSendCellphone, PageIndex, PageSize, IsSelectAll);
        return map;
    }
}
