package com.evian.sqct;

import net.sf.json.JSONObject;

/**
 * ClassName:Test10
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/4/14 13:19
 * @Author:XHX
 */
public class Test10 {
    public static void main(String[] args) {
        String ss = "{\"eid\":1,\"shopId\":1,\"did\":1106,\"sendAddress\":\"广东省深圳市宝安区芙蓉路宝安桃花源科技创新园松岗分园(宝安区)\",\"phone\":\"18718000629\",\"contacts\":\"张三\",\"orderRemark\":\"\",\"parent_gboId\":892,\"xaId\":15,\"hashTicket\":false,\"pid\":938,\"schemeId\":42,\"buyNum\":1,\"price\":0.01,\"buyMoney\":0.01,\"numTag\":\"1件\",\"sucMode\":2}";
        JSONObject order = JSONObject.fromObject(ss);
        if(order.containsKey("sucMode")){
            System.out.println(1111);
            int sucMode = order.getInt("sucMode");
            if(sucMode!=1&&sucMode!=2){
                System.out.println(111133333);
                order.put("sucMode",1);
            }
        }else{
            System.out.println(111166666);
            order.put("sucMode",1);
        }
        System.out.println(order.toString());
    }
}
