package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NonNull
    @Size(max = 40)
    private String title;

    @Size(max = 255)
    private String content;

    private Long userId;

    private Long topicId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
