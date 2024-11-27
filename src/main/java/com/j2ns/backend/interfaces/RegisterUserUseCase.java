package com.j2ns.backend.interfaces;

import com.j2ns.backend.exceptions.InvalidDataException;
import com.j2ns.backend.exceptions.UsernameAlreadyExistsException;
import com.j2ns.backend.models.auth.AuthenticationResponseModel;
import com.j2ns.backend.models.auth.RegisterUserModel;

public interface RegisterUserUseCase {
    AuthenticationResponseModel register(RegisterUserModel registerUserModel) throws UsernameAlreadyExistsException, InvalidDataException;
}
