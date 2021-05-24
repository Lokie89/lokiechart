package com.lokiechart.www.batch;

import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.dao.order.dto.UpbitOrderParameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2021/05/24
 */
@DisplayName("일치하는 주문 파라미터 테스트")
public class MatchedOrderParametersTest {

    MatchedOrderParameters matchedOrderParameters;

    private OrderStrategies getOrderStrategies() {
        Set<OrderStrategy> orderStrategySet = new HashSet<>();
        OrderStrategy orderStrategy = OrderStrategy.builder().tradeStrategy(TradeStrategy.ALWAYS).build();
        orderStrategySet.add(orderStrategy);
        return new OrderStrategies(orderStrategySet);
    }

    private OrderParameters getOrderParameters() {
        List<OrderParameter> orderParameterList = new ArrayList<>();
        OrderParameter orderParameter = UpbitOrderParameter.builder().market("KRW-BTC").orderType(OrderType.LIMIT).volume(30.0).price(5000.0).build();
        orderParameterList.add(orderParameter);
        return new OrderParameters(orderParameterList);
    }

    @BeforeEach
    void setUp() {
        this.matchedOrderParameters = new MatchedOrderParameters();
    }

    @DisplayName("앞 뒤 30초 이내는 같은 키 취급 테스트")
    @Test
    void duplicateKeyTest() {
        matchedOrderParameters.put(OrderStrategyCandleTime.of(getOrderStrategies(), LocalDateTime.now()), getOrderParameters());
        assertEquals(1, matchedOrderParameters.size());
        matchedOrderParameters.put(OrderStrategyCandleTime.of(getOrderStrategies(), LocalDateTime.now()), getOrderParameters());
        assertEquals(1, matchedOrderParameters.size());
        matchedOrderParameters.put(OrderStrategyCandleTime.of(getOrderStrategies(), LocalDateTime.now().plusMinutes(31)), getOrderParameters());
        assertEquals(2, matchedOrderParameters.size());
        matchedOrderParameters.put(OrderStrategyCandleTime.of(getOrderStrategies(), LocalDateTime.now().minusMinutes(31)), getOrderParameters());
        assertEquals(3, matchedOrderParameters.size());
    }

    @DisplayName("2시간 넘은 데이터 자동 삭제 테스트")
    @Test
    void removePassed2Hours() {
        matchedOrderParameters.put(OrderStrategyCandleTime.of(getOrderStrategies(), LocalDateTime.now().minusHours(2)), getOrderParameters());
        assertEquals(1, matchedOrderParameters.size());
        matchedOrderParameters.put(OrderStrategyCandleTime.of(getOrderStrategies(), LocalDateTime.now()), getOrderParameters());
        assertEquals(1, matchedOrderParameters.size());
    }
}
