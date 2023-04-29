package com.josepadron.quinielaapp.services.user;

import com.josepadron.quinielaapp.models.user.User;
import com.josepadron.quinielaapp.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
