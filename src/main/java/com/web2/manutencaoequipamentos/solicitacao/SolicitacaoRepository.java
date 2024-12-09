package com.web2.manutencaoequipamentos.solicitacao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web2.manutencaoequipamentos.funcionario.Funcionario;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    Funcionario findFirstByOrderByQuantidadeSolicitacoesAsc();

    List<Solicitacao> findByCliente_IdCliente(UUID clienteId);

    List<Solicitacao> findByFuncionario_IdFuncionario(UUID funcionarioId);

}
