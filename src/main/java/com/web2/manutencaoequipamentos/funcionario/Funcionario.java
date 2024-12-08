package com.web2.manutencaoequipamentos.funcionario;

import java.time.LocalDate;
import java.util.UUID;

import com.web2.manutencaoequipamentos.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "funcionario")
@Entity(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idFuncionario;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Usuario usuario;

    @NotNull
    private LocalDate dataNascimento;
}
