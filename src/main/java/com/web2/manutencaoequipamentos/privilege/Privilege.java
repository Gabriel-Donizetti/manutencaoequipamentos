package com.web2.manutencaoequipamentos.privilege;

import java.util.Collection;
import java.util.UUID;

import com.web2.manutencaoequipamentos.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "privilege")
@Entity(name = "privilege")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPrivilege;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
