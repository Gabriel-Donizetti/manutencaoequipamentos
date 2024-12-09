package com.web2.manutencaoequipamentos.solicitacao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {

}
