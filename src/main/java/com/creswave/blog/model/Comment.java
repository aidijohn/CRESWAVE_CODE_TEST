package com.creswave.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
@Table(name = "tbl_comments")
public class Comment {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    @NotBlank
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Comment(@NotBlank @Size(min = 10, message = "The comments must have a minimum of 10 characters") String body) {
        this.body = body;
    }

    @JsonIgnore
    public Post getPost() {
        return post;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
