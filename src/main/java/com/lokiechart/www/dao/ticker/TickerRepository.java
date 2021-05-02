package com.lokiechart.www.dao.ticker;

import com.lokiechart.www.dao.ticker.dto.TickerResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
public interface TickerRepository {
    TickerResponses getTicker(String markets);
}
