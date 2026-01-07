package com.pedroscheurer.investress.api.model;

import java.math.BigDecimal;
import java.util.List;

public record SimulationResult(
        List<BigDecimal> values,
        List<BigDecimal> returns
) {}