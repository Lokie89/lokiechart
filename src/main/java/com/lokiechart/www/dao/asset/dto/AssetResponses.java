package com.lokiechart.www.dao.asset.dto;

import com.lokiechart.www.dao.candle.dto.CandleResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Getter
public class AssetResponses implements Iterable<AssetResponse> {
    private final List<AssetResponse> assetResponses;

    public boolean isAlreadyOwnAndNotCheapEnough(CandleResponse candleResponse, final double scaleTradingPercent) {
        return assetResponses.stream()
                .anyMatch(assetResponse ->
                        assetResponse.isSameMarket(candleResponse.getMarket())
                                && assetResponse.isPossibleOrder()
                                && assetResponse.avgBuyPricePercent(candleResponse.getTradePrice()) > scaleTradingPercent * -1)
                ;
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
        return (int) assetResponses.stream().filter(AssetResponse::isExistBalance).count() - 1;
    }

    @Override
    public String toString() {
        return "코인\t|\t매수가\t|\t매수량\n"
                + assetResponses.toString().replaceAll(",", "").replace("[", "").replace("]", "")
                + "\t총 \t\t\t\t| \t" + getTotalSeed() + " 원";
    }
}
