package com.pedroscheurer.investress.api.scenario;

import com.pedroscheurer.investress.api.dtos.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.dtos.BrapiQuoteResult;
import com.pedroscheurer.investress.api.dtos.BrapiQuoteResultHistDataPrice;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.StressTestType;
import com.pedroscheurer.investress.api.services.MarketDataService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HistoricalScenarioStrategy implements StressScenarioStrategy {

    private final MarketDataService marketDataService;

    public HistoricalScenarioStrategy(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Override
    public SimulationResult simulate(StressTestContext context) throws InterruptedException, IOException {
        BrapiQuoteResponse quotes = marketDataService.getQuotes(context.tickers(), context.window());

        Map<Integer, BigDecimal> portfolioByIndex = new HashMap<>();
        for (BrapiQuoteResult q : quotes.results()) {
            List<BrapiQuoteResultHistDataPrice> history = q.historicalDataPrice();
            for (int i = 0; i < history.size(); i++) {
                BigDecimal price = history.get(i).close();

                portfolioByIndex.merge(i, price, BigDecimal::add);
            }
        }
        List<BigDecimal> totalPrices = portfolioByIndex.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();

        return new SimulationResult(totalPrices, calculateReturns(totalPrices));
    }

    @Override
    public StressTestType getType() {
        return StressTestType.HISTORICAL;
    }

    private List<BigDecimal> calculateReturns(List<BigDecimal> values) {

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
