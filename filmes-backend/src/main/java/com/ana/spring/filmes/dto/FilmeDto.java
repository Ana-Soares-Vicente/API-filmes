package com.ana.spring.filmes.dto;

import jakarta.validation.constraints.NotBlank;

public record FilmeDto (
        @NotBlank
        String titulo
) {}
