package com.evian.sqct;

import com.evian.sqct.bean.order.ProcBackstageClientPledgeTuiyaSelectDTO;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:Test14
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/6/5 17:44
 * @Author:XHX
 */
public class Test14 {

    public static void main(String[] args) throws Exception {

        ProcBackstageClientPledgeTuiyaSelectDTO dto = new ProcBackstageClientPledgeTuiyaSelectDTO();
        dto.setEid(1);
        dto.setIsAxceedOneYear(true);

        Class clazz = dto.getClass();
        List<Method> t = new ArrayList<>();
        while (clazz!=null){
            Method[] m = clazz.getDeclaredMethods();

            for (Method mm :m){
                String name = mm.getName();
                if(name.contains("get")){
                    t.add(mm);
                }
            }
            clazz = clazz.getSuperclass();
        }

        for (Method mm:t){
            System.out.println(mm.getName());
        }

        Class aClass = dto.getClass();
        Method getPageSize = aClass.getMethod("getPageSize");
        Object invoke = getPageSize.invoke(dto);
        System.out.println(invoke);

    }
}
