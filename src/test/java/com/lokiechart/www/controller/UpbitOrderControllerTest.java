package com.lokiechart.www.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author SeongRok.Oh
 * @since 2021/05/04
 */
@DisplayName("업비트 강제 주문 컨트롤러 테스트")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitOrderControllerTest {
    @Autowired
    MockMvc mockMvc;

//    @DisplayName("전체 매도 테스트")
//    @Test
//    void sellAllByAccountTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/order/meomchwo?email=tt")).andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @DisplayName("이익 매도 테스트")
//    @Test
//    void sellAllByAccountTest2() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/order/meomchwo?email=tt&profit=3")).andExpect(MockMvcResultMatchers.status().isOk());
//    }

}
