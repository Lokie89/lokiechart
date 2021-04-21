package com.lokiechart.www.dao.candle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.lokiechart.www.dao.order.dto.OrderParameter;
import com.lokiechart.www.dao.order.dto.UpbitOrderParameter;
import com.lokiechart.www.dao.order.dto.UpbitOrderSide;
import com.lokiechart.www.dao.order.dto.UpbitOrderType;
import com.lokiechart.www.exception.CannotCompareObjectException;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@EqualsAndHashCode(of = {"market", "candleDateTimeKST"})
public abstract class UpbitCandleResponse implements CandleResponse {

    @ApiModelProperty(value = "마켓", example = "KRW-BTC")
    @JsonProperty("market")
    private String market;

    @ApiModelProperty(value = "캔들 기준 시각 (UTC)", example = "2018-04-18T00:00:00")
    @JsonProperty("candle_date_time_utc")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime candleDateTimeUTC;

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

    @Override
    public OrderParameter toOrderParameter(UpbitOrderSide orderSide, Double totalCost) {
        return UpbitOrderParameter.builder().market(market).side(orderSide).price(tradePrice).volume(totalCost / tradePrice).orderType(UpbitOrderType.LIMIT).build();
    }

    @Override
    public Double compareVolumeReplacePercentage(CandleResponse compare) {
        UpbitCandleResponse other = getCompareInstance(compare);
        return (this.accTradeVolume - other.accTradeVolume) / other.accTradeVolume * 100;
    }

    @Override
    public Double compareTradePricePercentage(CandleResponse compare) {
        UpbitCandleResponse other = getCompareInstance(compare);
        return (this.tradePrice - other.tradePrice) / other.tradePrice * 100;
    }

    private boolean canCompare(final Object other) {
        return other instanceof UpbitCandleResponse;
    }

    private UpbitCandleResponse getCompareInstance(final Object compare) {
        if (!canCompare(compare)) {
            throw new CannotCompareObjectException();
        }
        UpbitCandleResponse other = (UpbitCandleResponse) compare;
        return other;
    }
}
