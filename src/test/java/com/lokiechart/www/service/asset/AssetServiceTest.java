package com.lokiechart.www.service.asset;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/29
 */
@DisplayName("자산 서비스 테스트")
@SpringBootTest
public class AssetServiceTest {

    @Autowired
    AssetService assetService;

    @DisplayName("현 자산 가져오기 테스트")
    @Test
    void getTotalSeedTest(){
        System.out.println(assetService.getTotalSeed(AccountResponse.builder().email("tjdfhrdk10@naver.com").build()));
        System.out.println(assetService.getTotalSeed(AccountResponse.builder().email("01099589323@hanmail.net").build()));
    }

    @DisplayName("현 실시간 자산 가져오기 테스트")
    @Test
    void getLiveAsset(){
        System.out.println(assetService.getLiveAssets(AccountResponse.builder().email("tjdfhrdk10@naver.com").build()));
        System.out.println(assetService.getLiveAssets(AccountResponse.builder().email("01099589323@hanmail.net").build()));
    }
}
