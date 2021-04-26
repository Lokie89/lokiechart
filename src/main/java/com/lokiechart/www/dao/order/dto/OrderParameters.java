package com.lokiechart.www.dao.order.dto;

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
        orderParameters = orderParameters.stream().filter(parameter -> !excludeMarkets.contains(parameter.getMarket())).collect(Collectors.toList());
    }

    public void filter(List<String> decidedMarkets) {
        orderParameters = orderParameters.stream().filter(parameter -> decidedMarkets.contains(parameter.getMarket())).collect(Collectors.toList());
    }

    @Override
    public Iterator<OrderParameter> iterator() {
        return orderParameters.iterator();
    }
}
