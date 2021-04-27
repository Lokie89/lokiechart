package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public interface CandleResponse {
    OrderParameter toOrderParameter(OrderSide orderSide, Integer totalCost, OrderType orderType);
    Double compareVolumePercentage(CandleResponse compare);
    Double compareTradePricePercentage(CandleResponse compare);
    Double compareUnderBollingerBands();
    Double compareOverBollingerBands();
    Double getTradePrice();
    String getMarket();
    void setBollingerBands(double middle, double deviation);
    LocalDateTime getCandleDateTimeKST();
}
