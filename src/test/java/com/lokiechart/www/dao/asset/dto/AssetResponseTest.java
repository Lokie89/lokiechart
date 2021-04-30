package com.lokiechart.www.dao.asset.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@DisplayName("자산 응답 객체 테스트")
@SpringBootTest
class AssetResponseTest {

    @Test
    void test(){
        AssetResponse assetResponse = UpbitAssetResponse.builder().currency("SC").avgBuyPrice(5510.0).balance(0.00000001).locked(0.0).build();
        Assertions.assertFalse(assetResponse.isPossibleOrder());
    }
}
