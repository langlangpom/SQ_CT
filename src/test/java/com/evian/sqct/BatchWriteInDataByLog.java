package com.evian.sqct;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 递归便利某个盘内文件的内容
 * 根据日志批量导入数据
 * @author 25050
 */
public class BatchWriteInDataByLog {
    private final static Logger logger = LoggerFactory.getLogger(BatchWriteInDataByLog.class);

    private static String FileName = null;// 文件夹名字

    private static String FileEnd = null;// 文件名称后缀 txt sql bat

    private static String SearchStr = null;//要查找的字符串

    private static String[] SearchStrList = null;//要查找的字符串集合

    private static Boolean IngronCase = true;// 是否区分大小写

    private static int count = 0;             // 查找第几个文件

    private static List<String> pathlist = new ArrayList<>();

    private static SmbFile remoteFile;

    private static NtlmPasswordAuthentication auth;

    /**
     *
     * @param FileName 文件夹名字
     * @param FileEnd   文件名称后缀 .log
     * @param SearchStr 要查找的字符串
     * @param IngronCase 是否区分大小写
     */
    public static void init(String FileName,String FileEnd,String SearchStr,Boolean IngronCase){
        BatchWriteInDataByLog.FileName = FileName;
        BatchWriteInDataByLog.FileEnd = FileEnd;
        BatchWriteInDataByLog.SearchStr = SearchStr;
        if(IngronCase!=null){
            BatchWriteInDataByLog.IngronCase = IngronCase;
        }
    }

    /**
     *
     * @param FileName 文件夹名字
     * @param FileEnd   文件名称后缀 .log
     * @param SearchStrList 要查找的字符串集合
     * @param IngronCase 是否区分大小写
     */
    public static void init(String FileName,String FileEnd,String[] SearchStrList,Boolean IngronCase){
        BatchWriteInDataByLog.FileName = FileName;
        BatchWriteInDataByLog.FileEnd = FileEnd;
        BatchWriteInDataByLog.SearchStrList = SearchStrList;
        if(IngronCase!=null){
            BatchWriteInDataByLog.IngronCase = IngronCase;
        }
    }

    /**
     *
     * @param FileName 文件夹名字
     * @param FileEnd   文件名称后缀 .log
     * @param SearchStrList 要查找的字符串集合
     * @param IngronCase 是否区分大小写
     */
    public static void init(String FileName,String FileEnd,String[] SearchStrList,Boolean IngronCase,SmbFile remoteFile,NtlmPasswordAuthentication auth){
        BatchWriteInDataByLog.FileName = FileName;
        BatchWriteInDataByLog.FileEnd = FileEnd;
        BatchWriteInDataByLog.SearchStrList = SearchStrList;
        BatchWriteInDataByLog.remoteFile = remoteFile;
        BatchWriteInDataByLog.auth = auth;
        if(IngronCase!=null){
            BatchWriteInDataByLog.IngronCase = IngronCase;
        }
    }


    /**
     *
     * @param FileName 文件夹名字
     * @param FileEnd   文件名称后缀 .log
     * @param SearchStrList 要查找的字符串集合
     * @param IngronCase 是否区分大小写
     */
    public static void init(String FileName,String FileEnd,String SearchStr,Boolean IngronCase,SmbFile remoteFile,NtlmPasswordAuthentication auth){
        BatchWriteInDataByLog.FileName = FileName;
        BatchWriteInDataByLog.FileEnd = FileEnd;
        BatchWriteInDataByLog.SearchStrList = SearchStrList;
        BatchWriteInDataByLog.remoteFile = remoteFile;
        BatchWriteInDataByLog.auth = auth;
        if(IngronCase!=null){
            BatchWriteInDataByLog.IngronCase = IngronCase;
        }
    }


    public static List<String> executeInquire() {
        List<String> resultList = new ArrayList<>();
        pathlist = getFile(FileName);
        System.out.println(pathlist);
        for (int k = 0; k < pathlist.size(); k++) {
            File file = new File(pathlist.get(k));
            if (file.exists()) {
                String s = file.toString();
                count++;
                logger.info("正在读取第{}个文件{}",new Object[]{count,s});
                int textCount = 0;
                /* 读取数据 */
                try {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(new FileInputStream(new File(s)), "UTF-8"));
                    String lineTxt = null;
                    while ((lineTxt = br.readLine()) != null) {
                        if (IngronCase) {
                            if(SearchStrList==null||SearchStrList.length==0){
                                textCount = matchingValue(lineTxt, SearchStr, textCount, resultList);
                            }else{
                                // 多字符串查找
                                textCount = foreMatching(lineTxt, textCount, resultList);
                            }
                        } else {
                            if(SearchStrList==null||SearchStrList.length==0){
                                textCount = matchingValueToLowerCase(lineTxt, SearchStr, textCount, resultList);
                            }else{
                                // 多字符串查找
                                textCount = foreMatchingToLowerCase(lineTxt, textCount, resultList);
                            }
                        }
                    }
                    br.close();
                } catch (Exception e) {
                    // TODO: handle exception
                    logger.error("读取文件错误 :",e);
                }
                logger.info("该文件有{}条数据",textCount);
            }
        }
        logger.info("===============输出结果===============");
        // 输出结果
        for (int i = 0; i < resultList.size(); i++) {
            logger.info(resultList.get(i));
        }
        logger.info("该文件夹一共有{}条数据",resultList.size());
        return resultList;
    }

    public static int foreMatching(String lineTxt,int textCount,List<String> resultList){
        for (String SearchStr:SearchStrList){
            textCount = matchingValue(lineTxt, SearchStr, textCount,resultList);
        }
        return textCount;
    }
    public static int foreMatchingToLowerCase(String lineTxt,int textCount,List<String> resultList){
        for (String SearchStr:SearchStrList){
            textCount = matchingValueToLowerCase(lineTxt, SearchStr, textCount,resultList);
        }
        return textCount;
    }

    public static int matchingValue(String lineTxt,String SearchStr,int textCount,List<String> resultList){
        if (lineTxt.contains(SearchStr)) {
            textCount++;
            patternMatchingJSONAndSaveResultList(lineTxt,resultList);
        }
        return textCount;
    }
    public static int matchingValueToLowerCase(String lineTxt,String SearchStr,int textCount,List<String> resultList){
        if (lineTxt.toLowerCase().contains(SearchStr)) {
            textCount++;
            patternMatchingJSONAndSaveResultList(lineTxt,resultList);
        }
        return textCount;
    }

    public static List<String> getFile(String strPath){
        if(auth==null||remoteFile==null){
            return getFileList(strPath);
        }else{
            return getSmbFileList(strPath);
        }
    }


    public static List<String> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("." + FileEnd)) { // 判断文件名是否以.avi结尾
                    String strFileName = files[i].getAbsolutePath();
                    pathlist.add(strFileName);
                } else {
                    continue;
                }
            }

        }
        return pathlist;
    }

    public static List<String> getSmbFileList(String strPath) {
        try {
            SmbFile dir = new SmbFile(strPath,auth);
            SmbFile[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getName();
                    if (files[i].isDirectory()) { // 判断是文件还是文件夹
                        getSmbFileList(files[i].getCanonicalPath()); // 获取文件绝对路径
                    } else if (fileName.endsWith("." + FileEnd)) { // 判断文件名是否以.avi结尾
                        String strFileName = files[i].getCanonicalPath();
                        pathlist.add(strFileName);
                    } else {
                        continue;
                    }
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        }
        return pathlist;
    }

    private static void patternMatchingJSONAndSaveResultList(String json,List<String> resultList){
        System.out.println(json);
        String matchingJSON = patternMatchingJSON(json);
        if(!StringUtils.isEmpty(matchingJSON)){
            JSONObject matching = JSONObject.fromObject(matchingJSON);
            if(!matching.containsKey("errcode")){
                resultList.add(matchingJSON);
            }
        }
    }

    /**
     * 正则匹配json
     * @param matchingStr   // 需要匹配的JSON
     */
    private static String patternMatchingJSON(String matchingStr) {
        String regex = "\\{.+}";
        //把正则表达式编译成一个正则对象
        Pattern p = Pattern.compile(regex);
        //获取匹配器
        Matcher m = p.matcher(matchingStr);
        while (m.find()) {
            return m.group();
        }
        return null;
    }
}
