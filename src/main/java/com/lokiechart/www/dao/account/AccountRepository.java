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
    private final String[] decidedMarkets = {"SC", "MOC", "SOLVE", "STMX", "IQ", "DKA"};

    private final Account.AccountBuilder accountBuilder = Account.builder().email("tjdfhrdk10@naver.com").excludeMarket(Arrays.asList(excludeMarkets)).decidedMarket(Arrays.asList(decidedMarkets));
    OrderStrategy buy1 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(20000).orderType(OrderType.UPPERMARKET).build();
    OrderStrategy buy2 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(10000).orderType(OrderType.LIMIT).build();
    OrderStrategy sell1 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS).onceInvestKRW(0).orderType(OrderType.LIMIT).build();

    public Account findByEmail(final String email) {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
        buyAccountStrategies.add(buy1);
        buyAccountStrategies.add(buy2);
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        sellAccountStrategies.add(sell1);
        return accountBuilder.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build();
    }

    public Set<Account> findByCandleMinute(final CandleMinute candleMinute) {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
        buyAccountStrategies.add(buy1);
        buyAccountStrategies.add(buy2);
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        sellAccountStrategies.add(sell1);
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountBuilder.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build());
        return accounts.stream().filter(account -> account.haveOrderStrategyByCandleMinute(candleMinute)).collect(Collectors.toSet());
    }

    public List<Account> findAll() {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
        buyAccountStrategies.add(buy1);
        buyAccountStrategies.add(buy2);
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        sellAccountStrategies.add(sell1);
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountBuilder.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build());
        return accounts;
    }
}
