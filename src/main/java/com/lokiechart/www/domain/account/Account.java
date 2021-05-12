package com.lokiechart.www.domain.account;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@EqualsAndHashCode(of = "email")
@ToString
@Getter
public class Account {
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

    @Builder
    public Account(String email, List<String> excludeMarket, List<String> decidedMarket, int totalSeed, int maxBuyMarket,
                   int totalTradeCount, boolean buyFlag, boolean sellFlag, double scaleTradingPercent, double incomePercent) {
        this.email = email;
        this.excludeMarket = excludeMarket;
        this.decidedMarket = decidedMarket;
        this.totalSeed = totalSeed;
        this.maxBuyMarket = maxBuyMarket == 0 ? 10 : maxBuyMarket;
        this.totalTradeCount = totalTradeCount == 0 ? 3 : totalTradeCount;
        this.buyFlag = buyFlag;
        this.sellFlag = sellFlag;
        this.scaleTradingPercent = scaleTradingPercent;
        this.incomePercent = incomePercent;
    }

}
