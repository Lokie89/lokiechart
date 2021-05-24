package com.lokiechart.www.service.strategy.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategies;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */
@ToString
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccountStrategyResponse {
    private final Logger logger = LoggerFactory.getLogger(AccountStrategyResponse.class);
    private AccountResponse accountResponse;
    private OrderStrategies orderStrategies;

    public boolean isContainsCandleMinute(CandleMinute candleMinute) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.equalsByCandleMinute(candleMinute));
    }

    public boolean isContainsOrderSide(OrderSide orderSide) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.equalsByOrderSide(orderSide));
    }

    public OrderParameters findSellStrategically(AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        if (!accountResponse.isSellFlag()) {
            return matchedOrderParameters;
        }

        for (OrderStrategy orderStrategy : orderStrategies) {
            matchedOrderParameters.addAll(orderStrategy.matchSell(assetResponses, accountResponse));
        }

        if (matchedOrderParameters.isEmpty()) {
            return matchedOrderParameters;
        }

        List<String> decidedMarket = accountResponse.getDecidedMarket();
        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filterMarkets(decidedMarket);
            return matchedOrderParameters;
        }
        List<String> excludeMarket = accountResponse.getDecidedMarket();
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        return matchedOrderParameters;
    }
}
