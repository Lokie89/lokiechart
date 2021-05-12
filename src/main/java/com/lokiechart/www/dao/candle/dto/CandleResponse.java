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
    OrderParameter toBuyOrderParameter(OrderSide orderSide, Integer totalCost, OrderType orderType);
    OrderParameter toSellOrderParameter(OrderSide orderSide, Double volume, OrderType orderType);
    Double compareVolumePercentage(CandleResponse compare);
    Double compareTradePricePercentage(CandleResponse compare);
    Double compareUnderBollingerBands();
    Double compareOverBollingerBands();
    Double getTradePrice();
    String getMarket();
    void setBollingerBands(double middle, double deviation);
    LocalDateTime getCandleDateTimeKST();
    double getIncreasePercent();
    String toLog();
    void setRsi(double rsi);
    double getRsi();
    void setAverageUp(double averageUp);
    double getAverageUp();
    void setAverageDown(double averageDown);
    double getAverageDown();
    double getChangePrice();
    Double compareUnderMiddleBands();
    Double compareOverMiddleBands();
}
