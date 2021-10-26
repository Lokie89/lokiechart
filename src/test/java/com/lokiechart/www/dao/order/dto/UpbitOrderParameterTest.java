package com.lokiechart.www.dao.order.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategies;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.batch.TradeStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.domain.account.Account;
import com.lokiechart.www.service.asset.AssetService;
import com.lokiechart.www.service.candle.UpbitCandleService;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/05/28
 */
@DisplayName("업비트 주문 파라미터 테스트")
@SpringBootTest
public class UpbitOrderParameterTest {

    @Autowired
    UpbitCandleService upbitCandleService;

    @Autowired
    AssetService upbitAssetService;

    private final AccountResponse tjdfhr = AccountResponse.builder()
            .email("tjdfhrdk10@naver.com")
            .maxBuyMarket(20)
            .totalSeed(6000000)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
            .scaleTradingPercent(10)
            .incomePercent(5)
            .build();


    @Test
    void setOrderParamTest() {
        CandleResponses candleResponses = upbitCandleService.getMinuteCandles(CandleMinute.THREE, "KRW-QTUM", 30, LocalDateTime.of(2021, 5, 28, 0, 21, 5));
        CandleResponses dayCandleResponses = upbitCandleService.get1DayCandles("KRW-QTUM", 30, LocalDateTime.of(2021, 5, 28, 0, 21, 5));
        candleResponses.setBollingerBands(8);
        dayCandleResponses.setRsi(2);
        AccountStrategyResponse accountStrategy
                = AccountStrategyResponse.builder()
                .accountResponse(tjdfhr)
                .orderStrategies(new OrderStrategies(new LinkedHashSet<>(Arrays.asList(
                        OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(TradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).orderSide(OrderSide.BUY).orderType(OrderType.LIMIT).build(),
                        OrderStrategy.builder().candleMinute(CandleMinute.DAY).tradeStrategy(TradeStrategy.RSI_UNDERSIXTYFIVE).orderSide(OrderSide.BUY).orderType(OrderType.LIMIT).build()
                ))))
                .build();
        Map<CandleMinute, Map<String, CandleResponses>> candleMinuteMapMap = new ConcurrentHashMap<>();
        Map<String, CandleResponses> candleResponsesMap = new ConcurrentHashMap<>();
        candleResponsesMap.put("KRW-QTUM", candleResponses);

        Map<String, CandleResponses> candleResponsesMap2 = new ConcurrentHashMap<>();
        candleResponsesMap2.put("KRW-QTUM", dayCandleResponses);
        candleMinuteMapMap.put(CandleMinute.THREE, candleResponsesMap);
        candleMinuteMapMap.put(CandleMinute.DAY, candleResponsesMap2);

        OrderParameters orderParameters = accountStrategy.getOrderStrategies().getMatchedOrderParameters(candleMinuteMapMap);
        AssetResponses assetResponses = upbitAssetService.getAssets(accountStrategy.getAccountResponse());
        orderParameters.filterByAccount(accountStrategy, assetResponses);
        System.out.println(orderParameters);
    }
}
