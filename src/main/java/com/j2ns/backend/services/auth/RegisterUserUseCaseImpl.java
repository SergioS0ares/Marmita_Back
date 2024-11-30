package com.j2ns.backend.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.j2ns.backend.exceptions.InvalidDataException;
import com.j2ns.backend.exceptions.UsernameAlreadyExistsException;
import com.j2ns.backend.interfaces.AuthenticationService;
import com.j2ns.backend.interfaces.RegisterUserUseCase;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.RegisterUserModel;
import com.j2ns.backend.models.auth.UserAccountModel;
import com.j2ns.backend.repositories.auth.UserRepository;

// Anotação @Service indica que esta classe é um componente do Spring gerenciado como um serviço.
@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    // Repositório para gerenciar dados de usuários no banco de dados.
    private final UserRepository userRepository;

    // Codificador de senha para garantir que as senhas sejam armazenadas de forma segura.
    private final PasswordEncoder passwordEncoder;

    // Serviço de autenticação para gerar respostas de autenticação após o registro.
    private final AuthenticationService authenticationService;

    // Construtor para injetar dependências do repositório, encoder e serviço de autenticação.
    public RegisterUserUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    // Implementação do método de registro definido na interface RegisterUserUseCase.
    @Override
    public AuthenticationResponseModel register(RegisterUserModel registerUserModel) throws UsernameAlreadyExistsException, InvalidDataException {
        
        // Verifica se o nome de usuário ou a senha estão nulos.
        if (registerUserModel.username == null || registerUserModel.password == null) {
            throw new InvalidDataException(); // Lança exceção se os dados estiverem inválidos.
        }

        // Verifica se já existe um usuário com o mesmo nome de usuário.
        if (userRepository.findByUsername(registerUserModel.username).isPresent()) {
            throw new UsernameAlreadyExistsException(); // Lança exceção se o nome de usuário já estiver em uso.
        }

        // Cria um novo modelo de conta de usuário com o nome de usuário e senha codificada.
        UserAccountModel user = new UserAccountModel()
                .setUsername(registerUserModel.username) // Define o nome de usuário.
                .setPassword(passwordEncoder.encode(registerUserModel.password)); // Codifica e define a senha.

        // Salva o usuário no banco de dados.
        userRepository.save(user);

        // Retorna a resposta de autenticação gerada com base no usuário registrado.
        return authenticationService.getAuthenticationResponseFromUser(user);
    }
}
