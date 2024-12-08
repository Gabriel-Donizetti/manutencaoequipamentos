package com.web2.manutencaoequipamentos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.web2.manutencaoequipamentos.role.Role;
import com.web2.manutencaoequipamentos.role.Role.Values;
import com.web2.manutencaoequipamentos.role.RoleRepository;




@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (Values values : Role.Values.values()) {
            if (roleRepository.findByName(values) == null) {
                Role role = new Role();
                role.setName(values);
                roleRepository.save(role);
            }
        }
    }
    

}
