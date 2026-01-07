package com.pedroscheurer.investress.api.scenario;

import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.StressTestType;

public interface StressScenarioStrategy {

    public SimulationResult simulate(StressTestContext context);

    StressTestType getType();
}
