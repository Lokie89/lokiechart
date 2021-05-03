package com.lokiechart.www.service.order.dto;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/28
 */
public interface OrderDetail {
    String getUuid();
    LocalDateTime getCreatedAt();
    boolean isBuyingOrder();
    boolean isPossibleReorder();
    String toLog();
}
