package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.service.candle.UpbitCandleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/29
 */
@DisplayName("캔들 응답 객체 테스트")
@SpringBootTest
public class CandleResponseTest {
    @Autowired
    UpbitCandleService upbitCandleService;

    @DisplayName("볼린저 밴드 세팅 테스트")
    @Test
    void setBollingerTest() {
        CandleResponses candleresponses = upbitCandleService.get3MinutesCandles("KRW-EOS",25, LocalDateTime.of(2021,4,29,19,24,0));
        candleresponses.setUnderBollingerBands(3);
        System.out.println(candleresponses);
    }

}
