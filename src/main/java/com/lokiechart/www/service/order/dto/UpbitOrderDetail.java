package com.lokiechart.www.service.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/28
 */
@ToString
@Getter
public class UpbitOrderDetail implements OrderDetail {
    private String uuid;
    private OrderSide side;
    private OrderType orderType;
    private Double price;
    private String state;
    private String market;
    private LocalDateTime createdAt;
    private Double volume;
    @JsonProperty(value = "remaining_volume")
    private Double remainingVolume;
    @JsonProperty(value = "reserved_fee")
    private Double reservedFee;
    @JsonProperty(value = "remaining_fee")
    private Double remainingFee;
    @JsonProperty(value = "paid_fee")
    private Double paidFee;
    private Double locked;
    @JsonProperty(value = "executed_volume")
    private Double executedVolume;
    @JsonProperty(value = "trades_count")
    private Integer tradesCount;

    @JsonSetter("ord_type")
    public void setOrderType(String orderType) {
        this.orderType = OrderType.valueOf(orderType.toUpperCase());
    }

    @JsonSetter("side")
    public void setSide(String side) {
        this.side = OrderSide.getOrderSideByUpbitParameter(side);
    }

    @JsonSetter("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public boolean isBuyingOrder() {
        return side.equals(OrderSide.BUY);
    }
}
