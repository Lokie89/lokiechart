package com.lokiechart.www.dao.order.dto;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public enum UpbitOrderSide {
    BUY("bid"),
    SELL("ask"),
    ;

    @Getter
    private String parameter;

    UpbitOrderSide(String parameter) {
        this.parameter = parameter;
    }

}
