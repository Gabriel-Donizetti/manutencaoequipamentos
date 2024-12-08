package com.web2.manutencaoequipamentos.usuario.Dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class CreateFuncionarioDTO {
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private LocalDate dataNascimento;
}
