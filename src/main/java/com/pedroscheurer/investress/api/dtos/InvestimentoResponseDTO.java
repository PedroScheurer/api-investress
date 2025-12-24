package com.pedroscheurer.investress.api.dtos;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;

import java.math.BigDecimal;

public record InvestimentoResponseDTO(Long id,
                                      String nome,
                                      BigDecimal retornoInvestimento,
                                      TypeInvestimento type,
                                      BigDecimal valorAtual,
                                      BigDecimal valorInvestido,
                                      UserResponseDTO user) {
}
