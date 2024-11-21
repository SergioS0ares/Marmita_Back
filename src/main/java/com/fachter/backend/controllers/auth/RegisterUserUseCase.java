package com.fachter.backend.controllers.auth;

import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.viewModels.auth.AuthenticationResponseViewModel;
import com.fachter.backend.viewModels.auth.RegisterUserViewModel;

public interface RegisterUserUseCase {
    AuthenticationResponseViewModel register(RegisterUserViewModel registerUserViewModel) throws UsernameAlreadyExistsException, InvalidDataException;
}
