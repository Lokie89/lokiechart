package com.lokiechart.www.upbit.dao.asset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@DisplayName("계정 API 테스트")
@SpringBootTest
class AssetRepositoryTest {

    @Autowired
    AssetRepository repository;

    @DisplayName("자산 정보 가져오기")
    @Test
    void getAssetsTest() {
        String account = "tjdfhrdk10@naver.com";
        String assetStr = repository.getAssets(account);
        System.out.println(assetStr);
    }
}
