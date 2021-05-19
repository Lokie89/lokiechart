package com.lokiechart.www.service.simulation;

import com.lokiechart.www.service.simulation.dto.SimulationResult;
import com.lokiechart.www.service.simulation.dto.UpbitSimulationModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SeongRok.Oh
 * @since 2021/04/29
 */
@DisplayName("업비트 시뮬레이션 서비스 테스트")
@SpringBootTest
public class UpbitSimulationServiceTest {

    @Autowired
    SimulationService upbitSimulationService;

    @DisplayName("시뮬레이트 테스트")
    @Test
    void simulateTest() {
        SimulationResult result = upbitSimulationService.simulate(UpbitSimulationModel.builder().build());
        System.out.println(result);
    }
}
