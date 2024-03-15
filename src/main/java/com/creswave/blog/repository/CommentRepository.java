package com.creswave.blog.repository;

import com.creswave.blog.model.Comment;
import com.creswave.blog.model.Post;
import com.creswave.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //Page<Comment> findByPostId(Long postId, Pageable pageable);
    Page<Comment> findByPost(Post post, Pageable pageable);

    Page<Comment> findByUser(User user, Pageable pageable);
}
