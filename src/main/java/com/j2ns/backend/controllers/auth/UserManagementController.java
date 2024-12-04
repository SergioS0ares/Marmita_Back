package com.j2ns.backend.controllers.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.j2ns.backend.exceptions.InvalidDataException;
import com.j2ns.backend.exceptions.UsernameAlreadyExistsException;
import com.j2ns.backend.interfaces.RegisterUserUseCase;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.RegisterUserModel;

@RestController // Define a classe como um controlador REST, responsável por lidar com requisições HTTP.
@RequestMapping("/api") // Mapeia todas as rotas para "/api".
public class UserManagementController {

    private final RegisterUserUseCase registerUserUseCase; // Instância do caso de uso que registra um novo usuário.

    // Construtor da classe que recebe o caso de uso e o injeta.
    public UserManagementController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    // Método que trata a rota POST "/api/register" para registrar um novo usuário.
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserModel registerUserModel) {
        try {
            // Tenta registrar o usuário utilizando o caso de uso
            AuthenticationResponseModel response = registerUserUseCase.register(registerUserModel);
            // Se o registro for bem-sucedido, retorna a resposta com status OK.
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyExistsException e) {
            // Se o nome de usuário já existir, retorna um erro com status NOT_ACCEPTABLE (406).
            return new ResponseEntity<>("Username already exists!", HttpStatus.NOT_ACCEPTABLE);
        } catch (InvalidDataException e) {
            // Se os dados fornecidos forem inválidos, retorna um erro com status BAD_REQUEST (400).
            return new ResponseEntity<>("Invalid data!", HttpStatus.BAD_REQUEST);
        }
    }
}
