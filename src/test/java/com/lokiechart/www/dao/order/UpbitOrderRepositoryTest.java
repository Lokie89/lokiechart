package com.lokiechart.www.dao.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @ince 2021/04/15
 */
@DisplayName("업비트 주문 API 테스트")
@SpringBootTest
class UpbitOrderRepositoryTest {

    @Autowired
    OrderRepository upbitOrderRepository;

    @DisplayName("주문 테스트")
    @Test
    void orderTest() {
//        String result = upbitOrderRepository.order("tjdfhrdk10@naver.com", "KRW-AHT", "bid", 208.0, 24.5, "limit", null);
//        System.out.println(result);
    }

}
