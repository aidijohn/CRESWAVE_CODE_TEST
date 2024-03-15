package com.creswave.blog.service;

import com.creswave.blog.model.Post;
import com.creswave.blog.payload.PagedResponse;
import com.creswave.blog.payload.PostRequest;
import com.creswave.blog.payload.PostResponse;

public interface PostService {
	PostResponse addPost(PostRequest postRequest, Long userId);

	PagedResponse<Post> getAllPosts(int page, int size);

	PagedResponse getPostById(Long postId, int page, int size);

	PagedResponse<Post> getPostsByUserId(Long userId, int page, int size);

	PostResponse updatePost(Long postId, PostRequest postRequest);

	void deletePost(Long postId);
}
