package com.web2.manutencaoequipamentos.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.web2.manutencaoequipamentos.utils.Return;

@RestController
@RequestMapping("api/v1/usuario/")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    // @PreAuthorize("hasAuthority('ROLE_USER')")
    // @PreAuthorize("hasAnyAuthority('USER', 'NONE', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Return> get(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            JwtAuthenticationToken token) {

        final var users = service.getRepository()
                .findAll(PageRequest.of(page, pageSize, Direction.DESC, "dateTimeAccess", "nickname"));

        return ResponseEntity
                .ok(new Return(users.toList(), page, pageSize, users.getTotalPages(), users.getTotalElements()));
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id) {

        return "1";
    }

    @DeleteMapping("/{id}")
    public String dalete(@PathVariable String id) {

        return "1";
    }
}
