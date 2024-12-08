package com.web2.manutencaoequipamentos.usuario;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web2.manutencaoequipamentos.cliente.ClientRepository;
import com.web2.manutencaoequipamentos.cliente.Cliente;
import com.web2.manutencaoequipamentos.endereco.Endereco;
import com.web2.manutencaoequipamentos.endereco.EnderecoRepository;
import com.web2.manutencaoequipamentos.role.Role;
import com.web2.manutencaoequipamentos.role.RoleRepository;
import com.web2.manutencaoequipamentos.security.AuthDTO;
import com.web2.manutencaoequipamentos.usuario.Dto.CreateUsuarioDTO;
import com.web2.manutencaoequipamentos.utils.EmailService;
import com.web2.manutencaoequipamentos.utils.GerarSenha;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService email;


    public UsuarioRepository getRepository() {
        return repository;
    }

    public Usuario create(CreateUsuarioDTO create) {

        if (!repository.findByEmail(create.getEmail()).isEmpty()) {
            throw new EntityNotFoundException("Usuário com e-mail " + create.getEmail() + " já cadastrado");
        }

        if (!clientRepository.findByCpf(create.getCpf()).isEmpty()) {
            throw new EntityNotFoundException("Usuário com CPF " + create.getCpf() + " já cadastrado");
        }

        final Usuario usuario = new Usuario();

        final String senha = GerarSenha.gerarSenha();

        usuario.setNome(create.getNome());
        usuario.setEmail(create.getEmail());
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setRoles(Set.of(roleRepository.findByName(Role.Values.ROLE_USER)));

        final Endereco endereco = new Endereco();

        endereco.setCep(create.getCep());
        endereco.setEstado(create.getEstado());
        endereco.setCidade(create.getCidade());
        endereco.setBairro(create.getBairro());
        endereco.setLogradouro(create.getLogradouro());
        endereco.setNumero(create.getNumero());
        endereco.setComplemento(create.getComplemento());
        endereco.setUnidade(create.getUnidade());

        final Cliente cliente = new Cliente();

        cliente.setUsuario(repository.save(usuario));
        cliente.setCpf(create.getCpf());
        cliente.setTelefone(create.getTelefone());
        cliente.setEndereco(enderecoRepository.save(endereco));

        clientRepository.save(cliente);

        email.sendEmail(usuario.getNome(), usuario.getEmail(), "Cadastro de Usuário", senha);

        return usuario;
    }

    public ResponseEntity<Usuario> get(UUID id) {
        return ResponseEntity.ok(repository.findById(id).get());

    }

    public Usuario loadUserByEmail(String email) throws EntityNotFoundException {

        final java.util.Optional<Usuario> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário com e-mail " + email + " não encontrado");
        }

        return user.get();

    }

    public Usuario loadUserByLogin(String login) throws EntityNotFoundException {

        final java.util.Optional<Usuario> user = repository.findByEmail(login);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Usuário com Login " + login + " não encontrado");
        }

        return user.get();

    }

    public Usuario makeLogin(AuthDTO account) throws EntityNotFoundException {

        final java.util.Optional<Usuario> usuario = repository.findByEmail(account.login());
    
        if (usuario.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    
        if (!usuario.get().isLoginCorrect(account, passwordEncoder)) {
            throw new BadCredentialsException("Senha ou Login inválido");
        }
    
        return usuario.get();
    }
    
}
