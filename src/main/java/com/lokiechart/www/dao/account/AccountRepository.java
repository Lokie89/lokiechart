package com.lokiechart.www.dao.account;

import com.lokiechart.www.batch.BuyTradeStrategy;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
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
        Set<OrderStrategy> accountStrategies = new HashSet<>();
//        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.FIFTEEN).tradeStrategy(BuyTradeStrategy.TRADEPRICE_KEEPPRICEAFTERFIVETIMESVOL).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(20000).orderType(OrderType.UPPERMARKET).build());
        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        return accountBuilder.buyTradeStrategies(accountStrategies).build();
    }

    public Set<Account> findByCandleMinute(final CandleMinute candleMinute) {
        Set<OrderStrategy> accountStrategies = new HashSet<>();
//        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.FIFTEEN).tradeStrategy(BuyTradeStrategy.TRADEPRICE_KEEPPRICEAFTERFIVETIMESVOL).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFIVETIMESINSIX).onceInvestKRW(20000).orderType(OrderType.UPPERMARKET).build());
        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        List<Account> accounts = new ArrayList<>();
        accounts.add(accountBuilder.buyTradeStrategies(accountStrategies).build());
        return accounts.stream().filter(account -> account.haveOrderStrategyByCandleMinute(candleMinute)).collect(Collectors.toSet());
    }
}
