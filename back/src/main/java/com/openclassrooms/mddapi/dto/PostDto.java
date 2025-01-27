package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NonNull
    @Size(max = 100)
    private String title;

    @NonNull
    @Lob
    private String content;

    @NonNull
    private Long userId;

    @NonNull
    private Long topicId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
