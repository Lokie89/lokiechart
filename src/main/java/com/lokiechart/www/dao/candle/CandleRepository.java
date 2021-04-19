package com.lokiechart.www.dao.candle;

import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.candle.dto.GetParameterUrl;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface CandleRepository {
    CandleResponses getCandles(GetParameterUrl parameter);
}
