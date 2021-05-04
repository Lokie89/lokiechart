package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.common.ThreadSleep;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.UpbitOrderRepository;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.ticker.dto.TickerResponses;
import com.lokiechart.www.service.asset.UpbitAssetService;
import com.lokiechart.www.service.order.dto.OrderDetail;
import com.lokiechart.www.service.order.dto.OrderDetails;
import com.lokiechart.www.service.ticker.TickerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Service
public class UpbitOrderService implements OrderService {
    private final UpbitOrderRepository upbitOrderRepository;
    private final UpbitAssetService upbitAssetService;
    private final TickerService upbitTickerService;
    private final Logger logger = LoggerFactory.getLogger(UpbitOrderService.class);

    @Override
    public void buyByAccount(AccountResponse accountResponse, final CandleMinute candleMinute, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findBuyStrategically(candleMinute, assetResponses);
        int possibleOrder = assetResponses.existAssetSize();
        for (OrderParameter parameter : matchMarkets) {
            logger.warn("ORDER BUY : " + accountResponse.getEmail() + " : " + parameter.toLog());
            upbitOrderRepository.order(accountResponse.getEmail(), parameter);
            ThreadSleep.doSleep(125);
        }
    }

    @Override
    public void sellByAccount(AccountResponse accountResponse, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findSellStrategically(assetResponses);
        for (OrderParameter parameter : matchMarkets) {
            logger.warn("ORDER SELL : " + accountResponse.getEmail() + " : " + parameter.toLog());
            upbitOrderRepository.order(accountResponse.getEmail(), parameter);
            ThreadSleep.doSleep(125);
        }
    }

    @Override
    public OrderDetails getOrderDetails(AccountResponse accountResponse) {
        return upbitOrderRepository.getOrdered(accountResponse.getEmail());
    }

    @Override
    public void cancelNotProcess(AccountResponse accountResponse, OrderDetails orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            LocalDateTime createdAt = orderDetail.getCreatedAt();
            LocalDateTime now = LocalDateTime.now();
            if (createdAt.isBefore(now.minusMinutes(3)) && (orderDetail.isBuyingOrder() || orderDetail.isPossibleReorder())) {
                logger.warn("ORDER CANCEL : " + accountResponse.getEmail() + " : " + orderDetail.toLog());
                upbitOrderRepository.cancelOrder(accountResponse.getEmail(), orderDetail.getUuid());
            }
            ThreadSleep.doSleep(125);
        }
    }

    public void sellAllByAccount(AccountResponse accountResponse) {
        AssetResponses assetResponses = upbitAssetService.getAssets(accountResponse);
        assetResponses.forEach(assetResponse -> upbitOrderRepository.order(accountResponse.getEmail(), assetResponse.toSellParameter()));
    }

    // TODO 만들기
    public void sellIncomeAsset(AccountResponse accountResponse){
        AssetResponses assetResponses = upbitAssetService.getAssets(accountResponse);
        List<String> markets=assetResponses.getMarkets();
        TickerResponses tickerResponses = upbitTickerService.getTickersByMarkets(markets.toString().replace("[","").replace("]","").replaceAll(" ",""));
    }

}
