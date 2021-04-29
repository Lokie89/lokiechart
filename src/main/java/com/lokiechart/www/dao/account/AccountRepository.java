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
//            .decidedMarket(Arrays.asList(decidedMarkets))
            ;

    private final Account.AccountBuilder rjsgml = Account.builder()
            .email("dlrjsgmlv@nate.com")
            .excludeMarket(Arrays.asList(excludeMarkets))
//            .decidedMarket(Arrays.asList(decidedMarkets))
            ;
    //    OrderStrategy buy1 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(200000).orderType(OrderType.UPPERMARKET).scaleTradingPercent(10).build();
    private final OrderStrategy buy1 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(50000).orderType(OrderType.LIMIT).scaleTradingPercent(10).build();
    private final OrderStrategy buy2 = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(10000).orderType(OrderType.LIMIT).scaleTradingPercent(10).build();
    private final OrderStrategy sell1 = OrderStrategy.builder().candleMinute(CandleMinute.FIVE).tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS).onceInvestKRW(0).orderType(OrderType.LIMIT).build();

    private final Map<String, Account> accountMap = new HashMap<>();

    public AccountRepository() {
        Set<OrderStrategy> buyAccountStrategies1 = new HashSet<>();
        Set<OrderStrategy> buyAccountStrategies2 = new HashSet<>();
        Set<OrderStrategy> sellAccountStrategies = new HashSet<>();
        buyAccountStrategies1.add(buy1);
        buyAccountStrategies2.add(buy2);
        sellAccountStrategies.add(sell1);
        Account dhtjdfhr = tjdfhr.buyTradeStrategies(buyAccountStrategies1).sellTradeStrategies(sellAccountStrategies).build();
        Account dlrjsgml = rjsgml.buyTradeStrategies(buyAccountStrategies2).sellTradeStrategies(sellAccountStrategies).build();
        accountMap.put("tjdfhrdk10@naver.com", dhtjdfhr);
        accountMap.put("dlrjsgmlv@nate.com", dlrjsgml);
    }


    public Account findByEmail(final String email) {
        return accountMap.get(email);
    }

    public Set<Account> findByCandleMinute(final CandleMinute candleMinute) {
        List<Account> accounts = new ArrayList<>(accountMap.values());
        return accounts.stream().filter(account -> account.haveOrderStrategyByCandleMinute(candleMinute)).collect(Collectors.toSet());
    }

    public List<Account> findAll() {
        System.out.println(accountMap);
        System.out.println(accountMap.values());
        return new ArrayList<>(accountMap.values());
    }
}
