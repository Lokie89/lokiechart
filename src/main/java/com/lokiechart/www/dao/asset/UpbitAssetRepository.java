package com.lokiechart.www.dao.asset;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.asset.dto.UpbitAssetResponse;
import com.lokiechart.www.dao.asset.dto.UpbitAssetResponses;
import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@RequiredArgsConstructor
@Component
public class UpbitAssetRepository implements AssetRepository {

    private final ConvertType convertType;
    private final ApiHeader upbitHeader;
    private final CallByApi api;

    public UpbitAssetResponses getAssets(String account) {
        final String url = "https://api.upbit.com/v1/accounts";
        UpbitAssetResponse[] assetResponses = convertType.stringToType(api.get(url, upbitHeader.getHeaders(account, null)), UpbitAssetResponse[].class);
        return new UpbitAssetResponses(Arrays.asList(assetResponses));
    }

}
