package com.lokiechart.www.dao.candle.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@AllArgsConstructor
@Builder
public class UpbitDayCandleParameter implements GetParameterUrl {

    @ApiModelProperty(value = "마켓", required = true, example = "KRW-BTC")
    @NotEmpty
    private String market;
    @ApiModelProperty(value = "캔들 개수", required = true, example = "20")
    @Min(1)
    private int count;
    @ApiModelProperty(value = "마지막 캔들 시각", example = "2018-04-18T10:16:00")
    private LocalDateTime to;

    @ApiModelProperty(value = "종가 환산 화폐 단위(생략 가능)", example = "KRW")
    private String convertingPriceUnit;

    @Override
    public String getUrl() {
        String url = "https://api.upbit.com/v1/candles/days?";
        url += "market=" + market + "&count=" + count;
        if (Objects.nonNull(to)) {
            url += "&to=" + to;
        }
        if (Objects.nonNull(convertingPriceUnit)) {
            url += "&convertingPriceUnit=" + convertingPriceUnit;
        }
        return url;
    }
}
