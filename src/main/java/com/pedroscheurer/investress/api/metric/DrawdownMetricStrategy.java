package com.pedroscheurer.investress.api.metric;

import com.pedroscheurer.investress.api.model.RiskMetricResult;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.enums.RiskMetricType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

@Component
public class DrawdownMetricStrategy implements RiskMetricStrategy {

    @Override
    public RiskMetricType getType() {
        return RiskMetricType.DRAWDOWN;
    }

    @Override
    public RiskMetricResult calculate(SimulationResult result) {

        BigDecimal peak = result.values().getFirst();
        BigDecimal maxDrawdown = BigDecimal.ZERO;

        for (BigDecimal value : result.values()) {
            if (value.compareTo(peak) > 0) {
                peak = value;
            }

            BigDecimal dd = value.subtract(peak).divide(peak, MathContext.DECIMAL64);

            if (dd.compareTo(maxDrawdown) < 0) {
                maxDrawdown = dd;
            }
        }

        return new RiskMetricResult(RiskMetricType.DRAWDOWN, maxDrawdown, null);
    }

}
