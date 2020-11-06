package com.evian.sqct.util;

import com.evian.sqct.bean.baseBean.PagingPojo;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DaoUtil
 * Package:com.evian.sqct.util
 * Description:dao层工具类
 *
 * @Date:2020/6/10 16:16
 * @Author:XHX
 */
public class DaoUtil {

    public static final String RESULT = "result";

    /**
     * 返回结果集改名
     * @param result
     * @param name
     * @return
     */
    public static Map<String,Object> resultRename(Map<String,Object> result,String... name){
        if(name!=null&&name.length>0){
            int count = name.length;
            for (int i = 0; i < count; i++) {
                String resultName = RESULT+i;
                if(result.containsKey(resultName)){
                    Object value = result.get(resultName);
                    result.put(name[i], value);
                    result.remove(resultName);
                }else{
                    result.put(name[i], new ArrayList());
                }
            }
        }
        return result;
    }

    /**
     * 返回 TAG
     * @param result
     * @return
     */
    public static String resultTAG(Map<String,Object> result){
        if (result.containsKey("TAG")){
            return (String) result.get("TAG");
        }else if(result.containsKey("tag")){
            return (String) result.get("tag");
        }
        return null;
    }

    /**
     * 取list的第一个元素(index=0)重新赋值
     * @param result
     * @param keyName
     * @return
     */
    public static Map<String,Object> resultListTransitionMap(Map<String,Object> result,String keyName){
        if(result.containsKey(keyName)){
            Object value = result.get(keyName);
            if(value!=null && value instanceof List){
                List list = (List) value;
                if(list.size()>0){
                    result.put(keyName,list.get(0));
                }
            }
        }
        return result;
    }

    public static Map<String,Object> resultListTransitionMap(Map<String,Object> result,String keyName,int index){
        if(result.containsKey(keyName)){
            Object value = result.get(keyName);
            if(value!=null && value instanceof List){
                List list = (List) value;
                if(list.size()>=index){
                    result.put(keyName,list.get(index));
                }
            }
        }
        return result;
    }

    /**
     * pagehelper 插件使用 封装成 常用的参数返回  1.Count 2.list
     * @param pagePojo  分页类
     * @param name      list 更换的名字  不传的话默认list
     * @param select    查询的方法
     *                  用法：
     *
     *         //2. Lambda
     *         return PageHelper.startPage(1, 10).doSelectPageInfo(() -> userService.findAll());
     * @return
     */
    public static Map<String,Object> resultPageData(PagingPojo pagePojo, String name, ISelect select){
        Page<Object> objects;
        if(pagePojo.getIsSelectAll()!=null&&pagePojo.getIsSelectAll()==true){
            objects = PageHelper.startPage(0, 0);
        }else {
            objects = PageHelper.startPage(pagePojo.getPageIndex(), pagePojo.getPageSize());
        }
        PageInfo<Object> objectPageInfo = objects.doSelectPageInfo(select);
        Map<String,Object> result = new HashMap<>();
        result.put("Count",objectPageInfo.getTotal());
        if(StringUtils.isBlank(name)){
            result.put("list",objectPageInfo.getList());
        }else{
            result.put(name,objectPageInfo.getList());
        }
        return result;
    }

    /**
     * pagehelper 插件使用 封装成 常用的参数返回  1.Count 2.list
     * @param pagePojo  分页类
     * @param select    查询的方法
     *                  用法：
     *
     *         //2. Lambda
     *         return PageHelper.startPage(1, 10).doSelectPageInfo(() -> userService.findAll());
     * @return
     */
    public static Map<String,Object> resultPageData(PagingPojo pagePojo, ISelect select){
        Page<Object> objects;
        if(pagePojo.getIsSelectAll()!=null&&pagePojo.getIsSelectAll()==true){
            objects = PageHelper.startPage(0, 0);
        }else {
            objects = PageHelper.startPage(pagePojo.getPageIndex(), pagePojo.getPageSize());
        }
        PageInfo<Object> objectPageInfo = objects.doSelectPageInfo(select);
        Map<String,Object> result = new HashMap<>();
        result.put("Count",objectPageInfo.getTotal());
        result.put("list",objectPageInfo.getList());
        return result;
    }

}
