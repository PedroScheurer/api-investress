package com.pedroscheurer.investress.api.metric;

import com.pedroscheurer.investress.api.model.RiskMetricResult;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.enums.RiskMetricType;

public interface RiskMetricStrategy {
    RiskMetricType getType();

    RiskMetricResult calculate(SimulationResult result);
}
