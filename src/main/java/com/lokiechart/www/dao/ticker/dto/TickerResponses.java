package com.lokiechart.www.dao.ticker.dto;

import com.lokiechart.www.dao.asset.dto.AssetResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@RequiredArgsConstructor
@Getter
public class TickerResponses {
    private final List<TickerResponse> tickerResponses;

    public Double getTradePriceByMarket(AssetResponse assetResponse) {
        if (assetResponse.isBaseCurrency() || !assetResponse.isExistTotalBalance()) {
            return null;
        }
        TickerResponse ticker = tickerResponses.stream()
                .filter(tickerResponse -> tickerResponse.getMarket().equals(assetResponse.getMarketCurrency()))
                .findFirst()
                .get()
                ;
        return ticker.getTradePrice();
    }
}
