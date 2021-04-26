package com.lokiechart.www.dao.order.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public abstract class AbstractOrderParameter implements OrderParameter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> toParameter() {
        return objectMapper.convertValue(this, new TypeReference<>() {});
    }

    public abstract String getMarket();
}
