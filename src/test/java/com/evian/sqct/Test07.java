package com.evian.sqct;

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
        new Thread((Runnable) () -> System.out.println("ttt")).start();
        System.out.println("yyyyyyy");
    }
}
