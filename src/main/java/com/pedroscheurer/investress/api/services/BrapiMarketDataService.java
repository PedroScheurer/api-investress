package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.dtos.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.dtos.BrapiQuoteResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "quotes")
public class BrapiMarketDataService implements MarketDataService {

    @Value("${BRAPI_TOKEN}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://brapi.dev/api";

    @Override
    @Cacheable(
            value = "quotes",
            key = "#tickers",
            unless = "#result == null || #result.isEmpty()"
    )
    public BrapiQuoteResponse getQuotes(String[] tickers, String window) throws InterruptedException {

        List<BrapiQuoteResult> results = new ArrayList<>();

        for (String ticker : tickers) {

            String url = String.format(
                    "%s/quote/%s?range=%s&interval=1mo&token=%s",
                    BASE_URL,
                    ticker,
                    window,
                    token
            );

            BrapiQuoteResponse response =
                    restTemplate.getForObject(url, BrapiQuoteResponse.class);

            if (response != null && response.results() != null && !response.results().isEmpty()) {
                results.add(response.results().getFirst());
            }

            Thread.sleep(1500);
        }



        return new BrapiQuoteResponse(
                results,
                Instant.now().toString(),
                results.size()
        );
    }
}
