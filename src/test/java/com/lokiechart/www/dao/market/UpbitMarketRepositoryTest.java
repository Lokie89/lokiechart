package com.lokiechart.www.dao.market;

import com.lokiechart.www.dao.market.dto.UpbitMarketResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@DisplayName("업비트 마켓 API 테스트")
@SpringBootTest
class UpbitMarketRepositoryTest {

    @Autowired
    MarketRepository upbitMarketRepository;

    @DisplayName("코인 목록 가져오기 테스트")
    @Test
    void getCoinListTest() {
        UpbitMarketResponses upbitMarketResponses = upbitMarketRepository.getMarkets();
        System.out.println(upbitMarketResponses);
    }
}
