package com.evian.sqct.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.app.APPUpgrader;
import com.evian.sqct.bean.app.APPUpgraderSelectObject;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.bean.vendor.VendorMainboardContainer;
import com.evian.sqct.service.BaseVendorManager;

/**
 * @date   2018年12月22日 下午2:08:20
 * @author XHX
 * @Description 定时检查售货机运行情况和定时检查app版本号
 */
@Component
public class taskExamineVendorOperationCondition {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static Map<String, String> sendList = new HashMap<String, String>();

	@Autowired
	BaseVendorManager vendorManager;
	 /**
     * 第一次延迟1秒后执行，之后按fixedRate的规则每十分钟执行一次
     */
    @Scheduled(initialDelay=1000, fixedRate=600000)
    public void logTime(){
    	if(!UrlManage.getInteceptorSwitch()) {
    		return;
    	}
        String vendorList = vendorManager.allOnLineMainboards();
        JSONObject vendors = JSONObject.parseObject(vendorList);
        String code = vendors.getString("code");
        if(!"1".equals(code)) {
        	logger.error("检查售货机是否正常运行定时任务，未执行！彭安接口message：{}"+vendors.getString("message"));
        	return;
        }
        JSONObject data = vendors.getJSONObject("data");
        Integer lineCount = data.getInteger("lineCount");
        logger.info("轮询开始");
        if(lineCount.intValue()<=0) {// 售货机数量<0
        	logger.error("[vendorList:{}]",vendorList);
        }
        JSONArray lineList = data.getJSONArray("lineList");
        
        List<VendorMainboardContainer> mainBoardList = vendorManager.selectAllMainboardContainer();
        logger.info("[mainBoardList:{}]",mainBoardList);
        for (VendorMainboardContainer vmc : mainBoardList) {
        	
        	// 判断离线的售货机 是否已经重新修复  如果已经修复 那就删除发送的缓存
        	
        	
        	// 该售货机已经激活														该售货机已经报损
			if(vmc.getContainerStatus()!=null&&vmc.getContainerStatus().intValue()!=0&&vmc.getContainerStatus().intValue()!=3) {
				/*String ret = sendList.get(vmc.getMainboardNo());
				if(!StringUtils.isEmpty(ret)) {
					continue;
				}*/
				
				if(lineList.size()<1) {
					
					// 发送通知
					sendInform(vmc);
					
				}else {
				
					for (int i = 0; i < lineList.size(); i++) {
						String mainboardNo = lineList.getString(i);
						
						if(vmc.getMainboardNo()!=null&&vmc.getMainboardNo().equals(mainboardNo)) {
							break;
						}
						if(i==lineList.size()-1) {
							
							// 发送通知
							sendInform(vmc);
							
						}
						
						
					}
				
				}
				
			}
		}
        
    }
    
    /**
     * 发送通知
     * @param vmc
     */
    private void sendInform(VendorMainboardContainer vmc) {
    	Map<String, Object> err_log = vendorManager.vendorErrorLogOperat(vmc.getEid(), vmc.getMainboardId(), 3, "轮循检测到离线", "0", null);
		logger.info("[err_log:{}]",err_log);
		if(err_log != null) {
			if(err_log.get("regeditId")==null) {
				logger.info(vmc.getMainboardNo()+" "+vmc.getMainboardId()+" 该货柜没有极光账号，无法发送！");
				return;
			}
			String sendJpushMessage = vendorManager.sendJpushMessage((Integer)err_log.get("logId"), 2, "", vmc.getShopContainerName()+" "+vmc.getMainboardNo()+"号:服务端检测到异常离线！", 0, err_log.get("regeditId").toString(), "");
			sendList.put(vmc.getMainboardNo(), "1");
			logger.info(vmc.getMainboardNo()+"号货柜已离线，发送通知:{}",sendJpushMessage);
		}
    }
    
    /**
     * 检查更新app版本号
     * 第一次延迟1秒后执行，之后按fixedRate的规则每30执行一次
     */
    @Scheduled(initialDelay=1000, fixedRate=30000)
    public void inspectUpdateAppVersion() {
    	APPUpgraderSelectObject appVersion = vendorManager.getAppVersion();
    	String versionName = appVersion.getVersionName();
    	String versionInt = appVersion.getVersionInt();
    	String downloadUrl = appVersion.getDownloadUrl();
    	String describe = appVersion.getDescribe();
    	if(!StringUtils.isEmpty(versionInt)) {
    		APPUpgrader.setVersionInt(versionInt);
    	}
    	if(!StringUtils.isEmpty(versionName)) {
    		APPUpgrader.setVersionName(versionName);
    	}
    	if(!StringUtils.isEmpty(downloadUrl)) {
    		APPUpgrader.setDownloadUrl(downloadUrl);
    	}
    	if(!StringUtils.isEmpty(describe)) {
    		APPUpgrader.setDescribe(describe);
    	}else {
    		APPUpgrader.setDescribe("");
    	}
    }

}
