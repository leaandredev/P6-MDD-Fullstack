package com.openclassrooms.mddapi.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.mddapi.exception.DuplicateEntryException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    public void beforeEach() {
        mockUser = User.builder()
                .id(1L)
                .email("test@email.com")
                .userName("Deborah123")
                .password("password1234!")
                .build();
    }

    @Test
    public void testSaveUser() {
        // Arrange
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUserName(any())).thenReturn(false);

        // Act
        User actualUser = userService.saveUser(mockUser);

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
        Throwable thrown = catchThrowable(() -> userService.saveUser(mockUser));

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
        Throwable thrown = catchThrowable(() -> userService.saveUser(mockUser));

        // Assert
        assertThat(thrown).isInstanceOf(DuplicateEntryException.class);
        verify(userRepository).existsByEmail(mockUser.getEmail());
        verify(userRepository).existsByUserName(mockUser.getUserName());
        verify(userRepository, never()).save(mockUser);
    }

}
