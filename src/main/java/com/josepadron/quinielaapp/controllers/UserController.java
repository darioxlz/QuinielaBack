package com.josepadron.quinielaapp.controllers;

import com.josepadron.quinielaapp.dto.user.UserCreateDTO;
import com.josepadron.quinielaapp.dto.user.UserDTO;
import com.josepadron.quinielaapp.exceptions.UserDontExistsException;
import com.josepadron.quinielaapp.exceptions.UserEmailAlreadyExistsException;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getUsers() throws Exception {
        List<User> users = userService.getUsers();

        return ResponseEntity.status(HttpStatus.OK).body(
                users.stream()
                        .map(UserDTO::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) throws Exception {
        User user;

        try {
            user = userService.getUser(userId);
        } catch (UserDontExistsException e) {
            ResponseEntity.notFound();
            throw e;
        }

        return ResponseEntity.status(HttpStatus.OK).body(UserDTO.toDTO(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDto) throws Exception {
        User user = UserCreateDTO.toModel(userCreateDto);
        user = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
        } catch (UserDontExistsException e) {
            ResponseEntity.notFound();
            throw e;
        }

        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, UserEmailAlreadyExistsException.class})
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
