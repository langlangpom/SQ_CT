package com.evian.sqct;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:Test09
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/4/13 13:46
 * @Author:XHX
 */
public class Test09 {

    private static void sss(String re) {
        String regex = "\\{.+}";
        //把正则表达式编译成一个正则对象
        Pattern p = Pattern.compile(regex);
        //获取匹配器
        Matcher m = p.matcher(re);
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}
