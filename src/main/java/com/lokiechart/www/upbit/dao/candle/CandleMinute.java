package com.lokiechart.www.upbit.dao.candle;

import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public enum CandleMinute {
    ONE(1),
    THREE(3),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    THIRTY(30),
    SIXTY(60),
    TWOFOURTY(240),
    ;

    @Getter
    private int num;

    CandleMinute(int num) {
        this.num = num;
    }
}
