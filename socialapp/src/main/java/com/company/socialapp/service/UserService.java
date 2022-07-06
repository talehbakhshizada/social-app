package com.company.socialapp.service;

import com.company.socialapp.entity.User;
import com.company.socialapp.exception.NotFoundException;
import com.company.socialapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("USER NOT FOUND"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User newUser) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()) {
            User foundUser = oldUser.get();
            foundUser.setUserName(newUser.getUserName());
            return userRepository.save(foundUser);
        } else {
            throw new NotFoundException("USER NOT FOUND");
        }
    }

    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if ((user.isPresent())) {
            userRepository.delete(user.get());
        } else {
            throw new NotFoundException("USER NOT FOUND");
        }

    }
}
