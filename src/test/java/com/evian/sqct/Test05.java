package com.evian.sqct;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.evian.sqct.bean.user.Eclient;
import com.evian.sqct.bean.user.EclientDTO;

/**
 * ClassName:Test05
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2019/11/20 11:14
 * @Author:XHX
 */
public class Test05 {

    public static void main(String[] args) {
        Eclient e = new Eclient();
        /*e.setClientId(123);
        e.setAccount("123123");*/
        EclientDTO b = new EclientDTO();
        b.setClientId(123);
        b.setAccount("123123");
        CopyOptions copyOptions = CopyOptions.create().setIgnoreNullValue(true);
        BeanUtil.copyProperties(e,b,true, copyOptions);
        System.out.println(b);
    }
}
