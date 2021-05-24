package com.lokiechart.www.dao.order.dto;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.service.order.dto.OrderDetails;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/25
 */
@ToString
@AllArgsConstructor
@Getter
public class OrderParameters implements Iterable<OrderParameter> {
    private final Logger logger = LoggerFactory.getLogger(OrderParameters.class);
    private List<OrderParameter> orderParameters;

    public void exclude(List<String> excludeMarkets) {
        this.orderParameters =
                orderParameters.stream()
                        .filter(parameter -> !excludeMarkets.contains(parameter.getMarket().replaceFirst("KRW-", "")))
                        .collect(Collectors.toList());
    }

    public void filterMarkets(List<String> decidedMarkets) {
        this.orderParameters =
                orderParameters.stream()
                        .filter(parameter -> decidedMarkets.contains(parameter.getMarket().replaceFirst("KRW-", "")))
                        .collect(Collectors.toList());
    }

    public void filterBuyOrdered(OrderDetails orderDetails) {
        this.orderParameters =
                orderParameters.stream()
                        .filter((match) -> orderDetails.getOrderDetails().stream()
                                .noneMatch(orderDetail -> orderDetail.isBuyingOrder() && orderDetail.isSameMarket(match.getMarket()))
                        )
                        .collect(Collectors.toList());
    }

    @Override
    public Iterator<OrderParameter> iterator() {
        return orderParameters.iterator();
    }

    public void dropAlreadyOwnAndAddCount(AssetResponses assetResponses, int addCount) {
        List<OrderParameter> filterOwn = orderParameters.stream().filter(parameter -> assetResponses.containMarket(parameter.getMarket())).collect(Collectors.toList());
        if (orderParameters.size() - filterOwn.size() < addCount) {
            addCount = orderParameters.size() - filterOwn.size();
        }
        List<OrderParameter> copyAddCountNotOwn = orderParameters.stream().filter(parameter -> !assetResponses.containMarket(parameter.getMarket())).collect(Collectors.toList()).subList(0, addCount);
        filterOwn.addAll(copyAddCountNotOwn);
        this.orderParameters = filterOwn;
    }

    public boolean isEmpty() {
        return orderParameters.isEmpty();
    }

    public OrderParameters filter(Predicate<OrderParameter> predicate) {
        return new OrderParameters(this.orderParameters.stream().filter(predicate).collect(Collectors.toList()));
    }

    public int size() {
        return orderParameters.size();
    }

    public void filterSellOrdered(OrderDetails orderDetails) {
        this.orderParameters = orderParameters.stream()
                .filter((match) -> orderDetails.getOrderDetails()
                        .stream()
                        .noneMatch(orderDetail -> !orderDetail.isBuyingOrder() && orderDetail.isSameMarket(match.getMarket()))
                )
                .collect(Collectors.toList());
    }

    public OrderParameters copy() {
        return new OrderParameters(new ArrayList<>(this.orderParameters.stream().map(OrderParameter::copy).collect(Collectors.toList())));
    }

    public void intersect(OrderParameters other) {
        if (orderParameters.isEmpty()) {
            orderParameters.addAll(other.orderParameters);
            return;
        }
        orderParameters = orderParameters.stream().filter(match -> other.orderParameters.contains(match)).collect(Collectors.toList());
    }

    // TODO : 물타는 전략 : 최근 매수 * 2
    public void filterByAccount(AccountStrategyResponse accountStrategyResponse, AssetResponses assetResponses) {
        AccountResponse accountResponse = accountStrategyResponse.getAccountResponse();
        if (!accountResponse.isBuyFlag()) {
            this.orderParameters = Collections.emptyList();
            return;
        }

        final List<String> excludeMarket = accountResponse.getExcludeMarket();
        if (Objects.nonNull(excludeMarket) && !excludeMarket.isEmpty()) {
            exclude(excludeMarket);
        }

        final int totalSeed = accountResponse.getTotalSeed();
        final int totalTradeCount = accountResponse.getTotalTradeCount();
        final int maxBuyMarket = accountResponse.getMaxBuyMarket();
        final int investSeed = totalSeed == 0 ? assetResponses.getTotalSeed() : totalSeed;
        final int onceInvestKRW = investSeed / totalTradeCount / maxBuyMarket;
        this.orderParameters.forEach(orderParameter -> orderParameter.setOrderParams(onceInvestKRW));

        final double scaleTradingPercent = accountResponse.getScaleTradingPercent();
        this.orderParameters = this.orderParameters.stream()
                .filter(orderParameter -> !orderParameter.isAlreadyOwnAndNotCheapEnough(assetResponses, scaleTradingPercent))
                .collect(Collectors.toList())
        ;

        final List<String> decidedMarket = accountResponse.getDecidedMarket();

        if (Objects.nonNull(decidedMarket) && !decidedMarket.isEmpty()) {
            filterMarkets(decidedMarket);
            return;
        }

        final int alreadyExistAndPlusSize = assetResponses.existAssetSize() + size();
        if (alreadyExistAndPlusSize > maxBuyMarket) {
            final int addCount = Integer.max(maxBuyMarket - assetResponses.existAssetSize(), 0);
            dropAlreadyOwnAndAddCount(assetResponses, addCount);
        }

        final double remainBaseCurrency = assetResponses.getBaseCurrency() == null ? 0 : assetResponses.getBaseCurrency();
        final int possiblePurchaseCount = (int) (remainBaseCurrency / onceInvestKRW);
        this.orderParameters = this.orderParameters.subList(0, Integer.min(size(), possiblePurchaseCount));
    }

    public void addAll(OrderParameters other) {

        this.orderParameters.addAll(other.orderParameters);
    }
}
