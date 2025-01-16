package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling user registration requests.
 * Contains the user's email, userName, and password with validation
 * constraints.
 */
@Data
public class RegisterRequest {
    /**
     * The user name, which can be used to log in the app.
     * This field is mandatory and cannot be null or blank.
     */
    @NotBlank(message = "The userName field is required.")
    @NotNull(message = "The userName field cannot be null.")
    @Size(min = 3, max = 20)
    private String userName;

    /**
     * The email address of the user.
     * This field is mandatory and cannot be null or blank.
     */
    @NotBlank(message = "The email field is required.")
    @NotNull(message = "The email field cannot be null.")
    @Size(max = 50)
    @Email
    private String email;

    /**
     * The password chosen by the user.
     * This field is mandatory and cannot be null or blank.
     */
    @NotBlank(message = "The password field is required.")
    @NotNull(message = "The password field cannot be null.")
    @Size(min = 6, max = 40)
    private String password;
}
