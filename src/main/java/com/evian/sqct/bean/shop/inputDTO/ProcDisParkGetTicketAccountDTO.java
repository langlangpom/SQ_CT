package com.evian.sqct.bean.shop.inputDTO;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcDisParkGetTicketAccountDTO
 * Package:com.evian.sqct.bean.shop.inputDTO
 * Description:Proc_DisPark_Get_Ticket_Account
 *
 * @Date:2020/6/29 9:21
 * @Author:XHX
 */
public class ProcDisParkGetTicketAccountDTO {
    @NotNull
    private Integer clientId;
    @NotNull
    private Integer type;

    @Override
    public String toString() {
        return "ProcDisParkGetTicketAccountDTO [" +
                "clientId=" + clientId +
                ", type=" + type +
                ']';
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
