package com.evian.sqct;


import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

import java.net.InetAddress;
import java.util.List;

/**
 * ClassName:Test11
 * Package:com.evian.sqct
 * Description:查询日志
 *
 * @Date:2020/4/26 13:29
 * @Author:XHX
 */
public class Test11 {
    public static void main(String[] args) {
        String [] SearchStrList = new String[12];
        int[] codeId = {54,101,144,181,251,264,308
        ,332
        ,1821
        ,1822
        ,1872
        ,2299
        };
        int[] storeId ={2,2,2,2,2,4,4,4,11,11,11,13};
        for (int i = 0; i <12 ; i++) {
            SearchStrList[i] = "&codeId="+codeId[i]+"&storeId="+storeId[i]+"&authorizer_appid=";
        }
        String fileName = "F:\\日志";
        fileName = "smb://10.16.108.20\\data\\logs\\evian-commerce\\2020-04-23";
//        fileName = "smb://Administrator:2QJ08G2%7v8&FyinhExGhj@10.16.108.20\\data\\logs\\evian-commerce\\2020-04-23";
//        "&codeId=2580&storeId=20&authorizer_appid="
// smb://10.1.44.193/data

        NtlmPasswordAuthentication auth = null ;
        SmbFile remoteFile = null;
        try {
            String userName = "Administrator";
            String password = "2QJ08G2%7v8&FyinhExGhj";
            String domainIP = "10.16.108.20";
            InetAddress ip = InetAddress.getByName(domainIP);
            UniAddress myDomain = new UniAddress(ip);
            auth = new NtlmPasswordAuthentication(domainIP, userName, password);  //先登录验证
            SmbSession.logon(myDomain,auth);
            System.out.println(auth);
            System.out.println(auth);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        BatchWriteInDataByLog.init(fileName, "log", SearchStrList, true,remoteFile,auth);
        List<String> resultList = BatchWriteInDataByLog.executeInquire();
        for (int i = 0; i <resultList.size() ; i++) {
            System.out.println(resultList.get(i));
        }
    }
}
