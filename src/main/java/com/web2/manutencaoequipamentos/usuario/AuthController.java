package com.web2.manutencaoequipamentos.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web2.manutencaoequipamentos.security.AuthDTO;
import com.web2.manutencaoequipamentos.security.AuthTokenJWT;
import com.web2.manutencaoequipamentos.security.Token;
import com.web2.manutencaoequipamentos.usuario.Dto.CreateUsuarioDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth/v1")
public class AuthController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private JwtEncoder jwtEncoder;

   @PostMapping("/login")
public ResponseEntity<MessageWithToken> login(@RequestBody @Valid AuthDTO account) {
    try {
        final Usuario usuarioAutenticado = service.makeLogin(account);

        return ResponseEntity.ok(
            new MessageWithToken(
                "Login bem-sucedido",
                new AuthTokenJWT(Token.generateTokenJWT(jwtEncoder, usuarioAutenticado),
                        Token.generateTokenExpirationTime())
            )
        );
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageWithToken(e.getMessage(), null));
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageWithToken("Usuário não encontrado", null));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageWithToken("Erro interno no servidor", null));
    }
}


    @PostMapping("/signin")
    @Transactional
    public ResponseEntity<MessageWithToken> create(@RequestBody @Valid CreateUsuarioDTO usuario) {

        try {
            final Usuario usuarioCriado = service.create(usuario);

            return ResponseEntity
                    .ok(new MessageWithToken("Usuario criado, será enviado no seu e-mail a senha para login",
                            new AuthTokenJWT(Token.generateTokenJWT(jwtEncoder, usuarioCriado),
                                    Token.generateTokenExpirationTime())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageWithToken(e.getMessage(), null));
        }

    }

    public record MessageWithToken(String message, AuthTokenJWT token) {
    }

}
