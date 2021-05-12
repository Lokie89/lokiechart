package com.lokiechart.www.batch;

import lombok.Getter;

import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/05/12
 */
@Getter
public class OrderStrategies {
    private Set<OrderStrategy> orderStrategies;

    public OrderStrategies(Set<OrderStrategy> orderStrategies) {
        validateOrderStrategiesOrderSide(orderStrategies);
        this.orderStrategies = orderStrategies;
    }

    // TODO : Exception
    private void validateOrderStrategiesOrderSide(Set<OrderStrategy> orderStrategies) {
        if (isContainsAnotherOrderSide(orderStrategies)) {
            throw new RuntimeException();
        }
    }

    private boolean isContainsAnotherOrderSide(Set<OrderStrategy> orderStrategies) {
        return orderStrategies.stream().map(orderStrategy -> orderStrategy.getTradeStrategy().getOrderSide()).distinct().count() > 1;
    }
}
