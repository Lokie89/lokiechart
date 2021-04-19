package com.lokiechart.www.dao.candle.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
@RequiredArgsConstructor
public class CandleResponses {
    private final List<CandleResponse> candleResponses;
}
