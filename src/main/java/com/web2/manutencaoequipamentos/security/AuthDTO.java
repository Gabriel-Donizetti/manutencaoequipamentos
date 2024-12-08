package com.web2.manutencaoequipamentos.security;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank String login, @NotBlank String senha) {//Um jeito rapido para Fazer DTO
}