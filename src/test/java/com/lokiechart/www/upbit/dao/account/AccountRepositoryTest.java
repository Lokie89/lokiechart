package com.lokiechart.www.upbit.dao.account;

import com.lokiechart.www.upbit.dao.account.dto.AssetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SeongRok.Oh
 * @since 2021/04/13
 */
@DisplayName("계정 API 테스트")
@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    void connectTest() {
        String account = "tjdfhrdk10@naver.com";
        List<AssetResponse> assetResponseList = repository.getAssets(account);
    }
}
