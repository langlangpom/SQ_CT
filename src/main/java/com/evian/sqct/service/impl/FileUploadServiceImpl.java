package com.evian.sqct.service.impl;

import com.alibaba.fastjson.JSON;
import com.evian.sqct.annotation.LogNotCheck;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.service.IFileUploadService;
import com.evian.sqct.util.QiniuConfig;
import com.evian.sqct.util.QiniuFileSystemUtil;
import com.qiniu.common.QiniuException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * ClassName:IFileUploadServiceImpl
 * Package:com.evian.sqct.service.impl
 * Description:请为该功能做描述
 *
 * @Date:2020/9/22 15:42
 * @Author:XHX
 */
@Service
public class FileUploadServiceImpl implements IFileUploadService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @LogNotCheck
    public String fileUpload(byte[] bytes) {
        try {
            String uploadPic = QiniuFileSystemUtil.uploadPic(bytes);
            StringBuilder recordImage = new StringBuilder();
            if (!StringUtils.isEmpty(uploadPic)) {
                com.alibaba.fastjson.JSONObject ject = JSON.parseObject(uploadPic);
                if (!StringUtils.isEmpty((String) ject.get("hash"))
                        && !StringUtils.isEmpty((String) ject.get("key"))) {

                    StringBuilder namespace = new StringBuilder(QiniuConfig.namespace);
                    namespace.append(ject.getString("key"));
                    recordImage.append(namespace.toString());
                    return recordImage.toString();
                }
            }
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_SYSTEM);
        } catch (QiniuException e) {
            logger.error("{}",e);
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_SYSTEM);
        }
    }

    @Override
    @LogNotCheck
    public String fileUpload(byte[] bytes,String suffix) {
        try {
            String uploadPic = QiniuFileSystemUtil.uploadPic(bytes,suffix);
            StringBuilder recordImage = new StringBuilder();
            if (!StringUtils.isEmpty(uploadPic)) {
                com.alibaba.fastjson.JSONObject ject = JSON.parseObject(uploadPic);
                if (!StringUtils.isEmpty((String) ject.get("hash"))
                        && !StringUtils.isEmpty((String) ject.get("key"))) {

                    StringBuilder namespace = new StringBuilder(QiniuConfig.namespace);
                    namespace.append(ject.getString("key"));
                    recordImage.append(namespace.toString());
                    return recordImage.toString();
                }
            }
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_SYSTEM);
        } catch (QiniuException e) {
            logger.error("{}",e);
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_SYSTEM);
        }
    }


}
