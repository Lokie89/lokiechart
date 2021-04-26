package com.lokiechart.www.domain.account;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.dao.order.dto.OrderType;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@EqualsAndHashCode(of = "email")
@Builder
@NoArgsConstructor
@ToString
@Getter
@AllArgsConstructor
public class Account {
    private String email;
    private Set<OrderStrategy> orderStrategies;
    private Integer onceInvestKRW;
    private OrderType orderType;
    private List<String> excludeMarket;
    private List<String> decidedMarket;

    public boolean haveOrderStrategyByCandleMinute(CandleMinute candleMinute) {
        return orderStrategies.stream()
                .anyMatch(orderStrategy -> orderStrategy.getCandleMinute().equals(candleMinute));
    }
}
