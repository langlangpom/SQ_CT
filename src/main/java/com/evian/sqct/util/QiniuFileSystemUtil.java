/**
 * 
 */
package com.evian.sqct.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @FileName: QiniuFileSystemUtil.java
 * @Package com.evian.mobile.util
 * @Description: TODO
 * @author EVIAN(PA)
 * @date 2016年4月14日 下午6:09:15
 * @version V3.0
 */
public class QiniuFileSystemUtil {
	/**
	 * 上传客户头像
	 * 
	 * @param imageTxt
	 * @return
	 */
	public static String uploadPhotos(File imgFile, int zyid)
			throws QiniuException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String time = String.format("%s-%s", date, System.currentTimeMillis());
		String filePath = String.format(
				"upload/los/photos/images/%s/%s-%d.jpg", date, time, zyid);
		UploadManager uploadManager = new UploadManager();
		Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
		String token = auth.uploadToken(QiniuConfig.bucket);
		Response r = uploadManager.put(imgFile, filePath, token);
		return r.bodyString();
	}

	/**
	 * 上传签到图片
	 */
	public static String uploadSignPicture(File imgFile, int id)
			throws QiniuException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String time = String.format("%s-%s", date, System.currentTimeMillis());
		String filePath = String.format("upload/los/sign/images/%s/%s-%d.jpg",
				date, time, id);
		UploadManager uploadManager = new UploadManager();
		Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
		String token = auth.uploadToken(QiniuConfig.bucket);
		Response r = uploadManager.put(imgFile, filePath, token);
		return r.bodyString();
	}
	
	/**
	 * 上传单据签名图片
	 */
	public static String uploadOrderSignPicture(File imgFile, int id)
			throws QiniuException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String time = String.format("%s-%s", date, System.currentTimeMillis());
		String filePath = String.format("upload/los/orderSign/images/%s/%s-%d.jpg",
				date, time, id);
		UploadManager uploadManager = new UploadManager();
		Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
		String token = auth.uploadToken(QiniuConfig.bucket);
		Response r = uploadManager.put(imgFile, filePath, token);
		return r.bodyString();
	}
	
	/**
	 * 备份数据
	 */
	public static String uploadbackUpDB(File imgFile, int id)
			throws QiniuException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String time = String.format("%s-%s", date, System.currentTimeMillis());
		String filePath = String.format("upload/los/backup/db/%s/%s-%d.db3",
				date, time, id);
		UploadManager uploadManager = new UploadManager();
		Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
		String token = auth.uploadToken(QiniuConfig.bucket);
		Response r = uploadManager.put(imgFile, filePath, token);
		return r.bodyString();
	}
	
	/**
     * 上传用户分享图片
     *
     * @param imageTxt
     * @return {"hash":"Fpntdbb7yTG1yWMhikxPjgwBfHFw","key":"Upload/Client/Shear/images/20170330/201703301490870434207.png"}
     */
    public static String uploadShearPic(String file) throws QiniuException {
    	
    	/*String prefix=file.substring(file.lastIndexOf(".")+1);
    	
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String time = String.format("%s%s", date, System.currentTimeMillis());
        String filePath = String.format("Upload/Client/Shear/images/%s/%s."+prefix, date, time);
        UploadManager uploadManager = new UploadManager();
        Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
        String token = auth.uploadToken(QiniuConfig.bucket);
        Response r = uploadManager.put(file, filePath, token);
        return r.bodyString();*/
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String time = String.format("%s%s", date, System.currentTimeMillis());
        String filePath = String.format("Upload/Client/photos/images/%s/%s.jpg", date, time);
        UploadManager uploadManager = new UploadManager();
        Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
        String token = auth.uploadToken(QiniuConfig.bucket);
        Response r = uploadManager.put(Base64.decode(file), filePath, token);
        return r.bodyString();
    }

	/**
	 * 上传图片
	 *
	 * @param imageTxt
	 * @return {"hash":"Fpntdbb7yTG1yWMhikxPjgwBfHFw","key":"Upload/Client/Shear/images/20170330/201703301490870434207.png"}
	 */
	public static String uploadPic(byte[] file) throws QiniuException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String time = String.format("%s%s", date, System.currentTimeMillis());
		String filePath = String.format("Upload/Client/photos/images/%s/%s.jpg", date, time);
		UploadManager uploadManager = new UploadManager();
		Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
		String token = auth.uploadToken(QiniuConfig.bucket);
		Response r = uploadManager.put(file, filePath, token);
		return r.bodyString();
	}

	/**
	 * 上传图片
	 *
	 * @param imageTxt
	 * @return {"hash":"Fpntdbb7yTG1yWMhikxPjgwBfHFw","key":"Upload/Client/Shear/images/20170330/201703301490870434207.png"}
	 */
	public static String uploadPic(byte[] file,String suffix) throws QiniuException {
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String time = String.format("%s%s", date, System.currentTimeMillis());
		String filePath = String.format(("Upload/Client/photos/images/%s/%s."+suffix), date, time);
		UploadManager uploadManager = new UploadManager();
		Auth auth = Auth.create(QiniuConfig.accessKey, QiniuConfig.secretKey);
		String token = auth.uploadToken(QiniuConfig.bucket);
		Response r = uploadManager.put(file, filePath, token);
		return r.bodyString();
	}
}
