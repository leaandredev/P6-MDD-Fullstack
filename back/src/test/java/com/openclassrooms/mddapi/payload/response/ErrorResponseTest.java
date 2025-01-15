package com.openclassrooms.mddapi.payload.response;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {

    @Test
    public void testConstructorAndGetter() {
        // Arrange
        String expectedMessage = "An error occurred";

        // Act
        ErrorResponse ErrorResponse = new ErrorResponse(expectedMessage);

        // Assert
        assertThat(ErrorResponse.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    public void testSetter() {
        // Arrange
        ErrorResponse ErrorResponse = new ErrorResponse(null);

        // Act
        String newMessage = "An error occurred";
        ErrorResponse.setMessage(newMessage);

        // Assert
        assertThat(ErrorResponse.getMessage()).isEqualTo(newMessage);
    }
}
