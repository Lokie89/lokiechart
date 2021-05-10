package com.lokiechart.www.dao.strategy.dto;

import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccountStrategyResponse {
    private Account account;
    private Set<OrderStrategy> orderStrategies;
}
