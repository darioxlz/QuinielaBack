package com.josepadron.quinielaapp.dto.user;

import com.josepadron.quinielaapp.models.user.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class UserDTO {
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;

    public UserDTO() {}

    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public static User toModel(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }
}
