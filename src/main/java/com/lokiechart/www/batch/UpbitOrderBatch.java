package com.lokiechart.www.batch;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.asset.AssetService;
import com.lokiechart.www.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/04/21
 */
@Slf4j
@Component
public class UpbitOrderBatch {
    private final Logger logger = LoggerFactory.getLogger(UpbitOrderBatch.class);
    private final AccountService accountService;
    private final AssetService upbitAssetService;
    private final OrderService upbitOrderService;
    private final Map<CandleMinute, Set<AccountResponse>> accountStrategy = new ConcurrentHashMap<>();

    public UpbitOrderBatch(AccountService accountService, OrderService upbitOrderService, AssetService upbitAssetService) {
        this.accountService = accountService;
        this.upbitOrderService = upbitOrderService;
        this.upbitAssetService = upbitAssetService;
        init();
    }

    private void init() {
        updateAccountStrategy();
    }

    @Scheduled(cron = "${schedule.order.one-day}")
    private void updateAccountStrategy() {
        logger.info("UPDATE MAP STRATEGY WITH ACCOUNT LIST : " + LocalDateTime.now());
        for (CandleMinute candleMinute : CandleMinute.values()) {
            Set<AccountResponse> accountResponses = accountService.getAccountsByCandleMinute(candleMinute);
            if (Objects.isNull(accountResponses) || accountResponses.isEmpty()) {
                continue;
            }
            accountStrategy.put(candleMinute, accountResponses);
        }
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

    private void orderBuyTradeStrategy(final CandleMinute candleMinute) {
        Set<AccountResponse> accounts = accountStrategy.get(candleMinute);
        if (Objects.nonNull(accounts) && !accounts.isEmpty()) {
            logger.info("ORDER BUY " + candleMinute.name().toUpperCase() + " MINUTES TRADE STRATEGY : " + LocalDateTime.now());
            accounts.forEach(accountResponse -> upbitOrderService.buyByAccount(accountResponse, candleMinute, upbitAssetService.getAssets(accountResponse)));
        }
    }

    @Scheduled(cron = "${schedule.order.all-minute}")
    private void orderSellTradeStrategy() {
        List<AccountResponse> accounts = accountService.getAll();
        if (Objects.nonNull(accounts) && !accounts.isEmpty()) {
            logger.info("ORDER SELL TRATEGY : " + LocalDateTime.now());
            accounts.forEach(accountResponse -> upbitOrderService.sellByAccount(accountResponse, upbitAssetService.getAssets(accountResponse)));
        }
    }

//    @Scheduled(cron = "${schedule.order.all-minute}")
//    private void orderCancel() {
//        List<AccountResponse> accounts = accountService.getAll();
//        if (Objects.nonNull(accounts) && !accounts.isEmpty()) {
//
//        }
//    }
}
