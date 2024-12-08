package com.web2.manutencaoequipamentos.cliente;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Cliente, UUID> {
    
    Optional<Cliente> findByCpf(String cpf);

}