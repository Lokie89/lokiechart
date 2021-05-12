package com.lokiechart.www.batch;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.asset.dto.AssetResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/25
 */
@Slf4j
@ToString
@EqualsAndHashCode(of = {"tradeStrategy", "candleMinute", "orderType"})
@Builder
@RequiredArgsConstructor
@Getter
public class OrderStrategy {
    // TODO : Buy, Sell OrderStrategy 로 분리
    private final Logger logger = LoggerFactory.getLogger(OrderStrategy.class);

    private final TradeStrategy tradeStrategy;
    private final CandleMinute candleMinute;
    private final OrderType orderType;

    public OrderParameters matchSell(AssetResponses assetResponses, final double incomePercent) {
        Map<String, CandleResponses> liveCandles = candleMinute.getLiveCandles();
        CandleResponses matchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        for (AssetResponse assetResponse : assetResponses) {
            if (assetResponse.isBaseCurrency()) {
                continue;
            }
            if (!assetResponse.isExistSellBalance()) {
                continue;
            }
            String market = assetResponse.getMarketCurrency();
            CandleResponses candleResponses = liveCandles.get(market);
            if (Objects.isNull(candleResponses)) {
                continue;
            }
            CandleResponse matched = tradeStrategy.match(candleResponses);
            if (Objects.isNull(matched)) {
                continue;
            }
            if (assetResponse.avgBuyPricePercent(matched.getTradePrice()) < incomePercent) {
                logger.warn(matched.toLog() + " " + incomePercent + "% 이상 올라야 매도함");
                continue;
            }
            matchedCandleResponses.add(matched);
        }
        return new OrderParameters(matchedCandleResponses.getCandleResponses()
                .stream()
                .map(candleResponse -> candleResponse.toSellOrderParameter(assetResponses.getBalanceByMarket(candleResponse.getMarket()), orderType))
                .collect(Collectors.toList())
        );
    }

    public boolean equalsByCandleMinute(final CandleMinute candleMinute) {
        return this.candleMinute.equals(candleMinute);
    }

    public boolean equalsByOrderSide(final OrderSide orderSide) {
        return this.tradeStrategy.getOrderSide().equals(orderSide);
    }

    public OrderParameters matchBuying() {
        Map<String, CandleResponses> liveCandles = candleMinute.getLiveCandles();
        CandleResponses matchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        for (String key : liveCandles.keySet()) {
            if (!UpbitCandlesBatch.isAlready15PercentNotIncreasedInTwoDays.get(key)) {
                continue;
            }
            CandleResponse matched = tradeStrategy.match(liveCandles.get(key));
            if (Objects.isNull(matched)) {
                continue;
            }
            matchedCandleResponses.add(matched);
        }
        return new OrderParameters(matchedCandleResponses.getCandleResponses()
                .stream()
                .map(candleResponse -> candleResponse.toBuyOrderParameter(orderType))
                .collect(Collectors.toList())
        );
    }

}
