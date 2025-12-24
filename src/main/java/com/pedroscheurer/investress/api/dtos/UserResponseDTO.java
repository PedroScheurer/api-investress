package com.pedroscheurer.investress.api.dtos;

import com.pedroscheurer.investress.api.entities.TypeUser;

public record UserResponseDTO(
        Long id,
        String nome,
        String email,
        TypeUser type
) {}