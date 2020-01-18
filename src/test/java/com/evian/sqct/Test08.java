package com.evian.sqct;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @date   2020年1月3日 下午5:12:15
 * @author XHX
 * @Description 文件写入
 */
public class Test08 {
public static void main(String[] args) throws Exception {
	File cc = new File("C:\\Users\\XHX\\Desktop\\日志\\cc.gxt");
	File dd = new File("C:\\Users\\XHX\\Desktop\\日志\\dd.gxt");
	if(cc.exists()) {// 是否存在
		System.out.println(1);
		
		FileInputStream s = new FileInputStream(cc);
		byte[] readInputStream = readInputStream(s);
		FileOutputStream fos = new FileOutputStream(dd);
		fos.write(readInputStream);
		if(fos!=null){
            fos.close();
        }
        if(s!=null){
            s.close();
        }
	}else {
		
		System.out.println(2);
	}
}

public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
    byte[] buffer = new byte[1024];  
    int len = 0;  
    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
    while((len = inputStream.read(buffer)) != -1) {  
        bos.write(buffer, 0, len);  
    }  
    bos.close();  
    return bos.toByteArray();  
}
}
