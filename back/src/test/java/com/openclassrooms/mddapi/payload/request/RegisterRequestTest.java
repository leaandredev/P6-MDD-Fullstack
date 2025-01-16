package com.openclassrooms.mddapi.payload.request;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestTest {

    @Test
    void testLombokGeneratedMethods() {
        // Test @Data (Getters and Setters)
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setUserName("John123");
        request.setPassword("securePassword");

        assertThat(request.getEmail()).isEqualTo("test@example.com");
        assertThat(request.getUserName()).isEqualTo("John123");
        assertThat(request.getPassword()).isEqualTo("securePassword");

        // Test @Data (toString, equals, hashCode)
        RegisterRequest anotherRequest = new RegisterRequest();
        anotherRequest.setEmail("test@example.com");
        anotherRequest.setUserName("John123");
        anotherRequest.setPassword("securePassword");

        assertThat(request).isEqualTo(anotherRequest);
        assertThat(request.hashCode()).isEqualTo(anotherRequest.hashCode());
        assertThat(request.toString()).contains("test@example.com", "John123", "securePassword");
    }

    @Test
    void testValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Invalid RegisterRequest
        RegisterRequest invalidRequest = new RegisterRequest();
        invalidRequest.setEmail("invalid-email"); // Invalid email format
        invalidRequest.setUserName("Jo"); // Too short
        invalidRequest.setPassword("123"); // Too short

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(invalidRequest);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(3); // 4 invalid fields

        violations.forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            switch (propertyPath) {
                case "email":
                    assertThat(violation.getMessage())
                            .isEqualTo("doit être une adresse électronique syntaxiquement correcte");
                    break;
                case "userName":
                    assertThat(violation.getMessage()).isEqualTo("la taille doit être comprise entre 3 et 20");
                    break;
                case "password":
                    assertThat(violation.getMessage()).isEqualTo("la taille doit être comprise entre 6 et 40");
                    break;
                default:
                    throw new IllegalStateException("Unexpected violation: " + propertyPath);
            }
        });

        // Valid RegisterRequest
        RegisterRequest validRequest = new RegisterRequest();
        validRequest.setEmail("valid@example.com");
        validRequest.setUserName("John123");
        validRequest.setPassword("securePassword");

        violations = validator.validate(validRequest);

        assertThat(violations).isEmpty();
    }
}
