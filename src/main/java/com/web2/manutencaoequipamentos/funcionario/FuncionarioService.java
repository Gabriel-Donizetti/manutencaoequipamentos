package com.web2.manutencaoequipamentos.funcionario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (funcionarios.isEmpty()) {
            throw new EntityNotFoundException("Nenhum funcionário encontrado.");
        }

        return funcionarios;
    }

    public Funcionario buscarPorId(UUID id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);

        if (funcionario.isEmpty()) {
            throw new EntityNotFoundException("Funcionário não encontrado com ID: " + id);
        }

        return funcionario.get();
    }

    public Funcionario salvar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void deletar(UUID id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        
        if (funcionario.isEmpty()) {
            throw new EntityNotFoundException("Funcionário não encontrado para exclusão com ID: " + id);
        }

        funcionarioRepository.deleteById(id);
    }
}
