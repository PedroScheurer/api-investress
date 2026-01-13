package com.pedroscheurer.investress.api.dtos.response;

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
