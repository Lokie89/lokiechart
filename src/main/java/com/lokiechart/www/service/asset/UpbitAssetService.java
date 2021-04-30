package com.lokiechart.www.service.asset;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.AssetRepository;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.service.candle.UpbitCandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/27
 */
@RequiredArgsConstructor
@Service
public class UpbitAssetService implements AssetService {
    private final AssetRepository upbitAssetRepository;
    private final UpbitCandleService upbitCandleService;

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
        return new AssetResponses(
                assetResponses.getAssetResponses().stream()
                        .map(assetResponse -> {
//                            upbitCandleService.
                            return assetResponse.getApplyPrice(null);
                        })
                        .collect(Collectors.toList())
        );
    }
}
