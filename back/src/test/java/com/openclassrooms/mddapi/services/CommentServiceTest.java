package com.openclassrooms.mddapi.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Topic mockTopic;
    private User mockUser;
    private Post mockPost;
    private Comment mockComment;

    @BeforeEach
    public void beforeEach() {
        mockUser = User.builder()
                .id(1L)
                .email("test@email.com")
                .userName("Deborah123")
                .password("password1234!")
                .build();

        mockTopic = Topic.builder()
                .id(1L)
                .title("Test Topic")
                .description("This is a test topic")
                .build();

        mockPost = Post.builder()
                .id(1L)
                .title("Test Post")
                .content("This is a test post")
                .user(mockUser)
                .topic(mockTopic)
                .build();
        mockComment = Comment.builder()
                .id(1L)
                .content("This is a comment")
                .user(mockUser)
                .post(mockPost)
                .build();
    }

    @Test
    public void testSave() {
        // Arrange
        when(commentRepository.save(mockComment)).thenReturn(mockComment);

        // Act
        Comment savedComment = commentService.save(mockComment);

        // Assert
        verify(commentRepository).save(mockComment);
        assertThat(mockComment).isEqualTo(savedComment);
    }
}
