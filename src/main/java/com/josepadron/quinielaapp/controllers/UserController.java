package com.josepadron.quinielaapp.controllers;

import com.josepadron.quinielaapp.dto.user.UserDTO;
import com.josepadron.quinielaapp.models.user.User;
import com.josepadron.quinielaapp.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        User user = UserDTO.toModel(userDto);
        user = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.toDTO(user));
    }
}
