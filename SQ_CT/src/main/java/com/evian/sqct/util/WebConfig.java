/**
 * 
 */
package com.evian.sqct.util;

/**
 * @FileName: WebConfig.java
 * @Package com.evian.mobile.util
 * @Description: Web配置参数
 * @author EVIAN(PA)
 * @date 2016年3月28日 下午1:16:23
 * @version V3.0
 */
public class WebConfig {
	public static int port; // 监听端口
	public static String projectName = ""; // 项目名称
	public static int timer = 18000; // 轮询间隔时间
	public static String[] EvianURLs = new String[] {
			"http://www.haoshui.com.cn", "http://www.h2odaojia.cn" };
	public static String LBSJieXuUrl = ""; // 坐标解析地址
	public static String company = ""; // 公司名称
	public static String releaseDate = ""; // 发布时间
	public static String version = ""; // APP发布版本
	public static String downUrl = ""; // 下载地址
	public static String TcpKey = ""; // TCP通信Key
	public static boolean PushData=true; //获取推送数据
	public static boolean signSwitch=false;// 签名开关
}
