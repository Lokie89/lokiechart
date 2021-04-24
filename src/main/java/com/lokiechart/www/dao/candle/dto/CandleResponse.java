package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.UpbitOrderSide;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public interface CandleResponse {
    OrderParameter toOrderParameter(UpbitOrderSide orderSide, Double totalCost);
    Double compareVolumeReplacePercentage(CandleResponse compare);
    Double compareTradePricePercentage(CandleResponse compare);
    Double compareUnderBollingerBands();
    Double getTradePrice();
    void setBollingerBands(double middle, double deviation);
    LocalDateTime getCandleDateTimeKST();
}
