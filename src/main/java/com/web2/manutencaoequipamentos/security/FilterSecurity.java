package com.web2.manutencaoequipamentos.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web2.manutencaoequipamentos.security.keys.PublicKeyConverter;
import com.web2.manutencaoequipamentos.usuario.Usuario;
import com.web2.manutencaoequipamentos.usuario.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    @Value("${security.public.key}")
    private String publicKey;

    @Autowired
    @Lazy
    private UsuarioService service;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    final UsernamePasswordAuthenticationToken authToken = validateToken(authElements[1]);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public UsernamePasswordAuthenticationToken validateToken(String token) {

        final DecodedJWT decoded = JWT.require(Algorithm.RSA256(new PublicKeyConverter().convert(publicKey.trim()))).build().verify(token);

        final Usuario usuario = service.loadUserByEmail(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    }
}
