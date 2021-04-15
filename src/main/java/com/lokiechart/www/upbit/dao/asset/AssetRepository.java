package com.lokiechart.www.upbit.dao.asset;

import com.lokiechart.www.upbit.dao.ApiHeader;
import com.lokiechart.www.upbit.dao.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@RequiredArgsConstructor
@Component
public class AssetRepository {

    private final ApiHeader upbitHeader;
    private final CallByApi api;

    public String getAssets(String account) {
        final String serverUrl = "https://api.upbit.com";
        return api.get(serverUrl + "/v1/accounts", upbitHeader.getHeaders(account));
    }

}
