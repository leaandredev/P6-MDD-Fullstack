package com.openclassrooms.mddapi.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO with session information for authentificated user
 */
@Data
@RequiredArgsConstructor
@Builder
public class SessionInformationResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String userName;
    private String email;
}
