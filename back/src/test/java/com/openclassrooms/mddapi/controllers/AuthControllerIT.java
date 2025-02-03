package com.openclassrooms.mddapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.MddApiApplication;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;

@ActiveProfiles("test")
@SpringBootTest(classes = { MddApiApplication.class, AuthController.class })
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testLoginUser() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("alice@mdd.com");
        loginRequest.setPassword("password123");

        String jsonLoginRequest = mapper.writeValueAsString(loginRequest);

        // Act and Assert
        this.mockMvc.perform(
                post("/api/auth/login")
                        .content(jsonLoginRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testLoginUserWithInvalidCredentials() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier("InvalidUser");
        loginRequest.setPassword("wrongPassword!");

        String jsonLoginRequest = mapper.writeValueAsString(loginRequest);

        // Act and Assert
        this.mockMvc.perform(
                post("/api/auth/login")
                        .content(jsonLoginRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Access denied"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JoshFeurdol1234");
        RegisterRequest.setEmail("jfeurdol@example.com");
        RegisterRequest.setPassword("newPassword1234!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act and Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    public void testRegisterUserWhenUsernameAlreadyExist() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("DevAlice");
        RegisterRequest.setEmail("devAliceNew@example.com");
        RegisterRequest.setPassword("newPassword1234!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act and Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("A user already exist with this email address or username"));
    }

    @Test
    public void testRegisterUserWhenEmailAlreadyExist() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("NewBob");
        RegisterRequest.setEmail("bob@mdd.com");
        RegisterRequest.setPassword("newPassword1234!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act and Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("A user already exist with this email address or username"));
    }

    @Test
    public void testWhenUserPasswordIncorrect_TooShort() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JeffTrulu5478");
        RegisterRequest.setEmail("jtrulu@example.com");
        RegisterRequest.setPassword("newP1!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act & Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWhenUserPasswordIncorrect_TooLong() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JeffTrulu5478");
        RegisterRequest.setEmail("jtrulu@example.com");
        RegisterRequest.setPassword(
                "newP1!ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act & Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWhenUserPasswordIncorrect_WithoutNumber() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JeffTrulu5478");
        RegisterRequest.setEmail("jtrulu@example.com");
        RegisterRequest.setPassword("newPassword!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act & Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWhenUserPasswordIncorrect_WithoutUppercase() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JeffTrulu5478");
        RegisterRequest.setEmail("jtrulu@example.com");
        RegisterRequest.setPassword("newpassword1234!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act & Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWhenUserPasswordIncorrect_WithoutLowercase() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JeffTrulu5478");
        RegisterRequest.setEmail("jtrulu@example.com");
        RegisterRequest.setPassword("NEWPASSWORD1234!");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act & Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWhenUserPasswordIncorrect_WithoutSpecialCaracter() throws Exception {
        // Arrange
        RegisterRequest RegisterRequest = new RegisterRequest();
        RegisterRequest.setUserName("JeffTrulu5478");
        RegisterRequest.setEmail("jtrulu@example.com");
        RegisterRequest.setPassword("NewPassword1234");

        String jsonRegisterRequest = mapper.writeValueAsString(RegisterRequest);

        // Act & Assert
        this.mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRegisterRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
