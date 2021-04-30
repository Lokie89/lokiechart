package com.lokiechart.www.domain.account;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@EqualsAndHashCode(of = "email")
@ToString
@Getter
public class Account {
    private final String email;
    private final Set<OrderStrategy> buyTradeStrategies;
    private final Set<OrderStrategy> sellTradeStrategies;
    private final List<String> excludeMarket;
    private final List<String> decidedMarket;
    private final int totalSeed;
    private final int maxBuyMarket;
    private final int totalTradeCount;

    @Builder
    public Account(String email, Set<OrderStrategy> buyTradeStrategies, Set<OrderStrategy> sellTradeStrategies,
                   List<String> excludeMarket, List<String> decidedMarket, int totalSeed, int maxBuyMarket,
                   int totalTradeCount) {
        this.email = email;
        this.buyTradeStrategies = buyTradeStrategies;
        this.sellTradeStrategies = sellTradeStrategies;
        this.excludeMarket = excludeMarket;
        this.decidedMarket = decidedMarket;
        this.totalSeed = totalSeed;
        this.maxBuyMarket = maxBuyMarket == 0 ? 10 : maxBuyMarket;
        this.totalTradeCount = totalTradeCount == 0 ? 3 : totalTradeCount;
    }

    public boolean haveOrderStrategyByCandleMinute(CandleMinute candleMinute) {
        return buyTradeStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.getCandleMinute().equals(candleMinute));
    }
}
