package com.lokiechart.www.dao.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

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
}
