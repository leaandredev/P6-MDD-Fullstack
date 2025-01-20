package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO with session information for authentificated user
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionInformationResponse {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private Long id;
    private String userName;
    private String email;
}
