package com.openclassrooms.mddapi.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;
    private UserDto mockUserDto;

    @BeforeEach
    public void beforeEach() {
        mockUser = User.builder()
                .id(1L)
                .email("test@email.com")
                .userName("Deborah123")
                .password("password1234!")
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
        User actualUser = userService.save(mockUser);

        // Assert
        assertThat(actualUser).isEqualTo(mockUser);
        verify(userRepository).existsByEmail(mockUser.getEmail());
        verify(userRepository).existsByUserName(mockUser.getUserName());
        verify(userRepository).save(mockUser);
    }

    @Test
    public void testSaveUserWhenSameEmailFound() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(true);

        // Act
        Throwable thrown = catchThrowable(() -> userService.save(mockUser));

        // Assert
        assertThat(thrown).isInstanceOf(DuplicateEntryException.class);
        verify(userRepository).existsByEmail(mockUser.getEmail());
        verify(userRepository, never()).existsByUserName(mockUser.getUserName());
        verify(userRepository, never()).save(mockUser);
    }

    @Test
    public void testSaveUserWhenSameUserNameFound() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUserName(any())).thenReturn(true);

        // Act
        Throwable thrown = catchThrowable(() -> userService.save(mockUser));

        // Assert
        assertThat(thrown).isInstanceOf(DuplicateEntryException.class);
        verify(userRepository).existsByEmail(mockUser.getEmail());
        verify(userRepository).existsByUserName(mockUser.getUserName());
        verify(userRepository, never()).save(mockUser);
    }

    @Test
    public void testFindById() {
        // Arrange
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        // Act
        User user = userService.findById(mockUser.getId());

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("Deborah123");
        verify(userRepository).findById(mockUser.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        // Arrange
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> userService.findById(mockUser.getId()));

        // Assert
        assertThat(thrown).isInstanceOf(NoEntryFoundException.class);
        verify(userRepository).findById(mockUser.getId());
    }

    @Test
    public void testUpdate() {
        // Arrange
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User user = userService.update(mockUser.getId(), mockUserDto);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo(mockUserDto.getUserName());
        assertThat(user.getEmail()).isEqualTo(mockUserDto.getEmail());

        verify(userRepository).findById(mockUser.getId());
        verify(userRepository).save(mockUser);
    }

    @Test
    public void testUpdateWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> userService.update(mockUser.getId(), mockUserDto));

        // Assert
        assertThat(thrown).isInstanceOf(NoEntryFoundException.class);
        verify(userRepository).findById(mockUser.getId());
        verify(userRepository, never()).save(mockUser);
    }

}
