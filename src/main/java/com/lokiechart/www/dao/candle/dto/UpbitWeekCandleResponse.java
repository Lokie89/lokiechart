package com.lokiechart.www.dao.candle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
public class UpbitWeekCandleResponse extends UpbitCandleResponse {

    @ApiModelProperty(value = "캔들 기간의 가장 첫 날", example = "2018-04-16")
    @JsonProperty("first_day_of_period")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate firstDay;
}
