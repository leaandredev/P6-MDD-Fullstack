package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing an success response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {

    private String message;

}
