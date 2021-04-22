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

    public void setUnderBollingerBands(final int setCount) {
        for (int i = 1; i <= setCount; i++) {
            final int countInvolveMe = this.candleResponses.size() - (setCount - i);
            if (countInvolveMe < 20) {
                continue;
            }
            final int setBollingerBandsIndex = countInvolveMe - 1;
            CandleResponse setBollingerBandsCandle = candleResponses.get(setBollingerBandsIndex);
            SynchronizedNonOverlapList<CandleResponse> copy = this.candleResponses.copy(setBollingerBandsIndex - 19, setBollingerBandsIndex);
            final double middle = copy.stream().mapToDouble(CandleResponse::getTradePrice).average().getAsDouble();
            double deviation = Math.sqrt(copy.stream().mapToDouble(candle -> Math.pow(middle - candle.getTradePrice(), 2)).sum() / 20);
            setBollingerBandsCandle.setBollingerBands(middle, deviation);
        }
    }
}
