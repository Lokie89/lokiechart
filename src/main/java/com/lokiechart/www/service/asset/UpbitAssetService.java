package com.lokiechart.www.service.asset;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.AssetRepository;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.market.dto.MarketResponses;
import com.lokiechart.www.dao.ticker.dto.TickerResponses;
import com.lokiechart.www.service.market.MarketService;
import com.lokiechart.www.service.ticker.TickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/27
 */
@RequiredArgsConstructor
@Service
public class UpbitAssetService implements AssetService {
    private final AssetRepository upbitAssetRepository;
    private final MarketService upbitMarketService;
    private final TickerService upbitTickerService;

    @Override
    public AssetResponses getAssets(AccountResponse accountResponse) {
        return upbitAssetRepository.getAssets(accountResponse.getEmail());
    }

    @Override
    public Integer getTotalSeed(AccountResponse accountResponse) {
        return upbitAssetRepository.getTotalSeed(accountResponse.getEmail());
    }

    // TODO : getLiveAssets
    @Override
    public AssetResponses getLiveAssets(AccountResponse accountResponse) {
        AssetResponses assetResponses = getAssets(accountResponse);
        MarketResponses marketResponses = upbitMarketService.getAll();
        List<String> markets = marketResponses.getMarkets();
        String marketParams = markets.toString().replace("[", "").replace("]", "").replaceAll(" ", "");
        TickerResponses upbitTickerResponses = upbitTickerService.getTickersByMarkets(marketParams);
        return new AssetResponses(
                assetResponses.getAssetResponses().stream()
                        .map(assetResponse -> {
                            Double recentTradePrice = upbitTickerResponses.getTradePriceByMarket(assetResponse);
                            return assetResponse.getApplyPrice(recentTradePrice);
                        })
                        .collect(Collectors.toList())
        );
    }
}
