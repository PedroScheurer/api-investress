package com.pedroscheurer.investress.api.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@Email String email, @NotNull String nome, @NotNull String password) {
}
