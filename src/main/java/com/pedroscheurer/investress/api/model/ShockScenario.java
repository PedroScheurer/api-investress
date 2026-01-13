package com.pedroscheurer.investress.api.model;

import com.pedroscheurer.investress.api.model.enums.ShockType;

import java.math.BigDecimal;

public record ShockScenario(ShockType type,
                            BigDecimal magnitude,
                            Integer periodos ) {
}
