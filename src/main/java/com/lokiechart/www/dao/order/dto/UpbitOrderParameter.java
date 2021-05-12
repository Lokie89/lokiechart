package com.lokiechart.www.dao.order.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.exception.order.NotEnoughTotalCostException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */

/**
 * market : 마켓 ID (필수)
 * side : 주문 종류 (필수)
 * - bid : 매수
 * - ask : 매도
 * volume : 주문량 (지정가, 시장가 매도 시 필수)
 * price : 주문 가격. (지정가, 시장가 매수 시 필수)
 * ex) KRW-BTC 마켓에서 1BTC당 1,000 KRW로 거래할 경우, 값은 1000 이 된다.
 * ex) KRW-BTC 마켓에서 1BTC당 매도 1호가가 500 KRW 인 경우, 시장가 매수 시 값을 1000으로 세팅하면 2BTC가 매수된다.
 * (수수료가 존재하거나 매도 1호가의 수량에 따라 상이할 수 있음)
 * ord_type : 주문 타입 (필수)
 * - limit : 지정가 주문
 * - price : 시장가 주문(매수)
 * - market : 시장가 주문(매도)
 */
@EqualsAndHashCode(of = "market", callSuper = false)
@ToString
@Getter
public class UpbitOrderParameter extends AbstractOrderParameter {

    @NotEmpty
    private String market;

    @NotEmpty
    private OrderSide side;

    @Min(0)
    private Double volume;

    @Getter
    @Min(0)
    private Double price;

    @JsonProperty("ord_type")
    @NotEmpty
    private OrderType orderType;

    @JsonGetter("side")
    private String getSide() {
        return side.getUpbitParameter();
    }

    @JsonGetter("ord_type")
    private String getOrderType() {
        return orderType.getUpbitParameter();
    }

    @Builder
    private UpbitOrderParameter(String market, OrderSide side, Double volume, Double price, OrderType orderType) {
        this.market = market;
        this.orderType = orderType;
        this.side = side;
        this.volume = volume;
        this.price = price;
//        validateMinimumOrderCost();
    }

    private void validateMinimumOrderCost() {
        if (!side.equals(OrderSide.SELL) && !enoughTotalCost(volume * price)) {
            throw new NotEnoughTotalCostException(this.toString());
        }
    }

    @Override
    public void setOrderParams(int onceInvestKRW) {
        Double buyVolume = onceInvestKRW / price;
        Double buyTradePrice = price;
        if (orderType.equals(OrderType.DOWNERMARKET)) {
            buyTradePrice = null;
        }
        if (orderType.equals(OrderType.UPPERMARKET)) {
            buyVolume = null;
        }
        this.price = buyTradePrice;
        this.volume = buyVolume;
    }

    private boolean enoughTotalCost(double totalCost) {
        return totalCost >= 5000;
    }

    @Override
    public String toLog() {
        return market + " " + side.name() + " " + volume + " " + price + " " + orderType.name();
    }

    @Override
    public boolean isAlreadyOwnAndNotCheapEnough(AssetResponses assetResponses, final double scaleTradingPercent) {
        return assetResponses.stream().anyMatch(assetResponse ->
                assetResponse.isSameMarket(market)
                        && assetResponse.isExistTotalBalance()
                        && assetResponse.avgBuyPricePercent(price) > scaleTradingPercent * -1);
    }
}
