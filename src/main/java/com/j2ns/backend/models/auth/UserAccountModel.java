package com.j2ns.backend.models.auth;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity // Define a classe como uma entidade JPA para mapeamento de banco de dados.
public class UserAccountModel implements UserDetails { // A classe implementa UserDetails, necessária para a autenticação no Spring Security.

    @Id // Marca o atributo como chave primária.
    @GeneratedValue(strategy = GenerationType.AUTO) // Define que o valor da chave primária será gerado automaticamente.
    private long id; // Atributo que representa o ID do usuário na base de dados.

    @Column(nullable = false, unique = true) // Define que o campo 'username' não pode ser nulo e deve ser único.
    private String username; // Nome de usuário do usuário da conta.

    @Column(nullable = false) // Define que o campo 'password' não pode ser nulo.
    private String password; // Senha do usuário da conta.

    private boolean accountNonExpired = true; // Indica se a conta do usuário não expirou.
    private boolean accountNonLocked = true; // Indica se a conta do usuário não está bloqueada.
    private boolean credentialsNonExpired = true; // Indica se as credenciais do usuário não expiraram.
    private boolean enabled = true; // Indica se a conta do usuário está habilitada.

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // Relacionamento muitos-para-muitos com a tabela de roles.
    @JoinTable(
            name = "role_user", // Define o nome da tabela intermediária.
            joinColumns = @JoinColumn(name = "user_id"), // Define a coluna da tabela intermediária que se refere ao usuário.
            inverseJoinColumns = @JoinColumn(name = "role_id") // Define a coluna da tabela intermediária que se refere à role.
    )
    private Set<UserRoleModel> userRoleModels = new HashSet<>(); // Conjunto de roles associadas ao usuário.

    // Métodos Getters e Setters

    public long getId() {
        return id; // Retorna o ID do usuário.
    }

    public void setId(long id) {
        this.id = id; // Define o ID do usuário.
    }

    @Override
    public String getUsername() {
        return username; // Retorna o nome de usuário.
    }

    public UserAccountModel setUsername(String username) {
        this.username = username; // Define o nome de usuário.
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoleModels; // Retorna as roles associadas ao usuário, implementando a interface GrantedAuthority.
    }

    @Override
    public String getPassword() {
        return password; // Retorna a senha do usuário.
    }

    public UserAccountModel setPassword(String password) {
        this.password = password; // Define a senha do usuário.
        return this;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired; // Retorna se a conta está expirada ou não.
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired; // Define se a conta está expirada ou não.
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked; // Retorna se a conta está bloqueada ou não.
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked; // Define se a conta está bloqueada ou não.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired; // Retorna se as credenciais do usuário estão expiradas ou não.
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired; // Define se as credenciais do usuário estão expiradas ou não.
    }

    @Override
    public boolean isEnabled() {
        return enabled; // Retorna se a conta do usuário está habilitada ou não.
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled; // Define se a conta do usuário está habilitada ou não.
    }

    public Set<UserRoleModel> getUserRoles() {
        return userRoleModels; // Retorna o conjunto de roles associadas ao usuário.
    }

    public UserAccountModel setUserRoles(Set<UserRoleModel> userRoleModels) {
        this.userRoleModels = userRoleModels; // Define as roles associadas ao usuário.
        return this;
    }
}
