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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

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
    public void testLogin() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwtToken");
        // Act

        String actualToken = authService.login("test@email.com", "password1234!");

        // Assert
        assertThat(actualToken).isEqualTo("jwtToken");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(authentication);
    }

    @Test
    public void testRegister() {
        // Arrange
        when(userService.saveUser(any(User.class))).thenReturn(mockUser);

        // Act
        authService.register(mockUser, "password1234!");

        // Assert
        verify(userService).saveUser(mockUser);
    }

}
