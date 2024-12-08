package com.web2.manutencaoequipamentos.utils;

import java.time.LocalDate;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.web2.manutencaoequipamentos.funcionario.Funcionario;
import com.web2.manutencaoequipamentos.funcionario.FuncionarioRepository;
import com.web2.manutencaoequipamentos.role.Role;
import com.web2.manutencaoequipamentos.role.Role.Values;
import com.web2.manutencaoequipamentos.usuario.Usuario;
import com.web2.manutencaoequipamentos.usuario.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() == 0) {

            Usuario usuario = new Usuario();
            usuario.setNome("Admin");
            usuario.setEmail("admin@web2.com");
            usuario.setSenha(passwordEncoder.encode("admin"));
            Role roleAdmin = new Role();
            roleAdmin.setName(Values.ROLE_ADMIN);
            usuario.setRoles(Collections.singleton(roleAdmin));
            usuarioRepository.saveAndFlush(usuario);

            System.out.println("Usuário padrão criado com sucesso!");

            Funcionario funcionario = new Funcionario();
            funcionario.setDataNascimento(LocalDate.now());
            funcionario.setUsuario(usuario);

            funcionarioRepository.saveAndFlush(funcionario);
            System.out.println("Funcionário padrão associado ao usuário criado com sucesso!");
        } else {
            System.out.println("Usuários ou funcionários já existem no banco de dados.");
        }
    }
}