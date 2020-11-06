package com.evian.sqct.bean.util;

import java.io.Serializable;

/**
 * ClassName:BaseResponseDTO
 * Package:com.evian.sqct.bean.util
 * Description:普通接口返回类code message 用于继承
 *
 * @Date:2020/9/25 11:58
 * @Author:XHX
 */
public class BaseResponseDTO implements Serializable {

    protected String code;
    protected String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
