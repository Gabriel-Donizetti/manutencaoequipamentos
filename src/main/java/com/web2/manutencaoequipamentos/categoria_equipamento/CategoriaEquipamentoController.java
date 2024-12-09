package com.web2.manutencaoequipamentos.categoria_equipamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/v1/categoriaequipamento")
public class CategoriaEquipamentoController {
    @Autowired
    private CategoriaEquipamentoService equipService;

    @PostMapping("/post")
    @Transactional
    public ResponseEntity<String> criarEquipamento(@RequestBody CategoriaEquipamentoDTO create) {
        try {
            equipService.createEquipamento(create);

            return ResponseEntity.ok("Categoria adicionada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: " + e.getMessage());
        }
    }
}
