package com.fachter.backend.controllers.auth;

import com.fachter.backend.interfaces.AuthenticationUseCase;
import com.fachter.backend.interfaces.RefreshAuthenticationUseCase;
import com.fachter.backend.models.auth.AuthenticationRequestViewModel;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;
import com.fachter.backend.models.auth.UserAccount;

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
    public ResponseEntity<AuthenticationResponseViewModel> createAuthToken(@RequestBody AuthenticationRequestViewModel authenticationRequestViewModel) {
        try {
            AuthenticationResponseViewModel response = authenticationUseCase.authenticate(authenticationRequestViewModel);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseViewModel> refreshToken(@AuthenticationPrincipal UserAccount user) {
        return ResponseEntity.ok(refreshAuthenticationUseCase.getRefreshedToken(user));
    }
}
