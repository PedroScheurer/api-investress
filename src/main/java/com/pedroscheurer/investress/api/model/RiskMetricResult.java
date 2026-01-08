package com.pedroscheurer.investress.api.model;

import com.pedroscheurer.investress.api.model.enums.RiskMetricType;

import java.math.BigDecimal;
import java.util.Map;

public record RiskMetricResult(RiskMetricType type, BigDecimal values, Map<String, Object> details) {

}
