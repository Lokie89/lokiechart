package com.lokiechart.www.batch;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.common.ThreadSleep;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    static final Map<String, Boolean> isAlready15PercentNotIncreasedInTwoDays = new ConcurrentHashMap<>();

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
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get1MinuteCandles(market, howGetCandles);
            CandleResponses origin = upbitOneMinuteCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }
        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get3MinutesCandles(market, howGetCandles);
            CandleResponses origin = upbitThreeMinuteCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }

        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get5MinutesCandles(market, howGetCandles);
            CandleResponses origin = upbitFiveMinuteCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }
        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get15MinutesCandles(market, howGetCandles);
            CandleResponses origin = upbitFifteenMinuteCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }
        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get30MinutesCandles(market, howGetCandles);
            CandleResponses origin = upbitThirtyMinuteCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }
        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get60MinutesCandles(market, howGetCandles);
            CandleResponses origin = upbitSixtyMinuteCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }
        for (MarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get1DayCandles(market, howGetCandles);
            responses.setRsi(howGetCandles);
            CandleResponses origin = upbitDayCandles.get(market);
            origin.addAll(responses);
            ThreadSleep.doSleep(100);
        }
        for (String market : upbitDayCandles.keySet()) {
            CandleResponses candleResponses = upbitDayCandles.get(market);
            SynchronizedNonOverlapList<CandleResponse> candles = candleResponses.getCandleResponses();
            isAlready15PercentNotIncreasedInTwoDays.put(market, candles.copyRecent(1, 3).stream().allMatch(candle -> candle.getIncreasePercent() < 15 && candles.getRecent(0).getIncreasePercent() < 10));
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
        updateUpbitMinuteCandles(CandleMinute.ONE, 300);
    }

    @Scheduled(cron = "${schedule.candle.three-minutes}")
    private void updateUpbitThreeMinuteCandles() {
        updateUpbitMinuteCandles(CandleMinute.THREE, 500);
    }

    @Scheduled(cron = "${schedule.candle.five-minutes}")
    private void updateUpbitFiveMinuteCandles() {
        updateUpbitMinuteCandles(CandleMinute.FIVE, 1000);
    }

    @Scheduled(cron = "${schedule.candle.fifteen-minutes}")
    private void updateUpbitFifteenMinuteCandles() {
        updateUpbitMinuteCandles(CandleMinute.FIFTEEN, 1000);
    }

    @Scheduled(cron = "${schedule.candle.thirty-minutes}")
    private void updateUpbitThirtyMinuteCandles() {
        updateUpbitMinuteCandles(CandleMinute.THIRTY, 1000);
    }

    @Scheduled(cron = "${schedule.candle.one-hour}")
    private void updateUpbitSixtyMinuteCandles() {
        updateUpbitMinuteCandles(CandleMinute.SIXTY, 1000);
    }


    private void updateUpbitMinuteCandles(final CandleMinute candleMinute, final int sleepTime) {
        long start = System.currentTimeMillis();
        logger.info("Scheduling UPBIT " + candleMinute.getNumber() + " MINUTES CANDLES UPDATE");
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            setUpbitMinuteCandles(market, candleMinute, sleepTime);
        });
        logger.info(candleMinute.getNumber() + " MINUTES SIZE " + candleMinute.getLiveCandles().get("KRW-BTC").getCandleResponses().size());
        logger.info(System.currentTimeMillis() - start + "밀리초");
    }

    void setUpbitMinuteCandles(final String market, final CandleMinute candleMinute, final int sleepTime) {
        CandleResponses origin = candleMinute.getLiveCandles().get(market);
        int onceCallGetCount = howToGetCandles(origin, candleMinute.getNumber());
        CandleResponses responses = upbitCandleService.getMinuteCandles(candleMinute, market, onceCallGetCount);
        origin.addAll(responses);
        origin.set120Line(onceCallGetCount);
        origin.setUnderBollingerBands(onceCallGetCount);
        origin.setRsi(onceCallGetCount);
        ThreadSleep.doSleep(sleepTime);
    }

    void updateAssetCandles(Set<MarketCandleMinute> marketCandleMinutes) {
        marketCandleMinutes.forEach(marketCandleMinute -> {
            final String market = marketCandleMinute.getMarket();
            final CandleMinute candleMinute = marketCandleMinute.getCandleMinute();
            logger.info(market + " " + candleMinute + " Update AssetCandles");
            setUpbitMinuteCandles(market, candleMinute, 1000);
        });
    }

    @Scheduled(cron = "${schedule.candle.one-hour}")
    private void updateUpbitDayCandles() {
        logger.info("Scheduling UPBIT 1 DAY CANDLES UPDATE");
        CandleMinute candleMinute = CandleMinute.DAY;
        upbitMarket.forEach(marketResponse -> {
            String market = marketResponse.getMarket();
            CandleResponses origin = upbitDayCandles.get(market);
            int onceCallGetCount = howToGetCandles(origin, candleMinute.getNumber());
            CandleResponses responses = upbitCandleService.get1DayCandles(market, onceCallGetCount);
            origin.addAll(responses);
            origin.set120Line(onceCallGetCount);
            origin.setUnderBollingerBands(onceCallGetCount);
            origin.setRsi(onceCallGetCount);
            ThreadSleep.doSleep(1000);
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
            ThreadSleep.doSleep(1000);
        });
        logger.info("1 DAY SIZE " + upbitDayCandles.get("KRW-BTC").getCandleResponses().size());
    }

}
