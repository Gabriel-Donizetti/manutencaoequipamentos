// package com.web2.manutencaoequipamentos.utils;

// import java.time.LocalDate;
// import java.util.Collections;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.web2.manutencaoequipamentos.funcionario.Funcionario;
// import com.web2.manutencaoequipamentos.funcionario.FuncionarioRepository;
// import com.web2.manutencaoequipamentos.role.Role;
// import com.web2.manutencaoequipamentos.role.Role.Values;
// import com.web2.manutencaoequipamentos.usuario.Usuario;
// import com.web2.manutencaoequipamentos.usuario.UsuarioRepository;

// @Component
// public class DataLoader implements CommandLineRunner {

//     @Autowired
//     private UsuarioRepository usuarioRepository;

//     @Autowired
//     private FuncionarioRepository funcionarioRepository;

//     @Autowired
//     private BCryptPasswordEncoder passwordEncoder;

//     @Override
//     public void run(String... args) throws Exception {
//         if (usuarioRepository.count() == 0) {
//             criarUsuarioAdmin();
//         } else {
//             System.out.println("Usuários ou funcionários já existem no banco de dados.");
//         }
//     }

//     private void criarUsuarioAdmin() {
//         // Verifica se já existe um usuário com o email "admin@web2.com"
//         if (usuarioRepository.findByEmail("admin@web2.com") != null) {
//             System.out.println("Usuário Admin já existe no banco de dados.");
//             return;
//         }
    
//         // Cria a role ADMIN se necessário
//         Role roleAdmin = new Role();
//         roleAdmin.setName(Values.ROLE_ADMIN);
    
//         // Cria o usuário ADMIN
//         Usuario usuario = new Usuario();
//         usuario.setNome("Admin");
//         usuario.setEmail("admin@web2.com");
//         usuario.setSenha(passwordEncoder.encode("admin"));
//         usuario.setRoles(Collections.singleton(roleAdmin));
    
//         // Salva o usuário no banco
//         usuarioRepository.save(usuario);
//         System.out.println("Usuário padrão criado com sucesso!");
    
//         // Associa o usuário a um funcionário
//         Funcionario funcionario = new Funcionario();
//         funcionario.setDataNascimento(LocalDate.now());
//         funcionario.setUsuario(usuario);
    
//         // Salva o funcionário no banco
//         funcionarioRepository.save(funcionario);
//         System.out.println("Funcionário padrão associado ao usuário criado com sucesso!");
//     }
    
// }