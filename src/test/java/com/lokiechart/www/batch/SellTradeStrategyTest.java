package com.lokiechart.www.batch;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.service.candle.UpbitCandleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SeongRok.Oh
 * @since 2021/04/29
 */
@DisplayName("매도 조건 검사 테스트")
@SpringBootTest
public class SellTradeStrategyTest {

    @Autowired
    UpbitCandleService candleService;

    @Autowired
    ConvertType convertType;

    @DisplayName("종가가 볼린저 밴드보다 높음 테스트")
    @Test
    void underBollingerBandsTwiceTest() {
        CandleResponses candleResponses = candleService.get5MinutesCandles("KRW-BTT", 30, LocalDateTime.of(2021, 5, 1, 14, 55, 5));
        candleResponses.setUnderBollingerBands(3);
        CandleResponse matchedResponse = SellTradeStrategy.TRADEPRICE_OVERBOLLINGERBANDS.match(candleResponses);
        System.out.println(matchedResponse);
        assertTrue(Objects.nonNull(matchedResponse));
    }
}
