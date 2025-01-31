package com.openclassrooms.mddapi.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommentTest {

    private Comment comment;
    private User user;
    private Post post;

    @BeforeEach
    public void beforeEach() {
        user = Mockito.mock(User.class);
        post = Mockito.mock(Post.class);
        comment = Comment.builder()
                .content("Test Content")
                .user(user)
                .post(post)
                .build();
    }

    @Test
    public void testCommentCreation() {
        // Assert
        assertThat(comment).isNotNull();
        assertThat(comment.getContent()).isEqualTo("Test Content");
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getPost()).isEqualTo(post);
    }

    @Test
    public void testBuilder() {
        // Act
        Comment builtComment = Comment.builder()
                .id(1L)
                .content("Sample Content")
                .user(user)
                .post(post)
                .build();

        // Assert
        assertThat(builtComment).isNotNull();
        assertThat(builtComment.getId()).isEqualTo(1L);
        assertThat(builtComment.getContent()).isEqualTo("Sample Content");
        assertThat(builtComment.getUser()).isEqualTo(user);
        assertThat(builtComment.getPost()).isEqualTo(post);
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        comment.setId(1L);
        comment.setContent("New Content");
        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(now);

        // Assert
        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("New Content");
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getPost()).isEqualTo(post);
        assertThat(comment.getCreatedAt()).isEqualTo(now);
    }

    @Test
    public void testAllArgsConstructor() {
        // Act
        LocalDateTime now = LocalDateTime.now();
        Comment requiredComment = new Comment(1l, user, post, "New Content", now);

        // Assert
        assertThat(requiredComment.getId()).isEqualTo(1L);
        assertThat(requiredComment.getContent()).isEqualTo("New Content");
        assertThat(requiredComment.getUser()).isEqualTo(user);
        assertThat(requiredComment.getPost()).isEqualTo(post);
        assertThat(requiredComment.getCreatedAt()).isEqualTo(now);
    }

    @Test
    public void testNoArgsConstructor() {
        // Assert
        Comment emptyComment = new Comment();
        assertThat(emptyComment).isNotNull();
    }

    @Test
    public void testExceptionForNullRequiredFields() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new Comment(null, null, null, null, null));
    }
}