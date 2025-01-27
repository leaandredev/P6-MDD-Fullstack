package com.openclassrooms.mddapi.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostMapperTest {

    @Mock
    private TopicService topicService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    private PostDto mockPostDto;
    private Post mockPost;
    private Topic mockTopic;
    private User mockUser;

    @BeforeEach
    public void beforeEach() {
        LocalDateTime now = LocalDateTime.now();

        mockUser = User.builder()
                .id(1L)
                .email("test@email.com")
                .userName("Deborah123")
                .password("password1234!")
                .createdAt(now)
                .updatedAt(now)
                .build();

        mockTopic = Topic.builder()
                .id(1L)
                .title("Test Topic")
                .description("This is a test topic")
                .createdAt(now)
                .updatedAt(now)
                .build();

        mockPost = Post.builder()
                .id(1L)
                .title("Test Post")
                .content("This is a test post")
                .user(mockUser)
                .topic(mockTopic)
                .createdAt(now)
                .updatedAt(now)
                .build();

        mockPostDto = new PostDto();
        mockPostDto.setId(1L);
        mockPostDto.setTitle("Test Post");
        mockPostDto.setContent("This is a test post");
        mockPostDto.setUserId(1L);
        mockPostDto.setTopicId(1L);
        mockPostDto.setCreatedAt(now);
        mockPostDto.setUpdatedAt(now);
    }

    @Test
    public void testToEntity() {
        // Arrange
        when(topicService.findById(1L)).thenReturn(mockTopic);
        when(userService.findById(1L)).thenReturn(mockUser);

        // Act
        Post post = postMapper.toEntity(mockPostDto);

        // Assert
        assertThat(post).isNotNull();
        assertThat(post).isEqualTo(mockPost);
    }

    @Test
    public void testullDtoToEntity() {
        // Act
        Post post = postMapper.toEntity((PostDto) null);

        // Assert
        assertThat(post).isNull();
    }

    @Test
    public void testToDto() {
        // Act
        PostDto postDto = postMapper.toDto(mockPost);

        // Assert
        assertThat(postDto).isNotNull();
        assertThat(postDto).isEqualTo(mockPostDto);
    }

    @Test
    public void testNullEntityToDto() {
        // Act
        PostDto postDto = postMapper.toDto((Post) null);

        // Assert
        assertThat(postDto).isNull();
    }

    @Test
    public void testToEntityList() {
        // Arrange
        when(topicService.findById(1L)).thenReturn(mockTopic);
        when(userService.findById(1L)).thenReturn(mockUser);
        List<PostDto> mockPostDtoList = List.of(mockPostDto, mockPostDto);

        // Act
        List<Post> postList = postMapper.toEntity(mockPostDtoList);

        // Assert
        assertThat(postList).isNotNull();
        assertThat(postList).hasSize(2);
    }

    @Test
    public void testNullPostDtoListToEntityList() {
        // Arrange
        List<PostDto> mockPostDtoList = null;

        // Act
        List<Post> postList = postMapper.toEntity(mockPostDtoList);

        // Assert
        assertThat(postList).isNull();
    }

    @Test
    public void testNullPostListToDtoList() {
        // Arrange
        List<Post> mockPostList = null;

        // Act
        List<PostDto> postDtoList = postMapper.toDto(mockPostList);

        // Assert
        assertThat(postDtoList).isNull();
    }
}
