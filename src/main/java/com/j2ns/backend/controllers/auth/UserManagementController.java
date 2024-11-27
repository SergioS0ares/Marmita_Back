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

@RestController
@RequestMapping("/api")
public class UserManagementController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserManagementController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserModel registerUserModel) {
        try {
            AuthenticationResponseModel response = registerUserUseCase.register(registerUserModel);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.NOT_ACCEPTABLE);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>("Invalid data!", HttpStatus.BAD_REQUEST);
        }
    }
}
