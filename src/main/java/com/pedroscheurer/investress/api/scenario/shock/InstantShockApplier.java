package com.pedroscheurer.investress.api.scenario.shock;

import com.pedroscheurer.investress.api.model.ShockScenario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class InstantShockApplier implements ShockApplier {
    @Override
    public List<BigDecimal> apply(
            List<BigDecimal> prices,
            ShockScenario shock
    ) {

        BigDecimal factor =
                BigDecimal.ONE.subtract(shock.magnitude());

        List<BigDecimal> shocked = new ArrayList<>(prices);

        int last = shocked.size() - 1;

        shocked.set(
                last,
                shocked.get(last).multiply(factor)
        );

        return shocked;
    }
}
