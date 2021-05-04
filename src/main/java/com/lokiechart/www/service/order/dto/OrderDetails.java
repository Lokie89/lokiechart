package com.lokiechart.www.service.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/04
 */
@ToString
@RequiredArgsConstructor
@Getter
public class OrderDetails implements Iterable<OrderDetail>{
    private final List<OrderDetail> orderDetails;

    @Override
    public Iterator<OrderDetail> iterator() {
        return null;
    }
}
