package com.lokiechart.www.service.candle;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.candle.dto.CandleResponses;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
public interface CandleService {
    CandleResponses getMinuteCandles(CandleMinute candleMinute, String market, int candleCount);
    CandleResponses getMinuteCandles(CandleMinute candleMinute, String market, int candleCount, LocalDateTime to);
}
