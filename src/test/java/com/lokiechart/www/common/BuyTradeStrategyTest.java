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

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SeongRok.Oh
 * @since 2021/04/24
 */
@DisplayName("조건 검사 테스트")
@SpringBootTest
public class BuyTradeStrategyTest {

    @Autowired
    UpbitCandleService candleService;

    @Autowired
    ConvertType convertType;

    @Test
    void test() {
        CandleResponses candleResponses = candleService.get3MinutesCandles("KRW-SC", 30, LocalDateTime.of(2021, 4, 28, 12, 45, 0));
        candleResponses.setUnderBollingerBands(3);
        CandleResponse matchedResponse = BuyTradeStrategy.TRADEPRICE_UNDERBOLLINGERBANDSTWICE.match(candleResponses);
        assertTrue(matchedResponse.compareUnderBollingerBands() < 0);
    }
}
