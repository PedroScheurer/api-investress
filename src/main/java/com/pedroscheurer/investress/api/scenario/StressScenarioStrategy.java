package com.pedroscheurer.investress.api.scenario;

import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.dtos.request.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.StressTestType;

import java.io.IOException;

public interface StressScenarioStrategy {

    public SimulationResult simulate(StressTestContext context) throws IOException, InterruptedException;

    StressTestType getType();
}
