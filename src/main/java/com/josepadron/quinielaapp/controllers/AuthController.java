package com.josepadron.quinielaapp.controllers;

import com.josepadron.quinielaapp.dao.response.JwtAuthenticationResponse;
import com.josepadron.quinielaapp.dto.user.UserSignInDTO;
import com.josepadron.quinielaapp.dto.user.UserSignUpDTO;
import com.josepadron.quinielaapp.exceptions.UserDontExistsException;
import com.josepadron.quinielaapp.exceptions.UserEmailAlreadyExistsException;
import com.josepadron.quinielaapp.services.authentication.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@Valid @RequestBody UserSignUpDTO request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody UserSignInDTO request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, UserEmailAlreadyExistsException.class, AuthenticationException.class, IllegalArgumentException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof UserEmailAlreadyExistsException) {
            errors.put("email", ex.getMessage());
        } else {
            errors.put("message", ex.getMessage());
        }

        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserDontExistsException.class})
    public Map<String, String> handleResourceNotFound(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof UserDontExistsException) {
            errors.put("message", ex.getMessage());
        }

        return errors;
    }
}
