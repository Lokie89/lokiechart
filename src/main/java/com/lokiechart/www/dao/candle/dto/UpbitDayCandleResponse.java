package com.lokiechart.www.dao.candle.dto;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

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
 * prev_closing_price : 전일 종가(UTC 0시 기준)
 * change_price : 전일 종가 대비 변화 금액
 * change_rate : 전일 종가 대비 변화량
 * converted_trade_price : 종가 환산 화폐 단위로 환산된 가격(요청에 convertingPriceUnit 파라미터 없을 시 해당 필드 포함되지 않음.)
 */
@ToString(callSuper = true)
@Getter
public class UpbitDayCandleResponse extends UpbitCandleResponse {

    @ApiModelProperty(value = "전일 종가(UTC 0시 기준)", example = "73085000")
    @JsonProperty("prev_closing_price")
    private Double previousPrice;

    @ApiModelProperty(value = "전일 종가 대비 변화 금액", example = "-1000")
    @JsonProperty("change_price")
    private Double changePrice;

    @ApiModelProperty(value = "전일 종가 대비 변화량", example = "-0.02")
    @JsonProperty("change_rate")
    private Double changeRate;

    @ApiModelProperty(value = "종가 환산 화폐 단위로 환산된 가격", example = "73100000")
    @JsonProperty("converted_trade_price")
    private Double convertedPrice;
}
