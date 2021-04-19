package com.lokiechart.www.dao.candle;

import com.lokiechart.www.dao.candle.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@DisplayName("업비트 캔들 API 테스트")
@SpringBootTest
class UpbitCandleRepositoryTest {
    @Autowired
    CandleRepository upbitMinuteCandleRepository;

    @Autowired
    CandleRepository upbitDayCandleRepository;

    @Autowired
    CandleRepository upbitWeekCandleRepository;

    @Autowired
    CandleRepository upbitMonthCandleRepository;

    @DisplayName("분 캔들 가져오기 테스트")
    @Test
    void getMinuteCandlesTest() {
        final String market = "KRW-BTC";
        CandleResponses candleResponseList = upbitMinuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().candleMinute(CandleMinute.THIRTY).market(market).count(10).build());
        System.out.println(candleResponseList);
    }

    @DisplayName("일 캔들 가져오기 테스트")
    @Test
    void getDayCandlesTest() {
        final String market = "KRW-BTC";
        CandleResponses candleResponseList = upbitDayCandleRepository.getCandles(UpbitDayCandleParameter.builder().market(market).count(10).build());
        System.out.println(candleResponseList);
    }

    @DisplayName("주 캔들 가져오기 테스트")
    @Test
    void getWeekCandlesTest() {
        final String market = "KRW-BTC";
        CandleResponses candleResponseList = upbitWeekCandleRepository.getCandles(UpbitWeekCandleParameter.builder().market(market).count(10).build());
        System.out.println(candleResponseList);
    }

    @DisplayName("월 캔들 가져오기 테스트")
    @Test
    void getMonthCandlesTest() {
        final String market = "KRW-BTC";
        CandleResponses candleResponseList = upbitMonthCandleRepository.getCandles(UpbitMonthCandleParameter.builder().market(market).count(10).build());
        System.out.println(candleResponseList);
    }

}
