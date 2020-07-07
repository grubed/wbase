package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/5
 */

public class UpdateReceiveOrderStatusDTO {

    @NotNull(message = "missing param: receive")
    private Integer receive;

    public Integer getReceive() {
        return receive;
    }

    public void setReceive(Integer receive) {
        this.receive = receive;
    }

}
