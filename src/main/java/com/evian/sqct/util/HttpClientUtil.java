package com.evian.sqct.util;

import com.evian.sqct.wxHB.ClientCustomSSL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



  
public class HttpClientUtil {  
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static final String CHARSET = "utf-8";
  
    public static String doGet(String url, Map<String, String> param) {  
  
        // 创建Httpclient对象  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
  
        String resultString = "";  
        CloseableHttpResponse response = null;  
        try {  
            // 创建uri  
            URIBuilder builder = new URIBuilder(url);  
            if (param != null) {  
                for (String key : param.keySet()) {  
                    builder.addParameter(key, param.get(key));  
                }  
            }  
            URI uri = builder.build();  
  
            // 创建http GET请求  
            HttpGet httpGet = new HttpGet(uri);  
  
            // 执行请求  
            response = httpclient.execute(httpGet);  
            // 判断返回状态是否为200  
            if (response.getStatusLine().getStatusCode() == 200) {  
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (response != null) {  
                    response.close();  
                }  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return resultString;  
    }  
  
    public static String doGet(String url) {  
        return doGet(url, null);  
    }  
  
    public static String doPost(String url, Map<String, String> param) {  
        // 创建Httpclient对象  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        CloseableHttpResponse response = null;  
        String resultString = "";  
        try {  
            // 创建Http Post请求  
            HttpPost httpPost = new HttpPost(url);  
            // 创建参数列表  
            if (param != null) {  
                List<NameValuePair> paramList = new ArrayList<>();  
                for (String key : param.keySet()) {  
                    paramList.add(new BasicNameValuePair(key, param.get(key)));  
                }  
                // 模拟表单  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");  
                httpPost.setEntity(entity);  
                
                // 打印请求参数信息
                /*HttpEntity entity2 = httpPost.getEntity();
                String string = EntityUtils.toString(entity2,"utf-8");
                logger.info("--------------entity2 = "+entity2);*/
            }  
            // 执行http请求  
            response = httpClient.execute(httpPost);  
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
            	if(response!=null){
            		response.close();  
            	}
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                logger.error("关闭response失败");
            }  
        }  
  
        return resultString;  
    }


  
    public static String doPost(String url) {  
        return doPost(url, null);  
    }  
      
    public static String doPostJson(String url, String json) {  
        // 创建Httpclient对象  
        CloseableHttpClient httpClient = HttpClients.createDefault();  
        CloseableHttpResponse response = null;  
        String resultString = "";  
        try {  
            // 创建Http Post请求  
            HttpPost httpPost = new HttpPost(url);  
            // 创建请求内容  
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);  
            httpPost.setEntity(entity);  
            // 执行http请求  
            response = httpClient.execute(httpPost);  
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                response.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
  
        return resultString;  
    }  
    
    /**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String post(String requestUrl, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			//TrustManager[] tm = { new MyX509TrustManager() };
			//SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			//sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			//SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			//conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			logger.info("连接URL: "+requestUrl+" 超时：{}", ce);
		} catch (Exception e) {
			logger.info("连接URL: "+requestUrl+" 异常：{}", e);
		}
		return null;
	}
	
	/**
	 * Post请求后台接口
	 * 
	 * @param contextContext对象
	 * @param mothodName方法名称
	 * @param params参数集合(无参数 传值:null)
	 * @return 消息(String/JSON格式)
	 */
	public static String postBackstageApi(String url, List<BasicNameValuePair> params) {
		String msg = null;
		
		// 从1970年1月1日到当前时间的毫秒差并转为String
		String timestamp = String.valueOf(System.currentTimeMillis());
		
		params.add(new BasicNameValuePair("timestamp", timestamp));
		// md5加密 毫秒差+证书key
		params.add(new BasicNameValuePair("sign", EvianSignatureUtil.getSignature1(params)));
		
		logger.info("访问后台接口：[url:{}]]", new Object[] {url+"?"+basicNameValuePairToString(params)});
		
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
		// 创建http post
		HttpPost httppost = new HttpPost(url);
		httppost.setConfig(requestConfig);
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(params, CHARSET);
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						msg = EntityUtils.toString(entity, CHARSET);
					}
				}else{
					logger.error("{},[url:{}], [参数1:{}]", new Object[] {"访问接口错误，返回状态："+response.getStatusLine().getStatusCode(), url, basicNameValuePairToString(params)});
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("[url:{}], [参数:{}],[exception:{}]", new Object[] { url, basicNameValuePairToString(params), e });
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (Exception e) {
				
			}
		}
		return msg;
	}
	
	private static String basicNameValuePairToString(List<BasicNameValuePair> list){
		if(list == null || list.size() == 0){return "无参数";}
		String result = "";
		for(BasicNameValuePair cur: list){
			result += cur.getName() + "=" + cur.getValue() + "&";
		}
		return result;
	}
	
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 * @throws Exception 
	 */
	public static String sslPost(String requestUrl, String outputStr,String mch_id) throws Exception {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			//TrustManager[] tm = { new MyX509TrustManager() };
			//SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			//sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			//SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			ClientCustomSSL a = new ClientCustomSSL();
//			SSLConnectionSocketFactory ssl = a.getSsl();
			SSLContext ssl = a.getSsl(mch_id);
			conn.setSSLSocketFactory(ssl.getSocketFactory());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
	}

	
	public static String getRequestIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
		return ip;
	}

	public static String UploadFile(String urlString,String filePath) throws Exception{
		//返回结果
		String result=null;
		File file=new File(filePath);
		if(!file.exists()||!file.isFile()){
			throw new IOException("文件不存在");
		}
		URL url=new URL(urlString);
		HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");//以POST方式提交表单
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);//POST方式不能使用缓存
		//设置请求头信息
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		//设置边界
		String BOUNDARY="----------"+System.currentTimeMillis();
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		//请求正文信息
		//第一部分
		StringBuilder sb=new StringBuilder();
		sb.append("--");//必须多两条道
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName()+"\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		System.out.println("sb:"+sb);

		//获得输出流
		OutputStream out=new DataOutputStream(conn.getOutputStream());
		//输出表头
		out.write(sb.toString().getBytes("UTF-8"));
		//文件正文部分
		//把文件以流的方式 推送道URL中
		DataInputStream din=new DataInputStream(new FileInputStream(file));
		int bytes=0;
		byte[] buffer=new byte[1024];
		while((bytes=din.read(buffer))!=-1){
			out.write(buffer,0,bytes);
		}
		din.close();
		//结尾部分
		byte[] foot=("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");//定义数据最后分割线
		out.write(foot);
		out.flush();
		out.close();
		if(HttpsURLConnection.HTTP_OK==conn.getResponseCode()){

			StringBuffer strbuffer=null;
			BufferedReader reader=null;
			try {
				strbuffer=new StringBuffer();
				reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String lineString=null;
				while((lineString=reader.readLine())!=null){
					strbuffer.append(lineString);

				}
				if(result==null){
					result=strbuffer.toString();
					System.out.println("result:"+result);
				}
			} catch (IOException e) {
				System.out.println("发送POST请求出现异常！"+e);
				e.printStackTrace();
			}finally{
				if(reader!=null){
					reader.close();
				}
			}

		}
		return result;

	}

	public static String UploadFile(String urlString,InputStream inputStream,String fileName) throws Exception{
		//返回结果
		String result=null;
		if(inputStream==null){
			throw new IOException("文件不存在");
		}
		URL url=new URL(urlString);
		HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("POST");//以POST方式提交表单
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);//POST方式不能使用缓存
		//设置请求头信息
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		//设置边界
		String BOUNDARY="----------"+System.currentTimeMillis();
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		//请求正文信息
		//第一部分
		StringBuilder sb=new StringBuilder();
		sb.append("--");//必须多两条道
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + fileName+"\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		System.out.println("sb:"+sb);

		//获得输出流
		OutputStream out=new DataOutputStream(conn.getOutputStream());
		//输出表头
		out.write(sb.toString().getBytes("UTF-8"));
		//文件正文部分
		//把文件以流的方式 推送道URL中
		DataInputStream din=new DataInputStream(inputStream);
		int bytes=0;
		byte[] buffer=new byte[1024];
		while((bytes=din.read(buffer))!=-1){
			out.write(buffer,0,bytes);
		}
		din.close();
		//结尾部分
		byte[] foot=("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");//定义数据最后分割线
		out.write(foot);
		out.flush();
		out.close();
		if(HttpsURLConnection.HTTP_OK==conn.getResponseCode()){

			StringBuffer strbuffer=null;
			BufferedReader reader=null;
			try {
				strbuffer=new StringBuffer();
				reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String lineString=null;
				while((lineString=reader.readLine())!=null){
					strbuffer.append(lineString);

				}
				if(result==null){
					result=strbuffer.toString();
					System.out.println("result:"+result);
				}
			} catch (IOException e) {
				System.out.println("发送POST请求出现异常！"+e);
				e.printStackTrace();
			}finally{
				if(reader!=null){
					reader.close();
				}
			}

		}
		return result;

	}

	/**
	 * 获取网络图片流
	 *
	 * @param url
	 * @return
	 */
	public static InputStream getImageStream(String url) throws IOException {

			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				return inputStream;
			}

		return null;
	}
}  