package com.openclassrooms.mddapi.payload.response;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageResponseTest {

    @Test
    public void testConstructorAndGetter() {
        // Arrange
        String expectedMessage = "Operation successful";

        // Act
        MessageResponse messageResponse = new MessageResponse(expectedMessage);

        // Assert
        assertThat(messageResponse.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    public void testSetter() {
        // Arrange
        MessageResponse messageResponse = new MessageResponse(null);

        // Act
        String newMessage = "Operation successful";
        messageResponse.setMessage(newMessage);

        // Assert
        assertThat(messageResponse.getMessage()).isEqualTo(newMessage);
    }
}
