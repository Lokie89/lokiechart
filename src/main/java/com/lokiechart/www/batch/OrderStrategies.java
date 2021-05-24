package com.lokiechart.www.batch;

import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author SeongRok.Oh
 * @since 2021/05/12
 */
@ToString
@EqualsAndHashCode
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
        return orderStrategies.stream().map(OrderStrategy::getOrderSide).distinct().count() > 1;
    }

    public Stream<OrderStrategy> stream() {
        return orderStrategies.stream();
    }

    public int minIndex() {
        return orderStrategies.stream().mapToInt(orderStrategy -> orderStrategy.getTradeStrategy().getUseIndex()).max().orElse(0);
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
        return matchedOrderParameters;
    }

    public OrderParameters getMatchedOrderParameters(Map<CandleMinute, Map<String, CandleResponses>> candleResponsesMap) {
        OrderParameters matchedOrderParameters = new OrderParameters(new ArrayList<>());
        orderStrategies.forEach(orderStrategy -> matchedOrderParameters.intersect(orderStrategy.matchBuying(candleResponsesMap)));
//        OrderParameters rsiUnderParameters = matchedOrderParameters.filter(orderParameter -> UpbitCandlesBatch.upbitDayCandles.get(orderParameter.getMarket()).getCandleResponses().getRecent(0).getRsi() < 65);
        return matchedOrderParameters;
    }
}
