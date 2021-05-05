package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.common.ThreadSleep;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponse;
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
import java.util.Objects;

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

    // TODO : 19개일때 3개가 한꺼번에 주문 됨
    @Override
    public void buyByAccount(AccountResponse accountResponse, final CandleMinute candleMinute, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findBuyStrategically(candleMinute, assetResponses);
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

    @Override
    public void sellAssetByAccount(AccountResponse accountResponse, Double profitPercent) {
        if (Objects.isNull(profitPercent)) {
            profitPercent = -100.0;
        }
        AssetResponses sortedProfitAsset = getProfitAsset(accountResponse, profitPercent);
        for (AssetResponse assetResponse : sortedProfitAsset) {
            upbitOrderRepository.order(accountResponse.getEmail(), assetResponse.toSellParameter());
        }
    }

    private AssetResponses getProfitAsset(AccountResponse accountResponse, final double profitPercent) {
        AssetResponses assetResponses = upbitAssetService.getAssets(accountResponse);
        List<String> markets = assetResponses.getMarkets();
        TickerResponses tickerResponses = upbitTickerService.getTickersByMarkets(markets.toString().replace("[", "").replace("]", "").replaceAll(" ", ""));
        return assetResponses.sortProfitLiveTradePrice(tickerResponses, profitPercent);
    }

}
