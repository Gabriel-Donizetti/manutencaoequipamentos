package com.web2.manutencaoequipamentos.solicitacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web2.manutencaoequipamentos.cliente.ClientRepository;
import com.web2.manutencaoequipamentos.cliente.Cliente;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository repository;

    @Autowired
    private ClientRepository clienteRepository;

    public SolicitacaoRepository getRepository() {
        return repository;
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
