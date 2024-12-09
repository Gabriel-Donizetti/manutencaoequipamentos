package com.web2.manutencaoequipamentos.solicitacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.web2.manutencaoequipamentos.security.Token;

import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping()
    @Transactional
    public ResponseEntity<Message> criarSolicitacao(@RequestBody CreateSolicitacaoDTO create, JwtAuthenticationToken token) {
        try {

            solicitacaoService.createSolicitacao(create, Token.getidAccount(token));

            return ResponseEntity.ok(new Message("Solicitação criada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Erro: " + e.getMessage()));
        }
    }

    @GetMapping("/solicitacoesUsuario")
    public MessageWithArray getSolicitacoesByUsuario(JwtAuthenticationToken token) {

        List<Solicitacao> solicitacoes = solicitacaoService.listarTodosPorCliente(Token.getidAccount(token));
        return new MessageWithArray("Solicitações recuperadas com sucesso.", solicitacoes);
    }

    @GetMapping("/solicitacoesFuncionario")
    public MessageWithArray getSolicitacoesByFuncionario(JwtAuthenticationToken token) {

        List<Solicitacao> solicitacoes = solicitacaoService.listarTodosPorFuncionario(Token.getidAccount(token));
        return new MessageWithArray("Solicitações recuperadas com sucesso.", solicitacoes);
    }

    @PutMapping()
    public Message updateSolicitacao(@RequestParam("id") Long id, @RequestBody CreateSolicitacaoDTO update, JwtAuthenticationToken token) {
       
        solicitacaoService.updateSolicitacao(update, id);
        return new Message("Solicitação atualizada com sucesso.");
    }

    public record MessageWithArray(String message, List<Solicitacao> list) {
    }

    public record Message(String message) {
    }
}