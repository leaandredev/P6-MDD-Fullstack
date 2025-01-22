package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.DuplicateEntryException;
import com.openclassrooms.mddapi.exception.NoEntryFoundException;
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
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUserName(user.getUserName())) {
            throw new DuplicateEntryException("A user already exist with this email address or username");
        } else {
            userRepository.save(user);
            log.info("User saved");
            return user;
        }
    }

    /**
     * Find a user by its id
     * 
     * @param id The id of the user to find
     * @return The User entity found
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoEntryFoundException("The user does not exist"));
    }

    /**
     * Update a user
     * 
     * @param id      The id of the user to update
     * @param userDto The UserDto with datas to update
     * @return The User entity updated
     */
    public User update(Long id, UserDto userDto) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new NoEntryFoundException("The user does not exist"));

        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setUserName(userDto.getUserName());
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(userToUpdate);
    }

}
