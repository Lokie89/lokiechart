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
    private Logger logger = LoggerFactory.getLogger(AccountResponse.class);
    private String email;
    private Set<OrderStrategy> buyTradeStrategies;
    private Set<OrderStrategy> sellTradeStrategies;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
    private int totalSeed;
    private int maxBuyMarket;
    private int scaleTradeCount;


    // TODO : 물타는 전략
    public OrderParameters findBuyStrategically(final CandleMinute candleMinute, final AssetResponses assetResponses) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        if (assetResponses.existAssetSize() >= maxBuyMarket) {
            logger.warn(email + " " + maxBuyMarket + " 자산 수에 가득 참");
            return matchedOrderParameters;
        }
        final int investSeed = totalSeed == 0 ? assetResponses.getTotalSeed() : totalSeed;
        final int onceInvestKRW = investSeed / (int) Math.pow(2, scaleTradeCount) / maxBuyMarket;
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
