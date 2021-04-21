package com.lokiechart.www.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Component
public class ConvertType {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T stringToType(String convert, Class<T> clazz) {
        T result;
        try {
            result = objectMapper.readValue(convert.getBytes(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CannotReadValueString();
        }
        return result;
    }
}
