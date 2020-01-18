package com.evian.sqct.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class CredentialFileDownload {
	
	private final static Logger logger = LoggerFactory.getLogger(CredentialFileDownload.class);

	/**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File file = new File(saveDir+File.separator+fileName);    
        FileOutputStream fos = new FileOutputStream(file);     
        fos.write(getData); 
        if(fos!=null){
            fos.close();  
        }
        if(inputStream!=null){
            inputStream.close();
        }

        logger.info(url+" download success");

    }
    

	/**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName) throws IOException{
    	String path = ResourceUtils.getURL("classpath:").getPath();
    	path = path.replace("%20"," ");
    	
        
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
    	File saveDir = new File(path+"/wechatMch/");
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File file = new File(saveDir+File.separator+fileName);    
        FileOutputStream fos = new FileOutputStream(file);     
        fos.write(getData); 
        if(fos!=null){
            fos.close();  
        }
        if(inputStream!=null){
            inputStream.close();
        }

        
        logger.info(url+" download success");

    }


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static BufferedImage  downLoadFromUrlGET(String urlStr,String fileName) throws IOException{
        String path = ResourceUtils.getURL("classpath:").getPath();
        path = path.replace("%20"," ");


        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(path+"/couponActivitySend/");
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        logger.info(url+" download success");
        return ImageIO.read(file);
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param params
     * @throws IOException
     */
    public static BufferedImage  downLoadFromUrlPOST(String urlStr,String outputStr,String fileName) throws IOException{
    	String path = ResourceUtils.getURL("classpath:").getPath();
    	path = path.replace("%20"," ");
    	
    	
    	URL url = new URL(urlStr);  
    	HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
    	//设置超时间为3秒
    	conn.setConnectTimeout(3*1000);
    	//防止屏蔽程序抓取而返回403错误
    	conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    	conn.setDoOutput(true);// 是否输入参数
    	// 提交模式
    	conn.setRequestMethod("POST");
    	// 当outputStr不为null时向输出流写数据
		if (null != outputStr) {
			OutputStream outputStream = conn.getOutputStream();
			// 注意编码格式
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}
		
    	//得到输入流
    	InputStream inputStream = conn.getInputStream();
    	//获取自己数组
    	byte[] getData = readInputStream(inputStream);    
    	
    	//文件保存位置
    	File saveDir = new File(path+"/couponActivitySend/");
    	if(!saveDir.exists()){
    		saveDir.mkdirs();
    	}
    	File file = new File(saveDir+File.separator+fileName);    
    	FileOutputStream fos = new FileOutputStream(file);     
    	fos.write(getData); 
    	if(fos!=null){
    		fos.close();  
    	}
    	if(inputStream!=null){
    		inputStream.close();
    	}
    	
    	
    	logger.info(url+" download success");
    	return ImageIO.read(file);
    }
    
    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param identification   标识为避免高并发时 生成文件名不重复
     * @param params
     * @throws IOException
     */
    public static BufferedImage  downLoadFromUrlPOST(String urlStr,String outputStr,Integer identification) throws IOException{
    	String path = ResourceUtils.getURL("classpath:").getPath();
    	path = path.replace("%20"," ");
    	
    	URL url = new URL(urlStr);  
    	HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
    	//设置超时间为3秒
    	conn.setConnectTimeout(3*1000);
    	//防止屏蔽程序抓取而返回403错误
    	conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    	conn.setDoOutput(true);// 是否输入参数
    	// 提交模式
    	conn.setRequestMethod("POST");
    	// 当outputStr不为null时向输出流写数据
    	if (null != outputStr) {
    		OutputStream outputStream = conn.getOutputStream();
    		// 注意编码格式
    		outputStream.write(outputStr.getBytes("UTF-8"));
    		outputStream.close();
    	}
    	
    	//得到输入流
    	InputStream inputStream = conn.getInputStream();
    	BufferedImage read = ImageIO.read(inputStream);
    	
    	// ImageIO.read支出的文件后缀可能不支持  就会返回null 如果返回null 将文件改成.png格式
    	if(read!=null) {
    		return read;
    	}
    	//获取自己数组
    	byte[] getData = readInputStream(inputStream);    
    	
    	//文件保存位置
    	File saveDir = new File(path+"/groupBuyXCXShare/");
    	if(!saveDir.exists()){
    		saveDir.mkdirs();
    	}
    	File file = new File(saveDir+File.separator+new Date().getTime()+".png");    
    	FileOutputStream fos = new FileOutputStream(file);     
    	fos.write(getData); 
    	if(fos!=null){
    		fos.close();  
    	}
    	if(inputStream!=null){
    		inputStream.close();
    	}
    	
    	read = ImageIO.read(file);
    	// 删除图片
    	file.delete();
    	logger.info(url+" download success");
    	return read;
    }

	/**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downBackstageApiLoadFromUrl(String urlStr,String fileName) throws IOException{
    	
    	ClassLoader classLoader = Thread.currentThread()  
    	        .getContextClassLoader();
    	if (classLoader == null) {  
    	    classLoader = ClassLoader.getSystemClassLoader();  
    	}
    	java.net.URL url2 = classLoader.getResource(""); 
    	String ROOT_CLASS_PATH = url2.getPath() + "/";  
    	File rootFile = new File(ROOT_CLASS_PATH);  
    	String WEB_INFO_DIRECTORY_PATH = rootFile.getParent() + "/";  
    	File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);  
    	String SERVLET_CONTEXT_PATH = webInfoDir.getParent();
    	
//        FileInputStream instream = new FileInputStream(new File(ResourceUtils.getURL("classpath:").getPath()+"/mchCertificate/"+mch_id+".p12"));
    	SERVLET_CONTEXT_PATH = SERVLET_CONTEXT_PATH.replace("%20"," ");
    	String savePath =SERVLET_CONTEXT_PATH+"/mchCertificate/";
//        FileInputStream instream = new FileInputStream(new File(SERVLET_CONTEXT_PATH+"/mchCertificate/"+fileName));
        
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File file = new File(saveDir+File.separator+fileName);    
        FileOutputStream fos = new FileOutputStream(file);     
        fos.write(getData); 
        if(fos!=null){
            fos.close();  
        }
        if(inputStream!=null){
            inputStream.close();
        }

        
        logger.info(url+" download success");

    }
    
    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        return bos.toByteArray();  
    }
    
    /**
     * 将字符串转为文件
     * @param res
     * @param filePath
     * @return
     */
    public static boolean string2File(String res, String filePath) { 
        boolean flag = true; 
        BufferedReader bufferedReader = null; 
        BufferedWriter bufferedWriter = null; 
        
        ClassLoader classLoader = Thread.currentThread()  
    	        .getContextClassLoader();
    	if (classLoader == null) {  
    	    classLoader = ClassLoader.getSystemClassLoader();  
    	}
    	java.net.URL url2 = classLoader.getResource(""); 
    	String ROOT_CLASS_PATH = url2.getPath() + "/";  
    	File rootFile = new File(ROOT_CLASS_PATH);  
    	String WEB_INFO_DIRECTORY_PATH = rootFile.getParent() + "/";  
    	File webInfoDir = new File(WEB_INFO_DIRECTORY_PATH);  
    	String SERVLET_CONTEXT_PATH = webInfoDir.getParent();
    	
//        FileInputStream instream = new FileInputStream(new File(ResourceUtils.getURL("classpath:").getPath()+"/mchCertificate/"+mch_id+".p12"));
    	SERVLET_CONTEXT_PATH = SERVLET_CONTEXT_PATH.replace("%20"," ");
    	String savePath =SERVLET_CONTEXT_PATH+"/mchCertificate/";
        
        try { 
                File distFile = new File(savePath+filePath); 
                if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs(); 
                bufferedReader = new BufferedReader(new StringReader(res)); 
                FileOutputStream writerStream = new FileOutputStream(distFile);    
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(writerStream,"UTF-8"));
                char buf[] = new char[1024];         //字符缓冲区 
                int len; 
                while ((len = bufferedReader.read(buf)) != -1) { 
                        bufferedWriter.write(buf, 0, len); 
                } 
                bufferedWriter.flush(); 
                bufferedReader.close(); 
                bufferedWriter.close(); 
        } catch (IOException e) { 
                e.printStackTrace(); 
                flag = false; 
                return flag; 
        } finally { 
                if (bufferedReader != null) { 
                        try { 
                                bufferedReader.close(); 
                        } catch (IOException e) { 
                                e.printStackTrace(); 
                        } 
                } 
        } 
        return flag; 
}
}
