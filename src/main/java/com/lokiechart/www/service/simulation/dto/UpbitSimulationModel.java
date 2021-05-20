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
    private final AccountStrategyResponses accountStrategyResponses;
    private final Map<CandleMinute, Map<String, CandleResponses>> simulateCandles;
    private final AssetResponses assetResponses;

    public SimulationResult experiment() {
        Map<CandleMinute, Map<String, CandleResponses>> experimentMap = new ConcurrentHashMap<>();
        int index = accountStrategyResponses.getOrderStrategySet().stream().mapToInt(OrderStrategies::minIndex).min().getAsInt();
        for (CandleMinute candleMinute : simulateCandles.keySet()) {
            Map<String, CandleResponses> candleResponsesMap = simulateCandles.get(candleMinute);
            Map<String, CandleResponses> experimentCandles = new ConcurrentHashMap<>();
            for (String market : candleResponsesMap.keySet()) {
                CandleResponses candleResponses = new CandleResponses(candleResponsesMap.get(market).getCandleResponses().copy(0, index));
                experimentCandles.put(market, candleResponses);
            }
            experimentMap.put(candleMinute, experimentCandles);
        }

        buy(experimentMap);
        sell(experimentMap);
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
            System.out.println(assetResponses);

        }
    }
}
