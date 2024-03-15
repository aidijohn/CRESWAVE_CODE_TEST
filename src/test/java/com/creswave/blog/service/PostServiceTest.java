package com.creswave.blog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.creswave.blog.model.Post;
import com.creswave.blog.payload.PostRequest;
import com.creswave.blog.payload.PostResponse;
import com.creswave.blog.repository.PostRepository;
import com.creswave.blog.service.impl.PostServiceImpl;

@SpringBootTest
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testUpdatePost() {
        // Arrange
        Long postId = 1L;
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Updated Title");
        postRequest.setBody("Updated Body");

        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Old Title");
        existingPost.setBody("Old Body");

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PostResponse response = postService.updatePost(postId, postRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Title", response.getTitle());
        assertEquals("Updated Body", response.getBody());
        assertEquals("Post updated successfully", response.getMessage());
    }

}
