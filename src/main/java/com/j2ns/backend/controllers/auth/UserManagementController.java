package com.fachter.backend.controllers.auth;

import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.interfaces.RegisterUserUseCase;
import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.RegisterUserModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
