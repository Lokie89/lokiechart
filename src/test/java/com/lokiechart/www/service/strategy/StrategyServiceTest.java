package com.lokiechart.www.service.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@DisplayName("전략 서비스 테스트")
@SpringBootTest
public class StrategyServiceTest {
    @Autowired
    StrategyService upbitStrategyService;

    @DisplayName("전략 적용하기 테스트")
    @Test
    void strategy1() {
        boolean isMatched = upbitStrategyService.enforceByAccount("tjdfhrdk10@naver.com");
        assertEquals(true, isMatched);
    }
}
