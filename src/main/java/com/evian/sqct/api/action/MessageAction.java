package com.evian.sqct.api.action;

import com.evian.sqct.service.BaseMessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:MessageAction
 * Package:com.evian.sqct.api.action
 * Description:消息action
 *
 * @Date:2020/7/16 9:34
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/message")
public class MessageAction {

    @Autowired
    private BaseMessageManager messageManager;

    @RequestMapping("/pushVendorShipmentMessage.action")
    public String pushVendorShipmentMessage(String groupSign){
        return messageManager.pushVendorShipmentMessage(groupSign);
    }

}
