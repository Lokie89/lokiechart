package com.lokiechart.www.service.candle;

import com.lokiechart.www.common.CandleMinute;
import com.lokiechart.www.dao.candle.*;
import com.lokiechart.www.dao.candle.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
@RequiredArgsConstructor
@Service
public class UpbitCandleService implements CandleService {
    private final UpbitMinuteCandleRepository minuteCandleRepository;
    private final UpbitDayCandleRepository dayCandleRepository;
    private final UpbitWeekCandleRepository weekCandleRepository;
    private final UpbitMonthCandleRepository monthCandleRepository;

    public CandleResponses get1MinuteCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.ONE).count(candleCount).build());
    }

    public CandleResponses get3MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.THREE).count(candleCount).build());
    }

    public CandleResponses get3MinutesCandles(String market, int candleCount, LocalDateTime to) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.THREE).count(candleCount).to(to).build());
    }

    public CandleResponses get5MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.FIVE).count(candleCount).build());
    }

    public CandleResponses get10MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.TEN).count(candleCount).build());
    }

    public CandleResponses get15MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.FIFTEEN).count(candleCount).build());
    }

    public CandleResponses get30MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.THIRTY).count(candleCount).build());
    }

    public CandleResponses get60MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.SIXTY).count(candleCount).build());
    }

    public CandleResponses get240MinutesCandles(String market, int candleCount) {
        return minuteCandleRepository.getCandles(UpbitMinuteCandleParameter.builder().market(market).candleMinute(CandleMinute.TWOFORTY).count(candleCount).build());
    }

    public CandleResponses get1DayCandles(String market, int candleCount) {
        return dayCandleRepository.getCandles(UpbitDayCandleParameter.builder().market(market).count(candleCount).build());
    }

    public CandleResponses get1WeekCandles(String market, int candleCount) {
        return weekCandleRepository.getCandles(UpbitWeekCandleParameter.builder().market(market).count(candleCount).build());
    }

    public CandleResponses get1MonthCandles(String market, int candleCount) {
        return monthCandleRepository.getCandles(UpbitMonthCandleParameter.builder().market(market).count(candleCount).build());
    }
}
