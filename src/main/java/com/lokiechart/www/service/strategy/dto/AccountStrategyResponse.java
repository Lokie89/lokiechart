package com.lokiechart.www.service.strategy.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.service.order.dto.OrderStrategyCandleTime;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private Set<OrderStrategy> orderStrategies;

    public boolean filterByCandleMinute(CandleMinute candleMinute) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.equalsByCandleMinute(candleMinute));
    }

    public boolean filterByOrderSide(OrderSide orderSide) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.equalsByOrderSide(orderSide));
    }

    // TODO : 물타는 전략 : 최근 매수 * 2
    public OrderParameters findBuyStrategically(final AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        if (!accountResponse.isBuyFlag() || orderStrategies.isEmpty()) {
            return matchedOrderParameters;
        }

        final int totalSeed = accountResponse.getTotalSeed();
        final int totalTradeCount = accountResponse.getTotalTradeCount();
        final int maxBuyMarket = accountResponse.getMaxBuyMarket();
        final int investSeed = totalSeed == 0 ? assetResponses.getTotalSeed() : totalSeed;
        final int onceInvestKRW = investSeed / totalTradeCount / maxBuyMarket;

        for (OrderStrategy orderStrategy : orderStrategies) {
            matchedOrderParameters.intersect(orderStrategy.matchBuy(assetResponses, onceInvestKRW));
        }

        if (matchedOrderParameters.isEmpty()) {
            return matchedOrderParameters;
        }

        final List<String> decidedMarket = accountResponse.getDecidedMarket();

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filterMarkets(decidedMarket);
            return matchedOrderParameters;
        }

        final int alreadyExistAndPlusSize = assetResponses.existAssetSize() + matchedOrderParameters.size();
        if (alreadyExistAndPlusSize > maxBuyMarket) {
            logger.warn(accountResponse.getEmail() + " " + maxBuyMarket + " 자산 수에 가득 참");
            matchedOrderParameters.filterAlreadyOwnAndAddCount(assetResponses, maxBuyMarket - assetResponses.existAssetSize());
        }

        final List<String> excludeMarket = accountResponse.getExcludeMarket();
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        final double remainBaseCurrency = assetResponses.getBaseCurrency() == null ? 0 : assetResponses.getBaseCurrency();
        final int possiblePurchaseCount = (int) (remainBaseCurrency / onceInvestKRW);
        return matchedOrderParameters.copy(0, Integer.min(matchedOrderParameters.size(), possiblePurchaseCount));
    }

    public OrderStrategyCandleTime toOrderStrategyCandleTime() {
        return new OrderStrategyCandleTime(this.orderStrategies);
    }
}
