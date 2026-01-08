package com.pedroscheurer.investress.api.dtos;

import java.math.BigDecimal;
import java.util.List;

public record BrapiQuoteResult(
        String symbol,
        String shortName,
        String longName,
        String currency,

        BigDecimal regularMarketPrice,
        BigDecimal regularMarketChange,
        BigDecimal regularMarketChangePercent,

        BigDecimal regularMarketDayHigh,
        BigDecimal regularMarketDayLow,
        BigDecimal regularMarketOpen,
        BigDecimal regularMarketPreviousClose,

        Long regularMarketVolume,
        BigDecimal marketCap,

        String fiftyTwoWeekRange,
        BigDecimal fiftyTwoWeekLow,
        BigDecimal fiftyTwoWeekHigh,

        List<BrapiQuoteResultHistDataPrice> historicalDataPrice,

        BigDecimal priceEarnings,
        BigDecimal earningsPerShare
) {
}
