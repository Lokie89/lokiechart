package com.lokiechart.www.dao.order.dto;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public enum UpbitOrderType {
    LIMIT("limit"),
    UPPERMARKET("price"),
    DOWNERMARKET("market"),
    ;

    @Getter
    private String parameter;

    UpbitOrderType(String parameter) {
        this.parameter = parameter;
    }
}
