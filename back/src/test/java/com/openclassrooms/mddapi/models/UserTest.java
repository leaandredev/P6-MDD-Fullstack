package com.openclassrooms.mddapi.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        // Act
        User user = User.builder()
                .email("test@example.com")
                .userName("John123")
                .password("password123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    void testLombokGeneratedMethods() {
        // Act
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .userName("John123")
                .password("password123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUserName()).isEqualTo("John123");
        assertThat(user.getPassword()).isEqualTo("password123");

        String userString = user.toString();
        assertThat(userString).contains("id=1");
        assertThat(userString).contains("email=test@example.com");
    }

    @Test
    void testRequiredArgsConstructor() {
        User user = new User("test@example.com", "John123", "password123");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUserName()).isEqualTo("John123");
        assertThat(user.getPassword()).isEqualTo("password123");
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "test@example.com", "John123", "password123", null, now, now);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUserName()).isEqualTo("John123");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertThat(user).isNotNull();
    }

    @Test
    void testDataAnnotation() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUserName("John123");
        user.setPassword("password123");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getUserName()).isEqualTo("John123");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User user = User.builder()
                .email("invalid-email")
                .userName("John123")
                .password("password123")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();

        ConstraintViolation<User> violation = violations.iterator().next();
        assertThat(violation.getMessage()).isEqualTo("doit être une adresse électronique syntaxiquement correcte");
    }

    @Test
    void testExceptionForNullRequiredFields() {
        assertThrows(NullPointerException.class, () -> new User(null, "John123", "password123"));
        assertThrows(NullPointerException.class, () -> new User("test@example.com", null, "password123"));
    }
}
