package com.lokiechart.www.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/05/17
 */
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class MarketCandleMinute {
    private String market;
    private CandleMinute candleMinute;
}
