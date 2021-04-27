package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
public class AccountResponse {
    private String email;
    private Set<OrderStrategy> buyTradeStrategies;
    private Set<OrderStrategy> sellTradeStrategies;
    private List<String> excludeMarket;
    private List<String> decidedMarket;

    public OrderParameters findStrategically() {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        for (OrderStrategy orderStrategy : buyTradeStrategies) {
            matchedOrderParameters.addAll(orderStrategy.match());
        }

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            matchedOrderParameters.filter(decidedMarket);
            return matchedOrderParameters;
        }
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            matchedOrderParameters.exclude(excludeMarket);
        }
        return matchedOrderParameters;
    }
}
