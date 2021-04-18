package com.lokiechart.www.dao.candle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */

/**
 * market : 마켓명
 * candle_date_time_utc : 캔들 기준 시각(UTC 기준)
 * candle_date_time_kst : 캔들 기준 시각(KST 기준)
 * opening_price : 시가
 * high_price : 고가
 * low_price : 저가
 * trade_price : 종가
 * timestamp : 해당 캔들에서 마지막 틱이 저장된 시각
 * candle_acc_trade_price : 누적 거래 금액
 * candle_acc_trade_volume : 누적 거래량
 * unit : 분 단위(유닛)
 */
@ToString
@Getter
public class UpbitMinuteCandleResponse extends UpbitCandleResponse {

    @ApiModelProperty(value = "마켓", example = "KRW-BTC")
    private String market;

    @ApiModelProperty(value = "캔들 기준 시각 (UTC)", example = "2018-04-18T00:00:00")
    @JsonProperty("candle_date_time_utc")
    private LocalDateTime candleDateTimeUTC;

    @ApiModelProperty(value = "캔들 기준 시각 (KST)", example = "2018-04-18T00:09:00")
    @JsonProperty("candle_date_time_kst")
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
    private Long timestamp;

    @ApiModelProperty(value = "누적 거래 금액", example = "585008853.39090000")
    @JsonProperty("candle_acc_trade_price")
    private Double accTradePrice;

    @ApiModelProperty(value = "누적 거래량", example = "7.99672811")
    @JsonProperty("candle_acc_trade_volume")
    private Double accTradeVolume;

    @ApiModelProperty(value = "분 단위", example = "30")
    private Integer unit;

}
