package com.lokiechart.www.upbit.dao.candle;

import com.lokiechart.www.upbit.dao.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class CandleRepository {
    private final CallByApi api;
    private final String prefixUrl = "https://api.upbit.com/v1/candles/minutes/";

    public String getCandleList(String market, int count) {
        final String url = prefixUrl + "3?market=" + market + "&count=" + count;
        return api.get(url, HttpHeaders.EMPTY);
    }
}
