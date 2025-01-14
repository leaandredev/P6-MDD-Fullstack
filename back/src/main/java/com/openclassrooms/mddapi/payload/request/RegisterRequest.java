package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "The userName field is required.")
    @NotNull(message = "The userName field cannot be null.")
    @Size(min = 3, max = 20)
    private String userName;

    @NotBlank(message = "The email field is required.")
    @NotNull(message = "The email field cannot be null.")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "The password field is required.")
    @NotNull(message = "The password field cannot be null.")
    @Size(min = 6, max = 40)
    private String password;
}
