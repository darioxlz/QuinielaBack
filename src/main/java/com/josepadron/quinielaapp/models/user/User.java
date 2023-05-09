package com.josepadron.quinielaapp.models.user;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;

    public User() {
    }
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
