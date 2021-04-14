package com.lokiechart.www.upbit.service.market;

import com.lokiechart.www.upbit.dao.market.dto.MarketResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@DisplayName("업비트 마켓 서비스 테스트")
@SpringBootTest
class MarketServiceTest {

    @Autowired
    MarketService marketService;

    @DisplayName("코인 목록 가져오기 테스트")
    @Test
    void getCoinListTest() {
        List<MarketResponse> marketResponseList = marketService.getAll();
        Assertions.assertTrue(marketResponseList.stream().anyMatch(marketResponse -> marketResponse.getKorean().equals("비트코인")));
    }
}
