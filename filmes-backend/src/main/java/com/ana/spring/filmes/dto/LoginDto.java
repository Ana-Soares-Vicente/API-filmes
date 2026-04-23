package com.ana.spring.filmes.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {}