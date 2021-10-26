package com.lokiechart.www.dao.strategy;

import com.lokiechart.www.batch.TradeStrategy;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.domain.account.Account;
import com.lokiechart.www.domain.strategy.AccountStrategy;
import org.modelmapper.ModelMapper;
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
            .scaleTradingPercent(10)
            .incomePercent(5)
            .excludeMarket(Arrays.asList(excludeMarkets))
            .build();

    private final Account tjdals = Account.builder()
            .email("01099589323@hanmail.net")
            .maxBuyMarket(20)
            .totalSeed(1600000)
            .totalTradeCount(2)
            .buyFlag(false)
            .sellFlag(true)
            .scaleTradingPercent(10)
            .incomePercent(5)
            .excludeMarket(Arrays.asList(excludeMarkets))
            .build();

    private final OrderStrategy fourTimeBollinger
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE)
            .candleMinute(CandleMinute.THREE)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.BUY)
            .build();

    private final OrderStrategy twoTimeBollinger
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE)
            .candleMinute(CandleMinute.THREE)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.BUY)
            .build();

    private final OrderStrategy rsi65
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.RSI_UNDERSIXTYFIVE)
            .candleMinute(CandleMinute.DAY)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.BUY)
            .build();

    private final OrderStrategy btc30m
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.BTCLINE120_INCREASING)
            .candleMinute(CandleMinute.THIRTY)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.BUY)
            .build();

    private final OrderStrategy btc60m
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.BTCLINE120_INCREASING)
            .candleMinute(CandleMinute.SIXTY)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.BUY)
            .build();


    private final OrderStrategy sellOrderStrategy
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS)
            .candleMinute(CandleMinute.FIVE)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.SELL)
            .build();

    private final OrderStrategy sellOrderStrategy2
            = OrderStrategy.builder()
            .tradeStrategy(TradeStrategy.TRADEPRICE_OVERFIVEPERCENT)
            .candleMinute(CandleMinute.THREE)
            .orderType(OrderType.LIMIT)
            .orderSide(OrderSide.SELL)
            .build();

    private final List<AccountStrategy> all = new ArrayList<>();

    public UpbitAccountStrategyRepository(ModelMapper modelMapper) {
        AccountResponse tjdfhrResponse = modelMapper.map(tjdfhr, AccountResponse.class);
        AccountResponse tjdalsResponse = modelMapper.map(tjdals, AccountResponse.class);

        final AccountStrategy tjdfhrBuyAccountStrategy = AccountStrategy.builder().accountResponse(tjdfhrResponse).orderStrategies(rsi65, fourTimeBollinger).build();
        final AccountStrategy tjdfhrBuyAccountStrategy2 = AccountStrategy.builder().accountResponse(tjdfhrResponse).orderStrategies(rsi65, twoTimeBollinger, btc30m, btc60m).build();
        final AccountStrategy tjdfhrSellAccountStrategy = AccountStrategy.builder().accountResponse(tjdfhrResponse).orderStrategies(sellOrderStrategy).build();
        final AccountStrategy tjdfhrSellAccountStrategy2 = AccountStrategy.builder().accountResponse(tjdfhrResponse).orderStrategies(sellOrderStrategy2).build();

        final AccountStrategy tjdalsBuyAccountStrategy = AccountStrategy.builder().accountResponse(tjdalsResponse).orderStrategies(fourTimeBollinger, rsi65).build();
        final AccountStrategy tjdalsSellAccountStrategy = AccountStrategy.builder().accountResponse(tjdalsResponse).orderStrategies(sellOrderStrategy).build();

        all.add(tjdfhrBuyAccountStrategy);
        all.add(tjdfhrBuyAccountStrategy2);
        all.add(tjdfhrSellAccountStrategy);
        all.add(tjdfhrSellAccountStrategy2);

        all.add(tjdalsBuyAccountStrategy);
        all.add(tjdalsSellAccountStrategy);
    }


    public List<AccountStrategy> getAll() {
        return all;
    }

}
