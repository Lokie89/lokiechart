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
    private String[] excludeMarkets = {"BTC", "ETH", "XRP", "ADA", "DOGE", "DOT", "LTC", "BCH", "LINK", "VET", "XLM", "THETA", "TRX"};

    private Account.AccountBuilder accountBuilder = Account.builder().email("tjdfhrdk10@naver.com").excludeMarket(Arrays.asList(excludeMarkets));

    public Account findByEmail(final String email) {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
//        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.FIFTEEN).tradeStrategy(BuyTradeStrategy.TRADEPRICE_KEEPPRICEAFTERFIVETIMESVOL).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(10000).orderType(OrderType.UPPERMARKET).build());
        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        sellAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(SellTradeStrategy.BEFORETRADEPRICE_PRICEOVERANDFIVETIMESVOL).onceInvestKRW(0).orderType(OrderType.LIMIT).build());
        return accountBuilder.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build();
    }

    public Set<Account> findByCandleMinute(final CandleMinute candleMinute) {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
//        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.FIFTEEN).tradeStrategy(BuyTradeStrategy.TRADEPRICE_KEEPPRICEAFTERFIVETIMESVOL).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(10000).orderType(OrderType.UPPERMARKET).build());
        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        sellAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(SellTradeStrategy.BEFORETRADEPRICE_PRICEOVERANDFIVETIMESVOL).onceInvestKRW(0).orderType(OrderType.LIMIT).build());
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountBuilder.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build());
        return accounts.stream().filter(account -> account.haveOrderStrategyByCandleMinute(candleMinute)).collect(Collectors.toSet());
    }

    public List<Account> findAll() {
        Set<OrderStrategy> buyAccountStrategies = new HashSet<>();
//        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.FIFTEEN).tradeStrategy(BuyTradeStrategy.TRADEPRICE_KEEPPRICEAFTERFIVETIMESVOL).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(10000).orderType(OrderType.UPPERMARKET).build());
        buyAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        sellAccountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(SellTradeStrategy.BEFORETRADEPRICE_PRICEOVERANDFIVETIMESVOL).onceInvestKRW(0).orderType(OrderType.DOWNERMARKET).build());
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountBuilder.buyTradeStrategies(buyAccountStrategies).sellTradeStrategies(sellAccountStrategies).build());
        return accounts;
    }
}
