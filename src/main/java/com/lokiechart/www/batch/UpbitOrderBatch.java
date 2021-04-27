package com.lokiechart.www.batch;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private final OrderService upbitOrderService;
    private final Map<CandleMinute, Set<AccountResponse>> accountStrategy = new ConcurrentHashMap<>();

    public UpbitOrderBatch(AccountService accountService, OrderService upbitOrderService) {
        this.accountService = accountService;
        this.upbitOrderService = upbitOrderService;
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
    private void orderOneMinuteTradeStrategy() {
        logger.info("ORDER ONE MINUTE TRADE STRATEGY : " + LocalDateTime.now());
        Set<AccountResponse> accounts = accountStrategy.get(CandleMinute.ONE);
        accounts.forEach(accountResponse -> upbitOrderService.tradeByAccount(accountResponse.getEmail()));
    }


}
