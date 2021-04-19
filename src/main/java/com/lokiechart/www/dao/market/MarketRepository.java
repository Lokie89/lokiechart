package com.lokiechart.www.dao.market;

import com.lokiechart.www.dao.market.dto.UpbitMarketResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface MarketRepository {
    UpbitMarketResponses getMarkets();
}
