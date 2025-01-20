package com.openclassrooms.mddapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.MddApiApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { MddApiApplication.class, UserController.class })
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testFindById() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/user/{id}", 2)
                .with(user("DevAlice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("bob@mdd.com"))
                .andExpect(jsonPath("$.userName").value("DevBob"));
    }

    @Test
    public void testFindByIdWithUserNotFound() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/user/{id}", 12)
                .with(user("DevAlice")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByIdForbidden() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/user/{id}", 2)).andExpect(status().isForbidden());
    }

}
