package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class AccountResponse {
    private String email;
    private Set<OrderStrategy> buyTradeStrategies;
    private Set<OrderStrategy> sellTradeStrategies;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
    private int totalSeed;
    private int maxBuyMarket;
    private int scaleTradeCount;

    public OrderParameters findBuyStrategically(final CandleMinute candleMinute, final AssetResponses assetResponses) {
        if (assetResponses.existAssetSize() > maxBuyMarket) {
            return null;
        }
        final int investSeed = totalSeed == 0 ? assetResponses.getTotalSeed() : totalSeed;
        final int onceInvestKRW = investSeed / (int) Math.pow(2, scaleTradeCount) / maxBuyMarket;
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        for (OrderStrategy orderStrategy : buyTradeStrategies) {
            if (orderStrategy.getCandleMinute().equals(candleMinute)) {
                matchedOrderParameters.addAll(orderStrategy.matchBuy(assetResponses, onceInvestKRW));
            }
        }

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filter(decidedMarket);
            return matchedOrderParameters;
        }
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        return matchedOrderParameters;
    }

    public OrderParameters findSellStrategically(final AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        for (OrderStrategy orderStrategy : sellTradeStrategies) {
            matchedOrderParameters.addAll(orderStrategy.matchSell(assetResponses));
        }

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
