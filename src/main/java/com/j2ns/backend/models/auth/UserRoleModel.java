package com.j2ns.backend.models.auth;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRoleModel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "userRoleModels")
    private Set<UserAccountModel> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public UserRoleModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRoleModel setName(String name) {
        this.name = name;
        return this;
    }

    public Set<UserAccountModel> getUsers() {
        return users;
    }

    public UserRoleModel setUsers(Set<UserAccountModel> users) {
        this.users = users;
        return this;
    }
}
