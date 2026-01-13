package com.pedroscheurer.investress.api.dtos.response;

import java.util.List;

public record BrapiQuoteResponse(
        List<BrapiQuoteResult> results,
        String requestedAt,
        int took
) {
}
