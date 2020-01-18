/**
 * 
 */
package com.evian.sqct.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @FileName: QiuniuConfig.java
 * @Package com.evian.mobile.util
 * @Description: TODO
 * @author EVIAN(PA)
 * @date 2016年4月14日 下午6:20:13
 * @version V3.0
 */
public class QiniuConfig {
//	private static final Logger logger = LoggerFactory.getLogger(QiniuFileSystemUtil.class);
//	private static QiniuConfig instance = new QiniuConfig();
	public static String namespace = "";
	public static String accessKey = "";
	public static String secretKey = "";
	public static String bucket = "";
	/*public static QiniuConfig getInstance() {
		return instance;
	}*/

	/*public void loadConfig() {
		Properties ppt = new Properties();
		try {
			ppt.load(getClass().getResourceAsStream("/qiniu2.properties"));
			namespace = ppt.getProperty("qiniu.namespace", "");
			accessKey = ppt.getProperty("qiniu.accessKey", "");
			secretKey = ppt.getProperty("qiniu.secretKey", "");
			bucket = ppt.getProperty("qiniu.bucket", "");
		} catch (Exception e) {
			logger.error(
					"[project:{}] [module:QiniuConfig] [method:QiniuConfig] [exception:{}]",
					new Object[] { WebConfig.projectName, e });
		}
	}*/
}
