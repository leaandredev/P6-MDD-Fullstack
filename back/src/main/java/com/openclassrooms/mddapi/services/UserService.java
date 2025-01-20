package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exception.DuplicateEntryException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/* Service for User treatment. Handle get and create user actions */
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save a user in database
     * 
     * @param user The User entity to save
     * @return The User entity saved
     */
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUserName(user.getUserName())) {
            throw new DuplicateEntryException("A user already exist with this email address or username");
        } else {
            userRepository.save(user);
            log.info("User saved");
            return user;
        }
    }

}
