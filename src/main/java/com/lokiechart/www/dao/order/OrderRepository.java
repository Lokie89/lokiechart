package com.lokiechart.www.dao.order;

import com.lokiechart.www.dao.order.dto.OrderParameter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface OrderRepository {
    String order(String account, OrderParameter request);
}
