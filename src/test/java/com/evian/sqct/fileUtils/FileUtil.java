/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evian.sqct.fileUtils;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author Lion
 */
public class FileUtil {
    
    private ArrayList<LineItem> itemArrayList;
    
    public String textURL;
    
    public FileUtil(String url, String regex){
        textURL = url;
        try {
            itemArrayList = new ArrayList();
            
            //这里比较重要，通过这样的转换消除了中文乱码问题
            InputStreamReader myInputStreamReader = new InputStreamReader(new FileInputStream(url),"gb2312");
       
            BufferedReader myBufferedReader = new BufferedReader(myInputStreamReader);
            
            String valueString = null;
                while ((valueString = myBufferedReader.readLine())!=null){
                    if(StringUtil.findPattern(valueString, regex)){
                        String date = StringUtil.getPatternString(valueString, regex);
                        itemArrayList.add(new LineItem(date, valueString));
                    }
                    else if(itemArrayList.isEmpty()){
                        itemArrayList.add(new LineItem("",valueString));
                    }
                    else{
                        int size = itemArrayList.size();
                        itemArrayList.get(size-1).appendString(valueString);
                    }
                }
                myBufferedReader.close();
               myInputStreamReader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList getItemArrayList(){
        return this.itemArrayList;
    }
    
    
    
}