package com.evian.sqct.api.action;

import com.evian.sqct.annotation.DataNotLogCheck;
import com.evian.sqct.annotation.ResponseNotAdvice;
import com.evian.sqct.annotation.TokenNotVerify;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.order.TuiyaPayDTO;
import com.evian.sqct.bean.pay.PayOnDeliveryByWeCathReqDTO;
import com.evian.sqct.bean.pay.SddPayInputDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BasePayManager;
import com.evian.sqct.util.code.AddPri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:PayAction
 * Package:com.evian.sqct.api.action
 * Description:支付action
 *
 * @Date:2019/12/18 14:27
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/pay")
public class PayAction extends BaseAction{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BasePayManager basePayManager;

    @ResponseNotAdvice
    @RequestMapping("notifyUrl")
    @DataNotLogCheck
    @TokenNotVerify
    public String showOrderData(HttpServletRequest request){
        try {
            return basePayManager.saveNotifyUrlOrder(request);
        } catch (Exception e) {
            logger.error("{}",e);
            return "SUCCESS";
        }
    }

    /**
     * 168.查询微信订单
     * @param group
     * @param appId
     * @return
     */
    @RequestMapping("orderquery.action")
    public Object orderquery(String group,String appId){
        if(group==null||appId==null) {
            return new ResultVO<>(ResultCode.CODE_ERROR_PARAM);
        }
        Map<String, Object> orderquery = basePayManager.orderquery(group, appId);
        Map<String, Object> data = new HashMap<>();
        data.put("orderquery",orderquery);
        return  data;
    }

    /**
     * 200.退押支付
     * @param group
     * @param appId
     * @return
     */
    @RequestMapping("tuiyaPay.action")
    public ResultVO tuiyaPay(@Valid TuiyaPayDTO dto) throws Exception {
        String tag = basePayManager.tuiyaPay(dto);
        if(!"1".equals(tag)){
            throw new ResultException(tag);
        }
        return  new ResultVO();
    }

    @RequestMapping("wxPayCodeByCodeUrl")
    @TokenNotVerify
    public void wxPayCodeByCodeUrl(String code_url, HttpServletResponse response){
        try {
            String decode = URLDecoder.decode(code_url, "UTF-8");
            BufferedImage ImageOutput = AddPri.generateQRCodeOutput(decode, 250, 250, "png");
            response.setContentType("image/png");
            ImageIO.write(ImageOutput, "png", response.getOutputStream());
        } catch (Exception e) {
            logger.error("生成二维码错误:{}",e);
        }
    }

    /**
     * 206.代客支付
     * @param dto
     * @return
     */
    @RequestMapping("payOnDeliveryByWeCath")
    public String payOnDeliveryByWeCath(@Valid PayOnDeliveryByWeCathReqDTO dto){
        return basePayManager.payOnDeliveryByWeCath(dto);
    }

    /**
     * 216.水叮咚提现
     * @param group
     * @param appId
     * @return
     */
    @RequestMapping("SDDPay.action")
    public ResultVO SDDPay(@Valid SddPayInputDTO dto) throws Exception {
        String tag = basePayManager.SDDPay(dto);
        if(!"1".equals(tag)){
            throw new ResultException(tag);
        }
        return  new ResultVO();
    }
}
