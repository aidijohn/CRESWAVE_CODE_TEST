package com.creswave.blog.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id; // Adding id field
    private String title;
    private String body;
    private String message;

    // Constructor to initialize with id, title, body, createdAt, and userId
    public PostResponse(Long id, String title, String body, LocalDateTime createdAt, Long userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        // You can include other fields as needed
        this.message = "Successfully created new post";
    }

    public PostResponse(String title, String body, String successfullyCreatedNewPost) {
    }

    public PostResponse(Long id, String title, String body, LocalDateTime createdAt, Integer id1) {
    }
}
