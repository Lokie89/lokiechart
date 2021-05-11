package com.lokiechart.www.batch;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.asset.AssetService;
import com.lokiechart.www.service.order.OrderService;
import com.lokiechart.www.service.strategy.AccountStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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
    private final AccountStrategyService upbitAccountStrategyService;
//    private final Map<CandleMinute, Set<AccountResponse>> accountStrategy = new ConcurrentHashMap<>();
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

//    @Scheduled(cron = "${schedule.order.one-day}")
//    private void updateAccountStrategy() {
//        logger.info("UPDATE MAP STRATEGY WITH ACCOUNT LIST : " + LocalDateTime.now());
//        for (CandleMinute candleMinute : CandleMinute.values()) {
//            Set<AccountResponse> accountResponses = accountService.getAccountsByCandleMinute(candleMinute);
//            if (Objects.isNull(accountResponses) || accountResponses.isEmpty()) {
//                continue;
//            }
//            accountStrategy.put(candleMinute, accountResponses);
//        }
//    }

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
        AccountStrategyResponses filterCandleMinuteResponses = accountStrategyResponses.filterCandleMinute(candleMinute);
        if (Objects.nonNull(filterCandleMinuteResponses) && !filterCandleMinuteResponses.isEmpty()) {
            logger.info("ORDER BUY " + candleMinute.name().toUpperCase() + " MINUTES TRADE STRATEGY");
            filterCandleMinuteResponses.forEach(accountStrategyResponse -> upbitOrderService.buyByAccount(accountStrategyResponse, upbitAssetService.getAssets(accountStrategyResponse.getAccountResponse())));
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
