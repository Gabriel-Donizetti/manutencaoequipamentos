package com.web2.manutencaoequipamentos.solicitacao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web2.manutencaoequipamentos.cliente.ClientRepository;
import com.web2.manutencaoequipamentos.cliente.Cliente;
import com.web2.manutencaoequipamentos.funcionario.Funcionario;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository repository;

    @Autowired
    private ClientRepository clienteRepository;

    public SolicitacaoRepository getRepository() {
        return repository;
    }

    public List<Solicitacao> listarTodos() {
        List<Solicitacao> solicitacoes = repository.findAll();

        if (solicitacoes.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma Solicitação encontrada.");
        }

        return solicitacoes;
    }

    public Solicitacao createSolicitacao(CreateSolicitacaoDTO create, UUID id) {
        Solicitacao solicitacao = new Solicitacao();

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Funcionario funcionario = repository.findFuncionarioComMenosSolicitacoes();

        if (funcionario == null) {
            throw new EntityNotFoundException("Funcionário não encontrado.");
        }

        solicitacao.setCategoria(create.getCategoria());
        solicitacao.setDefeito(create.getDefeito());
        solicitacao.setEquipamento(create.getEquipamento());
        solicitacao.setCliente(cliente);
        solicitacao.setFuncionario(funcionario);

        return repository.save(solicitacao);
    }

    public List<Solicitacao> listarTodosPorCliente(UUID clienteId) {
        List<Solicitacao> solicitacoes = repository.findByClienteId(clienteId);

        if (solicitacoes.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma solicitação encontrada para o cliente informado.");
        }

        return solicitacoes;
    }

    public List<Solicitacao> listarTodosPorFuncionario(UUID funcionarioId) {
        List<Solicitacao> solicitacoes = repository.findByFuncionarioId(funcionarioId);

        if (solicitacoes.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma solicitação encontrada para o funcionário informado.");
        }

        return solicitacoes;
    }

    public Solicitacao updateSolicitacao(CreateSolicitacaoDTO update, Long solicitacaoId) {
        Solicitacao solicitacao = repository.findById(solicitacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação não encontrada."));

        solicitacao.setCategoria(update.getCategoria());
        solicitacao.setDefeito(update.getDefeito());
        solicitacao.setEquipamento(update.getEquipamento());
        solicitacao.setEstado(update.getEstado());
        solicitacao.setValorOrcamento(update.getValorOrcamento());
        solicitacao.setDataOrcamento(update.getDataOrcamento());
        solicitacao.setDataAprovadoRejeitado(update.getDataAprovadoRejeitado());
        solicitacao.setDataPagamento(update.getDataPagamento());
        solicitacao.setDataEfetuada(update.getDataEfetuada());
        solicitacao.setDataFinalizada(update.getDataFinalizada());

        return repository.save(solicitacao);
    }
}