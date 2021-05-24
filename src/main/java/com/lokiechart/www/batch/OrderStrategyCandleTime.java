package com.lokiechart.www.batch;

import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
public class OrderStrategyCandleTime {
    private LocalDateTime localDateTime;
    private OrderStrategies orderStrategies;

    private OrderStrategyCandleTime(OrderStrategies orderStrategies, LocalDateTime localDateTime) {
        this.localDateTime = Objects.isNull(localDateTime) ? LocalDateTime.now() : localDateTime;
        this.orderStrategies = orderStrategies;
    }

    public static OrderStrategyCandleTime of(OrderStrategies orderStrategies) {
        return new OrderStrategyCandleTime(orderStrategies, null);
    }

    public static OrderStrategyCandleTime of(OrderStrategies orderStrategies, LocalDateTime localDateTime) {
        return new OrderStrategyCandleTime(orderStrategies, localDateTime);
    }

    public boolean is2HoursPass(){
        return this.localDateTime.isBefore(LocalDateTime.now().minusHours(2));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderStrategyCandleTime)) {
            return false;
        }
        OrderStrategyCandleTime other = (OrderStrategyCandleTime) obj;
        boolean isInnerOneMinute
                = other.localDateTime.isBefore(this.localDateTime.plusSeconds(30))
                && other.localDateTime.isAfter(this.localDateTime.minusSeconds(30));
        return isInnerOneMinute && this.orderStrategies.equals(other.orderStrategies);
    }

    @Override
    public int hashCode() {
        return orderStrategies.hashCode();
    }
}
