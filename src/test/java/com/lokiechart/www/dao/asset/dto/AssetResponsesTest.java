package com.lokiechart.www.dao.asset.dto;

import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.order.dto.*;
import com.lokiechart.www.service.candle.UpbitCandleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/05
 */
@DisplayName("자산 응답 객체 리스트 테스트")
@SpringBootTest
class AssetResponsesTest {

    @Autowired
    UpbitCandleService upbitCandleService;

    @DisplayName("이미 가지고 있고 가격이 많이 안떨어짐 테스트")
    @Test
    void isAlreadyOwnAndNotCheapEnoughTest() {
        List<AssetResponse> assetResponseList = new ArrayList<>();
        UpbitAssetResponse upbitAssetResponse = UpbitAssetResponse.builder().avgBuyPrice(69.5).build();
        assetResponseList.add(upbitAssetResponse);
        AssetResponses assetResponses = new AssetResponses(assetResponseList);

        CandleResponses candleResponses = upbitCandleService.get3MinutesCandles("KRW-STMX", 30, LocalDateTime.of(2021, 5, 4, 23, 12, 10));
        candleResponses.setUnderBollingerBands(5);
        CandleResponse candleResponse = candleResponses.getCandleResponses().getRecent(0);

    }

    @DisplayName("구매 테스트")
    @Test
    void buyTest() {
        List<AssetResponse> assetResponseList = new ArrayList<>();
        UpbitAssetResponse upbitAssetResponse = UpbitAssetResponse.builder().currency("KRW").unitCurrency("KRW").avgBuyPrice(null).balance(10000.0).avgBuyPriceModified(false).locked(0.0).build();
        assetResponseList.add(upbitAssetResponse);
        AssetResponses assetResponses = new AssetResponses(assetResponseList);

        List<OrderParameter> orderParameterList = new ArrayList<>();
        UpbitOrderParameter upbitOrderParameter = UpbitOrderParameter.builder().market("BTC").price(5000.0).side(OrderSide.BUY).volume(1.0).orderType(OrderType.LIMIT).build();
        orderParameterList.add(upbitOrderParameter);
        OrderParameters orderParameters = new OrderParameters(orderParameterList);
        assetResponses.buy(orderParameters);
        System.out.println(assetResponses);
    }
}
