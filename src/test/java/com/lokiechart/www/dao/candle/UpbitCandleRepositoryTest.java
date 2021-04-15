package com.lokiechart.www.dao.candle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@DisplayName("업비트 캔들 API 테스트")
@SpringBootTest
class UpbitCandleRepositoryTest {
    @Autowired
    CandleRepository upbitCandleRepository;

    @DisplayName("캔들 가져오기 테스트")
    @Test
    void getCandlesTest() {
        final String market = "KRW-BTC";
        String candleResponseList = upbitCandleRepository.getCandles(CandleMinute.THIRTY, market, 5);
        System.out.println(candleResponseList);
    }

}
