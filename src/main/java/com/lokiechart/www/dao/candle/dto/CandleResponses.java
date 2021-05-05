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
        this.candleResponses = sortedCandleResponses(candleResponses);
        this.maxSize = maxSize;
    }

    public CandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses) {
        this.candleResponses = sortedCandleResponses(candleResponses);
        this.maxSize = 240;
    }

    private SynchronizedNonOverlapList<CandleResponse> sortedCandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses) {
        return new SynchronizedNonOverlapList<>(candleResponses.stream().sorted((o1, o2) -> {
            if (o1.getCandleDateTimeKST().isBefore(o2.getCandleDateTimeKST())) {
                return -1;
            } else if (o1.getCandleDateTimeKST().isAfter(o2.getCandleDateTimeKST())) {
                return 1;
            }
            return 0;
        }).collect(Collectors.toList()));
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
            final int setBollingerBandsIndex = this.candleResponses.size() - (i + 1);
            if (setBollingerBandsIndex < 20) {
                break;
            }
            CandleResponse setBollingerBandsCandle = candleResponses.get(setBollingerBandsIndex);
            SynchronizedNonOverlapList<CandleResponse> copy = this.candleResponses.copyRecent(i, i + 20);
            final double middle = copy.stream().mapToDouble(CandleResponse::getTradePrice).average().orElseThrow();
            double deviation = Math.sqrt(copy.stream().mapToDouble(candle -> Math.pow(middle - candle.getTradePrice(), 2)).sum() / 20);
            setBollingerBandsCandle.setBollingerBands(middle, deviation);
        }
    }

    public Set<String> getMarkets() {
        return candleResponses.stream().map(CandleResponse::getMarket).collect(Collectors.toSet());
    }

}
