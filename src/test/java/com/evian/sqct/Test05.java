package com.evian.sqct;

import com.evian.sqct.bean.user.Eclient;
import com.evian.sqct.bean.user.EclientDTO;
import org.springframework.beans.BeanUtils;

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
        Eclient e = null;
//        e.setClientId(123);
//        e.setAccount("123123");
        EclientDTO b = new EclientDTO();
        BeanUtils.copyProperties(e,b);
        System.out.println(b);
    }
}
