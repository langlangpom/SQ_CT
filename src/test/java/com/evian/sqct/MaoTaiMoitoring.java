package com.evian.sqct;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:MaoTaiMoitoring
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/11/4 11:32
 * @Author:XHX
 */
public class MaoTaiMoitoring {
    public static String lamboKey = "053F1h0w3JNHfV2A3C2w3lYqdu4F1h0C#mQMtJNLoZfN7fZHbqLTIfs2bWuloHOjQOTQoXMsYoFk=";
    public static String token = "09377JGa1yaiUz0SKMHa12djNt377JGH";
    public static String openid = "mQMtJNLoZfN7fZHbqLTIfs2bWuloHOjQOTQoXMsYoFk=";

    private static Logger logger = LoggerFactory.getLogger(MaoTaiMoitoring.class);
    public static void main(String[] args) throws InterruptedException, AWTException {
        List<MaoTaiShop> tempShops = new ArrayList<>();
        init();
        GZH_mao_tai mt = new GZH_mao_tai();
        while (true){
            // http 请求查询店铺
            MaoTaiShopsBean maoTaiShopsBean = mt.monitoringMaoTaiShops(token+"#"+openid,token);
            if(!"000".equals(maoTaiShopsBean.code)){
                // 请求异常
                logger.info("{}",maoTaiShopsBean);
                displayTray(maoTaiShopsBean.getMessage(),maoTaiShopsBean.getCode());
                System.exit(0);
            }
            String shops = "";
            List<MaoTaiShop> data = maoTaiShopsBean.getData();
            // 差集 用来对比是否新增了店铺
            List<MaoTaiShop> difference = new ArrayList<>();
            for(MaoTaiShop shop : data){
                shops += shop.getV()+"="+shop.getK()+",";
                if("广东自营店".equals(shop.getV())){
                    logger.info("[广东自营店来了！！！！！！！]");
                    // http 请求查询广东自营店的商品
                    MaoTaiProductsBean maoTaiProductsBean = mt.helpBoxShopId(token + "#" + openid, token, shop.getK());
                    logger.info("{}",maoTaiProductsBean);
                    String base = "";
                    for (MaoTaiProductsRows row : maoTaiProductsBean.getData().getRows()){
                        // 去掉空格，去掉回车
                        String tempbase = "<img src=\"data:image/png;base64,"+row.getWje().replace("\\s+", "").replace("(?s)'.*'","")+"\">";
                        base += tempbase+"，";
                    }
                    // 打印图片
                    logger.info("[base:{}]",base);
                    logger.info("[广东自营店来了！！！！！！！]");
                    displayTray("广东自营店来了！！！！！！！","total:"+maoTaiProductsBean.getData().getTotal());
                    System.exit(0);
                }
                // 进行对比
                if(tempShops.size()>0){
                    for(int i =0;i<tempShops.size();i++){
                        MaoTaiShop temp = tempShops.get(i);
                        if(temp.getV().equals(shop.getV())){
                            break;
                        }
                        if(i==tempShops.size()-1){
                            difference.add(shop);
                        }
                    }
                }
            }
            if(tempShops.size()==0){
                difference = data;
            }
            tempShops = data;
            // 查询 新增店铺的商品
            difference.stream().forEach(shop -> {
                // http 请求查询商品
                MaoTaiProductsBean maoTaiProductsBean = mt.helpBoxShopId(token + "#" + openid, token, shop.getK());
                if(!"000".equals(maoTaiProductsBean.code)){
                    logger.info("{}",maoTaiProductsBean);
                }
                String base = "";
                for (MaoTaiProductsRows row : maoTaiProductsBean.getData().getRows()){
                    // 去掉空格，去掉回车
                    String tempbase = "<img src=\"data:image/png;base64,"+row.getWje().replace("\\s+", "").replace("(?s)'.*'","")+"\">";
                    base += tempbase+"，";
                }
                logger.info("[shopName:{}][total:{}] [base:{}]",shop.getV(),maoTaiProductsBean.getData().getTotal(),base);
                displayTray("新增："+shop.getV(),"total:"+maoTaiProductsBean.getData().getTotal());
            });
            logger.info(shops);
            Thread.sleep(30000);
        }
    }

    public static void displayTray(String acption,String text) {
        if (SystemTray.isSupported()) {
            NotificationDemo nd = new NotificationDemo();
            try {
                nd.displayTray(acption,text);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("System tray not supported!");
        }
    }



    public static void init(){
        // 设置默认工厂类
        System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
        // 设置日志打印类
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        //设置默认日志级别
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.simplelog.defaultlog", "error");
    }

}
