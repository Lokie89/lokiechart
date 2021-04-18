package com.lokiechart.www.dao.candle;

import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleParameter;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface CandleRepository {
    List<CandleResponse> getCandles(UpbitMinuteCandleParameter parameter);
}
