package com.lokiechart.www.dao.market.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */

/**
 * market : 업비트에서 제공중인 시장 정보
 * korean_name : 거래 대상 암호화폐 한글명
 * english_name : 거래 대상 암호화폐 영문명
 * market_warning : 유의 종목 여부 / NONE (해당 사항 없음), CAUTION(투자유의)
 */
@ToString
@Getter
public class UpbitMarketResponse {
    private String market;
    @JsonProperty("korean_name")
    private String korean;
    @JsonProperty("english_name")
    private String english;
    @JsonProperty("market_warning")
    private String warning;
}
