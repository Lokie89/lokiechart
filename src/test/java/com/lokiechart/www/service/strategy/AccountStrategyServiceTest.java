package com.lokiechart.www.service.strategy;

import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/05/11
 */
@DisplayName("계정 전략 서비스 테스트")
@SpringBootTest
class AccountStrategyServiceTest {
    @Autowired
    AccountStrategyService upbitAccountStrategyService;

    @DisplayName("전체 가져오기 테스트")
    @Test
    void filterCandleMinuteTest() {
        AccountStrategyResponses accountStrategyResponses = upbitAccountStrategyService.getAll();
        System.out.println(accountStrategyResponses);
    }


}
