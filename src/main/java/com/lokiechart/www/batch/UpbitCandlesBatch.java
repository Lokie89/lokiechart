package com.lokiechart.www.batch;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.market.MarketRepository;
import com.lokiechart.www.dao.market.dto.MarketResponse;
import com.lokiechart.www.service.candle.UpbitCandleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
@Slf4j
@Component
public class UpbitCandlesBatch {
    private final Logger logger = LoggerFactory.getLogger(UpbitCandlesBatch.class);

    private final MarketRepository upbitMarketRepository;
    private final UpbitCandleService upbitCandleService;

    public UpbitCandlesBatch(MarketRepository upbitMarketRepository, UpbitCandleService upbitCandleService) {
        this.upbitMarketRepository = upbitMarketRepository;
        this.upbitCandleService = upbitCandleService;
        init();
    }

    static List<MarketResponse> upbitMarket;
    static final Map<String, CandleResponses> upbitOneMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitThreeMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitFiveMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitTenMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitFifteenMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitThirtyMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitSixtyMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitTwoFortyMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitDayCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitWeekCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitMonthCandles = new ConcurrentHashMap<>();

    static final Map<String, Boolean> isAlready15PercentNotIncreasedInTwoDays = new HashMap<>();

    private void init() {
        final int minutesOfDay = 1440;
        updateUpbitMarket();
        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            upbitOneMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(), minutesOfDay));
            upbitThreeMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(), minutesOfDay / 3));
            upbitFiveMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(), minutesOfDay / 5));
//            upbitTenMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
            upbitFifteenMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
            upbitThirtyMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
            upbitSixtyMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
            upbitDayCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
//            upbitTwoFortyMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
//            upbitWeekCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
//            upbitMonthCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>()));
        }
        initPreCandles();
    }

    private void initPreCandles() {
        final int howGetCandles = 200;
        for (MarketResponse marketResponse : upbitMarket) {
            try {
                Thread.sleep(100);
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get1MinuteCandles(market, howGetCandles);
                CandleResponses origin = upbitOneMinuteCandles.get(market);
                origin.addAll(responses);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (MarketResponse marketResponse : upbitMarket) {
            try {
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get3MinutesCandles(market, howGetCandles);
                CandleResponses origin = upbitThreeMinuteCandles.get(market);
                origin.addAll(responses);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (MarketResponse marketResponse : upbitMarket) {
            try {
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get5MinutesCandles(market, howGetCandles);
                CandleResponses origin = upbitFiveMinuteCandles.get(market);
                origin.addAll(responses);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (MarketResponse marketResponse : upbitMarket) {
            try {
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get15MinutesCandles(market, howGetCandles);
                CandleResponses origin = upbitFifteenMinuteCandles.get(market);
                origin.addAll(responses);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (MarketResponse marketResponse : upbitMarket) {
            try {
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get30MinutesCandles(market, howGetCandles);
                CandleResponses origin = upbitThirtyMinuteCandles.get(market);
                origin.addAll(responses);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (MarketResponse marketResponse : upbitMarket) {
            try {
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get60MinutesCandles(market, howGetCandles);
                CandleResponses origin = upbitSixtyMinuteCandles.get(market);
                origin.addAll(responses);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (MarketResponse marketResponse : upbitMarket) {
            try {
                String market = marketResponse.getMarket();
                CandleResponses responses = upbitCandleService.get1DayCandles(market, howGetCandles);
                CandleResponses origin = upbitDayCandles.get(market);
                origin.addAll(responses);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (String market : upbitDayCandles.keySet()) {
            CandleResponses candleResponses = upbitDayCandles.get(market);
            SynchronizedNonOverlapList<CandleResponse> candles = candleResponses.getCandleResponses();
            isAlready15PercentNotIncreasedInTwoDays.put(market, candles.copy(1, 3).stream().allMatch(candle -> candle.getIncreasePercent() < 15));
        }
    }

    @Scheduled(cron = "${schedule.market}")
    private void updateUpbitMarket() {
        logger.info("Scheduling UPBIT MARKET UPDATE");
        upbitMarket = upbitMarketRepository.getMarkets();
    }


    private int howToGetCandles(CandleResponses origin, int duration) {
        LocalDateTime recentTime = origin.getCandleResponses().getRecent(0).getCandleDateTimeKST();
        return (int) (Duration.between(recentTime, LocalDateTime.now()).dividedBy(Duration.ofMinutes(duration)) + 1);
    }

    @Scheduled(cron = "${schedule.candle.one-minute}")
    private void updateUpbitOneMinuteCandles() {
        logger.info("Scheduling UPBIT 1 MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitOneMinuteCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 1);
            CandleResponses responses = upbitCandleService.get1MinuteCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("1 MINUTE SIZE " + upbitOneMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.three-minutes}")
    private void updateUpbitThreeMinuteCandles() {
        logger.info("Scheduling UPBIT 3 MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitThreeMinuteCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 3);
            CandleResponses responses = upbitCandleService.get3MinutesCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("3 MINUTES SIZE " + upbitThreeMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.five-minutes}")
    private void updateUpbitFiveMinuteCandles() {
        logger.info("Scheduling UPBIT 5 MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitFiveMinuteCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 5);
            CandleResponses responses = upbitCandleService.get5MinutesCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("5 MINUTES SIZE " + upbitFiveMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.fifteen-minutes}")
    private void updateUpbitFifteenMinuteCandles() {
        logger.info("Scheduling UPBIT 15 MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitFifteenMinuteCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 15);
            CandleResponses responses = upbitCandleService.get15MinutesCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("15 MINUTES SIZE " + upbitFifteenMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.thirty-minutes}")
    private void updateUpbitThirtyMinuteCandles() {
        logger.info("Scheduling UPBIT 30 MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitThirtyMinuteCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 30);
            CandleResponses responses = upbitCandleService.get30MinutesCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("30 MINUTES SIZE " + upbitThirtyMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.one-hour}")
    private void updateUpbitSixtyMinuteCandles() {
        logger.info("Scheduling UPBIT 60 MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitSixtyMinuteCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 60);
            CandleResponses responses = upbitCandleService.get60MinutesCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("60 MINUTES SIZE " + upbitSixtyMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.one-hour}")
    private void updateUpbitDayCandles() {
        logger.info("Scheduling UPBIT 1 DAY CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitDayCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, 1440);
            CandleResponses responses = upbitCandleService.get1DayCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.setUnderBollingerBands(onceCallGetCount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("1 DAY SIZE " + upbitDayCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "${schedule.candle.increase-day}")
    private void updateAlreadyIncreasedCandles() {
        logger.info("Scheduling UPBIT 1 DAY ALREADY INCREASED CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses candleResponses = upbitDayCandles.get(market);
            SynchronizedNonOverlapList<CandleResponse> candles = candleResponses.getCandleResponses();
            isAlready15PercentNotIncreasedInTwoDays.put(market, candles.copyRecent(1, 3).stream().allMatch(candle -> candle.getIncreasePercent() < 15) && candles.getRecent(0).getIncreasePercent() < 10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("1 DAY SIZE " + upbitDayCandles.get("KRW-BTC").getCandleResponses().size());
    }

}
