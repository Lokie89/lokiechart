package com.lokiechart.www.dao.order.dto;

import com.lokiechart.www.dao.asset.dto.AssetResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/25
 */
@ToString
@AllArgsConstructor
@Getter
public class OrderParameters implements Iterable<OrderParameter> {
    private List<OrderParameter> orderParameters;

    public void exclude(List<String> excludeMarkets) {
        orderParameters = orderParameters.stream().filter(parameter -> !excludeMarkets.contains(parameter.getMarket().replaceFirst("KRW-", ""))).collect(Collectors.toList());
    }

    public void filter(List<String> decidedMarkets) {
        orderParameters = orderParameters.stream().filter(parameter -> decidedMarkets.contains(parameter.getMarket().replaceFirst("KRW-", ""))).collect(Collectors.toList());
    }

    public void addAll(OrderParameters orderParameters) {
        this.orderParameters.addAll(orderParameters.orderParameters);
    }

    @Override
    public Iterator<OrderParameter> iterator() {
        return orderParameters.iterator();
    }

    public void filterAlreadyOwnAndAddCount(AssetResponses assetResponses, int addCount) {
        List<OrderParameter> filterOwn = orderParameters.stream().filter(parameter -> assetResponses.containMarket(parameter.getMarket())).collect(Collectors.toList());
        if (orderParameters.size() - filterOwn.size() < addCount) {
            addCount = orderParameters.size() - filterOwn.size();
        }
        List<OrderParameter> copyAddCountNotOwn = orderParameters.stream().filter(parameter -> !assetResponses.containMarket(parameter.getMarket())).collect(Collectors.toList()).subList(0, addCount);
        filterOwn.addAll(copyAddCountNotOwn);
        orderParameters = filterOwn;
    }

    public boolean isEmpty() {
        return orderParameters.isEmpty();
    }

    public int size() {
        return orderParameters.size();
    }

}
