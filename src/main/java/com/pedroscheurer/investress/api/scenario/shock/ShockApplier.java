package com.pedroscheurer.investress.api.scenario.shock;

import com.pedroscheurer.investress.api.model.ShockScenario;

import java.math.BigDecimal;
import java.util.List;

public interface ShockApplier {
    List<BigDecimal> apply(
            List<BigDecimal> prices,
            ShockScenario shock
    );
}
