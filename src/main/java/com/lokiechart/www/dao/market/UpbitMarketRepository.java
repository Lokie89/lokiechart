package com.lokiechart.www.dao.market;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.market.dto.UpbitMarketResponse;
import com.lokiechart.www.dao.market.dto.UpbitMarketResponses;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class UpbitMarketRepository implements MarketRepository {
    private final CallByApi api;
    private final ConvertType convertType;

    public UpbitMarketResponses getMarkets() {
        final String prefixUrl = "https://api.upbit.com/v1/market/";
        UpbitMarketResponse[] upbitMarketResponses = convertType.stringToType(api.get(prefixUrl + "all", HttpHeaders.EMPTY), UpbitMarketResponse[].class);
        return new UpbitMarketResponses(Arrays.asList(upbitMarketResponses));
    }
}
