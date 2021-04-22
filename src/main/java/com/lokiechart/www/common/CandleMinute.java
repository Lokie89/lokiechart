package com.lokiechart.www.common;

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
            return UpbitCandles.upbitOneMinuteCandles;
        }
    },
    THREE(3) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandles.upbitThreeMinuteCandles;
        }
    },
    FIVE(5) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return null;
        }
    },
    TEN(10) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return null;
        }
    },
    FIFTEEN(15) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandles.upbitFifteenMinuteCandles;
        }
    },
    THIRTY(30) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandles.upbitThirtyMinuteCandles;
        }
    },
    SIXTY(60) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return null;
        }
    },
    TWOFORTY(240) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return null;
        }
    },
    DAY(1440) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return UpbitCandles.upbitDayCandles;
        }
    },
    WEEK(10080) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return null;
        }
    },
    MONTH(43200) {
        @Override
        public Map<String, CandleResponses> getLiveCandles() {
            return null;
        }
    },
    ;

    @Getter
    private int number;

    CandleMinute(int number) {
        this.number = number;
    }

    public abstract Map<String, CandleResponses> getLiveCandles();
}
