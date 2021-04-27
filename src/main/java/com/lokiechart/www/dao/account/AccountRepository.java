package com.lokiechart.www.dao.account;

import com.lokiechart.www.batch.BuyTradeStrategy;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Component
public class AccountRepository {
    public Account findByEmail(final String email) {
        Set<OrderStrategy> accountStrategies = new HashSet<>();
        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.ONE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        return Account.builder().email("tjdfhrdk10@naver.com").buyTradeStrategies(accountStrategies).build();
    }

    public Set<Account> findByCandleMinute(final CandleMinute candleMinute) {
        Set<OrderStrategy> accountStrategies = new HashSet<>();
        accountStrategies.add(OrderStrategy.builder().candleMinute(CandleMinute.ONE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).onceInvestKRW(5000).orderType(OrderType.LIMIT).build());
        List<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().email("tjdfhrdk10@naver.com").buyTradeStrategies(accountStrategies).build());
        return accounts.stream().filter(account -> account.haveOrderStrategyByCandleMinute(candleMinute)).collect(Collectors.toSet());
    }
}
