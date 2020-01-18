package com.evian.sqct.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 
 * @FileName: HttpClientUtil.java
 * @Package com.evian.mobile.util
 * @Description: HTTP请求
 * @author EVIAN(PA)
 * @date 2016年3月2日 上午9:39:03
 * @version V3.0
 */
public class HttpClientUtilOkHttp {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtilOkHttp.class);
	private static final String CHARSET = "utf-8";

	/**
	 * Post请求彭安的服务接口
	 * 
	 * @param contextContext对象
	 * @param mothodName方法名称
	 * @param params参数集合(无参数 传值:null)
	 * @return 消息(String/JSON格式)
	 */
	public static String postEvianApi(String url, List<BasicNameValuePair> params) {

		if(params==null)
			params=new ArrayList<BasicNameValuePair>();
		
		params.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
		params.add(new BasicNameValuePair("equipment", "wx"));
		params.add(new BasicNameValuePair("version_name", "2017.03.22.001"));
//		params.add(new BasicNameValuePair("appSource", "ctapp"));
		params.add(new BasicNameValuePair("sign", EvianSignatureUtil.getSignature(params)));
		String basicNameValuePairToString = basicNameValuePairToString(params);
		basicNameValuePairToString = basicNameValuePairToString.replace("&version_name=2017.03.22.001", "").replace("&equipment=wx", "").replace("&appSource=ctapp", "");
		logger.info("访问水趣接口：[url:{}]", new Object[] {url+"?"+basicNameValuePairToString});
		
		
		FormEncodingBuilder builder = new FormEncodingBuilder();
		
		for (BasicNameValuePair param:params) {
			builder.add(param.getName(), StringUtils.isEmpty(param.getValue()) ? "" : param.getValue());
		}
		
		OkHttpClient client=new OkHttpClient();
		client.setConnectTimeout(5, TimeUnit.SECONDS);
		client.setReadTimeout(15, TimeUnit.SECONDS);
		client.setWriteTimeout(15, TimeUnit.SECONDS);
		Request request = new Request.Builder()
		.url( url).header("User-Agent", "OkHttp Headers.java")
        .addHeader("Accept", "application/json; q=0.5")
        .addHeader("Accept", "application/vnd.github.v3+json").post(builder.build())
		.build();
		
		Call call = client.newCall(request);
	    Response response;
		try {
			response = call.execute();
			 if(response.isSuccessful() && response.code()==200)
			    	return response.body().string();
			    else {
					return "{\"code\":\"E11111\",\"message\":\"访问接口错误或者超时"+response.code()+"\"}";
				}
		} catch (IOException e) {
			logger.error("[url:{}], [exception:{}]", new Object[] { url, e });
			return "{\"code\":\"E11111\",\"message\":\"访问接口错误或者超时\"}";
		}
	}
	
	private static String getUserAgent() {
        String  userAgent = System.getProperty("http.agent");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


	/**
	 * HTTP get请求服务到指定的URL
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		String msg = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
			// 创建get请求.
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(requestConfig);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 获取响应实体
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						// 打印响应内容
						msg = EntityUtils.toString(entity, CHARSET);
					}
				}else{
					logger.error("{},[url:{}]", new Object[] {"get访问接口错误，返回状态："+response.getStatusLine().getStatusCode(), url});
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("[url:{}], [exception:{}]", new Object[] { url, e });
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (Exception e) {
				
			}
		}
		return msg;
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
		
		String signKey = "sn239BV%#$BC^H#JEsjsh#H29b)GMG4-gjo2m3rm";
		String timestamp = String.valueOf(System.currentTimeMillis());
		params.add(new BasicNameValuePair("timestamp", timestamp));
		params.add(new BasicNameValuePair("sign", MD5Util.md5(timestamp+signKey)));
		
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
					if (entity != null)
						msg = EntityUtils.toString(entity, CHARSET);
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
		if(list == null || list.size() == 0)return "无参数";
		String result = "";
		for(BasicNameValuePair cur: list){
			result += cur.getName() + "=" + cur.getValue() + "&";
		}
		return result;
	}
	
	public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

}
