package com.openclassrooms.mddapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.openclassrooms.mddapi.MddApiApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = { MddApiApplication.class, TopicController.class })
@AutoConfigureMockMvc
public class TopicControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
        // Act and Assert
        this.mockMvc.perform(get("/api/topic")
                .with(user("DevAlice")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testSubscribe() throws Exception {
        // Act and Assert
        this.mockMvc.perform(post("/api/topic/1/subscribe/1")
                .with(user("DevAlice")))
                .andExpect(status().isOk());
    }

    @Test
    public void testSubscribeUnauthorized() throws Exception {
        // Act and Assert
        this.mockMvc.perform(post("/api/topic/2/subscribe/2")
                .with(user("DevAlice")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSubscribeWithIdNotFound() throws Exception {
        // Act and Assert
        this.mockMvc.perform(post("/api/topic/15/subscribe/1")
                .with(user("DevAlice")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSubscribeWithInvalidIds() throws Exception {
        // Act and Assert
        this.mockMvc.perform(post("/api/topic/1/subscribe/invalid")
                .with(user("DevAlice")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUnsubscribe() throws Exception {
        // Act and Assert
        this.mockMvc.perform(delete("/api/topic/3/unsubscribe/1")
                .with(user("DevAlice")))
                .andExpect(status().isOk());
        ;
    }

    @Test
    public void testUnsubscribeUnauthorized() throws Exception {
        // Act and Assert
        this.mockMvc.perform(delete("/api/topic/3/unsubscribe/2")
                .with(user("DevAlice")))
                .andExpect(status().isUnauthorized());
        ;
    }

    @Test
    public void testUnsubscribeWithTopicNotFound() throws Exception {
        // Act and Assert
        this.mockMvc.perform(delete("/api/topic/3/unsubscribe/36")
                .with(user("DevAlice")))
                .andExpect(status().isNotFound());
        ;
    }

    @Test
    public void testUnsubscribeWithInvalidIds() throws Exception {
        // Act and Assert
        this.mockMvc.perform(delete("/api/topic/invalid/unsubscribe/1")
                .with(user("DevAlice")))
                .andExpect(status().isBadRequest());
        ;
    }
}
