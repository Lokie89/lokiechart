package com.lokiechart.www.dao.strategy;

import com.lokiechart.www.domain.strategy.AccountStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/11
 */
@DisplayName("계정 전략 저장소 테스트")
@SpringBootTest
class AccountStrategyRepositoryTest {

    @Autowired
    AccountStrategyRepository upbitStrategyRepository;

    @DisplayName("계정전략 가져오기 테스트")
    @Test
    void getAllTest() {
        List<AccountStrategy> upbitAccountStrategyResponses = upbitStrategyRepository.getAll();
        System.out.println(upbitAccountStrategyResponses);
    }
}
