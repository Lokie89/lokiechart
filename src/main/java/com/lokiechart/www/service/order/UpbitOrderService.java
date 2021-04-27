package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.UpbitOrderRepository;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Service
public class UpbitOrderService implements OrderService {
    private final UpbitOrderRepository upbitOrderRepository;
    private final Logger logger = LoggerFactory.getLogger(UpbitOrderService.class);

    @Override
    public void buyByAccount(AccountResponse accountResponse, final CandleMinute candleMinute, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findBuyStrategically(candleMinute, assetResponses);
        for (OrderParameter parameter : matchMarkets) {
            logger.warn(LocalDateTime.now() + " ORDER BUY : " + accountResponse + " : " + parameter);
            upbitOrderRepository.order(accountResponse.getEmail(), parameter);
        }
    }

    @Override
    public void sellByAccount(AccountResponse accountResponse, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findSellStrategically(assetResponses);
        for (OrderParameter parameter : matchMarkets) {
            logger.warn(LocalDateTime.now() + " ORDER SELL : " + accountResponse + " : " + parameter);
            upbitOrderRepository.order(accountResponse.getEmail(), parameter);
        }
    }
}
