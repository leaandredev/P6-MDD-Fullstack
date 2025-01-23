package com.openclassrooms.mddapi.models;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TopicTest {

    private Topic topic;

    @BeforeEach
    public void setUp() {
        topic = new Topic();
    }

    @Test
    public void testBuilder() {
        // Act
        Topic builtTopic = Topic.builder()
                .id(1L)
                .title("Sample Title")
                .description("Sample Description")
                .build();

        // Assert
        assertThat(builtTopic.getId()).isEqualTo(1L);
        assertThat(builtTopic.getTitle()).isEqualTo("Sample Title");
        assertThat(builtTopic.getDescription()).isEqualTo("Sample Description");
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        topic.setId(1L);
        topic.setTitle("New Title");
        topic.setDescription("New Description");
        topic.setCreatedAt(now);
        topic.setUpdatedAt(now);

        // Assert
        assertThat(topic.getId()).isEqualTo(1L);
        assertThat(topic.getTitle()).isEqualTo("New Title");
        assertThat(topic.getDescription()).isEqualTo("New Description");
        assertThat(topic.getCreatedAt()).isEqualTo(now);
        assertThat(topic.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void testAllArgsConstructor() {
        // Act
        LocalDateTime now = LocalDateTime.now();
        Topic requiredTopic = new Topic(1L, "New Title", "New Description", now, now);

        // Assert
        assertThat(requiredTopic.getId()).isEqualTo(1L);
        assertThat(requiredTopic.getTitle()).isEqualTo("New Title");
        assertThat(requiredTopic.getDescription()).isEqualTo("New Description");
        assertThat(requiredTopic.getCreatedAt()).isEqualTo(now);
        assertThat(requiredTopic.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void testNoArgsConstructor() {
        // Assert
        assertThat(topic).isNotNull();
    }
}