package com.lokiechart.www.service.strategy.dto;

import com.lokiechart.www.batch.BuyTradeStrategy;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategy;
import com.lokiechart.www.batch.SellTradeStrategy;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.domain.account.Account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/11
 */
@DisplayName("계정 전략 응답 객체들 테스트")
class AccountStrategyResponsesTest {

    private final AccountResponse.AccountResponseBuilder tjdfhr = AccountResponse.builder()
            .email("tjdfhrdk10@naver.com")
            .maxBuyMarket(20)
            .totalSeed(6000000)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true);

    private final AccountResponse.AccountResponseBuilder tjdals = AccountResponse.builder()
            .email("01099589323@hanmail.net")
            .totalSeed(1600000)
            .maxBuyMarket(20)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true);

    private final OrderStrategy buy = OrderStrategy.builder().candleMinute(CandleMinute.THREE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).orderType(OrderType.LIMIT).scaleTradingPercent(10).build();
    private final OrderStrategy buy5 = OrderStrategy.builder().candleMinute(CandleMinute.FIVE).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).orderType(OrderType.LIMIT).scaleTradingPercent(10).build();
    private final OrderStrategy buy15 = OrderStrategy.builder().candleMinute(CandleMinute.FIFTEEN).tradeStrategy(BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE).orderType(OrderType.LIMIT).scaleTradingPercent(10).build();
    private final OrderStrategy sell1 = OrderStrategy.builder().candleMinute(CandleMinute.FIVE).tradeStrategy(SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS).orderType(OrderType.LIMIT).incomePercent(5).build();

    private AccountStrategyResponses upbitAccountStrategyResponses;

    @BeforeEach
    void setUp() {
        List<AccountStrategyResponse> accountStrategyResponses = new ArrayList<>();
        AccountStrategyResponse tjdfhrBuyStrategyResponse = AccountStrategyResponse.builder().accountResponse(tjdfhr.build()).orderStrategies(new HashSet<>(Arrays.asList(buy, buy5, buy15))).build();
        AccountStrategyResponse tjdfhrSellStrategyResponse = AccountStrategyResponse.builder().accountResponse(tjdfhr.build()).orderStrategies(new HashSet<>(Arrays.asList(sell1))).build();
        AccountStrategyResponse tjdalsBuyStrategyResponse = AccountStrategyResponse.builder().accountResponse(tjdals.build()).orderStrategies(new HashSet<>(Arrays.asList(buy5))).build();
        AccountStrategyResponse tjdalsSellStrategyResponse = AccountStrategyResponse.builder().accountResponse(tjdals.build()).orderStrategies(new HashSet<>(Arrays.asList(sell1))).build();
        AccountStrategyResponse tjdfhr2BuyStrategyResponse = AccountStrategyResponse.builder().accountResponse(tjdfhr.build()).orderStrategies(new HashSet<>(Arrays.asList(buy15))).build();
        AccountStrategyResponse tjdfhr2SellStrategyResponse = AccountStrategyResponse.builder().accountResponse(tjdfhr.build()).orderStrategies(new HashSet<>(Arrays.asList(sell1))).build();
        accountStrategyResponses.add(tjdfhrBuyStrategyResponse);
        accountStrategyResponses.add(tjdfhrSellStrategyResponse);
        accountStrategyResponses.add(tjdalsBuyStrategyResponse);
        accountStrategyResponses.add(tjdalsSellStrategyResponse);
        accountStrategyResponses.add(tjdfhr2BuyStrategyResponse);
        accountStrategyResponses.add(tjdfhr2SellStrategyResponse);
        upbitAccountStrategyResponses = new AccountStrategyResponses(accountStrategyResponses);
    }

    @DisplayName("분봉으로 필터링 테스트")
    @Test
    void filterCandleMinuteTest() {
        AccountStrategyResponses filterThreeCandlesAccountStrategyResponses = upbitAccountStrategyResponses.filterCandleMinute(CandleMinute.THREE);
        assertEquals(1, filterThreeCandlesAccountStrategyResponses.size());
    }

    @DisplayName("OrderSide 로 필터링 테스트")
    @Test
    void filterOrderSide() {
        AccountStrategyResponses filterSellAccountStrategyResponses = upbitAccountStrategyResponses.filterOrderSide(OrderSide.SELL);
        assertEquals(3, filterSellAccountStrategyResponses.size());
    }
}
