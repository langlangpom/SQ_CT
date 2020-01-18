package com.evian.sqct.api.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.service.BaseVendorManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;

/**
 * ClassName:UploadingAction
 * Package:com.evian.sqct.api.action
 * Description:上传app版本
 *
 * @Date:2020/1/3 11:43
 * @Author:XHX
 */
@RestController
@RequestMapping("/uploading")
public class UploadingAction  extends BaseAction {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseVendorManager baseVendorManager;

    private static final String  USERNAME = "admin";
    private static final String  PASSWORD = "753951852456";

    @RequestMapping("login")
    public Map<String,Object> login(String userName,String password){
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(userName==null||password==null) {
            setERROR_PARAM(parMap);
            return parMap;
        }
        // 登录失败
        if(!USERNAME.equals(userName)||!PASSWORD.equals(password)){
            setERROR_BY_CODE(parMap, Constants.CODE_FAIL_LOGIN);
        }


        return parMap;
    }

    @RequestMapping(value = "/multiImport", method = RequestMethod.POST)
    public Map<String,Object> multiImport(@RequestParam("txt_file") MultipartFile uploadFile,String userName,String password,HttpServletRequest request) throws IOException{
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(userName==null||password==null) {
            setERROR_PARAM(parMap);
            return parMap;
        }
        // 登录失败
        if(!USERNAME.equals(userName)||!PASSWORD.equals(password)){
            setERROR_BY_CODE(parMap, Constants.CODE_FAIL_LOGIN);
            return parMap;
        }
        String fileName = uploadFile.getOriginalFilename();
        logger.info("[上传文件:{}] [ip:{}]",new Object[] {fileName,getIp(request)});
        byte[] fileBytes = uploadFile.getBytes();
        
        String resource = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        resource = resource.replace("%20"," ");
        StringBuilder path = new StringBuilder(resource);
        StringBuilder apk = path.append("/static/apk");
        logger.info("[apk:{}]",new Object[] {apk});
        File apkSite = new File(apk.toString());
        if(!apkSite.exists()) {
        	apkSite.mkdirs();
        }
        apk.append("/");
        StringBuilder newFileSite = apk.append(fileName);
        File newFile = new File(newFileSite.toString());
        FileOutputStream fos = new FileOutputStream(newFile);
		fos.write(fileBytes);
		fos.flush();
		if(fos!=null){
            fos.close();
        }
        
        return parMap;
    }
    
    @RequestMapping("setVersions")
    public Map<String,Object> setVersions(String versionInt,String versionName,String userName,String password){
    	Map<String, Object> parMap = CallBackPar.getParMap();
    	if(versionInt==null||versionName==null||userName==null||password==null) {
            setERROR_PARAM(parMap);
            return parMap;
        }
    	// 登录失败
        if(!USERNAME.equals(userName)||!PASSWORD.equals(password)){
            setERROR_BY_CODE(parMap, Constants.CODE_FAIL_LOGIN);
            return parMap;
        }
    	baseVendorManager.setAppVersion(versionInt, versionName);
    	return parMap;
    	
    }



}
