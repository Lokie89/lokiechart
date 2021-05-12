package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.domain.account.Account;
import com.lokiechart.www.domain.strategy.AccountStrategy;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/29
 */
@DisplayName("계정 응답 DTO 테스트")
@SpringBootTest
class AccountResponseTest {

    @Autowired
    ModelMapper modelMapper;

    @DisplayName("계정 계정응답 매핑 테스트")
    @Test
    void mappingTest() {
        Account account = Account.builder().maxBuyMarket(5).build();
        AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
        Assertions.assertEquals(account.getMaxBuyMarket(), accountResponse.getMaxBuyMarket());
        Assertions.assertEquals(2, accountResponse.getTotalTradeCount());
    }

    @DisplayName("BuyFlag 로직 테스트")
    @Test
    void buyFlagTest() {
        AccountResponse accountResponse = AccountResponse.builder().maxBuyMarket(5).buyFlag(false).build();
        AccountStrategy accountStrategy = AccountStrategy.builder().accountResponse(accountResponse).build();
        AccountStrategyResponse accountStrategyResponse = modelMapper.map(accountStrategy, AccountStrategyResponse.class);
    }

}
