package com.evian.sqct;

import com.evian.sqct.bean.vendor.AdLedPrice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * ClassName:Test20
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/10/10 14:18
 * @Author:XHX
 */
public class Test20 {
    public static void main(String[] args) throws IOException {
        AdLedPrice adLedPrice = new AdLedPrice();
        adLedPrice.setPriceId(1);
//        adLedPrice.setCreater("dffff");
        ObjectMapper json = new ObjectMapper();
        String s = json.writeValueAsString(adLedPrice);
        System.out.println(s);
        json = new ObjectMapper();
        JsonNode jsonNode = json.readTree(s);
        JsonNode creater = jsonNode.get("creater");
        String s1 = creater.textValue();
        System.out.println(s1);

    }
}
