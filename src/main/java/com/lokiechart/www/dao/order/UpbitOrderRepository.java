package com.lokiechart.www.dao.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderParameters;
import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UpbitOrderRepository implements OrderRepository {

    private final CallByApi api;
    private final ApiHeader upbitHeader;

    private final Logger logger = LoggerFactory.getLogger(UpbitOrderRepository.class);

    @Override
    public String order(String account, OrderParameter request) {
        Map<String, Object> params = request.toParameter();

        String url = "https://api.upbit.com/v1/orders";
        String mapAsJson = null;
        try {
            mapAsJson = new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return api.post(url, upbitHeader.getHeaders(account, params), mapAsJson);

    }

    public void buyByStrategy(AccountResponse accountResponse, final CandleMinute candleMinute, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findBuyStrategically(candleMinute, assetResponses);
        for (OrderParameter parameter : matchMarkets) {
            logger.warn(LocalDateTime.now() + " ORDER BUY : " + accountResponse + " : " + parameter);
            order(accountResponse.getEmail(), parameter);
        }
    }

    public void sellByStrategy(AccountResponse accountResponse, final AssetResponses assetResponses) {
        OrderParameters matchMarkets = accountResponse.findSellStrategically(assetResponses);
        for (OrderParameter parameter : matchMarkets) {
            logger.warn(LocalDateTime.now() + " ORDER SELL : " + accountResponse + " : " + parameter);
            order(accountResponse.getEmail(), parameter);
        }
    }
}
