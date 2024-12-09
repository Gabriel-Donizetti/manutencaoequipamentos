package com.web2.manutencaoequipamentos.categoria_equipamento;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/v1/categoriaequipamento")
public class CategoriaEquipamentoController {
    @Autowired
    private CategoriaEquipamentoService equipService;

    @PostMapping()
    @Transactional
    public ResponseEntity<Message> criarEquipamento(@RequestBody CategoriaEquipamentoDTO create,
            JwtAuthenticationToken token) {
        try {
            equipService.createEquipamento(create);

            return ResponseEntity.ok(new Message("Categoria criada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Erro:" + e.getMessage()));
        }
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<Message> updateEquipamento(@RequestBody CategoriaEquipamentoDTO update, @RequestParam("id") UUID id,
            JwtAuthenticationToken token) {

        try {
            equipService.updateEquip(id, update);

            return ResponseEntity.ok(new Message("Categoria atualizada com sucesso!"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Erro: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Erro: " + e.getMessage()));
        }

    }

    @DeleteMapping()
    public ResponseEntity<Message> deleteEquipamento(@RequestParam("id") UUID id,
            JwtAuthenticationToken token) {

        try {

            equipService.deleteEquip(id);

            return ResponseEntity.ok(new Message("Categoria deletada!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Erro:" + e.getMessage()));
        }

    }

    @GetMapping("/equips")
    @Transactional
    public ResponseEntity<MessageWithArray> listAll(JwtAuthenticationToken token){
        try {
            List<String> equipNames = equipService.findAllNames();
            
            return ResponseEntity.ok(new MessageWithArray("Categorias:",equipNames));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageWithArray("Erro:" + e.getMessage(),null));
        }
        
    }

    public record Message(String message) {
    }

    public record MessageWithArray(String message, List<String> list) {
    }

}
