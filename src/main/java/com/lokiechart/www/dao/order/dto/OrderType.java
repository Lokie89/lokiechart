package com.lokiechart.www.dao.order.dto;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public enum OrderType {
    LIMIT("limit"),
    UPPERMARKET("price"),
    DOWNERMARKET("market"),
    ;

    @Getter
    private String parameter;

    OrderType(String parameter) {
        this.parameter = parameter;
    }
}
