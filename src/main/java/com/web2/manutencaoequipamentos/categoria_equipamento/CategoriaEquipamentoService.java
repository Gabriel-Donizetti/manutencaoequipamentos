package com.web2.manutencaoequipamentos.categoria_equipamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;;

@Service
public class CategoriaEquipamentoService {

    @Autowired
    private CategoriaEquipamentoRepository categoriaRepository;

    public CategoriaEquipamento createEquipamento(CategoriaEquipamentoDTO create) {
        CategoriaEquipamento categoria = new CategoriaEquipamento();

        categoria.setNome(create.getNome());

        categoriaRepository.save(categoria);

        return categoria;
    }

    public CategoriaEquipamento updateEquip(UUID id, CategoriaEquipamentoDTO dto) {
        CategoriaEquipamento equipamento = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + id));
    
        equipamento.setNome(dto.getNome());
        return categoriaRepository.save(equipamento);
    }

    public void deleteEquip(UUID id) {

        Optional<CategoriaEquipamento> equipamentoOptional = categoriaRepository.findById(id);

        if (equipamentoOptional.isEmpty()) {
            throw new EntityNotFoundException("Categoria de Equipamento não encontrada para o ID: " + id);
        }

        CategoriaEquipamento equipamento = equipamentoOptional.get();

        categoriaRepository.delete(equipamento);
    }
    List<String> findAllNames(){
        List<CategoriaEquipamento> lista = categoriaRepository.findAll();
        return lista.stream()
        .map(CategoriaEquipamento::getNome) // Mapeia o nome de cada objeto
        .toList();
    }



}
