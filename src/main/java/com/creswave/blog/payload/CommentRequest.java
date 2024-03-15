package com.creswave.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    @NotNull
    private Long userId;

    @NotNull
    private Long postId;

    // Getters and setters for userId and postId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
