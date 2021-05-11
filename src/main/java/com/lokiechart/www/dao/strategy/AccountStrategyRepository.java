package com.lokiechart.www.dao.strategy;

import com.lokiechart.www.domain.strategy.AccountStrategy;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/10
 */
public interface AccountStrategyRepository {
    List<AccountStrategy> getAll();
}
