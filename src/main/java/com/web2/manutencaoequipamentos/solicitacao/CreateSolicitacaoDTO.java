package com.web2.manutencaoequipamentos.solicitacao;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSolicitacaoDTO {
    private Long id;
    private UUID clienteId;  
    private UUID funcionarioId;  
    private String equipamento;
    private String categoria;
    private String defeito;
    private String estado;
    private Double valorOrcamento;
    private LocalDate dataCriada;
    private LocalDate dataOrcamento;
    private LocalDate dataAprovadoRejeitado;
    private LocalDate dataPagamento;
    private LocalDate dataEfetuada;
    private LocalDate dataFinalizada;
}
