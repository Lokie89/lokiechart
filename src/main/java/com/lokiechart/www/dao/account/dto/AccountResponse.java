package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.common.TradeStrategy;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import lombok.Getter;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Getter
public class AccountResponse {
    private String email;
    private TradeStrategy strategy;

    public List<OrderParameter> findStrategically() {
        return strategy.match();
    }
}
