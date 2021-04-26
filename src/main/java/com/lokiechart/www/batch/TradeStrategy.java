package com.lokiechart.www.batch;

import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.order.dto.OrderSide;

/**
 * @author SeongRok.Oh
 * @since 2021/04/26
 */
public interface TradeStrategy {
    CandleResponse match(CandleResponses candleResponses);
    OrderSide getOrderSide();
}
