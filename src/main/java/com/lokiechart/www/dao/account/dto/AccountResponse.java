package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
public class AccountResponse {
    private String email;
    private Set<OrderStrategy> buyTradeStrategies;
    private Set<OrderStrategy> sellTradeStrategies;
    private Double onceInvestKRW;
    private OrderType orderType;
    private List<String> excludeMarket;
    private List<String> decidedMarket;

    public OrderParameters findStrategically() {
        Set<String> markets = new LinkedHashSet<>();
        CandleResponses buyMatchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        for (OrderStrategy orderStrategy : buyTradeStrategies) {
            CandleResponses candleResponses = orderStrategy.match(markets);
            buyMatchedCandleResponses.addAll(candleResponses);
            markets.addAll(candleResponses.getMarkets());
        }

        OrderParameters matchedOrderParameters =
                new OrderParameters(
                        buyMatchedCandleResponses.getCandleResponses()
                                .stream()
                                .map(candleResponse -> candleResponse.toOrderParameter(OrderSide.BUY, onceInvestKRW, orderType))
                                .collect(Collectors.toList()));
        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filter(decidedMarket);
            return matchedOrderParameters;
        }
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        return matchedOrderParameters;
    }
}
