package com.lokiechart.www.dao.candle;

import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class UpbitCandleRepository implements CandleRepository {
    private final CallByApi api;
    private final String prefixUrl = "https://api.upbit.com/v1/candles/minutes/";

    public String getCandles(CandleMinute minute, String market, int count) {
        final String url = prefixUrl + minute.getNum() + "?market=" + market + "&count=" + count;
        return api.get(url, HttpHeaders.EMPTY);
    }
}
