package com.lokiechart.www.dao.market;

import com.lokiechart.www.dao.market.dto.MarketResponse;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface MarketRepository {
    List<MarketResponse> getMarkets();
}
