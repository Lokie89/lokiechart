package com.lokiechart.www.dao.order.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/04/16
 */
public class AbstractOrderParameter implements OrderParameter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map toParameter() {
        return objectMapper.convertValue(this, Map.class);
    }
}
