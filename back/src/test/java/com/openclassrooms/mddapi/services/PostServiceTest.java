package com.openclassrooms.mddapi.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
