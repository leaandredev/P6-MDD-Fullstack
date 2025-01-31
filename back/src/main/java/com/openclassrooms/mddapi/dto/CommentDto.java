package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    @NonNull
    @Lob
    private String content;

    @NonNull
    private Long userId;

    @NonNull
    private Long postId;

    private LocalDateTime createdAt;
}
