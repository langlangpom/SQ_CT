package com.evian.sqct.api.action;

import com.evian.sqct.annotation.TokenNotVerify;
import com.evian.sqct.api.BaseAction;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.ISystemService;
import com.evian.sqct.util.CallBackPar;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:SystemAction
 * Package:com.evian.sqct.api.action
 * Description:切换参数action
 *
 * @Date:2020/6/9 11:06
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/system")
public class SystemAction extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISystemService systemService;

    /**
     * 存储过程入参缓存开关切换
     * @return
     */
    @TokenNotVerify
    @RequestMapping("updateStoredProcedureCacheSwitch")
    public Map<String,Object> updateStoredProcedureCacheSwitch(){
        Map<String,Object> map = new HashMap<>();
        boolean cacheSwitch = systemService.updateStoredProcedureCacheSwitch();
        map.put("cacheSwitch",cacheSwitch);
        logger.error("存储过程入参缓存开关切换:{}",cacheSwitch?"开":"关");
        return map;
    }


    /**
     * 存储过程入参缓存切换
     * @return
     */
    @TokenNotVerify
    @RequestMapping("updateStoredProcedureCacheState")
    public Object updateStoredProcedureCacheState(String state){
        if(state==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        boolean cacheSwitch = systemService.updateStoredProcedureCacheState(state);
        logger.error("存储过程入参缓存切换:{}",state);
        JSONObject map = new JSONObject();
        JSONObject parJSON = CallBackPar.getParJSON();
        parJSON.put("data",map);
        parJSON.put("code",567);
        map.put("cacheSwitch",cacheSwitch);

        return parJSON.toString();
    }


    /**
     * 存储过程入参缓存切换
     * @return
     */
    @TokenNotVerify
    @RequestMapping("test")
    public ResultVO test(String state){
        return new ResultVO();
    }
}
