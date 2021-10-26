package com.lokiechart.www.dao.account.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Slf4j
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class AccountResponse {
    private String email;
    private List<String> excludeMarket;
    private List<String> decidedMarket;
    private int totalSeed;
    private int maxBuyMarket;
    private int totalTradeCount;
    private boolean buyFlag;
    private boolean sellFlag;
    private double scaleTradingPercent;
    private double incomePercent;

}
