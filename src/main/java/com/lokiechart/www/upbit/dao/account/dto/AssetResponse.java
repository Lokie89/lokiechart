package com.lokiechart.www.upbit.dao.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */

/**
 * currency : 화폐를 의미하는 영문 대문자 코드
 * balance : 주문가능 금액/수량
 * locked : 주문 중 묶여있는 금액/수량
 * avg_buy_price : 매수평균가
 * avg_buy_price_modified : 매수평균가 수정 여부
 * unit_currency : 평단가 기준 화폐
 */
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {
    private String currency;
    private Double balance;
    private Double locked;
    @JsonProperty("avg_buy_price")
    private Double avgBuyPrice;
    @JsonProperty("avg_buy_price_modified")
    private Boolean avgBuyPriceModified;
    @JsonProperty("unit_currency")
    private String unitCurrency;
}
