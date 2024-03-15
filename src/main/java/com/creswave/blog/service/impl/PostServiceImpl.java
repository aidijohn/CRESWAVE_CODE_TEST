package com.creswave.blog.service.impl;

import com.creswave.blog.exception.ResourceNotFoundException;
import com.creswave.blog.model.Post;
import com.creswave.blog.model.User;
import com.creswave.blog.payload.PagedResponse;
import com.creswave.blog.payload.PostRequest;
import com.creswave.blog.payload.PostResponse;
import com.creswave.blog.repository.PostRepository;
import com.creswave.blog.repository.UserRepository;
import com.creswave.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.creswave.blog.utils.AppConstants.*;
import static com.creswave.blog.utils.AppUtils.validatePageNumberAndSize;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;


	//add new blog post
	@Override
	public PostResponse addPost(PostRequest postRequest, Long userId) {
		User user = userRepository.findById(Long.valueOf(userId))
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Post post = new Post();
		post.setTitle(postRequest.getTitle());
		post.setBody(postRequest.getBody());
		post.setUser(user);

		Post savedPost = postRepository.save(post);

		return new PostResponse(savedPost.getTitle(), savedPost.getBody(), "Successfully created new post");
	}


	//get all posts
	@Override
	public PagedResponse<Post> getAllPosts(int page, int size) {
		validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Post> posts = postRepository.findAll(pageable);

		List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

		return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}


	//get single post by ID
	@Override
	public PagedResponse<Post> getPostById(Long postId, int page, int size) {
		validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Post> posts = postRepository.findById(postId, pageable);

		List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

		return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}


	//get All Posts By user ID
	@Override
	public PagedResponse<Post> getPostsByUserId(Long userId, int page, int size) {
		validatePageNumberAndSize(page, size);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Post> posts = postRepository.findByUser(user, pageable);

		List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

		return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}


	//update a post
	@Override
	public PostResponse updatePost(Long postId, PostRequest postRequest) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		post.setTitle(postRequest.getTitle());
		post.setBody(postRequest.getBody());
		// Update other fields as needed

		Post updatedPost = postRepository.save(post);

		return new PostResponse(updatedPost.getTitle(), updatedPost.getBody(), "Post updated successfully");
	}


	//search blog
	@Override
	public PagedResponse<Post> searchPosts(String searchTerm, int page, int size) {
		validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

		Page<Post> posts = postRepository.findByTitleContainingOrBodyContaining(searchTerm, searchTerm, pageable);

		List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

		return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}



	//delete a post
	@Override
	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		postRepository.delete(post);
	}

}
