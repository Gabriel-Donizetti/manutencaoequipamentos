package com.web2.manutencaoequipamentos.solicitacao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    @Query(value = "SELECT f.* FROM funcionario f " +
            "LEFT JOIN (SELECT funcionario_idfuncionario, COUNT(*) AS quantidade_solicitacoes " +
            "           FROM solicitacao GROUP BY funcionario_idfuncionario) s " +
            "ON f.idfuncionario = s.funcionario_idfuncionario " +
            "ORDER BY COALESCE(s.quantidade_solicitacoes, 0) ASC LIMIT 1", nativeQuery = true)
    Object[] findFuncionarioComMenosSolicitacoes();

    List<Solicitacao> findByCliente_IdCliente(UUID clienteId);

    List<Solicitacao> findByFuncionario_IdFuncionario(UUID funcionarioId);

}
