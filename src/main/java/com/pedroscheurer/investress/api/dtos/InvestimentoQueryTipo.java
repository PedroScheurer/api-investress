package com.pedroscheurer.investress.api.dtos;

import com.pedroscheurer.investress.api.entities.TypeInvestimento;

import java.math.BigDecimal;
import java.math.BigInteger;

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
