package com.lokiechart.www.dao.order;

import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.service.order.dto.OrderDetail;

import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface OrderRepository {
    String order(String email, OrderParameter request);
    List<OrderDetail> getOrdered(String email);
    void cancelOrder(String email, String uuid);
}
