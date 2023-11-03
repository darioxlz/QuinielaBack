package com.josepadron.quinielaapp.dto.user;

import com.josepadron.quinielaapp.models.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class UserSignUpDTO {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 150)
    private String name;

    @NotNull
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 150)
    private String password;

    public UserSignUpDTO() {}

    public UserSignUpDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User toModel(UserSignUpDTO userSignUpDTO) {
        return new User(userSignUpDTO.getName(), userSignUpDTO.getEmail(), userSignUpDTO.getPassword());
    }
}
