package com.lokiechart.www.dao.market.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@RequiredArgsConstructor
@Getter
public class MarketResponses {
    private final List<MarketResponse> marketResponseList;

    public List<String> getMarkets() {
        return marketResponseList.stream()
                .map(MarketResponse::getMarket)
                .collect(Collectors.toList())
                ;
    }
}
