package com.lokiechart.www.dao.asset;

import com.lokiechart.www.dao.asset.dto.UpbitAssetResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface AssetRepository {
    UpbitAssetResponses getAssets(String account);
}
