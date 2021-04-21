package com.lokiechart.www.common;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.candle.CandleMinute;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/04/21
 */
@Slf4j
@Component
public class UpbitOrder {
    private final Logger logger = LoggerFactory.getLogger(UpbitOrder.class);
    private final AccountService accountService;
    private final OrderService upbitOrderService;
    private final Map<TradeStrategy, List<AccountResponse>> accountStrategy = new ConcurrentHashMap<>();

    public UpbitOrder(AccountService accountService, OrderService upbitOrderService) {
        this.accountService = accountService;
        this.upbitOrderService = upbitOrderService;
        init();
    }

    private void init() {
        updateAccountStrategy();
    }

    @Scheduled(cron = "50 59 23 * * *")
    private void updateAccountStrategy() {
        logger.info("UPDATE MAP STRATEGY WITH ACCOUNT LIST : " + LocalDateTime.now());
        for (TradeStrategy strategy : TradeStrategy.values()) {
            accountStrategy.put(strategy, accountService.getAccountsByStrategy(strategy));
        }
    }

    @Scheduled(cron = "2 * * * * *")
    private void orderOneMinuteTradeStrategy() {
        logger.info("ORDER ONE MINUTE TRADE STRATEGY : " + LocalDateTime.now());
        List<TradeStrategy> strategies = TradeStrategy.getByCandleMinute(CandleMinute.ONE);
        for (TradeStrategy strategy : strategies) {
            List<AccountResponse> accounts = accountStrategy.get(strategy);
            accounts.forEach(accountResponse -> upbitOrderService.tradeByAccount(accountResponse.getEmail()));
        }
    }


}
