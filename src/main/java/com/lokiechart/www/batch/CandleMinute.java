package com.lokiechart.www.batch;

import com.lokiechart.www.dao.candle.dto.CandleResponses;
import lombok.Getter;

import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public enum CandleMinute {
    ONE(1) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitOneMinuteCandles;
        }
    },
    THREE(3) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitThreeMinuteCandles;
        }
    },
    FIVE(5) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitFiveMinuteCandles;
        }
    },
    TEN(10) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitTenMinuteCandles;
        }
    },
    FIFTEEN(15) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitFifteenMinuteCandles;
        }
    },
    THIRTY(30) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitThirtyMinuteCandles;
        }
    },
    SIXTY(60) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitSixtyMinuteCandles;
        }
    },
    TWOFORTY(240) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitTwoFortyMinuteCandles;
        }
    },
    DAY(1440) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitDayCandles;
        }
    },
    WEEK(10080) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitWeekCandles;
        }
    },
    MONTH(43200) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandlesBatch.upbitMonthCandles;
        }
    },
    ;

    @Getter
    private final int number;

    CandleMinute(int number) {
        this.number = number;
    }

    public abstract Map<String, CandleResponses> getLiveCandles();
}
