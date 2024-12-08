package com.web2.manutencaoequipamentos.solicitacao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/v1/solicitacao")
public class SolicitacaoController {

    @PostMapping()
    @Transactional
    public void criarSolicitacao() {

    }

    @GetMapping("/solicitacoesUsuario")
    public void getSolicituacoesByUsuario() {

    }

    @GetMapping("/solicitacoesFuncionario")
    public void getSolicituacoesByFuncionario() {

    }

    @PutMapping("/{id}")
    public void updateSolicitacao() {

    }

}
