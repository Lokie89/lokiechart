package com.lokiechart.www.common;

import com.lokiechart.www.batch.BuyTradeStrategy;
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
 * @since 2021/04/24
 */
@DisplayName("매수 조건 검사 테스트")
@SpringBootTest
public class BuyTradeStrategyTest {

    @Autowired
    UpbitCandleService candleService;

    @Autowired
    ConvertType convertType;

    @DisplayName("종가가 두 번 연속 볼린저 밴드 보다 낮음 테스트")
    @Test
    void underBollingerBandsTwiceTest() {
        CandleResponses candleResponses = candleService.get3MinutesCandles("KRW-SC", 30, LocalDateTime.of(2021, 4, 28, 12, 45, 0));
        candleResponses.setUnderBollingerBands(3);
        CandleResponse matchedResponse = BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE.match(candleResponses);
        assertTrue(Objects.nonNull(matchedResponse));
    }

    @DisplayName("종가가 여섯 번 중 다섯 번 볼린저 밴드 보다 낮음 테스트")
    @Test
    void underBollingerBandsFiveTimesTest() {
        CandleResponses candleResponses = candleService.get3MinutesCandles("KRW-SC", 30, LocalDateTime.of(2021, 4, 28, 12, 45, 0));
        candleResponses.setUnderBollingerBands(6);
        CandleResponse matchedResponse = BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSFOURTIMESINFIVE.match(candleResponses);
        assertTrue(Objects.nonNull(matchedResponse));
    }

}
