package com.lokiechart.www.dao.candle.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
@EqualsAndHashCode(of = {"unit"}, callSuper = true)
@ToString(callSuper = true)
@Getter
public class UpbitMinuteCandleResponse extends UpbitCandleResponse {

    @ApiModelProperty(value = "분 단위", example = "30")
    private Integer unit;

}
