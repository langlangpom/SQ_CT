package com.evian.sqct;

/**
 * ClassName:Test23
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/11/2 15:47
 * @Author:XHX
 */
public class Test23 {
    public static void main(String[] args) {
        String fileName = "filegetName()";
        System.out.println(fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println(suffix);
    }
}
