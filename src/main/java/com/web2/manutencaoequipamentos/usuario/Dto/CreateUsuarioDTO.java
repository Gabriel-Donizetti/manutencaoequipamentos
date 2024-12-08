package com.web2.manutencaoequipamentos.usuario.Dto;

import lombok.Getter;

@Getter
public class CreateUsuarioDTO {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String unidade;
}
