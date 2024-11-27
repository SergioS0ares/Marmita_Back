package com.fachter.backend.controllers.auth;

import com.fachter.backend.interfaces.AuthenticationUseCase;
import com.fachter.backend.interfaces.RefreshAuthenticationUseCase;
import com.fachter.backend.models.auth.AuthenticationRequestModel;
import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.UserAccountModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;

    public AuthenticationController(AuthenticationUseCase authenticationUseCase, RefreshAuthenticationUseCase refreshAuthenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
        this.refreshAuthenticationUseCase = refreshAuthenticationUseCase;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseModel> createAuthToken(@RequestBody AuthenticationRequestModel authenticationRequestModel) {
        try {
            AuthenticationResponseModel response = authenticationUseCase.authenticate(authenticationRequestModel);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseModel> refreshToken(@AuthenticationPrincipal UserAccountModel user) {
        return ResponseEntity.ok(refreshAuthenticationUseCase.getRefreshedToken(user));
    }
}
