package com.web2.manutencaoequipamentos.solicitacao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web2.manutencaoequipamentos.funcionario.Funcionario;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    @Query(value = "SELECT funcionario_id, COUNT(*) AS quantidade_solicitacoes " +
            "FROM solicitacao GROUP BY funcionario_id ORDER BY quantidade_solicitacoes ASC LIMIT 1", nativeQuery = true)
    Funcionario findFuncionarioComMenosSolicitacoes();

    List<Solicitacao> findByClienteId(UUID clienteId);

    List<Solicitacao> findByFuncionarioId(UUID funcionarioId);
    

}
