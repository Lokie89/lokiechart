package com.lokiechart.www.service.order.dto;

import com.lokiechart.www.batch.OrderStrategies;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
public class OrderStrategyCandleTime {
    private LocalDateTime localDateTime;
    private OrderStrategies orderStrategies;

    public OrderStrategyCandleTime(OrderStrategies orderStrategies) {
        this.localDateTime = LocalDateTime.now().withSecond(0);
        this.orderStrategies = orderStrategies;
    }
}
