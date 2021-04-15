package com.lokiechart.www.dao.asset;

import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@RequiredArgsConstructor
@Component
public class UpbitAssetRepository {

    private final ApiHeader upbitHeader;
    private final CallByApi api;

    public String getAssets(String account) {
        final String url = "https://api.upbit.com/v1/accounts";
        return api.get(url, upbitHeader.getHeaders(account));
    }

}
