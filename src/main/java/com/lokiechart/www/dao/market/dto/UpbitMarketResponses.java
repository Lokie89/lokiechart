package com.lokiechart.www.dao.market.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@ToString
@Getter
public class UpbitMarketResponses {
    private final List<UpbitMarketResponse> upbitMarketResponses;
}
