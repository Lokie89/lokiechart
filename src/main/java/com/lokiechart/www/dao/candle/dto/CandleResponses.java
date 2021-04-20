package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
@RequiredArgsConstructor
public class CandleResponses {
    private final SynchronizedNonOverlapList<CandleResponse> candleResponses;
    private final int maxSize = 240;

    public void add(CandleResponses responses) {
        this.candleResponses.addAll(responses.candleResponses);
        while (candleResponses.size() > maxSize) {
            candleResponses.removeOldest();
        }
    }
}
