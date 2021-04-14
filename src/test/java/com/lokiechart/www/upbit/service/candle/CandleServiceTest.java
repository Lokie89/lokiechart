package com.lokiechart.www.upbit.service.candle;

import com.lokiechart.www.upbit.dao.candle.CandleRepository;
import com.lokiechart.www.upbit.dao.candle.dto.CandleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@DisplayName("캔들 서비스 테스트")
@SpringBootTest
public class CandleServiceTest {
    @Autowired
    CandleRepository repository;

    @DisplayName("캔들 가져오기 테스트")
    @Test
    void getCandlesTest() {
        final String market = "KRW-BTC";
        List<CandleResponse> candleResponseList = repository.getCandleList(market, 5);
        System.out.println(candleResponseList);
        Assertions.assertEquals(5, candleResponseList.size());
    }

}
