package com.web2.manutencaoequipamentos.funcionario;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/v1/funcionario/")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping()
    public ResponseEntity<MessageWithArray> getFuncionarios() {
        try {
            List<Funcionario> funcionarios = funcionarioService.listarTodos();

            return ResponseEntity.ok(new MessageWithArray("Funcionários", funcionarios));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageWithArray("Erro ao buscar funcionários: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageWithArray> updateFuncionario(@PathVariable("id") UUID id,
            @RequestBody Funcionario funcionarioAtualizado) {
        try {
            Funcionario funcionarioExistente = funcionarioService.buscarPorId(id);

            funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
            funcionarioExistente.setDataNascimento(funcionarioAtualizado.getDataNascimento());
            funcionarioExistente.setUsuario(funcionarioAtualizado.getUsuario());

            funcionarioService.salvar(funcionarioExistente);

            return ResponseEntity.ok(new MessageWithArray("Funcionário atualizado com sucesso", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageWithArray("Funcionário não encontrado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageWithArray("Erro ao atualizar funcionário: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageWithArray> deleteFuncionario(@PathVariable("id") UUID id) {
        try {
            Funcionario funcionario = funcionarioService.buscarPorId(id);

            if (funcionario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageWithArray("Funcionário não encontrado", null));
            }

            funcionarioService.deletar(id);

            return ResponseEntity.ok(new MessageWithArray("Funcionário deletado com sucesso", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageWithArray("Erro ao deletar funcionário: " + e.getMessage(), null));
        }
    }

    public record MessageWithArray(String message, List<Funcionario> list) {
    }

}
