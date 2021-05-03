package com.lokiechart.www.dao.order.dto;

import lombok.Getter;

import java.util.Arrays;

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
    private String upbitParameter;

    OrderType(String upbitParameter) {
        this.upbitParameter = upbitParameter;
    }

    public static OrderType getByUpbitParameter(String upbitParameter) {
        return Arrays.stream(OrderType.values()).filter(orderType -> orderType.upbitParameter.equals(upbitParameter)).findFirst().orElseThrow(RuntimeException::new);
    }
}
