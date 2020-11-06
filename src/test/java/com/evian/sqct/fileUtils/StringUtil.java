/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evian.sqct.fileUtils;
 
 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 *
 * @author Lion
 */
public class StringUtil {
    //定义要找到的关键字
    public String text;
    
    //定义要找到的规则
    
    
    public StringUtil(String aString){
        text = aString;
    }
    
    
    public static boolean findTextIgnoreCase(String sourceString, String text){
        String ignoreCaseString = sourceString.toLowerCase();
        String ignoreText = text.toLowerCase();
        
        CharSequence textSequence;
        textSequence = ignoreText.subSequence(0,ignoreText.length());
        
        return ignoreCaseString.contains(textSequence);    
    }
    
    public static boolean findTextNotIgnoreCase(String sourceString, String text){
        CharSequence textSequence = text.subSequence(0, text.length());
        return sourceString.contains(textSequence);
    }
    
    public static boolean findPattern(String sourceString, String myParttern){
        Pattern pattern = Pattern.compile(myParttern);
        Matcher matcher;
        matcher = pattern.matcher(sourceString);
        return matcher.find();
    }
    
    public static String getPatternString(String sourceString , String myPattern){
        Pattern pattern = Pattern.compile(myPattern);
        Matcher matcher;
        matcher = pattern.matcher(sourceString);
        if(matcher.find()){
            return matcher.group();
        }
        else
            return null;
    }
    
//    public static void main(String[] args){
//        String a = "我爱北京天安门";
//        String b = "北京a";
//        
//        String c = "abcdefghigk";
//        String d = "K";
//        String e = "x ";
//        System.out.println("aaaaaaa");
//        System.out.println(findTextIgnoreCase(a,b));
//        System.out.println(findTextNotIgnoreCase(c,d));
//        
//        String xx = "我爱北京天安门*************";
//        
//        String abc = "2015-11-20 11:26:24,434 [WebContainer : 13] INFO  com.mocha.bpm.hncmcc.itxq.action"
//                + ".ReportingProcessAction:"
//                + "356  - 保存需求上报流程数据";
//        
//        String part ="[2]\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d";
//        
//        System.out.println(findPattern(abc,part));
//        System.out.println(getPatternString(abc,part));
//        
//        ArrayList mylist = new ArrayList();
//        mylist.add(1);
//        mylist.add(2);
//        System.out.println(mylist.size());
//        System.out.println(mylist.get(mylist.size()-1));
//        
//        try {
//         
//            
//            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("C:\\test.log")), "gb2312"); 
//            BufferedReader myB = new BufferedReader(isr);
//            String aa = myB.readLine();
//            System.out.println(aa);
//            myB.close();
//            isr.close();
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
    
}