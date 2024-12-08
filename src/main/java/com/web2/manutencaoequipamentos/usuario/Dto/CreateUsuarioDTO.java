package com.web2.manutencaoequipamentos.usuario.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUsuarioDTO {
    private String nome;
    private String cpf;

    @NotBlank(message = "O email não deve estar em branco")
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
