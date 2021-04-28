package com.lokiechart.www.dao.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.tunnel.ApiHeader;
import com.lokiechart.www.dao.tunnel.CallByApi;
import com.lokiechart.www.service.order.dto.OrderList;
import com.lokiechart.www.service.order.dto.UpbitOrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

    @Override
    public String order(String email, OrderParameter request) {
        Map<String, Object> params = request.toParameter();

        String url = "https://api.upbit.com/v1/orders";
        String mapAsJson = null;
        try {
            mapAsJson = new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return api.post(url, upbitHeader.getHeaders(email, params), mapAsJson);

    }

    @Override
    public List<OrderList> getOrdered(String email) {
        String url = "https://api.upbit.com/v1/orders?state=wait";
        UpbitOrderList[] upbitOrderLists = convertType.stringToType(api.get(url, upbitHeader.getHeaders(email, null)), UpbitOrderList[].class);
        return Arrays.asList(upbitOrderLists);
    }


}
