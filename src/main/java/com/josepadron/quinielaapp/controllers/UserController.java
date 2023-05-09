package com.josepadron.quinielaapp.controllers;

import com.josepadron.quinielaapp.dto.user.UserCreateDTO;
import com.josepadron.quinielaapp.dto.user.UserDTO;
import com.josepadron.quinielaapp.exceptions.EmailAlreadyExistsException;
import com.josepadron.quinielaapp.models.user.User;
import com.josepadron.quinielaapp.services.user.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDto) throws Exception {
        LOGGER.info("llega request");
        User user = UserCreateDTO.toModel(userCreateDto);
        user = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, EmailAlreadyExistsException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof EmailAlreadyExistsException) {
            errors.put("email", ex.getMessage());
        }

        return errors;
    }
}
