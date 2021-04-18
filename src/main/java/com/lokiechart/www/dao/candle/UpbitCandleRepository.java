package com.lokiechart.www.dao.candle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.GetParameterUrl;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class UpbitCandleRepository implements CandleRepository {
    private final CallByApi api;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CandleResponse> getCandles(GetParameterUrl parameter) {
        String response = api.get(parameter.getUrl(), HttpHeaders.EMPTY);
        return objectMapper.convertValue(response, new TypeReference<>() {});
    }

}
