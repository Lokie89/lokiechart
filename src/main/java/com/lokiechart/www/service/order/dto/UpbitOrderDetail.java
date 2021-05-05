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

/**
 * uuid : 주문의 고유 아이디
 * side : 주문 종류
 * ord_type : 주문 방식
 * price : 주문 당시 화폐 가격
 * state : 주문 상태
 * market : 마켓의 유일키
 * created_at : 주문 생성 시간
 * volume : 사용자가 입력한 주문 양
 * remaining_volume : 체결 후 남은 주문 양
 * reserved_fee : 수수료로 예약된 비용
 * remaining_fee : 남은 수수료
 * paid_fee : 사용된 수수료
 * locked : 거래에 사용중인 비용
 * executed_volume : 체결된 양
 * trade_count : 해당 주문에 걸린 체결 수
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
        this.orderType = OrderType.getByUpbitParameter(orderType);
    }

    @JsonSetter("side")
    public void setSide(String side) {
        this.side = OrderSide.getOrderSideByUpbitParameter(side);
    }

    @JsonSetter("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public boolean isSameMarket(String market) {
        return this.market.equals(market);
    }

    public boolean isBuyingOrder() {
        return side.equals(OrderSide.BUY);
    }

    @Override
    public boolean isPossibleReorder() {
        return remainingVolume * price > 5000;
    }

    @Override
    public String toLog() {
        return market + " " + side.name() + " " + orderType.name() + " " + price + " 주문 양 : " + volume + " 취소 양 : " + remainingVolume;
    }
}
