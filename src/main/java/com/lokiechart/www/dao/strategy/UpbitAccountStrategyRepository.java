package com.lokiechart.www.dao.strategy;

import com.lokiechart.www.batch.BuyTradeStrategy;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.batch.SellTradeStrategy;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.domain.account.Account;
import com.lokiechart.www.domain.strategy.AccountStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */

@Component
public class UpbitAccountStrategyRepository implements AccountStrategyRepository {

    private final String[] excludeMarkets = {"BTC", "ETH", "XRP", "ADA", "DOGE", "DOT", "LTC", "BCH", "LINK", "VET", "XLM", "THETA", "TRX"};

    private final Account tjdfhr = Account.builder()
            .email("tjdfhrdk10@naver.com")
            .maxBuyMarket(20)
            .totalSeed(6000000)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
            .excludeMarket(Arrays.asList(excludeMarkets))
            .build();

    private final Account tjdals = Account.builder()
            .email("01099589323@hanmail.net")
            .maxBuyMarket(20)
            .totalSeed(1600000)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
            .excludeMarket(Arrays.asList(excludeMarkets))
            .build();

    private final OrderStrategy buyOrderStrategy
            = OrderStrategy.builder()
            .tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE)
            .candleMinute(CandleMinute.THREE)
            .orderType(OrderType.LIMIT)
            .build();


    private final OrderStrategy sellOrderStrategy
            = OrderStrategy.builder()
            .tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS)
            .candleMinute(CandleMinute.FIVE)
            .orderType(OrderType.LIMIT)
            .build();

    private final AccountStrategy tjdfhrBuyAccountStrategy = AccountStrategy.builder().account(tjdfhr).orderStrategies(buyOrderStrategy).build();
    private final AccountStrategy tjdfhrSellAccountStrategy = AccountStrategy.builder().account(tjdfhr).orderStrategies(sellOrderStrategy).build();

    private final AccountStrategy tjdalsBuyAccountStrategy = AccountStrategy.builder().account(tjdals).orderStrategies(buyOrderStrategy).build();
    private final AccountStrategy tjdalsSellAccountStrategy = AccountStrategy.builder().account(tjdals).orderStrategies(sellOrderStrategy).build();

    private final List<AccountStrategy> all = new ArrayList<>();

    public UpbitAccountStrategyRepository() {
        all.add(tjdfhrBuyAccountStrategy);
        all.add(tjdfhrSellAccountStrategy);
        all.add(tjdalsBuyAccountStrategy);
        all.add(tjdalsSellAccountStrategy);
    }

    public List<AccountStrategy> getAll() {
        return all;
    }

}
