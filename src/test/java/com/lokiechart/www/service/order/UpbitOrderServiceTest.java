package com.lokiechart.www.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@DisplayName("업비트 주문 서비스 테스트")
@SpringBootTest
class UpbitOrderServiceTest {

    @Autowired
    OrderService upbitOrderService;

    @DisplayName("Email 의 전략에 맞게 주문 하기")
    @Test
    void orderByEmail() {
//        upbitOrderService.tradeByAccount("tjdfhrdk10@naver.com");
    }

}
