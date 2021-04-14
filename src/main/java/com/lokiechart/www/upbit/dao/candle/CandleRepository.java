package com.lokiechart.www.upbit.dao.candle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.upbit.dao.CallByApi;
import com.lokiechart.www.upbit.dao.candle.dto.CandleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class CandleRepository {
    private final CallByApi api;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String prefixUrl = "https://api.upbit.com/v1/candles/minutes/";

    public List<CandleResponse> getCandleList(String market, int count) {
        final String url = prefixUrl + "3?market=" + market + "&count=" + count;
        String jsonArray = api.get(url, HttpHeaders.EMPTY);
        List<CandleResponse> candleResponseList = null;
        try {
            candleResponseList = Arrays.asList(objectMapper.readValue(jsonArray, CandleResponse[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candleResponseList;
    }
}
