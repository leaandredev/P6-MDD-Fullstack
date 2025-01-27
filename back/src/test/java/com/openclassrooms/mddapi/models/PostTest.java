package com.openclassrooms.mddapi.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTest {

    private Post post;
    private User user;
    private Topic topic;

    @BeforeEach
    public void beforeEach() {
        user = Mockito.mock(User.class);
        topic = Mockito.mock(Topic.class);
        post = Post.builder()
                .title("Test Title")
                .content("Test Content")
                .user(user)
                .topic(topic)
                .build();
    }

    @Test
    public void testPostCreation() {
        // Assert
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("Test Title");
        assertThat(post.getContent()).isEqualTo("Test Content");
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getTopic()).isEqualTo(topic);
    }

    @Test
    public void testBuilder() {
        // Act
        Post builtPost = Post.builder()
                .id(1L)
                .title("Sample Title")
                .content("Sample Content")
                .user(user)
                .topic(topic)
                .build();

        // Assert
        assertThat(builtPost).isNotNull();
        assertThat(builtPost.getId()).isEqualTo(1L);
        assertThat(builtPost.getTitle()).isEqualTo("Sample Title");
        assertThat(builtPost.getContent()).isEqualTo("Sample Content");
        assertThat(builtPost.getUser()).isEqualTo(user);
        assertThat(builtPost.getTopic()).isEqualTo(topic);
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        post.setId(1L);
        post.setTitle("New Title");
        post.setContent("New Content");
        post.setUser(user);
        post.setTopic(topic);
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        // Assert
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("New Title");
        assertThat(post.getContent()).isEqualTo("New Content");
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getTopic()).isEqualTo(topic);
        assertThat(post.getCreatedAt()).isEqualTo(now);
        assertThat(post.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void testAllArgsConstructor() {
        // Act
        LocalDateTime now = LocalDateTime.now();
        Post requiredPost = new Post(1L, "New Title", "New Content", user, topic, now, now);

        // Assert
        assertThat(requiredPost.getId()).isEqualTo(1L);
        assertThat(requiredPost.getTitle()).isEqualTo("New Title");
        assertThat(requiredPost.getContent()).isEqualTo("New Content");
        assertThat(requiredPost.getUser()).isEqualTo(user);
        assertThat(requiredPost.getTopic()).isEqualTo(topic);
        assertThat(requiredPost.getCreatedAt()).isEqualTo(now);
        assertThat(requiredPost.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void testNoArgsConstructor() {
        // Assert
        Post emptyPost = new Post();
        assertThat(emptyPost).isNotNull();
    }

    @Test
    public void testExceptionForNullRequiredFields() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new Post(null, null, null, null, null, null, null));
    }
}