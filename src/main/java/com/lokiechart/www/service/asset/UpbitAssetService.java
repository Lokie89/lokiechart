package com.lokiechart.www.service.asset;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.AssetRepository;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/04/27
 */
@RequiredArgsConstructor
@Service
public class UpbitAssetService implements AssetService {
    private final AssetRepository upbitAssetRepository;

    @Override
    public AssetResponses getAssets(AccountResponse accountResponse) {
        return upbitAssetRepository.getAssets(accountResponse.getEmail());
    }

    @Override
    public Integer getTotalSeed(AccountResponse accountResponse) {
        return upbitAssetRepository.getTotalSeed(accountResponse.getEmail());
    }
}
