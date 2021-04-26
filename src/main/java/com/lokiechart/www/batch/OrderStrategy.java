package com.lokiechart.www.batch;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author SeongRok.Oh
 * @since 2021/04/25
 */
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Getter
public class OrderStrategy {
    private TradeStrategy tradeStrategy;
    private CandleMinute candleMinute;

    public CandleResponses match(final Set<String> markets) {
        Map<String, CandleResponses> liveCandles = candleMinute.getLiveCandles();
        CandleResponses matchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        final boolean existMarkets = Objects.nonNull(markets) && !markets.isEmpty();
        for (String key : liveCandles.keySet()) {
            if (existMarkets && !markets.contains(key)) {
                continue;
            }
            CandleResponse matched = tradeStrategy.match(liveCandles.get(key));
            if (Objects.isNull(matched)) {
                continue;
            }
            matchedCandleResponses.add(matched);
        }
        return matchedCandleResponses;
    }
}
