package com.lokiechart.www.service.order.dto;

import com.lokiechart.www.batch.OrderStrategies;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class OrderStrategyCandleTime {
    private LocalDateTime localDateTime;
    private OrderStrategies orderStrategies;

    public OrderStrategyCandleTime(OrderStrategies orderStrategies) {
        this.localDateTime = LocalDateTime.now();
        this.orderStrategies = orderStrategies;
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
