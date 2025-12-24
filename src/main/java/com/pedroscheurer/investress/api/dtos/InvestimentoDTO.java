package com.pedroscheurer.investress.api.dtos;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;

import java.math.BigDecimal;

public record InvestimentoDTO(String nome, BigDecimal valorInvestido, BigDecimal valorAtual,
                              TypeInvestimento type) {
}
