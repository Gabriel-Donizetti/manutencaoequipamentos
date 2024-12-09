package com.web2.manutencaoequipamentos.solicitacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/v1/solicitacao")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping("/post")
    @Transactional
    public ResponseEntity<String> criarSolicitacao(@RequestBody CreateSolicitacaoDTO create) {
        try {
            solicitacaoService.createSolicitacao(create);

            return ResponseEntity.ok("Solicitação criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: " + e.getMessage());
        }
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
