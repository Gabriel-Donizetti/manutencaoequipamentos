package com.web2.manutencaoequipamentos.solicitacao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web2.manutencaoequipamentos.usuario.Usuario;

public interface SolicitacaoRepository extends JpaRepository<Usuario, UUID> {

}
