package com.evian.sqct;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.WechatPayUploadHttpPost;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.security.PrivateKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AutoUpdateVerifierTest {

  private static String mchId = "1602932741"; // 商户号
  private static String mchSerialNo = "27A9D2B853F10E1499215B6B0DA963153F6603CB"; // 商户证书序列号
  private static String apiV3Key = "SQWLkqyzLKJG390K3L5mv5339kgjK3v1"; // api密钥
  // 你的商户私钥
  private static String privateKey = "-----BEGIN PRIVATE KEY-----\n" +
          "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTtvaAWytsbmXb\n" +
          "I3pUdqMj2djtLfyFSIUNi/nPdBFg3BAh0N1dOjd+LAVXJ650ErU+gzmIZtD8pFZL\n" +
          "HBfpLHls6pRBXfK1BmF3jnmif2hE6TCXFYi3cu6wClpt3Tz6gqKZ1Z+NdHoDZ2tW\n" +
          "O3GuWcgOnVQ4NZBMKc0dgSCAru2X8hONsqTwuTZX8bBS19+U5fTu8xTctNT5T/FQ\n" +
          "OOUUYyWgExDcEw9aVgoaVSz8yQZSCvae6ekznpTwwQldcpKEqJAFpqZDQC94C3g3\n" +
          "NOl6dj3biYG1oVdlKXBeqctvK0DRlAH3xUWU4P5nlBayejPzlwkTpNiaXFCIszYK\n" +
          "8kydSlALAgMBAAECggEAbqD57tnZJMVt3XittrJ/rmsKj2AAnuXYWHP8LUX1egB2\n" +
          "yIzALYFbbFCV01dxzZbxTRdEsIaogH6n7mrk8YqQimUJkgdFrhRQTRVUvcIEGpA3\n" +
          "BwBmu1YFs6ydW2j1sXzDQMmATJXuh7wd5KBynKy7K07jc5GSN6ia6Tz7VcUtRr0w\n" +
          "YVakNZslVMT9tfPPcb2nEW42O3qYYGtR9e5ASq9mcVnmWRb+rwXf9l3M2Cy7YW/V\n" +
          "kIyiKFstGO7yPw9r34Tg9NXAPZ7ry9c1i/2eE1ToHp6627/pFpSSw5a5HsDrzda6\n" +
          "xhvwLcpO/NIk3WPj3ySjOKSucHQPaqMYBVRSSMZ+4QKBgQDElVPqetO5x6XHCKW/\n" +
          "e/LvYZ7TduxkuxKn3CSXGAC7FowIRJ4l74Snqcof7CUiuu97ubwOC0Z3RFIZwO5A\n" +
          "w91r1UTBZBxZm5HRVtsmY5OwzAurdJSpCV9Y8HlgOQsGtCc5bksXDKr9Q4L4WfnT\n" +
          "P4z+StzJIjEJJE9lL/Ce/uCKkQKBgQDAXGqnWV1tFYNbPeOstWnJ0mtg0tKFZHzF\n" +
          "GC2peANBgHH6wn/sV9h9Axq0aNAkFg976l4fj5tymXllzFCbQfSOcEfqGdtjRPHa\n" +
          "5Bw6OG6hd3S7rYajvKavlKbs2r9VN3wajytHIBPuhomqFgIkUkuR2PH/w62/yWTE\n" +
          "Ue/2bx5m2wKBgC96MS4sQ/5WbX9IY19/vAh8dzNzQVyCodrQnDWxoluM8nz8tYVL\n" +
          "96nWo+5Zxg5BP52OGhD6QCaF5Z46rlAN/egRNe3PE9nAbNM2Ou9mlUDZxue93DmG\n" +
          "/fQo/+dxX2xbDi4cqR4D/Tf1migI8wFGE/NGeVRKYbVng5VYorCDtg9BAoGAeFgZ\n" +
          "HZOKIJnJ8UZZ7w4cHfi31v5uWKfSa1hCEPkVSGB8bn5Km+kldqh/h4jfVwQ6MbMh\n" +
          "qaslfF/KFEbr0wZDYu2aADqnB4b1rJTzl/UAOaJLdLMOPdfjjIt6l0oPU9f/m3og\n" +
          "gEHwVlTzQWa9dk3KG3fXhsS8KfVsRmpgrxM7uOsCgYEAvRTMWwBw83RWDqy4Fjai\n" +
          "Co0PIsK6aDrIjV9tsQY/t3QdGUF803JhR6ovPoRbmbPtdEAmF8ErOtywbB4iNhZp\n" +
          "Ncr+F5L7/L5pBrFlzKK29nwAopRRuKwSK1O/s6SzAzIS6luUuQmLxMyc1efiUlOj\n" +
          "BsylbPbAECk/8m8LZkLfA3c=\n" +
          "-----END PRIVATE KEY-----\n";

  //测试AutoUpdateCertificatesVerifier的verify方法参数
  private static String serialNumber = "";
  private static String message = "";
  private static String signature = "";
  private CloseableHttpClient httpClient;
  private AutoUpdateCertificatesVerifier verifier;

  @Before
  public void setup() throws IOException {
    PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(
        new ByteArrayInputStream(privateKey.getBytes("utf-8")));

    //使用自动更新的签名验证器，不需要传入证书
    verifier = new AutoUpdateCertificatesVerifier(
        new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),
        apiV3Key.getBytes("utf-8"));

    httpClient = WechatPayHttpClientBuilder.create()
        .withMerchant(mchId, mchSerialNo, merchantPrivateKey)
        .withValidator(new WechatPay2Validator(verifier))
        .build();
  }

  @After
  public void after() throws IOException {
    httpClient.close();
  }

  @Test
  public void autoUpdateVerifierTest() throws Exception {
    assertTrue(verifier.verify(serialNumber, message.getBytes("utf-8"), signature));
  }

  @Test
  public void getCertificateTest() throws Exception {
    URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/certificates");
    HttpGet httpGet = new HttpGet(uriBuilder.build());
    httpGet.addHeader("Accept", "application/json");
    CloseableHttpResponse response1 = httpClient.execute(httpGet);
    assertEquals(200, response1.getStatusLine().getStatusCode());
    try {
      HttpEntity entity1 = response1.getEntity();
      // do something useful with the response body
      // and ensure it is fully consumed
      EntityUtils.consume(entity1);
    } finally {
      response1.close();
    }
  }


  @Test
  public void uploadImageTest() throws Exception {
    String filePath = "C:/Users/XHX/Desktop/日志/190321153432302617-d.jpg";

    URI uri = new URI("https://api.mch.weixin.qq.com/v3/merchant/media/upload");

    File file = new File(filePath);
    try (FileInputStream s1 = new FileInputStream(file)) {
      String sha256 = DigestUtils.sha256Hex(s1);
      try (InputStream s2 = new FileInputStream(file)) {
        WechatPayUploadHttpPost request = new WechatPayUploadHttpPost.Builder(uri)
            .withImage(file.getName(), sha256, s2)
            .build();

        CloseableHttpResponse response1 = httpClient.execute(request);
        assertEquals(200, response1.getStatusLine().getStatusCode());
        try {
          HttpEntity entity1 = response1.getEntity();
          // do something useful with the response body
          // and ensure it is fully consumed
          String s = EntityUtils.toString(entity1);
          System.out.println(s);
        } finally {
          response1.close();
        }
      }
    }
  }
}