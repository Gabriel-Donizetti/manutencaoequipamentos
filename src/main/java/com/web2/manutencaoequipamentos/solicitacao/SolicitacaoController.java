package com.web2.manutencaoequipamentos.solicitacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.manutencaoequipamentos.security.Token;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/v1/solicitacao")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping()
    @Transactional
    public ResponseEntity<String> criarSolicitacao(@RequestBody CreateSolicitacaoDTO create, JwtAuthenticationToken token) {
        try {

            solicitacaoService.createSolicitacao(create, Token.getidAccount(token));

            return ResponseEntity.ok("Solicitação criada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/solicitacoesUsuario")
    public void getSolicituacoesByUsuario(JwtAuthenticationToken token) {

    }

    @GetMapping("/solicitacoesFuncionario")
    public void getSolicituacoesByFuncionario(JwtAuthenticationToken token) {

    }

    @PutMapping()
    public void updateSolicitacao(@RequestBody CreateSolicitacaoDTO update, JwtAuthenticationToken token) {

    }

    public record MessageWithArray(String message, List<Solicitacao> list) {
    }

}
