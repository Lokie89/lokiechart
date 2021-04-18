package com.lokiechart.www.dao.candle;

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
    private int number;

    CandleMinute(int number) {
        this.number = number;
    }

}
