package com.web2.manutencaoequipamentos.solicitacao;

import jakarta.persistence.*;

import com.web2.manutencaoequipamentos.cliente.Cliente;
import com.web2.manutencaoequipamentos.funcionario.Funcionario;

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

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Funcionario funcionario;

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
    private String criada;

    @Column(nullable = false)
    private String dataOrcamento;

    @Column(nullable = false)
    private String dataAprovadoRejeitado;

    @Column(nullable = false)
    private String dataPagamento;

    @Column(nullable = false)
    private String dataEfetuada;

    @Column(nullable = false)
    private String dataFinalizada ;

}
