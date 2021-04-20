package com.lokiechart.www.dao.candle;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.candle.dto.GetParameterUrl;
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
public abstract class UpbitCandleRepository implements CandleRepository {
    private final CallByApi api;

    public CandleResponses getCandles(GetParameterUrl parameter) {
        String response = api.get(parameter.getUrl(), HttpHeaders.EMPTY);
        return new CandleResponses(getCandlesByTime(response));
    }

    protected abstract SynchronizedNonOverlapList<CandleResponse> getCandlesByTime(String response);

}
