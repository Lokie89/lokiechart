package com.lokiechart.www.service.ticker;

import com.lokiechart.www.dao.ticker.dto.TickerResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
public interface TickerService {
    TickerResponses getTickersByMarkets(String markets);
}
