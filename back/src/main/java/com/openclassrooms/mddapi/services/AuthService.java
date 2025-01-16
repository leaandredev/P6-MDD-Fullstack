package com.openclassrooms.mddapi.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;

import lombok.extern.slf4j.Slf4j;

/* 
 * Service which handle authentication and user registration
 */
@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /**
     * Authenticate a user and return a new JwtToken
     * 
     * @param identifier    the email of the user or username attempting to log in
     * @param plainPassword the plain-text password of the user
     * @return a new jwtToken provide by jwtUtils
     */
    public String login(String identifier, String plainPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        identifier,
                        plainPassword));
        log.info("Authentication successful");
        return jwtUtils.generateJwtToken(authentication);
    }

    /**
     * Save a new user to the database, authenticate him and return a JwtToken
     * 
     * @param newUser       The user to register
     * @param plainPassword the plain-text password of the new user
     * @return a new jwtToken for the newly registered user
     */
    public void register(User newUser, String plainPassword) {
        userService.saveUser(newUser);
    }

}
