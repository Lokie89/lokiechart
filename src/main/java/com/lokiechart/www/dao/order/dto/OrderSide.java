package com.lokiechart.www.dao.order.dto;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public enum OrderSide {
    BUY("bid"),
    SELL("ask"),
    ;

    @Getter
    private String upbitParameter;

    OrderSide(String upbitParameter) {
        this.upbitParameter = upbitParameter;
    }

}
