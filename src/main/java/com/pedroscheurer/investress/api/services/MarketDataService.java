package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MarketDataService {

    BrapiQuoteResponse getQuotes(
            TypeInvestimento typeInvestimento
    ) throws IOException, InterruptedException;
}