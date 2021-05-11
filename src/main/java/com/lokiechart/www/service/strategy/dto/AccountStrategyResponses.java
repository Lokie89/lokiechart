package com.lokiechart.www.service.strategy.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.order.dto.OrderSide;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/05/11
 */
@ToString
@RequiredArgsConstructor
@Getter
public class AccountStrategyResponses implements Iterable<AccountStrategyResponse>{

    private final List<AccountStrategyResponse> accountStrategyResponses;

    public AccountStrategyResponses filterCandleMinute(final CandleMinute candleMinute) {
        return new AccountStrategyResponses(
                accountStrategyResponses.stream()
                        .filter(accountStrategyResponse -> accountStrategyResponse.filterByCandleMinute(candleMinute))
                        .collect(Collectors.toList())
        );
    }

    public int size() {
        return accountStrategyResponses.size();
    }

    public AccountStrategyResponses filterOrderSide(OrderSide orderSide) {
        return new AccountStrategyResponses(
                accountStrategyResponses.stream()
                        .filter(accountStrategyResponse -> accountStrategyResponse.filterByOrderSide(orderSide))
                        .collect(Collectors.toList())
        );
    }

    public boolean isEmpty() {
        return accountStrategyResponses.isEmpty();
    }

    @Override
    public Iterator<AccountStrategyResponse> iterator() {
        return accountStrategyResponses.iterator();
    }
}