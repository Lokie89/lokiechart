package com.lokiechart.www.dao.market;

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
public class UpbitMarketRepository implements MarketRepository {
    private final CallByApi api;

    public String getMarkets() {
        final String prefixUrl = "https://api.upbit.com/v1/market/";
        return api.get(prefixUrl + "all", HttpHeaders.EMPTY);
    }
}
