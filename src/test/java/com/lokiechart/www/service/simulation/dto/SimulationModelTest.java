package com.lokiechart.www.service.simulation.dto;

import com.lokiechart.www.batch.*;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.common.ThreadSleep;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.asset.dto.UpbitAssetResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.market.dto.MarketResponse;
import com.lokiechart.www.dao.market.dto.MarketResponses;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.service.candle.UpbitCandleService;
import com.lokiechart.www.service.market.MarketService;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/05/20
 */
@DisplayName("시뮬레이션 모델 테스트")
@SpringBootTest
class SimulationModelTest {

    @Autowired
    MarketService upbitMarketService;

    @Autowired
    UpbitCandleService upbitCandleService;


    private final String[] excludeMarkets = {"BTC", "ETH", "XRP", "ADA", "DOGE", "DOT", "LTC", "BCH", "LINK", "VET", "XLM", "THETA", "TRX"};

    @DisplayName("buy 테스트")
    @Test
    void buyTest() {
        AccountResponse accountResponse = AccountResponse.builder().buyFlag(true).sellFlag(true).totalSeed(1000000).excludeMarket(Arrays.asList(excludeMarkets)).incomePercent(5).scaleTradingPercent(10).totalTradeCount(2).build();

        Set<OrderStrategy> buyOrderStrategySet = new HashSet<>();
        buyOrderStrategySet.add(OrderStrategy.builder().orderType(OrderType.LIMIT).candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).build());
        Set<OrderStrategy> sellOrderStrategySet = new HashSet<>();
        sellOrderStrategySet.add(OrderStrategy.builder().orderType(OrderType.LIMIT).candleMinute(CandleMinute.FIVE).tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS).build());
        OrderStrategies buyOrderStrategies = new OrderStrategies(buyOrderStrategySet);
        OrderStrategies sellOrderStrategies = new OrderStrategies(sellOrderStrategySet);


        AccountStrategyResponse buyAccountStrategyResponse = AccountStrategyResponse.builder().accountResponse(accountResponse).orderStrategies(buyOrderStrategies).build();
        AccountStrategyResponse sellAccountStrategyResponse = AccountStrategyResponse.builder().accountResponse(accountResponse).orderStrategies(sellOrderStrategies).build();

        List<AccountStrategyResponse> accountStrategyResponseList = new ArrayList<>();
        accountStrategyResponseList.add(buyAccountStrategyResponse);
        accountStrategyResponseList.add(sellAccountStrategyResponse);

        List<AssetResponse> assetResponseList = new ArrayList<>();
        assetResponseList.add(UpbitAssetResponse.builder().currency("KRW").unitCurrency("KRW").balance(1000000.0).avgBuyPrice(null).build());
        AssetResponses assetResponses = new AssetResponses(assetResponseList);


        Map<CandleMinute, Map<String, CandleResponses>> candles = new ConcurrentHashMap<>();
        Map<String, CandleResponses> threeCandleResponsesMap = new ConcurrentHashMap<>();
        Map<String, CandleResponses> fiveCandleResponsesMap = new ConcurrentHashMap<>();
        MarketResponses marketResponses = upbitMarketService.getAll();
        final int howGetCandles = 200;
        for (MarketResponse marketResponse : marketResponses) {
            final String market = marketResponse.getMarket();
            threeCandleResponsesMap.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(), Integer.MAX_VALUE));
            CandleResponses responses3 = upbitCandleService.get3MinutesCandles(market, howGetCandles);
            CandleResponses origin3 = threeCandleResponsesMap.get(market);
            origin3.addAll(responses3);
            ThreadSleep.doSleep(100);

            fiveCandleResponsesMap.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(), Integer.MAX_VALUE));
            CandleResponses responses5 = upbitCandleService.get5MinutesCandles(market, howGetCandles);
            CandleResponses origin5 = threeCandleResponsesMap.get(market);
            origin5.addAll(responses5);
            ThreadSleep.doSleep(100);
        }
        candles.put(CandleMinute.THREE, threeCandleResponsesMap);
        candles.put(CandleMinute.FIVE, fiveCandleResponsesMap);

        SimulationModel simulationModel = UpbitSimulationModel.builder().accountStrategyResponses(new AccountStrategyResponses(accountStrategyResponseList)).assetResponses(assetResponses).simulateCandles(candles).build();
        simulationModel.experiment();
    }
}
