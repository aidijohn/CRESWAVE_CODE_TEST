package com.creswave.blog.controller;

import com.creswave.blog.payload.CommentRequest;
import com.creswave.blog.payload.CommentResponse;
import com.creswave.blog.payload.PagedResponse;
import com.creswave.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        Long userId = commentRequest.getUserId();
        Long postId = commentRequest.getPostId();

        // Rest of your code remains the same
        CommentResponse response = commentService.addComment(commentRequest, userId, postId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/getAllComments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<CommentResponse>> getAllComments(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PagedResponse<CommentResponse> response = commentService.getAllComments(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCommentById/{commentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId) {
        CommentResponse response = commentService.getCommentById(commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCommentsByUserId/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentsByUserId(
            @PathVariable Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PagedResponse<CommentResponse> response = commentService.getCommentsByUserId(userId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/getCommentsByPostId/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PagedResponse<CommentResponse> response = commentService.getCommentsByPostId(postId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/updateComment/{commentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,
                                                         @Valid @RequestBody CommentRequest commentRequest) {
        CommentResponse response = commentService.updateComment(commentId, commentRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/deleteComment/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Comment successfully deleted");
        return ResponseEntity.ok(responseBody);
    }

}
