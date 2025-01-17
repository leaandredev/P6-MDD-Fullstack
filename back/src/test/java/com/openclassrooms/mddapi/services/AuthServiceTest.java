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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.response.SessionInformationResponse;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

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
    public void testRegister() {
        // Arrange
        when(userService.saveUser(any(User.class))).thenReturn(mockUser);

        // Act
        authService.register(mockUser, "password1234!");

        // Assert
        verify(userService).saveUser(mockUser);
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");
        when(userDetails.getId()).thenReturn(1L);
        when(userDetails.getUsername()).thenReturn("Deborah123");
        when(userDetails.getEmail()).thenReturn("test@email.com");

        // Act
        SessionInformationResponse response = authService.login("email", "password");

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("token");
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUserName()).isEqualTo("Deborah123");
        assertThat(response.getEmail()).isEqualTo("test@email.com");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThatThrownBy(() -> authService.login("email", "wrongpassword"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid credentials");
    }
}
