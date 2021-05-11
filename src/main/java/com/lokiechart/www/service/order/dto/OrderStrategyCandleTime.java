package com.lokiechart.www.service.order.dto;

import com.lokiechart.www.batch.OrderStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class OrderStrategyCandleTime {
    private LocalDateTime localDateTime;
    private Set<OrderStrategy> orderStrategy;

    public OrderStrategyCandleTime(Set<OrderStrategy> orderStrategy) {
        this.localDateTime = LocalDateTime.now().withSecond(0);
        this.orderStrategy = orderStrategy;
    }
}
