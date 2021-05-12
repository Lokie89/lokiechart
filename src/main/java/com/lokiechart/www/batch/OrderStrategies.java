package com.lokiechart.www.batch;

import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author SeongRok.Oh
 * @since 2021/05/12
 */
@EqualsAndHashCode
@Getter
public class OrderStrategies implements Iterable<OrderStrategy> {
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

    public Stream<OrderStrategy> stream() {
        return orderStrategies.stream();
    }

    public boolean isEmpty() {
        return orderStrategies.isEmpty();
    }

    @Override
    public Iterator<OrderStrategy> iterator() {
        return orderStrategies.iterator();
    }

    public OrderParameters getMatchedOrderParameters() {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        orderStrategies.forEach(orderStrategy -> matchedOrderParameters.intersect(orderStrategy.matchBuying()));
        matchedOrderParameters.filter(orderParameter -> UpbitCandlesBatch.upbitDayCandles.get(orderParameter.getMarket()).getCandleResponses().getRecent(0).getRsi() > 65);
        return matchedOrderParameters;
    }
}
