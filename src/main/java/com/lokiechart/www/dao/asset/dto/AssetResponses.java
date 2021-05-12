package com.lokiechart.www.dao.asset.dto;

import com.lokiechart.www.dao.ticker.dto.TickerResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Getter
public class AssetResponses implements Iterable<AssetResponse> {
    private final List<AssetResponse> assetResponses;

    public Stream<AssetResponse> stream() {
        return assetResponses.stream();
    }

    @Override
    public Iterator<AssetResponse> iterator() {
        return assetResponses.iterator();
    }

    public Double getBalanceByMarket(String market) {
        AssetResponse asset = assetResponses.stream().filter(assetResponse -> market.equals(assetResponse.getMarketCurrency())).findAny().get();
        return asset.getBalance();
    }

    public Integer getTotalSeed() {
        return assetResponses.stream().mapToInt(AssetResponse::getTotalCost).sum();
    }

    public int existAssetSize() {
        return (int) assetResponses.stream().filter(assetResponse -> !assetResponse.isBaseCurrency() && assetResponse.isExistTotalBalance()).count();
    }

    public boolean containMarket(String market) {
        return assetResponses.stream().anyMatch(assetResponse -> assetResponse.getMarketCurrency().equals(market));
    }

    public List<String> getMarkets() {
        return assetResponses.stream().filter(assetResponse -> !assetResponse.isBaseCurrency() && assetResponse.isExistTotalBalance()).map(AssetResponse::getMarketCurrency).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "코인\t|\t매수가\t|\t매수량\n"
                + assetResponses.toString().replaceAll(",", "").replace("[", "").replace("]", "")
                + "\t총 " + getMarkets().size() + "\t\t\t| \t" + getTotalSeed() + " 원";
    }

    public AssetResponses sortProfitLiveTradePrice(TickerResponses tickerResponses, final double profitPercent) {
        return new AssetResponses(assetResponses.stream()
                .filter(assetResponse -> !assetResponse.isBaseCurrency() && assetResponse.isExistTotalBalance())
                .filter(assetResponse -> assetResponse.avgBuyPricePercent(tickerResponses.getTradePriceByMarket(assetResponse)) > profitPercent)
                .sorted((o1, o2) -> {
                    Double o1Profit = o1.avgBuyPricePercent(tickerResponses.getTradePriceByMarket(o1));
                    Double o2Profit = o2.avgBuyPricePercent(tickerResponses.getTradePriceByMarket(o2));
                    if (o1Profit < o2Profit) {
                        return 1;
                    } else if (o1Profit > o2Profit) {
                        return -1;
                    }
                    return 0;
                }).collect(Collectors.toList()));
    }

    public Double getBaseCurrency() {
        return assetResponses.stream().filter(AssetResponse::isBaseCurrency).findFirst().map(AssetResponse::getBalance).orElse(null);
    }
}
