package com.web2.manutencaoequipamentos.categoria_equipamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaEquipamentoRepository extends JpaRepository<CategoriaEquipamento, UUID>{
    
}
