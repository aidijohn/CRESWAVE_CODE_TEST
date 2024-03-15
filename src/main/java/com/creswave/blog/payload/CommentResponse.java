package com.creswave.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {

    private String body;
    private LocalDateTime createdAt;
}
