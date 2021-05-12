package com.lokiechart.www.service.strategy.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategies;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.service.order.dto.OrderStrategyCandleTime;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */
@ToString
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccountStrategyResponse {
    private final Logger logger = LoggerFactory.getLogger(AccountStrategyResponse.class);
    private AccountResponse accountResponse;
    private OrderStrategies orderStrategies;

    public boolean filterByCandleMinute(CandleMinute candleMinute) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.equalsByCandleMinute(candleMinute));
    }

    public boolean filterByOrderSide(OrderSide orderSide) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.equalsByOrderSide(orderSide));
    }

}
