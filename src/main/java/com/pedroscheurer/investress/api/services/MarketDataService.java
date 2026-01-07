package com.pedroscheurer.investress.api.services;

import java.math.BigDecimal;
import java.util.List;

public interface MarketDataService {

    List<BigDecimal> getHistoricalPrices(
            String ticker,
            int window
    );
}