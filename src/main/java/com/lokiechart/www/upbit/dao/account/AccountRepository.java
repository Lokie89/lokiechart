package com.lokiechart.www.upbit.dao.account;

import com.lokiechart.www.upbit.dao.CallByApi;
import com.lokiechart.www.upbit.dao.TokenHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@RequiredArgsConstructor
@Component
public class AccountRepository {

    private final TokenHeader tokenHeader;
    private final CallByApi api;

    public String getAssets(String account) {
        final String serverUrl = "https://api.upbit.com";
        return api.get(serverUrl + "/v1/accounts", tokenHeader.getHeaders(account));
    }

}
