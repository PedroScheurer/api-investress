package com.pedroscheurer.investress.api.model;

import java.math.BigDecimal;
import java.util.List;

public record StressTestContext(
        String[] tickers,
        String window,
        BigDecimal confidence
) {}