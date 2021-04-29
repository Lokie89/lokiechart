package com.lokiechart.www.dao.account.dto;

import com.lokiechart.www.domain.account.Account;
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
        Assertions.assertEquals(2, accountResponse.getScaleTradeCount());
    }

}
