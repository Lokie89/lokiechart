package com.lokiechart.www.common;

import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.UpbitOrderParameter;
import com.lokiechart.www.dao.order.dto.UpbitOrderSide;
import com.lokiechart.www.dao.order.dto.UpbitOrderType;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public enum TradeStrategy {
    TEST("TEST 입니다.") {
        @Override
        public List<OrderParameter> match() {
            OrderParameter orderParameter = UpbitOrderParameter.builder().market("KRW-AHT").side(UpbitOrderSide.BUY).volume(5000.0).price(1.0).orderType(UpbitOrderType.LIMIT).build();
            return Arrays.asList(orderParameter);
        }
    },
    ;

    @Getter
    private String description;

    TradeStrategy(String description) {
        this.description = description;
    }

    public abstract List<OrderParameter> match();
}
