package com.pedroscheurer.investress.api.model;

import java.math.BigDecimal;

public record StressTestContext(
        String ticker,
        Integer window,
        BigDecimal confidence
) {}