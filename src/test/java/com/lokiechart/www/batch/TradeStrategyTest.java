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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SeongRok.Oh
 * @since 2021/04/24
 */
@DisplayName("매수 조건 검사 테스트")
@SpringBootTest
public class TradeStrategyTest {

    @Autowired
    UpbitCandleService candleService;

    @Autowired
    ConvertType convertType;

    @DisplayName("종가가 두 번 연속 볼린저 밴드 보다 낮음 테스트")
    @Test
    void underBollingerBandsTwiceTest() {
        CandleResponses candleResponses = candleService.get3MinutesCandles("KRW-SC", 30, LocalDateTime.of(2021, 4, 28, 12, 45, 0));
        candleResponses.setUnderBollingerBands(3);
        CandleResponse matchedResponse = TradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE.match(candleResponses);
        assertTrue(Objects.nonNull(matchedResponse));
    }

    @DisplayName("종가가 여섯 번 중 다섯 번 볼린저 밴드 보다 낮음 테스트")
    @Test
    void underBollingerBandsFiveTimesTest() {
        CandleResponses candleResponses = candleService.get3MinutesCandles("KRW-SC", 30, LocalDateTime.of(2021, 4, 28, 12, 45, 0));
        candleResponses.setUnderBollingerBands(6);
        CandleResponse matchedResponse = TradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE.match(candleResponses);
        assertTrue(Objects.nonNull(matchedResponse));
    }

    @DisplayName("120 라인 증가 테스트")
    @Test
    void increasing120LineTest(){
        CandleResponses candleResponses = candleService.getMinuteCandles(CandleMinute.THIRTY,"KRW-BTC", 125, LocalDateTime.of(2021, 5, 10, 4, 0, 3));
        candleResponses.set120Line(5);
        CandleResponse matchedResponse = TradeStrategy.BTCLINE120_INCREASING.match(candleResponses);
        assertTrue(Objects.nonNull(matchedResponse));


        CandleResponses candleResponses2 = candleService.getMinuteCandles(CandleMinute.THIRTY,"KRW-BTC", 125, LocalDateTime.of(2021, 5, 12, 0, 0, 3));
        candleResponses2.set120Line(5);
        CandleResponse matchedResponse2 = TradeStrategy.BTCLINE120_INCREASING.match(candleResponses2);
        assertTrue(Objects.isNull(matchedResponse2));
    }

}
