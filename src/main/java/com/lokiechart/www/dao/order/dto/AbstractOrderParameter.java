package com.lokiechart.www.dao.order.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public abstract class AbstractOrderParameter implements OrderParameter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> toParameter() {
        Map<String, Object> params = objectMapper.convertValue(this, new TypeReference<>() {});
        params = Maps.filterValues(params, Objects::nonNull);
        System.out.println(params);
        return params;
    }

    public abstract String getMarket();
}
