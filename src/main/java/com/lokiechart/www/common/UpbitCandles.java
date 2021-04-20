package com.lokiechart.www.common;

import com.lokiechart.www.dao.candle.dto.CandleResponses;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
@Component
public class Candles {
    static CandleResponses upbitOneMinuteCandles;
    static CandleResponses upbitThreeMinuteCandles;
    static CandleResponses upbitFiveMinuteCandles;
    static CandleResponses upbitTenMinuteCandles;
    static CandleResponses upbitFifteenMinuteCandles;
    static CandleResponses upbitThirtyMinuteCandles;
    static CandleResponses upbitSixtyMinuteCandles;
    static CandleResponses upbitTwoFortyMinuteCandles;
}
