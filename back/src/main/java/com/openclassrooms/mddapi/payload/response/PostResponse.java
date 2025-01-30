package com.openclassrooms.mddapi.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO with post information
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponse {

    private String title;

    private String content;

    private String userName;

    private String topicTitle;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
