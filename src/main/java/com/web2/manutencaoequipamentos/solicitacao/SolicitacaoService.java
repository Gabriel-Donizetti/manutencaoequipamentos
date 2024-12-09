package com.web2.manutencaoequipamentos.solicitacao;

import java.util.List;

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
            throw new EntityNotFoundException("Nenhum funcionário encontrado.");
        }

        return solicitacoes;
    }

    public Solicitacao createSolicitacao(CreateSolicitacaoDTO create) {
        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setCategoria(create.getCategoria());
        solicitacao.setDefeito(create.getDefeito());
        solicitacao.setEquipamento(create.getEquipamento());

        Cliente cliente = clienteRepository.findById(create.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        solicitacao.setCliente(cliente);
        solicitacao.setDataCriada(create.getDataCriada());

        return solicitacao;
    }

}
