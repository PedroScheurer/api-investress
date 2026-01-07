package com.pedroscheurer.investress.api.scenario;

import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.StressTestType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class HistoricalScenarioStrategy implements StressScenarioStrategy {

    @Override
    public SimulationResult simulate(StressTestContext context){
        List<BigDecimal> values = List.of(
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(9200),
                BigDecimal.valueOf(8800),
                BigDecimal.valueOf(13000)
        );
        return new SimulationResult(values, calculateReturns(values));
    }
    @Override
    public StressTestType getType(){
        return StressTestType.HISTORICAL;
    }

    private List<BigDecimal> calculateReturns(List<BigDecimal> values) {

        List<BigDecimal> returns = new ArrayList<>();

        for (int i = 1; i < values.size(); i++) {
            BigDecimal previous = values.get(i - 1);
            BigDecimal current = values.get(i);

            BigDecimal r = current
                    .subtract(previous)
                    .divide(previous, MathContext.DECIMAL64);

            returns.add(r);
        }

        return returns;
    }
}
