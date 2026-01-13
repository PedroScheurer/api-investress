package com.pedroscheurer.investress.api.dtos.request;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StressTestContext(
        @NotNull(message = "Tipo do investimento nao pode ser nulo")
        TypeInvestimento typeInvestimento,
        BigDecimal confidence
) {}