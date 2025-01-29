package com.openclassrooms.mddapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.MddApiApplication;
import com.openclassrooms.mddapi.dto.UserDto;

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

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("alice@mdd.com");
        userDto.setUserName("DevAliceUpdated");

        String jsonUserDto = mapper.writeValueAsString(userDto);

        // Act and Assert
        this.mockMvc.perform(put("/api/user/{id}", 1)
                .with(user("DevAlice"))
                .content(jsonUserDto).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("alice@mdd.com"))
                .andExpect(jsonPath("$.userName").value("DevAliceUpdated"));

    }

    @Test
    public void testUpdateWithWrongNumberFormat() throws Exception {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("alice@mdd.com");
        userDto.setUserName("DevAliceUpdated");

        String jsonUserDto = mapper.writeValueAsString(userDto);

        // Act and Assert
        this.mockMvc.perform(put("/api/user/{id}", "notNumber")
                .with(user("DevAlice"))
                .content(jsonUserDto).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testUpdateWithUserNotFound() throws Exception {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setEmail("alice@mdd.com");
        userDto.setUserName("DevAliceUpdated");

        String jsonUserDto = mapper.writeValueAsString(userDto);

        // Act and Assert
        this.mockMvc.perform(put("/api/user/{id}", 54)
                .with(user("DevAlice"))
                .content(jsonUserDto).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The user does not exist"));

    }

    @Test
    public void testGetSubscriptions() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/user/{id}/subscriptions", 1)
                .with(user("DevAlice")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetFeedPosts() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/user/{id}/feed", 1)
                .with(user("DevAlice")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

}
