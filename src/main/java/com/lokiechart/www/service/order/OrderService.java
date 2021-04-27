package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public interface OrderService {
    void tradeByAccount(String email, final CandleMinute candleMinute);
}
