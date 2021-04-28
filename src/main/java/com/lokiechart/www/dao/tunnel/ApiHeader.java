package com.lokiechart.www.dao.tunnel;

import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface ApiHeader {
    HttpHeaders getHeaders(String email, Map<String, Object> params);
}
