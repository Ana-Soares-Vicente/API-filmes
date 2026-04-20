package com.ana.spring.filmes.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto (
    @NotBlank
    String nome
) {}
