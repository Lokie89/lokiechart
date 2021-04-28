package com.lokiechart.www.service.order.dto;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/28
 */
public enum OrderState {
    WAIT("wait"),
    WATCH("watch"),
    DONE("done"),
    CANCEL("cancel"),
    ;

    @Getter
    private String upbitParameter;

    OrderState(String upbitParameter) {
        this.upbitParameter = upbitParameter;
    }
}
