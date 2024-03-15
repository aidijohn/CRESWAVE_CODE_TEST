package com.creswave.blog.service;

import com.creswave.blog.payload.CommentRequest;
import com.creswave.blog.payload.CommentResponse;
import com.creswave.blog.payload.PagedResponse;

public interface CommentService {
    CommentResponse addComment(CommentRequest commentRequest, Long postId, Long userId);

    PagedResponse<CommentResponse> getCommentsByPostId(Long postId, int page, int size);

    PagedResponse<CommentResponse> getAllComments(int page, int size);

    CommentResponse getCommentById(Long commentId);

    PagedResponse<CommentResponse> getCommentsByUserId(Long userId, int page, int size);

    CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

    void deleteComment(Long commentId);
}
