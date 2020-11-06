/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evian.sqct.fileUtils;
 
import java.util.ArrayList;
 
/**
 *
 * @author Lion
 */
public class FindTextInLog {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        double startTime = System.currentTimeMillis();
        
        // TODO code application logic here
        FileUtil myFile = new FileUtil("C:\\test.log","[2]\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d");
        String myString = "保存需求上报流程数据";
        String myString2 = "计通[2015]66号《关于下发《基于全过程的管线资源建设管理要求》的通知》";
        
        ArrayList findRegexTextResult = new ArrayList();
        
        ArrayList<LineItem> myList = myFile.getItemArrayList();
        
        for(LineItem a:myList)
        {
            String tmp = a.getTextString().toString();
            
            if(StringUtil.findTextIgnoreCase(tmp, myString) && StringUtil.findTextIgnoreCase(tmp, myString2))
            {
                System.out.println(tmp);
            }
            
        }
        
        double endTime = System.currentTimeMillis();
        System.out.println("The program running: "+(endTime-startTime)/1000);
        
    }
    
}