package com.lokiechart.www.service.simulation.dto;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.batch.OrderStrategies;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/05/19
 */
@Builder
@Getter
@RequiredArgsConstructor
public class UpbitSimulationModel implements SimulationModel {
    private final Logger logger = LoggerFactory.getLogger(UpbitSimulationModel.class);
    private final AccountStrategyResponses accountStrategyResponses;
    private final Map<CandleMinute, Map<String, CandleResponses>> simulateCandles;
    private final AssetResponses assetResponses;

    public SimulationResult experiment() {
        Map<CandleMinute, Map<String, CandleResponses>> experimentMap = new ConcurrentHashMap<>();
        final int index = accountStrategyResponses.getOrderStrategySet().stream().mapToInt(OrderStrategies::minIndex).max().getAsInt();
        // TODO : CandleSize 해결
        final int candleSize = simulateCandles.get(CandleMinute.THREE).get("KRW-BTC").getCandleResponses().size();
        for (int i = 0; i <= candleSize-index; i++) {
            for (CandleMinute candleMinute : simulateCandles.keySet()) {
                Map<String, CandleResponses> candleResponsesMap = simulateCandles.get(candleMinute);
                Map<String, CandleResponses> experimentCandles = new ConcurrentHashMap<>();
                for (String market : candleResponsesMap.keySet()) {
                    CandleResponses candleResponses = new CandleResponses(candleResponsesMap.get(market).getCandleResponses().copy(i, index + i));
                    experimentCandles.put(market, candleResponses);
                }
                experimentMap.put(candleMinute, experimentCandles);
            }
            buy(experimentMap);
            sell(experimentMap);
        }
        System.out.println(assetResponses);
        return SimulationResult.builder().build();
    }

    private void sell(Map<CandleMinute, Map<String, CandleResponses>> experimentCandles) {

    }

    private void buy(Map<CandleMinute, Map<String, CandleResponses>> experimentCandles) {
        AccountStrategyResponses buyAccountStrategyResponses = accountStrategyResponses.filterOrderSide(OrderSide.BUY);
        for (AccountStrategyResponse accountStrategyResponse : buyAccountStrategyResponses) {
            OrderParameters buyOrderParameters = accountStrategyResponse.getOrderStrategies().getMatchedOrderParameters(experimentCandles);
            buyOrderParameters.filterByAccount(accountStrategyResponse, assetResponses);
            assetResponses.buy(buyOrderParameters);
        }
    }
}
