package com.evian.sqct.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * ClassName:WxCodeUtil
 * Package:com.evian.sqct.util
 * Description:微信二维码工具类
 *
 * @Date:2019/10/16 15:21
 * @Author:XHX
 */
public class WxCodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(WxCodeUtil.class);

    /**
     * 生成微信永久二维码
     * @return
     */
    public static BufferedImage perpetualCodeFabrication(String appId, String scene_str) throws IOException {

        String path = ResourceUtils.getURL("classpath:").getPath();
        path = path.replace("%20"," ");
        String fileName = appId+scene_str+".png";
        File png = new File(path+"/couponActivitySend/"+fileName);
        if(png.exists() || png.isFile()) {
            BufferedImage read = ImageIO.read(png);
            return read;
        }

        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + WxTokenAndJsticketCache.getAccess_token(appId);
        String content = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+ scene_str +"\"}}}";
        String ticketStr = HttpClientUtil.post(url, content);
        if(StringUtils.isEmpty(ticketStr)){
            logger.info("生成永久二维码换取ticket失败。url:" + url + "   content:"+content);
        }
        JSONObject ticketJson = JSONObject.fromObject(ticketStr);
        if(!ticketJson.has("ticket") || StringUtils.isEmpty(ticketJson.getString("ticket"))){
            logger.info("换取永久二维码ticket失败。url:" + url + "   content:"+content + "   tikectStr:"+ticketStr);
        }
        url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticketJson.getString("ticket");

        try {
            BufferedImage ImageOutput = CredentialFileDownload.downLoadFromUrlGET(url, fileName);
            return ImageOutput;
        } catch (Exception e) {
            logger.error("生成二维码错误:{}",e);
        }
        return null;
    }
}
