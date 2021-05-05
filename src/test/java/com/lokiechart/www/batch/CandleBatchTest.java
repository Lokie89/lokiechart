package com.lokiechart.www.batch;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.service.candle.UpbitCandleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/05/05
 */
@DisplayName("차트 배치 테스트")
@SpringBootTest
public class CandleBatchTest {

    @Autowired
    UpbitCandleService upbitCandleService;

    @DisplayName("이미 올랐던 종목 테스트")
    @Test
    void isAlreadyIncreasedTest(){
        final Map<String, Boolean> isAlready15PercentNotIncreasedInTwoDays = new ConcurrentHashMap<>();
        String market = "KRW-ETC";
        CandleResponses candleResponses = upbitCandleService.get1DayCandles(market,10);
        SynchronizedNonOverlapList<CandleResponse> candles = candleResponses.getCandleResponses();
        isAlready15PercentNotIncreasedInTwoDays.put(market, candles.copyRecent(1, 3).stream().allMatch(candle -> candle.getIncreasePercent() < 15 && candles.getRecent(0).getIncreasePercent() < 10));
        System.out.println(isAlready15PercentNotIncreasedInTwoDays);
    }


}
