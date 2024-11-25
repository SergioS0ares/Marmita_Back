package com.fachter.backend.interfaces;

import com.fachter.backend.exceptions.InvalidDataException;
import com.fachter.backend.exceptions.UsernameAlreadyExistsException;
import com.fachter.backend.models.auth.AuthenticationResponseModel;
import com.fachter.backend.models.auth.RegisterUserModel;

public interface RegisterUserUseCase {
    AuthenticationResponseModel register(RegisterUserModel registerUserModel) throws UsernameAlreadyExistsException, InvalidDataException;
}
