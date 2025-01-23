package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(
            UserService userService,
            UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /**
     * Find a user by its id
     * 
     * @param id The id of the user to find
     * @return a {@link ResponseEntity} with {@link UserDto} response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        try {
            User user = this.userService.findById(Long.valueOf(id));
            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a user
     * 
     * @param id      The id of the user to update
     * @param userDto The UserDto entity to update
     * @return a {@link ResponseEntity} with {@link UserDto} response
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        try {
            User user = this.userService.update(Long.valueOf(id), userDto);
            return ResponseEntity.ok().body(this.userMapper.toDto(user));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
