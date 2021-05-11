package com.lokiechart.www.dao.account;

import com.lokiechart.www.batch.BuyTradeStrategy;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.batch.SellTradeStrategy;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Component
public class AccountRepository {
    private final String[] excludeMarkets = {"BTC", "ETH", "XRP", "ADA", "DOGE", "DOT", "LTC", "BCH", "LINK", "VET", "XLM", "THETA", "TRX"};
//    private final String[] decidedMarkets = {"SC", "MOC", "SOLVE", "STMX", "IQ", "DKA"};

    private final Account.AccountBuilder tjdfhr = Account.builder()
            .email("tjdfhrdk10@naver.com")
            .excludeMarket(Arrays.asList(excludeMarkets))
            .maxBuyMarket(20)
            .totalSeed(6000000)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
//            .decidedMarket(Arrays.asList(decidedMarkets))
            ;

    private final Account.AccountBuilder tjdals = Account.builder()
            .email("01099589323@hanmail.net")
            .excludeMarket(Arrays.asList(excludeMarkets))
            .totalSeed(1600000)
            .maxBuyMarket(20)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
//            .decidedMarket(Arrays.asList(decidedMarkets))
            ;
    //    OrderStrategy buy1 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(200000).orderType(OrderType.UPPERMARKET).scaleTradingPercent(10).build();
    private final OrderStrategy buy = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).orderType(OrderType.LIMIT).scaleTradingPercent(8).build();
    private final OrderStrategy sell1 = OrderStrategy.builder().candleMinute(CandleMinute.FIVE).tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS).orderType(OrderType.LIMIT).incomePercent(5).build();
//    private final OrderStrategy sell2 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDSTWICE).orderType(OrderType.DOWNERMARKET).incomePercent(1).build();

    private final Map<String, Account> accountMap = new HashMap<>();

    public AccountRepository() {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        buyAccountStrategies.add(buy);
        sellAccountStrategies.add(sell1);
        Account dhtjdfhr = tjdfhr.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build();
        Account dhtjdals = tjdals.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build();
        accountMap.put(dhtjdfhr.getEmail(), dhtjdfhr);
        accountMap.put(dhtjdals.getEmail(), dhtjdals);
    }


    public Account findByEmail(final String email) {
        return accountMap.get(email);
    }

    public Set<Account> findByCandleMinute(final CandleMinute candleMinute) {
        List<Account> accounts = new ArrayList<>(accountMap.values());
        return accounts.stream().filter(account -> account.haveOrderStrategyByCandleMinute(candleMinute)).collect(Collectors.toSet());
    }

    public List<Account> findAll() {
        return new ArrayList<>(accountMap.values());
    }
}
