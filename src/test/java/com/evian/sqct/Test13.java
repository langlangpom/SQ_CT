package com.evian.sqct;

import com.evian.sqct.util.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName:Test13
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/5/28 15:43
 * @Author:XHX
 */
public class Test13 {

    public static void main(String[] args) {
        Long time = DateUtil.convertTimeToLong("20200617154026", "yyyyMMddHHmmss");
        System.out.println(time);
        String s = DateUtil.convertTimeToString(time, "yyyy-MM-dd HH:mm:ss");
        System.out.println(s);

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = ftf.format(LocalDateTime.now());
        System.out.println(format);
    }
    private static void ss(String aa){
        aa="ttt";

    }
}
