package com.evian.sqct.bean.user;

import javax.validation.constraints.NotNull;

/**
 * ClassName:BackLoginReqDTO
 * Package:com.evian.sqct.bean.user
 * Description:backLoginApiDTO
 *
 * @Date:2020/6/11 14:13
 * @Author:XHX
 */
public class BackLoginReqDTO {
    @NotNull
    private String account;
    @NotNull
    private String passWord;
    @NotNull
    private String regeditId;
    @NotNull
    private Integer sourceId;
//    @NotNull
    private Integer platformId = 1;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRegeditId() {
        return regeditId;
    }

    public void setRegeditId(String regeditId) {
        this.regeditId = regeditId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }
}
