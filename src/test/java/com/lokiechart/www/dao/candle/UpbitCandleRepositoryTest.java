package com.lokiechart.www.dao.candle;

import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleParameter;
import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleResponse;
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

    @DisplayName("캔들 가져오기 테스트")
    @Test
    void getCandlesTest() {
        final String market = "KRW-BTC";
        List<CandleResponse> candleResponseList = upbitMinuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().candleMinute(CandleMinute.THIRTY).market(market).count(10).build());
        System.out.println(candleResponseList);
    }

}
