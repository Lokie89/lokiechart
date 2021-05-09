package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private Set<OrderStrategy> buyTradeStrategies;
    private Set<OrderStrategy> sellTradeStrategies;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
    private int totalSeed;
    private int maxBuyMarket;
    private int totalTradeCount;
    private boolean buyFlag;
    private boolean sellFlag;


    // TODO : 물타는 전략 : 최근 매수 * 2
    public OrderParameters findBuyStrategically(final CandleMinute candleMinute, final AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        if (!buyFlag) {
            return matchedOrderParameters;
        }

        final int investSeed = totalSeed == 0 ? assetResponses.getTotalSeed() : totalSeed;
        final int onceInvestKRW = investSeed / totalTradeCount / maxBuyMarket;

        for (OrderStrategy orderStrategy : buyTradeStrategies) {
            if (orderStrategy.getCandleMinute().equals(candleMinute)) {
                matchedOrderParameters.addAll(orderStrategy.matchBuy(assetResponses, onceInvestKRW));
            }
        }

        if (matchedOrderParameters.isEmpty()) {
            return matchedOrderParameters;
        }

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filterMarkets(decidedMarket);
            return matchedOrderParameters;
        }

        final int alreadyExistAndPlusSize = assetResponses.existAssetSize() + matchedOrderParameters.size();
        if (alreadyExistAndPlusSize > maxBuyMarket) {
            logger.warn(email + " " + maxBuyMarket + " 자산 수에 가득 참");
            matchedOrderParameters.filterAlreadyOwnAndAddCount(assetResponses, maxBuyMarket - assetResponses.existAssetSize());
        }
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        final double remainBaseCurrency = assetResponses.getBaseCurrency() == null ? 0 : assetResponses.getBaseCurrency();
        final int possiblePurchaseCount = (int) (remainBaseCurrency / onceInvestKRW);
        return matchedOrderParameters.copy(0, Integer.min(matchedOrderParameters.size(), possiblePurchaseCount));
    }

    public OrderParameters findSellStrategically(final AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        if (!sellFlag) {
            return matchedOrderParameters;
        }
        for (OrderStrategy orderStrategy : sellTradeStrategies) {
            matchedOrderParameters.addAll(orderStrategy.matchSell(assetResponses));
        }

        if (matchedOrderParameters.isEmpty()) {
            return matchedOrderParameters;
        }

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filterMarkets(decidedMarket);
            return matchedOrderParameters;
        }
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        return matchedOrderParameters;
    }

}
