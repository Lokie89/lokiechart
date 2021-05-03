package com.lokiechart.www.dao.asset.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lokiechart.www.dao.market.MarketRepository;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.dao.order.dto.UpbitOrderParameter;
import lombok.*;

import java.util.Objects;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpbitAssetResponse implements AssetResponse {
    private String currency;
    private Double balance;
    private Double locked;
    @JsonProperty("avg_buy_price")
    private Double avgBuyPrice;
    @JsonProperty("avg_buy_price_modified")
    private Boolean avgBuyPriceModified;
    @JsonProperty("unit_currency")
    private String unitCurrency;

    @Override
    public boolean isSameMarket(String market) {
        return market.equals(getMarketCurrency());
    }

    @Override
    public Double avgBuyPricePercent(double tradePrice) {
        return (tradePrice - avgBuyPrice) / avgBuyPrice * 100;
    }

    @Override
    public OrderParameter toSellParameter() {
        if (isBaseCurrency()) {
            return null;
        }
        return UpbitOrderParameter.builder().market(getMarketCurrency()).volume(balance).side(OrderSide.SELL).orderType(OrderType.DOWNERMARKET).build();
    }

    @Override
    public Integer getTotalCost() {
        if (isBaseCurrency() || Objects.isNull(avgBuyPrice)) {
            return balance.intValue();
        }
        return (int) ((balance + locked) * avgBuyPrice);
    }

    @Override
    public String getMarketCurrency() {
        return unitCurrency + "-" + currency;
    }

    @Override
    public boolean isExistBalance() {
        return Objects.nonNull(avgBuyPrice) && isPossibleOrder();
    }

    public UpbitAssetResponse getApplyPrice(Double price) {
        UpbitAssetResponse applyPriceAsset = new UpbitAssetResponse();
        applyPriceAsset.currency = this.currency;
        applyPriceAsset.balance = this.balance;
        applyPriceAsset.locked = this.locked;
        applyPriceAsset.avgBuyPrice = price;
        applyPriceAsset.avgBuyPriceModified = this.avgBuyPriceModified;
        applyPriceAsset.unitCurrency = this.unitCurrency;
        return applyPriceAsset;
    }

    public boolean isBaseCurrency() {
        return currency.equals(unitCurrency);
    }

    @Override
    public boolean isPossibleOrder() {
        return getTotalCost() > 5000;
    }

    @Override
    public boolean isPossibleBalanceOrder() {
        return balance * avgBuyPrice > 5000;
    }

    @Override
    public String toString() {
        return currency +
                "\t|\t" +
                avgBuyPrice +
                "\t|\t" +
                (balance + locked) +
                "\n";
    }
}
