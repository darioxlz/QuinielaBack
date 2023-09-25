package com.josepadron.quinielaapp.services.user;

import com.josepadron.quinielaapp.exceptions.UserEmailAlreadyExistsException;
import com.josepadron.quinielaapp.exceptions.UserDontExistsException;
import com.josepadron.quinielaapp.models.user.User;
import com.josepadron.quinielaapp.repositories.user.UserRepository;
import com.josepadron.quinielaapp.utils.StringEncryptor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserDontExistsException("User dont exists");
        }

        return user.get();
    }

    public User createUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException("Email user is already exists");
        }

        user.setPassword(StringEncryptor.encrypt(user.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
