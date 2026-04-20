package com.ana.spring.filmes.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoDto(

        @NotNull
        Integer usuarioId,

        @NotNull
        Integer filmeId,

        @Min(1)
        @Max(5)
        Integer nota
) {}