package com.lokiechart.www.service.candle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
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
public class CandleResponse {
    private String market;
    @JsonProperty("candle_date_time_utc")
    private String candleDateTimeUTC;
    @JsonProperty("candle_date_time_kst")
    private String candleDateTimeKST;
    @JsonProperty("opening_price")
    private Double openingPrice;
    @JsonProperty("high_price")
    private Double highPrice;
    @JsonProperty("low_price")
    private Double lowPrice;
    @JsonProperty("trade_price")
    private Double tradePrice;
    private Long timestamp;
    @JsonProperty("candle_acc_trade_price")
    private Double candleAccTradePrice;
    @JsonProperty("candle_acc_trade_volume")
    private Double candleAccTradeVolume;
    private Integer unit;
}
