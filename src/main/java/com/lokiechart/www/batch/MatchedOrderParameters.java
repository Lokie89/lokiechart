package com.lokiechart.www.batch;

import com.lokiechart.www.dao.order.dto.OrderParameters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/05/24
 */
public class MatchedOrderParameters {
    private final Map<OrderStrategyCandleTime, OrderParameters> cache = new ConcurrentHashMap<>();

    public void put(OrderStrategyCandleTime orderStrategyCandleTime, OrderParameters orderParameters) {
        clearUp();
        cache.put(orderStrategyCandleTime, orderParameters);
    }

    private void clearUp() {
        cache.keySet().forEach(this::removeBeforeHours);
    }

    private void removeBeforeHours(OrderStrategyCandleTime candleTime) {
        if (candleTime.is2HoursPass()) {
            remove(candleTime);
        }
    }

    private void remove(OrderStrategyCandleTime orderStrategyCandleTime) {
        cache.remove(orderStrategyCandleTime);
    }

    public OrderParameters get(OrderStrategyCandleTime orderStrategyCandleTime) {
        return cache.get(orderStrategyCandleTime);
    }

    public int size(){
        return cache.size();
    }
}
