package com.lokiechart.www.service.simulation;

import com.lokiechart.www.service.simulation.dto.SimulationModel;
import com.lokiechart.www.service.simulation.dto.SimulationResult;

/**
 * @author SeongRok.Oh
 * @since 2021/05/19
 */
public interface SimulationService {
    SimulationResult simulate(SimulationModel model);
}
