package com.pedroscheurer.investress.api.dtos;

import java.util.List;

public record BrapiQuoteResponse(
        List<BrapiQuoteResult> results,
        String requestedAt,
        int took
) {
}
