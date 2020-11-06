package com.evian.sqct;

import com.evian.sqct.util.Constants;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName:Test15
 * Package:com.evian.sqct
 * Description:获取对象的属性和值 写入txt
 *
 * @Date:2020/6/11 10:10
 * @Author:XHX
 */
public class Test15 {

    public static void main(String[] args) throws IOException, IllegalAccessException {
        /*File file = new File("F:\\test\\Constants.txt");
        if(!file.exists()){
            file.createNewFile();
        }*/
        BufferedWriter out = new BufferedWriter(new FileWriter("F:\\test\\Constants.txt"));

        Constants t = new Constants();
        Map<String,Integer> filedName = new Test15().getFiledName(t);
        /*for (int i = 0; i < filedName.length ; i++) {
            System.out.println(filedName[i]);
        }*/
        Set<Map.Entry<String, Integer>> entries = filedName.entrySet();
        // 排序
        List<Map.Entry<String, Integer>> collect = entries.stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toList());


        for(Map.Entry<String, Integer> entry : collect){
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();

            try {
                String codeValue = Constants.getCodeValue((int) mapValue);
                if(!StringUtils.isEmpty(codeValue)){
                    String zj = "/** "+codeValue+" */\n";

                    String content = mapKey+"("+(int)mapValue+",\""+codeValue+"\"),\n";
                    System.out.println(zj+content);
                    out.write(zj+content);
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        out.close();
        System.out.println("文件创建成功！");

    }

    private Map<String,Integer>  getFiledName(Object o) throws IllegalAccessException {
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<fields.length;i++){
            Object val = new Object();
            fieldNames[i]=fields[i].getName();
            fields[i].setAccessible(true); // 设置些属性是可以访问的
            val = fields[i].get(val);
//            System.out.println(val);
            try {
                map.put(fieldNames[i],(Integer) val);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return map;
    }
}
