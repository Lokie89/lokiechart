package com.lokiechart.www.batch;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.asset.AssetService;
import com.lokiechart.www.service.order.OrderService;
import com.lokiechart.www.service.order.dto.OrderStrategyCandleTime;
import com.lokiechart.www.service.strategy.AccountStrategyService;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/21
 */

/**
 * 1. AccountStrategy 가져옴
 * 2. AccountStrategy 와 CandleMinute 에 맞는 OrderParameters 추출, 캐시
 * 3. AccountStrategy 가 동일한 계정은 캐시된 OrderParameters 로 주문
 */
@Slf4j
@Component
public class UpbitOrderBatch {
    private final Logger logger = LoggerFactory.getLogger(UpbitOrderBatch.class);
    private final AccountService accountService;
    private final AssetService upbitAssetService;
    private final OrderService upbitOrderService;
    private final AccountStrategyService upbitAccountStrategyService;
    private final UpbitCandlesBatch upbitCandlesBatch;
    private AccountStrategyResponses accountStrategyResponses;

    public UpbitOrderBatch(AccountService accountService, OrderService upbitOrderService, AssetService upbitAssetService,
                           AccountStrategyService upbitAccountStrategyService, UpbitCandlesBatch upbitCandlesBatch) {
        this.accountService = accountService;
        this.upbitOrderService = upbitOrderService;
        this.upbitAssetService = upbitAssetService;
        this.upbitAccountStrategyService = upbitAccountStrategyService;
        this.upbitCandlesBatch = upbitCandlesBatch;
        init();
    }

    private void init() {
        updateAccountStrategy();
    }

    @Scheduled(cron = "${schedule.order.one-minute}")
    private void orderBuyOneMinuteTradeStrategy() {
        final CandleMinute candleMinute = CandleMinute.ONE;
        orderBuyTradeStrategy(candleMinute);
    }

    @Scheduled(cron = "${schedule.order.three-minutes}")
    private void orderBuyThreeMinutesTradeStrategy() {
        final CandleMinute candleMinute = CandleMinute.THREE;
        orderBuyTradeStrategy(candleMinute);
    }

    @Scheduled(cron = "${schedule.order.fifteen-minutes}")
    private void orderBuyFifteenMinutesTradeStrategy() {
        final CandleMinute candleMinute = CandleMinute.FIFTEEN;
        orderBuyTradeStrategy(candleMinute);
    }

    @Scheduled(cron = "${schedule.order.thirty-minutes}")
    private void orderBuyThirtyMinutesTradeStrategy() {
        final CandleMinute candleMinute = CandleMinute.THIRTY;
        orderBuyTradeStrategy(candleMinute);
    }

    @Scheduled(cron = "${schedule.order.one-hour}")
    private void orderBuyOneHourTradeStrategy() {
        final CandleMinute candleMinute = CandleMinute.SIXTY;
        orderBuyTradeStrategy(candleMinute);
    }

    @Scheduled(cron = "${schedule.order.one-day}")
    private void orderBuyOneDayTradeStrategy() {
        final CandleMinute candleMinute = CandleMinute.DAY;
        orderBuyTradeStrategy(candleMinute);
    }

    private final Map<OrderStrategyCandleTime, OrderParameters> cache = new ConcurrentHashMap<>();

    private void orderBuyTradeStrategy(final CandleMinute candleMinute) {
        AccountStrategyResponses filterBuyCandleMinuteResponses = accountStrategyResponses.filterCandleMinute(candleMinute).filterOrderSide(OrderSide.BUY);
        if (Objects.nonNull(filterBuyCandleMinuteResponses) && !filterBuyCandleMinuteResponses.isEmpty()) {
            filterBuyCandleMinuteResponses.getOrderStrategySet().forEach(
                    orderStrategies -> cache.put(OrderStrategyCandleTime.of(orderStrategies), orderStrategies.getMatchedOrderParameters()));

            for (AccountStrategyResponse accountStrategyResponse : filterBuyCandleMinuteResponses) {
                OrderStrategyCandleTime candleTime = OrderStrategyCandleTime.of(accountStrategyResponse.getOrderStrategies());
                OrderParameters matchParameters = cache.get(candleTime).copy();
                if (Objects.isNull(matchParameters) || matchParameters.isEmpty()) {
                    continue;
                }
                matchParameters.filterByAccount(accountStrategyResponse, upbitAssetService.getAssets(accountStrategyResponse.getAccountResponse()));
                upbitOrderService.buyByAccount(accountStrategyResponse, matchParameters);
            }
        }
    }


    @Scheduled(cron = "${schedule.order.all-minute}")
    private void orderSellTradeStrategy() {
        // TODO Response 의 메서드 분리
        AccountStrategyResponses filterSellResponses = accountStrategyResponses.filterOrderSide(OrderSide.SELL);
        if (Objects.nonNull(filterSellResponses) && !filterSellResponses.isEmpty()) {
            logger.info("ORDER SELL STRATEGY");
            Set<MarketCandleMinute> updateCandles = new HashSet<>();
            filterSellResponses.forEach(accountStrategyResponse -> {
                Set<CandleMinute> candleMinutes = accountStrategyResponse.getOrderStrategies().stream()
                        .map(OrderStrategy::getCandleMinute)
                        .collect(Collectors.toSet())
                        ;
                AssetResponses assetResponses = upbitAssetService.getAssets(accountStrategyResponse.getAccountResponse());
                Set<String> markets = assetResponses.stream()
                        .filter(assetResponse -> !assetResponse.isBaseCurrency() && assetResponse.isExistSellBalance())
                        .map(AssetResponse::getMarketCurrency)
                        .collect(Collectors.toSet())
                        ;
                candleMinutes
                        .forEach(candleMinute -> updateCandles.addAll(markets.stream()
                                .map(market -> MarketCandleMinute.builder().market(market).candleMinute(candleMinute).build())
                                .collect(Collectors.toSet())))
                ;
            });
            upbitCandlesBatch.updateAssetCandles(updateCandles);
            filterSellResponses.forEach(accountStrategyResponse ->
                    upbitOrderService.sellByAccount(accountStrategyResponse, upbitAssetService.getAssets(accountStrategyResponse.getAccountResponse()))
            );
        }
    }

    @Scheduled(cron = "${schedule.order.all-minute}")
    private void orderCancel() {
        List<AccountResponse> accounts = accountService.getAll();
        if (Objects.nonNull(accounts) && !accounts.isEmpty()) {
            logger.info("ORDER CANCEL");
            accounts.forEach(accountResponse -> upbitOrderService.cancelNotProcess(accountResponse, upbitOrderService.getOrderDetails(accountResponse)));
        }
    }

    @Scheduled(cron = "${schedule.order.one-day}")
    private void updateAccountStrategy() {
        logger.info("UPDATE ACCOUNT STRATEGY LIST");
        accountStrategyResponses = upbitAccountStrategyService.getAll();
    }
}
