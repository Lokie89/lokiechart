package com.lokiechart.www.dao.order;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface OrderRepository {
    String order(String account, String market, String side, Double volume, Double price, String orderType, String identifier);
}
