package com.j2ns.backend.services.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.j2ns.backend.repositories.auth.UserRepository;

@Service // Marca esta classe como um componente gerenciado pelo Spring (Service layer).
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Repositório para acessar os dados do usuário.

    // Construtor para injetar o repositório de usuários.
    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    // Método obrigatório da interface UserDetailsService, utilizado pelo Spring Security para carregar os dados de um usuário com base no nome de usuário (username).
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo username.
        // Caso não encontre, lança a exceção UsernameNotFoundException.
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
