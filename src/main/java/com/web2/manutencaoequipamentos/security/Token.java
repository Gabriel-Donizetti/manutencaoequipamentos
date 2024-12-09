package com.web2.manutencaoequipamentos.security;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.web2.manutencaoequipamentos.usuario.Usuario;


@Component
public abstract class Token {

    public static Instant generateTokenExpirationTime() {
        return Instant.now().plusSeconds(3600);// 1 hora
    }

    public static JwtClaimsSet generateTokenClaims(Usuario usuario) {
        return JwtClaimsSet.builder()
                .issuer("web-api") // Nome do emissor do token
                .issuedAt(Instant.now()) // Data e hora em que o token foi emitido
                .expiresAt(generateTokenExpirationTime()) // Método externo para definir o tempo de expiração
                .subject(usuario.getEmail()) // Identificação única do usuário (email como padrão)
                .claim("idUsuario", usuario.getIdUsuario().toString()) // ID do usuário
                .claim("nome", usuario.getNome()) // Nome do usuário
                .claim("email", usuario.getEmail()) // Email do usuário
                .claim("authorities", usuario.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority) // Pega as permissões do usuário
                        .collect(Collectors.toList())) // Converte para lista de strings
                .build();
    }

    public static String generateTokenJWT(JwtEncoder jwtEncoder, Usuario usuario) {
        return jwtEncoder.encode(JwtEncoderParameters.from(generateTokenClaims(usuario))).getTokenValue();
    }

    public static Object getClaimFromToken(JwtAuthenticationToken token, String nameClaim) {
        return token.getTokenAttributes().get(nameClaim);
    }

    public static UUID getidAccount(JwtAuthenticationToken token) {

        return UUID.fromString(getClaimFromToken(token, "idUsuario").toString());

    }
}
