package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.metric.RiskMetricStrategy;
import com.pedroscheurer.investress.api.model.RiskMetricResult;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.RiskMetricType;
import com.pedroscheurer.investress.api.model.enums.StressTestType;
import com.pedroscheurer.investress.api.scenario.StressScenarioStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StressTestService {

    private final Map<StressTestType, StressScenarioStrategy> scenarios;
    private final Map<RiskMetricType, RiskMetricStrategy> metrics;

    public StressTestService(List<StressScenarioStrategy> scenarioList, List<RiskMetricStrategy> metricList) {
        this.scenarios = scenarioList.stream()
                .collect(Collectors.toMap(
                        StressScenarioStrategy::getType,
                        Function.identity()
                ));
        this.metrics = metricList.stream()
                .collect(Collectors.toMap(
                        RiskMetricStrategy::getType,
                        Function.identity()
                ));
    }

    public List<RiskMetricResult> run(StressTestType scenarioType,
                                      List<RiskMetricType> metricTypes,
                                      StressTestContext context){

        StressScenarioStrategy scenarioStrategy = scenarios.get(scenarioType);

        if (scenarioStrategy == null) {
            throw new IllegalArgumentException(
                    "Scenario not supported: " + scenarioType
            );
        }

        SimulationResult simulationResult = scenarioStrategy.simulate(context);

        return metricTypes.stream()
                .map(type -> metrics.get(type).calculate(simulationResult))
                .toList();
    }
}
