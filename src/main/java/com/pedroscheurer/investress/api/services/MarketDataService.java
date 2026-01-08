package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.dtos.BrapiQuoteResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface MarketDataService {

    BrapiQuoteResponse getQuotes(
            String[] tickers,
            String window
    ) throws IOException, InterruptedException;
}