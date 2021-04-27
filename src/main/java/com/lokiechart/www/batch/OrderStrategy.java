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

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private final Integer onceInvestKRW;
    private final OrderType orderType;

    public OrderParameters matchBuy(AssetResponses assetResponses) {
        Map<String, CandleResponses> liveCandles = candleMinute.getLiveCandles();
        CandleResponses matchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        for (String key : liveCandles.keySet()) {
            CandleResponse matched = tradeStrategy.match(liveCandles.get(key));
            if (Objects.isNull(matched)) {
                continue;
            }
            if (assetResponses.isAlreadyOwnAndNotCheapEnough(matched)) {
                continue;
            }
            matchedCandleResponses.add(matched);
        }
        return new OrderParameters(matchedCandleResponses.getCandleResponses()
                .stream()
                .map(candleResponse -> candleResponse.toBuyOrderParameter(OrderSide.BUY, onceInvestKRW, orderType))
                .collect(Collectors.toList())
        );
    }

    public OrderParameters matchSell(AssetResponses assetResponses) {
        Map<String, CandleResponses> liveCandles = candleMinute.getLiveCandles();
        CandleResponses matchedCandleResponses = new CandleResponses(new SynchronizedNonOverlapList<>());
        for (AssetResponse assetResponse : assetResponses) {
            if (assetResponse.getCurrency().equals("KRW")) {
                continue;
            }
            String market = "KRW-" + assetResponse.getCurrency();
            CandleResponse matched = tradeStrategy.match(liveCandles.get(market));
            if (Objects.isNull(matched)) {
                continue;
            }
            if (assetResponse.avgBuyPricePercent(matched.getTradePrice()) < 0.5) {
                continue;
            }
            matchedCandleResponses.add(matched);
        }
        return new OrderParameters(matchedCandleResponses.getCandleResponses()
                .stream()
                .map(candleResponse -> candleResponse.toSellOrderParameter(OrderSide.SELL, assetResponses.getBalanceByMarket(candleResponse.getMarket()), orderType))
                .collect(Collectors.toList())
        );
    }
}
