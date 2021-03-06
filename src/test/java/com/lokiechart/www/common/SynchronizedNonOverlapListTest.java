package com.lokiechart.www.common;

import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
@DisplayName("병렬 중복 없는 리스트 테스트")
@SpringBootTest
class SynchronizedNonOverlapListTest {

    @Autowired
    ConvertType convertType;

    @DisplayName("생성 중복 확인")
    @Test
    void createDuplicateTest() {
        String candleResponseStr = "{\"market\":\"KRW-BTC\",\"candle_date_time_kst\":\"2018-04-18T00:09:00\",\"unit\":1}";
        UpbitMinuteCandleResponse response = convertType.stringToType(candleResponseStr, UpbitMinuteCandleResponse.class);
        UpbitMinuteCandleResponse response2 = convertType.stringToType(candleResponseStr, UpbitMinuteCandleResponse.class);
        ArrayList<UpbitMinuteCandleResponse> list = new ArrayList<>();
        list.add(response);
        list.add(response2);
        SynchronizedNonOverlapList<UpbitMinuteCandleResponse> test = new SynchronizedNonOverlapList<>(list);
        assertEquals(1, test.size());
    }


    @DisplayName("더하기 중복 확인")
    @Test
    void addDuplicateTest() {
        final SynchronizedNonOverlapList<UpbitMinuteCandleResponse> test = new SynchronizedNonOverlapList<>();
        String candleResponseStr = "{\"market\":\"KRW-BTC\",\"candle_date_time_kst\":\"2018-04-18T00:09:00\",\"unit\":1}";
        UpbitMinuteCandleResponse response = convertType.stringToType(candleResponseStr, UpbitMinuteCandleResponse.class);
        test.add(response);
        assertEquals(1, test.size());
        UpbitMinuteCandleResponse response2 = convertType.stringToType(candleResponseStr, UpbitMinuteCandleResponse.class);
        test.add(response2);
        assertEquals(1, test.size());
    }

    @DisplayName("copy 테스트")
    @Test
    void copyTest() {
        final SynchronizedNonOverlapList<UpbitMinuteCandleResponse> test = new SynchronizedNonOverlapList<>();
        String candleResponseStr1 = "{\"market\":\"KRW-BTC\",\"candle_date_time_kst\":\"2018-04-18T00:09:01\",\"unit\":1}";
        String candleResponseStr2 = "{\"market\":\"KRW-BTC\",\"candle_date_time_kst\":\"2018-04-18T00:09:02\",\"unit\":1}";
        String candleResponseStr3 = "{\"market\":\"KRW-BTC\",\"candle_date_time_kst\":\"2018-04-18T00:09:03\",\"unit\":1}";

        test.add(convertType.stringToType(candleResponseStr1, UpbitMinuteCandleResponse.class));
        test.add(convertType.stringToType(candleResponseStr2, UpbitMinuteCandleResponse.class));
        test.add(convertType.stringToType(candleResponseStr3, UpbitMinuteCandleResponse.class));
        assertEquals(3, test.size());
        assertEquals(1, test.copy(1, 2).size());
    }

}
