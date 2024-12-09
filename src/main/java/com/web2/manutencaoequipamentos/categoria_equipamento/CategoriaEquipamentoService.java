package com.web2.manutencaoequipamentos.categoria_equipamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web2.manutencaoequipamentos.categoria_equipamento.*;;

@Service
public class CategoriaEquipamentoService {
    
    @Autowired
    private CategoriaEquipamentoRepository categoriaRepository;

    public CategoriaEquipamento createEquipamento(CategoriaEquipamentoDTO create){
        CategoriaEquipamento categoria = new CategoriaEquipamento();

        categoria.setNome(create.getNome());

        categoriaRepository.save(categoria);

        return categoria;
    }
}
