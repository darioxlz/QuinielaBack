package com.josepadron.quinielaapp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class UserSignInDTO {
    @NotNull
    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    private String email;

    @NotNull
    @NotBlank
    private String password;

    public UserSignInDTO() {}

    public UserSignInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
