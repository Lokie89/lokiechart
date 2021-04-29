package com.lokiechart.www.dao.order.dto;

import lombok.Getter;

import java.util.Arrays;

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

    // TODO : Exception
    public static OrderSide getOrderSideByUpbitParameter(String upbitParameter){
        return Arrays.stream(OrderSide.values()).filter(orderSide -> orderSide.upbitParameter.equals(upbitParameter)).findAny().orElseThrow(RuntimeException::new);
    }

}
