package com.lokiechart.www.service.asset;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/04/27
 */
public interface AssetService {
    AssetResponses getAssets(AccountResponse accountResponse);
    Integer getTotalSeed(AccountResponse accountResponse);
}
