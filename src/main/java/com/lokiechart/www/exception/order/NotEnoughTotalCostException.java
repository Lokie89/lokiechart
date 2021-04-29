package com.lokiechart.www.exception.order;

import lombok.ToString;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
@ToString(callSuper = true)
public class NotEnoughTotalCostException extends RuntimeException {
    public NotEnoughTotalCostException(String msg) {
        super(msg);
    }
}
