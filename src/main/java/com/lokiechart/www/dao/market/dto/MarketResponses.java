package com.lokiechart.www.dao.market.dto;

import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@RequiredArgsConstructor
public class MarketResponses implements Iterable<MarketResponse> {
    private final List<MarketResponse> marketResponseList;

    public List<String> getMarkets() {
        return marketResponseList.stream()
                .map(MarketResponse::getMarket)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public Iterator<MarketResponse> iterator() {
        return marketResponseList.iterator();
    }
}
