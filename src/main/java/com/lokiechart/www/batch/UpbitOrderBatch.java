package com.lokiechart.www.batch;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.asset.AssetService;
import com.lokiechart.www.service.order.OrderService;
import com.lokiechart.www.service.order.dto.OrderStrategyCandleTime;
import com.lokiechart.www.service.strategy.AccountStrategyService;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
    private AccountStrategyResponses accountStrategyResponses;

    public UpbitOrderBatch(AccountService accountService, OrderService upbitOrderService, AssetService upbitAssetService,
                           AccountStrategyService upbitAccountStrategyService) {
        this.accountService = accountService;
        this.upbitOrderService = upbitOrderService;
        this.upbitAssetService = upbitAssetService;
        this.upbitAccountStrategyService = upbitAccountStrategyService;
//        init();
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

            // TODO : 이부분 자동 캐시로 구현할 수 있는 프레임 워크가 분명히 있을 것 이다. 나중에 찾아 볼것
            filterBuyCandleMinuteResponses.getOrderStrategies().forEach(orderStrategies -> {
                OrderParameters matchedOrderParameters = orderStrategies.getMatchedOrderParameters();
                cache.put(new OrderStrategyCandleTime(orderStrategies), matchedOrderParameters);
            });

            filterBuyCandleMinuteResponses.forEach(accountStrategyResponse -> {
                OrderParameters matchParameters = cache.get(new OrderStrategyCandleTime(accountStrategyResponse.getOrderStrategies()));
                matchParameters.filterByAccount(accountStrategyResponse, upbitAssetService.getAssets(accountStrategyResponse.getAccountResponse()));
                upbitOrderService.buyByAccount(accountStrategyResponse, matchParameters);
            });
        }
    }

    @Scheduled(cron = "${schedule.order.all-minute}")
    private void orderSellTradeStrategy() {
        List<AccountResponse> accounts = accountService.getAll();
        if (Objects.nonNull(accounts) && !accounts.isEmpty()) {
            logger.info("ORDER SELL STRATEGY");
            accounts.forEach(accountResponse -> upbitOrderService.sellByAccount(accountResponse, upbitAssetService.getAssets(accountResponse)));
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
