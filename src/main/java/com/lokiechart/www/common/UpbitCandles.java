package com.lokiechart.www.common;

import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.market.MarketRepository;
import com.lokiechart.www.dao.market.dto.UpbitMarketResponse;
import com.lokiechart.www.service.candle.UpbitCandleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeongRok.Oh
 * @since 2021/04/20
 */
@Slf4j
@Component
public class UpbitCandles {
    private final Logger logger = LoggerFactory.getLogger(UpbitCandles.class);

    private final MarketRepository upbitMarketRepository;
    private final UpbitCandleService upbitCandleService;

    public UpbitCandles(MarketRepository upbitMarketRepository, UpbitCandleService upbitCandleService) {
        this.upbitMarketRepository = upbitMarketRepository;
        this.upbitCandleService = upbitCandleService;
        init();
    }

    static List<UpbitMarketResponse> upbitMarket;
    static final Map<String, CandleResponses> upbitOneMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitThreeMinuteCandles = new ConcurrentHashMap<>();
    //    static final Map<String, CandleResponses> upbitFiveMinuteCandles = new ConcurrentHashMap<>();
//    static final Map<String, CandleResponses> upbitTenMinuteCandles = new ConcurrentHashMap<>();
//    static final Map<String, CandleResponses> upbitFifteenMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitThirtyMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitSixtyMinuteCandles = new ConcurrentHashMap<>();
    //    static final Map<String, CandleResponses> upbitTwoFortyMinuteCandles = new ConcurrentHashMap<>();
    static final Map<String, CandleResponses> upbitDayCandles = new ConcurrentHashMap<>();
//    static final Map<String, CandleResponses> upbitWeekCandles = new ConcurrentHashMap<>();
//    static final Map<String, CandleResponses> upbitMonthCandles = new ConcurrentHashMap<>();

    private void init() {
        updateUpbitMarket();
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            upbitOneMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
            upbitThreeMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
//            upbitFiveMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
//            upbitTenMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
//            upbitFifteenMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
            upbitThirtyMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
            upbitSixtyMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
//            upbitTwoFortyMinuteCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
            upbitDayCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
//            upbitWeekCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
//            upbitMonthCandles.put(market, new CandleResponses(new SynchronizedNonOverlapList<>(new ArrayList<>())));
        }
    }

    @Scheduled(cron = "1 0 0 * * *")
    private void updateUpbitMarket() {
        logger.info("Scheduling UPBIT MARKET UPDATE");
        upbitMarket = upbitMarketRepository.getMarkets();
    }

    @Scheduled(cron = "1 * * * * *")
    private void updateUpbitOneMinuteCandles() {
        logger.info("Scheduling UPBIT 1 MINUTES CANDLES UPDATE");
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get1MinuteCandles(market, 2);
            CandleResponses origin = upbitOneMinuteCandles.get(market);
            origin.add(responses);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("1 MINUTE " + market);
        }
        logger.info("1 MINUTE SIZE " + upbitOneMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "1 */3 * * * *")
    private void updateUpbitThreeMinuteCandles() {
        logger.info("Scheduling UPBIT 3 MINUTES CANDLES UPDATE");
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get3MinutesCandles(market, 2);
            CandleResponses origin = upbitThreeMinuteCandles.get(market);
            origin.add(responses);
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("3 MINUTES " + market);
        }
        logger.info("SIZE " + upbitThreeMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "1 */30 * * * *")
    private void updateUpbitThirtyMinuteCandles() {
        logger.info("Scheduling UPBIT 30 MINUTES CANDLES UPDATE");
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get30MinutesCandles(market, 2);
            CandleResponses origin = upbitThirtyMinuteCandles.get(market);
            origin.add(responses);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("30 MINUTES " + market);
        }
        logger.info("SIZE " + upbitThirtyMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "1 0 * * * *")
    private void updateUpbitSixtyMinuteCandles() {
        logger.info("Scheduling UPBIT 60 MINUTES CANDLES UPDATE");
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get60MinutesCandles(market, 2);
            CandleResponses origin = upbitSixtyMinuteCandles.get(market);
            origin.add(responses);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("60 MINUTES " + market);
        }
        logger.info("SIZE " + upbitSixtyMinuteCandles.get("KRW-BTC").getCandleResponses().size());
    }

    @Scheduled(cron = "1 0 9 * * *")
    private void updateUpbitDayCandles() {
        logger.info("Scheduling UPBIT 1 DAY CANDLES UPDATE");
        for (UpbitMarketResponse marketResponse : upbitMarket) {
            String market = marketResponse.getMarket();
            CandleResponses responses = upbitCandleService.get1DayCandles(market, 2);
            CandleResponses origin = upbitDayCandles.get(market);
            origin.add(responses);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("1 DAY " + market);
        }
        logger.info("SIZE " + upbitDayCandles.get("KRW-BTC").getCandleResponses().size());
    }

}
