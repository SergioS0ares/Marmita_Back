package com.fachter.backend.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRole implements GrantedAuthority {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "userRoles")
    private Set<UserAccount> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public UserRole setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRole setName(String name) {
        this.name = name;
        return this;
    }

    public Set<UserAccount> getUsers() {
        return users;
    }

    public UserRole setUsers(Set<UserAccount> users) {
        this.users = users;
        return this;
    }
}
