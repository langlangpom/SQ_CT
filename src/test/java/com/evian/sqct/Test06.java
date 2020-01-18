package com.evian.sqct;

import com.evian.sqct.util.DES3_CBCUtil;

/**
 * ClassName:Test06
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2019/12/16 15:44
 * @Author:XHX
 */
public class Test06 {
    public static void main(String[] args) {
        String s = DES3_CBCUtil.des3EncodeCBC("SQ2019jkj656ejdsgl11fjglfjg21jgk");
        String s1 = DES3_CBCUtil.des3DecodeCBC(s);
        System.out.println(s);
        System.out.println(s1);
    }
}
