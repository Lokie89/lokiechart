package com.lokiechart.www.dao.ticker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.ticker.dto.TickerResponses;
import com.lokiechart.www.dao.ticker.dto.UpbitTickerResponse;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@RequiredArgsConstructor
@Component
public class UpbitTickerRepository implements TickerRepository {
    private final CallByApi api;
    private final ConvertType convertType;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TickerResponses getTicker(String markets) {
        final String url = "https://api.upbit.com/v1/ticker?markets=" + markets;
        UpbitTickerResponse[] upbitTickerResponses = convertType.stringToType(api.get(url, HttpHeaders.EMPTY), UpbitTickerResponse[].class);
        return new TickerResponses(Arrays.asList(upbitTickerResponses));
    }
}
