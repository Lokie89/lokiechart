package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
public class CandleResponses {
    private final SynchronizedNonOverlapList<CandleResponse> candleResponses;
    private final int maxSize;

    public CandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses, int maxSize) {
        this.candleResponses = candleResponses;
        this.maxSize = maxSize;
    }

    public CandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses) {
        this.candleResponses = candleResponses;
        this.maxSize = 240;
    }

    public void addAll(CandleResponses responses) {
        this.candleResponses.addAll(responses.candleResponses);
        while (candleResponses.size() > maxSize) {
            candleResponses.removeOldest();
        }
    }

    public void add(CandleResponse response) {
        this.candleResponses.add(response);
        while (candleResponses.size() > maxSize) {
            candleResponses.removeOldest();
        }
    }

    public void setUnderBollingerBands(final int setCount) {
        for (int i = 0; i < setCount; i++) {
            final int countInvolveMe = this.candleResponses.size() - setCount;
            if (countInvolveMe < 20) {
                continue;
            }
            CandleResponse setBollingerBandsCandle = candleResponses.get(setCount);
            SynchronizedNonOverlapList<CandleResponse> copy = this.candleResponses.copyRecent(setCount, 20 + setCount);
            final double middle = copy.stream().mapToDouble(CandleResponse::getTradePrice).average().getAsDouble();
            double deviation = Math.sqrt(copy.stream().mapToDouble(candle -> Math.pow(middle - candle.getTradePrice(), 2)).sum() / 20);
            setBollingerBandsCandle.setBollingerBands(middle, deviation);
        }
    }

    public Set<String> getMarkets() {
        return candleResponses.stream().map(CandleResponse::getMarket).collect(Collectors.toSet());
    }
}
