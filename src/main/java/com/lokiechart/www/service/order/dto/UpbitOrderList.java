package com.lokiechart.www.service.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author SeongRok.Oh
 * @since 2021/04/28
 */
@ToString
@Getter
public class UpbitOrderList implements OrderList {
    private String market;
    private String[] uuids;
    private String[] identifiers;
    private OrderState state;
    private OrderState[] states;
    private Integer page;
    private Integer limit;
    @JsonProperty(value = "order_By")
    private String orderBy;


}
