package com.lokiechart.www.dao.market;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.market.dto.MarketResponse;
import com.lokiechart.www.dao.market.dto.UpbitMarketResponse;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Component
public class UpbitMarketRepository implements MarketRepository {
    private final CallByApi api;
    private final ConvertType convertType;

    public List<MarketResponse> getMarkets() {
        final String prefixUrl = "https://api.upbit.com/v1/market/";
        UpbitMarketResponse[] upbitMarketResponses = convertType.stringToType(api.get(prefixUrl + "all", HttpHeaders.EMPTY), UpbitMarketResponse[].class);
        return Arrays.stream(upbitMarketResponses)
                .filter((market) -> market.getMarket().contains("KRW-"))
                .collect(Collectors.toList())
                ;
    }
}
