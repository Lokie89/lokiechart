package com.lokiechart.www.service.order;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.service.order.dto.OrderDetails;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
public interface OrderService {
    void buyByAccount(AccountStrategyResponse accountStrategyResponse, final OrderParameters matchParameters);
    void sellByAccount(AccountStrategyResponse accountStrategyResponse, final AssetResponses assetResponses);
    OrderDetails getOrderDetails(AccountResponse accountResponse);
    void cancelNotProcess(AccountResponse accountResponse, OrderDetails orderDetails);
    void sellAssetByAccount(AccountResponse accountResponse, Double profitPercent);
}
