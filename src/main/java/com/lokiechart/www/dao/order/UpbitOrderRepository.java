package com.lokiechart.www.dao.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import com.lokiechart.www.service.order.dto.OrderDetail;
import com.lokiechart.www.service.order.dto.UpbitOrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private final ConvertType convertType;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String order(String email, OrderParameter request) {
        Map<String, Object> params = request.toParameter();

        String url = "https://api.upbit.com/v1/orders";
        String mapAsJson = null;
        try {
            mapAsJson = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return api.post(url, upbitHeader.getHeaders(email, params), mapAsJson);

    }

    @Override
    public List<OrderDetail> getOrdered(String email) {
        String url = "https://api.upbit.com/v1/orders?state=wait";
        Map<String, Object> param = new HashMap<>();
        param.put("state", "wait");
        UpbitOrderDetail[] upbitOrderDetails = convertType.stringToType(api.get(url, upbitHeader.getHeaders(email, param)), UpbitOrderDetail[].class);
        return Arrays.asList(upbitOrderDetails);
    }

    @Override
    public void cancelOrder(String email, String uuid) {
        String url = "https://api.upbit.com/v1/order";
        Map<String, Object> params = new HashMap<>();
        params.put("uuid", uuid);
        String mapAsJson = null;
        try {
            mapAsJson = new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        convertType.stringToType(api.delete(url, upbitHeader.getHeaders(email, params), mapAsJson), UpbitOrderDetail.class);
    }


}
