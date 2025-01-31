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
import com.openclassrooms.mddapi.dto.CommentDto;

@ActiveProfiles("test")
@SpringBootTest(classes = { MddApiApplication.class, CommentController.class })
@AutoConfigureMockMvc
public class CommentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreate() throws Exception {
        // Arrange
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("Test Comment");
        commentDto.setUserId(1L);
        commentDto.setPostId(1L);

        String jsonCommentDto = mapper.writeValueAsString(commentDto);

        // Act and Assert
        this.mockMvc.perform(post("/api/comment")
                .with(user("DevAlice"))
                .content(jsonCommentDto).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Test Comment"));
    }

    @Test
    public void testGetComments() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/post/{id}/comments", 1)
                .with(user("DevAlice")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

}
