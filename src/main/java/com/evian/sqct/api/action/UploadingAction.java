package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseVendorManager;
import com.evian.sqct.service.IFileUploadService;
import com.evian.sqct.util.CallBackPar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	@Autowired
    private IFileUploadService fileUploadService;

    private static final String  USERNAME = "admin";
    private static final String  PASSWORD = "753951852456";

    @RequestMapping("login")
    public ResultVO login(String userName, String password){
        if(userName==null||password==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        // 登录失败
        if(!USERNAME.equals(userName)||!PASSWORD.equals(password)){
            return new ResultVO(ResultCode.CODE_FAIL_LOGIN);
        }

        return new ResultVO();
    }

    @RequestMapping(value = "/multiImport", method = RequestMethod.POST)
    public ResultVO multiImport(@RequestParam("txt_file") MultipartFile uploadFile,String userName,String password,HttpServletRequest request) throws IOException{
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(userName==null||password==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        // 登录失败
        if(!USERNAME.equals(userName)||!PASSWORD.equals(password)){
            return new ResultVO(ResultCode.CODE_FAIL_LOGIN);
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
        
        return new ResultVO();
    }
    
    @RequestMapping("setVersions")
    public ResultVO setVersions(String versionInt,String versionName,String userName,String downloadUrl,String describe,String password){
    	if(versionInt==null||versionName==null||userName==null||password==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
    	// 登录失败
        if(!USERNAME.equals(userName)||!PASSWORD.equals(password)){
            return new ResultVO(ResultCode.CODE_FAIL_LOGIN);
        }
    	baseVendorManager.setAppVersion(versionInt, versionName,downloadUrl,describe);
    	return new ResultVO();
    }

    @RequestMapping("uploadingImgQiniu")
    public ResultVO uploadingImgQiniu(@RequestParam("images") MultipartFile... images) throws IOException {
        StringBuilder pictures = new StringBuilder();
        for (int i = 0; i <images.length ; i++) {
            String s = fileUploadService.fileUpload(images[i].getBytes());
            pictures.append(s);
            if(i!=(images.length-1)){
                pictures.append(",");
            }
        }
        Map<String,String> result = new HashMap<>();
        result.put("imgUrls",pictures.toString());
        return new ResultVO(result);
    }

    @RequestMapping("uploadingFilesQiniu")
    public ResultVO uploadingFilesQiniu(@RequestParam("files") MultipartFile... files) throws IOException {
        StringBuilder pictures = new StringBuilder();
        for (int i = 0; i <files.length ; i++) {
            String originalFilename = files[i].getOriginalFilename();
            int lastIndex = originalFilename.lastIndexOf(".");
            if(lastIndex==-1){
                throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_FILE_NOT_SUFFIX);
            }
            String suffix = originalFilename.substring(lastIndex + 1);
            String s = fileUploadService.fileUpload(files[i].getBytes(),suffix);
            pictures.append(s);
            if(i!=(files.length-1)){
                pictures.append(",");
            }
        }
        Map<String,String> result = new HashMap<>();
        result.put("urls",pictures.toString());
        return new ResultVO(result);
    }



}
