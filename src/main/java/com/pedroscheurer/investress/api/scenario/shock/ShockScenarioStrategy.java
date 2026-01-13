package com.pedroscheurer.investress.api.scenario.shock;

import com.pedroscheurer.investress.api.dtos.request.StressTestContext;
import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.model.SimulationResult;
import com.pedroscheurer.investress.api.model.enums.StressTestType;
import com.pedroscheurer.investress.api.scenario.StressScenarioStrategy;
import com.pedroscheurer.investress.api.services.MarketDataService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.pedroscheurer.investress.api.utils.Calculator.buildPortfolioSeries;
import static com.pedroscheurer.investress.api.utils.Calculator.calculateReturns;

@Component
public class ShockScenarioStrategy implements StressScenarioStrategy {

    private final MarketDataService marketDataService;
    private final ShockApplier shockApplier;

    public ShockScenarioStrategy(MarketDataService marketDataService, ShockApplier shockApplier) {
        this.marketDataService = marketDataService;
        this.shockApplier = shockApplier;
    }

    @Override
    public StressTestType getType() {
        return StressTestType.SHOCK;
    }

    @Override
    public SimulationResult simulate(StressTestContext context)
            throws InterruptedException, IOException {

        BrapiQuoteResponse quotes =
                marketDataService.getQuotes(context.typeInvestimento());

        List<BigDecimal> basePrices = buildPortfolioSeries(quotes);

        List<BigDecimal> shockedPrices =
                shockApplier.apply(basePrices, context.shock());

        return new SimulationResult(
                shockedPrices,
                calculateReturns(shockedPrices)
        );
    }

}
