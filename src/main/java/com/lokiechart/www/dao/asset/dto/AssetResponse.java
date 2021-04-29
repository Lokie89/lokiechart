package com.lokiechart.www.dao.asset.dto;

import com.lokiechart.www.dao.order.dto.OrderParameter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/27
 */
public interface AssetResponse {
    boolean isSameMarket(String market);
    Double avgBuyPricePercent(double tradePrice);
    String getCurrency();
    Double getBalance();
    OrderParameter toSellParameter();
    Integer getTotalCost();
}
