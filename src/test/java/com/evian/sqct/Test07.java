package com.evian.sqct;

import com.evian.sqct.wxHB.RequestHandler;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * ClassName:Test07
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2019/12/18 13:40
 * @Author:XHX
 */
public class Test07 {
    public static void main(String[] args) {
        SortedMap<String,String> packageParams = new TreeMap<>();
        packageParams.put("prepayid","wx181257217385069c8cd825301315826900");
        packageParams.put("appid","wxb2262f9af2b80ed9");
        packageParams.put("partnerid","1563973861");
        packageParams.put("package","Sign=WXPay");
        packageParams.put("noncestr", "4e2a6330465c8ffcaa696a5a16639176");
        packageParams.put("timestamp", "1576645040");
        RequestHandler reqHandler = new RequestHandler();
        reqHandler.init("SQ2019jkj656ejdsgl11fjglfjg21jgk");
        // 生成app端发起支付签名
        String sign = reqHandler.createSign(packageParams);
        System.out.println(sign);
    }
}
