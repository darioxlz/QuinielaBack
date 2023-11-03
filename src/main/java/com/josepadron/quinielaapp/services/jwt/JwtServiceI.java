package com.josepadron.quinielaapp.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtServiceI {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
