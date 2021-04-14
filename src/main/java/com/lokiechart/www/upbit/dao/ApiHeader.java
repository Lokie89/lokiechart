package com.lokiechart.www.upbit.dao;

import org.springframework.http.HttpHeaders;

/**
 * @author SeongRok.Oh
 * @since 2021/04/15
 */
public interface ApiHeader {
    HttpHeaders getHeaders(String account);
}
