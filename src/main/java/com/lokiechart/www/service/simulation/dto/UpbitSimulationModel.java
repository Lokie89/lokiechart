package com.lokiechart.www.service.simulation.dto;

import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author SeongRok.Oh
 * @since 2021/05/19
 */
@Builder
@Getter
@RequiredArgsConstructor
public class UpbitSimulationModel implements SimulationModel {
    private final AccountStrategyResponse accountStrategyResponse;
    private final CandleResponses candleResponses;
    private final AssetResponses assetResponses;

    public SimulationResult experiment() {
        final int candleSize = candleResponses.getCandleResponses().size();
        final int startIndex = accountStrategyResponse.getOrderStrategies().minIndex();
        for (int i = startIndex; i < candleSize; i++) {

        }
        return null;
    }
}
