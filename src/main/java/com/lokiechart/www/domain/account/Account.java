package com.lokiechart.www.domain.account;

import com.lokiechart.www.common.TradeStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
@AllArgsConstructor
public class Account {
    private String email;
    private TradeStrategy strategy;
    private Integer onceInvestKRW;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
}
