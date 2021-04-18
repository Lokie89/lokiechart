package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.dao.candle.CandleMinute;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/18
 */
@Builder
@AllArgsConstructor
@Getter
public class UpbitMinuteCandleParameter {
    @ApiModelProperty(value = "분 단위", required = true, example = "THIRTY")
    @NotNull
    private CandleMinute candleMinute;
    @ApiModelProperty(value = "코인 이름",required = true,example = "KRW-BTC")
    @NotEmpty
    private String market;
    @ApiModelProperty(value = "캔들 개수", required = true, example = "20")
    @Min(1)
    private int count;
    @ApiModelProperty(value = "마지막 캔들 시각", example = "2018-04-18T10:16:00")
    private LocalDateTime to;
}
