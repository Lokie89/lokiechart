package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.UpbitOrderSide;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public interface CandleResponse {
    OrderParameter toOrderParameter(UpbitOrderSide orderSide, Double totalCost);
}
