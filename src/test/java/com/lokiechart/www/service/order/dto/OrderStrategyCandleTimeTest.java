package com.lokiechart.www.service.order.dto;

import com.lokiechart.www.batch.BuyTradeStrategy;
import com.lokiechart.www.batch.OrderStrategies;
import com.lokiechart.www.batch.OrderStrategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/05/13
 */
@DisplayName("주문 전략, 시간 객체 테스트")
class OrderStrategyCandleTimeTest {

    @DisplayName("equals, hashCode 테스트")
    @Test
    void equalsHashCodeTest() {
        Set<OrderStrategyCandleTime> hashSet = new HashSet<>();

        OrderStrategy orderStrategy = OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TEST).build();
        OrderStrategies orderStrategies = new OrderStrategies(new HashSet<>(Arrays.asList(orderStrategy)));
        OrderStrategyCandleTime orderStrategyCandleTime = new OrderStrategyCandleTime(orderStrategies);

        hashSet.add(orderStrategyCandleTime);

        assertEquals(1, hashSet.size());

        OrderStrategy orderStrategy2 = OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TEST).build();
        OrderStrategies orderStrategies2 = new OrderStrategies(new HashSet<>(Arrays.asList(orderStrategy2)));
        OrderStrategyCandleTime orderStrategyCandleTime2 = new OrderStrategyCandleTime(orderStrategies2);

        hashSet.add(orderStrategyCandleTime2);
        assertEquals(1, hashSet.size());

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OrderStrategy orderStrategy3 = OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TEST).build();
        OrderStrategies orderStrategies3 = new OrderStrategies(new HashSet<>(Arrays.asList(orderStrategy3)));
        OrderStrategyCandleTime orderStrategyCandleTime3 = new OrderStrategyCandleTime(orderStrategies3);

        hashSet.add(orderStrategyCandleTime3);
        assertEquals(2, hashSet.size());

        OrderStrategy orderStrategy4 = OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).build();
        OrderStrategies orderStrategies4 = new OrderStrategies(new HashSet<>(Arrays.asList(orderStrategy4)));
        OrderStrategyCandleTime orderStrategyCandleTime4 = new OrderStrategyCandleTime(orderStrategies4);

        hashSet.add(orderStrategyCandleTime4);
        assertEquals(3, hashSet.size());
    }
}
