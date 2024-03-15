package com.creswave.blog.service;

import com.creswave.blog.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();
    User updateUser(User user);

    User findById(Integer id);


    List<User> findAllUsers();
    void deleteUserById(Integer userId);
}
