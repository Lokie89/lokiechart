package com.lokiechart.www.dao.ticker.dto;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * market	종목 구분 코드	String
 * trade_date	최근 거래 일자(UTC)	String
 * trade_time	최근 거래 시각(UTC)	String
 * trade_date_kst	최근 거래 일자(KST)	String
 * trade_time_kst	최근 거래 시각(KST)	String
 * opening_price	시가	Double
 * high_price	고가	Double
 * low_price	저가	Double
 * trade_price	종가	Double
 * prev_closing_price	전일 종가	Double
 * change	EVEN : 보합
 * RISE : 상승
 * FALL : 하락	String
 * change_price	변화액의 절대값	Double
 * change_rate	변화율의 절대값	Double
 * signed_change_price	부호가 있는 변화액	Double
 * signed_change_rate	부호가 있는 변화율	Double
 * trade_volume	가장 최근 거래량	Double
 * acc_trade_price	누적 거래대금(UTC 0시 기준)	Double
 * acc_trade_price_24h	24시간 누적 거래대금	Double
 * acc_trade_volume	누적 거래량(UTC 0시 기준)	Double
 * acc_trade_volume_24h	24시간 누적 거래량	Double
 * highest_52_week_price	52주 신고가	Double
 * highest_52_week_date	52주 신고가 달성일	String
 * lowest_52_week_price	52주 신저가	Double
 * lowest_52_week_date	52주 신저가 달성일	String
 * timestamp	타임스탬프	Long
 */
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class UpbitTickerResponse implements TickerResponse {
    private String market;
    private LocalDate tradeDate;
    private LocalTime tradeTime;
    private LocalDate tradeDateKst;
    private LocalTime tradeTimeKst;
    @JsonProperty("opening_price")
    private Double openingPrice;
    @JsonProperty("high_price")
    private Double highPrice;
    @JsonProperty("low_price")
    private Double lowPrice;
    @JsonProperty("trade_price")
    private Double tradePrice;
    @JsonProperty("prev_closing_price")
    private Double prevClosingPrice;
    private TickerChange change;
    @JsonProperty("change_price")
    private Double changePrice;
    @JsonProperty("change_rate")
    private Double changeRate;
    @JsonProperty("signed_change_price")
    private Double signedChangePrice;
    @JsonProperty("signed_change_rate")
    private Double signedChangeRate;
    @JsonProperty("trade_volume")
    private Double tradeVolume;
    @JsonProperty("acc_trade_price")
    private Double accTradePrice;
    @JsonProperty("acc_trade_price_24h")
    private Double accTradePrice24h;
    @JsonProperty("acc_trade_volume")
    private Double accTradeVolume;
    @JsonProperty("acc_trade_volume_24h")
    private Double accTradeVolume24h;
    @JsonProperty("highest_52_week_price")
    private Double highest52WeekPrice;
    private LocalDate highest52WeekDate;
    @JsonProperty("lowest_52_week_price")
    private Double lowest52WeekPrice;
    private LocalDate lowest52WeekDate;
    @JsonProperty("trade_timestamp")
    private Long tradeTimestamp;
    private Long timestamp;

    @JsonSetter("trade_date")
    public void setTradeDate(String trade_date) {
        this.tradeDate = LocalDate.parse(trade_date, DateTimeFormatter.BASIC_ISO_DATE);
    }

    @JsonSetter("trade_time")
    public void setTradeTime(String trade_time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        this.tradeTime = LocalTime.parse(trade_time, dateTimeFormatter);
    }

    @JsonSetter("trade_date_kst")
    public void setTradeDateKst(String trade_date_kst) {
        this.tradeDate = LocalDate.parse(trade_date_kst, DateTimeFormatter.BASIC_ISO_DATE);
    }

    @JsonSetter("trade_time_kst")
    public void setTradeTimeKst(String trade_time_kst) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        this.tradeTime = LocalTime.parse(trade_time_kst, dateTimeFormatter);
    }

    @JsonSetter("highest_52_week_date")
    public void setHighest52WeekDate(String highest_52_week_date) {
        this.tradeDate = LocalDate.parse(highest_52_week_date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @JsonSetter("lowest_52_week_date")
    public void setLowest52WeekDate(String lowest_52_week_date) {
        this.tradeDate = LocalDate.parse(lowest_52_week_date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
