package com.pedroscheurer.investress.api.services;

import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResponse;
import com.pedroscheurer.investress.api.dtos.response.BrapiQuoteResult;
import com.pedroscheurer.investress.api.entities.InvestimentoEntity;
import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import com.pedroscheurer.investress.api.exceptions.BrapiResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "quotes")
@Primary
public class BrapiMarketDataService implements MarketDataService {

    private final InvestimentoService investimentoService;

    @Value("${BRAPI_TOKEN}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://brapi.dev/api";

    public BrapiMarketDataService(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    @Override
    @Cacheable(
            value = "quotes",
            key = "#tickers",
            unless = "#result == null || #result.isEmpty()"
    )
    public BrapiQuoteResponse getQuotes(TypeInvestimento typeInvestimento) throws InterruptedException {

        List<InvestimentoEntity> listaInvestimentoPorTipo = investimentoService.listarPorTipo(typeInvestimento);

        if (listaInvestimentoPorTipo.isEmpty()) {
            throw new RuntimeException("Nenhuma acao encontrada");
        }
        System.out.println(listaInvestimentoPorTipo);
        List<BrapiQuoteResult> results = new ArrayList<>();

        for (InvestimentoEntity investimento : listaInvestimentoPorTipo) {

            String ticket = investimento.getNome();
            LocalDate dataInv = investimento.getDataInvestimento().toLocalDate();
            String range = calcularRange(dataInv);


            //TODO mudar param de interval de acordo com o range
            String url = String.format(
                    "%s/quote/%s?range=%s&interval=1mo&token=%s",
                    BASE_URL,
                    ticket,
                    range,
                    token
            );

            try {
                BrapiQuoteResponse response =
                        restTemplate.getForObject(url, BrapiQuoteResponse.class);


                if (response == null ||
                        response.results() == null ||
                        response.results().isEmpty()) {

                    throw new BrapiResponseException(
                            "Resposta vazia da Brapi para o ticker " + ticket
                    );
                }

                BrapiQuoteResult result = response.results().getFirst();

                if (result.historicalDataPrice() == null ||
                        result.historicalDataPrice().size() < 2) {

                    throw new BrapiResponseException(
                            "Dados insuficientes para o ticker " + ticket
                    );
                }

                results.add(result);

            } catch (HttpClientErrorException e) {
                throw new BrapiResponseException(e.getMessage());
            }

            Thread.sleep(1500);
        }


        return new BrapiQuoteResponse(
                results,
                Instant.now().toString(),
                results.size()
        );
    }

    public String calcularRange(LocalDate dataInvestimento) {
        long dias = ChronoUnit.DAYS.between(dataInvestimento, LocalDate.now());

        if (dias <= 1) return "1d";
        if (dias <= 2) return "2d";
        if (dias <= 5) return "5d";
        if (dias <= 7) return "7d";
        if (dias <= 30) return "1mo";
        if (dias <= 90) return "3mo";
        if (dias <= 180) return "6mo";
        if (dias <= 365) return "1y";
        if (dias <= 730) return "2y";
        if (dias <= 1825) return "5y";
        if (dias <= 3650) return "10y";

        return "max";
    }
}
