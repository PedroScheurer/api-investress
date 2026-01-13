package com.pedroscheurer.investress.api.dtos.response;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;

import java.math.BigDecimal;

public record InvestimentoQueryTipo(
        TypeInvestimento type,
        long quantidade,
        BigDecimal valorTotalAtual,
        BigDecimal valorTotalInvestido
) {
    public BigDecimal getRetorno() {
        if (valorTotalInvestido == null || valorTotalInvestido.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return valorTotalAtual.subtract(valorTotalInvestido);
    }
}
