package com.lokiechart.www.dao.candle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.OrderSide;
import com.lokiechart.www.dao.order.dto.OrderType;
import com.lokiechart.www.dao.order.dto.UpbitOrderParameter;
import com.lokiechart.www.exception.CannotCompareObjectException;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@EqualsAndHashCode(of = {"market", "candleDateTimeKST"})
public abstract class UpbitCandleResponse implements CandleResponse, Comparable<UpbitCandleResponse> {

    @Getter
    @ApiModelProperty(value = "마켓", example = "KRW-BTC")
    @JsonProperty("market")
    private String market;

    @ApiModelProperty(value = "캔들 기준 시각 (UTC)", example = "2018-04-18T00:00:00")
    @JsonProperty("candle_date_time_utc")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime candleDateTimeUTC;

    @Getter
    @ApiModelProperty(value = "캔들 기준 시각 (KST)", example = "2018-04-18T00:09:00")
    @JsonProperty("candle_date_time_kst")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime candleDateTimeKST;

    @ApiModelProperty(value = "시가", example = "73132000")
    @JsonProperty("opening_price")
    private Double openingPrice;

    @ApiModelProperty(value = "고가", example = "73200000")
    @JsonProperty("high_price")
    private Double highPrice;

    @ApiModelProperty(value = "저가", example = "73086000")
    @JsonProperty("low_price")
    private Double lowPrice;

    @Getter
    @ApiModelProperty(value = "종가", example = "73100000")
    @JsonProperty("trade_price")
    private Double tradePrice;

    @ApiModelProperty(value = "해당 캔들에서 마지막 틱이 저장된 시각", example = "1618767207085")
    @JsonProperty("timestamp")
    private Long timestamp;

    @ApiModelProperty(value = "누적 거래 금액", example = "585008853.39090000")
    @JsonProperty("candle_acc_trade_price")
    private Double accTradePrice;

    @ApiModelProperty(value = "누적 거래량", example = "7.99672811")
    @JsonProperty("candle_acc_trade_volume")
    private Double accTradeVolume;

    @ApiModelProperty(value = "아래 볼린저 밴드", example = "7.99672811")
    private double lowBollingerBands;

    @ApiModelProperty(value = "중간 볼린저 밴드", example = "7.99672811")
    private double middleBands;

    @ApiModelProperty(value = "위 볼린저 밴드", example = "7.99672811")
    private double highBollingerBands;


    @Getter
    @Setter
    @ApiModelProperty(value = "14일 동안 상승의 합", example = "978.3")
    private double averageUp;

    @Getter
    @Setter
    @ApiModelProperty(value = "14일 동안 하락의 합", example = "634.1")
    private double averageDown;

    @Getter
    @Setter
    @ApiModelProperty(value = "rsi", example = "70.23")
    private double rsi;

    @Getter
    @Setter
    @ApiModelProperty(value = "line120", example = "859.3")
    private double line120;

    @Override
    public OrderParameter toBuyOrderParameter(OrderType orderType) {
        return UpbitOrderParameter.builder().market(market).side(OrderSide.BUY).price(tradePrice).orderType(orderType).build();
    }

    @Override
    public OrderParameter toSellOrderParameter(Double volume, OrderType orderType) {
        Double sellTradePrice = tradePrice;
        if (isPowerfulTicker()) {
            orderType = OrderType.DOWNERMARKET;
        }
        if (orderType.equals(OrderType.DOWNERMARKET)) {
            sellTradePrice = null;
        }
        if (orderType.equals(OrderType.UPPERMARKET)) {
            volume = null;
        }
        return UpbitOrderParameter.builder().market(market).side(OrderSide.SELL).price(sellTradePrice).volume(volume).orderType(orderType).build();
    }

    @Override
    public Double compareVolumePercentage(CandleResponse compare) {
        UpbitCandleResponse other = getCompareInstance(compare);
        return (this.accTradeVolume - other.accTradeVolume) / other.accTradeVolume * 100;
    }

    @Override
    public Double compareTradePricePercentage(CandleResponse compare) {
        UpbitCandleResponse other = getCompareInstance(compare);
        return (this.tradePrice - other.tradePrice) / other.tradePrice * 100;
    }

    @Override
    public Double compareUnderBollingerBands() {
        if (lowBollingerBands == 0) {
            return null;
        }
        return (tradePrice - lowBollingerBands) / lowBollingerBands * 100;
    }

    @Override
    public Double compareOverBollingerBands() {
        if (highBollingerBands == 0) {
            return null;
        }
        return (tradePrice - highBollingerBands) / highBollingerBands * 100;
    }

    @Override
    public Double compareMiddleBands() {
        if (middleBands == 0) {
            return null;
        }
        return (tradePrice - middleBands) / middleBands * 100;
    }

    @Override
    public Double compare120LinePercentage(CandleResponse compare) {
        UpbitCandleResponse other = getCompareInstance(compare);
        if (this.line120 == 0 || other.line120 == 0) {
            return null;
        }
        return (this.line120 - other.line120) / other.line120 * 100;
    }

    @Override
    public Double compareOpeningTradePricePercentage() {
        return (tradePrice - openingPrice) / openingPrice * 100;
    }

    @Override
    public void setBollingerBands(double middle, double deviation) {
        this.lowBollingerBands = middle - deviation * 2;
        this.middleBands = middle;
        this.highBollingerBands = middle + deviation * 2;
    }

    @Override
    public void set120Line(double average) {
        this.line120 = average;
    }

    @Override
    public double getIncreasePercent() {
        return getChangePrice() / this.openingPrice * 100;
    }

    @Override
    public double getChangePrice() {
        return this.tradePrice - this.openingPrice;
    }

    private boolean isPowerfulTicker() {
        return tradePrice < 300;
    }

    private boolean canCompare(final Object other) {
        return other instanceof UpbitCandleResponse;
    }

    private UpbitCandleResponse getCompareInstance(final Object compare) {
        if (!canCompare(compare)) {
            throw new CannotCompareObjectException();
        }
        return (UpbitCandleResponse) compare;
    }

    @Override
    public int compareTo(UpbitCandleResponse o) {
        if (this.candleDateTimeKST.isBefore(o.candleDateTimeKST)) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toLog() {
        return candleDateTimeKST + " " + market + " " + tradePrice;
    }
}
