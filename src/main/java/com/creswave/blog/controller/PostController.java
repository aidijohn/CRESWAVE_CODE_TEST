package com.creswave.blog.controller;

import com.creswave.blog.model.Post;
import com.creswave.blog.payload.PagedResponse;
import com.creswave.blog.payload.PostRequest;
import com.creswave.blog.payload.PostResponse;
import com.creswave.blog.service.PostService;
import com.creswave.blog.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostService postService;

	@PostMapping("/addPost")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PostResponse> addPost(@Valid @RequestBody PostRequest postRequest, @RequestParam("user_id") Long userId) {
		PostResponse response = postService.addPost(postRequest, userId);

		logger.debug("Called PostController.addPost");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/getAllPosts")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<Post>> getAllPosts(
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
		PagedResponse<Post> response = postService.getAllPosts(page, size);

		logger.debug("Called PostController.getAllPosts");

		return new ResponseEntity< >(response, HttpStatus.OK);
	}


	@GetMapping("/getPostById/{postId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<Post>> getPostById(
			@PathVariable Long postId,
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		logger.debug("Called PostController.getPostById");

		PagedResponse<Post> response = postService.getPostById(postId, page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@GetMapping("/getPostsByUserId/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagedResponse<Post>> getPostsByUserId(
			@PathVariable Long userId,
			@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

		logger.debug("Called PostController.getPostsByUserId");

		PagedResponse<Post> response = postService.getPostsByUserId(userId, page, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@PutMapping("/updatePost/{postId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> updatePost(@PathVariable Long postId,
														  @Valid @RequestBody PostRequest postRequest) {

		logger.debug("Called PostController.updatePost");

		PostResponse response = postService.updatePost(postId, postRequest);
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", "Post details successfully updated");
		return ResponseEntity.ok(responseBody);
	}


	@DeleteMapping("/deletePost/{postId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> deletePost(@PathVariable Long postId) {

		logger.debug("Called PostController.deletePost");

		postService.deletePost(postId);
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", "Post record successfully deleted");
		return ResponseEntity.ok(responseBody);
	}

}
