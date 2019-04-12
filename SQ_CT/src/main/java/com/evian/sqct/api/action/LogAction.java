package com.evian.sqct.api.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.evian.sqct.util.LogAspect;


/**
 * @date   2018年9月29日 上午11:50:10
 * @author XHX
 * @Description 该函数的功能描述
 */
@Controller
@RequestMapping("/log")
public class LogAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/** 数据接口返回值打印日志开关 vm */
	@RequestMapping(value = "/logAroundSwitch", method = RequestMethod.GET)
	public void logAroundSwitch(HttpServletResponse response) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			LogAspect.logAroundSwitch = !LogAspect.logAroundSwitch;
			PrintWriter writer = response.getWriter();
			
			if(LogAspect.logAroundSwitch){
				logger.info("开启接口返回值日志开关");
				writer.print("返回值开关已开启");
			}else{
				logger.info("关闭接口返回值日志开关");
				writer.print("返回值开关已关闭");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	private String  dateZhuanhuan(){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String format = sf.format(new Date());
		
		return format;
	}
	
	@RequestMapping(value = "{format}/{data}", method = RequestMethod.GET)
	public void showLog(@PathVariable(value = "format") String format,@PathVariable(value = "data") String data,HttpServletResponse response){
		if(!dateZhuanhuan().equals(format)){
			logger.info("1"+format);
			return ;
		}
		if(StringUtils.isEmpty(data)){
			logger.info("2"+data);
			return ;
		}
		String filePath = "";
		String path = "D://data//logs//sqct//";
		if(data.indexOf("info")!=-1){
			data = data.replace("info", "");
			filePath = path+"info."+data+".log";
		}else if(data.indexOf("error")!=-1){
			data = data.replace("error", "");
			filePath = path+"error.log."+data;
		}else{
			logger.info("3"+data);
			return ;
		}
		File file = new File(filePath);
		if(!file.exists()){
			logger.info("4"+file);
			return ;
		}
		
		FileReader fr = null;
		try {
			fr = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		BufferedReader br = new BufferedReader(fr);
		StringBuffer sbf = new StringBuffer();
		String s = "";
		try {
			while((s = br.readLine())!=null){
				sbf.append(s+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return ;
		}
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(sbf.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}finally{
			try {
				if(fr!=null){
					fr.close();
				}
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
			}
		}
		return ;
	}
}
