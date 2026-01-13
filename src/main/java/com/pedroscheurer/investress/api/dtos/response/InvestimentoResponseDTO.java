package com.pedroscheurer.investress.api.dtos.response;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvestimentoResponseDTO(Long id,
                                      String nome,
                                      BigDecimal retornoInvestimento,
                                      TypeInvestimento type,
                                      BigDecimal valorAtual,
                                      BigDecimal valorInvestido,
                                      LocalDateTime dataInvestimento,
                                      UserResponseDTO user) {
}
