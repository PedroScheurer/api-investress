package com.pedroscheurer.investress.api.dtos;

import java.math.BigDecimal;

public record BrapiQuoteResultHistDataPrice(
        int date,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        int volume,
        BigDecimal adjustedClose
) {
}
