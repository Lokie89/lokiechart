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
    private String email;
    private Set<OrderStrategy> buyTradeStrategies;
    private Set<OrderStrategy> sellTradeStrategies;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
    private int totalSeed;
    private int maxBuyMarket;
    private int totalTradeCount;
    @Builder.Default
    private boolean buyFlag = true;
    @Builder.Default
    private boolean sellFlag = true;

    @Builder
    public Account(String email, Set<OrderStrategy> buyTradeStrategies, Set<OrderStrategy> sellTradeStrategies,
                   List<String> excludeMarket, List<String> decidedMarket, int totalSeed, int maxBuyMarket,
                   int totalTradeCount, boolean buyFlag, boolean sellFlag) {
        this.email = email;
        this.buyTradeStrategies = buyTradeStrategies;
        this.sellTradeStrategies = sellTradeStrategies;
        this.excludeMarket = excludeMarket;
        this.decidedMarket = decidedMarket;
        this.totalSeed = totalSeed;
        this.maxBuyMarket = maxBuyMarket == 0 ? 10 : maxBuyMarket;
        this.totalTradeCount = totalTradeCount == 0 ? 3 : totalTradeCount;
        this.buyFlag = buyFlag;
        this.sellFlag = sellFlag;
    }

    public boolean haveOrderStrategyByCandleMinute(CandleMinute candleMinute) {
        return buyTradeStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.getCandleMinute().equals(candleMinute));
    }
}
