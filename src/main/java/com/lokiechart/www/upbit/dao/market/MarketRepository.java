package com.lokiechart.www.upbit.dao.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.upbit.dao.CallByApi;
import com.lokiechart.www.upbit.dao.market.dto.MarketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class MarketRepository {
    private final String prefixUrl = "https://api.upbit.com/v1/market/";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CallByApi api;

    public List<MarketResponse> getCoinList() throws JsonProcessingException {
        String jsonArray = api.get(prefixUrl + "all", HttpHeaders.EMPTY);
        List<MarketResponse> marketRespons = Arrays.asList(objectMapper.readValue(jsonArray, MarketResponse[].class));
        System.out.println(marketRespons);
        return marketRespons;
    }
}
