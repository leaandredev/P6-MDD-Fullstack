package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.payload.response.SessionInformationResponse;
import com.openclassrooms.mddapi.services.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    /**
     * @param passwordEncoder
     * @param authService
     */
    AuthController(PasswordEncoder passwordEncoder, AuthService authService) {
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    /**
     * Authentificate a user
     * 
     * @param loginRequest A requestBody with user email and password
     * @return a JwtToken for the user
     */
    @PostMapping(value = "/login", consumes = { "application/json" })
    @ResponseBody
    public ResponseEntity<SessionInformationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        SessionInformationResponse sessionInformation = authService.login(loginRequest.getIdentifier(),
                loginRequest.getPassword());

        return ResponseEntity
                .ok(sessionInformation);
    }

    /**
     * Register and authentificate a new user
     * 
     * @param registerRequest A requestBody with user email, name and password
     * @return a {@link ResponseEntity} with {@link MessageResponse} request
     */
    @PostMapping(value = "/register", consumes = { "application/json" })
    @ResponseBody
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        // Create new user's account
        User user = User.builder()
                .userName(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        authService.register(user, registerRequest.getPassword());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
