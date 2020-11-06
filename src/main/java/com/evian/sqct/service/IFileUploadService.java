package com.evian.sqct.service;

/**
 * ClassName:IFileUploadService
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2020/9/22 15:41
 * @Author:XHX
 */
public interface IFileUploadService {
    String fileUpload(byte[] bytes);

    String fileUpload(byte[] bytes,String suffix);
}
