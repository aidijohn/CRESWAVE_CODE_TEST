package com.creswave.blog.repository;

import com.creswave.blog.model.Post;
import com.creswave.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findById(Long postId, Pageable pageable);
    Page<Post> findByTitleContainingOrBodyContaining(String title, String body, Pageable pageable);

}

