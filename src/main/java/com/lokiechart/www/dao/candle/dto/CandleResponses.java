package com.lokiechart.www.dao.candle.dto;

import com.lokiechart.www.common.SynchronizedNonOverlapList;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@ToString
@Getter
public class CandleResponses {
    private final SynchronizedNonOverlapList<CandleResponse> candleResponses;
    private final int maxSize;

    public CandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses, int maxSize) {
        this.candleResponses = sortedCandleResponses(candleResponses);
        this.maxSize = maxSize;
    }

    public CandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses) {
        this.candleResponses = sortedCandleResponses(candleResponses);
        this.maxSize = 240;
    }

    private SynchronizedNonOverlapList<CandleResponse> sortedCandleResponses(SynchronizedNonOverlapList<CandleResponse> candleResponses) {
        return new SynchronizedNonOverlapList<>(candleResponses.stream().sorted((o1, o2) -> {
            if (o1.getCandleDateTimeKST().isBefore(o2.getCandleDateTimeKST())) {
                return -1;
            } else if (o1.getCandleDateTimeKST().isAfter(o2.getCandleDateTimeKST())) {
                return 1;
            }
            return 0;
        }).collect(Collectors.toList()));
    }

    public void addAll(CandleResponses responses) {
        this.candleResponses.addAll(responses.candleResponses);
        removeOverSize();
    }

    public void add(CandleResponse response) {
        this.candleResponses.add(response);
        removeOverSize();
    }

    private void removeOverSize() {
        while (candleResponses.size() > maxSize) {
            candleResponses.removeOldest();
        }
    }

    public void setBollingerBands(final int setCount) {
        for (int i = 0; i < setCount; i++) {
            final int setBollingerBandsIndex = this.candleResponses.size() - (i + 1);
            if (setBollingerBandsIndex < 20) {
                break;
            }
            CandleResponse setBollingerBandsCandle = candleResponses.get(setBollingerBandsIndex);
            SynchronizedNonOverlapList<CandleResponse> copy = this.candleResponses.copyRecent(i, i + 20);
            final double middle = copy.stream().mapToDouble(CandleResponse::getTradePrice).average().orElseThrow();
            double deviation = Math.sqrt(copy.stream().mapToDouble(candle -> Math.pow(middle - candle.getTradePrice(), 2)).sum() / 20);
            setBollingerBandsCandle.setBollingerBands(middle, deviation);
        }
    }

    private double calRsi(double averageUp, double averageDown) {
        double rsi = Math.round(averageUp / (averageUp + averageDown) * 10000) / 100.0;
        return rsi;
    }

    public void set120Line(final int setCount) {
        for (int i = 0; i < setCount; i++) {
            final int set120LineIndex = this.candleResponses.size() - (i + 1);
            if (set120LineIndex < 120) {
                break;
            }
            CandleResponse set120LineCandle = candleResponses.get(set120LineIndex);
            SynchronizedNonOverlapList<CandleResponse> copy = this.candleResponses.copyRecent(i, i + 120);
            final double avg = copy.stream().mapToDouble(CandleResponse::getTradePrice).average().orElseThrow();
            set120LineCandle.set120Line(avg);
        }
    }

    public void setRsi(final int setCount) {
        for (int i = setCount - 1; i >= 0; i--) {
            final int setRsiIndex = this.candleResponses.size() - (i + 1);
            if (setRsiIndex < 14) {
                continue;
            }
            CandleResponse setRsiCandle = candleResponses.getRecent(i);
            CandleResponse beforeCandle = candleResponses.getRecent(i + 1);
            double beforeAU = beforeCandle.getAverageUp() * 13;
            double beforeAD = beforeCandle.getAverageDown() * 13;
            if (beforeAD > 0 && beforeAU > 0) {
                double changePrice = setRsiCandle.getChangePrice();
                if (changePrice > 0) {
                    beforeAU += changePrice;
                } else {
                    beforeAD += (changePrice * -1);
                }
                double averageDown = beforeAD / 14;
                double averageUp = beforeAU / 14;
                setRsiCandle.setAverageDown(averageDown);
                setRsiCandle.setAverageUp(averageUp);
                setRsiCandle.setRsi(calRsi(averageUp, averageDown));
                continue;
            }
            SynchronizedNonOverlapList<CandleResponse> copy = this.candleResponses.copyRecent(i, i + 14);
            double upSum = 0;
            double downSum = 0;
            for (CandleResponse candleResponse : copy) {
                final double change = candleResponse.getChangePrice();
                if (change >= 0) {
                    upSum += change;
                    continue;
                }
                downSum += (change * -1);
            }
            final double averageUp = upSum / 14;
            final double averageDown = downSum / 14;
            setRsiCandle.setAverageUp(averageUp);
            setRsiCandle.setAverageDown(averageDown);
            setRsiCandle.setRsi(calRsi(averageUp, averageDown));
        }
    }

    public Set<String> getMarkets() {
        return candleResponses.stream().map(CandleResponse::getMarket).collect(Collectors.toSet());
    }

    public CandleResponse get(int index) {
        return this.candleResponses.get(index);
    }

    public CandleResponse getRecent(int index) {
        return this.candleResponses.getRecent(index);
    }

    public CandleResponses copy(int startIndex, int endIndex) {
        return new CandleResponses(new SynchronizedNonOverlapList<>(this.candleResponses.copy(startIndex, endIndex)));
    }

    public CandleResponses copyRecent(int startIndex, int endIndex) {
        return new CandleResponses(new SynchronizedNonOverlapList<>(this.candleResponses.copyRecent(startIndex, endIndex)));
    }

}
