package com.pedroscheurer.investress.api.scenario.historical;

import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResult;
import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResultHistDataPrice;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.dtos.request.StressTestContext;
import com.pedroscheurer.investress.api.model.enums.StressTestType;
import com.pedroscheurer.investress.api.scenario.StressScenarioStrategy;
import com.pedroscheurer.investress.api.services.MarketDataService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pedroscheurer.investress.api.utils.Calculator.calculateReturns;

@Component
public class HistoricalScenarioStrategy implements StressScenarioStrategy {

    private final MarketDataService marketDataService;

    public HistoricalScenarioStrategy(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Override
    public SimulationResult simulate(StressTestContext context) throws InterruptedException, IOException {
        BrapiQuoteResponse quotes = marketDataService.getQuotes(context.typeInvestimento());

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
}
