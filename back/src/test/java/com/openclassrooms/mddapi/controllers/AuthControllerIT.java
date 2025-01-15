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
                .andExpect(jsonPath("$.message").value("Error: Username is already taken!"));
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
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));
    }

}
