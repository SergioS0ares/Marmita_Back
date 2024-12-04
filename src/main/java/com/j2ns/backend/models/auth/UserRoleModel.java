package com.j2ns.backend.models.auth;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRoleModel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Gera automaticamente o valor do ID para cada instância da entidade.
    private Long id; // ID único para cada função de usuário (role).
    
    private String name; // Nome da função do usuário (ex: ADMIN, USER, etc.).
    
    // Relacionamento muitos-para-muitos entre UserRoleModel e UserAccountModel.
    @ManyToMany(mappedBy = "userRoleModels") // Especifica que a propriedade "userRoleModels" é o lado proprietário do relacionamento.
    private Set<UserAccountModel> users = new HashSet<>(); // Conjunto de usuários associados a esta função.

    // Implementação do método da interface GrantedAuthority para retornar o nome da função.
    @Override
    public String getAuthority() {
        return name; // Retorna o nome da função que será usado para autenticação e autorização.
    }

    // Getter e Setter para o ID.
    public Long getId() {
        return id; // Retorna o ID da função.
    }

    public UserRoleModel setId(Long id) {
        this.id = id; // Define o ID da função.
        return this; // Retorna a instância atual para permitir encadeamento de métodos.
    }

    // Getter e Setter para o nome da função.
    public String getName() {
        return name; // Retorna o nome da função.
    }

    public UserRoleModel setName(String name) {
        this.name = name; // Define o nome da função.
        return this; // Retorna a instância atual para permitir encadeamento de métodos.
    }

    // Getter e Setter para os usuários associados à função.
    public Set<UserAccountModel> getUsers() {
        return users; // Retorna o conjunto de usuários associados à função.
    }

    public UserRoleModel setUsers(Set<UserAccountModel> users) {
        this.users = users; // Define os usuários associados à função.
        return this; // Retorna a instância atual para permitir encadeamento de métodos.
    }
}
