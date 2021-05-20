package com.lokiechart.www.dao.asset.dto;

import com.lokiechart.www.dao.order.dto.OrderParameter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/27
 */
public interface AssetResponse {
    boolean isSameMarket(String market);
    Double avgBuyPricePercent(double tradePrice);
    String getMarketCurrency();
    Double getBalance();
    OrderParameter toSellParameter();
    Integer getTotalCost();
    boolean isExistTotalBalance();
    boolean isExistSellBalance();
    AssetResponse getApplyPrice(Double price);
    boolean isBaseCurrency();
    boolean isPossibleOrder();
    void useBaseCurrency(double currency);
}
