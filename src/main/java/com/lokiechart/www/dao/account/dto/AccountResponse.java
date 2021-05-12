package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Slf4j
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class AccountResponse {
    private final Logger logger = LoggerFactory.getLogger(AccountResponse.class);
    private String email;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
    private int totalSeed;
    private int maxBuyMarket;
    private int totalTradeCount;
    private boolean buyFlag;
    private boolean sellFlag;
    private double scaleTradingPercent;
    private double incomePercent;

    public OrderParameters findSellStrategically(final AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        if (!sellFlag) {
            return matchedOrderParameters;
        }
//        for (OrderStrategy orderStrategy : sellTradeStrategies) {
//            matchedOrderParameters.addAll(orderStrategy.matchSell(assetResponses));
//        }

        if (matchedOrderParameters.isEmpty()) {
            return matchedOrderParameters;
        }

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            return matchedOrderParameters.filterMarkets(decidedMarket);
        }
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        return matchedOrderParameters;
    }

}
