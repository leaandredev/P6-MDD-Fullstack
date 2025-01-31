package com.openclassrooms.mddapi.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.exception.NoEntryFoundException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Topic mockTopic;
    private User mockUser;
    private Post mockPost;

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
    }

    @Test
    public void testSave() {
        // Arrange
        when(postRepository.save(mockPost)).thenReturn(mockPost);

        // Act
        Post savedPost = postService.save(mockPost);

        // Assert
        verify(postRepository).save(mockPost);
        assertThat(mockPost).isEqualTo(savedPost);
    }

    @Test
    public void testFindById() {
        // Arrange
        when(postRepository.findById(mockPost.getId())).thenReturn(Optional.of(mockPost));

        // Act
        Post post = postService.findById(mockPost.getId());

        // Assert
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("Test Post");
        verify(postRepository).findById(mockPost.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        // Arrange
        when(postRepository.findById(mockPost.getId())).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> postService.findById(mockPost.getId()));

        // Assert
        assertThat(thrown).isInstanceOf(NoEntryFoundException.class);
        verify(postRepository).findById(mockPost.getId());
    }
}
