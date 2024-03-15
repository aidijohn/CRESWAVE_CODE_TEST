package com.creswave.blog.controller;

import com.creswave.blog.payload.CommentRequest;
import com.creswave.blog.payload.CommentResponse;
import com.creswave.blog.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @Test
    public void testAddComment() throws Exception {
        // Arrange
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setBody("Test Comment Body");
        Long postId = 1L;
        Long userId = 1L;

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setBody(commentRequest.getBody());
        commentResponse.setCreatedAt(LocalDateTime.now());

        when(commentService.addComment(any(CommentRequest.class), any(Long.class), any(Long.class))).thenReturn(commentResponse);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments/addComment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .param("postId", String.valueOf(postId))
                        .param("userId", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(commentRequest.getBody()));
    }

    // Similar tests for other controller methods
}
