package com.pedroscheurer.investress.api.utils;

import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResult;
import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResultHistDataPrice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    public static List<BigDecimal> buildPortfolioSeries(BrapiQuoteResponse quotes) {

        Map<Integer, BigDecimal> portfolioByIndex = new HashMap<>();

        for (BrapiQuoteResult q : quotes.results()) {
            List<BrapiQuoteResultHistDataPrice> history =
                    q.historicalDataPrice();

            for (int i = 0; i < history.size(); i++) {
                BigDecimal price = history.get(i).close();
                portfolioByIndex.merge(i, price, BigDecimal::add);
            }
        }

        return portfolioByIndex.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
    }

    public static List<BigDecimal> calculateReturns(List<BigDecimal> values) {
        List<BigDecimal> returns = new ArrayList<>();

        for (int i = 1; i < values.size(); i++) {
            BigDecimal previous = values.get(i - 1);
            BigDecimal current = values.get(i);

            BigDecimal r = current
                    .subtract(previous)
                    .divide(previous, MathContext.DECIMAL64);

            returns.add(r);
        }
        return returns;
    }
}
