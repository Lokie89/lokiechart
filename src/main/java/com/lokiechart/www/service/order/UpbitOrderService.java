package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.UpbitOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Service
public class UpbitOrderService implements OrderService {
    private final UpbitOrderRepository upbitOrderRepository;

    @Override
    public void buyByAccount(AccountResponse accountResponse, final CandleMinute candleMinute, final AssetResponses assetResponses) {
        upbitOrderRepository.buyByStrategy(accountResponse, candleMinute, assetResponses);
    }

    @Override
    public void sellByAccount(AccountResponse accountResponse, final AssetResponses assetResponses) {
        upbitOrderRepository.sellByStrategy(accountResponse, assetResponses);
    }
}
