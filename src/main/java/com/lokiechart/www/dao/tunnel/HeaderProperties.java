package com.lokiechart.www.dao.tunnel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author SeongRok.Oh
 * @since 2021/04/29
 */
@Getter
@RequiredArgsConstructor
public class HeaderProperties {
    private final String accessKey;
    private final String secretKey;
}
