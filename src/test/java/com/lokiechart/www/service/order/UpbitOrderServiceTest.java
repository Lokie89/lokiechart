package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.common.ThreadSleep;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.market.UpbitMarketRepository;
import com.lokiechart.www.dao.market.dto.MarketResponse;
import com.lokiechart.www.service.asset.AssetService;
import com.lokiechart.www.service.candle.UpbitCandleService;
import com.lokiechart.www.service.order.dto.OrderDetail;
import com.lokiechart.www.service.order.dto.OrderDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    AssetService upbitAssetService;

    List<MarketResponse> upbitMarket;

    @DisplayName("Email 의 전략에 맞게 주문 하기")
    @Test
    void orderByEmail() {
        final Map<String, CandleResponses> upbitThreeMinuteCandles = new ConcurrentHashMap<>();
        upbitMarket = upbitMarketRepository.getMarkets();
        for (MarketResponse marketResponse : upbitMarket) {
            ThreadSleep.doSleep(100);
            String market = marketResponse.getMarket();
            upbitThreeMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
            CandleResponses responses = upbitCandleService.get3MinutesCandles(market, 30, LocalDateTime.of(2021, 4, 22, 17, 36, 03));
            CandleResponses origin = upbitThreeMinuteCandles.get(market);
            origin.addAll(responses);
        }
        AccountResponse accountResponse = AccountResponse.builder().email("tjdfhrdk10@naver.com").build();
        upbitOrderService.buyByAccount(accountResponse, CandleMinute.ONE, upbitAssetService.getAssets(accountResponse));
    }

    @DisplayName("미체결 매수 매도 가져오기")
    @Test
    void getOrdered() {
        OrderDetails orderLists = upbitOrderService.getOrderDetails(AccountResponse.builder().email("tjdfhrdk10@naver.com").build());
        System.out.println(orderLists);
    }

    @DisplayName("자산 이득 순으로 정렬")
    @Test
    void sellIncomeAsset(){
        upbitOrderService.sellIncomeAsset(AccountResponse.builder().email("tjdfhrdk10@naver.com").build());
    }

}
