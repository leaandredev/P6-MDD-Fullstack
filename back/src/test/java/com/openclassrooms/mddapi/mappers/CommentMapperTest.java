package com.openclassrooms.mddapi.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.PostService;
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
public class CommentMapperTest {

    @Mock
    private TopicService topicService;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private CommentDto mockCommentDto;
    private Post mockPost;
    private Comment mockComment;
    private CommentResponse mockCommentResponse;
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

        mockComment = Comment.builder()
                .id(1L)
                .content("This is a test comment")
                .user(mockUser)
                .post(mockPost)
                .createdAt(now)
                .build();

        mockCommentDto = new CommentDto();
        mockCommentDto.setId(1L);
        mockCommentDto.setContent("This is a test comment");
        mockCommentDto.setUserId(1L);
        mockCommentDto.setPostId(1L);
        mockCommentDto.setCreatedAt(now);

        mockCommentResponse = CommentResponse.builder()
                .id(1L)
                .content("This is a test comment")
                .userName("Deborah123")
                .postId(1L)
                .createdAt(now)
                .build();
    }

    @Test
    public void testToEntity() {
        // Arrange
        when(postService.findById(1L)).thenReturn(mockPost);
        when(userService.findById(1L)).thenReturn(mockUser);

        // Act
        Comment comment = commentMapper.toEntity(mockCommentDto);

        // Assert
        assertThat(comment).isNotNull();
        assertThat(comment).isEqualTo(mockComment);
    }

    @Test
    public void testNullDtoToEntity() {
        // Act
        Comment comment = commentMapper.toEntity((CommentDto) null);

        // Assert
        assertThat(comment).isNull();
    }

    @Test
    public void testToDto() {
        // Act
        CommentDto commentDto = commentMapper.toDto(mockComment);

        // Assert
        assertThat(commentDto).isNotNull();
        assertThat(commentDto).isEqualTo(mockCommentDto);
    }

    @Test
    public void testNullEntityToDto() {
        // Act
        CommentDto commentDto = commentMapper.toDto((Comment) null);

        // Assert
        assertThat(commentDto).isNull();
    }

    @Test
    public void testToEntityList() {
        // Arrange
        when(postService.findById(1L)).thenReturn(mockPost);
        when(userService.findById(1L)).thenReturn(mockUser);
        List<CommentDto> mockCommentDtoList = List.of(mockCommentDto, mockCommentDto);

        // Act
        List<Comment> commentList = commentMapper.toEntity(mockCommentDtoList);

        // Assert
        assertThat(commentList).isNotNull();
        assertThat(commentList).hasSize(2);
    }

    @Test
    public void testNullPostDtoListToEntityList() {
        // Arrange
        List<CommentDto> mockCommentDtoList = null;

        // Act
        List<Comment> commentList = commentMapper.toEntity(mockCommentDtoList);

        // Assert
        assertThat(commentList).isNull();
    }

    @Test
    public void testNullPostListToDtoList() {
        // Arrange
        List<Comment> mockCommentList = null;

        // Act
        List<CommentDto> commentDtoList = commentMapper.toDto(mockCommentList);

        // Assert
        assertThat(commentDtoList).isNull();
    }

    @Test
    public void testToResponse() {
        // Act
        CommentResponse commentResponse = commentMapper.toResponse(mockComment);

        // Assert
        assertThat(commentResponse).isNotNull();
        assertThat(commentResponse).isEqualTo(mockCommentResponse);
    }

    @Test
    public void testNullEntityToResponse() {
        // Act
        CommentResponse commentResponse = commentMapper.toResponse((Comment) null);

        // Assert
        assertThat(commentResponse).isNull();
    }

    @Test
    public void testToResponseList() {
        // Arrange
        List<Comment> mockCommentList = List.of(mockComment, mockComment);

        // Act
        List<CommentResponse> commentResponseList = commentMapper.toResponse(mockCommentList);

        // Assert
        assertThat(commentResponseList).isNotNull();
        assertThat(commentResponseList).hasSize(2);
    }

    @Test
    public void testNullCommentListToResponseList() {
        // Arrange
        List<Comment> mockCommentList = null;

        // Act
        List<CommentResponse> commentResponseList = commentMapper.toResponse(mockCommentList);

        // Assert
        assertThat(commentResponseList).isNull();
    }
}
