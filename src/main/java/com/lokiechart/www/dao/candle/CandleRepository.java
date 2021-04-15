package com.lokiechart.www.dao.candle;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface CandleRepository {
    String getCandles(CandleMinute minute, String market, int candleCount);
}
