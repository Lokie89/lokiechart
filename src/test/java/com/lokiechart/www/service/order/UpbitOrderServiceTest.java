package com.lokiechart.www.service.order;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.batch.UpbitCandles;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.market.UpbitMarketRepository;
import com.lokiechart.www.dao.market.dto.UpbitMarketResponse;
import com.lokiechart.www.service.candle.UpbitCandleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@DisplayName("업비트 주문 서비스 테스트")
@SpringBootTest
class UpbitOrderServiceTest {

    @Autowired
    OrderService upbitOrderService;

    @Autowired
    UpbitMarketRepository upbitMarketRepository;

    @Autowired
    UpbitCandleService upbitCandleService;

    List<UpbitMarketResponse> upbitMarket;

    @DisplayName("Email 의 전략에 맞게 주문 하기")
    @Test
    void orderByEmail() {
        upbitMarket = upbitMarketRepository.getMarkets();
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            try {
                Thread.sleep(100);
                String market = marketResponse.getMarket();
                UpbitCandles.upbitThreeMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
                CandleResponses responses = upbitCandleService.get3MinutesCandles(market, 30, LocalDateTime.of(2021, 4, 22, 17, 36, 03));
                CandleResponses origin = UpbitCandles.upbitThreeMinuteCandles.get(market);
                origin.add(responses);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        upbitOrderService.tradeByAccount("tjdfhrdk10@naver.com");
    }

}
