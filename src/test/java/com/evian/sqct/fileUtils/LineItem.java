/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evian.sqct.fileUtils;
 
/**
 *
 * @author Lion
 */
public class LineItem {
    public String date;
    public StringBuffer textStringB;
    
    public String getDate(){
        return date;
    }
    
    public StringBuffer getTextString(){
        return textStringB;
    }
    
    public LineItem(String date, String textString){
        this.date = date;
        StringBuffer myStringBuffer = new StringBuffer(textString);
        this.textStringB = myStringBuffer;
    }
    
    public void appendString(String text){
        this.textStringB.append(text);
    }
    
}