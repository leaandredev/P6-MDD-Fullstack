package com.openclassrooms.mddapi.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.DuplicateEntryException;
import com.openclassrooms.mddapi.exception.NoEntryFoundException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser1;
    private User mockUser2;
    private UserDto mockUserDto;
    private Topic mockTopic;
    private Post mockPost;

    @BeforeEach
    public void beforeEach() {
        mockUser1 = User.builder()
                .id(1L)
                .email("test@email.com")
                .userName("Deborah123")
                .password("password1234!")
                .build();
        mockUser2 = User.builder()
                .id(2L)
                .email("test2@email.com")
                .userName("Clea456")
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
                .user(mockUser1)
                .topic(mockTopic)
                .build();

        mockUserDto = new UserDto();
        mockUserDto.setEmail("test@email.com");
        mockUserDto.setUserName("Deborah123updated");
    }

    @Test
    public void testSaveUser() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUserName(any())).thenReturn(false);

        // Act
        User actualUser = userService.save(mockUser1);

        // Assert
        assertThat(actualUser).isEqualTo(mockUser1);
        verify(userRepository).existsByEmail(mockUser1.getEmail());
        verify(userRepository).existsByUserName(mockUser1.getUserName());
        verify(userRepository).save(mockUser1);
    }

    @Test
    public void testSaveUserWhenSameEmailFound() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act
        Throwable thrown = catchThrowable(() -> userService.save(mockUser1));

        // Assert
        assertThat(thrown).isInstanceOf(DuplicateEntryException.class);
        verify(userRepository).existsByEmail(mockUser1.getEmail());
        verify(userRepository, never()).existsByUserName(mockUser1.getUserName());
        verify(userRepository, never()).save(mockUser1);
    }

    @Test
    public void testSaveUserWhenSameUserNameFound() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUserName(any())).thenReturn(true);

        // Act
        Throwable thrown = catchThrowable(() -> userService.save(mockUser1));

        // Assert
        assertThat(thrown).isInstanceOf(DuplicateEntryException.class);
        verify(userRepository).existsByEmail(mockUser1.getEmail());
        verify(userRepository).existsByUserName(mockUser1.getUserName());
        verify(userRepository, never()).save(mockUser1);
    }

    @Test
    public void testFindById() {
        // Arrange
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.of(mockUser1));

        // Act
        User user = userService.findById(mockUser1.getId());

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("Deborah123");
        verify(userRepository).findById(mockUser1.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        // Arrange
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> userService.findById(mockUser1.getId()));

        // Assert
        assertThat(thrown).isInstanceOf(NoEntryFoundException.class);
        verify(userRepository).findById(mockUser1.getId());
    }

    @Test
    public void testUpdate() {
        // Arrange
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.of(mockUser1));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User user = userService.update(mockUser1.getId(), mockUserDto);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo(mockUserDto.getUserName());
        assertThat(user.getEmail()).isEqualTo(mockUserDto.getEmail());

        verify(userRepository).findById(mockUser1.getId());
        verify(userRepository).save(mockUser1);
    }

    @Test
    public void testUpdateWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(mockUser1.getId())).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> userService.update(mockUser1.getId(), mockUserDto));

        // Assert
        assertThat(thrown).isInstanceOf(NoEntryFoundException.class);
        verify(userRepository).findById(mockUser1.getId());
        verify(userRepository, never()).save(mockUser1);
    }

    @Test
    public void testAddPostToFeeds() {
        // Arrange
        when(userRepository.findBySubscriptionsContaining(mockPost.getTopic()))
                .thenReturn(List.of(mockUser1, mockUser2));

        // Act
        userService.addPostToFeeds(mockPost);

        // Assert
        assertThat(mockUser1.getFeed()).contains(mockPost);
        assertThat(mockUser2.getFeed()).contains(mockPost);
        verify(userRepository).findBySubscriptionsContaining(mockPost.getTopic());
        verify(userRepository).save(mockUser1);
        verify(userRepository).save(mockUser2);
    }

    @Test
    public void testAddPostToFeedsWhenNoUsersSubscribed() {
        // Arrange
        when(userRepository.findBySubscriptionsContaining(mockPost.getTopic())).thenReturn(List.of());

        // Act
        userService.addPostToFeeds(mockPost);

        // Assert
        verify(userRepository).findBySubscriptionsContaining(mockPost.getTopic());
        verify(userRepository, never()).save(any(User.class));
    }

}
