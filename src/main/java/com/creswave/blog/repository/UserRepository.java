package com.creswave.blog.repository;

import com.creswave.blog.exception.ResourceNotFoundException;
import com.creswave.blog.model.Role;
import com.creswave.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);

    default User getUser(User currentUser) {
        return getUser(currentUser);
    }

    default User getUserByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
