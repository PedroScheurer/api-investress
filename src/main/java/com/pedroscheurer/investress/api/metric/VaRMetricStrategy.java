package com.pedroscheurer.investress.api.metric;

import com.pedroscheurer.investress.api.model.RiskMetricResult;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.enums.RiskMetricType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class VaRMetricStrategy implements RiskMetricStrategy {
    private static final BigDecimal CONFIDENCE = BigDecimal.valueOf(0.95);

    @Override
    public RiskMetricType getType() {
        return RiskMetricType.VAR;
    }

    @Override
    public RiskMetricResult calculate(SimulationResult result) {

        List<BigDecimal> returns = result.returns();

        if(returns == null || returns.isEmpty()){
            throw new IllegalArgumentException("Simulation has no result");
        }

        List<BigDecimal> sorted = returns.stream().sorted().toList();

        int index = (int) Math.floor(1 - CONFIDENCE.doubleValue()) * sorted.size();

        BigDecimal var = sorted.get(index);

        Map<String,Object> details = Map.of("confidence", CONFIDENCE);

        return new RiskMetricResult(RiskMetricType.VAR, var, details);
    }
}
