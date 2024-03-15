package com.creswave.blog.service;

import com.creswave.blog.exception.ResourceNotFoundException;
import com.creswave.blog.model.Comment;
import com.creswave.blog.model.Post;
import com.creswave.blog.model.User;
import com.creswave.blog.payload.CommentRequest;
import com.creswave.blog.payload.CommentResponse;
import com.creswave.blog.repository.CommentRepository;
import com.creswave.blog.repository.PostRepository;
import com.creswave.blog.repository.UserRepository;
import com.creswave.blog.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddComment() {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setBody("Test Comment Body");
        Long postId = 1L;
        Long userId = 1L;

        Post post = new Post();
        post.setId(postId);

        User user = new User();
        user.setId(Math.toIntExact(userId));

        Comment comment = new Comment();
        comment.setBody(commentRequest.getBody());
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Act
        CommentResponse response = commentService.addComment(commentRequest, postId, userId);

        // Assert
        assertEquals(commentRequest.getBody(), response.getBody());
    }

    @Test
    public void testAddCommentWithInvalidPostId() {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setBody("Test Comment Body");
        Long postId = 1L;
        Long userId = 1L;

        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            commentService.addComment(commentRequest, postId, userId);
        });
    }

    // Similar tests for other methods like getCommentsByPostId, getAllComments, etc.
}
