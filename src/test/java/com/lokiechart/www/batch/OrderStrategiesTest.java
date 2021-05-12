package com.lokiechart.www.batch;

import com.lokiechart.www.dao.order.dto.OrderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/05/12
 */
@DisplayName("주문 전략 테스트")
class OrderStrategiesTest {

    @DisplayName("자료구조 Set 테스트")
    @Test
    void setTest() {
        Set<OrderStrategies> orderStrategiesSet = new HashSet<>();
        Set<OrderStrategy> strategies = new HashSet<>();
        strategies.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).candleMinute(CandleMinute.THREE).orderType(OrderType.LIMIT).build());
        strategies.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).candleMinute(CandleMinute.FIVE).orderType(OrderType.DOWNERMARKET).build());

        Set<OrderStrategy> strategies2 = new HashSet<>();
        strategies2.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).candleMinute(CandleMinute.THREE).orderType(OrderType.LIMIT).build());
        strategies2.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).candleMinute(CandleMinute.FIVE).orderType(OrderType.DOWNERMARKET).build());

        OrderStrategies orderStrategies1 = new OrderStrategies(strategies);
        OrderStrategies orderStrategies2 = new OrderStrategies(strategies2);
        orderStrategiesSet.add(orderStrategies1);
        orderStrategiesSet.add(orderStrategies2);

        Assertions.assertEquals(1, orderStrategiesSet.size());
    }

    @DisplayName("자료구조 Set 테스트2")
    @Test
    void setTest2() {
        Set<OrderStrategies> orderStrategiesSet = new HashSet<>();
        Set<OrderStrategy> strategies = new HashSet<>();
        strategies.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).candleMinute(CandleMinute.THREE).orderType(OrderType.LIMIT).build());
        strategies.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).candleMinute(CandleMinute.FIVE).orderType(OrderType.DOWNERMARKET).build());

        Set<OrderStrategy> strategies2 = new HashSet<>();
        strategies2.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).candleMinute(CandleMinute.THREE).orderType(OrderType.LIMIT).build());
        strategies2.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE).candleMinute(CandleMinute.FIVE).orderType(OrderType.DOWNERMARKET).build());

        Set<OrderStrategy> strategies3 = new HashSet<>();
        strategies3.add(OrderStrategy.builder().tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).candleMinute(CandleMinute.THREE).orderType(OrderType.LIMIT).build());

        OrderStrategies orderStrategies1 = new OrderStrategies(strategies);
        OrderStrategies orderStrategies2 = new OrderStrategies(strategies2);
        OrderStrategies orderStrategies3 = new OrderStrategies(strategies3);
        orderStrategiesSet.add(orderStrategies1);
        orderStrategiesSet.add(orderStrategies2);
        orderStrategiesSet.add(orderStrategies3);

        Assertions.assertEquals(2, orderStrategiesSet.size());
    }
}
