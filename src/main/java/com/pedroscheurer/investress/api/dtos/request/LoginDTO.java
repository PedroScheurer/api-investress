package com.pedroscheurer.investress.api.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(@Email String email, @NotNull String password) {
}
