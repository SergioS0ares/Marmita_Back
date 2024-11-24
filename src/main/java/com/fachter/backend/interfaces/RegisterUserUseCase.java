package com.fachter.backend.interfaces;

import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.models.auth.AuthenticationResponseViewModel;
import com.fachter.backend.models.auth.RegisterUserViewModel;

public interface RegisterUserUseCase {
    AuthenticationResponseViewModel register(RegisterUserViewModel registerUserViewModel) throws UsernameAlreadyExistsException, InvalidDataException;
}
