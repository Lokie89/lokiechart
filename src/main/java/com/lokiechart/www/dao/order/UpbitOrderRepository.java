package com.lokiechart.www.dao.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
@RequiredArgsConstructor
@Component
public class UpbitOrderRepository implements OrderRepository {

    private final CallByApi api;
    private final ApiHeader upbitHeader;

    @Override
    public String order(String account, String market, String side, Double volume, Double price, String orderType, String identifier) {

        HashMap<String, String> params = new HashMap<>();
        params.put("market", market);
        params.put("side", side);
        params.put("volume", String.valueOf(volume));
        params.put("price", String.valueOf(price));
        params.put("ord_type", orderType);

        String url = "https://api.upbit.com/v1/orders";
        String mapAsJson = null;
        try {
            mapAsJson = new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return api.post(url, upbitHeader.getHeaders(account, params), mapAsJson);

    }
}
