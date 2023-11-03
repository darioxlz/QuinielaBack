package com.josepadron.quinielaapp.services.authentication;

import com.josepadron.quinielaapp.dao.response.JwtAuthenticationResponse;
import com.josepadron.quinielaapp.dto.user.UserSignUpDTO;
import com.josepadron.quinielaapp.dto.user.UserSignInDTO;

public interface AuthenticationServiceI {
    JwtAuthenticationResponse signup(UserSignUpDTO request) throws Exception;

    JwtAuthenticationResponse signin(UserSignInDTO request);
}
