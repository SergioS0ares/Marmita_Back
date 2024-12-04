package com.j2ns.backend.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.models.auth.UserRoleModel;
import com.j2ns.backend.repositories.auth.RoleRepository;
import com.j2ns.backend.repositories.auth.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Configuration // Marca a classe como uma classe de configuração do Spring.
public class InitData {

    private final UserRepository userRepository; // Repositório para interagir com os usuários no banco de dados.
    private final RoleRepository roleRepository; // Repositório para interagir com os papéis de usuário (roles) no banco de dados.
    private final PasswordEncoder passwordEncoder; // Responsável por codificar as senhas de forma segura.

    // Construtor da classe, que injeta as dependências dos repositórios e do PasswordEncoder.
    public InitData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método anotado com @EventListener, que escuta o evento ApplicationReadyEvent do Spring Boot.
    // Este método é executado quando a aplicação está pronta para ser usada.
    @EventListener(ApplicationReadyEvent.class)
    public void insertInitData() {
        // Verifica se já existem roles no banco de dados.
        var roles = new HashSet<>(roleRepository.findAll()); // Recupera todas as roles do banco e as coloca em um conjunto (HashSet).
        
        // Caso não haja nenhuma role cadastrada, cria e insere as roles ADMIN e USER.
        if (roles.isEmpty()) {
            List<UserRoleModel> allRoles = new ArrayList<>();
            // Criação dos papéis de usuário (ADMIN e USER).
            allRoles.add(new UserRoleModel().setName(Role.ADMIN.name())); // Cria o papel ADMIN.
            allRoles.add(new UserRoleModel().setName(Role.USER.name()));  // Cria o papel USER.
            roleRepository.saveAll(allRoles); // Salva todos os papéis no banco de dados.
            roles = new HashSet<>(allRoles); // Atualiza a variável roles com os papéis inseridos.
        }

        // Verifica se já existe um usuário com o nome "admin" no banco de dados.
        if (userRepository.findByUsername("admin").isEmpty()) {
            // Caso o usuário "admin" não exista, cria um novo usuário com esse nome.
            UserAccountModel admin = new UserAccountModel()
                    .setUsername("admin") // Define o nome de usuário como "admin".
                    .setUserRoles(roles) // Associa as roles ADMIN e USER ao novo usuário.
                    .setPassword(passwordEncoder.encode("admin123")); // Codifica a senha do usuário de forma segura.

            userRepository.save(admin); // Salva o usuário no banco de dados.
        }
    }
}
