package com.pedroscheurer.investress.api.dtos.request;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record InvestimentoDTO(
        @NotNull(message = "Nome do investimento nao pode ser nulo")
        String nome,
        @Min(value = 0, message = "Valor investido deve ser maior que 0")
        BigDecimal valorInvestido,
        @Min(value = 0, message = "Valor atual deve ser maior que 0")
        BigDecimal valorAtual,
        @NotNull(message = "Tipo de investimento nao pode ser nulo")
        TypeInvestimento type) {
}
