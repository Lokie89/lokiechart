package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public interface OrderService {
    void buyByAccount(AccountResponse accountResponse, final CandleMinute candleMinute, final AssetResponses assetResponses);
    void sellByAccount(AccountResponse accountResponse, final AssetResponses assetResponses);
}
