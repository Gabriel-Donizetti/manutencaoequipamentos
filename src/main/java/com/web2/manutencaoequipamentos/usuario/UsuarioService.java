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
import com.web2.manutencaoequipamentos.funcionario.Funcionario;
import com.web2.manutencaoequipamentos.funcionario.FuncionarioRepository;
import com.web2.manutencaoequipamentos.role.Role;
import com.web2.manutencaoequipamentos.role.RoleRepository;
import com.web2.manutencaoequipamentos.security.AuthDTO;
import com.web2.manutencaoequipamentos.usuario.Dto.CreateFuncionarioDTO;
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
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService email;

    public UsuarioRepository getRepository() {
        return repository;
    }

    public Usuario createUsuario(CreateUsuarioDTO create) {
        if (repository.findByEmail(create.getEmail()).isPresent()) {
            throw new EntityNotFoundException("Usuário com e-mail " + create.getEmail() + " já cadastrado");
        }

        if (clientRepository.findByCpf(create.getCpf()).isPresent()) {
            throw new EntityNotFoundException("Usuário com CPF " + create.getCpf() + " já cadastrado");
        }

        Usuario usuario = new Usuario();
        String senha = GerarSenha.gerarSenha();

        usuario.setNome(create.getNome());
        usuario.setEmail(create.getEmail());
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setRoles(Set.of(roleRepository.findByName(Role.Values.ROLE_USER)));

        Endereco endereco = new Endereco();
        endereco.setCep(create.getCep());
        endereco.setEstado(create.getEstado());
        endereco.setCidade(create.getCidade());
        endereco.setBairro(create.getBairro());
        endereco.setLogradouro(create.getLogradouro());
        endereco.setNumero(create.getNumero());
        endereco.setComplemento(create.getComplemento());
        endereco.setUnidade(create.getUnidade());

        Cliente cliente = new Cliente();
        cliente.setUsuario(repository.save(usuario));
        cliente.setCpf(create.getCpf());
        cliente.setTelefone(create.getTelefone());
        cliente.setEndereco(enderecoRepository.save(endereco));

        clientRepository.save(cliente);

        email.sendEmail(usuario.getNome(), usuario.getEmail(), "Cadastro de Usuário", senha);

        return usuario;
    }

    public Usuario createFuncionario(CreateFuncionarioDTO create) {
        if (repository.findByEmail(create.getEmail()).isPresent()) {
            throw new EntityNotFoundException("Funcionário com e-mail " + create.getEmail() + " já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(create.getNome());
        usuario.setEmail(create.getEmail());
        usuario.setSenha(passwordEncoder.encode(create.getSenha())); // Criptografa a senha
        usuario.setRoles(Set.of(roleRepository.findByName(Role.Values.ROLE_STAFF)));

        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario(repository.save(usuario));
        funcionario.setDataNascimento(create.getDataNascimento());

        funcionarioRepository.save(funcionario);

        return usuario;
    }

    public ResponseEntity<Usuario> get(UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    public Usuario loadUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com e-mail " + email + " não encontrado"));
    }

    public Usuario loadUserByLogin(String login) {
        return repository.findByEmail(login)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com Login " + login + " não encontrado"));
    }

    public Usuario makeLogin(AuthDTO account) {
        Usuario usuario = repository.findByEmail(account.login())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!usuario.isLoginCorrect(account, passwordEncoder)) {
            throw new BadCredentialsException("Senha ou Login inválido");
        }

        return usuario;
    }
}
