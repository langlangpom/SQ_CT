package com.evian.sqct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Test16
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 10:18
 * @Author:XHX
 */
public class Test16 {
    public static void main(String[] args) throws IOException {
        Integer y = new Integer(1);
        Map<String,Object> m = new HashMap<>();
        m.put("rrr",y);

        Integer t = 1;
        System.out.println((Integer) m.get("rrr")==t.intValue());
    }
}
