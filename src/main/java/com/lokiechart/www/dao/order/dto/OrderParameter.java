package com.lokiechart.www.dao.order.dto;

import com.lokiechart.www.dao.asset.dto.AssetResponses;

import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public interface OrderParameter {
    Map<String, Object> toParameter();
    String getMarket();
    Double getPrice();
    String toLog();
    void setOrderParams(final int onceInvestKRW);
    boolean isAlreadyOwnAndNotCheapEnough(AssetResponses assetResponses, double scaleTradingPercent);
    OrderParameter copy();
}
