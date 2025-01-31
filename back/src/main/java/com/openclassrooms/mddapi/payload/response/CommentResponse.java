package com.openclassrooms.mddapi.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO with comment information
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentResponse {

    private Long id;

    private String content;

    private Long postId;

    private String userName;

    private LocalDateTime createdAt;

}
