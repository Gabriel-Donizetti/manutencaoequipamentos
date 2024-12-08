package com.web2.manutencaoequipamentos.solicitacao;

import jakarta.persistence.*;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "solicitacao")
@Entity(name = "solicitacao")
@Getter
@Setter
@NoArgsConstructor
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private String equipamento;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String defeito;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private Double valorOrcamento;

    @Column(nullable = false)
    private String funcionarioOrcamento;

    @Column(nullable = false)
    private String dataOrcamento;

    @Temporal(TemporalType.TIMESTAMP) 
    private Date datahora;

}
