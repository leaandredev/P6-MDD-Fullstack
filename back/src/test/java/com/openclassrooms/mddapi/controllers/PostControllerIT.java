package com.openclassrooms.mddapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.openclassrooms.mddapi.dto.PostDto;

@ActiveProfiles("test")
@SpringBootTest(classes = { MddApiApplication.class, PostController.class })
@AutoConfigureMockMvc
public class PostControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreate() throws Exception {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setUserId(1L);
        postDto.setTopicId(1L);

        String jsonPostDto = mapper.writeValueAsString(postDto);

        // Act and Assert
        this.mockMvc.perform(post("/api/post")
                .with(user("DevAlice"))
                .content(jsonPostDto).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    public void testCreateUnauthorizedUser() throws Exception {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setUserId(2L);
        postDto.setTopicId(1L);

        String jsonPostDto = mapper.writeValueAsString(postDto);

        // Act and Assert
        this.mockMvc.perform(post("/api/post")
                .with(user("DevAlice"))
                .content(jsonPostDto).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testFindById() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/post/{id}", 1)
                .with(user("DevAlice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Singleton ou pas ?"))
                .andExpect(jsonPath("$.topicTitle").value("Java Best Practices"))
                .andExpect(jsonPath("$.userName").value("DevAlice"));
    }

    @Test
    public void testFindByIdWithPostNotFound() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/post/{id}", 12)
                .with(user("DevAlice")))
                .andExpect(status().isNotFound());
    }

}
