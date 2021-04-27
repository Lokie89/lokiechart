package com.lokiechart.www.batch;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import lombok.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/04/25
 */
@ToString
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
@Getter
public class OrderStrategy {
    private final TradeStrategy tradeStrategy;
    private final CandleMinute candleMinute;

    public CandleResponses match() {
        Map<String, CandleResponses> liveCandles = candleMinute.getLiveCandles();
        CandleResponses matchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        for (String key : liveCandles.keySet()) {
            CandleResponse matched = tradeStrategy.match(liveCandles.get(key));
            if (Objects.isNull(matched)) {
                continue;
            }
            matchedCandleResponses.add(matched);
        }
        return matchedCandleResponses;
    }
}
