package com.web2.manutencaoequipamentos.categoria_equipamento;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "categoria_equipamento")
@Entity(name = "categoria_equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEquipamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUsuario;

    @NotBlank
    private String nome;
}
