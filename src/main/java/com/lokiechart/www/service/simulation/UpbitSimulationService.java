package com.lokiechart.www.service.simulation;

import com.lokiechart.www.service.simulation.dto.SimulationModel;
import com.lokiechart.www.service.simulation.dto.SimulationResult;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/05/19
 */
@Service
public class UpbitSimulationService implements SimulationService {

    @Override
    public SimulationResult simulate(SimulationModel model) {
        return model.experiment();
    }
}
