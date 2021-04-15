package com.lokiechart.www.dao.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@DisplayName("업비트 마켓 서비스 테스트")
@SpringBootTest
class MarketRepositoryTest {

    @Autowired
    MarketRepository repository;

    @DisplayName("코인 목록 가져오기 테스트")
    @Test
    void getCoinListTest() {
        String coinListStr = repository.getCoinList();
        System.out.println(coinListStr);
    }
}
