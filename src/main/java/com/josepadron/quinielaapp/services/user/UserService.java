package com.josepadron.quinielaapp.services.user;

import com.josepadron.quinielaapp.exceptions.EmailAlreadyExistsException;
import com.josepadron.quinielaapp.models.user.User;
import com.josepadron.quinielaapp.repositories.user.UserRepository;
import com.josepadron.quinielaapp.utils.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email user is already exists");
        }

        user.setPassword(StringEncryptor.encrypt(user.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
