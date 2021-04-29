package com.lokiechart.www.dao.asset;

import com.lokiechart.www.dao.asset.dto.AssetResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface AssetRepository {
    AssetResponses getAssets(String email);
    Integer getTotalSeed(String email);
}
