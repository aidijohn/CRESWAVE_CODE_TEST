package com.creswave.blog.service.impl;

import com.creswave.blog.exception.ResourceNotFoundException;
import com.creswave.blog.model.Comment;
import com.creswave.blog.model.Post;
import com.creswave.blog.model.User;
import com.creswave.blog.payload.PagedResponse;
import com.creswave.blog.payload.CommentRequest;
import com.creswave.blog.payload.CommentResponse;
import com.creswave.blog.repository.CommentRepository;
import com.creswave.blog.repository.PostRepository;
import com.creswave.blog.repository.UserRepository;
import com.creswave.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.creswave.blog.utils.AppConstants.CREATED_AT;
import static com.creswave.blog.utils.AppConstants.DEFAULT_PAGE_SIZE;
import static com.creswave.blog.utils.AppUtils.validatePageNumberAndSize;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentResponse addComment(CommentRequest commentRequest, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Comment comment = new Comment();
        comment.setBody(commentRequest.getBody());
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment.getBody(), savedComment.getCreatedAt());
    }


    @Override
    public PagedResponse<CommentResponse> getCommentsByPostId(Long postId, int page, int size) {
        validatePageNumberAndSize(page, size);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Comment> comments = commentRepository.findByPost(post, pageable);

        List<CommentResponse> content = comments.getNumberOfElements() == 0 ? Collections.emptyList() : comments.getContent().stream()
                .map(comment -> new CommentResponse(comment.getBody(), comment.getCreatedAt()))
                .collect(Collectors.toList());

        return new PagedResponse<>(content, comments.getNumber(), comments.getSize(), comments.getTotalElements(),
                comments.getTotalPages(), comments.isLast());
    }

    @Override
    public PagedResponse<CommentResponse> getAllComments(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Comment> comments = commentRepository.findAll(pageable);

        List<CommentResponse> content = comments.getNumberOfElements() == 0 ? Collections.emptyList() : comments.getContent().stream()
                .map(comment -> new CommentResponse(comment.getBody(), comment.getCreatedAt()))
                .collect(Collectors.toList());

        return new PagedResponse<>(content, comments.getNumber(), comments.getSize(), comments.getTotalElements(),
                comments.getTotalPages(), comments.isLast());
    }

    @Override
    public CommentResponse getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        return new CommentResponse(comment.getBody(), comment.getCreatedAt());
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setBody(commentRequest.getBody());
        // Update other fields as needed

        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponse(updatedComment.getBody(), updatedComment.getCreatedAt());
    }


    @Override
    public PagedResponse<CommentResponse> getCommentsByUserId(Long userId, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT); // Specify sorting direction as DESC

        Page<Comment> comments = commentRepository.findByUser(user, pageable);

        List<CommentResponse> content = comments.getNumberOfElements() == 0 ? Collections.emptyList() : comments.getContent().stream()
                .map(comment -> new CommentResponse(comment.getBody(), comment.getCreatedAt()))
                .collect(Collectors.toList());

        return new PagedResponse<>(content, comments.getNumber(), comments.getSize(), comments.getTotalElements(),
                comments.getTotalPages(), comments.isLast());
    }


    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.delete(comment);
    }
}
