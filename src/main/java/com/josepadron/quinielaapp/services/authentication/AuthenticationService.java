package com.josepadron.quinielaapp.services.authentication;

import com.josepadron.quinielaapp.dao.response.JwtAuthenticationResponse;
import com.josepadron.quinielaapp.dto.user.UserSignInDTO;
import com.josepadron.quinielaapp.dto.user.UserSignUpDTO;
import com.josepadron.quinielaapp.models.user.User;
import com.josepadron.quinielaapp.repositories.user.UserRepository;
import com.josepadron.quinielaapp.services.jwt.JwtService;
import com.josepadron.quinielaapp.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceI {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(UserSignUpDTO request) throws Exception {
        User user = userService.saveUser(UserSignUpDTO.toModel(request));

        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(UserSignInDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
